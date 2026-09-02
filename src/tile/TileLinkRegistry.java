package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Legge /maps/tile_links.txt — una riga per collegamento, formato:
 *   world01,col16,row17 link world01,col15,row20
 *   world03,col19,row23 link world04,col22,row25
 * (la virgola tra col e row è obbligatoria su ENTRAMBI i lati — una riga senza va in errore
 * chiaro in console, non viene ignorata silenziosamente).
 *
 * Ogni riga viene registrata in ENTRAMBI i sensi: il link funziona andata e ritorno anche se nel
 * file è scritto una volta sola. Righe vuote o che iniziano con # vengono ignorate.
 *
 * Caricato pigro alla prima get(), non ad ogni chiamata.
 */
public class TileLinkRegistry {
    private static final Map<String, TileLink> links = new HashMap<>();
    private static boolean loaded = false;

    public static TileLink get(String world, int col, int row) {
        if (!loaded) load();
        return links.get(key(world, col, row));
    }

    private static String key(String world, int col, int row) {
        return world + "," + col + "," + row;
    }

    private static void load() {
        loaded = true;
        try (InputStream is = TileLinkRegistry.class.getResourceAsStream("/maps/tile_links.txt")) {
            if (is == null) {
                System.err.println("ERROR: resource not found: /maps/tile_links.txt");
                return;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] halves = line.split("\\s+link\\s+");
                if (halves.length != 2) {
                    System.err.println("ERROR: riga link malformata (manca ' link '): " + line);
                    continue;
                }
                TileLink parsed = parse(halves[0].trim(), halves[1].trim());
                if (parsed == null) {
                    System.err.println("ERROR: riga link malformata: " + line);
                    continue;
                }
                links.put(key(parsed.fromWorld, parsed.fromCol, parsed.fromRow), parsed);
                links.put(key(parsed.toWorld, parsed.toCol, parsed.toRow),
                        new TileLink(parsed.toWorld, parsed.toCol, parsed.toRow,
                                parsed.fromWorld, parsed.fromCol, parsed.fromRow));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static TileLink parse(String from, String to) {
        String[] fromParts = from.split(",");
        String[] toParts   = to.split(",");
        if (fromParts.length != 3 || toParts.length != 3) return null;
        try {
            int fromCol = Integer.parseInt(fromParts[1].replace("col", "").trim());
            int fromRow = Integer.parseInt(fromParts[2].replace("row", "").trim());
            int toCol   = Integer.parseInt(toParts[1].replace("col", "").trim());
            int toRow   = Integer.parseInt(toParts[2].replace("row", "").trim());
            return new TileLink(fromParts[0], fromCol, fromRow, toParts[0], toCol, toRow);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
