package combat;

import entity.Entity;
import main.GamePanel;
import main.PaletteSwap;
import main.UI;

import java.awt.*;
import java.util.List;
import java.util.Random;

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

    // ── Ordine dei turni (velocità) ──
    // Deciso a inizio round (non solo a inizio combattimento) confrontando player.speed e
    // monster.speed, così un cambio di velocità a metà scontro si riflette dal round dopo.
    boolean playerGoesFirst = true;  // chi va per primo in QUESTO round
    boolean firstMoverActed = false; // true dopo che chi va per primo ha già agito

    // ── Coda azioni ──
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
        actionQueue.clear();
        inAbilityMenu  = false;
        abilityCommandNum = 0;
        combatMessage  = "";
        messageTimer   = 0;
        scossaExtraAttack = false;
        combatVictory  = false;
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

        // Stordimento: se è il turno del giocatore ma è stordito, salta il turno da solo, senza
        // aspettare un click — stesso trattamento del mostro stordito dentro monsterTurn().
        if (turnPhase == PLAYER_TURN && isStunned(gp.player)) {
            int selfTick = ElementSystem.processTurnEffects(gp.player);
            gp.player.life -= selfTick;
            queueAction("You are stunned and skip your turn!" + (selfTick > 0 ? "  (" + selfTick + " effect damage)" : ""));
            if (gp.player.life <= 0) { checkDefeat(); return; }
            advanceRound(true);
            return;
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
        } else {
            commandNum--;
            if (commandNum < 0) commandNum = CMD_COUNT - 1;
        }
    }

    public void navigateDown() {
        if (inAbilityMenu) {
            abilityCommandNum++;
            if (abilityCommandNum >= gp.player.unlockedAbilities.size()) abilityCommandNum = 0;
        } else {
            commandNum++;
            if (commandNum >= CMD_COUNT) commandNum = 0;
        }
    }

    public void pressEsc() {
        if (inAbilityMenu) { inAbilityMenu = false; abilityCommandNum = 0; }
    }

    public void confirmCommand() {
        if (turnPhase != PLAYER_TURN || messageTimer > 0 || isStunned(gp.player)) return;

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
                playerUseAbility("NormalAttack");
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

    // ─────────────────────────────────────────────
    //  ORDINE DEI TURNI (velocità) — DealDMG e coda azioni
    // ─────────────────────────────────────────────

    // Decide chi va per primo in QUESTO round confrontando la velocità — richiamata a inizio
    // combattimento (startCombat) e a inizio di ogni round successivo (advanceRound), non solo
    // una volta: così un cambio di velocità a metà scontro si riflette dal round dopo.
    void decideTurnOrder() {
        playerGoesFirst = gp.player.speed >= monster.speed; // pareggio -> il giocatore, come prima
        firstMoverActed = false;
        turnPhase = playerGoesFirst ? PLAYER_TURN : MONSTER_TURN;
    }

    // Da richiamare a fine turno di chi ha appena agito. Se era il primo del round, passa al
    // secondo; se era il secondo, il round è finito e se ne decide uno nuovo (ricontrollando la
    // velocità, non semplicemente "tocca sempre al giocatore" come prima).
    void advanceRound(boolean actorWasPlayer) {
        if (!firstMoverActed) {
            firstMoverActed = true;
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

    // Calcola e applica il danno di un'abilità da attacker a target: precisione/schivata,
    // moltiplicatore elementale, reazione, effetti attivi del bersaglio, e il contraccolpo di
    // Folgore/Infiammazione sull'attaccante in caso di fallimento. Accentra la logica prima
    // duplicata (quasi identica) in playerUseAbility()/monsterTurn().
    //
    // Cambio di comportamento dovuto all'unificazione: prima solo gli attacchi del mostro
    // potevano fallire (il giocatore colpiva sempre) — ora precisione/schivata si applicano a
    // entrambi allo stesso modo, quindi anche il giocatore può mancare un colpo (e subire il
    // contraccolpo di Folgore/Infiammazione se le porta).
    void dealDamage(Entity attacker, Entity target, String abilityId) {
        ElementSystem.Element abilityElement = Ability.getElement(abilityId);
        double abrBonus = (abilityElement == ElementSystem.Element.FUOCO
                && ElementSystem.hasEffect(target, ElementSystem.StatusEffect.ABRASIONE)) ? 1.5 : 1.0;
        int baseDmg     = Ability.use(abilityId, attacker, target);
        double elemMult = ElementSystem.getMultiplier(abilityElement, target.lastElementHit);
        double potMult  = ElementSystem.hasEffect(target, ElementSystem.StatusEffect.POTENZIAMENTO) ? 3.0 : 1.0;
        double rotMult  = ElementSystem.hasEffect(target, ElementSystem.StatusEffect.ROTTURA)
                ? 1.0 + (0.10 * attacker.level) : 1.0;
        int totalDmg    = (int) Math.max(1, baseDmg * elemMult * abrBonus * potMult * rotMult);
        int raggioDmg   = ElementSystem.hasEffect(target, ElementSystem.StatusEffect.RAGGIO)
                ? ElementSystem.rollD6(1) : 0;

        int hitChance = attacker.precision - target.evasion;
        boolean hit   = new Random().nextInt(100) < hitChance;

        boolean attackerIsPlayer = (attacker == gp.player);
        String attackerLabel = attackerIsPlayer ? "You" : attacker.name;
        String targetLabel   = (target == gp.player) ? "Your" : target.name;
        String s = attackerIsPlayer ? "" : "s"; // suffisso verbo 3a persona ("use"/"miss" vs "uses"/"misses")

        StringBuilder msg = new StringBuilder();

        if (!hit && ElementSystem.hasEffect(attacker, ElementSystem.StatusEffect.FOLGORE)) {
            int folgoreDmg = ElementSystem.rollD8(2);
            attacker.life -= folgoreDmg;
            msg.append(attackerLabel).append(" miss").append(s).append(" and take").append(s)
                    .append(" ").append(folgoreDmg).append(" Folgore damage!");
        } else if (!hit && ElementSystem.hasEffect(attacker, ElementSystem.StatusEffect.INFIAMMAZIONE)) {
            int infDmg = ElementSystem.rollD6(1);
            attacker.life -= infDmg;
            msg.append(attackerLabel).append(" miss").append(s).append(" and take").append(s)
                    .append(" ").append(infDmg).append(" Infiammazione damage!");
        } else {
            target.life -= hit ? (totalDmg + raggioDmg) : 0;
            Reaction reaction = ElementSystem.getReaction(target.lastElementHit, abilityElement, target.level);
            int reactionDmg   = ElementSystem.applyReaction(reaction, target, abilityElement);
            target.life      -= reactionDmg;
            int tickDmg       = ElementSystem.processTurnEffects(target);
            target.life      -= tickDmg;

            msg.append(attackerLabel).append(" use").append(s).append(" ").append(Ability.getName(abilityId));
            if (!hit) {
                msg.append(" → MISSED!");
            } else {
                if (elemMult > 1.0) msg.append(" [ADVANTAGE]");
                else if (elemMult < 1.0) msg.append(" [DISADVANTAGE]");
                msg.append(" → ").append(totalDmg).append(" damage");
                if (raggioDmg   > 0) msg.append(" + ").append(raggioDmg).append(" (Raggio)");
                if (reactionDmg > 0) msg.append(" + ").append(reactionDmg).append(" (").append(reaction.name).append(")");
                if (tickDmg     > 0) msg.append(" + ").append(tickDmg).append(" (effects)");
                if (reaction != Reaction.NONE) msg.append("  ⚡ ").append(reaction.name).append("!");
            }
            msg.append("  [").append(targetLabel).append(" HP: ")
                    .append(Math.max(0, target.life)).append("/").append(target.maxLife).append("]");

            // Scossa: solo quando è il giocatore a colpire e il bersaglio la porta — comportamento
            // invariato rispetto a prima (il mostro non ne beneficiava nemmeno nel codice originale).
            if (attackerIsPlayer) {
                if (hit && !scossaExtraAttack && ElementSystem.hasEffect(target, ElementSystem.StatusEffect.SCOSSA)) {
                    scossaExtraAttack = true;
                    msg.append("  [SCOSSA: attack again!]");
                } else {
                    scossaExtraAttack = false;
                }
            }
        }

        queueAction(msg.toString());
    }

    // ─────────────────────────────────────────────
    //  PLAYER TURN
    // ─────────────────────────────────────────────

    void playerUseAbility(String abilityId) {
        dealDamage(gp.player, monster, abilityId);
        if (monster.life <= 0) { checkVictory(); return; }
        if (!scossaExtraAttack) advanceRound(true); // scossa: il giocatore riattacca subito, niente avanzamento
    }

    void tryFlee() {
        combatMessage = "You fled!";
        messageTimer  = 60;
        turnPhase     = COMBAT_OVER;
    }

    // ─────────────────────────────────────────────
    //  MONSTER TURN
    // ─────────────────────────────────────────────

    void monsterTurn() {
        if (isStunned(monster)) {
            int selfTick = ElementSystem.processTurnEffects(monster);
            monster.life -= selfTick;
            queueAction(monster.name + " is stunned and skips its turn!" + (selfTick > 0 ? "  (" + selfTick + " effect damage)" : ""));
            if (monster.life <= 0) { checkVictory(); return; }
            advanceRound(false);
            return;
        }

        String chosenId = monster.chooseAction();
        dealDamage(monster, gp.player, chosenId);
        if (monster.life <= 0)   { checkVictory(); return; }
        if (gp.player.life <= 0) { checkDefeat();  return; }
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
            if (inAbilityMenu) drawAbilityMenu();
            else               drawCommandMenu();
        }
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
        String[] commands = {"Attack", "Ability", "Inventory", "Minimap", "Flee"};
        g2.setFont(ui.MaruMonica.deriveFont(Font.PLAIN, 20f));
        g2.setColor(Color.white);
        int textX = x + gp.tileSize, textY = y + 36;
        int lineH = (h - 20) / CMD_COUNT;
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