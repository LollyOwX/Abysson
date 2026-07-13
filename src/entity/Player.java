package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import main.GamePanel;
import main.KeyHandler;
import java.awt.image.BufferedImage;
import java.awt.*;

public class Player extends Entity {
    KeyHandler KeyH;
    public final int screenX;
    public final int screenY;
    public String playerClass = "Warrior";
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
        speed = gp.worldWidth / 600;
        direction = "down";
        idleDirection = "down";
        maxLife = 10;
        life = maxLife;
        attack = 3;
        defense = 2;
        unlockedAbilities.add("PowerStrike");
        unlockedAbilities.add("AcquaJet");
        unlockedAbilities.add("Thunderbolt");
        unlockedAbilities.add("Earthshock");
    }

    public void getPlayerImage() {
        up1 = setup("/player/" + playerClass + "_up_1", 19*3, 19*3);
        up2 = setup("/player/" + playerClass + "_up_2", 19*3, 19*3);
        down1 = setup("/player/" + playerClass + "_down_1", 19*3, 19*3);
        down2 = setup("/player/" + playerClass + "_down_2", 19*3, 19*3);
        left1 = setup("/player/" + playerClass + "_left_1", 19*3, 19*3);
        left2 = setup("/player/" + playerClass + "_left_2", 19*3, 19*3);
        right1 = setup("/player/" + playerClass + "_right_1", 19*3, 19*3);
        right2 = setup("/player/" + playerClass + "_right_2", 19*3, 19*3);
        //idle
        downIdle1 = setup("/player/" + playerClass + "_downidle_1", 19*3, 19*3);
        downIdle2 = setup("/player/" + playerClass + "_downidle_2", 19*3, 19*3);
        upIdle1 = setup("/player/" + playerClass + "_upidle_1", 19*3, 19*3);
        upIdle2 = setup("/player/" + playerClass + "_upidle_2", 19*3, 19*3);
        leftIdle1 = setup("/player/" + playerClass + "_leftidle_1", 19*3, 19*3);
        leftIdle2 = setup("/player/" + playerClass + "_leftidle_2", 19*3, 19*3);
        rightIdle1 = setup("/player/" + playerClass + "_rightidle_1", 19*3, 19*3);
        rightIdle2 = setup("/player/" + playerClass + "_rightidle_2", 19*3, 19*3);
        //ortogonal
        rightUp1 = setup("/player/" + playerClass + "_rightup_1", 19*3, 19*3);
        rightUp2 = setup("/player/" + playerClass + "_rightup_2", 19*3, 19*3);
        rightDown1 = setup("/player/" + playerClass + "_rightdown_1", 19*3, 19*3);
        rightDown2 = setup("/player/" + playerClass + "_rightdown_2", 19*3, 19*3);
        leftUp1 = setup("/player/" + playerClass + "_leftup_1", 19*3, 19*3);
        leftUp2 = setup("/player/" + playerClass + "_leftup_2", 19*3, 19*3);
        leftDown1 = setup("/player/" + playerClass + "_leftdown_1", 19*3, 19*3);
        leftDown2 = setup("/player/" + playerClass + "_leftdown_2", 19*3, 19*3);
    }

    public void update() {

        if (KeyH.upPressed || KeyH.downPressed || KeyH.leftPressed || KeyH.rightPressed || KeyH.enterPressed) {
            double dx = 0;
            double dy = 0;
            if (KeyH.upPressed) {
                dy -= 1;
                direction = "up";
                idleDirection = "idle_up";
            }
            if (KeyH.downPressed) {
                dy += 1;
                direction = "down";
                idleDirection = "idle_down";
            }
            if (KeyH.leftPressed) {
                dx -= 1;
                direction = "left";
                idleDirection = "idle_left";
            }
            if (KeyH.rightPressed) {
                dx += 1;
                direction = "right";
                idleDirection = "idle_right";
            }

            if (dx != 0 || dy != 0) {
                double len = Math.sqrt(dx * dx + dy * dy);
                dx /= len;
                dy /= len;
                int moveX = (int) Math.round(dx * speed);
                int moveY = (int) Math.round(dy * speed);
                collisionOn = false;
                worldX += moveX;

                gp.cChecker.checkTile(this);
                gp.cChecker.checkObject(this, true);
                gp.cChecker.checkEntity(this, gp.npc);
                gp.cChecker.checkEntity(this, gp.monster);

                if (collisionOn) {
                    worldX -= moveX;
                }
                collisionOn = false;
                worldY += moveY;
                gp.cChecker.checkTile(this);
                gp.cChecker.checkObject(this, true);
                gp.cChecker.checkEntity(this, gp.npc);
                gp.cChecker.checkEntity(this, gp.monster);
                if (collisionOn) {
                    worldY -= moveY;
                }
            }

            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);
            int npcIndex = gp.cChecker.checkEntityInteraction(this, gp.npc, gp.tileSize/2);
            interactNPC(npcIndex);
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            gp.cChecker.checkEntity(this, gp.monster);
            gp.eHandler.checkEvent();
            gp.KeyH.enterPressed = false;
            spriteCounter++;

            if (spriteCounter > 13) {
                spriteNum = spriteNum == 1 ? 2 : 1;
                spriteCounter = 0;
            }
        } else {
            direction = idleDirection;
            idleSpriteCounter++;
            if (idleSpriteCounter > 32) {
                spriteNum = spriteNum == 1 ? 2 : 1;
                idleSpriteCounter = 0;
            }
        }
    }

    public void interactNPC(int i) {
        if (i != 999) {
            if (gp.KeyH.enterPressed) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }
    public void contactMonster(int i) {
        if(i != 999) {
            gp.gameState = gp.combatState;
            gp.ui.combat.startCombat(gp.monster[i], i);
        }
    }


    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        boolean up = KeyH.upPressed;
        boolean down = KeyH.downPressed;
        boolean left = KeyH.leftPressed;
        boolean right = KeyH.rightPressed;

        if (up && right) {
            image = spriteNum == 1 ? rightUp1 : rightUp2;
        } else if (up && left) {
            image = spriteNum == 1 ? leftUp1 : leftUp2;
        } else if (down && right) {
            image = spriteNum == 1 ? rightDown1 : rightDown2;
        } else if (down && left) {
            image = spriteNum == 1 ? leftDown1 : leftDown2;
        } else {
            String dir =
                    direction != null
                            ? direction
                            : idleDirection != null
                              ? idleDirection
                              : "down";
            image = switch (dir) {
                case "up" -> spriteNum == 1 ? up1 : up2;
                case "down" -> spriteNum == 1 ? down1 : down2;
                case "left" -> spriteNum == 1 ? left1 : left2;
                case "right" -> spriteNum == 1 ? right1 : right2;
                case "idle_up" -> spriteNum == 1 ? upIdle1 : upIdle2;
                case "idle_down" -> spriteNum == 1 ? downIdle1 : downIdle2;
                case "idle_left" -> spriteNum == 1 ? leftIdle1 : leftIdle2;
                case "idle_right" -> spriteNum == 1 ? rightIdle1 : rightIdle2;
                default -> spriteNum == 1 ? downIdle1 : downIdle2;
            };
        }
        g2.drawImage(image, screenX, screenY, null);
    }
    public void pickUpObject(int i) {
        if (i != 999) {
        }
    }
}