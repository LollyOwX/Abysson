package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Left_Health extends Entity {
    GamePanel gp;
    public OBJ_Left_Health(GamePanel gp) {
        super(gp);
        name = "Left_Health";
        image = setup("/bars/bar_health_left_full.png", gp.tileSize, gp.tileSize);
        image2 = setup("/bars/bar_health_left_half.png", gp.tileSize, gp.tileSize);
        image3 = setup("/bars/bar_health_left_low.png", gp.tileSize, gp.tileSize);
        image4 = setup("/bars/bar_all_left_empty.png", gp.tileSize, gp.tileSize);
    }
}
