package combat;

import entity.Entity;
import entity.Player;
import items.Armor;
import items.Item;
import items.Rarity;
import items.Weapon;
import main.GamePanel;
import main.PaletteSwap;
import main.UI;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CombatState {
    GamePanel gp;
    UI ui;
    Graphics2D g2;

    public Entity monster;
    public int monsterIndex;

    public int commandNum = 0;
    static final int CMD_ATTACK    = 0;
    static final int CMD_ABILITY   = 1;
    static final int CMD_INVENTORY = 2;
    static final int CMD_MINIMAP   = 3;
    static final int CMD_FLEE      = 4;
    static final int CMD_COUNT     = 5;

    public boolean inAbilityMenu = false;
    public int abilityCommandNum = 0;

    public static final int PLAYER_TURN   = 0;
    public static final int MONSTER_TURN  = 1;
    public static final int COMBAT_OVER   = 2;
    public int turnPhase = PLAYER_TURN;

    // Deciso a inizio round
    boolean playerGoesFirst = true;  // chi va per primo in QUESTO round
    boolean firstMoverActed = false; // true dopo che chi va per primo ha già agito

    // Evita di rivalutare beforeTurn() (stordimento ecc.) ad ogni frame mentre si aspetta
    // l'input del giocatore — va valutato una volta sola all'inizio del turno di ciascuno.
    boolean preTurnChecked = false;

    // I messaggi di combattimento passano da qui invece di essere scritti subito in
    // combatMessage — update() li estrae uno alla volta quando messageTimer torna a 0. Prepara
    // il terreno per più messaggi sullo stesso evento (es. un multi-colpo) senza sovrascriversi.
    private final java.util.ArrayDeque<String> actionQueue = new java.util.ArrayDeque<>();

    public String combatMessage = "";
    int messageTimer = 0;
    int monsterSpriteCounter = 0;
    int monsterSpriteNum = 1;
    boolean scossaExtraAttack = false;
    boolean combatVictory = false;

    // Contrattacco (Weapon.counterattackChance): un turno EXTRA per il player, ristretto a
    // Attack/Ability, che NON sostituisce il suo turno normale — vedi il blocco counterattack
    // dentro dealDamage() e resolveCounterattackCommand(). Solo il player può subirlo oggi: weaponOf() ritorna
    // null per i mostri (non hanno equip), quindi non possono mai essere il difensore qui.
    boolean counterattackPending = false;
    Entity counterattackOrigin   = null; // chi il player deve colpire (chi l'ha appena attaccato)
    boolean insideCounterattack  = false; // guardia anti-ricorsione, vedi il blocco counterattack in dealDamage()

    // ─────────────────────────────────────────────
    //  MIRA (attacco normale del player — parata/schivata angolare)
    // ─────────────────────────────────────────────
    // Sostituisce SOLO "NormalAttack" del player: le abilità restano a formula come prima.
    // Vedi startAiming()/confirmAim() per il flusso completo.
    boolean aiming = false;
    int clashX, clashY;      // punto fisso di riferimento a schermo (centro del mostro)
    int aimX, aimY;          // posizione del colpo scelta dall'attaccante (coordinate schermo)
    double aimAngleDeg = 0;  // angolazione del colpo
    static final int AIM_RANGE = 60;      // raggio massimo di spostamento dal centro (px)
    static final int AIM_STEP = 6;        // px spostati per pressione tasto
    public static final double AIM_ANGLE_STEP = 10; // gradi ruotati per pressione tasto — pubblica, la usa anche KeyHandler

    public CombatState(GamePanel gp, UI ui) {
        this.gp = gp;
        this.ui = ui;
    }

    // ─────────────────────────────────────────────
    //  START / END
    // ─────────────────────────────────────────────

    public void startCombat(Entity monster, int monsterIndex) {
        this.monster           = monster;
        this.monsterIndex      = monsterIndex;
        this.commandNum        = 0;
        this.inAbilityMenu     = false;
        this.abilityCommandNum = 0;
        this.combatMessage     = "A wild " + monster.name + " approaches!";
        this.messageTimer      = 90;
        this.monsterSpriteCounter = 0;
        this.monsterSpriteNum  = 1;
        this.scossaExtraAttack = false;
        decideTurnOrder(); // chi va per primo nel round 1, in base a player.speed vs monster.speed
    }

    void endCombat() {
        ElementSystem.removeAllEffects(gp.player);
        // Do NOT remove the monster here — GamePanel removes it once the dying animation finishes

        gp.gameState   = gp.playState;
        monster        = null;
        monsterIndex   = -1;
        turnPhase      = PLAYER_TURN;
        playerGoesFirst = true;
        firstMoverActed = false;
        preTurnChecked = false;
        actionQueue.clear();
        inAbilityMenu  = false;
        abilityCommandNum = 0;
        combatMessage  = "";
        messageTimer   = 0;
        scossaExtraAttack = false;
        combatVictory  = false;
        counterattackPending = false;
        counterattackOrigin  = null;
        insideCounterattack  = false;
        aiming = false;
    }

    // ─────────────────────────────────────────────
    //  UPDATE
    // ─────────────────────────────────────────────

    public void update() {
        monsterSpriteCounter++;
        if (monsterSpriteCounter > 20) {
            monsterSpriteNum = monsterSpriteNum == 1 ? 2 : 1;
            monsterSpriteCounter = 0;
        }
        if (messageTimer > 0) { messageTimer--; return;}

        // Un messaggio in coda va mostrato prima di qualunque altra cosa (anche prima di finire
        // il combattimento, se turnPhase è già COMBAT_OVER) — vedi queueAction().
        if (!actionQueue.isEmpty()) {
            combatMessage = actionQueue.poll();
            messageTimer  = 90;
            return;
        }

        if (turnPhase == PLAYER_TURN) {
            // beforeTurn() va valutato una volta sola a inizio turno, non ad ogni frame in cui
            // si aspetta l'input — altrimenti il 50% di skip di Stordimento verrebbe ritentato
            // ogni frame e finirebbe per scattare quasi sempre entro pochi istanti.
            if (!preTurnChecked) {
                preTurnChecked = true;
                if (beforeTurn(gp.player, true)) { advanceRound(true); return; }
            }
            return; // aspetta confirmCommand()
        }

        if (turnPhase == MONSTER_TURN) { monsterTurn(); return; }

        if (turnPhase == COMBAT_OVER) {
            int savedMonsterIndex = monsterIndex;
            boolean wasVictory = combatVictory;
            endCombat();
            if (wasVictory && savedMonsterIndex >= 0 && savedMonsterIndex < gp.monster.length) {
                gp.monster[savedMonsterIndex].dying = true;
                gp.monster[savedMonsterIndex].dyingCounter = 0;
            }
            return;
        }
    }

    // ─────────────────────────────────────────────
    //  INPUT
    // ─────────────────────────────────────────────

    public void navigateUp() {
        if (inAbilityMenu) {
            abilityCommandNum--;
            if (abilityCommandNum < 0) abilityCommandNum = gp.player.unlockedAbilities.size() - 1;
        } else if (counterattackPending) {
            commandNum = (commandNum == CMD_ATTACK) ? CMD_ABILITY : CMD_ATTACK; // solo 2 scelte, niente Inventory/Minimap/Flee
        } else {
            commandNum--;
            if (commandNum < 0) commandNum = CMD_COUNT - 1;
        }
    }

    public void navigateDown() {
        if (inAbilityMenu) {
            abilityCommandNum++;
            if (abilityCommandNum >= gp.player.unlockedAbilities.size()) abilityCommandNum = 0;
        } else if (counterattackPending) {
            commandNum = (commandNum == CMD_ATTACK) ? CMD_ABILITY : CMD_ATTACK;
        } else {
            commandNum++;
            if (commandNum >= CMD_COUNT) commandNum = 0;
        }
    }

    public void pressEsc() {
        if (aiming) { aiming = false; return; } // annulla la mira, torna al menu senza consumare il turno
        if (inAbilityMenu) { inAbilityMenu = false; abilityCommandNum = 0; }
    }

    public void confirmCommand() {
        if (turnPhase != PLAYER_TURN || messageTimer > 0) return;

        if (aiming) { confirmAim(); return; }

        if (counterattackPending) {
            resolveCounterattackCommand();
            return;
        }

        if (inAbilityMenu) {
            List<String> abilities = gp.player.unlockedAbilities;
            if (!abilities.isEmpty()) {
                playerUseAbility(abilities.get(abilityCommandNum));
                inAbilityMenu = false;
                abilityCommandNum = 0;
            }
            return;
        }

        switch (commandNum) {
            case CMD_ATTACK:
                startAiming();
                break;
            case CMD_ABILITY:
                if (gp.player.unlockedAbilities.isEmpty()) {
                    combatMessage = "Your journey didn't bring\nyou far enough yet.";
                    messageTimer  = 60;
                } else {
                    inAbilityMenu = true;
                    abilityCommandNum = 0;
                }
                break;
            case CMD_INVENTORY:
                combatMessage = "Inventory is empty.";
                messageTimer  = 60;
                break;
            case CMD_MINIMAP:
                combatMessage = "(Minimap - not yet implemented)";
                messageTimer  = 60;
                break;
            case CMD_FLEE:
                tryFlee();
                break;
        }
    }

    // Menu ristretto durante un contrattacco: solo Attack/Ability (Inventory/Minimap/Flee
    // ignorati anche se per errore commandNum ci finisse sopra) — vedi dealDamage().
    private void resolveCounterattackCommand() {
        if (inAbilityMenu) {
            List<String> abilities = gp.player.unlockedAbilities;
            if (!abilities.isEmpty()) {
                executeCounterattack(abilities.get(abilityCommandNum));
                inAbilityMenu = false;
                abilityCommandNum = 0;
            }
            return;
        }
        if (commandNum == CMD_ATTACK) {
            executeCounterattack("NormalAttack");
        } else if (commandNum == CMD_ABILITY) {
            if (!gp.player.unlockedAbilities.isEmpty()) {
                inAbilityMenu = true;
                abilityCommandNum = 0;
            }
        }
    }

    // Risolve il colpo di contrattacco e riprende il round esattamente da dove monsterTurn()
    // l'aveva lasciato — niente afterTurn(): è un'azione bonus, non il vero turno del player,
    // quindi non fa ticchettare di nuovo i suoi effetti attivi.
    private void executeCounterattack(String abilityId) {
        counterattackPending = false;
        Entity origin = counterattackOrigin;
        counterattackOrigin = null;
        commandNum = CMD_ATTACK; // ripristina il menu normale per il prossimo vero turno

        dealDamage(gp.player, origin, abilityId);
        if (origin.life     <= 0) { checkVictory(); return; }
        if (gp.player.life  <= 0) { checkDefeat();  return; }
        advanceRound(false);
    }

    // ─────────────────────────────────────────────
    //  ORDINE DEI TURNI (velocità)
    // ─────────────────────────────────────────────

    // Decide chi va per primo in QUESTO round confrontando la velocità — richiamata a inizio
    // combattimento (startCombat) e a inizio di ogni round successivo (advanceRound), non solo
    // una volta: così un cambio di velocità a metà scontro si riflette dal round dopo.
    void decideTurnOrder() {
        playerGoesFirst = gp.player.speed >= monster.speed; // pareggio -> il giocatore, come prima
        firstMoverActed = false;
        preTurnChecked = false;
        turnPhase = playerGoesFirst ? PLAYER_TURN : MONSTER_TURN;
    }

    // Da richiamare a fine turno di chi ha appena agito. Se era il primo del round, passa al
    // secondo; se era il secondo, il round è finito e se ne decide uno nuovo (ricontrollando la
    // velocità, non semplicemente "tocca sempre al giocatore" come prima).
    void advanceRound(boolean actorWasPlayer) {
        if (!firstMoverActed) {
            firstMoverActed = true;
            preTurnChecked = false;
            turnPhase = actorWasPlayer ? MONSTER_TURN : PLAYER_TURN;
        } else {
            decideTurnOrder();
        }
        if (turnPhase == PLAYER_TURN) commandNum = 0;
    }

    boolean isStunned(Entity e) {
        return ElementSystem.hasEffect(e, ElementSystem.StatusEffect.STORDIMENTO);
    }

    // Accoda un messaggio di combattimento invece di scriverlo subito in combatMessage — update()
    // ne estrae uno alla volta quando messageTimer torna a 0 (vedi update()). Prepara il terreno
    // per più messaggi sullo stesso evento (es. un multi-colpo) senza che si sovrascrivano.
    void queueAction(String message) {
        actionQueue.add(message);
    }

    // ─────────────────────────────────────────────
    //  HOOK PRE/POST TURNO
    // ─────────────────────────────────────────────

    /**
     * Richiamato all'inizio del turno di 'actor', PRIMA che agisca. Ritorna true se il turno
     * va saltato del tutto — il chiamante deve poi passare la mano con advanceRound() e non
     * lasciar agire l'entità questo giro.
     *
     * Generalizza quello che prima era un caso speciale solo per lo stordimento dentro
     * update()/monsterTurn(): qualunque nuovo "controllo prima di agire" (una nuova azione tra
     * i turni, un altro status che blocca il turno...) va aggiunto qui, non duplicato nei due
     * punti da cui si entra in un turno.
     */
    boolean beforeTurn(Entity actor, boolean isPlayer) {
        firePreTurnEffects(actor);
        if (isStunned(actor)) {
            boolean skipTurn = new Random().nextInt(100) < 50; // Stordimento: 50% di saltare il turno
            if (skipTurn) {
                String who = isPlayer ? "You are" : actor.name + " is";
                String s   = isPlayer ? "" : "s";
                queueAction(who + " stunned and skip" + s + " the turn!");
                afterTurn(actor, isPlayer); // il turno è "avvenuto" comunque: tick dei propri effetti
                return true;
            }
        }
        return false;
    }

    /**
     * Richiamato a fine turno di 'actor', DOPO che ha agito (o dopo che gli è stato saltato da
     * beforeTurn). Applica il tick degli effetti attivi sul PROPRIO portatore — bruciatura,
     * sovraccarico, tempesta, carbonizzazione, ramificazione, erosione...
     *
     * Prima questi effetti ticchettavano solo sul BERSAGLIO quando veniva colpito, dentro
     * dealDamage(): un mostro con la bruciatura la subiva solo se il giocatore lo colpiva di
     * nuovo, non ad ogni turno. Ora ogni entità subisce i propri effetti una volta a round, sul
     * proprio turno — risolve il TODO aperto in STATUS.md §5.
     */
    void afterTurn(Entity actor, boolean isPlayer) {
        firePostTurnEffects(actor);
        int dmg = ElementSystem.processTurnEffects(actor);
        if (dmg > 0) {
            actor.life -= dmg;
            String who = isPlayer ? "You take" : actor.name + " takes";
            queueAction(who + " " + dmg + " damage from active effects!");
        }
    }

    /** PRE_TURN/POST_TURN di ogni pezzo equipaggiato da 'actor' — solo i Player hanno equip
     *  oggi (weaponOf() lo nota già: i mostri non ne hanno), quindi per un Monster questi due
     *  metodi non trovano nulla da richiamare, non è un caso speciale da gestire qui.
     *
     *  target è il vero avversario (opponentOf), non più 'actor' due volte: un effetto "su di
     *  sé" (es. una cura) ignora semplicemente target e agisce solo su source; un effetto
     *  offensivo (es. "Eco", che attacca due volte: il secondo colpo scatta come POST_TURN) ha
     *  già il bersaglio giusto senza doverlo recuperare da solo. */
    private void firePreTurnEffects(Entity actor) {
        if (!(actor instanceof Player p)) return;
        Entity opponent = opponentOf(actor);
        for (Item item : p.equippedItems())
            for (SpecialEffect eff : item.preTurnEffects) eff.execute(this, actor, opponent);
        fireRaritySpecialEffects(p, actor, opponent, SpecialEffect.Timing.PRE_TURN);
    }

    private void firePostTurnEffects(Entity actor) {
        if (!(actor instanceof Player p)) return;
        Entity opponent = opponentOf(actor);
        for (Item item : p.equippedItems())
            for (SpecialEffect eff : item.postTurnEffects) eff.execute(this, actor, opponent);
        fireRaritySpecialEffects(p, actor, opponent, SpecialEffect.Timing.POST_TURN);
    }

    /**
     * Effetti legati alla rarità, non ai componenti — richiamati dopo i preTurnEffects/
     * postTurnEffects "normali" sopra, stesso timing.
     *   MITICO   — invariato: attivo SEMPRE, su QUALUNQUE pezzo equipaggiato, per pezzo
     *              (ognuno il proprio mythicUniqueStub) — indipendente dal set.
     *   FULL SET — OGNI pezzo d'armatura equipaggiato spara il PROPRIO fullSetOrStyleStub (non
     *              un bonus condiviso: ognuno ha un testo diverso), MA solo se
     *              activeArmorMatchGroup() dice che il set è completo — vedi Player: 12 slot,
     *              confrontati per GRUPPO (Guardia+Passo sono lo stesso gruppo, Veggente+Ombra
     *              pure — Voto resta da solo, un Voto da 12 pezzi non può esistere).
     */
    private void fireRaritySpecialEffects(Player p, Entity actor, Entity opponent, SpecialEffect.Timing timing) {
        boolean setComplete = p.activeArmorMatchGroup() >= 0;
        for (Item item : p.equippedItems()) {
            if (item.rarity == Rarity.MYTHIC) {
                SpecialEffect eff = item.resolveMythicUniqueEffect();
                if (eff != null && eff.timing() == timing) eff.execute(this, actor, opponent);
            }
            if (setComplete && item instanceof Armor) {
                SpecialEffect eff = item.resolveFullSetOrStyleEffect();
                if (eff != null && eff.timing() == timing) eff.execute(this, actor, opponent);
            }
        }
    }

    // Chiavi "una volta per combattimento" (es. Elmo Saldo: il primo stordimento annullato) —
    // svuotato implicitamente ad ogni nuovo CombatState (un combattimento = un'istanza).
    private final Set<String> usedOnce = new HashSet<>();

    /** true la prima volta che viene chiamato con questa chiave in questo combattimento, false
     *  tutte le volte successive — per gli effetti "una volta per combattimento". */
    boolean consumeOnce(String key) {
        return usedOnce.add(key);
    }

    /** true se 'e' ha ORA attivo un effetto — mitico (sempre) o di pieno set (solo se il
     *  gruppo è completo, vedi Player.activeArmorMatchGroup()) — il cui testo INIZIA con
     *  'textPrefix'. startsWith invece di equals: da questa sessione i testi Masterwork/Mitico
     *  hanno un suffisso di livello in coda (es. "(livello Adepto)"), diverso per fascia — vedi
     *  generate_equip.py. Per query puntuali FUORI dal ciclo di turno (es. "posso essere
     *  disarmato adesso?"). */
    boolean hasActivePassive(Entity e, String textPrefix) {
        if (!(e instanceof Player p)) return false;
        for (Item item : p.equippedItems())
            if (item.rarity == Rarity.MYTHIC && item.mythicUniqueStub != null && item.mythicUniqueStub.startsWith(textPrefix)) return true;
        if (p.activeArmorMatchGroup() >= 0)
            for (Item item : p.equippedItems())
                if (item instanceof Armor && item.fullSetOrStyleStub != null && item.fullSetOrStyleStub.startsWith(textPrefix)) return true;
        return false;
    }

    // 3 pezzi diversi danno la stessa immunità (Vanbrace full-set Guardia + il suo stesso
    // mitico + Gauntlet mitico) — non è un "caso speciale" per ognuno, sono 3 prefissi che
    // questo helper controlla nello stesso modo generico di hasActivePassive().
    private static final String[] DISARM_IMMUNITY_TEXTS = {
        "[Full Set: Guardia] Presa Ferrea: non perdi mai l'arma, nemmeno per effetti che ignorano le resistenze",
        "[Mitico] Presa Eterna: la tua arma non può mai essere rimossa o sostituita contro la tua volontà",
        "[Mitico] Mano di Ferro: non puoi mai essere disarmato",
    };
    private boolean isDisarmImmune(Entity e) {
        for (String t : DISARM_IMMUNITY_TEXTS) if (hasActivePassive(e, t)) return true;
        return false;
    }

    /** "Guardia... riduce i danni alle mani, se subiti (come IRL)" + "controllo: probDiDisarmo-
     *  controllo": Guardia E Manico dell'arma equipaggiata da 'target' sottraggono ENTRAMBI
     *  punti percentuali diretti dalla probabilità di essere disarmato (10 punti ciascuno =
     *  -10%, si sommano), non passano da MaterialRegistry — sono modificatori diretti, non un
     *  bonus %/StatType. Nessun'arma equipaggiata (mostri) = nessuna resistenza, chance
     *  invariata. */
    private int effectiveDisarmChance(int baseChance, Entity target) {
        Weapon defWeapon = weaponOf(target);
        int resistenza = (defWeapon != null) ? (defWeapon.guardiaBonus + defWeapon.manicoBonus) : 0;
        return Math.max(0, baseChance - resistenza * 10);
    }

    /** L'altro combattente rispetto ad actor — sempre gp.player o monster, essendo un
     *  combattimento 1 contro 1. */
    private Entity opponentOf(Entity actor) {
        return (actor == gp.player) ? monster : gp.player;
    }

    // Calcola e applica il danno di un'abilità da attacker a target: mira/schivata (comprese
    // le riduzioni da Stordimento/Accecamento/Deviazione e i blocchi mira a distanza di
    // Accecamento/Polverizzazione/Deviazione), stat giusta per il tipo di abilità (fisica vs
    // elementale), moltiplicatore elementale, reazione, disarmo (Inondazione), e il contraccolpo
    // di Folgore/Infiammazione sull'attaccante in caso di fallimento.
    //
    // Qualunque nuova abilità, anche una che non infligge danno diretto (es. un'abilità
    // "di supporto" che applica solo un effetto), deve comunque passare da qui — non richiamare
    // Ability.use()/ElementSystem.getReaction() per conto proprio altrove — altrimenti reazioni,
    // disarmo e SpecialEffect non scatterebbero per quell'abilità.
    // Arma equipaggiata in MainHand da un'entità, se ne ha una e se è un Weapon (non tutte le
    // entità hanno equip — i mostri oggi non ne hanno, weaponOf ritorna null per loro).
    private Weapon weaponOf(Entity e) {
        if (!(e instanceof Player)) return null;
        Item item = ((Player) e).mainHandSlot.item;
        return (item instanceof Weapon) ? (Weapon) item : null;
    }

    void dealDamage(Entity attacker, Entity target, String abilityId) {
        dealDamage(attacker, target, abilityId, false);
    }

    // forceHit=true: salta il tiro di mira interno, il colpo va sempre a segno — usato dal
    // minigioco di parata/schivata (CombatState.resolveDodge()/resolveParry()) quando l'esito
    // è già stato deciso da un altro calcolo (schivata fallita, parata elusa) e non va rideciso
    // qui con un secondo tiro casuale indipendente.
    void dealDamage(Entity attacker, Entity target, String abilityId, boolean forceHit) {
        ElementSystem.Element abilityElement = Ability.getElement(abilityId);

        double abrBonus = (abilityElement == ElementSystem.Element.FUOCO
                && ElementSystem.hasEffect(attacker, ElementSystem.StatusEffect.ABRASIONE)) ? 1.5 : 1.0;

        int baseDmg     = Ability.use(abilityId, attacker, target);
        double elemMult = ElementSystem.getMultiplier(abilityElement, target.lastElementHit);
        double potMult  = ElementSystem.hasEffect(target, ElementSystem.StatusEffect.POTENZIAMENTO) ? 1.2 : 1.0;
        double rotMult  = ElementSystem.hasEffect(target, ElementSystem.StatusEffect.ROTTURA)
                ? 1.0 + (0.10 * attacker.level) : 1.0;
        double effMult  = attacker.efficiency / 100.0;
        int totalDmg    = (int) Math.max(1, baseDmg * elemMult * abrBonus * potMult * rotMult * effMult);
        int raggioDmg   = ElementSystem.hasEffect(target, ElementSystem.StatusEffect.RAGGIO)
                ? 5 * attacker.getElementAttack(ElementSystem.Element.FULMINE) : 0;

        boolean rangedBlocked = ElementSystem.isRangedBlocked(attacker, abilityId);
        boolean deflected     = ElementSystem.isDeflected(target, abilityId);

        double precMult   = ElementSystem.precisionMultiplier(attacker);
        int hitChance      = (int) (attacker.precision * precMult) - target.evasion;
        boolean hit        = forceHit || (!rangedBlocked && !deflected && new Random().nextInt(100) < hitChance);

        boolean attackerIsPlayer = (attacker == gp.player);
        String attackerLabel = attackerIsPlayer ? "You" : attacker.name;
        String targetLabel   = (target == gp.player) ? "Your" : target.name;
        String s = attackerIsPlayer ? "" : "s"; // suffisso verbo 3a persona ("use"/"miss" vs "uses"/"misses")

        StringBuilder msg = new StringBuilder();

        if (rangedBlocked) {
            msg.append(attackerLabel).append(" can't take aim").append(s).append(" — no ranged attacks possible!");
        } else if (deflected) {
            msg.append(targetLabel).append(" deflect").append(attackerIsPlayer ? "s" : "").append(" the attack!");
        } else if (!hit && ElementSystem.hasEffect(attacker, ElementSystem.StatusEffect.FOLGORE)) {
            int folgoreDmg = 10 * attacker.getElementAttack(ElementSystem.Element.FULMINE);
            attacker.life -= folgoreDmg;
            msg.append(attackerLabel).append(" miss").append(s).append(" and take").append(s)
                    .append(" ").append(folgoreDmg).append(" Folgore damage!");
        } else if (!hit && ElementSystem.hasEffect(attacker, ElementSystem.StatusEffect.INFIAMMAZIONE)) {
            int infDmg = 10 * attacker.getElementAttack(ElementSystem.Element.FUOCO);
            attacker.life -= infDmg;
            msg.append(attackerLabel).append(" miss").append(s).append(" and take").append(s)
                    .append(" ").append(infDmg).append(" Infiammazione damage!");
        } else {
            target.life -= hit ? (totalDmg + raggioDmg) : 0;
            Reaction reaction = ElementSystem.getReaction(target.lastElementHit, abilityElement, target.level, attacker);
            int reactionDmg   = ElementSystem.applyReaction(reaction, target, abilityElement);
            target.life      -= reactionDmg;

            msg.append(attackerLabel).append(" use").append(s).append(" ").append(Ability.getName(abilityId));
            if (!hit) {
                msg.append(" → MISSED!");
            } else {
                if (elemMult > 1.0) msg.append(" [ADVANTAGE]");
                else if (elemMult < 1.0) msg.append(" [DISADVANTAGE]");
                msg.append(" → ").append(totalDmg).append(" damage");
                if (raggioDmg   > 0) msg.append(" + ").append(raggioDmg).append(" (Raggio)");
                if (reactionDmg > 0) msg.append(" + ").append(reactionDmg).append(" (").append(reaction.name).append(")");
                if (reaction != Reaction.NONE) msg.append("  ⚡ ").append(reaction.name).append("!");

                // Inondazione: disarmo "immediato" (base 100%, comunque riducibile dalla
                // Guardia del bersaglio — vedi effectiveDisarmChance) dell'arma (solo il
                // player ha equipaggiamento).
                if (reaction.disarms && target instanceof Player && !isDisarmImmune(target)
                        && new Random().nextInt(100) < effectiveDisarmChance(100, target)) {
                    ((Player) target).unequip(Item.ItemSlot.MainHand);
                    msg.append("  [DISARMED: weapon unequipped]");
                }

                // Meccaniche dell'arma equipaggiata da chi attacca — tutte automatiche su ogni
                // colpo andato a segno (nessuna richiede un'azione dedicata nel menu).
                Weapon attackerWeapon = weaponOf(attacker);
                if (attackerWeapon != null) {
                    if (attackerWeapon.disarmChance > 0
                            && target instanceof Player
                            && !isDisarmImmune(target)
                            && new Random().nextInt(100) < effectiveDisarmChance(attackerWeapon.disarmChance, target)) {
                        ((Player) target).unequip(Item.ItemSlot.MainHand);
                        msg.append("  [DISARMED]");
                    }
                    if (attackerWeapon.stunChance > 0
                            && new Random().nextInt(100) < attackerWeapon.stunChance) {
                        ElementSystem.addEffect(target, ElementSystem.StatusEffect.STORDIMENTO, 1);
                        msg.append("  [STUNNED]");
                    }
                }

                // Contrattacco: dipende dall'arma di chi SUBISCE il colpo, non di chi attacca.
                // Concede un turno EXTRA ad "attack/ability" soltanto — NON sostituisce il turno
                // normale di nessuno dei due. Solo il player può subirlo oggi (weaponOf() ritorna
                // null per i mostri, che non hanno equip): il ramo "else" qui sotto è quindi
                // irraggiungibile ora, pronto per quando/se i mostri potranno equipaggiare armi.
                Weapon targetWeapon = weaponOf(target);
                if (targetWeapon != null && targetWeapon.counterattackChance > 0 && !insideCounterattack
                        && new Random().nextInt(100) < targetWeapon.counterattackChance) {
                    msg.append("  [COUNTERATTACK TRIGGERED]");
                    if (target instanceof Player) {
                        counterattackPending = true;
                        counterattackOrigin  = attacker;
                    } else {
                        insideCounterattack = true;
                        dealDamage(target, attacker, target.chooseAction());
                        insideCounterattack = false;
                    }
                }
            }
            msg.append("  [").append(targetLabel).append(" HP: ")
                    .append(Math.max(0, target.life)).append("/").append(target.maxLife).append("]");

            // Scossa: solo con l'Attacco Normale, solo quando è il giocatore a colpire e il
            // bersaglio la porta — comportamento invariato rispetto a prima salvo la restrizione
            // esplicita ad "attacchi normali" richiesta ("due attacchi normali di fila").
            if (attackerIsPlayer) {
                if (hit && abilityId.equals("NormalAttack") && !scossaExtraAttack
                        && ElementSystem.hasEffect(target, ElementSystem.StatusEffect.SCOSSA)) {
                    scossaExtraAttack = true;
                    msg.append("  [SCOSSA: attack again!]");
                } else {
                    scossaExtraAttack = false;
                }
            }
        }

        queueAction(msg.toString());

        // Azione speciale dell'abilità (STUB per ora — vedi SpecialEffect).
        SpecialEffect action = Ability.getSpecialAction(abilityId);
        if (action != null) action.execute(this, attacker, target);
    }

    // ─────────────────────────────────────────────
    //  PLAYER TURN
    // ─────────────────────────────────────────────

    void playerUseAbility(String abilityId) {
        dealDamage(gp.player, monster, abilityId);
        afterTurn(gp.player, true);
        if (monster.life   <= 0) { checkVictory(); return; }
        if (gp.player.life <= 0) { checkDefeat();  return; }
        if (!scossaExtraAttack) advanceRound(true); // scossa: il giocatore riattacca subito, niente avanzamento
    }

    // ─────────────────────────────────────────────
    //  MIRA — attacco normale del player: minigioco angolare di parata/schivata
    //  (SOLO "NormalAttack"; le abilità restano su playerUseAbility()/dealDamage() a formula)
    // ─────────────────────────────────────────────

    void startAiming() {
        aiming = true;
        int monsterSize = gp.tileSize * 3;
        clashX = gp.screenWidth / 2;
        clashY = gp.tileSize + monsterSize / 2; // stesso centro usato da drawMonster()
        aimX = clashX;
        aimY = clashY;
        aimAngleDeg = 0;
    }

    public void aimMove(int dx, int dy) {
        if (!aiming) return;
        aimX = clamp(aimX + dx * AIM_STEP, clashX - AIM_RANGE, clashX + AIM_RANGE);
        aimY = clamp(aimY + dy * AIM_STEP, clashY - AIM_RANGE, clashY + AIM_RANGE);
    }

    public void aimRotate(double deltaDeg) {
        if (!aiming) return;
        aimAngleDeg = (aimAngleDeg + deltaDeg + 360) % 360;
    }

    private int clamp(int v, int lo, int hi) {
        return Math.max(lo, Math.min(hi, v));
    }

    // La "lama" dell'attaccante: un rettangolo sottile centrato su (aimX,aimY), ruotato di
    // aimAngleDeg. Stesso spazio di coordinate (schermo) delle zone del difensore trasformate.
    private Shape buildAttackShape() {
        Rectangle2D.Double local = new Rectangle2D.Double(-3, -20, 6, 40);
        AffineTransform t = new AffineTransform();
        t.translate(aimX, aimY);
        t.rotate(Math.toRadians(aimAngleDeg));
        return t.createTransformedShape(local);
    }

    // Una zona di difesa (coordinate locali, pivot=impugnatura) ruotata di angleDeg e traslata
    // sul punto di scontro fisso (clashX,clashY).
    private Area transformedZone(Weapon.DefenseZone zone, double angleDeg) {
        AffineTransform t = new AffineTransform();
        t.translate(clashX, clashY);
        t.rotate(Math.toRadians(angleDeg));
        return new Area(t.createTransformedShape(zone.shape));
    }

    void confirmAim() {
        aiming = false;
        Weapon defWeapon = monster.resolveDefenseWeapon();
        // Nessuna regola data su come il difensore sceglie tra parare e schivare: placeholder
        // dichiarato — se ha un'arma difensiva prova a pararla il più delle volte, altrimenti
        // schiva sempre (non ha nulla con cui parare). Da rivedere quando deciderai l'IA vera.
        boolean willParry = defWeapon != null && new Random().nextInt(100) < 70;

        if (willParry) resolveParry(defWeapon);
        else resolveDodge();
    }

    private void resolveDodge() {
        boolean isRanged = Ability.isRanged("NormalAttack"); // sempre false oggi: NormalAttack è mischia
        int relevantStat = isRanged ? gp.player.precision : gp.player.speed;
        int dodgeChance = clamp(monster.evasion - relevantStat, 0, 100);
        boolean dodged = new Random().nextInt(100) < dodgeChance;

        if (dodged) {
            queueAction(monster.name + " dodges out of the way!");
        } else {
            queueAction(monster.name + " fails to dodge — clean hit!");
            dealDamage(gp.player, monster, "NormalAttack", true); // schivata fallita: colpo garantito, niente secondo tiro
        }
        finishPlayerAttack();
    }

    private void resolveParry(Weapon defWeapon) {
        // Quanto l'arma difensiva "trema" rispetto al riposo quando intercetta — manico alto =
        // meno variazione, arma più precisa. Scelta di formula arbitraria, non bilanciata: solo
        // per avere qualcosa di funzionante da tarare in seguito.
        double variance = 40.0 / (1 + defWeapon.manicoBonus / 20.0);
        double defenseAngleDeg = defWeapon.staticGuard ? defWeapon.restAngleDeg
                : defWeapon.restAngleDeg + (new Random().nextDouble() * 2 - 1) * variance;

        Area attackArea = new Area(buildAttackShape());
        boolean weakHit = false, rigidHit = false;

        for (Weapon.DefenseZone zone : defWeapon.defenseZones) {
            Area overlap = new Area(attackArea);
            overlap.intersect(transformedZone(zone, defenseAngleDeg));
            if (!overlap.isEmpty()) {
                if (zone.type == Weapon.ZoneType.WEAK) weakHit = true;
                else rigidHit = true;
            }
        }

        if (weakHit) {
            int loss = 8; // placeholder: quanta durabilità toglie un colpo su punto debole
            defWeapon.durability = Math.max(0, defWeapon.durability - loss);
            queueAction(monster.name + " blocks, but you hit a WEAK POINT! -" + loss + " durability");
        } else if (rigidHit) {
            int loss = 2; // placeholder: usura normale di un blocco riuscito
            defWeapon.durability = Math.max(0, defWeapon.durability - loss);
            queueAction(monster.name + " blocks your attack.");
        } else if (defWeapon.staticGuard) {
            // Uno scudo non può mai essere eluso: nessun overlap geometrico forza comunque un
            // blocco rigido, non un colpo passato.
            defWeapon.durability = Math.max(0, defWeapon.durability - 2);
            queueAction(monster.name + " blocks with the shield.");
        } else {
            queueAction("You slip past " + monster.name + "'s guard!");
            dealDamage(gp.player, monster, "NormalAttack", true); // parata elusa: colpo garantito
        }
        finishPlayerAttack();
    }

    private void finishPlayerAttack() {
        afterTurn(gp.player, true);
        if (monster.life   <= 0) { checkVictory(); return; }
        if (gp.player.life <= 0) { checkDefeat();  return; }
        if (!scossaExtraAttack) advanceRound(true);
    }

    void tryFlee() {
        // Naturalizzazione/Infangato bloccano il movimento: fuggire È muoversi, quindi fallisce.
        if (ElementSystem.hasEffect(gp.player, ElementSystem.StatusEffect.NATURALIZZAZIONE)
                || ElementSystem.hasEffect(gp.player, ElementSystem.StatusEffect.INFANGATO)) {
            combatMessage = "You can't move — unable to flee!";
            messageTimer  = 60;
            return;
        }
        combatMessage = "You fled!";
        messageTimer  = 60;
        turnPhase     = COMBAT_OVER;
    }

    // ─────────────────────────────────────────────
    //  MONSTER TURN
    // ─────────────────────────────────────────────

    void monsterTurn() {
        if (beforeTurn(monster, false)) { advanceRound(false); return; }

        String chosenId = monster.chooseAction();
        dealDamage(monster, gp.player, chosenId);
        afterTurn(monster, false);
        if (monster.life <= 0)   { checkVictory(); return; }
        if (gp.player.life <= 0) { checkDefeat();  return; }
        if (counterattackPending) {
            // Turno extra per il player, non il suo turno vero: preTurnChecked=true salta
            // beforeTurn() (niente ri-check dello stordimento per un'azione bonus).
            turnPhase = PLAYER_TURN;
            preTurnChecked = true;
            commandNum = CMD_ATTACK;
            return;
        }
        advanceRound(false);
    }

    // ─────────────────────────────────────────────
    //  VICTORY / DEFEAT
    // ─────────────────────────────────────────────

    void checkVictory() {
        combatVictory = true;
        onVictory();
        turnPhase = COMBAT_OVER;
    }
    void checkDefeat() {
        onDefeat();
        turnPhase = COMBAT_OVER;
    }

    void onVictory() {
        //TODO premi
        gp.questManager.notify(quest.QuestEventType.KILL, monster.name);
        queueAction("You defeated " + monster.name + "!");
    }
    void onDefeat() {
        //TODO penalità
        queueAction("You have been defeated...");
    }

    // ─────────────────────────────────────────────
    //  DRAW
    // ─────────────────────────────────────────────

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        drawBackground();
        drawMonster();
        drawPlayerHUD();
        drawMessageBox();
        if (turnPhase == PLAYER_TURN && messageTimer == 0) {
            if (aiming)             drawAimingOverlay();
            else if (inAbilityMenu) drawAbilityMenu();
            else                    drawCommandMenu();
        }
    }

    // Zona di mira (riquadro), la guardia del difensore alla sua posizione di RIPOSO (non
    // l'angolo vero a cui finirà — quello si scopre solo al momento della parata, così mirare
    // "alla cieca" ha un senso), e la lama dell'attaccante alla posizione/angolo correnti.
    void drawAimingOverlay() {
        g2.setColor(new Color(255, 255, 255, 50));
        g2.fillRect(clashX - AIM_RANGE, clashY - AIM_RANGE, AIM_RANGE * 2, AIM_RANGE * 2);

        Weapon defWeapon = monster.resolveDefenseWeapon();
        if (defWeapon != null) {
            for (Weapon.DefenseZone zone : defWeapon.defenseZones) {
                g2.setColor(zone.type == Weapon.ZoneType.WEAK
                        ? new Color(255, 70, 70, 150) : new Color(140, 140, 255, 150));
                g2.fill(transformedZone(zone, defWeapon.restAngleDeg));
            }
        }

        g2.setColor(Color.yellow);
        g2.fill(buildAttackShape());

        g2.setFont(ui.MaruMonica.deriveFont(Font.PLAIN, 16f));
        g2.setColor(Color.white);
        g2.drawString("Arrows: aim   A/D: rotate   Enter: strike   Esc: back",
                gp.tileSize / 2, gp.screenHeight - gp.tileSize / 2);
    }

    void drawBackground() {
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
    }

    void drawMonster() {
        if (monster == null) return;
        int monsterSize = gp.tileSize * 3;
        int monsterX    = gp.screenWidth / 2 - monsterSize / 2;
        int monsterY    = gp.tileSize;

        java.awt.image.BufferedImage img = (monster.downIdle1 != null && monster.downIdle2 != null)
                ? (monsterSpriteNum == 1 ? monster.downIdle1 : monster.downIdle2) : monster.down1;

        if (monster.palette != null) {
            img = PaletteSwap.getOrCreate("e" + monster.hashCode(), img, monster.palette);
        }

        // No blink during combat — alpha is always 1
        if (img != null) {
            g2.drawImage(img, monsterX, monsterY, monsterSize, monsterSize, null);
        }

        g2.setFont(ui.MaruMonica.deriveFont(Font.PLAIN, 22f));
        g2.setColor(Color.white);
        String info = monster.name + "  HP: " + Math.max(0, monster.life) + "/" + monster.maxLife
                + "  Lv." + monster.level;
        if (!monster.activeEffects.isEmpty()) {
            StringBuilder fx = new StringBuilder(" [");
            for (ElementSystem.ActiveEffect ae : monster.activeEffects)
                fx.append(ae.effect.displayName).append(ae.duration > 0 ? "(" + ae.duration + ")" : "").append(" ");
            fx.append("]");
            info += fx.toString();
        }
        g2.drawString(info, ui.getXforCenteredText(info), monsterY + monsterSize + 36);
    }

    void drawPlayerHUD() {
        g2.setFont(ui.MaruMonica.deriveFont(Font.PLAIN, 22f));
        g2.setColor(Color.white);
        String info = "Player  HP: " + gp.player.life + "/" + gp.player.maxLife
                + "  ATK: " + gp.player.attack + "  DEF: " + gp.player.defense;
        if (!gp.player.activeEffects.isEmpty()) {
            StringBuilder fx = new StringBuilder(" [");
            for (ElementSystem.ActiveEffect ae : gp.player.activeEffects)
                fx.append(ae.effect.displayName).append(ae.duration > 0 ? "(" + ae.duration + ")" : "").append(" ");
            fx.append("]");
            info += fx.toString();
        }
        g2.drawString(info, gp.tileSize / 2, gp.screenHeight - gp.tileSize * 5);
    }

    void drawMessageBox() {
        if (combatMessage.isEmpty()) return;
        int x = gp.tileSize / 2, y = gp.screenHeight - gp.tileSize * 4;
        int w = gp.screenWidth - gp.tileSize, h = gp.tileSize * 2;
        ui.drawSubWindwow(x, y, w, h);
        g2.setColor(Color.white);
        g2.setFont(ui.MaruMonica.deriveFont(Font.PLAIN, 20f));
        int textX = x + 20, textY = y + 36;
        for (String line : wrapText(combatMessage, w - 40)) {
            g2.drawString(line, textX, textY);
            textY += 26;
        }
    }

    void drawCommandMenu() {
        int x = gp.tileSize / 2, y = gp.screenHeight - gp.tileSize * 4;
        int w = gp.screenWidth - gp.tileSize, h = gp.tileSize * 4 - gp.tileSize / 2;
        ui.drawSubWindwow(x, y, w, h);
        g2.setFont(ui.MaruMonica.deriveFont(Font.PLAIN, 20f));

        // Turno extra da contrattacco: solo Attack/Ability, niente Inventory/Minimap/Flee — non
        // sostituisce il turno normale, quindi il menu resta ristretto finché non si sceglie.
        String[] commands = counterattackPending
                ? new String[]{"Attack", "Ability"}
                : new String[]{"Attack", "Ability", "Inventory", "Minimap", "Flee"};

        if (counterattackPending) {
            g2.setColor(new Color(255, 180, 120));
            g2.drawString("Counterattack! Choose:", x + gp.tileSize, y + 24);
        }

        g2.setColor(Color.white);
        int textX = x + gp.tileSize, textY = y + (counterattackPending ? 56 : 36);
        int lineH = (h - (counterattackPending ? 56 : 20)) / commands.length;
        for (int i = 0; i < commands.length; i++) {
            g2.drawString(commands[i], textX, textY);
            if (commandNum == i) g2.drawString(">", textX - 28, textY);
            textY += lineH;
        }
    }

    void drawAbilityMenu() {
        List<String> abilities = gp.player.unlockedAbilities;
        int x = gp.tileSize / 2, y = gp.screenHeight - gp.tileSize * 4;
        int w = gp.screenWidth - gp.tileSize, h = gp.tileSize * 4 - gp.tileSize / 2;
        ui.drawSubWindwow(x, y, w, h);
        g2.setFont(ui.MaruMonica.deriveFont(Font.PLAIN, 20f));
        g2.setColor(new Color(180, 180, 255));
        g2.drawString("Choose an ability  [ESC = back]", x + gp.tileSize, y + 28);
        g2.setColor(Color.white);
        int textX = x + gp.tileSize, textY = y + 60;
        int lineH = abilities.isEmpty() ? 0 : (h - 60) / Math.max(abilities.size(), 1);
        for (int i = 0; i < abilities.size(); i++) {
            String label = Ability.getName(abilities.get(i))
                    + "  [" + Ability.getElement(abilities.get(i)).name() + "]";
            g2.drawString(label, textX, textY);
            if (abilityCommandNum == i) g2.drawString(">", textX - 28, textY);
            textY += lineH;
        }
    }

    java.util.List<String> wrapText(String text, int maxWidth) {
        java.util.List<String> lines = new java.util.ArrayList<>();
        FontMetrics fm = g2.getFontMetrics();
        String[] words = text.split(" ");
        StringBuilder cur = new StringBuilder();
        for (String word : words) {
            String test = cur.length() == 0 ? word : cur + " " + word;
            if (fm.stringWidth(test) <= maxWidth) { cur = new StringBuilder(test); }
            else { if (cur.length() > 0) lines.add(cur.toString()); cur = new StringBuilder(word); }
        }
        if (cur.length() > 0) lines.add(cur.toString());
        return lines;
    }
}
