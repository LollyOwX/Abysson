package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import main.GamePanel;
import main.KeyHandler;
import combat.ElementSystem;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

public class Player extends Entity {
    KeyHandler KeyH;
    public final int screenX;
    public final int screenY;
    public String playerClass;

    // Rampino: sblocca l'uso delle tile SpecialTile.Kind.HOOK (vedi tryHook()) — di default
    // disattivo, pensato per essere sbloccato più avanti (item/skill). Il salto (COLLINETTA/
    // CORNICE) non ha un flag equivalente: è sempre disponibile, tocca solo le meccaniche del
    // rampino questo blocco.
    public boolean hookUnlocked = false;


    // ── Stat base (flat) ────────────────────────────────────────
    // Il valore "vero" del personaggio, prima di qualunque bonus % da equip.
    // stat finale = baseX * (1 + percentBonus[X]/100) — vedi recalculateStats().
    public int baseMaxLife;
    public int baseAttack;
    public int baseDefense;
    public int baseSpeed;
    public int basePrecision;
    public int baseEvasion;
    public int baseEfficiency;
    // Una coppia ATK/DEF base per elemento (FUOCO/ACQUA/FULMINE/TERRA/ARIA/LUCE) —
    // niente ElementoATK unico, vedi StatType.
    private final Map<ElementSystem.Element, Integer> baseElementAttack  = new EnumMap<>(ElementSystem.Element.class);
    private final Map<ElementSystem.Element, Integer> baseElementDefense = new EnumMap<>(ElementSystem.Element.class);

    // Somma dei bonus % correnti per stat, accumulata da calcStat() ad ogni equip/unequip.
    private final Map<StatType, Integer> percentBonus = new EnumMap<>(StatType.class);
    // Stadio 3 (dopo flat e percentuale): moltiplicatore per stat, 1.0 = nessun effetto — non
    // ancora popolato da nessuno, pronto per buff/debuff temporanei futuri (es. una pozione,
    // uno status). Ordine di calcolo: stat flat, poi percentuale, poi questo — vedi recalculateStats().
    private final Map<StatType, Double> statMultiplier = new EnumMap<>(StatType.class);

    // ── Equipaggiamento ──────────────────────────────────────────
    // Ogni slot ha il proprio "statChanged": true = i bonus % dell'item nello slot
    // sono già stati sommati a percentBonus, false = non ancora (o già rimossi).
    public static class EquipSlot {
        public items.Item item = null;
        public boolean statChanged = false;
    }
    public EquipSlot mainHandSlot  = new EquipSlot();
    public EquipSlot offHandSlot   = new EquipSlot();
    public EquipSlot headSlot      = new EquipSlot(); // Corona (Jewelry) o Helmet (Armor)
    public EquipSlot neckSlot      = new EquipSlot(); // Collana (Jewelry) o Gorget (Armor)
    public EquipSlot ring1Slot     = new EquipSlot();
    public EquipSlot ring2Slot     = new EquipSlot();
    public EquipSlot bracelet1Slot = new EquipSlot();
    public EquipSlot bracelet2Slot = new EquipSlot();
    // I 10 pezzi d'armatura (Helmet/Gorget inclusi — slot propri, non condivisi con i Gioielli)
    public EquipSlot helmetSlot    = new EquipSlot();
    public EquipSlot gorgetSlot    = new EquipSlot();
    public EquipSlot pauldronSlot  = new EquipSlot();
    public EquipSlot rerebraceSlot = new EquipSlot();
    public EquipSlot couterSlot    = new EquipSlot();
    public EquipSlot vanbraceSlot  = new EquipSlot();
    public EquipSlot gauntletSlot  = new EquipSlot();
    public EquipSlot cuirasseSlot  = new EquipSlot();
    public EquipSlot cuisseSlot    = new EquipSlot();
    public EquipSlot poleynSlot    = new EquipSlot();
    public EquipSlot greaveSlot    = new EquipSlot();
    public EquipSlot sabatonSlot   = new EquipSlot();

    public Player(GamePanel gp, KeyHandler KeyH) {
        super(gp);
        this.gp = gp;
        this.KeyH = KeyH;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        solidArea = new Rectangle();
        solidArea.x = 14;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30;
        solidArea.height = 28;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
        idleDirection = "down";

        // Valori base (flat) del personaggio. Prima venivano scritti direttamente nelle stat
        // derivate (attack/defense/...), scavalcando baseAttack/baseDefense — inconsistenza
        // pre-esistente: ora le basi sono la fonte di verità e le derivate si calcolano da qui.
        baseMaxLife        = 1000;
        baseAttack         = 40;
        baseDefense        = 20;
        baseSpeed          = speed;
        basePrecision      = 100;
        baseEvasion        = 0;
        baseEfficiency     = 100;
        // Stesso valore uniforme su tutti gli elementi per ora (40/20, come il vecchio
        // ElementoATK/ElementDEF unico) — la differenziazione per elemento arriverà da
        // equipaggiamento/level design, qui parte identica finché non si decide altrimenti.
        for (ElementSystem.Element e : ElementSystem.Element.values()) {
            if (ElementSystem.attackStat(e) == null) continue; // FISICO/NONE non hanno una coppia elementale
            baseElementAttack.put(e, 40);
            baseElementDefense.put(e, 20);
        }

        recalculateStats(); // nessun equip ancora: le derivate = le basi (bonus % tutti a 0)
        life = maxLife;

        unlockedAbilities.add("PowerStrike");
        unlockedAbilities.add("AcquaJet");
        unlockedAbilities.add("Thunderbolt");
        unlockedAbilities.add("Earthshock");
        unlockedAbilities.add("Fireblade");

        behavior = FRIENDLY; // il player è sempre "friendly" come entità
    }

    public void getPlayerImage() {
        if(playerClass != null) {
            up1 = setup("/player/" + playerClass + "_up_1", 19 * 3, 19 * 3);
            up2 = setup("/player/" + playerClass + "_up_2", 19 * 3, 19 * 3);
            down1 = setup("/player/" + playerClass + "_down_1", 19 * 3, 19 * 3);
            down2 = setup("/player/" + playerClass + "_down_2", 19 * 3, 19 * 3);
            left1 = setup("/player/" + playerClass + "_left_1", 19 * 3, 19 * 3);
            left2 = setup("/player/" + playerClass + "_left_2", 19 * 3, 19 * 3);
            right1 = setup("/player/" + playerClass + "_right_1", 19 * 3, 19 * 3);
            right2 = setup("/player/" + playerClass + "_right_2", 19 * 3, 19 * 3);
            downIdle1 = setup("/player/" + playerClass + "_downidle_1", 19 * 3, 19 * 3);
            downIdle2 = setup("/player/" + playerClass + "_downidle_2", 19 * 3, 19 * 3);
            upIdle1 = setup("/player/" + playerClass + "_upidle_1", 19 * 3, 19 * 3);
            upIdle2 = setup("/player/" + playerClass + "_upidle_2", 19 * 3, 19 * 3);
            leftIdle1 = setup("/player/" + playerClass + "_leftidle_1", 19 * 3, 19 * 3);
            leftIdle2 = setup("/player/" + playerClass + "_leftidle_2", 19 * 3, 19 * 3);
            rightIdle1 = setup("/player/" + playerClass + "_rightidle_1", 19 * 3, 19 * 3);
            rightIdle2 = setup("/player/" + playerClass + "_rightidle_2", 19 * 3, 19 * 3);
            rightUp1 = setup("/player/" + playerClass + "_rightup_1", 19 * 3, 19 * 3);
            rightUp2 = setup("/player/" + playerClass + "_rightup_2", 19 * 3, 19 * 3);
            rightDown1 = setup("/player/" + playerClass + "_rightdown_1", 19 * 3, 19 * 3);
            rightDown2 = setup("/player/" + playerClass + "_rightdown_2", 19 * 3, 19 * 3);
            leftUp1 = setup("/player/" + playerClass + "_leftup_1", 19 * 3, 19 * 3);
            leftUp2 = setup("/player/" + playerClass + "_leftup_2", 19 * 3, 19 * 3);
            leftDown1 = setup("/player/" + playerClass + "_leftdown_1", 19 * 3, 19 * 3);
            leftDown2 = setup("/player/" + playerClass + "_leftdown_2", 19 * 3, 19 * 3);
        }
    }

    public void update() {
        // Blocca il movimento se il neutral menu è aperto
        if (gp.ui.neutralMenuOpen) return;

        // Movimento scriptato in corso (salto/rampino): input normale disattivo finché non finisce.
        if (scripting) { updateScriptedMove(); return; }

        if (KeyH.upPressed || KeyH.downPressed || KeyH.leftPressed || KeyH.rightPressed || KeyH.enterPressed) {
            double dx = 0;
            double dy = 0;
            if (KeyH.upPressed)    { dy -= 1; direction = "up";    idleDirection = "idle_up";    }
            if (KeyH.downPressed)  { dy += 1; direction = "down";  idleDirection = "idle_down";  }
            if (KeyH.leftPressed)  { dx -= 1; direction = "left";  idleDirection = "idle_left";  }
            if (KeyH.rightPressed) { dx += 1; direction = "right"; idleDirection = "idle_right"; }

            if (dx != 0 || dy != 0) {
                // Cornice: se davanti c'è una tile attraversabile a senso unico dal verso in cui
                // sto camminando, non è un movimento normale — scatta la discesa scriptata e
                // salta tutta la logica di collisione sotto per questo frame.
                if (!tryCornice()) {
                    double len = Math.sqrt(dx * dx + dy * dy);
                    dx /= len; dy /= len;
                    int moveX = (int) Math.round(dx * speed);
                    int moveY = (int) Math.round(dy * speed);

                    collisionOn = false;
                    worldX += moveX;
                    gp.cChecker.checkTile(this);
                    gp.cChecker.checkObject(this, true);
                    gp.cChecker.checkEntity(this, gp.npc);
                    gp.cChecker.checkEntity(this, gp.monster);
                    if (collisionOn) worldX -= moveX;

                    collisionOn = false;
                    worldY += moveY;
                    gp.cChecker.checkTile(this);
                    gp.cChecker.checkObject(this, true);
                    gp.cChecker.checkEntity(this, gp.npc);
                    gp.cChecker.checkEntity(this, gp.monster);
                    if (collisionOn) worldY -= moveY;
                }
            }

            gp.eHandler.checkEvent();
            spriteCounter++;
            if (spriteCounter > 13) { spriteNum = spriteNum == 1 ? 2 : 1; spriteCounter = 0; }
        } else {
            direction = idleDirection;
            idleSpriteCounter++;
            if (idleSpriteCounter > 32) { spriteNum = spriteNum == 1 ? 2 : 1; idleSpriteCounter = 0; }
        }
        int objIndex     = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);
        int npcIndex     = gp.cChecker.checkEntityInteraction(this, gp.npc, gp.tileSize / 2);
        interactNPC(npcIndex);
        int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
        contactMonster(monsterIndex);
        gp.KeyH.enterPressed = false;

        // Salto (sempre disponibile) e rampino (solo se hookUnlocked) — mutuamente esclusivi per
        // tipo di tile davanti, sicuro chiamarli entrambi in sequenza sullo stesso tasto.
        if (KeyH.spacePressed) {
            if (!tryJump()) tryHook();
            KeyH.spacePressed = false;
        }
    }

    // ─────────────────────────────────────────────
    //  MOVIMENTO SCRIPTATO (salto / rampino)
    // ─────────────────────────────────────────────
    // Durante uno scripted move la posizione si interpola linearmente da inizio a fine in
    // 'scriptDuration' frame; l'input normale resta disattivo (vedi in cima a update()).

    private boolean scripting = false;
    private int scriptStartX, scriptStartY, scriptEndX, scriptEndY, scriptDuration, scriptElapsed;
    private Runnable scriptOnComplete;

    private void startScriptedMove(int endX, int endY, int durationFrames, Runnable onComplete) {
        scriptStartX    = worldX;
        scriptStartY    = worldY;
        scriptEndX      = endX;
        scriptEndY      = endY;
        scriptDuration  = Math.max(1, durationFrames);
        scriptElapsed   = 0;
        scriptOnComplete = onComplete;
        scripting       = true;
    }

    private void updateScriptedMove() {
        scriptElapsed++;
        double t = Math.min(1.0, scriptElapsed / (double) scriptDuration);
        worldX = (int) (scriptStartX + (scriptEndX - scriptStartX) * t);
        worldY = (int) (scriptStartY + (scriptEndY - scriptStartY) * t);
        if (t >= 1.0) {
            scripting = false;
            Runnable callback = scriptOnComplete;
            scriptOnComplete = null;
            if (callback != null) callback.run();
        }
    }

    /**
     * Cornice (SpecialTile.Kind.CORNICE): se la tile 1 avanti nella direzione in cui sto
     * camminando è attraversabile a senso unico DA QUESTO verso, avvia la discesa scriptata (2
     * tile) e ritorna true — il chiamante deve saltare il movimento/collisione normale per
     * questo frame. Dall'altro lato (passableFrom non combacia) ritorna false: resta un muro
     * normale, gestito dalla collisione esistente. Sempre disponibile, nessun unlock.
     */
    private boolean tryCornice() {
        if (scripting) return false;
        int[] ahead = gp.cChecker.tileAhead(this, 1);
        tile.SpecialTile special = gp.cChecker.specialTileAt(ahead[0], ahead[1]);
        if (special == null || special.kind != tile.SpecialTile.Kind.CORNICE) return false;

        tile.SpecialTile.Direction needed = switch (direction) {
            case "up"    -> tile.SpecialTile.Direction.UP;
            case "down"  -> tile.SpecialTile.Direction.DOWN;
            case "left"  -> tile.SpecialTile.Direction.LEFT;
            case "right" -> tile.SpecialTile.Direction.RIGHT;
            default      -> null;
        };
        if (special.passableFrom != needed) return false;

        int dxTiles = 0, dyTiles = 0;
        switch (direction) {
            case "up":    dyTiles = -2; break;
            case "down":  dyTiles = 2;  break;
            case "left":  dxTiles = -2; break;
            case "right": dxTiles = 2;  break;
        }
        startScriptedMove(worldX + dxTiles * gp.tileSize, worldY + dyTiles * gp.tileSize, 12, null);
        return true;
    }

    /**
     * Salto (SpecialTile.Kind.COLLINETTA): SEMPLIFICAZIONE rispetto a "rettangolo di collisione
     * più piccolo" — la tile blocca per intero come qualunque altra, il salto la scavalca tutta
     * atterrando sulla tile subito successiva (2 tile di spostamento, come la cornice). Sempre
     * disponibile, nessun unlock. Ritorna true se il salto è partito.
     */
    private boolean tryJump() {
        if (scripting) return false;
        int[] obstacle = gp.cChecker.tileAhead(this, 1);
        tile.SpecialTile special = gp.cChecker.specialTileAt(obstacle[0], obstacle[1]);
        if (special == null || special.kind != tile.SpecialTile.Kind.COLLINETTA) return false;

        int[] landing = gp.cChecker.tileAhead(this, 2);
        if (gp.cChecker.isTileCollisionAt(landing[0], landing[1])) return false; // atterraggio bloccato

        int dxTiles = 0, dyTiles = 0;
        switch (direction) {
            case "up":    dyTiles = -2; break;
            case "down":  dyTiles = 2;  break;
            case "left":  dxTiles = -2; break;
            case "right": dxTiles = 2;  break;
        }
        startScriptedMove(worldX + dxTiles * gp.tileSize, worldY + dyTiles * gp.tileSize, 12, null);
        return true;
    }

    /**
     * Rampino (SpecialTile.Kind.HOOK): richiede hookUnlocked. La destinazione viene da
     * TileLinkRegistry per (mondo corrente, colonna, riga) della tile davanti — nessun link
     * registrato = nessun effetto (l'appiglio è "muto", non succede nulla). Stesso mondo =
     * riposizionamento immediato (scenograficamente un'arrampicata su muro); mondo diverso =
     * stessa animazione ma a fine corsa cambia la mappa sotto (vedi GamePanel.loadWorld() e la
     * nota lì sopra: npc/oggetti non sono ancora per-mondo).
     */
    private boolean tryHook() {
        if (scripting || !hookUnlocked) return false;
        int[] ahead = gp.cChecker.tileAhead(this, 1);
        tile.SpecialTile special = gp.cChecker.specialTileAt(ahead[0], ahead[1]);
        if (special == null || special.kind != tile.SpecialTile.Kind.HOOK) return false;

        tile.TileLink link = tile.TileLinkRegistry.get(gp.currentWorld, ahead[0], ahead[1]);
        if (link == null) return false; // appiglio senza destinazione registrata: nessun effetto

        if (link.toWorld.equals(gp.currentWorld)) {
            startScriptedMove(link.toCol * gp.tileSize, link.toRow * gp.tileSize, 20, null);
        } else {
            // Le due mappe non condividono lo stesso spazio di coordinate: non si può animare
            // uno scorrimento continuo tra l'una e l'altra. L'animazione "resta ferma" sul posto
            // e il cambio mondo scatta di netto a fine corsa (vedi GamePanel.loadWorld()).
            startScriptedMove(worldX, worldY, 20, () -> gp.loadWorld(link.toWorld, link.toCol, link.toRow));
        }
        return true;
    }

    // ─────────────────────────────────────────────
    //  EQUIP / STAT
    // ─────────────────────────────────────────────

    /**
     * Somma o sottrae il bonus percentuale di UNA statistica al totale accumulato.
     * statChanged è lo stato dello SLOT (non della singola stat): false = il bonus
     * va ancora sommato (equip), true = va tolto (unequip) — per questo lo slot lo
     * inverte una volta sola dopo aver richiamato calcStat per ogni sua statistica,
     * non ad ogni chiamata (altrimenti un item con più stat alternerebbe somma e
     * sottrazione invece di applicarle tutte insieme).
     */
    public static int calcStat(int stat, int bonus, boolean statChanged) {
        return statChanged ? stat - bonus : stat + bonus;
    }

    private void applySlotBonus(EquipSlot slot) {
        if (slot.item == null) return;
        for (Map.Entry<StatType, Integer> e : slot.item.statBonusPercent.entrySet()) {
            int current = percentBonus.getOrDefault(e.getKey(), 0);
            percentBonus.put(e.getKey(), calcStat(current, e.getValue(), slot.statChanged));
        }
        slot.statChanged = !slot.statChanged; // un solo toggle per l'intero slot
    }

    private EquipSlot slotFor(items.Item.ItemSlot type) {
        switch (type) {
            case MainHand:   return mainHandSlot;
            case OffHand:    return offHandSlot;
            case Head:       return headSlot;
            case Neck:       return neckSlot;
            case Ring1:      return ring1Slot;
            case Ring2:      return ring2Slot;
            case Bracelet1:  return bracelet1Slot;
            case Bracelet2:  return bracelet2Slot;
            case Helmet:     return helmetSlot;
            case Gorget:     return gorgetSlot;
            case Pauldron:   return pauldronSlot;
            case Rerebrace:  return rerebraceSlot;
            case Couter:     return couterSlot;
            case Vanbrace:   return vanbraceSlot;
            case Gauntlet:   return gauntletSlot;
            case Cuirasse:   return cuirasseSlot;
            case Cuisse:     return cuisseSlot;
            case Poleyn:     return poleynSlot;
            case Greave:     return greaveSlot;
            case Sabaton:    return sabatonSlot;
            default:         return mainHandSlot;
        }
    }

    public void equip(items.Item item) {
        EquipSlot slot = slotFor(item.slot);
        if (slot.item != null) applySlotBonus(slot); // toglie prima i bonus del pezzo precedente
        item.computeBonusPercent(); // "add components": ricalcola i bonus dai campi grezzi + componenti innestati
        slot.item = item;
        applySlotBonus(slot); // poi somma quelli del nuovo pezzo
        recalculateStats();
    }

    public void unequip(items.Item.ItemSlot slotType) {
        EquipSlot slot = slotFor(slotType);
        if (slot.item != null) {
            applySlotBonus(slot);
            slot.item = null;
        }
        recalculateStats();
    }

    public void recalculateStats() {
        maxLife        = Math.max(1, (int) Math.round(baseMaxLife        * pct(StatType.VITA)       * mult(StatType.VITA)));
        attack         = Math.max(0, (int) Math.round(baseAttack         * pct(StatType.ATTACK)     * mult(StatType.ATTACK)));
        defense        = Math.max(0, (int) Math.round(baseDefense        * pct(StatType.DIFESA)     * mult(StatType.DIFESA)));
        speed          = Math.max(1, (int) Math.round(baseSpeed          * pct(StatType.VELOCITA)   * mult(StatType.VELOCITA)));
        evasion        = Math.max(0, (int) Math.round(baseEvasion        * pct(StatType.ELUSIONE)   * mult(StatType.ELUSIONE)));
        precision      = Math.max(0, (int) Math.round(basePrecision      * pct(StatType.PRECISIONE) * mult(StatType.PRECISIONE)));
        efficiency     = Math.max(0, (int) Math.round(baseEfficiency     * pct(StatType.EFFICIENZA) * mult(StatType.EFFICIENZA)));

        // Una coppia ATK/DEF per elemento, ciascuna con il proprio bonus % (StatType.FIRE_ATK...)
        for (ElementSystem.Element e : ElementSystem.Element.values()) {
            StatType atkType = ElementSystem.attackStat(e);
            StatType defType = ElementSystem.defenseStat(e);
            if (atkType == null) continue; // FISICO/NONE non hanno una coppia elementale
            int baseAtk = baseElementAttack.getOrDefault(e, 0);
            int baseDef = baseElementDefense.getOrDefault(e, 0);
            elementAttack.put(e, Math.max(0, (int) Math.round(baseAtk * pct(atkType) * mult(atkType))));
            elementDefense.put(e, Math.max(0, (int) Math.round(baseDef * pct(defType) * mult(defType))));
        }
    }

    // stat = flat * % * moltiplicatore, in quest'ordine — percentBonus è espresso in "punti
    // percentuali" (3 = +3%), statMultiplier è un fattore diretto (1.0 = nessun effetto).
    private double pct(StatType type) {
        return 1.0 + percentBonus.getOrDefault(type, 0) / 100.0;
    }

    private double mult(StatType type) {
        return statMultiplier.getOrDefault(type, 1.0);
    }

    public void interactNPC(int i) {
        if (i == 999) return;
        Entity target = gp.npc[i];
        if (!gp.KeyH.enterPressed) return;

        switch (target.behavior) {
            case Entity.FRIENDLY:
                gp.gameState = gp.dialogueState;
                target.speak();
                break;
            case Entity.NEUTRAL:
                gp.ui.openNeutralMenu(target, i, true);
                break;
            case Entity.HOSTILE:
                gp.gameState = gp.combatState;
                gp.ui.combat.startCombat(target, i);
                break;
        }
    }

    public void contactMonster(int i) {
        if (i == 999) return;
        Entity target = gp.monster[i];

        switch (target.behavior) {
            case Entity.FRIENDLY:
                if (gp.KeyH.enterPressed) {
                    gp.gameState = gp.dialogueState;
                    target.speak();
                }
                break;
            case Entity.NEUTRAL:
                if (gp.KeyH.enterPressed) {
                    gp.ui.openNeutralMenu(target, i, false);
                }
                break;
            case Entity.HOSTILE:
                gp.gameState = gp.combatState;
                gp.ui.combat.startCombat(target, i);
                break;
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) { }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        boolean up    = KeyH.upPressed;
        boolean down  = KeyH.downPressed;
        boolean left  = KeyH.leftPressed;
        boolean right = KeyH.rightPressed;

        if      (up && right) { image = spriteNum == 1 ? rightUp1   : rightUp2;   }
        else if (up && left)  { image = spriteNum == 1 ? leftUp1    : leftUp2;    }
        else if (down && right){ image = spriteNum == 1 ? rightDown1 : rightDown2; }
        else if (down && left) { image = spriteNum == 1 ? leftDown1  : leftDown2;  }
        else {
            String dir = direction != null ? direction : (idleDirection != null ? idleDirection : "down");
            image = switch (dir) {
                case "up"         -> spriteNum == 1 ? up1        : up2;
                case "down"       -> spriteNum == 1 ? down1      : down2;
                case "left"       -> spriteNum == 1 ? left1      : left2;
                case "right"      -> spriteNum == 1 ? right1     : right2;
                case "idle_up"    -> spriteNum == 1 ? upIdle1    : upIdle2;
                case "idle_down"  -> spriteNum == 1 ? downIdle1  : downIdle2;
                case "idle_left"  -> spriteNum == 1 ? leftIdle1  : leftIdle2;
                case "idle_right" -> spriteNum == 1 ? rightIdle1 : rightIdle2;
                default           -> spriteNum == 1 ? downIdle1  : downIdle2;
            };
        }
        g2.drawImage(image, screenX, screenY, null);
    }
}
