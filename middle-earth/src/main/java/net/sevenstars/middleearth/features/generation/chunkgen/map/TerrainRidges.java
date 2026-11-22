package net.sevenstars.middleearth.features.generation.chunkgen.map;

// Tectonic's mountain_ridges: a 150 block base plus 32 block detail, sampled where the base slope pushes it so ribs
// and gullies shear across the flanks. Pure function of position and seed. Callers sample every 4 blocks and blend,
// as Tectonic does, or it turns to stripes.
final class TerrainRidges {
    // blocks per noise cell
    private static final double BASE_SCALE = 218;
    private static final double DETAIL_SCALE = 46;

    // sized so the push and the spread of the value match Tectonic's: a median push of 31 blocks, |ridges| median 0.19
    private static final double BASE_AMPLITUDE = 0.5;
    private static final double DETAIL_AMPLITUDE = 0.3;
    private static final double DETAIL_WEIGHT = 0.6;

    // the base is compared 5.9 blocks away and the difference moves the detail 1500 blocks per unit
    private static final double SLOPE_STEP = 5.9;
    private static final double SHIFT_BLOCKS_PER_UNIT = 1500;

    // noise stretch per axis, the larger lengthens the ridges along it
    static final double[] NORTH_SOUTH = {1, 2.2};
    static final double[] EAST_WEST = {2.2, 1};
    static final double[] ISOTROPIC = {1, 1};

    private static final int SALT_BASE = 0x1b873593;
    private static final int SALT_BASE2 = 0x2c1b3c6d;
    private static final int SALT_DETAIL = 0x297a2d39;
    private static final int SALT_DETAIL2 = 0x3a2d8b17;

    private static final double F2 = 0.5 * (Math.sqrt(3) - 1);
    private static final double G2 = (3 - Math.sqrt(3)) / 6;
    private static final double NOISE_SCALE = 92; // unit gradients, so that the output reaches about +-1
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

    private TerrainRidges() {
    }

    // Tectonic's ridges value, about -0.6 to 0.6. Land stands high where it is low.
    static double ridges(double x, double z, double[] stretch, long seed) {
        double px = x / stretch[0];
        double pz = z / stretch[1];
        double base = base(px, pz, seed);
        double shiftX = SHIFT_BLOCKS_PER_UNIT * Math.abs(base - base(px + SLOPE_STEP, pz, seed));
        double shiftZ = SHIFT_BLOCKS_PER_UNIT * Math.abs(base - base(px, pz + SLOPE_STEP, seed));
        double dx = (px + shiftX) / DETAIL_SCALE;
        double dz = (pz + shiftZ) / DETAIL_SCALE;
        double detail = DETAIL_AMPLITUDE * (noise(dx + offset(seed, SALT_DETAIL), dz, SALT_DETAIL)
                + 0.2 * noise(dx / 2 + offset(seed, SALT_DETAIL2), dz / 2, SALT_DETAIL2)) / 1.2;
        return base + DETAIL_WEIGHT * detail;
    }

    private static double base(double px, double pz, long seed) {
        return BASE_AMPLITUDE * (noise(px / BASE_SCALE + offset(seed, SALT_BASE), pz / BASE_SCALE, SALT_BASE)
                + 0.2 * noise(px / (BASE_SCALE / 2) + offset(seed, SALT_BASE2), pz / (BASE_SCALE / 2), SALT_BASE2)) / 1.2;
    }

    // seed as a bounded shift of the noise cells
    static double offset(long seed, int salt) {
        return Math.floorMod(hash((int) seed, (int) (seed >>> 32), salt), 65536);
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

    static double noise(double xin, double yin, int salt) {
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
        return NOISE_SCALE * (corner(x0, y0, i, j, salt) + corner(x1, y1, i + i1, j + j1, salt) + corner(x2, y2, i + 1, j + 1, salt));
    }

    private static double corner(double x, double y, int i, int j, int salt) {
        double t = 0.5 - x * x - y * y;
        if (t < 0) return 0;
        int g = hash(i, j, salt) & (ANGLES - 1);
        t *= t;
        return t * t * (GRAD_X[g] * x + GRAD_Y[g] * y);
    }
}
