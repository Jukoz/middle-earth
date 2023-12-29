package net.jukoz.me.world.chunkgen.map;

import net.jukoz.me.utils.noises.BlendedNoise;
import net.jukoz.me.world.biomes.MEBiome;
import net.jukoz.me.world.biomes.MEBiomesData;
import net.jukoz.me.world.datas.WorldMapDatas;

import java.awt.*;

public class MiddleEarthHeightMap {
    public static final int SMOOTH_BRUSH_SIZE = 2;
    public static final int PERLIN_STRETCH_X = 210;
    public static final int PERLIN_STRETCH_Y = 180;
    public static final int PERLIN_STRETCH_X2 = 37;
    public static final int PERLIN_STRETCH_Y2 = 37;
    public static final int PERLIN_HEIGHT_RANGE = 33;
    public static final float MOUNTAIN_HEIGHT_RANGE = 8.6f;
    public static final float MOUNTAIN_HEIGHT_MULTIPLIER = 2.4f;
    public static final int MOUNTAIN_START_HEIGHT = 16; // Height depending on the Biome Data.
    public static final int PERLIN_HEIGHT_OFFSET = 8;
    public static final int STONE_HEIGHT = 50;
    public static final int HEIGHT = 8 + STONE_HEIGHT;
    public static final int DIRT_HEIGHT = 3 + HEIGHT;


    private static float getImageHeight(int xWorld, int zWorld) {

        int xMapCoordinate = Math.round((float) xWorld / 4);
        int zMapCoordinate = Math.round((float) zWorld / 4);

        if(!WorldMapDatas.isWorldCoordinateInBound(xWorld, zWorld)){
            return MEBiomesData.defaultBiome.height + getPerlinMapHeight(xMapCoordinate, zMapCoordinate);
        }

        Color color = WorldMapDatas.getHeight(xMapCoordinate, zMapCoordinate);

        if(color != null){
            float red = color.getRed();
            float blue = color.getBlue();
            float height = red - 25; //+ (color.getGreen() / 255f);
            if(blue > 180){
                height = red * -1;
            }
            else if(blue > 0 && blue > red){
                float newHeight = height * -1;
                newHeight -=(-newHeight) * (blue / 200);
                newHeight *= -1;
                newHeight = Math.max(newHeight, height);
                height = Math.max(-7, newHeight);
            }

            return height;
        }
        return 0;
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
        double additionalHeight;
        MEBiome meBiome;
        double perlin = getPerlinHeight(x, z);

        if(WorldMapDatas.isWorldCoordinateInBound(x, z)) {
            float biomeHeight = getImageHeight(x, z);
            if(biomeHeight >= MOUNTAIN_START_HEIGHT) {
                float multiplier = (biomeHeight / MOUNTAIN_START_HEIGHT) - 1;
                multiplier = MOUNTAIN_HEIGHT_MULTIPLIER * multiplier;
                perlin += multiplier * MOUNTAIN_HEIGHT_RANGE * BlendedNoise.noise((double) x / PERLIN_STRETCH_X2,  (double) z / PERLIN_STRETCH_Y2);
            }
            additionalHeight = biomeHeight + perlin;
        } else {
            meBiome = MEBiomesData.defaultBiome;
            additionalHeight = meBiome.height + perlin;
        }
        return (float) additionalHeight;
    }

    private static float getSmoothHeight(int x, int z) {
        float total = 0;
        for(int i = -SMOOTH_BRUSH_SIZE; i <= SMOOTH_BRUSH_SIZE; i++) {
            for(int j = -SMOOTH_BRUSH_SIZE; j <= SMOOTH_BRUSH_SIZE; j++) {
                if(!WorldMapDatas.isWorldCoordinateInBound(x + i, z + j)) total += MEBiomesData.defaultBiome.height;
                else total += getImageHeight(x,z);
            }
        }

        return total / ((SMOOTH_BRUSH_SIZE * 2 + 1) * (SMOOTH_BRUSH_SIZE * 2 + 1));
    }

    public static float getHeight(int x, int z) {
        return getSmoothHeight(x, z) + getPerlinMapHeight(x, z);
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
