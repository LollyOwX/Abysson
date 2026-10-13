package book;

import entity.Player;
import items.Consumable;
import items.Inventory;
import items.Item;
import items.ItemInfo;
import items.Precious;
import items.QuestItem;
import items.Resource;
import items.StatLine;
import main.Button;
import main.GameCalendar;
import main.GamePanel;
import main.PaletteSwap;
import main.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Contenuto delle pagine dell'area Inventario del libro. Un sottoargomento (bookzone) per zona:
 * 0 gear, 1 consumabili, 2 quest, 3 risorse, 4 preziosi (stesso ordine delle icone del bookmark).
 * Pagina sinistra = una riga per oggetto (ROWS_PER_PAGE per pagina, bookpage sceglie la pagina);
 * pagina destra = dettagli dell'oggetto selezionato con un click sulla riga.
 * Tutte le coordinate sono in pixel dell'immagine originale del libro (272x272), misurate sulle
 * immagini in res/ui/book_inventory.
 */
public class InventoryPage {
    public static final int ZONE_GEAR = 0, ZONE_CONSUMABLE = 1, ZONE_QUESTS = 2, ZONE_RESOURCES = 3, ZONE_PRECIOUS = 4;
    private static final String[] ZONE_FILE = {"gear", "consumable", "quests", "resources", "precious"};

    private static final String DIR = "/ui/book_inventory/";
    private static final int ROWS_PER_PAGE = 10, ROW_PITCH = 13;
    private static final int LIST_X = 31, LIST_Y = 106;
    // Colonne della riga (per zona): nome, cella A, cella B
    private static final int[] NAME_W = {54, 53, 55, 51, 51};
    private static final int[] A_X = {99, 98, 100, 96, 96}, A_W = {12, 14, 12, 14, 14};
    private static final int[] B_X = {114, 115, 115, 113, 113}, B_W = {11, 10, 10, 12, 12};

    private static final int STATS_X = 146, STATS_Y = 106, STATS_PITCH = 10, STATS_MAX = 13;
    private static final Rectangle DETAIL_BOX = new Rectangle(204, 104, 38, 38);
    private static final Color INK = new Color(30, 20, 10), CELL = new Color(242, 232, 166);

    private final GamePanel gp;
    private final UI ui;
    private final Button[] rowButtons = new Button[ROWS_PER_PAGE];
    private static final Map<String, BufferedImage> IMAGES = new HashMap<>();

    private Graphics2D g2;
    private int ox, oy;
    private double scale;
    private int selected = -1, lastZone = -2;

    public InventoryPage(GamePanel gp, UI ui) {
        this.gp = gp;
        this.ui = ui;
        for (int k = 0; k < ROWS_PER_PAGE; k++) {
            final int slot = k;
            rowButtons[k] = new Button(() -> selected = gp.bookpage * ROWS_PER_PAGE + slot);
        }
    }

    // ── Liste per zona ───────────────────────────────────────
    private static class Row {
        String name, iconPath, a, b, bIconPath;
        boolean marked;
    }

    private List<Item> gearItems() {
        Player p = gp.player;
        List<Item> all = new ArrayList<>(p.equippedItems());
        all.addAll(p.inventory.gear);
        return all;
    }

    private int size(int zone) {
        Inventory inv = gp.player.inventory;
        return switch (zone) {
            case ZONE_GEAR -> gearItems().size();
            case ZONE_CONSUMABLE -> inv.consumables.size();
            case ZONE_QUESTS -> inv.questItems.size();
            case ZONE_RESOURCES -> inv.resources.size();
            case ZONE_PRECIOUS -> inv.precious.size();
            default -> 0;
        };
    }

    public int pageCount() {
        return Math.max(1, (size(gp.bookzone) + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE);
    }

    private List<Row> rows(int zone) {
        Inventory inv = gp.player.inventory;
        List<Row> out = new ArrayList<>();
        switch (zone) {
            case ZONE_GEAR -> {
                Set<Item> worn = new HashSet<>(gp.player.equippedItems());
                for (Item it : gearItems()) {
                    Row r = new Row();
                    r.name = (worn.contains(it) ? "*" : "") + it.name;
                    r.iconPath = it.itemPath;
                    r.a = ItemInfo.weightText(it);
                    r.b = String.valueOf(ItemInfo.durabilityPercent(it));
                    out.add(r);
                }
            }
            case ZONE_CONSUMABLE -> {
                for (Consumable c : inv.consumables) {
                    Row r = new Row();
                    r.name = c.name; r.iconPath = c.itemPath; r.a = compact(c.goldValue); r.b = quantity(c.quantity);
                    out.add(r);
                }
            }
            case ZONE_QUESTS -> {
                GameCalendar cal = gp.calendar;
                for (QuestItem q : inv.questItems) {
                    Row r = new Row();
                    r.name = q.name; r.iconPath = q.itemPath; r.bIconPath = q.kind.iconPath();
                    r.a = (q.mustDeliver && q.deliverDay > 0) ? String.valueOf(Math.max(0, q.deliverDay - cal.day)) : "";
                    out.add(r);
                }
            }
            case ZONE_RESOURCES -> {
                for (Resource rs : inv.resources) {
                    Row r = new Row();
                    r.name = rs.name; r.iconPath = rs.itemPath; r.a = compact(rs.value); r.b = quantity(rs.quantity);
                    out.add(r);
                }
            }
            case ZONE_PRECIOUS -> {
                for (Precious pr : inv.precious) {
                    Row r = new Row();
                    r.name = pr.name; r.iconPath = pr.itemPath; r.a = compact(pr.currentValue); r.b = quantity(pr.quantity);
                    out.add(r);
                }
            }
            default -> { }
        }
        return out;
    }

    // ── Disegno ──────────────────────────────────────────────
    public void draw(Graphics2D g2, int ox, int oy, double scale) {
        int zone = gp.bookzone;
        if (zone < 0 || zone > ZONE_PRECIOUS) {
            clearButtons();
            return;
        }
        this.g2 = g2;
        this.ox = ox;
        this.oy = oy;
        this.scale = scale;
        if (zone != lastZone) {
            selected = -1;
            lastZone = zone;
        }
        Object oldInterp = g2.getRenderingHint(RenderingHints.KEY_INTERPOLATION);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        List<Row> rows = rows(zone);
        drawList(zone, rows);
        if (selected >= 0 && selected < rows.size()) drawDetails(zone, selected);

        if (oldInterp != null) g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, oldInterp);
    }

    private void clearButtons() {
        for (Button b : rowButtons) b.bounds = null;
    }

    private void drawList(int zone, List<Row> rows) {
        BufferedImage strip = image(DIR + "list_inventory_" + ZONE_FILE[zone] + ".png");
        int from = gp.bookpage * ROWS_PER_PAGE;
        for (int k = 0; k < ROWS_PER_PAGE; k++) {
            int i = from + k;
            if (i >= rows.size()) {
                rowButtons[k].bounds = null;
                continue;
            }
            Row row = rows.get(i);
            int y = LIST_Y + k * ROW_PITCH;
            int stripW = strip != null ? strip.getWidth() : 94;
            Rectangle rowRect = rect(LIST_X, y, stripW, 8);
            if (strip != null) g2.drawImage(strip, rowRect.x, rowRect.y, rowRect.width, rowRect.height, null);

            drawImage(row.iconPath, rect(LIST_X, y, 8, 8));
            text(row.name, rect(42, y, NAME_W[zone], 8), -1, INK);
            if (row.a != null && !row.a.isEmpty()) text(row.a, rect(A_X[zone], y, A_W[zone], 8), 0, INK);
            if (row.b != null) text(row.b, rect(B_X[zone], y, B_W[zone], 8), 0, INK);
            if (row.bIconPath != null) drawImage(row.bIconPath, rect(B_X[zone], y - 1, 10, 10));

            if (i == selected) {
                g2.setColor(new Color(160, 40, 30));
                g2.drawRect(rowRect.x - 1, rowRect.y - 1, rowRect.width + 1, rowRect.height + 1);
            }
            rowButtons[k].setBounds(rowRect.x, rowRect.y, rowRect.width, rowRect.height);
        }
    }

    private void drawDetails(int zone, int index) {
        Inventory inv = gp.player.inventory;
        switch (zone) {
            case ZONE_GEAR -> {
                Item it = gearItems().get(index);
                drawStatRows(ItemInfo.statLines(it), "list_inventory_gear_stats.png");
                drawImage(ItemInfo.detailPath(it), DETAIL_BOX);
            }
            case ZONE_CONSUMABLE -> {
                Consumable c = inv.consumables.get(index);
                drawStatRows(c.gains, "list_inventory_consumable_stats.png");
                drawImage(c.detailPath != null ? c.detailPath : c.itemPath, DETAIL_BOX);
                drawEffects(c);
            }
            case ZONE_QUESTS -> drawQuest(inv.questItems.get(index));
            case ZONE_RESOURCES -> drawResource(inv.resources.get(index));
            case ZONE_PRECIOUS -> drawPrecious(inv.precious.get(index));
            default -> { }
        }
    }

    // Righe nome-valore a sinistra del bookmark (una riga per stat: flat, % e moltiplicatori separati)
    private void drawStatRows(List<StatLine> lines, String stripFile) {
        BufferedImage strip = image(DIR + stripFile);
        for (int k = 0; k < Math.min(lines.size(), STATS_MAX); k++) {
            int y = STATS_Y + k * STATS_PITCH;
            Rectangle r = rect(STATS_X, y, 39, 8);
            if (strip != null) g2.drawImage(strip, r.x, r.y, r.width, r.height, null);
            text(lines.get(k).label, rect(STATS_X, y, 24, 8), -1, INK);
            text(lines.get(k).valueText(), rect(172, y, 13, 8), 1, INK);
        }
    }

    private void drawEffects(Consumable c) {
        BufferedImage strip = image(DIR + "list_inventory_consumable_effects.png");
        for (int k = 0; k < Math.min(c.effects.size(), 9); k++) {
            int y = 162 + k * 9;
            Rectangle r = rect(144, y, 98, 7);
            if (strip != null) g2.drawImage(strip, r.x, r.y, r.width, r.height, null);
            drawImage(c.effects.get(k).iconPath, rect(145, y, 7, 7));
            text(c.effects.get(k).description, rect(155, y, 87, 7), -1, INK);
        }
    }

    private void drawPrecious(Precious p) {
        drawImage(p.detailPath != null ? p.detailPath : p.itemPath, DETAIL_BOX);
        text(compact(p.currentValue), rect(161, 97, 23, 8), 0, INK);
        text("+" + Math.round(p.reputationUp * 100) + "%", rect(161, 107, 23, 7), 0, INK);
        text("-" + Math.round(p.reputationDown * 100) + "%", rect(161, 116, 23, 7), 0, INK);

        BufferedImage strip = image(DIR + "list_inventory_precious_reputation.png");
        for (int k = 0; k < Math.min(p.cities.size(), 7); k++) {
            Precious.City city = p.cities.get(k);
            int y = 169 + k * 10;
            Rectangle r = rect(144, y, 98, 8);
            if (strip != null) {
                Map<Integer, Integer> map = new HashMap<>();
                map.put(0xFFFFFFFF, city.flagColor);
                BufferedImage flagged = PaletteSwap.getOrCreate("flag_" + Integer.toHexString(city.flagColor), strip, map);
                g2.drawImage(flagged, r.x, r.y, r.width, r.height, null);
            }
            Rectangle flag = rect(145, y + 1, 8, 6);
            text(city.initials(), flag, 0, luminance(city.flagColor) > 140 ? Color.BLACK : Color.WHITE);
            text(city.name, rect(157, y, 69, 8), -1, INK);
            text(compact(city.value), rect(228, y, 14, 8), 0, INK);
        }
    }

    private void drawQuest(QuestItem q) {
        GameCalendar cal = gp.calendar;
        drawImage(q.detailPath != null ? q.detailPath : q.itemPath, DETAIL_BOX);

        Rectangle label = rect(156, 98, 27, 9);
        g2.setColor(CELL);
        g2.fillRect(label.x, label.y, label.width, label.height);
        text(String.format("%02d/%02d", cal.day, cal.month), label, 0, INK);

        // Griglia del calendario: 6 colonne x 5 righe di segni 3x2, righe leggermente spostate (prospettiva)
        int[] rowX = {154, 154, 153, 152, 151};
        for (int day = 1; day <= GameCalendar.DAYS_PER_MONTH; day++) {
            Color c = null;
            if (day == cal.day) c = new Color(60, 190, 70);
            if (q.mustDeliver && day == q.deliverDay) c = q.exactDay ? new Color(215, 45, 40) : new Color(235, 200, 40);
            if (c == null) continue;
            int i = day - 1;
            Rectangle r = rect(rowX[i / 6] + (i % 6) * 5, 114 + (i / 6) * 5, 3, 2);
            g2.setColor(c);
            g2.fillRect(r.x, r.y, r.width, r.height);
        }

        drawImage(q.itemPath, rect(145, 156, 8, 8));
        Rectangle nameCell = rect(153, 156, 37, 8);
        clear(nameCell);
        text(q.name, nameCell, -1, INK);
        wrapped(q.description, rect(147, 168, 94, 71), 11f);
    }

    private void drawResource(Resource r) {
        drawImage(r.itemPath, rect(145, 96, 8, 8));
        Rectangle nameCell = rect(154, 98, 29, 6);
        clear(nameCell);
        text(r.name, nameCell, -1, INK);
        wrapped(r.description, rect(147, 108, 35, 34), 10f);

        drawImage(r.processExtra, rect(218, 93, 8, 8));
        drawImage(r.processPrimary, rect(202, 110, 8, 8));
        drawImage(r.processTool, rect(219, 110, 8, 8));
        drawImage(r.processRefined, rect(236, 110, 8, 8));

        for (int i = 0; i < Math.min(r.usages.size(), 80); i++) {
            drawImage(r.usages.get(i), rect(149 + (i % 10) * 9, 169 + (i / 10) * 9, 8, 8));
        }
    }

    // ── Input ────────────────────────────────────────────────
    public boolean handleClick(int x, int y) {
        for (Button b : rowButtons) {
            if (b.click(x, y)) return true;
        }
        return false;
    }

    // ── Helper ───────────────────────────────────────────────
    private Rectangle rect(int x, int y, int w, int h) {
        int x0 = ox + (int) Math.round(x * scale), y0 = oy + (int) Math.round(y * scale);
        int x1 = ox + (int) Math.round((x + w) * scale), y1 = oy + (int) Math.round((y + h) * scale);
        return new Rectangle(x0, y0, x1 - x0, y1 - y0);
    }

    /** align: -1 sinistra, 0 centro, 1 destra. Il corpo segue l'altezza della cella; ellissi se non entra. */
    private void text(String s, Rectangle cell, int align, Color color) {
        if (s == null || s.isEmpty()) return;
        float fs = Math.max(9f, cell.height * 0.82f);
        g2.setFont(ui.MaruMonica.deriveFont(Font.PLAIN, fs));
        FontMetrics fm = g2.getFontMetrics();
        String t = fit(fm, s, cell.width - 2);
        int w = fm.stringWidth(t);
        int x = align < 0 ? cell.x + 2 : (align > 0 ? cell.x + cell.width - w - 1 : cell.x + (cell.width - w) / 2);
        int y = cell.y + (cell.height + fm.getAscent() - fm.getDescent()) / 2;
        g2.setColor(color);
        g2.drawString(t, x, y);
    }

    // Copre il testo segnaposto disegnato nell'immagine di sfondo prima di scrivere il testo vero
    private void clear(Rectangle cell) {
        g2.setColor(CELL);
        g2.fillRect(cell.x, cell.y, cell.width, cell.height);
    }

    private void wrapped(String s, Rectangle box, float size) {
        if (s == null || s.isEmpty()) return;
        g2.setFont(ui.MaruMonica.deriveFont(Font.PLAIN, size));
        FontMetrics fm = g2.getFontMetrics();
        g2.setColor(INK);
        int y = box.y + fm.getAscent();
        StringBuilder line = new StringBuilder();
        for (String word : s.split(" ")) {
            String trial = line.length() == 0 ? word : line + " " + word;
            if (fm.stringWidth(trial) > box.width && line.length() > 0) {
                g2.drawString(line.toString(), box.x, y);
                y += fm.getHeight();
                if (y > box.y + box.height) return;
                line = new StringBuilder(word);
            } else {
                line = new StringBuilder(trial);
            }
        }
        if (line.length() > 0 && y <= box.y + box.height) g2.drawString(line.toString(), box.x, y);
    }

    private static String fit(FontMetrics fm, String s, int maxW) {
        if (fm.stringWidth(s) <= maxW) return s;
        while (s.length() > 1 && fm.stringWidth(s + "…") > maxW) s = s.substring(0, s.length() - 1);
        return s + "…";
    }

    private void drawImage(String path, Rectangle box) {
        BufferedImage img = image(path);
        if (img == null) return;
        double k = Math.min((double) box.width / img.getWidth(), (double) box.height / img.getHeight());
        int w = (int) Math.round(img.getWidth() * k), h = (int) Math.round(img.getHeight() * k);
        g2.drawImage(img, box.x + (box.width - w) / 2, box.y + (box.height - h) / 2, w, h, null);
    }

    private static BufferedImage image(String path) {
        if (path == null) return null;
        if (IMAGES.containsKey(path)) return IMAGES.get(path);
        BufferedImage img = null;
        try (java.io.InputStream is = InventoryPage.class.getResourceAsStream(path)) {
            if (is != null) img = javax.imageio.ImageIO.read(is);
            else System.err.println("ERROR: resource not found: " + path);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        IMAGES.put(path, img);
        return img;
    }

    private static String compact(int v) {
        return v >= 10000 ? (v / 1000) + "k" : (v >= 1000 ? String.format("%.1fk", v / 1000.0) : String.valueOf(v));
    }

    private static String quantity(int q) {
        return q > 99 ? "99+" : String.valueOf(q);
    }

    private static int luminance(int argb) {
        return (int) (0.299 * ((argb >> 16) & 255) + 0.587 * ((argb >> 8) & 255) + 0.114 * (argb & 255));
    }
}
