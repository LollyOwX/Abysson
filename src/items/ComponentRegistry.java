package items;

import entity.StatType;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Risolve i component di Weapon/Armor: nomecomponent[slot] = ID -> bonus. Ogni tabella è
 * indicizzata per FAMIGLIA (mischia, distanza, scudo, armatura) e per COMPONENT: l'ID 4 di
 * POMO non ha niente a che fare con l'ID 4 di CORDA, né con l'ID 4 di POMO in un'altra
 * famiglia. Un component senza tabella per quella famiglia (o con ID 0) non produce nulla.
 *
 * Il bonus di un ID è un Entry: nome mostrato in gioco, StatType (null = effetto non-stat,
 * vedi Part.GUARDIA/MANICO: resistenza al disarmo) e valore. I valori di partenza sono quelli
 * delle vecchie tabelle di MaterialRegistry; i nomi sono segnaposto ("Punta 3") da sostituire
 * con rename().
 */
public final class ComponentRegistry {

    public enum Family { MELEE, RANGED, SHIELD, ARMOR }

    public enum Part {
        POMO("Pomo"), MANICO("Manico"), GUARDIA("Guardia"), LAMA("Lama"), PUNTA("Punta"),
        CORDA("Corda"), LEGAMENTI("Legamenti"),
        METALLI("Metalli"), SOSTEGNO("Sostegno");

        public final String label;
        Part(String label) { this.label = label; }
    }

    public static final class Entry {
        public final String name;
        public final StatType stat;
        public final int value;
        Entry(String name, StatType stat, int value) {
            this.name = name;
            this.stat = stat;
            this.value = value;
        }
    }

    private static final Map<Family, Map<Part, Map<Integer, Entry>>> TABLES = new EnumMap<>(Family.class);

    // ── Tabelle ─────────────────────────────────────────────
    static {
        int[] tier     = {2, 4, 6, 8, 10, 13, 16, 19, 22, 26, 30, 34, 38, 42}; // ID 1..14
        int[] tierHalf = {1, 2, 3, 4, 5, 6, 8, 10, 11, 13, 15, 17, 19, 21};    // ID 1..14
        int[] pomo     = {-6, -4, -2, 0, 2, 4, 6, 8};                          // ID -3..4
        int[] identity = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};   // ID 0..14

        // Mischia (SPADE/MAZZE/LANCE + Coltello da Lancio)
        table(Family.MELEE, Part.PUNTA,   StatType.ATTACK,   1, tier);
        table(Family.MELEE, Part.POMO,    StatType.VELOCITA, -3, pomo);
        table(Family.MELEE, Part.GUARDIA, null,              0, identity);
        table(Family.MELEE, Part.MANICO,  null,              0, identity);

        // Distanza (ARCO_CORTO/ARCO_LUNGO/BALESTRA). struttura non ha tabella: struttura[0] è la
        // durabilità massima, valore diretto.
        table(Family.RANGED, Part.CORDA,     StatType.PRECISIONE, 1, tierHalf);
        table(Family.RANGED, Part.LEGAMENTI, StatType.ATTACK,     1, tier);

        // Scudi (SCUDO/BROQUEL/SAI)
        table(Family.SHIELD, Part.METALLI, StatType.DIFESA, 1, tier);

        // Armature
        table(Family.ARMOR, Part.METALLI,   StatType.DIFESA,   1, tier);
        table(Family.ARMOR, Part.LEGAMENTI, StatType.VELOCITA, 1, tierHalf);

        // ── Nomi mostrati in gioco ──────────────────────────
        rename(Family.MELEE, Part.MANICO, 3, "Manico rifinito");
    }

    private static void table(Family f, Part p, StatType stat, int firstId, int... values) {
        Map<Integer, Entry> ids = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            int id = firstId + i;
            ids.put(id, new Entry(p.label + " " + id, stat, values[i]));
        }
        TABLES.computeIfAbsent(f, k -> new EnumMap<>(Part.class)).put(p, ids);
    }

    private static void rename(Family f, Part p, int id, String name) {
        Entry old = TABLES.get(f).get(p).get(id);
        TABLES.get(f).get(p).put(id, new Entry(name, old.stat, old.value));
    }

    // ── Lookup ──────────────────────────────────────────────
    /** Entry dell'ID, o null se ID 0 / il component non ha tabella per questa famiglia. */
    public static Entry get(Family f, Part p, int id) {
        if (id == 0) return null;
        Map<Part, Map<Integer, Entry>> byPart = TABLES.get(f);
        if (byPart == null || !byPart.containsKey(p)) return null;
        Entry e = byPart.get(p).get(id);
        if (e == null) System.err.println("ComponentRegistry: ID sconosciuto " + id + " per " + f + "/" + p);
        return e;
    }

    /** Nome da mostrare in gioco ("Manico rifinito"), o null se non c'è un component. */
    public static String nameOf(Family f, Part p, int id) {
        Entry e = get(f, p, id);
        return e == null ? null : e.name;
    }
}
