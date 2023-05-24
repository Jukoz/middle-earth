package net.jesteur.me.world.chunkgen.map;

import net.jesteur.me.utils.noises.PerlinNoise;
import net.jesteur.me.world.biomes.MEBiome;
import net.jesteur.me.world.biomes.MEBiomesData;

import java.util.Arrays;

public class MiddleEarthHeightMap {
    public static final int SMOOTH_BRUSH_SIZE = 16;
    public static final int PERLIN_STRETCH_X = 210;
    public static final int PERLIN_STRETCH_Y = 180;
    public static final int PERLIN_STRETCH_X2 = 27;
    public static final int PERLIN_STRETCH_Y2 = 27;
    public static final int PERLIN_HEIGHT_RANGE = 54;
    public static final float MOUNTAIN_HEIGHT_RANGE = 4.5f;
    public static final int MOUNTAIN_START_HEIGHT = 16; // Height depending on the Biome Data.
    public static final int PERLIN_HEIGHT_OFFSET = 4;
    public static final int STONE_HEIGHT = 50;
    public static final int HEIGHT = 8 + STONE_HEIGHT;
    public static final int DIRT_HEIGHT = 3 + HEIGHT;

    public static float[][] heightMap;

    public static int latitude; // Horizontal
    public static int longitude; // Vertical

    public static void loadHeightMap() {
        latitude = MapImageLoader.getImageSize()[0];
        longitude = MapImageLoader.getImageSize()[1];

        heightMap = new float[latitude][longitude];
        for (int z = 0; z < longitude; z++) {
            for (int x = latitude - 1; x >= 0; x--) {
                heightMap[x][z] = MapImageLoader.getBiomeHeight(x, z);
            }
        }

        smoothHeightMap();
        applyNoise();
    }

    private static void smoothHeightMap() {
        float[][] newHeightMap = new float[latitude][longitude];
        Arrays.stream(newHeightMap).forEach(a -> Arrays.fill(a, heightMap[0][0]));
        int radius = SMOOTH_BRUSH_SIZE / 2;
        for (int z = radius; z < longitude - radius; z++) {
            for (int x = radius; x < latitude - radius; x++) {
                float[] heights = new float[SMOOTH_BRUSH_SIZE * SMOOTH_BRUSH_SIZE];
                for (int j = 0; j < SMOOTH_BRUSH_SIZE; j++) {
                    for (int i = 0; i < SMOOTH_BRUSH_SIZE; i++) {
                        heights[i + (j * SMOOTH_BRUSH_SIZE)] = heightMap[x + i - radius][z + j - radius];
                    }
                }
                newHeightMap[x][z] = average(heights);
            }
        }
        heightMap = newHeightMap;
    }

    private static void applyNoise() {
        for(int posX = 0; posX < latitude; posX++) {
            for (int posZ = 0; posZ < longitude; posZ++) {
                heightMap[posX][posZ] += getPerlinHeight(posX, posZ);
            }
        }
    }

    public static double getPerlinHeight(int x, int z) {
        double additionalHeight;
        MEBiome meBiome;

        double perlin = 1 * PerlinNoise.noise((double) x / PERLIN_STRETCH_X, 0,  (double) z / PERLIN_STRETCH_Y);
        perlin += 0.6f * PerlinNoise.noise((double) x * 2 / PERLIN_STRETCH_X, 0,  (double) z * 2 / PERLIN_STRETCH_Y);
        perlin += 0.3f * PerlinNoise.noise((double) x * 4 / PERLIN_STRETCH_X, 0,  (double) z * 4 / PERLIN_STRETCH_Y);

        perlin = perlin / (1 + 0.6f + 0.3f);
        perlin *= PERLIN_HEIGHT_RANGE;
        perlin += PERLIN_HEIGHT_OFFSET;

        if(MiddleEarthHeightMap.isCoordinateInBounds(x, z)) {
            float biomeHeight = MiddleEarthHeightMap.getHeight(x, z);
            if(biomeHeight >= MOUNTAIN_START_HEIGHT) {
                float multiplier = 2f * (biomeHeight / MOUNTAIN_START_HEIGHT);
                //perlin2 *= 1 + ((multiplier - 1) / 3);
                perlin *= Math.max(3 - multiplier, multiplier * MOUNTAIN_HEIGHT_RANGE * PerlinNoise.noise((double) x / PERLIN_STRETCH_X2, 0,  (double) z / PERLIN_STRETCH_Y2));
            }
            additionalHeight = biomeHeight + perlin;
        } else {
            meBiome = MEBiomesData.defaultBiome;
            additionalHeight = meBiome.height + perlin;
        }
        return additionalHeight;
    }

    private static float average(float[] heights) {
        float average = 0;
        for (float height : heights) {
            average += height;
        }
        return average / heights.length;
    }

    public static float getHeight(int x, int z) {
        if(!isCoordinateInBounds(x, z)) return MEBiomesData.defaultBiome.height + (float)getPerlinHeight(x, z);
        return heightMap[x][z];
    }

    public static boolean isCoordinateInBounds(int x, int z) {
        if(x < 0 || z < 0) return false;
        return (x < latitude && z < longitude);
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
}
