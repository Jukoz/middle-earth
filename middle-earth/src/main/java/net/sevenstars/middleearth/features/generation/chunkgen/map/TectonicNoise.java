package net.sevenstars.middleearth.features.generation.chunkgen.map;

import net.sevenstars.middleearth.features.generation.noise.SaltedSimplexNoise;

// Tectonic's noises (vanilla first octave, amplitudes, xz scale) on hashed simplex. Octave weights and value factor
// are vanilla's. The simplex is widened as for the ridges, scaled and its tails lifted so the median, p90 and p99
// of |value| land within a few percent of vanilla's.
final class TectonicNoise {
    private static final double WIDEN = 1.45;
    private static final double SCALE = 0.714;
    private static final double TAIL = 0.27;
    private static final double SINGLE_OCTAVE_TAIL = 9; // one simplex octave alone is far more bounded than vanilla's pair of perlins

    static final TectonicNoise RIDGE = new TectonicNoise(-8, new double[]{1, 2, 1}, 0.25, 0x11a2b3c1);
    static final TectonicNoise SELECTOR = new TectonicNoise(-11, new double[]{1, 2.1, 1.5, 1.7, 1.4, 2, 2}, 1.1, 0x22b3c4d2);
    static final TectonicNoise HEIGHT_MULTIPLIER = new TectonicNoise(-8, new double[]{1, 2, 1}, 0.25, 0x33c4d5e3);
    static final TectonicNoise LOWER_TIER = new TectonicNoise(-8, new double[]{1.4, 2, 1}, 0.35, 0x44d5e6f4);
    static final TectonicNoise UPPER_TIER = new TectonicNoise(-8, new double[]{1.4, 2, 1}, 0.35, 0x55e6f705);
    static final TectonicNoise BADLANDS = new TectonicNoise(-8, new double[]{1, 2, 1}, 0.35, 0x66f70816);
    static final TectonicNoise ROLLING_HILLS = new TectonicNoise(-6, new double[]{1, 1.6, 1.2, 1, 2}, 0.2, 0x77081927);
    static final TectonicNoise JUNGLE_PILLAR = new TectonicNoise(-8, new double[]{1, 1}, 8, 0x88192a38);
    static final TectonicNoise WETLAND = new TectonicNoise(-6, new double[]{0.6, 1.7, 1.2, 1}, 0.8, 0x992a3b49);
    static final TectonicNoise DUNE_BASE = new TectonicNoise(-7, new double[]{0.5}, 1, 0xaa3b4c5a);
    static final TectonicNoise DUNE_OFFSET = new TectonicNoise(-11, new double[]{1}, 1, 0xbb4c5d6b); // only its sign and size matter

    private final double[] frequencies;
    private final double[] weights;
    private final int salt;
    private final double xzScale;
    private final double tail;

    private TectonicNoise(int firstOctave, double[] amplitudes, double xzScale, int salt) {
        int n = amplitudes.length;
        int first = 0;
        int lastOctave = 0;
        for (int i = 0; i < n; i++) {
            if (amplitudes[i] == 0) continue;
            if (amplitudes[first] == 0) first = i;
            lastOctave = i;
        }
        double valueFactor = (1.0 / 6) / (0.1 * (1 + 1.0 / (lastOctave - first + 1)));
        this.frequencies = new double[n];
        this.weights = new double[n];
        for (int i = 0; i < n; i++) {
            frequencies[i] = Math.pow(2, firstOctave + i) / WIDEN;
            weights[i] = valueFactor * SCALE * amplitudes[i] * Math.pow(2, n - 1 - i) / (Math.pow(2, n) - 1);
        }
        this.xzScale = xzScale;
        this.salt = salt;
        this.tail = lastOctave == first ? SINGLE_OCTAVE_TAIL : TAIL;
    }

    // at a block position
    double at(double x, double z, long seed) {
        return sample(x * xzScale, z * xzScale, seed);
    }

    // at a position in noise units, block times xz scale, so a Tectonic shift can be added first
    double sample(double u, double w, long seed) {
        double value = 0;
        for (int i = 0; i < weights.length; i++) {
            if (weights[i] == 0) continue;
            int octaveSalt = salt + i * 0x9e3779b9;
            value += weights[i] * SaltedSimplexNoise.noise(u * frequencies[i] + SaltedSimplexNoise.seedOffset(seed, octaveSalt), w * frequencies[i], octaveSalt);
        }
        return value * (1 + tail * value * value);
    }
}
