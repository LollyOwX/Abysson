package combat;

/**
 * Formule di combattimento su stat nella scala 0..STAT_MAX. Tutte le costanti da tarare sono qui.
 * Le stat non vengono tagliate: nelle formule un valore sopra STAT_MAX conta come STAT_MAX.
 */
public final class CombatMath {
    private CombatMath() {}

    public static final int STAT_MAX  = 1000;
    public static final int MAX_LEVEL = 100;

    // ── Mitigazione danni ───────────────────────────────────
    // mitigazione = MAX_MITIGATION × DIF / (DIF + K), con K = K_BASE + K_PER_LEVEL × livello di
    // CHI ATTACCA: a parità di difesa, un attaccante di livello alto mitiga meno.
    public static final double MAX_MITIGATION = 0.90;
    public static final double K_BASE = 20;
    public static final double K_PER_LEVEL = 8;

    // ── Precisione ──────────────────────────────────────────
    // probabilità di colpire = precisione / precisione massima, con precisione massima =
    // PRECISION_BASE_MAX + EVASION_WEIGHT × elusione del bersaglio (l'elusione non toglie
    // precisione: alza il tetto). La probabilità di mancare viene poi moltiplicata per
    // MISS_MULTIPLIER (+25% relativo, non fisso), come ultimo passaggio.
    public static final double PRECISION_BASE_MAX = 100;
    public static final double EVASION_WEIGHT = 1.0;
    public static final double MISS_MULTIPLIER = 1.25;

    /** Quota di danno assorbita (0..MAX_MITIGATION) da 'defense' contro un attaccante di livello 'attackerLevel'. */
    public static double mitigation(int defense, int attackerLevel) {
        double d = clamp(defense, 0, STAT_MAX);
        double k = K_BASE + K_PER_LEVEL * clamp(attackerLevel, 1, MAX_LEVEL);
        return MAX_MITIGATION * d / (d + k);
    }

    /** Danno finale (minimo 1) dopo la mitigazione di 'defense'. */
    public static int mitigate(double rawDamage, int defense, int attackerLevel) {
        return (int) Math.max(1, Math.round(rawDamage * (1 - mitigation(defense, attackerLevel))));
    }

    /** Probabilità (0..1) di colpire con 'precision' un bersaglio con 'evasion'. */
    public static double hitChance(double precision, int evasion) {
        double max = PRECISION_BASE_MAX + EVASION_WEIGHT * clamp(evasion, 0, STAT_MAX);
        double hit = clamp(precision, 0, STAT_MAX) / max;
        hit = Math.min(1.0, hit);
        double miss = Math.min(1.0, (1 - hit) * MISS_MULTIPLIER);
        return 1 - miss;
    }

    private static double clamp(double v, double lo, double hi) { return Math.max(lo, Math.min(hi, v)); }
}