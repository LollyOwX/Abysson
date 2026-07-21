package main;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GifPlayer {

    private BufferedImage[] frames = new BufferedImage[0];
    private int[] frameDelaysMs = new int[0];
    private int currentFrame = 0;
    private int elapsedMs = 0;
    private boolean loop = false;
    private boolean finished = false;

    private static final int MS_PER_TICK = 1000 / 60; // coerente col game loop a 60 FPS
    private static final int DEFAULT_DELAY_MS = 100;   // fallback se un frame non specifica un delay

    public void load(String classpathResource) {
        load(classpathResource, false);
    }

    public void load(String classpathResource, boolean loop) {
        this.loop = loop;
        currentFrame = 0;
        elapsedMs = 0;
        finished = false;

        String path = classpathResource.startsWith("/") ? classpathResource : "/" + classpathResource;
        InputStream is = getClass().getResourceAsStream(path);
        if (is == null) {
            System.err.println("ERROR: cinematic non trovata: " + path);
            frames = new BufferedImage[0];
            frameDelaysMs = new int[0];
            return;
        }

        try {
            decodeGif(is);
        } catch (IOException e) {
            System.err.println("ERROR: impossibile decodificare la cinematic: " + path);
            e.printStackTrace();
            frames = new BufferedImage[0];
            frameDelaysMs = new int[0];
        }
    }

    private void decodeGif(InputStream is) throws IOException {
        Iterator<ImageReader> readers = ImageIO.getImageReadersBySuffix("gif");
        if (!readers.hasNext()) throw new IOException("Nessun reader GIF disponibile in questa JVM");
        ImageReader reader = readers.next();

        ImageInputStream iis = ImageIO.createImageInputStream(is);
        reader.setInput(iis, false);
        int count = reader.getNumImages(true);

        int canvasW = 0, canvasH = 0;
        IIOMetadata streamMetadata = reader.getStreamMetadata();
        if (streamMetadata != null) {
            IIOMetadataNode streamRoot = (IIOMetadataNode) streamMetadata.getAsTree("javax_imageio_gif_stream_1.0");
            IIOMetadataNode lsd = getChild(streamRoot, "LogicalScreenDescriptor");
            if (lsd != null) {
                canvasW = Integer.parseInt(lsd.getAttribute("logicalScreenWidth"));
                canvasH = Integer.parseInt(lsd.getAttribute("logicalScreenHeight"));
            }
        }

        List<BufferedImage> frameList = new ArrayList<>();
        List<Integer> delayList = new ArrayList<>();
        BufferedImage canvas = null;

        for (int i = 0; i < count; i++) {
            BufferedImage raw = reader.read(i);

            if (canvas == null) {
                int w = canvasW > 0 ? canvasW : raw.getWidth();
                int h = canvasH > 0 ? canvasH : raw.getHeight();
                canvas = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            }

            IIOMetadata metadata = reader.getImageMetadata(i);
            IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree("javax_imageio_gif_image_1.0");

            int left = 0, top = 0;
            IIOMetadataNode descriptor = getChild(root, "ImageDescriptor");
            if (descriptor != null) {
                left = Integer.parseInt(descriptor.getAttribute("imageLeftPosition"));
                top  = Integer.parseInt(descriptor.getAttribute("imageTopPosition"));
            }

            int delayCenti = 10;
            String disposal = "none";
            IIOMetadataNode gce = getChild(root, "GraphicControlExtension");
            if (gce != null) {
                String d = gce.getAttribute("delayTime");
                if (d != null && !d.isEmpty()) delayCenti = Integer.parseInt(d);
                String disp = gce.getAttribute("disposalMethod");
                if (disp != null && !disp.isEmpty()) disposal = disp;
            }

            // Snapshot pre-disegno, serve solo se questo frame ha disposal "restoreToPrevious"
            BufferedImage preDrawSnapshot = deepCopy(canvas);

            Graphics2D g = canvas.createGraphics();
            g.drawImage(raw, left, top, null);
            g.dispose();

            frameList.add(deepCopy(canvas));
            delayList.add(delayCenti * 10); // centisecondi -> millisecondi

            if (disposal.equals("restoreToBackgroundColor")) {
                Graphics2D gClear = canvas.createGraphics();
                gClear.setComposite(AlphaComposite.Clear);
                gClear.fillRect(left, top, raw.getWidth(), raw.getHeight());
                gClear.dispose();
            } else if (disposal.equals("restoreToPrevious")) {
                canvas = preDrawSnapshot;
            }
            // "none" / "doNotDispose" -> il canvas resta cumulativo per il frame successivo
        }

        reader.dispose();
        iis.close();

        frames = frameList.toArray(new BufferedImage[0]);
        frameDelaysMs = new int[delayList.size()];
        for (int i = 0; i < delayList.size(); i++) {
            int ms = delayList.get(i);
            frameDelaysMs[i] = ms > 0 ? ms : DEFAULT_DELAY_MS;
        }
    }

    private IIOMetadataNode getChild(IIOMetadataNode root, String name) {
        if (root == null) return null;
        for (int i = 0; i < root.getLength(); i++) {
            if (root.item(i).getNodeName().equalsIgnoreCase(name)) {
                return (IIOMetadataNode) root.item(i);
            }
        }
        return null;
    }

    private BufferedImage deepCopy(BufferedImage src) {
        BufferedImage copy = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = copy.createGraphics();
        g.drawImage(src, 0, 0, null);
        g.dispose();
        return copy;
    }


    public void update() {
        if (finished || frames.length == 0) return;
        elapsedMs += MS_PER_TICK;
        while (elapsedMs >= frameDelaysMs[currentFrame]) {
            elapsedMs -= frameDelaysMs[currentFrame];
            currentFrame++;
            if (currentFrame >= frames.length) {
                if (loop) {
                    currentFrame = 0;
                } else {
                    currentFrame = frames.length - 1;
                    finished = true;
                    break;
                }
            }
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames.length == 0 ? null : frames[currentFrame];
    }

    public boolean isFinished() {
        return finished;
    }
}