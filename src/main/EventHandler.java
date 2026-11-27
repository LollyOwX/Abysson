package main;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][];
    private final quest.EventList eventList;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    List<LocationZone> zones = LocationZone.load();
    Set<LocationZone> insideZones = new HashSet<>();
    String zonesWorld = "";

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventList = new quest.EventList(gp);
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

    // ── Eventi di EventList: da qualunque classe, es. gp.eHandler.zoom(16) ──

    public void call(String name, int value) {
        eventList.run(name, value);
    }

    public void zoom(int amount) {
        call("zoom", amount);
    }

    public void checkEvent() {
        checkLocations();

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
        Rectangle event = new Rectangle(col * gp.tileSize + scaled(eventRect[col][row].x), row * gp.tileSize + scaled(eventRect[col][row].y),
                Math.max(1, scaled(eventRect[col][row].width)), Math.max(1, scaled(eventRect[col][row].height)));
        if (gp.player.solidArea.intersects(event) && eventRect[col][row].eventDone == false) {
            if (gp.player.direction.equals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;
                previousEventX = (int)gp.player.worldX;
                previousEventY = (int)gp.player.worldY;
            }
        }
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        return hit;
    }

    private int scaled(int designPixels) {
        return Math.round((float) designPixels * gp.tileSize / GamePanel.DESIGN_TILE_SIZE);
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

    // ── Quest: zone di mappa ───────────────────────────────────────

    public void checkLocations() {
        if (!gp.currentWorld.equals(zonesWorld)) {
            insideZones.clear();
            zonesWorld = gp.currentWorld;
        }
        int col = (int) ((gp.player.worldX + gp.player.solidAreaDefaultX + gp.player.solidArea.width / 2.0) / gp.tileSize);
        int row = (int) ((gp.player.worldY + gp.player.solidAreaDefaultY + gp.player.solidArea.height / 2.0) / gp.tileSize);

        for (LocationZone z : zones) {
            boolean inside = z.contains(gp.currentWorld, col, row);
            if (!inside) {
                insideZones.remove(z);
            } else if (insideZones.add(z)) {
                if (z.startQuestId != null) gp.questManager.startQuest(z.startQuestId);
                gp.questManager.notify(quest.QuestEventType.REACH_LOCATION, z.id);
            }
        }
    }
}
