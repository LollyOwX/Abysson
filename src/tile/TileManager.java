package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;
    public NoiseField.Height[][] mapHeight;
    static final int SPECIAL_TILE_BASE = 100;
    private static final SpecialTile[] SPECIALS = SpecialTile.values();
    private final UtilityTool uTool = new UtilityTool();
    private int animationTick = 0;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[300];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        mapHeight = new NoiseField.Height[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/world01.txt");
    }

    // ── Caricamento immagini ──────────────────────────────────

    private BufferedImage loadImage(String imagePath) {
        String fullPath = "/tiles/" + imagePath + ".png";
        InputStream found = getClass().getResourceAsStream(fullPath);
        if (found == null) found = Thread.currentThread().getContextClassLoader().getResourceAsStream("tiles/" + imagePath + ".png");
        if (found == null) throw new IllegalArgumentException("Resource not found: " + fullPath);
        try (InputStream is = found) {
            BufferedImage img = ImageIO.read(is);
            if (img == null) throw new IOException("ImageIO returned null: " + fullPath);
            return uTool.scaleImage(img, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load tile: " + fullPath, e);
        }
    }

    public void setup(int index, String imagePath, boolean collision) {
        Tile t = new Tile();
        t.image = loadImage(imagePath);
        t.collision = collision;
        tile[index] = t;
    }

    public void setupAnimated(int index, String[] imagePaths, boolean collision, int frameSpeed) {
        Tile t = new Tile();
        t.collision  = collision;
        t.animated   = true;
        t.frameCount = imagePaths.length;
        t.frameSpeed = frameSpeed;
        t.frames     = new BufferedImage[imagePaths.length];
        for (int i = 0; i < imagePaths.length; i++) t.frames[i] = loadImage(imagePaths[i]);
        t.image = t.frames[0];
        tile[index] = t;
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

        Arrays.fill(tile, SPECIAL_TILE_BASE, tile.length, null);
        loadSpecialTilesUsedInMap();
    }

    // ── Caricamento mappa ─────────────────────────────────────

    public void loadMap(String filePath) {
        for (int[] column : mapTileNum) Arrays.fill(column, 0);
        long h = SeedCreator.START;
        int cellsRead = 0;
        int row = 0, col = 0;
        try (InputStream is = getClass().getResourceAsStream(filePath)) {
            if (is == null) throw new IOException("mappa non trovata");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            for (row = 0; row < gp.maxWorldRow; row++) {
                String line = br.readLine();
                if (line == null) break;
                String[] numbers = line.trim().split("\\s+");
                for (col = 0; col < gp.maxWorldCol && col < numbers.length; col++) {
                    int id = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = id;
                    h = SeedCreator.mix(h, id);
                    cellsRead++;
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("ERROR: " + filePath + " (riga " + (row + 1) + ", colonna " + (col + 1) + "): " + e.getMessage());
        }
        int cells = gp.maxWorldCol * gp.maxWorldRow;
        if (cellsRead < cells) System.err.println("WARNING: " + filePath + " ha " + cellsRead + " celle su " + cells + ", le altre restano id 0");

        NoiseField field = new NoiseField(SeedCreator.finish(h, cellsRead), 12.0, 4, 0.5, 2.0, -0.30, 0.30);
        for (int c = 0; c < gp.maxWorldCol; c++) for (int r = 0; r < gp.maxWorldRow; r++) mapHeight[c][r] = field.height(c, r);
        loadSpecialTilesUsedInMap();
    }

    private void loadSpecialTilesUsedInMap() {
        for (int col = 0; col < gp.maxWorldCol; col++) {
            for (int row = 0; row < gp.maxWorldRow; row++) {
                int id = mapTileNum[col][row];
                if (id >= 0 && id < tile.length && tile[id] != null) continue;
                int ordinal = id - SPECIAL_TILE_BASE;
                if (id >= SPECIAL_TILE_BASE && id < tile.length && ordinal < SPECIALS.length) {
                    setup(id, SPECIALS[ordinal].name(), SPECIALS[ordinal].collision);
                    continue;
                }
                System.err.println("ERROR: tile " + id + " in (" + col + "," + row + ") non esiste, sostituita con 0");
                mapTileNum[col][row] = 0;
            }
        }
    }

    public SpecialTile getSpecialTileAt(int col, int row) {
        if (col < 0 || row < 0 || col >= gp.maxWorldCol || row >= gp.maxWorldRow) return null;
        int ordinal = mapTileNum[col][row] - SPECIAL_TILE_BASE;
        return ordinal >= 0 && ordinal < SPECIALS.length ? SPECIALS[ordinal] : null;
    }

    // ── Disegno ───────────────────────────────────────────────

    public void draw(Graphics2D g2) {
        animationTick++;
        int ts = gp.tileSize;
        double leftX   = gp.player.worldX - gp.player.screenX;
        double topY    = gp.player.worldY - gp.player.screenY;
        double rightX  = gp.player.worldX + gp.player.screenX;
        double bottomY = gp.player.worldY + gp.player.screenY;
        int firstCol = Math.max(0, (int) Math.floor(leftX / ts));
        int lastCol  = Math.min(gp.maxWorldCol - 1, (int) Math.ceil(rightX / ts));
        int firstRow = Math.max(0, (int) Math.floor(topY / ts));
        int lastRow  = Math.min(gp.maxWorldRow - 1, (int) Math.ceil(bottomY / ts));

        for (int row = firstRow; row <= lastRow; row++) {
            for (int col = firstCol; col <= lastCol; col++) {
                Tile t = tile[mapTileNum[col][row]];
                BufferedImage img = t.image;
                if (t.animated && t.frames != null && t.frameCount > 0) {
                    img = t.frames[(animationTick / t.frameSpeed) % t.frameCount];
                }
                int screenX = (int) (col * ts - gp.player.worldX + gp.player.screenX);
                int screenY = (int) (row * ts - gp.player.worldY + gp.player.screenY);
                g2.drawImage(img, screenX, screenY, null);
            }
        }
    }
}