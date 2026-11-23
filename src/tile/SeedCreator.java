package tile;

public class SeedCreator {
    public static final long START = 1469598103934665603L;

    public static long mix(long h, int v) {
        h ^= v + 0x9E3779B97F4A7C15L + (h << 6) + (h >>> 2);
        h *= 0xFF51AFD7ED558CCDL;
        return h ^ (h >>> 32);
    }

    public static long finish(long h, int cellsRead) {
        return mix(h, cellsRead);
    }
}