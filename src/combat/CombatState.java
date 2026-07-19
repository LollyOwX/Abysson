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
        this.turnPhase         = PLAYER_TURN;
        this.inAbilityMenu     = false;
        this.abilityCommandNum = 0;
        this.combatMessage     = "A wild " + monster.name + " approaches!";
        this.messageTimer      = 90;
        this.monsterSpriteCounter = 0;
        this.monsterSpriteNum  = 1;
        this.scossaExtraAttack = false;
    }

    void endCombat() {
        ElementSystem.removeAllEffects(gp.player);
        // Do NOT remove the monster here — GamePanel removes it once the dying animation finishes

        gp.gameState   = gp.playState;
        monster        = null;
        monsterIndex   = -1;
        turnPhase      = PLAYER_TURN;
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

        if (messageTimer > 0) { messageTimer--; return; }
        if (turnPhase == MONSTER_TURN) { monsterTurn(); return; }

        if (turnPhase == COMBAT_OVER) {
            // Message is done - save monsterIndex before endCombat clears it
            int savedMonsterIndex = monsterIndex;
            boolean wasVictory = combatVictory;
            // End combat (return to playState, dark overlay removed)
            endCombat();
            // Only set dying animation if player won
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
                    combatMessage = "No abilities available.";
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
    //  PLAYER TURN
    // ─────────────────────────────────────────────

    void playerUseAbility(String abilityId) {
        ElementSystem.Element abilityElement = Ability.getElement(abilityId);
        double abrBonus = (abilityElement == ElementSystem.Element.FUOCO
                && ElementSystem.hasEffect(monster, ElementSystem.StatusEffect.ABRASIONE)) ? 1.5 : 1.0;
        int baseDmg    = Ability.use(abilityId, gp.player, monster);
        double elemMult = ElementSystem.getMultiplier(abilityElement, monster.lastElementHit);
        double potMult = ElementSystem.hasEffect(monster, ElementSystem.StatusEffect.POTENZIAMENTO) ? 3.0 : 1.0;
        double rotMult = ElementSystem.hasEffect(monster, ElementSystem.StatusEffect.ROTTURA)
                ? 1.0 + (0.10 * gp.player.level) : 1.0;
        int totalDmg   = (int) Math.max(1, baseDmg * elemMult * abrBonus * potMult * rotMult);
        int raggioDmg  = ElementSystem.hasEffect(monster, ElementSystem.StatusEffect.RAGGIO)
                ? ElementSystem.rollD6(1) : 0;
        monster.life  -= (totalDmg + raggioDmg);
        Reaction reaction   = ElementSystem.getReaction(monster.lastElementHit, abilityElement, monster.level);
        int reactionDmg     = ElementSystem.applyReaction(reaction, monster, abilityElement);
        monster.life       -= reactionDmg;
        int tickDmg         = ElementSystem.processTurnEffects(monster);
        monster.life       -= tickDmg;

        StringBuilder msg = new StringBuilder();
        msg.append("You use ").append(Ability.getName(abilityId));
        if (elemMult > 1.0) msg.append(" [ADVANTAGE]");
        else if (elemMult < 1.0) msg.append(" [DISADVANTAGE]");
        msg.append(" → ").append(totalDmg).append(" damage");
        if (raggioDmg  > 0) msg.append(" + ").append(raggioDmg).append(" (Raggio)");
        if (reactionDmg > 0) msg.append(" + ").append(reactionDmg).append(" (").append(reaction.name).append(")");
        if (tickDmg    > 0) msg.append(" + ").append(tickDmg).append(" (effects)");
        msg.append("  [Monster HP: ").append(Math.max(0, monster.life)).append("/").append(monster.maxLife).append("]");
        if (reaction != Reaction.NONE) msg.append("  ⚡ ").append(reaction.name).append("!");
        combatMessage = msg.toString();
        messageTimer  = 90;

        if (monster.life <= 0) {
            checkVictory();
        } else {
            if (!scossaExtraAttack && ElementSystem.hasEffect(monster, ElementSystem.StatusEffect.SCOSSA)) {
                scossaExtraAttack = true;
                combatMessage += "  [SCOSSA: you can attack again!]";
            } else {
                scossaExtraAttack = false;
                turnPhase = MONSTER_TURN;
            }
        }
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
        String chosenId = monster.chooseAction();
        ElementSystem.Element abilityElement = Ability.getElement(chosenId);
        int baseDmg     = Ability.use(chosenId, monster, gp.player);
        double elemMult = ElementSystem.getMultiplier(abilityElement, gp.player.lastElementHit);
        double potMult  = ElementSystem.hasEffect(gp.player, ElementSystem.StatusEffect.POTENZIAMENTO) ? 3.0 : 1.0;
        double rotMult  = ElementSystem.hasEffect(gp.player, ElementSystem.StatusEffect.ROTTURA)
                ? 1.0 + (0.10 * monster.level) : 1.0;
        int totalDmg    = (int) Math.max(1, baseDmg * elemMult * potMult * rotMult);
        int raggioDmg   = ElementSystem.hasEffect(gp.player, ElementSystem.StatusEffect.RAGGIO)
                ? ElementSystem.rollD6(1) : 0;
        int hitChance   = monster.precision - gp.player.evasion;
        boolean hit     = new Random().nextInt(100) < hitChance;

        if (!hit && ElementSystem.hasEffect(monster, ElementSystem.StatusEffect.FOLGORE)) {
            int folgoreDmg = ElementSystem.rollD8(2);
            monster.life  -= folgoreDmg;
            combatMessage  = monster.name + " misses and takes " + folgoreDmg + " Folgore damage!";
        } else if (!hit && ElementSystem.hasEffect(monster, ElementSystem.StatusEffect.INFIAMMAZIONE)) {
            int infDmg    = ElementSystem.rollD6(1);
            monster.life -= infDmg;
            combatMessage = monster.name + " misses and takes " + infDmg + " Infiammazione damage!";
        } else {
            gp.player.life -= hit ? (totalDmg + raggioDmg) : 0;
            Reaction reaction   = ElementSystem.getReaction(gp.player.lastElementHit, abilityElement, gp.player.level);
            int reactionDmg     = ElementSystem.applyReaction(reaction, gp.player, abilityElement);
            gp.player.life     -= reactionDmg;
            int tickDmg         = ElementSystem.processTurnEffects(gp.player);
            gp.player.life     -= tickDmg;

            StringBuilder msg = new StringBuilder();
            msg.append(monster.name).append(" uses ").append(Ability.getName(chosenId));
            if (!hit) { msg.append(" → MISSED!"); }
            else {
                if (elemMult > 1.0) msg.append(" [ADVANTAGE]");
                else if (elemMult < 1.0) msg.append(" [DISADVANTAGE]");
                msg.append(" → ").append(totalDmg).append(" damage");
                if (raggioDmg  > 0) msg.append(" + ").append(raggioDmg).append(" (Raggio)");
                if (reactionDmg > 0) msg.append(" + ").append(reactionDmg).append(" (").append(reaction.name).append(")");
                if (tickDmg    > 0) msg.append(" + ").append(tickDmg).append(" (effects)");
            }
            msg.append("  [Your HP: ").append(Math.max(0, gp.player.life)).append("/").append(gp.player.maxLife).append("]");
            if (reaction != Reaction.NONE) msg.append("  ⚡ ").append(reaction.name).append("!");
            combatMessage = msg.toString();
        }

        messageTimer = 90;
        if (monster.life <= 0)   { checkVictory(); return; }
        if (gp.player.life <= 0) { checkDefeat();  return; }
        turnPhase  = PLAYER_TURN;
        commandNum = 0;
    }

    // ─────────────────────────────────────────────
    //  VICTORY / DEFEAT
    // ─────────────────────────────────────────────

    void checkVictory() {
        // Show victory message in COMBAT_OVER state
        combatVictory = true;
        onVictory();
        // Set turnPhase to COMBAT_OVER so update() waits for messageTimer
        // The dying animation will start AFTER returning to playState
        turnPhase = COMBAT_OVER;
    }

    void onVictory() {
        // stub — future: exp, drops, animations
        combatMessage = "You defeated " + monster.name + "!";
        messageTimer  = 90;
    }

    void checkDefeat() {
        onDefeat();
        turnPhase = COMBAT_OVER;
    }

    void onDefeat() {
        // stub — future: game over screen, penalties
        combatMessage = "You have been defeated...";
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