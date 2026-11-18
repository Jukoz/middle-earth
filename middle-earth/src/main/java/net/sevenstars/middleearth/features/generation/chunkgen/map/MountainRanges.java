package net.sevenstars.middleearth.features.generation.chunkgen.map;

import net.sevenstars.middleearth.features.generation.biomes.BiomeGenerationData;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedCustomBiome;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;

// Which ridge variant a mountain column uses and how strongly, from the ridge settings of its biomes. The shares come
// from a world-fixed lattice of averages over the surrounding map biomes, interpolated bilinearly, so they never jump
// at a biome border and neighbouring chunks agree exactly.
final class MountainRanges {
    static final int NORTH_SOUTH = BiomeGenerationData.RidgeAxis.NORTH_SOUTH.ordinal();
    static final int EAST_WEST = BiomeGenerationData.RidgeAxis.EAST_WEST.ordinal();
    static final int ISOTROPIC = BiomeGenerationData.RidgeAxis.ISOTROPIC.ordinal();
    static final int SMOOTH = BiomeGenerationData.RidgeAxis.SMOOTH.ordinal(); // its amplitude is only a weight

    private static final int LATTICE = 32;
    private static final int SAMPLE_STEP = 24;
    private static final int SAMPLE_RADIUS = 3;

    // already weighted by the amplitude of the range, so the ridge variants can simply be summed
    private static final class Point {
        int ix = Integer.MIN_VALUE;
        int iz;
        final float[] shares = new float[4];
    }

    private static final int CACHE_SIZE = 256;
    private static final ThreadLocal<Point[]> cache = ThreadLocal.withInitial(() -> new Point[CACHE_SIZE]);

    private MountainRanges() {
    }

    static void sharesAt(int x, int z, float[] out) {
        int ix = Math.floorDiv(x, LATTICE);
        int iz = Math.floorDiv(z, LATTICE);
        float fx = (x - ix * LATTICE) / (float) LATTICE;
        float fz = (z - iz * LATTICE) / (float) LATTICE;
        Point p00 = point(ix, iz);
        Point p10 = point(ix + 1, iz);
        Point p01 = point(ix, iz + 1);
        Point p11 = point(ix + 1, iz + 1);
        for (int i = 0; i < 4; i++) {
            float top = p00.shares[i] + fx * (p10.shares[i] - p00.shares[i]);
            float bottom = p01.shares[i] + fx * (p11.shares[i] - p01.shares[i]);
            out[i] = top + fz * (bottom - top);
        }
    }

    private static Point point(int ix, int iz) {
        Point[] points = cache.get();
        int slot = (ix * 31 + iz * 17) & (CACHE_SIZE - 1);
        Point point = points[slot];
        if (point != null && point.ix == ix && point.iz == iz) return point;
        if (point == null) point = points[slot] = new Point();
        point.ix = ix;
        point.iz = iz;
        fill(point, ix * LATTICE, iz * LATTICE);
        return point;
    }

    private static void fill(Point point, int x, int z) {
        MiddleEarthMapRuntime map = MiddleEarthMapRuntime.getInstance();
        float[] sums = point.shares;
        java.util.Arrays.fill(sums, 0);
        int count = 0;
        for (int i = -SAMPLE_RADIUS; i <= SAMPLE_RADIUS; i++) {
            for (int j = -SAMPLE_RADIUS; j <= SAMPLE_RADIUS; j++) {
                MapBasedCustomBiome biome = map.getBiome(x + i * SAMPLE_STEP, z + j * SAMPLE_STEP);
                if (biome.getHeight() < MiddleEarthHeightMap.MOUNTAIN_START_HEIGHT) continue;
                BiomeGenerationData data = biome.getBiomeData();
                sums[data.ridgeAxis.ordinal()] += data.ridgeAmplitude;
                count++;
            }
        }
        if (count == 0) {
            sums[ISOTROPIC] = new BiomeGenerationData().ridgeAmplitude;
            return;
        }
        for (int i = 0; i < 4; i++) sums[i] /= count;
    }
}
