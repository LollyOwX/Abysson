package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Middle_Health extends Entity {
    GamePanel gp;
    public OBJ_Middle_Health(GamePanel gp) {
        super(gp);
        name = "Left_Health";
        image = setup("/bars/bar_health_middle_full.png", gp.tileSize, gp.tileSize);
        image2 = setup("/bars/bar_health_middle_half.png", gp.tileSize, gp.tileSize);
        image3 = setup("/bars/bar_health_middle_low.png", gp.tileSize, gp.tileSize);
        image4 = setup("/bars/bar_all_middle_empty.png", gp.tileSize, gp.tileSize);
    }
}
