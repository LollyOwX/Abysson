package tile;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;       // frame statico (usato se animated = false)
    public boolean collision = false;

    // Animazione
    public boolean animated = false;
    public BufferedImage[] frames;    // frame dell'animazione
    public int frameCount = 0;        // numero di frame
    public int frameSpeed = 15;       // quanti tick per frame (più alto = più lento)
}
