package net.sevenstars.middleearth.features.generation.noise;

// 2D simplex with hashed unit gradients: each salt gives its own independent field, no permutation table
public final class SaltedSimplexNoise {
    private static final double F2 = 0.5 * (Math.sqrt(3) - 1);
    private static final double G2 = (3 - Math.sqrt(3)) / 6;
    private static final double SCALE = 92; // so that the output reaches about +-1
    private static final int ANGLES = 256;
    private static final double[] GRAD_X = new double[ANGLES];
    private static final double[] GRAD_Y = new double[ANGLES];

    static {
        for (int i = 0; i < ANGLES; i++) {
            double angle = i * 2 * Math.PI / ANGLES;
            GRAD_X[i] = Math.cos(angle);
            GRAD_Y[i] = Math.sin(angle);
        }
    }

    private SaltedSimplexNoise() {
    }

    public static double noise(double xin, double yin, int salt) {
        double s = (xin + yin) * F2;
        int i = (int) Math.floor(xin + s);
        int j = (int) Math.floor(yin + s);
        double t = (i + j) * G2;
        double x0 = xin - (i - t);
        double y0 = yin - (j - t);
        int i1 = x0 > y0 ? 1 : 0;
        int j1 = 1 - i1;
        double x1 = x0 - i1 + G2;
        double y1 = y0 - j1 + G2;
        double x2 = x0 - 1 + 2 * G2;
        double y2 = y0 - 1 + 2 * G2;
        return SCALE * (corner(x0, y0, i, j, salt) + corner(x1, y1, i + i1, j + j1, salt) + corner(x2, y2, i + 1, j + 1, salt));
    }

    // the world seed as a shift of up to 65536 noise cells, added to x
    public static double seedOffset(long seed, int salt) {
        return Math.floorMod(hash((int) seed, (int) (seed >>> 32), salt), 65536);
    }

    private static double corner(double x, double y, int i, int j, int salt) {
        double t = 0.5 - x * x - y * y;
        if (t < 0) return 0;
        int g = hash(i, j, salt) & (ANGLES - 1);
        t *= t;
        return t * t * (GRAD_X[g] * x + GRAD_Y[g] * y);
    }

    private static int hash(int i, int j, int salt) {
        int h = i * 0x27d4eb2d ^ j * 0x165667b1 ^ salt * 0x9e3779b1;
        h ^= h >>> 15;
        h *= 0x85ebca6b;
        h ^= h >>> 13;
        h *= 0xc2b2ae35;
        h ^= h >>> 16;
        return h;
    }
}
