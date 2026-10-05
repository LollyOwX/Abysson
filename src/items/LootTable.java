package items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Loot di fine combattimento: sceglie UN pezzo in base al livello del mostro. Gli ID dei
 * registry finiscono in uno "scaglione" (fascia di livello + rarità, es. 20_30_masterwork).
 * 1) Gruppo: 50% stesso livello (la fascia contiene il livello del mostro), 35% livello
 *    inferiore, 15% livello superiore — mai una fascia che parte oltre MAX_LEVELS_ABOVE livelli
 *    sopra il mostro. Un gruppo vuoto salta, gli altri si rinormalizzano.
 * 2) Scaglione nel gruppo: più è rara, meno è probabile (peso dimezzato per ogni gradino).
 * 3) Pezzo nello scaglione: equiprobabile (armi, scudi e armature insieme).
 * La lista degli ID viene da LootIndex, generato da generate_loot_index.py: va rigenerato
 * quando i registry cambiano.
 */
public final class LootTable {
    private LootTable() {}

    private static final double P_SAME = 0.50, P_LOWER = 0.35, P_HIGHER = 0.15;
    private static final int MAX_LEVELS_ABOVE = 10;
    private static final double RARITY_FACTOR = 0.5; // peso = RARITY_FACTOR ^ ordinal(rarità)

    private enum Kind { WEAPON, SHIELD, ARMOR }

    private static final class Scaglione {
        final int lo, hi;
        final Rarity rarity;
        final List<String> ids = new ArrayList<>();
        Scaglione(int lo, int hi, Rarity rarity) { this.lo = lo; this.hi = hi; this.rarity = rarity; }
        double weight() { return Math.pow(RARITY_FACTOR, rarity.ordinal()); }
    }

    private static final Pattern TAIL = Pattern.compile("_(\\d+)_(\\d+)_([a-z_]+)$");
    private static final List<Scaglione> SCAGLIONI = new ArrayList<>();
    private static final Map<String, Kind> KIND_OF = new HashMap<>();
    private static int topHi = 0;

    static {
        add(LootIndex.WEAPONS, Kind.WEAPON);
        add(LootIndex.SHIELDS, Kind.SHIELD);
        add(LootIndex.ARMORS, Kind.ARMOR);
    }

    private static void add(String[] ids, Kind kind) {
        for (String id : ids) {
            Matcher m = TAIL.matcher(id);
            if (!m.find()) continue;
            int lo = Integer.parseInt(m.group(1)), hi = Integer.parseInt(m.group(2));
            Rarity rarity = Rarity.valueOf(m.group(3).toUpperCase());
            Scaglione sc = null;
            for (Scaglione s : SCAGLIONI) if (s.lo == lo && s.hi == hi && s.rarity == rarity) { sc = s; break; }
            if (sc == null) { sc = new Scaglione(lo, hi, rarity); SCAGLIONI.add(sc); }
            sc.ids.add(id);
            KIND_OF.put(id, kind);
            topHi = Math.max(topHi, hi);
        }
    }

    /** Un pezzo casuale per un mostro di questo livello, o null se non ce ne sono. */
    public static Item roll(int monsterLevel, Random rng) {
        String id = rollId(monsterLevel, rng);
        return id == null ? null : create(id);
    }

    /** Come roll(), ma ritorna solo l'ID del pezzo scelto (utile per test e statistiche). */
    public static String rollId(int monsterLevel, Random rng) {
        List<Scaglione> same = new ArrayList<>(), lower = new ArrayList<>(), higher = new ArrayList<>();
        for (Scaglione s : SCAGLIONI) {
            if (s.lo > monsterLevel + MAX_LEVELS_ABOVE) continue;
            boolean contains = monsterLevel >= s.lo && (monsterLevel < s.hi || s.hi == topHi);
            if (contains) same.add(s);
            else if (s.hi <= monsterLevel) lower.add(s);
            else higher.add(s);
        }
        List<List<Scaglione>> groups = new ArrayList<>();
        List<Double> weights = new ArrayList<>();
        if (!same.isEmpty())   { groups.add(same);   weights.add(P_SAME); }
        if (!lower.isEmpty())  { groups.add(lower);  weights.add(P_LOWER); }
        if (!higher.isEmpty()) { groups.add(higher); weights.add(P_HIGHER); }
        if (groups.isEmpty()) return null;

        List<Scaglione> group = groups.get(pick(weights, rng));
        List<Double> scWeights = new ArrayList<>();
        for (Scaglione s : group) scWeights.add(s.weight());
        Scaglione sc = group.get(pick(scWeights, rng));

        return sc.ids.get(rng.nextInt(sc.ids.size()));
    }

    private static int pick(List<Double> weights, Random rng) {
        double total = 0;
        for (double w : weights) total += w;
        double r = rng.nextDouble() * total;
        for (int i = 0; i < weights.size(); i++) {
            r -= weights.get(i);
            if (r < 0) return i;
        }
        return weights.size() - 1;
    }

    /** Crea il pezzo dal suo ID, cercandolo nel registry giusto. */
    public static Item create(String id) {
        return switch (KIND_OF.get(id)) {
            case WEAPON -> WeaponRegistry.get(id);
            case SHIELD -> ShieldRegistry.get(id);
            case ARMOR  -> ArmorRegistry.get(id);
        };
    }
}
