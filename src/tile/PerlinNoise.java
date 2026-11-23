package tile;

import java.util.Random;

public class PerlinNoise {
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