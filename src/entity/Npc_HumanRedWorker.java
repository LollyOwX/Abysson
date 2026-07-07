package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;

public class Npc_HumanRedWorker extends Entity {

    public Npc_HumanRedWorker(GamePanel gp) {
        super(gp);

        direction = "down";
        idleDirection = "idle_down";
        speed = 1;
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        dialogueResetIndex = 5;
        getImage();
        setDialogue();
    }


    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    public void setDialogue() {
        dialogues[0] = "Oh, hi there!";
        dialogues[1] = "What can I do for you?";
        dialogues[2] = "Not that I can do anything...";
        dialogues[3] = "Explore as much \nas you want";
        dialogues[4] = "I'll stay here";
        dialogues[5] = "Good luck";
    }

    public void getImage() {
        up1 = setup("/npc/RedWorkerNpc_up1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/RedWorkerNpc_up2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/RedWorkerNpc_down1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/RedWorkerNpc_down2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/RedWorkerNpc_left1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/RedWorkerNpc_left2", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/RedWorkerNpc_right1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/RedWorkerNpc_right2", gp.tileSize, gp.tileSize);
        downIdle1 = setup("/npc/RedWorkerNpc_downIdle1", gp.tileSize, gp.tileSize);
        downIdle2  = setup("/npc/RedWorkerNpc_downIdle2", gp.tileSize, gp.tileSize);
        leftIdle1 = setup("/npc/RedWorkerNpc_leftIdle1", gp.tileSize, gp.tileSize);
        leftIdle2 = setup("/npc/RedWorkerNpc_leftIdle2", gp.tileSize, gp.tileSize);
        rightIdle1 = setup("/npc/RedWorkerNpc_rightIdle1", gp.tileSize, gp.tileSize);
        rightIdle2 = setup("/npc/RedWorkerNpc_rightIdle2", gp.tileSize, gp.tileSize);
        upIdle1 = setup("/npc/RedWorkerNpc_upIdle1", gp.tileSize, gp.tileSize);
        upIdle2 = setup("/npc/RedWorkerNpc_upIdle2", gp.tileSize, gp.tileSize);
    }

    public void setAction() {
        actionLockCounter++;

        if(actionLockCounter >= 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if(i <= 40) {
                direction = idleDirection;
            } else if(i <= 55) {
                direction = "up";
                idleDirection = "idle_up";
            } else if(i <= 70) {
                direction = "down";
                idleDirection = "idle_down";
            } else if(i <= 85) {
                direction = "left";
                idleDirection = "idle_left";
            } else {
                direction = "right";
                idleDirection = "idle_right";
            }
            actionLockCounter = 0;
        }
    }
    public void speak() {
        super.speak();
    }
}

