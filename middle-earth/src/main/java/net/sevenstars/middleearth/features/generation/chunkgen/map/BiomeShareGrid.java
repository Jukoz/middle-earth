package net.sevenstars.middleearth.features.generation.chunkgen.map;

import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedCustomBiome;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;

import java.util.Arrays;

// Per-biome weights blended across borders: worked out from the map biomes round world-fixed lattice points and
// bilinear between them, so borders and chunks agree.
final class BiomeShareGrid {
    private static final int LATTICE = 32;
    private static final int SAMPLE_STEP = 24;
    private static final int SAMPLE_RADIUS = 3;
    static final int SAMPLES = (2 * SAMPLE_RADIUS + 1) * (2 * SAMPLE_RADIUS + 1);
    private static final int CACHE_SIZE = 256;
    private static final ThreadLocal<MapBasedCustomBiome[]> BIOMES = ThreadLocal.withInitial(() -> new MapBasedCustomBiome[SAMPLES]);

    // shares of one lattice point from the biomes round it; out starts at zero
    interface Weigh {
        void weigh(MapBasedCustomBiome[] biomes, float[] out);
    }

    private static final class Point {
        int ix = Integer.MIN_VALUE;
        int iz;
        final float[] shares;

        Point(int size) {
            shares = new float[size];
        }
    }

    private final int size;
    private final Weigh weigh;
    private final ThreadLocal<Point[]> cache = ThreadLocal.withInitial(() -> new Point[CACHE_SIZE]);

    BiomeShareGrid(int size, Weigh weigh) {
        this.size = size;
        this.weigh = weigh;
    }

    void sharesAt(int x, int z, float[] out) {
        int ix = Math.floorDiv(x, LATTICE);
        int iz = Math.floorDiv(z, LATTICE);
        float fx = (x - ix * LATTICE) / (float) LATTICE;
        float fz = (z - iz * LATTICE) / (float) LATTICE;
        float[] s00 = point(ix, iz).shares;
        float[] s10 = point(ix + 1, iz).shares;
        float[] s01 = point(ix, iz + 1).shares;
        float[] s11 = point(ix + 1, iz + 1).shares;
        for (int i = 0; i < size; i++) {
            out[i] = MathHelper.lerp(fz, MathHelper.lerp(fx, s00[i], s10[i]), MathHelper.lerp(fx, s01[i], s11[i]));
        }
    }

    private Point point(int ix, int iz) {
        Point[] points = cache.get();
        int slot = (ix * 31 + iz * 17) & (CACHE_SIZE - 1);
        Point point = points[slot];
        if (point != null && point.ix == ix && point.iz == iz) return point;
        if (point == null) point = points[slot] = new Point(size);
        point.ix = ix;
        point.iz = iz;
        Arrays.fill(point.shares, 0);
        weigh.weigh(sample(ix * LATTICE, iz * LATTICE), point.shares);
        return point;
    }

    private static MapBasedCustomBiome[] sample(int x, int z) {
        MiddleEarthMapRuntime map = MiddleEarthMapRuntime.getInstance();
        MapBasedCustomBiome[] biomes = BIOMES.get();
        int n = 0;
        for (int i = -SAMPLE_RADIUS; i <= SAMPLE_RADIUS; i++) {
            for (int j = -SAMPLE_RADIUS; j <= SAMPLE_RADIUS; j++) biomes[n++] = map.getBiome(x + i * SAMPLE_STEP, z + j * SAMPLE_STEP);
        }
        return biomes;
    }
}
