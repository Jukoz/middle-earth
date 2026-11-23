package net.sevenstars.middleearth.features.generation.chunkgen.map;

import net.sevenstars.middleearth.features.generation.biomes.BiomeGenerationData;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedCustomBiome;

// Which ridge variant a mountain column uses and how strongly, from its biomes' ridge settings
final class MountainRanges {
    static final int NORTH_SOUTH = BiomeGenerationData.RidgeAxis.NORTH_SOUTH.ordinal();
    static final int EAST_WEST = BiomeGenerationData.RidgeAxis.EAST_WEST.ordinal();
    static final int ISOTROPIC = BiomeGenerationData.RidgeAxis.ISOTROPIC.ordinal();
    static final int SMOOTH = BiomeGenerationData.RidgeAxis.SMOOTH.ordinal(); // its amplitude is only a weight

    static final int VARIANTS = 4; // one per ridge axis

    // already weighted by the amplitude of the range, so the ridge variants can simply be summed
    private static final BiomeShareGrid GRID = new BiomeShareGrid(VARIANTS, MountainRanges::weigh);

    private MountainRanges() {
    }

    static void sharesAt(int x, int z, float[] out) {
        GRID.sharesAt(x, z, out);
    }

    private static void weigh(MapBasedCustomBiome[] biomes, float[] sums) {
        int count = 0;
        for (MapBasedCustomBiome biome : biomes) {
            if (biome.getHeight() < MiddleEarthHeightMap.MOUNTAIN_START_HEIGHT) continue;
            BiomeGenerationData data = biome.getBiomeData();
            sums[data.ridgeAxis.ordinal()] += data.ridgeAmplitude;
            count++;
        }
        if (count == 0) {
            sums[ISOTROPIC] = new BiomeGenerationData().ridgeAmplitude;
            return;
        }
        for (int i = 0; i < VARIANTS; i++) sums[i] /= count;
    }
}
