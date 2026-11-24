package items;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

final class ItemTable {
    private final Map<String, Integer> columns = new HashMap<>();
    private final Map<String, String[]> rows = new LinkedHashMap<>();

    ItemTable(String path) {
        try (InputStream is = ItemTable.class.getResourceAsStream(path)) {
            if (is == null) throw new IOException("file non trovato");
            BufferedReader in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String[] header = in.readLine().split("\t", -1);
            for (int i = 0; i < header.length; i++) columns.put(header[i], i);
            String line;
            int lineNo = 1;
            while ((line = in.readLine()) != null) {
                lineNo++;
                if (line.isBlank()) continue;
                String[] cells = line.split("\t", -1);
                if (cells.length != header.length) throw new IOException("riga " + lineNo + ": " + cells.length + " colonne invece di " + header.length);
                if (rows.put(cells[0], cells) != null) throw new IOException("riga " + lineNo + ": id doppio " + cells[0]);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Tabella oggetti " + path + ": " + e.getMessage(), e);
        }
    }

    String[] ids() { return rows.keySet().toArray(new String[0]); }

    Row row(String id) {
        String[] cells = rows.get(id);
        return cells == null ? null : new Row(cells);
    }

    final class Row {
        private final String[] cells;

        private Row(String[] cells) { this.cells = cells; }

        private String cell(String column) {
            Integer i = columns.get(column);
            if (i == null) throw new IllegalArgumentException("colonna inesistente: " + column);
            return cells[i];
        }

        String str(String column) {
            String v = cell(column);
            return v.isEmpty() ? null : v;
        }

        int num(String column) { return Integer.parseInt(cell(column)); }

        <E extends Enum<E>> E enumOf(Class<E> type, String column) { return Enum.valueOf(type, cell(column)); }

        void into(int[] target, int slot, String column) {
            if (!cell(column).isEmpty()) target[slot] = num(column);
        }

        void fillCommon(Item item) {
            item.name = str("name");
            item.rarity = enumOf(Rarity.class, "rarity");
            item.synergyBonusStub = str("synergy");
            item.fullSetOrStyleStub = str("fullSet");
            item.mythicUniqueStub = str("mythic");
        }
    }
}
