package items;

import entity.StatType;

/** Una riga di statistica per la UI: nome abbreviato + valore. Flat, % e moltiplicatori sono righe separate. */
public class StatLine {
    public enum Kind { FLAT, PERCENT, MULTIPLIER }

    public final String label;
    public final Kind kind;
    public final double value;

    public StatLine(String label, Kind kind, double value) {
        this.label = label;
        this.kind = kind;
        this.value = value;
    }

    public StatLine(StatType type, Kind kind, double value) {
        this(abbreviation(type), kind, value);
    }

    public String valueText() {
        return switch (kind) {
            case FLAT -> (value >= 0 ? "+" : "") + Math.round(value);
            case PERCENT -> (value >= 0 ? "+" : "") + Math.round(value) + "%";
            case MULTIPLIER -> "x" + (Math.round(value * 100) / 100.0);
        };
    }

    public static String abbreviation(StatType t) {
        return switch (t) {
            case VITA -> "VIT"; case ATTACK -> "ATK"; case DIFESA -> "DEF"; case VELOCITA -> "SPD";
            case ELUSIONE -> "EVA"; case PRECISIONE -> "PRE"; case EFFICIENZA -> "EFF";
            default -> t.name().replace('_', '-');
        };
    }
}
