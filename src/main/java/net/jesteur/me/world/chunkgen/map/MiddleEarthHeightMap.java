package net.jesteur.me.world.chunkgen.map;

import net.jesteur.me.utils.noises.PerlinNoise;
import net.jesteur.me.world.biomes.MEBiome;
import net.jesteur.me.world.biomes.MEBiomesData;

import java.awt.image.BufferedImage;

public class MiddleEarthHeightMap {
    public static final int SMOOTH_BRUSH_SIZE = 2;
    public static final int PERLIN_STRETCH_X = 210;
    public static final int PERLIN_STRETCH_Y = 180;
    public static final int PERLIN_STRETCH_X2 = 33;
    public static final int PERLIN_STRETCH_Y2 = 33;
    public static final int PERLIN_HEIGHT_RANGE = 90;
    public static final float MOUNTAIN_HEIGHT_RANGE = 2.5f;
    public static final int MOUNTAIN_START_HEIGHT = 16; // Height depending on the Biome Data.
    public static final int PERLIN_HEIGHT_OFFSET = 8;
    public static final int STONE_HEIGHT = 50;
    public static final int HEIGHT = 8 + STONE_HEIGHT;
    public static final int DIRT_HEIGHT = 3 + HEIGHT;

    private static BufferedImage heightMapImage;

    public static int latitude; // Horizontal
    public static int longitude; // Vertical


    public static void applyHeightMapImage(BufferedImage newHeightMapImage) {
        heightMapImage = newHeightMapImage;
        latitude = heightMapImage.getHeight();
        longitude = heightMapImage.getWidth();
    }

    public static float getPerlinHeight(int x, int z) {
        double additionalHeight;
        MEBiome meBiome;

        double perlin = 1 * PerlinNoise.noise((double) x / PERLIN_STRETCH_X, 0,  (double) z / PERLIN_STRETCH_Y);
        perlin += 0.5f * PerlinNoise.noise((double) x * 2 / PERLIN_STRETCH_X, 0,  (double) z * 2 / PERLIN_STRETCH_Y);
        perlin += 0.25f * PerlinNoise.noise((double) x * 4 / PERLIN_STRETCH_X, 0,  (double) z * 4 / PERLIN_STRETCH_Y);
        perlin += 0.125f * PerlinNoise.noise((double) x * 8 / PERLIN_STRETCH_X, 0,  (double) z * 8 / PERLIN_STRETCH_Y);

        perlin = perlin / (1 + 0.5f + 0.25f + 0.125f);
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
        return (float) additionalHeight;
    }

    public static float getHeight(int x, int z) {
        if(!isCoordinateInBounds(x, z)) return MEBiomesData.defaultBiome.height + (float)getPerlinHeight(x, z);
        return ((float) ((heightMapImage.getRGB(x, z)>>16)&0xFF) / 4) + MEBiomesData.MINIMAL_HEIGHT;
    }

    public static float getSmoothHeight(int x, int z) {
        float total = 0;
        for(int i = -SMOOTH_BRUSH_SIZE; i <= SMOOTH_BRUSH_SIZE; i++) {
            for(int j = -SMOOTH_BRUSH_SIZE; j <= SMOOTH_BRUSH_SIZE; j++) {
                if(!isCoordinateInBounds(x + i, z + j)) total += MEBiomesData.defaultBiome.height;
                else total += ((float)((heightMapImage.getRGB(x + i, z + j)>>16)&0xFF) / 4) + MEBiomesData.MINIMAL_HEIGHT;
            }
        }

        return total / ((SMOOTH_BRUSH_SIZE * 2 + 1) * (SMOOTH_BRUSH_SIZE * 2 + 1));
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
