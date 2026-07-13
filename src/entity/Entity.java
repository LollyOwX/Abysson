package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import main.*;
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
    public int precision = 100; // % probabilità di colpire (default = sempre)
    public int evasion   = 0;   // % probabilità di eludere

    // Character Status
    public int maxLife;
    public int life;

    // Campi ex-SuperObject
    public BufferedImage image, image2, image3, image4;
    public boolean collision = false;
    public String name;

    // Sistema elementi
    public ElementSystem.Element lastElementHit = ElementSystem.Element.NONE;
    public List<ElementSystem.ActiveEffect> activeEffects = new ArrayList<>();

    // Abilità sbloccate e AI
    public List<String> unlockedAbilities = new ArrayList<>();
    public Map<String, Integer> abilityWeights = new HashMap<>();
    public int monsterIntelligence = 5;

    public Entity(GamePanel gp) { this.gp = gp; }


    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int i = 5;
        if(dyingCounter <= i) {changeAlpha(g2, 0f);}
        if(dyingCounter > i && dyingCounter <= i*2) {changeAlpha(g2, 1f);}
        if(dyingCounter > i*2 && dyingCounter <= i*3) {changeAlpha(g2, 0f);}
        if(dyingCounter > i*3 && dyingCounter <= i*4) {changeAlpha(g2, 1f);}
        if(dyingCounter > i*4 && dyingCounter <= i*5) {changeAlpha(g2, 0f);}
        if(dyingCounter > i*5 && dyingCounter <= i*6) {changeAlpha(g2, 1f);}
        if(dyingCounter > i*6 && dyingCounter <= i*7) {changeAlpha(g2, 0f);}
        if(dyingCounter > i*7) {
            dying = false;
            alive = false;
        }
    }
    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    public void restoreAlpha(Graphics2D g2) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        String path = imagePath;
        if (!path.startsWith("/")) path = "/" + path;
        if (!path.toLowerCase().endsWith(".png")) path = path + ".png";
        InputStream is = getClass().getResourceAsStream(path);
        if (is == null) { System.err.println("ERROR: resource not found: " + path); return null; }
        try {
            image = ImageIO.read(is);
            if (image == null) { System.err.println("ERROR: null image: " + path); return null; }
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            System.err.println("ERROR loading: " + path); e.printStackTrace(); return null;
        } finally { try { is.close(); } catch (IOException ignored) {} }
        return image;
    }

    public void setAction() {}

    /**
     * Sceglie un'abilità con weighted random modulata da monsterIntelligence.
     * peso_finale = peso_base ^ (intelligence / 5.0)
     */
    public String chooseAction() {
        if (unlockedAbilities.isEmpty()) return "NormalAttack";
        double exp = monsterIntelligence / 5.0;
        double[] w = new double[unlockedAbilities.size()];
        double total = 0;
        for (int i = 0; i < unlockedAbilities.size(); i++) {
            w[i] = Math.pow(abilityWeights.getOrDefault(unlockedAbilities.get(i), 1), exp);
            total += w[i];
        }
        double roll = new Random().nextDouble() * total, cum = 0;
        for (int i = 0; i < unlockedAbilities.size(); i++) {
            cum += w[i];
            if (roll < cum) return unlockedAbilities.get(i);
        }
        return unlockedAbilities.get(unlockedAbilities.size() - 1);
    }

    public void speak() {
        if (dialogues[dialoguesIndex] == null) dialoguesIndex = dialogueResetIndex;
        gp.ui.currentDialogue = dialogues[dialoguesIndex];
        dialoguesIndex++;
        if (dialoguesIndex >= dialogues.length || dialogues[dialoguesIndex] == null)
            dialoguesIndex = dialogueResetIndex;
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
        double dx = 0, dy = 0;
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
            if (spriteCounter > 13) { spriteNum = spriteNum == 1 ? 2 : 1; spriteCounter = 0; }
            idleSpriteCounter = 0;
        } else {
            direction = idleDirection;
            idleSpriteCounter++;
            if (idleSpriteCounter > 32) { spriteNum = spriteNum == 1 ? 2 : 1; idleSpriteCounter = 0; }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = (int)(worldX - gp.player.worldX + gp.player.screenX);
        int screenY = (int)(worldY - gp.player.worldY + gp.player.screenY);
        String dir = direction != null ? direction : (idleDirection != null ? idleDirection : "idle_down");
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
        if(dying) {
            dyingAnimation(g2);
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        if(dying) {
            restoreAlpha(g2);
        }
    }
}