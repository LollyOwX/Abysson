package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Entity {
    GamePanel gp;
    public double worldX, worldY;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2,
            rightUp1, leftUp1, rightDown1, leftDown1, rightUp2, leftUp2, rightDown2, leftDown2,
            leftIdle1, leftIdle2, rightIdle1, rightIdle2, downIdle1, downIdle2, upIdle1, upIdle2;
    public String direction;
    public String idleDirection;
    public int spriteCounter = 0;
    public int idleSpriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public boolean onPath = false;
    String dialogues[] = new String[100];
    int dialoguesIndex = 0;
    protected int dialogueResetIndex = 0;
    public boolean alive = true;
    public boolean dying = false;
    public int dyingCounter = 0;

    // Stats combattimento
    public int attack;
    public int defense;
    public int level;
    public int monsterIndex = -1;

    // Character Status
    public int maxLife;
    public int life;

    // Campi ex-SuperObject
    public BufferedImage image, image2, image3, image4;
    public boolean collision = false;
    public String name;

    // Abilità sbloccate — lista di ID stringa (es. "NormalAttack", "PowerStrike")
    public List<String> unlockedAbilities = new ArrayList<>();

    /**
     * Pesi delle abilità per l'AI del mostro.
     * Es: abilityWeights.put("NormalAttack", 1);
     *     abilityWeights.put("PowerStrike",  3);
     *     abilityWeights.put("Thunderbolt",  5);
     *
     * Se un'abilità è in unlockedAbilities ma non ha un peso,
     * viene usato il peso di default = 1.
     */
    public Map<String, Integer> abilityWeights = new HashMap<>();

    /**
     * Intelligenza AI del mostro (1-10).
     * 1 = sceglie quasi a caso (pesi appiattiti)
     * 10 = amplifica molto i pesi, sceglie quasi sempre la più forte
     */
    public int monsterIntelligence = 5;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public BufferedImage setup(String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        String path = imagePath;
        if (!path.startsWith("/")) path = "/" + path;
        if (!path.toLowerCase().endsWith(".png")) path = path + ".png";
        InputStream is = getClass().getResourceAsStream(path);
        if (is == null) {
            System.err.println("ERROR: resource not found: " + path);
            return null;
        }
        try {
            image = ImageIO.read(is);
            if (image == null) {
                System.err.println("ERROR: ImageIO.read returned null for: " + path);
                return null;
            }
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            System.err.println("ERROR loading image: " + path);
            e.printStackTrace();
            return null;
        } finally {
            try { is.close(); } catch (IOException ignored) {}
        }
        return image;
    }

    public void setAction() {}

    /**
     * Sceglie un'abilità usando weighted random modulata da monsterIntelligence.
     *
     * Funzionamento:
     *   1. Ogni abilità ha un peso base (da abilityWeights, default 1).
     *   2. Il peso viene elevato a una potenza dipendente da monsterIntelligence:
     *        peso_finale = peso_base ^ (intelligence / 5.0)
     *      Con intelligence=5 → esponente=1 → pesi invariati
     *      Con intelligence=1 → esponente=0.2 → pesi quasi uguali (random)
     *      Con intelligence=10 → esponente=2.0 → pesi amplificati (sceglie il migliore)
     *   3. Si fa una weighted random sulla somma dei pesi finali.
     *
     * Override nei mostri per logiche diverse (es. situazionale: cura se HP bassa).
     */
    public String chooseAction() {
        if (unlockedAbilities.isEmpty()) return "NormalAttack";

        double exponent = monsterIntelligence / 5.0;

        // Calcola i pesi finali
        double[] finalWeights = new double[unlockedAbilities.size()];
        double totalWeight = 0;
        for (int i = 0; i < unlockedAbilities.size(); i++) {
            String id = unlockedAbilities.get(i);
            int baseWeight = abilityWeights.getOrDefault(id, 1);
            double w = Math.pow(baseWeight, exponent);
            finalWeights[i] = w;
            totalWeight += w;
        }

        // Weighted random
        double roll = new Random().nextDouble() * totalWeight;
        double cumulative = 0;
        for (int i = 0; i < unlockedAbilities.size(); i++) {
            cumulative += finalWeights[i];
            if (roll < cumulative) {
                return unlockedAbilities.get(i);
            }
        }
        // fallback (non dovrebbe mai arrivare qui)
        return unlockedAbilities.get(unlockedAbilities.size() - 1);
    }

    public void speak() {
        if (dialogues[dialoguesIndex] == null) {
            dialoguesIndex = dialogueResetIndex;
        }
        gp.ui.currentDialogue = dialogues[dialoguesIndex];
        dialoguesIndex++;
        if (dialoguesIndex >= dialogues.length || dialogues[dialoguesIndex] == null) {
            dialoguesIndex = dialogueResetIndex;
        }
        switch (gp.player.direction) {
            case "down":  direction = "up";    break;
            case "left":  direction = "right"; break;
            case "right": direction = "left";  break;
            default:      direction = "down";  break;
        }
    }

    public void update() {
        setAction();
        if (direction == null) direction = idleDirection != null ? idleDirection : "down";

        double dx = 0;
        double dy = 0;
        switch (direction) {
            case "up":    dy = -1; idleDirection = "idle_up";    break;
            case "down":  dy =  1; idleDirection = "idle_down";  break;
            case "left":  dx = -1; idleDirection = "idle_left";  break;
            case "right": dx =  1; idleDirection = "idle_right"; break;
        }

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);

        boolean isMoving = !collisionOn && (dx != 0 || dy != 0);
        if (isMoving) {
            worldX += (int) Math.round(dx * speed);
            worldY += (int) Math.round(dy * speed);
            spriteCounter++;
            if (spriteCounter > 13) {
                spriteNum = spriteNum == 1 ? 2 : 1;
                spriteCounter = 0;
            }
            idleSpriteCounter = 0;
        } else {
            direction = idleDirection;
            idleSpriteCounter++;
            if (idleSpriteCounter > 32) {
                spriteNum = spriteNum == 1 ? 2 : 1;
                idleSpriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = (int)(worldX - gp.player.worldX + gp.player.screenX);
        int screenY = (int)(worldY - gp.player.worldY + gp.player.screenY);
        String dir = (direction != null) ? direction : (idleDirection != null ? idleDirection : "idle_down");
        switch (dir) {
            case "up":         image = spriteNum == 1 ? up1        : up2;        break;
            case "down":       image = spriteNum == 1 ? down1      : down2;      break;
            case "left":       image = spriteNum == 1 ? left1      : left2;      break;
            case "right":      image = spriteNum == 1 ? right1     : right2;     break;
            case "idle_up":    image = spriteNum == 1 ? upIdle1    : upIdle2;    break;
            case "idle_down":  image = spriteNum == 1 ? downIdle1  : downIdle2;  break;
            case "idle_left":  image = spriteNum == 1 ? leftIdle1  : leftIdle2;  break;
            case "idle_right": image = spriteNum == 1 ? rightIdle1 : rightIdle2; break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
