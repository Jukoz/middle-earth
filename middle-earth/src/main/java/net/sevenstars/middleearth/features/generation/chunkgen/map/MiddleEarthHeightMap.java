package net.sevenstars.middleearth.features.generation.chunkgen.map;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.features.generation.biomes.BiomeKeyRegistryME;
import net.sevenstars.middleearth.features.generation.biomes.surface.SubBiome;
import net.sevenstars.middleearth.features.generation.biomes.surface.SubBiomes;
import net.sevenstars.middleearth.features.generation.biomes.surface.BiomeSourceME;
import net.sevenstars.middleearth.features.generation.chunkgen.MiddleEarthChunkGenerator;
import net.sevenstars.middleearth.features.generation.noise.BlendedNoise;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedBiomePool;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedCustomBiome;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapConfigs;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapUtils;

import java.awt.*;

public class MiddleEarthHeightMap {
    public static final int SMOOTH_BRUSH_SIZE = 4;
    public static final int PERLIN_STRETCH_X = 210;
    public static final int PERLIN_STRETCH_Y = 180;
    public static final int PERLIN_STRETCH_X2 = 37;
    public static final int PERLIN_STRETCH_Y2 = 37;
    public static final int PERLIN_HEIGHT_RANGE = 53;
    public static final float MOUNTAIN_HEIGHT_RANGE = 3.5f;
    public static final float MOUNTAIN_EXPONENTIAL_HEIGHT = 1.02f;
    public static final int MOUNTAIN_START_HEIGHT = 32; // Height depending on the Biome Data.
    public static final int PERLIN_HEIGHT_OFFSET = 8;
    public static final int STONE_HEIGHT = 54;
    public static final int HEIGHT = 8 + STONE_HEIGHT;
    public static final int DIRT_HEIGHT = 3 + HEIGHT;
    public static final int WATER_MAX = 24;
    public static final float WATER_MULTIPLIER = 0.65f;
    public static final float WATER_PERLIN_DIVIDER = 3.6f;
    private static final int PIXEL_WEIGHT = MiddleEarthMapConfigs.PIXEL_WEIGHT;
    private static MiddleEarthMapRuntime middleEarthMapRuntime;
    private static Float defaultWeightHeight = null;

    private static final float MIN_SHARE = 0.001f;
    private static final ThreadLocal<float[]> MOUNTAIN_SHARES = ThreadLocal.withInitial(() -> new float[4]);

    // Above the start the terrain bends towards the ceiling (y values, not heights relative to DIRT_HEIGHT)
    public static final int SOFT_CEILING_START = 380;
    public static final int SOFT_CEILING = 430;

    private static Long SEED;
    public MiddleEarthHeightMap(){
        middleEarthMapRuntime = MiddleEarthMapRuntime.getInstance();
    }

    public static void setSeed(long newSeed){
        if(SEED == null || newSeed != SEED){
            SEED = newSeed;
        }
    }

    public static Long getSeed(){
        if(SEED == null)
            return 0L;
        return SEED;
    }

    private static float getImageHeight(int xWorld, int zWorld) {
        if(middleEarthMapRuntime == null) middleEarthMapRuntime = MiddleEarthMapRuntime.getInstance();

        int rgb = middleEarthMapRuntime.getHeightRgb(xWorld, zWorld);

        float blue = rgb & 0xFF;
        float height = (rgb >> 16) & 0xFF;

        if(blue > 0) { // Water carver
            MapBasedCustomBiome meBiome = middleEarthMapRuntime.getBiome(xWorld, zWorld);
            float percentage = (WATER_MAX - blue) / WATER_MAX;
            percentage = Math.max(0, Math.min(1, percentage));
            float waterDifference = (float) (meBiome.getWaterHeight() - MapBasedCustomBiome.DEFAULT_WATER_HEIGHT);
            height -= waterDifference;
            height *= percentage;
            height += waterDifference;
            height -= blue * WATER_MULTIPLIER;
        }
        return height;
    }

    public static float getImageNoiseModifier(int xWorld, int zWorld) {
        if(middleEarthMapRuntime == null) middleEarthMapRuntime = MiddleEarthMapRuntime.getInstance();

        return (middleEarthMapRuntime.getHeightRgb(xWorld, zWorld) >> 8) & 0xFF;
    }

    public static double getPerlinHeight(int x, int z) {
        x += getSeed();
        z += getSeed();
        double perlin = 1 * BlendedNoise.noise((double) x / PERLIN_STRETCH_X,(double) z / PERLIN_STRETCH_Y);
        perlin += 0.5f * BlendedNoise.noise((double) x * 2 / PERLIN_STRETCH_X,(double) z * 2 / PERLIN_STRETCH_Y);
        perlin += 0.25f * BlendedNoise.noise((double) x * 4 / PERLIN_STRETCH_X,(double) z * 4 / PERLIN_STRETCH_Y);
        perlin += 0.125f * BlendedNoise.noise((double) x * 8 / PERLIN_STRETCH_X,(double) z * 8 / PERLIN_STRETCH_Y);

        perlin = perlin / (1 + 0.5f + 0.25f + 0.125f);
        perlin *= PERLIN_HEIGHT_RANGE;
        perlin += PERLIN_HEIGHT_OFFSET;

        return perlin;
    }

    private static float getPerlinMapHeight(int x, int z) {
        if(middleEarthMapRuntime == null)
            middleEarthMapRuntime = MiddleEarthMapRuntime.getInstance();

        double additionalHeight;

        double perlin = getPerlinHeight(x, z);

        float biomeHeight = 0;

        if(MiddleEarthMapUtils.getInstance().isWorldCoordinateInBorder(x,z)) {
            biomeHeight = getBiomeWeightHeight(x, z);
            int green = (middleEarthMapRuntime.getHeightRgb(x, z) >> 8) & 0xFF;
            perlin *= ((float)green) / 128f;
        } else {
            biomeHeight = ((getSmoothHeight(x, z) + getDefaultWeightHeight()) / 2f);
        }

        if(biomeHeight < 0) {
            perlin /= (Math.max(1, Math.min(5, Math.abs(biomeHeight / WATER_PERLIN_DIVIDER))));
        }

        if(biomeHeight >= MOUNTAIN_START_HEIGHT) {
            float multiplier = (biomeHeight / MOUNTAIN_START_HEIGHT) - 1;
            biomeHeight += biomeHeight * multiplier * MOUNTAIN_EXPONENTIAL_HEIGHT;
            perlin += multiplier * getMountainTerm(x, z);
        }

        additionalHeight = biomeHeight + perlin;

        return (float) additionalHeight;
    }

    // Ridges where a range sets them, the old smooth two octaves for the Lonely Mountain; both are scaled by the
    // caller with the same multiplier, so foothills stay smooth and the cores go jagged.
    private static double getMountainTerm(int x, int z) {
        float[] shares = MOUNTAIN_SHARES.get();
        MountainRanges.sharesAt(x, z, shares);
        double term = 0;
        if(shares[MountainRanges.NORTH_SOUTH] > MIN_SHARE)
            term += shares[MountainRanges.NORTH_SOUTH] * TerrainRidges.ridge(x, z, TerrainRidges.NORTH_SOUTH, getSeed());
        if(shares[MountainRanges.EAST_WEST] > MIN_SHARE)
            term += shares[MountainRanges.EAST_WEST] * TerrainRidges.ridge(x, z, TerrainRidges.EAST_WEST, getSeed());
        if(shares[MountainRanges.ISOTROPIC] > MIN_SHARE)
            term += shares[MountainRanges.ISOTROPIC] * TerrainRidges.ridge(x, z, TerrainRidges.ISOTROPIC, getSeed());
        if(shares[MountainRanges.SMOOTH] > MIN_SHARE) {
            double smooth = MOUNTAIN_EXPONENTIAL_HEIGHT * MOUNTAIN_HEIGHT_RANGE * BlendedNoise.noise((double) x / PERLIN_STRETCH_X2, (double) z / PERLIN_STRETCH_Y2);
            smooth += (MOUNTAIN_HEIGHT_RANGE / 2) * BlendedNoise.noise((double) (2 * x) / PERLIN_STRETCH_X2, (double) (2 * z) / PERLIN_STRETCH_Y2);
            term += shares[MountainRanges.SMOOTH] * smooth;
        }
        return term;
    }

    private static float getBiomeWeightHeight(int x, int z) {
        float topLeft = getImageHeight(x, z);
        float topRight = getImageHeight(x + PIXEL_WEIGHT, z);
        float bottomLeft = getImageHeight(x, z + PIXEL_WEIGHT);
        float bottomRight = getImageHeight(x + PIXEL_WEIGHT, z + PIXEL_WEIGHT);
        return getHeightBetween(new float[]{topLeft, topRight, bottomLeft, bottomRight},
                (float) (x % PIXEL_WEIGHT) / PIXEL_WEIGHT, (float) (z % PIXEL_WEIGHT) / PIXEL_WEIGHT);
    }

    private static float getDefaultWeightHeight() {
        if(defaultWeightHeight == null) {
            defaultWeightHeight = getImageHeight(0, 0);
        }
        return defaultWeightHeight;
    }

    private static float getHeightBetween(float[] heights, float xPercent, float zPercent) {
        float h1 = getMiddleHeight(heights[0], heights[1], xPercent);
        float h2 = getMiddleHeight(heights[2], heights[3], xPercent);
        return getMiddleHeight(h1, h2, zPercent);
    }

    private static float getMiddleHeight(float a, float b, float percentage) {
        float percentage2 = 1 - percentage;
        return (a * percentage2) + (b * percentage);
    }

    private static float getSmoothHeight(int x, int z) {
        float total = 0;
        for(int i = -SMOOTH_BRUSH_SIZE; i <= SMOOTH_BRUSH_SIZE; i++) {
            for(int j = -SMOOTH_BRUSH_SIZE; j <= SMOOTH_BRUSH_SIZE; j++) {
                if(!MiddleEarthMapUtils.getInstance().isWorldCoordinateInBorder(x + i, z + j))
                    total += MapBasedBiomePool.defaultBiome.getHeight();
                else
                    total += getImageHeight(x, z);
            }
        }

        return total / ((SMOOTH_BRUSH_SIZE * 2 + 1) * (SMOOTH_BRUSH_SIZE * 2 + 1));
    }

    // The terrain height every system agrees on: the map and noise height, the sub biome hills, the Mount Doom cone,
    // the Dead Marshes and the soft ceiling. Relative to MiddleEarthChunkGenerator.DIRT_HEIGHT.
    public static float getHeight(int x, int z) {
        ChunkHeights heights = findChunkHeights(x >> 4, z >> 4);
        if(heights != null) return heights.at(x & 15, z & 15);
        return computeHeight(x, z);
    }

    // Same value, remembered per chunk for the callers that ask for many columns of one chunk
    public static float getCachedHeight(int x, int z) {
        return getChunkHeights(x >> 4, z >> 4).at(x & 15, z & 15);
    }

    private static float computeHeight(int x, int z) {
        float height = getPerlinMapHeight(x, z);

        MapBasedCustomBiome mapBiome = getBiomeFromMap(x, z);
        RegistryKey<Biome> key = mapBiome.getBiomeKey();
        SubBiome subBiome = SubBiomes.getSubBiome(key);
        if(subBiome != null) {
            double perlin = BiomeSourceME.getSubBiomeNoise(x, z, subBiome.getFrequency());
            double additionalHeight = Math.max(subBiome.getAdditionalHeight((float) perlin) - 1, 0);
            additionalHeight *= middleEarthMapRuntime.getEdge(x, z);
            height += (float) additionalHeight;
        } else if(key == BiomeKeyRegistryME.MOUNT_DOOM || key == BiomeKeyRegistryME.MOUNT_DOOM_PIT) {
            float percentage = (float) Math.sqrt(MiddleEarthChunkGenerator.mountDoom.distanceSquared(new Vec2f(x, z))) / 42;
            percentage = Math.min(1, Math.max(0.0f, percentage));
            percentage = (float) Math.pow(percentage, 2.47f);
            height = height * percentage;
            height -= (1 - percentage) * MiddleEarthChunkGenerator.getNoisyHeight(x, z) * 8;
        } else if(key == BiomeKeyRegistryME.DEAD_MARSHES || key == BiomeKeyRegistryME.DEAD_MARSHES_WATER) {
            float oldHeight = height;
            height = MiddleEarthChunkGenerator.getMarshesHeight(x, z, height);
            float percentage = Math.min(getImageNoiseModifier(x, z), 0.3f) / 0.3f;
            height = lerp(height, oldHeight, percentage);
        }

        return softCeiling(height);
    }

    private static float softCeiling(float height) {
        float y = MiddleEarthChunkGenerator.DIRT_HEIGHT + height;
        if(y <= SOFT_CEILING_START) return height;
        float range = SOFT_CEILING - SOFT_CEILING_START;
        return SOFT_CEILING_START + range * (float) Math.tanh((y - SOFT_CEILING_START) / range) - MiddleEarthChunkGenerator.DIRT_HEIGHT;
    }

    // The 16x16 columns of a chunk plus a margin of 3 to the east and south for the slope samples; each one is
    // computed when first asked for
    public static final class ChunkHeights {
        public static final int SIZE = 16 + 3;
        private final long seed;
        private final int chunkX;
        private final int chunkZ;
        private final float[] heights = new float[SIZE * SIZE];

        private ChunkHeights(long seed, int chunkX, int chunkZ) {
            this.seed = seed;
            this.chunkX = chunkX;
            this.chunkZ = chunkZ;
            java.util.Arrays.fill(heights, Float.NaN);
        }

        public float at(int dx, int dz) {
            int index = dz * SIZE + dx;
            float height = heights[index];
            if(Float.isNaN(height)) {
                height = computeHeight((chunkX << 4) + dx, (chunkZ << 4) + dz);
                heights[index] = height;
            }
            return height;
        }
    }

    private static final int CHUNK_CACHE_SIZE = 16;
    private static final ThreadLocal<ChunkHeights[]> chunkCache = ThreadLocal.withInitial(() -> new ChunkHeights[CHUNK_CACHE_SIZE]);

    private static ChunkHeights findChunkHeights(int chunkX, int chunkZ) {
        ChunkHeights heights = chunkCache.get()[(chunkX * 31 + chunkZ) & (CHUNK_CACHE_SIZE - 1)];
        if(heights != null && heights.chunkX == chunkX && heights.chunkZ == chunkZ && heights.seed == getSeed()) return heights;
        return null;
    }

    public static ChunkHeights getChunkHeights(int chunkX, int chunkZ) {
        ChunkHeights heights = findChunkHeights(chunkX, chunkZ);
        if(heights == null) {
            heights = new ChunkHeights(getSeed(), chunkX, chunkZ);
            chunkCache.get()[(chunkX * 31 + chunkZ) & (CHUNK_CACHE_SIZE - 1)] = heights;
        }
        return heights;
    }


    // Going to be useful for making roads with curves.
    static float getPointOnBezierCurve(float h0, float h1, float h2, float t)
    {
        float a = lerp(h0, h1, t);
        float b = lerp(h1, h2, t);
        float d = lerp(a, b, t);

        return d;
    }

    public static float lerp(float a, float b, float interpolation) {
        return a + interpolation * (b - a);
    }

    public static MapBasedCustomBiome getBiomeFromMap(int posX, int posZ) {
        if(middleEarthMapRuntime == null)
            middleEarthMapRuntime = MiddleEarthMapRuntime.getInstance();
        return middleEarthMapRuntime.getBiome(posX, posZ);
    }
}
