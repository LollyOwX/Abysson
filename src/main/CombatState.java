package main;

import entity.Entity;
import java.awt.*;
import java.util.Random;

public class CombatState {
    GamePanel gp;
    UI ui;
    Graphics2D g2;

    public Entity monster;
    public int monsterIndex;

    // 0=Attacca, 1=Abilità, 2=Inventario, 3=Fuggi
    public int commandNum = 0;

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

    public void startCombat(Entity monster, int monsterIndex) {
        this.monster = monster;
        this.monsterIndex = monsterIndex;
        this.commandNum = 0;
        this.turnPhase = PLAYER_TURN;
        this.combatMessage = "Un " + monster.name + " selvatico si avvicina!";
        this.messageTimer = 0;
        this.monsterSpriteCounter = 0;
        this.monsterSpriteNum = 1;
    }

    public void update() {
        // Animazione idle del mostro - sempre attiva
        monsterSpriteCounter++;
        if(monsterSpriteCounter > 20) {
            monsterSpriteNum = monsterSpriteNum == 1 ? 2 : 1;
            monsterSpriteCounter = 0;
        }

        if(messageTimer > 0) {
            messageTimer--;
            return;
        }

        if(turnPhase == MONSTER_TURN) {
            monsterAttack();
            return;
        }

        if(turnPhase == COMBAT_OVER) {
            endCombat();
        }
    }

    public void confirmCommand() {
        if(turnPhase != PLAYER_TURN || messageTimer > 0) return;

        switch(commandNum) {
            case 0: playerAttack();  break;
            case 1: playerAbility(); break;
            case 2: // Inventario - da implementare
                combatMessage = "Inventario vuoto.";
                messageTimer = 60;
                break;
            case 3: tryFlee(); break;
        }
    }

    void playerAttack() {
        int dmg = gp.player.attackAction(monster);
        monster.life -= dmg;
        combatMessage = "Hai attaccato " + monster.name + " per " + dmg + " danni!";
        messageTimer = 60;
        if(monster.life <= 0) {
            combatMessage = "Hai sconfitto " + monster.name + "!";
            messageTimer = 90;
            turnPhase = COMBAT_OVER;
            gp.monster[monsterIndex] = null;
        } else {
            turnPhase = MONSTER_TURN;
        }
    }

    void playerAbility() {
        int dmg = gp.player.abilityAction(monster);
        monster.life -= dmg;
        combatMessage = "Hai usato un'abilità per " + dmg + " danni!";
        messageTimer = 60;
        if(monster.life <= 0) {
            combatMessage = "Hai sconfitto " + monster.name + "!";
            messageTimer = 90;
            turnPhase = COMBAT_OVER;
            gp.monster[monsterIndex] = null;
        } else {
            turnPhase = MONSTER_TURN;
        }
    }

    void monsterAttack() {
        Random random = new Random();
        int dmg;
        // 50% attacco normale, 50% abilità
        if(random.nextInt(2) == 0) {
            dmg = monster.attackAction(gp.player);
            combatMessage = monster.name + " ti ha colpito per " + dmg + " danni!";
        } else {
            dmg = monster.abilityAction(gp.player);
            combatMessage = monster.name + " usa un'abilità per " + dmg + " danni!";
        }
        gp.player.life -= dmg;
        messageTimer = 60;
        if(gp.player.life <= 0) {
            combatMessage = "Sei stato sconfitto...";
            messageTimer = 90;
            turnPhase = COMBAT_OVER;
            gp.player.life = gp.player.maxLife;
        } else {
            turnPhase = PLAYER_TURN;
            commandNum = 0;
        }
    }

    void tryFlee() {
        combatMessage = "Sei fuggito!";
        messageTimer = 60;
        turnPhase = COMBAT_OVER;
        // non rimuove il mostro: è ancora nel mondo
    }

    void endCombat() {
        gp.gameState = gp.playState;
        monster = null;
        monsterIndex = -1;
        turnPhase = PLAYER_TURN;
        combatMessage = "";
        messageTimer = 0;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        // Sfondo
        g2.setColor(new Color(20, 20, 40));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Sprite animata del mostro (downIdle in loop)
        if(monster != null) {
            int monsterSize = gp.tileSize * 3;
            int monsterX = gp.screenWidth/2 - monsterSize/2;
            int monsterY = gp.tileSize;

            java.awt.image.BufferedImage img;
            if(monster.downIdle1 != null && monster.downIdle2 != null) {
                img = monsterSpriteNum == 1 ? monster.downIdle1 : monster.downIdle2;
            } else {
                img = monster.down1;
            }
            if(img != null) {
                g2.drawImage(img, monsterX, monsterY, monsterSize, monsterSize, null);
            }

            // Nome e HP mostro
            g2.setFont(ui.MaruMonica.deriveFont(Font.PLAIN, 22f));
            g2.setColor(Color.white);
            String monsterInfo = monster.name + "  HP: " + monster.life + "/" + monster.maxLife;
            int x = ui.getXforCenteredText(monsterInfo);
            g2.drawString(monsterInfo, x, monsterY + monsterSize + 40);
        }

        // HP player
        g2.setFont(ui.MaruMonica.deriveFont(Font.PLAIN, 22f));
        g2.setColor(Color.white);
        String playerInfo = "Player  HP: " + gp.player.life + "/" + gp.player.maxLife;
        g2.drawString(playerInfo, gp.tileSize/2, gp.screenHeight - gp.tileSize * 4);

        // Box messaggio
        if(!combatMessage.isEmpty()) {
            int x = gp.tileSize/2;
            int y = gp.screenHeight - gp.tileSize * 3 - gp.tileSize/2;
            int width = gp.screenWidth - gp.tileSize;
            int height = gp.tileSize + gp.tileSize/2;
            ui.drawSubWindwow(x, y, width, height);
            g2.setColor(Color.white);
            g2.setFont(ui.MaruMonica.deriveFont(Font.PLAIN, 22f));
            g2.drawString(combatMessage, x + 24, y + height/2 + 8);
        }

        // Menu comandi (solo turno player, nessun messaggio attivo)
        if(turnPhase == PLAYER_TURN && messageTimer == 0) {
            drawCommandMenu();
        }
    }

    void drawCommandMenu() {
        int x = gp.tileSize/2;
        int y = gp.screenHeight - gp.tileSize * 3;
        int width = gp.screenWidth - gp.tileSize;
        int height = gp.tileSize * 3 - gp.tileSize/2;
        ui.drawSubWindwow(x, y, width, height);

        String[] commands = {"Attacca", "Abilità", "Inventario", "Fuggi"};

        g2.setFont(ui.MaruMonica.deriveFont(Font.PLAIN, 22f));
        g2.setColor(Color.white);

        int textX = x + gp.tileSize;
        int textY = y + gp.tileSize/2;
        int lineHeight = (height - gp.tileSize/2) / commands.length;

        for(int i = 0; i < commands.length; i++) {
            g2.drawString(commands[i], textX, textY);
            if(commandNum == i) {
                g2.drawString(">", textX - 30, textY);
            }
            textY += lineHeight;
        }
    }
}