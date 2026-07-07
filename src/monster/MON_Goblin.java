package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class MON_Goblin extends Entity {
    public MON_Goblin(GamePanel gp) {
        super(gp);

        name = "Goblin";
        level = 2 * gp.difficulty;
        speed = 1;
        maxLife = 2 * level;
        attack = 1 * level;
        defense = 1 * level;

        life = maxLife;
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 30;
        solidArea.height = 24;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        direction = "down";
        idleDirection = "idle_down";
    }

    public void getImage() {
        up1 = setup("/monsters/Goblin_up_1");
        up2 = setup("/monsters/Goblin_up_2");
        down1 = setup("/monsters/Goblin_down_1");
        down2 = setup("/monsters/Goblin_down_2");
        right1 = setup("/monsters/Goblin_right_1");
        right2 = setup("/monsters/Goblin_right_2");
        left1 = setup("/monsters/Goblin_left_1");
        left2 = setup("/monsters/Goblin_left_2");
        upIdle1 = setup("/monsters/Goblin_upidle_1");
        upIdle2 = setup("/monsters/Goblin_upidle_2");
        downIdle1 = setup("/monsters/Goblin_downidle_1");
        downIdle2 = setup("/monsters/Goblin_downidle_2");
        rightIdle1 = setup("/monsters/Goblin_rightidle_1");
        rightIdle2 = setup("/monsters/Goblin_rightidle_2");
        leftIdle1 = setup("/monsters/Goblin_leftidle_1");
        leftIdle2 = setup("/monsters/Goblin_leftidle_2");
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

    @Override
    public int attackAction(Entity target) {
        return Math.max(1, this.attack - target.defense);
    }

    @Override
    public int abilityAction(Entity target) {
        return Math.max(1, (this.attack * 3) - target.defense);
    }
}
