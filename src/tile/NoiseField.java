package tile;

public class NoiseField {
    private static final double OFFSET = 0.37;
    public enum Height { LOW, MEDIUM, HIGH }
    private final PerlinNoise perlin;
    private final double scale, persistence, lacunarity, lowLimit, highLimit;
    private final int octaves;

    public NoiseField(long seed, double scale, int octaves, double persistence, double lacunarity, double lowLimit, double highLimit) {
        this.perlin = new PerlinNoise(seed);
        this.scale = scale;
        this.octaves = octaves;
        this.persistence = persistence;
        this.lacunarity = lacunarity;
        this.lowLimit = lowLimit;
        this.highLimit = highLimit;
    }
    public double value(int x, int y) {
        for (int i = 0; i < octaves; i++) {
            noiseHeight += perlin.noise(((x + OFFSET) / scale) * frequency, ((y + OFFSET) / scale) * frequency) * amplitude;
            amplitude *= persistence;
            frequency *= lacunarity;
        }
        return noiseHeight;
    }
    public Height height(int x, int y) {
        double v = value(x, y);
        double lowLimit = -0.30;
        double highLimit = 0.30;
        if (v < lowLimit)  return Height.LOW;
        if (v > highLimit) return Height.HIGH;
        return Height.MEDIUM;
    }
}