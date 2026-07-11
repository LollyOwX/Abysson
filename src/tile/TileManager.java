package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    // Contatore globale per le animazioni — avanza ogni frame di gioco
    private int animationTick = 0;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[100];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void setup(int index, String imagePath, boolean collision) {
        UtilityTool uTool = new UtilityTool();
        String fullPath = "/tiles/" + imagePath + ".png";
        String altPath  = "tiles/"  + imagePath + ".png";
        try (InputStream is = getClass().getResourceAsStream(fullPath) != null
                ? getClass().getResourceAsStream(fullPath)
                : Thread.currentThread().getContextClassLoader().getResourceAsStream(altPath)) {
            if (is == null) throw new IllegalArgumentException("Resource not found: " + fullPath);
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(is);
            if (tile[index].image == null) throw new IOException("ImageIO returned null: " + fullPath);
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load tile: " + fullPath, e);
        }
    }


    public void setupAnimated(int index, String[] imagePaths, boolean collision, int frameSpeed) {
        UtilityTool uTool = new UtilityTool();
        tile[index] = new Tile();
        tile[index].collision  = collision;
        tile[index].animated   = true;
        tile[index].frameCount = imagePaths.length;
        tile[index].frameSpeed = frameSpeed;
        tile[index].frames     = new BufferedImage[imagePaths.length];

        for (int i = 0; i < imagePaths.length; i++) {
            String fullPath = "/tiles/" + imagePaths[i] + ".png";
            String altPath  = "tiles/"  + imagePaths[i] + ".png";
            try (InputStream is = getClass().getResourceAsStream(fullPath) != null
                    ? getClass().getResourceAsStream(fullPath) : Thread.currentThread().getContextClassLoader().getResourceAsStream(altPath)) {
                        if (is == null) throw new IllegalArgumentException("Resource not found: " + fullPath);
                        BufferedImage img = ImageIO.read(is);
                        if (img == null) throw new IOException("ImageIO returned null: " + fullPath);
                        tile[index].frames[i] = uTool.scaleImage(img, gp.tileSize, gp.tileSize);
                    } catch (IOException e) {
                throw new RuntimeException("Failed to load animated tile: " + fullPath, e);
            }
        }
        // image punta al primo frame come fallback
        tile[index].image = tile[index].frames[0];
    }


    public void getTileImage() {
        setupAnimated(0,  new String[]{"grass_an1", "grass_an2", "grass_an3"}, false, 45);
        setup(1,  "wall",                 true);
        setupAnimated(2, new String[]{"water_an1", "water_an2"},true, 30);
        setup(3,  "earth",                false);
        setup(4,  "tree",                 true);
        setup(5,  "sand",                 false);
        setupAnimated(6,  new String[]{"water_rightUp_an1", "water_rightUp_an2"},true, 30);
        setupAnimated(7,  new String[]{"water_rightDown_an1", "water_rightDown_an2"},true, 30);
        setupAnimated(8,  new String[]{"water_leftUp_an1", "water_leftUp_an2"}, true, 30);
        setupAnimated(9,  new String[]{"water_leftDown_an1", "water_leftDown_an2"}, true, 30);
        setupAnimated(10, new String[]{"water_centerRight_an1", "water_centerRight_an2"}, true, 30);
        setupAnimated(11, new String[]{"water_centerLeft_an1", "water_centerLeft_an2"}, true, 30);
        setupAnimated(12, new String[]{"water_centerUp_an1", "water_centerUp_an2"}, true, 30);
        setupAnimated(13, new String[]{"water_centerDown_an1", "water_centerDown_an2"}, true, 30);
        setup(14, "water_island_rightUp", true);
        setup(15, "water_island_rightDown",true);
        setup(16, "water_island_leftUp",  true);
        setup(17, "water_island_leftDown",true);
        setup(18, "sand_rightUp",         false);
        setup(19, "sand_rightDown",       false);
        setup(20, "sand_leftUp",          false);
        setup(21, "sand_leftDown",        false);
        setup(22, "sand_centerRight",     false);
        setup(23, "sand_centerLeft",      false);
        setup(24, "sand_centerUp",        false);
        setup(25, "sand_centerDown",      false);

        // setupAnimated(2, new String[]{"water_1","water_2","water_3"}, true, 15);
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0, row = 0;
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                while (col < gp.maxWorldCol) {
                    String[] numbers = line.split(" ");
                    mapTileNum[col][row] = Integer.parseInt(numbers[col]);
                    col++;
                }
                if (col == gp.maxWorldCol) { col = 0; row++; }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        // Avanza il contatore animazione ad ogni frame
        animationTick++;

        int worldCol = 0, worldRow = 0;
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX  = worldCol * gp.tileSize;
            int worldY  = worldRow * gp.tileSize;
            double screenX = worldX - gp.player.worldX + gp.player.screenX;
            double screenY = worldY - gp.player.worldY + gp.player.screenY;

            // Frustum culling — disegna solo le tile visibili
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                Tile t = tile[tileNum];
                BufferedImage img;

                if (t.animated && t.frames != null && t.frameCount > 0) {
                    // Sceglie il frame corrente in base al tick globale
                    int frameIndex = (animationTick / t.frameSpeed) % t.frameCount;
                    img = t.frames[frameIndex];
                } else {
                    img = t.image;
                }

                g2.drawImage(img, (int) screenX, (int) screenY, null);
            }

            worldCol++;
            if (worldCol == gp.maxWorldCol) { worldCol = 0; worldRow++; }
        }
    }
}
