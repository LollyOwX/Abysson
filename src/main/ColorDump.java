package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/** Edit configuration, then add paths divided by a space, nothing else (example:res/monsters/Goblin_downidle_1.png res/monsters/Goblin_down_1.png*/
public class ColorDump {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Uso: main.ColorDump <path1.png> [path2.png ...]");
            return;
        }
        for (String path : args) {
            dumpFile(path);
        }
    }

    static void dumpFile(String path) throws Exception {
        BufferedImage img = ImageIO.read(new File(path));
        if (img == null) {
            System.out.println("Impossibile leggere: " + path);
            return;
        }

        // LinkedHashMap solo per un ordine di inserimento stabile prima del sort per frequenza
        Map<Integer, Integer> counts = new LinkedHashMap<>();
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int argb = img.getRGB(x, y);
                counts.merge(argb, 1, Integer::sum);
            }
        }

        System.out.println("── " + path + "  (" + img.getWidth() + "x" + img.getHeight() + ", "
                + counts.size() + " colori unici) ──");

        counts.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .forEach(e -> {
                    int argb = e.getKey();
                    int a = (argb >>> 24) & 0xFF;
                    int r = (argb >>> 16) & 0xFF;
                    int g = (argb >>> 8) & 0xFF;
                    int b = argb & 0xFF;
                    String hex = String.format("%02X%02X%02X", r, g, b);
                    System.out.printf("  %s  (a=%3d)  %6d px%n", hex, a, e.getValue());
                });
        System.out.println();
    }
}