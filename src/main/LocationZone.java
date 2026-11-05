package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocationZone {
    private static final String PATH = "/maps/locations.txt";
    private static final Pattern LINE = Pattern.compile(
            "(\\w+)\\s*=\\s*(\\w+),col(\\d+),row(\\d+)(?:\\s*\\.\\.\\s*col(\\d+),row(\\d+))?(?:\\s+start\\s+(\\w+))?");

    public final String id;
    public final String world;
    public final int col1, row1, col2, row2;
    public final String startQuestId; // null = la zona notifica soltanto

    public LocationZone(String id, String world, int colA, int rowA, int colB, int rowB, String startQuestId) {
        this.id           = id;
        this.world        = world;
        this.col1         = Math.min(colA, colB);
        this.row1         = Math.min(rowA, rowB);
        this.col2         = Math.max(colA, colB);
        this.row2         = Math.max(rowA, rowB);
        this.startQuestId = startQuestId;
    }

    public boolean contains(String world, int col, int row) {
        return this.world.equals(world) && col >= col1 && col <= col2 && row >= row1 && row <= row2;
    }

    // ── Caricamento ──────────────────────────────────────────────

    public static List<LocationZone> load() {
        List<LocationZone> zones = new ArrayList<>();
        try (InputStream is = LocationZone.class.getResourceAsStream(PATH)) {
            if (is == null) {
                System.err.println("Zone non trovate: " + PATH);
                return zones;
            }
            parse(new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)), zones);
        } catch (IOException e) {
            System.err.println("Errore nel file zone " + PATH + ": " + e.getMessage());
        }
        return zones;
    }

    static void parse(BufferedReader in, List<LocationZone> zones) throws IOException {
        String raw;
        int lineNo = 0;
        while ((raw = in.readLine()) != null) {
            lineNo++;
            String line = raw.trim();
            if (line.isEmpty() || line.startsWith("#")) continue;
            Matcher m = LINE.matcher(line);
            if (!m.matches()) {
                System.err.println("ERROR: " + PATH + " riga " + lineNo + " malformata: " + line);
                continue;
            }
            int colA = Integer.parseInt(m.group(3));
            int rowA = Integer.parseInt(m.group(4));
            int colB = (m.group(5) != null) ? Integer.parseInt(m.group(5)) : colA;
            int rowB = (m.group(6) != null) ? Integer.parseInt(m.group(6)) : rowA;
            zones.add(new LocationZone(m.group(1), m.group(2), colA, rowA, colB, rowB, m.group(7)));
        }
    }
}
