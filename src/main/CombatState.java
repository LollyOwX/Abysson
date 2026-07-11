package main;

import entity.Entity;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class CombatState {
    GamePanel gp;
    UI ui;
    Graphics2D g2;

    public Entity monster;
    public int monsterIndex;

    // 0=Attacca 1=Abilità 2=Inventario 3=Minimappa 4=Fuggi
    public int commandNum = 0;
    static final int CMD_ATTACK    = 0;
    static final int CMD_ABILITY   = 1;
    static final int CMD_INVENTORY = 2;
    static final int CMD_MINIMAP   = 3;
    static final int CMD_FLEE      = 4;
    static final int CMD_COUNT     = 5;

    // Sottomenu abilità
    public boolean inAbilityMenu = false;
    public int abilityCommandNum = 0;

    public static final int PLAYER_TURN  = 0;
    public static final int MONSTER_TURN = 1;
    public static final int COMBAT_OVER  = 2;
    public int turnPhase = PLAYER_TURN;

    public String combatMessage = "";
    int messageTimer = 0;

    // Animazione idle mostro
    int monsterSpriteCounter = 0;
    int monsterSpriteNum = 1;

    // Scossa: questo turno il player può attaccare due volte
    boolean sossaExtraAttack = false;

    public CombatState(GamePanel gp, UI ui) {
        this.gp = gp;
        this.ui = ui;
    }

    // ─────────────────────────────────────────────
    //  AVVIO / FINE
    // ─────────────────────────────────────────────

    public void startCombat(Entity monster, int monsterIndex) {
        this.monster      = monster;
        this.monsterIndex = monsterIndex;
        this.commandNum   = 0;
        this.turnPhase    = PLAYER_TURN;
        this.inAbilityMenu = false;
        this.abilityCommandNum = 0;
        this.combatMessage = "Un " + monster.name + " selvatico si avvicina!";
        this.messageTimer  = 90;
        this.monsterSpriteCounter = 0;
        this.monsterSpriteNum = 1;
        this.sossaExtraAttack = false;
    }

    void endCombat() {
        // Pulisci effetti attivi da entrambe le entità
        ElementSystem.removeAllEffects(gp.player);
        if (monster != null) ElementSystem.removeAllEffects(monster);

        gp.gameState = gp.playState;
        monster      = null;
        monsterIndex = -1;
        turnPhase    = PLAYER_TURN;
        inAbilityMenu = false;
        abilityCommandNum = 0;
        combatMessage = "";
        messageTimer  = 0;
        sossaExtraAttack = false;
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
        if (messageTimer > 0) { messageTimer--; return; }
        if (turnPhase == MONSTER_TURN) { monsterTurn(); return; }
        if (turnPhase == COMBAT_OVER)  { endCombat(); }
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
        if (turnPhase != PLAYER_TURN || messageTimer > 0) return;

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
                    combatMessage = "Nessuna abilità disponibile.";
                    messageTimer  = 60;
                } else {
                    inAbilityMenu = true;
                    abilityCommandNum = 0;
                }
                break;
            case CMD_INVENTORY:
                combatMessage = "Inventario vuoto.";
                messageTimer  = 60;
                break;
            case CMD_MINIMAP:
                combatMessage = "(Minimappa - da implementare)";
                messageTimer  = 60;
                break;
            case CMD_FLEE:
                tryFlee();
                break;
        }
    }

    // ─────────────────────────────────────────────
    //  TURNO PLAYER
    // ─────────────────────────────────────────────

    void playerUseAbility(String abilityId) {
        ElementSystem.Element abilityElement = Ability.getElement(abilityId);

        // Abrasione: abilità Fuoco hanno vantaggio (+50% danno)
        double abrBonus = (abilityElement == ElementSystem.Element.FUOCO
                && ElementSystem.hasEffect(monster, ElementSystem.StatusEffect.ABRASIONE)) ? 1.5 : 1.0;

        // Danno base con moltiplicatore elemento e Abrasione
        int baseDmg = Ability.use(abilityId, gp.player, monster);
        double elemMult = ElementSystem.getMultiplier(abilityElement, monster.lastElementHit);

        // Potenziamento: target subisce 200% danni (x3 totale)
        double potMult = ElementSystem.hasEffect(monster, ElementSystem.StatusEffect.POTENZIAMENTO) ? 3.0 : 1.0;

        // Rottura: target subisce +(10*liv)% danni
        double rotMult = ElementSystem.hasEffect(monster, ElementSystem.StatusEffect.ROTTURA)
                ? 1.0 + (0.10 * gp.player.level) : 1.0;

        int totalDmg = (int) Math.max(1, baseDmg * elemMult * abrBonus * potMult * rotMult);

        // Raggio: ogni abilità causa 1d6 danni fulmine al bersaglio
        int raggioDmg = ElementSystem.hasEffect(monster, ElementSystem.StatusEffect.RAGGIO)
                ? ElementSystem.rollD6(1) : 0;

        monster.life -= (totalDmg + raggioDmg);

        // Reazione elemento
        Reaction reaction = ElementSystem.getReaction(monster.lastElementHit, abilityElement, monster.level);
        int reactionDmg   = ElementSystem.applyReaction(reaction, monster, abilityElement);
        monster.life -= reactionDmg;

        // Tick effetti persistenti del mostro a fine turno player
        int tickDmg = ElementSystem.processTurnEffects(monster);
        monster.life -= tickDmg;

        // Costruisci messaggio
        StringBuilder msg = new StringBuilder();
        msg.append("Usi ").append(Ability.getName(abilityId));
        if (elemMult > 1.0) msg.append(" [VANTAGGIO]");
        else if (elemMult < 1.0) msg.append(" [SVANTAGGIO]");
        msg.append(" → ").append(totalDmg).append(" danni");
        if (raggioDmg > 0) msg.append(" + ").append(raggioDmg).append(" (Raggio)");
        if (reactionDmg > 0) msg.append(" + ").append(reactionDmg).append(" (").append(reaction.name).append(")");
        if (tickDmg > 0) msg.append(" + ").append(tickDmg).append(" (effetti)");
        msg.append("  [HP mostro: ").append(Math.max(0, monster.life)).append("/").append(monster.maxLife).append("]");
        if (reaction != Reaction.NONE) msg.append("  ⚡ ").append(reaction.name).append("!");

        combatMessage = msg.toString();
        messageTimer  = 90;

        if (monster.life <= 0) {
            checkVictory();
        } else {
            // Scossa: il player può attaccare una seconda volta
            if (!sossaExtraAttack && ElementSystem.hasEffect(monster, ElementSystem.StatusEffect.SCOSSA)) {
                sossaExtraAttack = true;
                combatMessage += "  [SCOSSA: puoi attaccare ancora!]";
                // Rimaniamo in PLAYER_TURN
            } else {
                sossaExtraAttack = false;
                turnPhase = MONSTER_TURN;
            }
        }
    }

    void tryFlee() {
        combatMessage = "Sei fuggito!";
        messageTimer  = 60;
        turnPhase     = COMBAT_OVER;
    }

    // ─────────────────────────────────────────────
    //  TURNO MOSTRO
    // ─────────────────────────────────────────────

    void monsterTurn() {
        String chosenId = monster.chooseAction();
        ElementSystem.Element abilityElement = Ability.getElement(chosenId);

        int baseDmg  = Ability.use(chosenId, monster, gp.player);
        double elemMult = ElementSystem.getMultiplier(abilityElement, gp.player.lastElementHit);

        double potMult = ElementSystem.hasEffect(gp.player, ElementSystem.StatusEffect.POTENZIAMENTO) ? 3.0 : 1.0;
        double rotMult = ElementSystem.hasEffect(gp.player, ElementSystem.StatusEffect.ROTTURA)
                ? 1.0 + (0.10 * monster.level) : 1.0;

        int totalDmg = (int) Math.max(1, baseDmg * elemMult * potMult * rotMult);

        int raggioDmg = ElementSystem.hasEffect(gp.player, ElementSystem.StatusEffect.RAGGIO)
                ? ElementSystem.rollD6(1) : 0;

        // Calcola probabilità di colpire (precision - evasion del player)
        int hitChance = monster.precision - gp.player.evasion;
        boolean hit   = new Random().nextInt(100) < hitChance;

        gp.player.life -= hit ? (totalDmg + raggioDmg) : 0;

        // Folgore: sul fallimento → 2d8 danni fulmine al mostro stesso
        if (!hit && ElementSystem.hasEffect(monster, ElementSystem.StatusEffect.FOLGORE)) {
            int folgoreDmg = ElementSystem.rollD8(2);
            monster.life -= folgoreDmg;
            combatMessage = monster.name + " manca e subisce " + folgoreDmg + " danni da Folgore!";
        }
        // Infiammazione: sul fallimento → 1d6 danni fuoco
        else if (!hit && ElementSystem.hasEffect(monster, ElementSystem.StatusEffect.INFIAMMAZIONE)) {
            int infDmg = ElementSystem.rollD6(1);
            monster.life -= infDmg;
            combatMessage = monster.name + " manca e subisce " + infDmg + " danni da Infiammazione!";
        } else {
            // Reazione elemento sul player
            Reaction reaction = ElementSystem.getReaction(gp.player.lastElementHit, abilityElement, gp.player.level);
            int reactionDmg   = ElementSystem.applyReaction(reaction, gp.player, abilityElement);
            gp.player.life   -= reactionDmg;

            // Tick effetti persistenti del player
            int tickDmg = ElementSystem.processTurnEffects(gp.player);
            gp.player.life -= tickDmg;

            StringBuilder msg = new StringBuilder();
            msg.append(monster.name).append(" usa ").append(Ability.getName(chosenId));
            if (!hit) { msg.append(" → MANCATO!"); }
            else {
                if (elemMult > 1.0) msg.append(" [VANTAGGIO]");
                else if (elemMult < 1.0) msg.append(" [SVANTAGGIO]");
                msg.append(" → ").append(totalDmg).append(" danni");
                if (raggioDmg > 0) msg.append(" + ").append(raggioDmg).append(" (Raggio)");
                if (reactionDmg > 0) msg.append(" + ").append(reactionDmg).append(" (").append(reaction.name).append(")");
                if (tickDmg > 0) msg.append(" + ").append(tickDmg).append(" (effetti)");
            }
            msg.append("  [Tuoi HP: ").append(Math.max(0, gp.player.life)).append("/").append(gp.player.maxLife).append("]");
            if (reaction != Reaction.NONE) msg.append("  ⚡ ").append(reaction.name).append("!");
            combatMessage = msg.toString();
        }

        messageTimer = 90;

        if (monster.life <= 0)    { checkVictory(); return; }
        if (gp.player.life <= 0) { checkDefeat();  return; }

        turnPhase  = PLAYER_TURN;
        commandNum = 0;
    }

    // ─────────────────────────────────────────────
    //  VITTORIA / SCONFITTA
    // ─────────────────────────────────────────────

    void checkVictory() {
        turnPhase = COMBAT_OVER;
        gp.monster[monsterIndex].dying = true;
        onVictory();
    }

    void onVictory() {
        // stub — in futuro: exp, drop, animazioni
        combatMessage = "Hai sconfitto " + monster.name + "!";
        messageTimer  = 90;
    }

    void checkDefeat() {
        turnPhase = COMBAT_OVER;
        gp.player.life = gp.player.maxLife;
        onDefeat();
    }

    void onDefeat() {
        // stub — in futuro: game over screen, penalità
        combatMessage = "Sei stato sconfitto... Vita ripristinata.";
        messageTimer  = 90;
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
        if (img != null) g2.drawImage(img, monsterX, monsterY, monsterSize, monsterSize, null);

        g2.setFont(ui.MaruMonica.deriveFont(Font.PLAIN, 22f));
        g2.setColor(Color.white);
        String info = monster.name + "  HP: " + Math.max(0, monster.life) + "/" + monster.maxLife
                    + "  Lv." + monster.level;
        // Aggiunge effetti attivi del mostro
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
        String[] commands = {"Attacca", "Abilità", "Inventario", "Minimappa", "Fuggi"};
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
        g2.drawString("Scegli abilità  [ESC = indietro]", x + gp.tileSize, y + 28);
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

    // ─────────────────────────────────────────────
    //  UTILITY
    // ─────────────────────────────────────────────

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
