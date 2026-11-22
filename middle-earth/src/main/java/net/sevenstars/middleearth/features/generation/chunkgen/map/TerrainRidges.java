package net.sevenstars.middleearth.features.generation.chunkgen.map;

import static net.sevenstars.middleearth.features.generation.noise.SaltedSimplexNoise.noise;
import static net.sevenstars.middleearth.features.generation.noise.SaltedSimplexNoise.seedOffset;

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
        double detail = DETAIL_AMPLITUDE * (noise(dx + seedOffset(seed, SALT_DETAIL), dz, SALT_DETAIL)
                + 0.2 * noise(dx / 2 + seedOffset(seed, SALT_DETAIL2), dz / 2, SALT_DETAIL2)) / 1.2;
        return base + DETAIL_WEIGHT * detail;
    }

    private static double base(double px, double pz, long seed) {
        return BASE_AMPLITUDE * (noise(px / BASE_SCALE + seedOffset(seed, SALT_BASE), pz / BASE_SCALE, SALT_BASE)
                + 0.2 * noise(px / (BASE_SCALE / 2) + seedOffset(seed, SALT_BASE2), pz / (BASE_SCALE / 2), SALT_BASE2)) / 1.2;
    }
}
