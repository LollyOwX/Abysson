package main;

import entity.Entity;
import object.OBJ_Left_Health;
import object.OBJ_Middle_Health;
import object.OBJ_Right_Health;

import java.awt.*;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font MaruMonica, PurisaB, BrothericR, GraenMetalR, WonderWorldR, HoperushD;
    BufferedImage health_left_full, health_left_half, health_left_low, health_left_empty, health_center_full, health_center_half, health_center_low, health_center_empty, health_right_full, health_right_half, health_right_low, health_right_empty;
    public boolean messageOn = false;
	public String message = " ";
	int messageCounter = 0;
	public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0;
    public CombatState combat;
	public UI(GamePanel gp) {
		this.gp = gp;
        combat = new CombatState(gp, this);
        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            MaruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            PurisaB = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Brotheric-Regular-Demo.otf");
            BrothericR = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/GraenMetal-Regular.otf");
            GraenMetalR = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/HoperushDemo-G35pG.otf");
            HoperushD = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/WonderworldPersonalUseRegular-gxdo3.otf");
            WonderWorldR = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        //HUD Health
        Entity lefthealth = new OBJ_Left_Health(gp);
        Entity middlehealth = new OBJ_Middle_Health(gp);
        Entity righthealth = new OBJ_Right_Health(gp);

        health_left_full = lefthealth.image;
        health_left_half = lefthealth.image2;
        health_left_low = lefthealth.image3;
        health_left_empty = lefthealth.image4;

        health_center_full = middlehealth.image;
        health_center_half = middlehealth.image2;
        health_center_low = middlehealth.image3;
        health_center_empty = middlehealth.image4;

        health_right_full = righthealth.image;
        health_right_half = righthealth.image2;
        health_right_low = righthealth.image3;
        health_right_empty = righthealth.image4;
    }

    public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(MaruMonica);
		g2.setColor(Color.white);
        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
		if(gp.gameState == gp.playState) {
			drawPlayerLife();
		}
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
            drawPlayerLife();
		}
        if(gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }
        if(gp.gameState == gp.combatState) {
            combat.draw(g2);
        }
	}
    public void drawPlayerLife() {

        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        BufferedImage left = null;
        BufferedImage center = null;
        BufferedImage right = null;
        double healthPercent = (double) gp.player.life / gp.player.maxLife * 100;

        if(healthPercent > 75) {
            left = health_left_full;
            center = health_center_full;
            right = health_right_full;
        }
        else if(healthPercent > 50) {
            left = health_left_half;
            center = health_center_half;
            right = health_right_half;
        }
        else if(healthPercent > 25) {
            left = health_left_low;
            center = health_center_low;
            right = health_right_low;
        }
        else {
            left = health_left_empty;
            center = health_center_empty;
            right = health_right_empty;
        }
        g2.drawImage(left, x, y, null);
        x += gp.tileSize;
        g2.drawImage(center, x, y, null);
        x += gp.tileSize;
        g2.drawImage(right, x, y, null);
    }
    public void drawTitleScreen() {
        if (titleScreenState == 0) {
            g2.setFont(BrothericR);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 72));
            String text = "Abysson";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            g2.setColor(new Color(0, 0, 255));
            g2.drawString(text, x + 4, y + 4);
            g2.setColor(new Color(0, 127, 255));
            g2.drawString(text, x + 2, y + 2);
            g2.setColor(new Color(0, 255, 200));
            g2.drawString(text, x, y);
            //PLAYER AL CENTRO. SCRAP
            //x = gp.screenWidth/2 - (gp.tileSize*2)/2;
            //y = gp.screenHeight/2;
            //g2.drawImage(gp.player.downIdle1, x, y, gp.tileSize*2, gp.tileSize*2,null);
            g2.setFont(MaruMonica);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48));
            text = "New Game";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3.5;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Load Game";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Options";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Quit";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 3) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        } else if (titleScreenState == 1) {
            g2.setFont(MaruMonica);
            g2.setColor(new Color(255, 255, 255));
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 42F));
            String text = "Select your class";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);

            text = "Warrior";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "Mage";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "Archer";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "Back";
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.drawString(text, x, y);
            if(commandNum == 3) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        } else if (titleScreenState == 2) {
            g2.setFont(MaruMonica);
            g2.setColor(new Color(255, 255, 255));
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 42F));
            String text = "Select difficulty";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);

            text = "Easy";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "Normal";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "Hard";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "Back";
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.drawString(text, x, y);
            if(commandNum == 3) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
    }

     public void drawStatsBars() {

     }
	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,83));
		String text = "PAUSED";
		int y = gp.screenHeight/2;
		int x = getXforCenteredText(text);
		g2.drawString(text, x, y);
	}
    public void drawDialogueScreen(){
        Color c = new Color(255,255,255);
        g2.setFont(MaruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32));
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth-(gp.tileSize*4);
        int height = gp.tileSize*4;
        drawSubWindwow(x, y, width, height);
        x += gp.tileSize;
        y += gp.tileSize;
        g2.setColor(c);
        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 40;
        }
     }
    public void drawSubWindwow(int x, int y, int width, int height) {
        Color c =  new Color(255, 255, 255);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        c = new Color(0,0,0);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.fillRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
	public int getXforCenteredText(String text) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
	public int getYforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int y = gp.screenWidth/2 - length/2;
		return y;
	}
}




