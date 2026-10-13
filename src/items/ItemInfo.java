package items;

import java.util.ArrayList;
import java.util.List;

/** Informazioni di un Item pronte per la UI dell'inventario. */
public final class ItemInfo {
    private ItemInfo() {}

    /** Righe di stat: prima i valori flat, poi le percentuali, poi i moltiplicatori. */
    public static List<StatLine> statLines(Item item) {
        List<StatLine> lines = new ArrayList<>();
        if (item instanceof Weapon w && w.usesWeightDamage()) {
            lines.add(new StatLine("DMG", StatLine.Kind.FLAT, w.punta[0]));
        }
        item.statBonusPercent.forEach((t, v) -> {
            if (v != 0) lines.add(new StatLine(t, StatLine.Kind.PERCENT, v));
        });
        item.statMultiplier.forEach((t, v) -> {
            if (v != 1.0) lines.add(new StatLine(t, StatLine.Kind.MULTIPLIER, v));
        });
        return lines;
    }

    public static int durabilityPercent(Item item) {
        if (item.maxDurability <= 0) return 0;
        if (item.durability < 0) return 100;
        return (int) Math.round(item.durability * 100.0 / item.maxDurability);
    }

    /** Peso mostrato in lista: nelle MAZZE è il peso dell'arma (punta[0]); per gli altri Item.weight, "-" se non impostato. */
    public static String weightText(Item item) {
        if (item instanceof Weapon w && w.usesWeightDamage()) return String.valueOf(w.punta[0]);
        return item.weight > 0 ? String.valueOf(Math.round(item.weight)) : "-";
    }

    /** Immagine dettagliata: quella usata in combattimento per le armi, altrimenti l'icona. */
    public static String detailPath(Item item) {
        if (item instanceof Weapon w && w.combatItemPath != null) return w.combatItemPath;
        return item.itemPath;
    }
}
