package tile;
import main.GamePanel;
import java.util.Random;

public class NoiseMapGenerator extends GamePanel {

    public static void NoiseMapGenerator(GamePanel gp) {
        // Noise configuration parameters
        double scale = 12.7;      // Frequency zoom (higher = more zoomed out)
        int octaves = 3;          // Number of detail layers
        double persistence = 0.5; // Amplitude decrease per octave
        double lacunarity = 2.0;  // Frequency increase per octave
        long seed = 123456L;      // Random seed for reproducibility
        double OFFSET = 0.37;

        PerlinNoise noiseGen = new PerlinNoise(seed);

        double minNoise = Double.MAX_VALUE;
        double maxNoise = Double.MIN_VALUE;

        // Generate noise values for each pixel
        double[][] noiseValues = new double[gp.maxWorldCol][gp.maxWorldRow];

        for (int x = 0; x < gp.maxWorldCol; x++) {
            for (int y = 0; y < gp.maxWorldRow; y++) {
                double amplitude = 1.0;
                double frequency = 1.0;
                double noiseHeight = 0.0;

                // Combine octaves (Fractional Brownian Motion)
                for (int i = 0; i < octaves; i++) {
                    double sampleX = ((x + OFFSET) / scale) * frequency;
                    double sampleY = ((y + OFFSET) / scale) * frequency;

                    double perlinValue = noiseGen.noise(sampleX, sampleY);
                    noiseHeight += perlinValue * amplitude;

                    amplitude *= persistence;
                    frequency *= lacunarity;
                }

                if (noiseHeight < minNoise) minNoise = noiseHeight;
                if (noiseHeight > maxNoise) maxNoise = noiseHeight;

                noiseValues[x][y] = noiseHeight;
            }
        }

        // Normalize values to [0, 1] and render grayscale pixels
        for (int x = 0; x < gp.maxWorldCol; x++) {
            for (int y = 0; y < gp.maxWorldRow; y++) {
                // Linear interpolation to normalize noise range to 0.0 - 1.0
                double normalized = (noiseValues[x][y] - minNoise) / (maxNoise - minNoise);

                int gray = (int) (normalized * 255);
                gray = Math.clamp(gray, 0, 255); // Clamp value

            }
        }

    }
}

/**
 * Standard implementation of 2D Perlin Noise using permutation tables.
 */
class PerlinNoise {
    private final int[] p = new int[512];

    public PerlinNoise(long seed) {
        int[] permutation = new int[256];
        for (int i = 0; i < 256; i++) {
            permutation[i] = i;
        }

        // Shuffle array using seed
        Random rand = new Random(seed);
        for (int i = 255; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = permutation[i];
            permutation[i] = permutation[j];
            permutation[j] = temp;
        }

        // Duplicate permutation table to avoid buffer overflow during indexing
        for (int i = 0; i < 512; i++) {
            p[i] = permutation[i % 256];
        }
    }

    public double noise(double x, double y) {
        // Find unit grid cell containing point
        int X = (int) Math.floor(x) & 255;
        int Y = (int) Math.floor(y) & 255;

        // Get relative X, Y within cell
        x -= Math.floor(x);
        y -= Math.floor(y);

        // Compute fade curves for X and Y
        double u = fade(x);
        double v = fade(y);

        // Hash coordinates of 4 corners
        int A = p[X] + Y;
        int B = p[X + 1] + Y;

        // Add blended results from 4 corners of rectangle
        return lerp(v,
                lerp(u, grad(p[A], x, y), grad(p[B], x - 1, y)),
                lerp(u, grad(p[A + 1], x, y - 1), grad(p[B + 1], x - 1, y - 1)));
    }

    private static double fade(double t) {
        // 6t^5 - 15t^4 + 10t^3 (Improved Perlin Noise fade curve)
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    private static double grad(int hash, double x, double y) {
        // Convert lower 3 bits of hash code into 8 gradient directions
        int h = hash & 7;
        double u = h < 4 ? x : y;
        double v = h < 4 ? y : x;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }
}