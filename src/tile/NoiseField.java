package tile;

public class NoiseField {
    public enum Height { LOW, MEDIUM, HIGH }

    private final PerlinNoise perlin;
    // + scale, octaves, persistence, lacunarity, lowLimit, highLimit come campi final

    public NoiseField(long seed, double scale, int octaves,
                      double persistence, double lacunarity,
                      double lowLimit, double highLimit) {
        this.perlin = new PerlinNoise(seed);
    }
    public double value(int x, int y) {
        double amplitude = 1.0;
        double frequency = 1.0;
        double noiseHeight = 0.0;
        double scale = 12.7;
        double octaves = 3;
        double persistence = 0.5;
        double lacunarity = 2.0;
        for (int i = 0; i < octaves; i++) {
            noiseHeight += perlin.noise((x / scale) * frequency, (y / scale) * frequency) * amplitude;
            amplitude *= persistence;
            frequency *= lacunarity;
        }
        return noiseHeight;
    }
    public Height height(int x, int y) {
        double v = value(x, y);
        double lowLimit = 0.30;
        double highLimit = -0.30;
        if (v < lowLimit)  return Height.LOW;
        if (v > highLimit) return Height.HIGH;
        return Height.MEDIUM;
    }
}