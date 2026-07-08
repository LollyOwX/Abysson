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

    // Menu principale
    // 0=Attacca, 1=Abilità, 2=Inventario, 3=Minimappa, 4=Fuggi
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

    public static final int PLAYER_TURN = 0;
    public static final int MONSTER_TURN = 1;
    public static final int COMBAT_OVER = 2;
    public int turnPhase = PLAYER_TURN;

    public String combatMessage = "";
    int messageTimer = 0;

    // Animazione idle mostro
    int monsterSpriteCounter = 0;
    int monsterSpriteNum = 1;

    public CombatState(GamePanel gp, UI ui) {
        this.gp = gp;
        this.ui = ui;
    }

    // ─────────────────────────────────────────────
    //  AVVIO / FINE
    // ─────────────────────────────────────────────

    public void startCombat(Entity monster, int monsterIndex) {
        this.monster = monster;
        this.monsterIndex = monsterIndex;
        this.commandNum = 0;
        this.turnPhase = PLAYER_TURN;
        this.inAbilityMenu = false;
        this.abilityCommandNum = 0;
        this.combatMessage = "Un " + monster.name + " selvatico si avvicina!";
        this.messageTimer = 90;
        this.monsterSpriteCounter = 0;
        this.monsterSpriteNum = 1;
    }

    void endCombat() {
        gp.gameState = gp.playState;
        monster = null;
        monsterIndex = -1;
        turnPhase = PLAYER_TURN;
        inAbilityMenu = false;
        abilityCommandNum = 0;
        combatMessage = "";
        messageTimer = 0;
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

        if (messageTimer > 0) {
            messageTimer--;
            return;
        }

        if (turnPhase == MONSTER_TURN) {
            monsterTurn();
            return;
        }

        if (turnPhase == COMBAT_OVER) {
            endCombat();
        }
    }

    // ─────────────────────────────────────────────
    //  INPUT — chiamato da KeyHandler
    // ─────────────────────────────────────────────

    public void confirmCommand() {
        if (turnPhase != PLAYER_TURN || messageTimer > 0) return;

        if (inAbilityMenu) {
            // Conferma abilità dal sottomenu
            List<String> abilities = gp.player.unlockedAbilities;
            if (!abilities.isEmpty()) {
                String chosen = abilities.get(abilityCommandNum);
                playerUseAbility(chosen);
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
                    messageTimer = 60;
                } else {
                    inAbilityMenu = true;
                    abilityCommandNum = 0;
                }
                break;
            case CMD_INVENTORY:
                combatMessage = "Inventario vuoto.";
                messageTimer = 60;
                break;
            case CMD_MINIMAP:
                // da sviluppare
                combatMessage = "(Minimappa - da implementare)";
                messageTimer = 60;
                break;
            case CMD_FLEE:
                tryFlee();
                break;
        }
    }

    public void navigateUp() {
        if (inAbilityMenu) {
            abilityCommandNum--;
            if (abilityCommandNum < 0)
                abilityCommandNum = gp.player.unlockedAbilities.size() - 1;
        } else {
            commandNum--;
            if (commandNum < 0) commandNum = CMD_COUNT - 1;
        }
    }

    public void navigateDown() {
        if (inAbilityMenu) {
            abilityCommandNum++;
            if (abilityCommandNum >= gp.player.unlockedAbilities.size())
                abilityCommandNum = 0;
        } else {
            commandNum++;
            if (commandNum >= CMD_COUNT) commandNum = 0;
        }
    }

    public void pressEsc() {
        if (inAbilityMenu) {
            inAbilityMenu = false;
            abilityCommandNum = 0;
        }
        // fuori dal sottomenu ESC non fa nulla in combattimento
    }

    // ─────────────────────────────────────────────
    //  TURNO PLAYER
    // ─────────────────────────────────────────────

    void playerUseAbility(String abilityId) {
        int dmg = Ability.use(abilityId, gp.player, monster);
        monster.life -= dmg;
        combatMessage = "Usi " + Ability.getName(abilityId) + " su " + monster.name
                + " → " + dmg + " danni! "
                + "(HP mostro: " + Math.max(0, monster.life) + "/" + monster.maxLife + ")";
        messageTimer = 90;

        if (monster.life <= 0) {
            checkVictory();
        } else {
            turnPhase = MONSTER_TURN;
        }
    }

    void tryFlee() {
        combatMessage = "Sei fuggito!";
        messageTimer = 60;
        turnPhase = COMBAT_OVER;
    }

    // ─────────────────────────────────────────────
    //  TURNO MOSTRO
    // ─────────────────────────────────────────────

    void monsterTurn() {
        String chosenId = monster.chooseAction();
        int dmg = Ability.use(chosenId, monster, gp.player);
        gp.player.life -= dmg;
        combatMessage = monster.name + " usa " + Ability.getName(chosenId) + "!"
                + " Ti colpisce per " + dmg + " danni. "
                + "(Tuoi HP: " + Math.max(0, gp.player.life) + "/" + gp.player.maxLife + ")";
        messageTimer = 90;

        if (gp.player.life <= 0) {
            checkDefeat();
        } else {
            turnPhase = PLAYER_TURN;
            commandNum = 0;
        }
    }

    // ─────────────────────────────────────────────
    //  VITTORIA / SCONFITTA
    // ─────────────────────────────────────────────

    void checkVictory() {
        turnPhase = COMBAT_OVER;
        gp.monster[monsterIndex] = null;
        onVictory();
    }

    void onVictory() {
        // stub — in futuro: exp, drop, animazioni
        combatMessage = "Hai sconfitto " + monster.name + "!";
        messageTimer = 90;
    }

    void checkDefeat() {
        turnPhase = COMBAT_OVER;
        gp.player.life = gp.player.maxLife;
        onDefeat();
    }

    void onDefeat() {
        // stub — in futuro: game over screen, penalità
        combatMessage = "Sei stato sconfitto... Vita ripristinata.";
        messageTimer = 90;
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
            if (inAbilityMenu) {
                drawAbilityMenu();
            } else {
                drawCommandMenu();
            }
        }
    }

    void drawBackground() {
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
    }

    void drawMonster() {
        if (monster == null) return;
        int monsterSize = gp.tileSize * 3;
        int monsterX = gp.screenWidth / 2 - monsterSize / 2;
        int monsterY = gp.tileSize;

        java.awt.image.BufferedImage img;
        if (monster.downIdle1 != null && monster.downIdle2 != null) {
            img = monsterSpriteNum == 1 ? monster.downIdle1 : monster.downIdle2;
        } else {
            img = monster.down1;
        }
        if (img != null) g2.drawImage(img, monsterX, monsterY, monsterSize, monsterSize, null);

        g2.setFont(ui.MaruMonica.deriveFont(Font.PLAIN, 22f));
        g2.setColor(Color.white);
        String info = monster.name
                + "  HP: " + Math.max(0, monster.life) + "/" + monster.maxLife
                + "  Lv." + monster.level;
        g2.drawString(info, ui.getXforCenteredText(info), monsterY + monsterSize + 36);
    }

    void drawPlayerHUD() {
        g2.setFont(ui.MaruMonica.deriveFont(Font.PLAIN, 22f));
        g2.setColor(Color.white);
        String info = "Player  HP: " + gp.player.life + "/" + gp.player.maxLife
                + "  ATK: " + gp.player.attack
                + "  DEF: " + gp.player.defense;
        g2.drawString(info, gp.tileSize / 2, gp.screenHeight - gp.tileSize * 5);
    }

    void drawMessageBox() {
        if (combatMessage.isEmpty()) return;
        int x = gp.tileSize / 2;
        int y = gp.screenHeight - gp.tileSize * 4;
        int width = gp.screenWidth - gp.tileSize;
        int height = gp.tileSize * 2;
        ui.drawSubWindwow(x, y, width, height);
        g2.setColor(Color.white);
        g2.setFont(ui.MaruMonica.deriveFont(Font.PLAIN, 20f));
        int textX = x + 20;
        int textY = y + 36;
        for (String line : wrapText(combatMessage, width - 40)) {
            g2.drawString(line, textX, textY);
            textY += 26;
        }
    }

    void drawCommandMenu() {
        int x = gp.tileSize / 2;
        int y = gp.screenHeight - gp.tileSize * 4;
        int width = gp.screenWidth - gp.tileSize;
        int height = gp.tileSize * 4 - gp.tileSize / 2;
        ui.drawSubWindwow(x, y, width, height);

        String[] commands = {"Attacca", "Abilità", "Inventario", "Minimappa", "Fuggi"};
        g2.setFont(ui.MaruMonica.deriveFont(Font.PLAIN, 20f));
        g2.setColor(Color.white);

        int textX = x + gp.tileSize;
        int textY = y + 36;
        int lineHeight = (height - 20) / CMD_COUNT;

        for (int i = 0; i < commands.length; i++) {
            g2.drawString(commands[i], textX, textY);
            if (commandNum == i) g2.drawString(">", textX - 28, textY);
            textY += lineHeight;
        }
    }

    void drawAbilityMenu() {
        List<String> abilities = gp.player.unlockedAbilities;
        int x = gp.tileSize / 2;
        int y = gp.screenHeight - gp.tileSize * 4;
        int width = gp.screenWidth - gp.tileSize;
        int height = gp.tileSize * 4 - gp.tileSize / 2;
        ui.drawSubWindwow(x, y, width, height);

        g2.setFont(ui.MaruMonica.deriveFont(Font.PLAIN, 20f));
        g2.setColor(new Color(180, 180, 255));
        g2.drawString("Scegli abilità  [ESC = indietro]", x + gp.tileSize, y + 28);

        g2.setColor(Color.white);
        int textX = x + gp.tileSize;
        int textY = y + 60;
        int lineHeight = abilities.isEmpty() ? 0 : (height - 60) / Math.max(abilities.size(), 1);

        for (int i = 0; i < abilities.size(); i++) {
            g2.drawString(Ability.getName(abilities.get(i)), textX, textY);
            if (abilityCommandNum == i) g2.drawString(">", textX - 28, textY);
            textY += lineHeight;
        }
    }

    // ─────────────────────────────────────────────
    //  UTILITY
    // ─────────────────────────────────────────────

    java.util.List<String> wrapText(String text, int maxWidth) {
        java.util.List<String> lines = new java.util.ArrayList<>();
        FontMetrics fm = g2.getFontMetrics();
        String[] words = text.split(" ");
        StringBuilder current = new StringBuilder();
        for (String word : words) {
            String test = current.length() == 0 ? word : current + " " + word;
            if (fm.stringWidth(test) <= maxWidth) {
                current = new StringBuilder(test);
            } else {
                if (current.length() > 0) lines.add(current.toString());
                current = new StringBuilder(word);
            }
        }
        if (current.length() > 0) lines.add(current.toString());
        return lines;
    }
}
