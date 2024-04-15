package net.jukoz.me.world.chunkgen.map;

import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.utils.noises.BlendedNoise;
import net.jukoz.me.world.map.MiddleEarthMapConfigs;
import net.jukoz.me.world.map.MiddleEarthMapRuntime;
import net.jukoz.me.world.map.MiddleEarthMapUtils;
import net.jukoz.me.world.biomes.surface.MEBiome;
import net.jukoz.me.world.biomes.surface.MEBiomesData;

import java.awt.*;
import java.util.ArrayList;

public class MiddleEarthHeightMap {
    public static final int SMOOTH_BRUSH_SIZE = 2;
    public static final int PERLIN_STRETCH_X = 180;
    public static final int PERLIN_STRETCH_Y = 135;
    public static final int PERLIN_STRETCH_X2 = 37;
    public static final int PERLIN_STRETCH_Y2 = 37;
    public static final int PERLIN_HEIGHT_RANGE = 45;
    public static final float MOUNTAIN_HEIGHT_RANGE = 3.5f;
    public static final float MOUNTAIN_EXPONENTIAL_HEIGHT = 1.02f;
    public static final int MOUNTAIN_START_HEIGHT = 32; // Height depending on the Biome Data.
    public static final int PERLIN_HEIGHT_OFFSET = 8;
    public static final int STONE_HEIGHT = 54;
    public static final int HEIGHT = 8 + STONE_HEIGHT;
    public static final int DIRT_HEIGHT = 3 + HEIGHT;
    public static final int WATER_MAX = 24;
    public static final float WATER_MULTIPLIER = 2.5f;
    public static final float WATER_PERLIN_DIVIDER = 1.75f;
    private static final int PIXEL_WEIGHT = MiddleEarthMapConfigs.PIXEL_WEIGHT;
    public static final ArrayList<Float> percentages = new ArrayList<Float>();
    private static MiddleEarthMapRuntime middleEarthMapRuntime;
    private static Float defaultWeightHeight = null;

    public MiddleEarthHeightMap(){
        middleEarthMapRuntime = MiddleEarthMapRuntime.getInstance();
    }

    private static float getImageHeight(int xWorld, int zWorld) {
        if(middleEarthMapRuntime == null) middleEarthMapRuntime = MiddleEarthMapRuntime.getInstance();

        Color color = middleEarthMapRuntime.getHeight(xWorld, zWorld);


        if(color != null){
            float blue = color.getBlue();

            float height = color.getRed();

            if(blue > 0) { // Water carver
                float percentage = (WATER_MAX - blue) / WATER_MAX;
                percentage = Math.max(0, Math.min(1, percentage));
                height *= percentage;
                height -= blue * WATER_MULTIPLIER;
            }
            return height;
        }
        return MEBiomesData.defaultBiome.height * WATER_MULTIPLIER;
    }

    public static double getPerlinHeight(int x, int z) {
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
            int green = middleEarthMapRuntime.getHeight(x, z).getGreen();
            perlin *= ((float)green) / 128f;
        } else {
            biomeHeight = getDefaultWeightHeight();
        }

        if(biomeHeight < 0) {
            perlin /= (Math.max(1, Math.min(5, Math.abs(biomeHeight / WATER_PERLIN_DIVIDER))));
        }

        if(biomeHeight >= MOUNTAIN_START_HEIGHT) {
            float multiplier = (biomeHeight / MOUNTAIN_START_HEIGHT) - 1;
            biomeHeight += biomeHeight * multiplier * MOUNTAIN_EXPONENTIAL_HEIGHT;
            perlin += multiplier * MOUNTAIN_EXPONENTIAL_HEIGHT * MOUNTAIN_HEIGHT_RANGE * BlendedNoise.noise((double) x / PERLIN_STRETCH_X2,  (double) z / PERLIN_STRETCH_Y2);
            perlin += multiplier * (MOUNTAIN_HEIGHT_RANGE / 2) * BlendedNoise.noise((double) (2 * x) / PERLIN_STRETCH_X2,  (double) (2 * z) / PERLIN_STRETCH_Y2);
        }

        additionalHeight = biomeHeight + perlin;

        return (float) additionalHeight;
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
            int x = 0;
            int z = 0;
            float topLeft = getImageHeight(x, z);

            float topRight = getImageHeight(x + PIXEL_WEIGHT, z);
            float bottomLeft = getImageHeight(x, z + PIXEL_WEIGHT);
            float bottomRight = getImageHeight(x + PIXEL_WEIGHT, z + PIXEL_WEIGHT);
            defaultWeightHeight =  getHeightBetween(new float[]{topLeft, topRight, bottomLeft, bottomRight},
                    (float) (x % PIXEL_WEIGHT) / PIXEL_WEIGHT, (float) (z % PIXEL_WEIGHT) / PIXEL_WEIGHT);
        }

        return defaultWeightHeight;
    }

    private static float getHeightBetween(float[] heights, float xPercent, float zPercent) {
        float h1 = getMiddleHeight(heights[0], heights[1], xPercent);
        float h2 = getMiddleHeight(heights[2], heights[3], xPercent);
        return getMiddleHeight(h1, h2, zPercent);
    }

    private static float getMiddleHeight(float a, float b, float percentage) {
        if(!percentages.contains(percentage)) percentages.add(percentage);
        float percentage2 = 1 - percentage;
        return (a * percentage2) + (b * percentage);
    }

    private static float getSmoothHeight(int x, int z) {
        float total = 0;
        for(int i = -SMOOTH_BRUSH_SIZE; i <= SMOOTH_BRUSH_SIZE; i++) {
            for(int j = -SMOOTH_BRUSH_SIZE; j <= SMOOTH_BRUSH_SIZE; j++) {
                if(MiddleEarthMapUtils.getInstance().isWorldCoordinateInBorder(x + i, z + j))
                    total += MEBiomesData.defaultBiome.height;
                else
                    total += getBiomeWeightHeight(x,z);
            }
        }

        return total / ((SMOOTH_BRUSH_SIZE * 2 + 1) * (SMOOTH_BRUSH_SIZE * 2 + 1));
    }

    public static float getHeight(int x, int z) {
        return getPerlinMapHeight(x, z);
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
