package net.sevenstars.middleearth.features.generation.chunkgen.map;

// ridged mountain term after Tectonic's mountain_ridges: low-frequency base, ridged detail noise sampled at a position
// pushed along the base gradient, and narrow gullies; pure function of position and seed, hashed simplex so it never repeats
final class TerrainRidges {
    // blocks per noise cell
    private static final double BASE_SCALE = 150;
    private static final double DETAIL_SCALE = 96;
    private static final double WEATHER_SCALE = 24;
    private static final double WARP_SCALE = 260;
    private static final double WARP_STRENGTH = 35; // blocks

    // Tectonic shifts the detail by |base(p) - base(p + 10)| * 3000 noise units (1500 blocks per unit of difference): a median
    // shift of 3 cells and 80 degree slopes here, so it is scaled down to a median shift of about 60 blocks
    private static final double GRADIENT_STEP = 10;
    private static final double SHIFT_BLOCKS_PER_UNIT = 250;

    private static final double DETAIL_WEIGHT = 0.6;
    private static final double GULLY_WEIGHT = 0.12;
    private static final double RIDGE_POWER = 2;
    private static final double GULLY_WIDTH = 0.25; // widens the gullies, a block is much coarser than a density cell
    private static final double BIAS = 0.3; // mean of 0.6 * ridged detail, so the term is centred on the base height

    // stretch factors of the noise space per axis; the larger one lengthens the ridges along that axis
    static final double[] NORTH_SOUTH = {1, 2.2};
    static final double[] EAST_WEST = {2.2, 1};
    static final double[] ISOTROPIC = {1, 1};

    private static final int SALT_BASE = 0x1b873593;
    private static final int SALT_BASE2 = 0x2c1b3c6d;
    private static final int SALT_DETAIL = 0x297a2d39;
    private static final int SALT_DETAIL2 = 0x3a2d8b17;
    private static final int SALT_WEATHER = 0x4cf5ad43;
    private static final int SALT_WARP_X = 0x5bd1e995;
    private static final int SALT_WARP_Z = 0x6c8e9cf5;

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

    // about -1.4 in the gullies up to +1.4 on the crests, before the amplitude of the range
    static double ridge(double x, double z, double[] stretch, long seed) {
        double warpX = x + WARP_STRENGTH * noise(x / WARP_SCALE + offset(seed, SALT_WARP_X), z / WARP_SCALE, SALT_WARP_X);
        double warpZ = z + WARP_STRENGTH * noise(x / WARP_SCALE, z / WARP_SCALE + offset(seed, SALT_WARP_Z), SALT_WARP_Z);
        double px = warpX / stretch[0];
        double pz = warpZ / stretch[1];

        double base = base(px, pz, seed);
        double slopeX = Math.abs(base - base(px + GRADIENT_STEP / stretch[0], pz, seed));
        double slopeZ = Math.abs(base - base(px, pz + GRADIENT_STEP / stretch[1], seed));
        double shiftX = slopeX * SHIFT_BLOCKS_PER_UNIT;
        double shiftZ = slopeZ * SHIFT_BLOCKS_PER_UNIT;

        double dx = (px + shiftX) / DETAIL_SCALE;
        double dz = (pz + shiftZ) / DETAIL_SCALE;
        double detail = (noise(dx + offset(seed, SALT_DETAIL), dz, SALT_DETAIL)
                + 0.2 * noise(dx / 2 + offset(seed, SALT_DETAIL2), dz / 2, SALT_DETAIL2)) / 1.2;
        // the square root rounds the crest, which |detail| alone leaves as a sharp crease
        double crest = Math.sqrt(detail * detail + 0.01) - 0.1;
        double ridged = Math.pow(1 - Math.min(1, crest), RIDGE_POWER);

        double gully = gully(noise(px / WEATHER_SCALE + offset(seed, SALT_WEATHER), pz / WEATHER_SCALE, SALT_WEATHER));
        return base + DETAIL_WEIGHT * ridged - BIAS - GULLY_WEIGHT * gully;
    }

    private static double base(double px, double pz, long seed) {
        return noise(px / BASE_SCALE + offset(seed, SALT_BASE), pz / BASE_SCALE, SALT_BASE)
                + 0.2 * noise(px / (BASE_SCALE / 2) + offset(seed, SALT_BASE2), pz / (BASE_SCALE / 2), SALT_BASE2);
    }

    // Tectonic's weathering: one minus a clamped hermite spline of |noise|, from (0.025, 0, slope 20) to (1, 1, slope 0)
    private static double gully(double n) {
        double t = (Math.abs(n) * GULLY_WIDTH - 0.025) / 0.975;
        if (t <= 0) return 1;
        double value = 19.5 * (t * t * t - 2 * t * t + t) + (-2 * t * t * t + 3 * t * t);
        return 1 - Math.min(1, value);
    }

    // seed as a bounded shift of the noise cells
    private static double offset(long seed, int salt) {
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
