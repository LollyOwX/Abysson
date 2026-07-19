package main;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
            col++;

            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void checkEvent() {
        int xDistance = (int)Math.abs(gp.player.worldX - previousEventX);
        int yDistance = (int)Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }

        if (canTouchEvent == true) {
            if (hit(28, 11, "any") == true) {damagePit(23, 10, gp.dialogueState);}
            if (hit(19, 14, "up") == true) {healingPool(19, 14, gp.dialogueState);}
        }
    }

    public boolean hit(int col, int row, String reqDirection) {
        boolean hit = false;
        gp.player.solidArea.x = (int)gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = (int)gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;
        if (gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
            if (gp.player.direction.equals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;
                previousEventX = (int)gp.player.worldX;
                previousEventY = (int)gp.player.worldY;
            }
        }
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
        return hit;
    }

    public void damagePit(int col, int row, int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue =
                "You fell into a Pit!";
        gp.player.life -= 1;
        canTouchEvent = false;
    }
    public void healingPool(int col, int row, int gameState) {
        if (gp.KeyH.enterPressed == true) {
            gp.gameState = gameState;
            gp.ui.currentDialogue =
                    "Drinking from the Lake,\nyour vital energy got restored";
            gp.player.life = gp.player.maxLife;
        }
    }
}