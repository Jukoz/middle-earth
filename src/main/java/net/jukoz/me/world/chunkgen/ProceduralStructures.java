package net.jukoz.me.world.chunkgen;

import net.jukoz.me.block.StoneBlockSets;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.biomes.surface.MEBiome;
import net.jukoz.me.world.map.MiddleEarthMapConfigs;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.chunk.Chunk;

public class ProceduralStructures {
    private static final int mapMultiplier = (int) Math.pow(2, MiddleEarthMapConfigs.MAP_ITERATION + MiddleEarthMapConfigs.PIXEL_WEIGHT - 2);

    public static void generateStructures(MEBiome meBiome, Chunk chunk, int x, int y, int z) {
        if(meBiome.biome.isOf(MEBiomeKeys.NAN_CURUNIR.getRegistryRef())) generateIsengard(chunk, x, y, z);
    }

    // region Isengard
    public static final Vec2f centerOrthanc = new Vec2f(1402, 1464).multiply(mapMultiplier);
    private static final float radiusOrthanc = 10.12f;
    private static final float topRadiusOrthanc = 4.63f;
    private static final int bottomOrthanc = 72;
    private static final int topOrthanc = 182;

    public static final float isengardRingRadius = 6.7f * mapMultiplier;
    public static final float isengardRingThickness = 5;
    private static final float isengardWallsHeight = 18;
    private static final float isengardPathSize = 0.034f;
    private static final BlockState isengardBlock = StoneBlockSets.SMOOTH_EPMOSTO.base().getDefaultState();

    private static void generateIsengard(Chunk chunk, int x, int y, int z) {
        Vec2f coordinates = new Vec2f(x, z);
        float distance = (float) Math.sqrt(centerOrthanc.distanceSquared(coordinates));
        if(distance < radiusOrthanc) {
            for(int i = bottomOrthanc; i < topOrthanc; i++) {
                float ratio = (float) (i - bottomOrthanc) / (topOrthanc - bottomOrthanc);
                float currentRadius = radiusOrthanc * (1 - ratio) + (topRadiusOrthanc * ratio);
                if (distance < currentRadius) {
                    chunk.setBlockState(chunk.getPos().getBlockPos(x, i, z), isengardBlock, false);
                }
            }
        } else if(distance < isengardRingRadius + isengardRingThickness) {
            if(distance > isengardRingRadius - isengardRingThickness) {
                Vec2f direction = (centerOrthanc.add(coordinates.negate())).normalize();
                float dropHeight = Math.abs(distance - isengardRingRadius) / 3;

                float dotProduct = Math.abs(direction.dot(Vec2f.EAST_UNIT));
                if(dotProduct <= isengardPathSize && z > centerOrthanc.y) {
                    chunk.setBlockState(chunk.getPos().getBlockPos(x, y, z), isengardBlock, false);
                    float tunnel = (float) Math.pow(dotProduct + 1, 52);
                    for(int i = (int) (isengardWallsHeight - 2 - tunnel); i < (isengardWallsHeight - dropHeight); i++) {
                        chunk.setBlockState(chunk.getPos().getBlockPos(x, y + i, z), isengardBlock, false);
                    }
                } else {
                    for(int i = -1; i < (isengardWallsHeight - dropHeight); i++) {
                        chunk.setBlockState(chunk.getPos().getBlockPos(x, y + i, z), isengardBlock, false);
                    }
                }
            } else if(isInsideIsengard(x, z)) {
                Vec2f direction = (centerOrthanc.add(coordinates.negate())).normalize();
                if(Math.abs(direction.dot(Vec2f.EAST_UNIT)) <= isengardPathSize && z > centerOrthanc.y) {
                    chunk.setBlockState(chunk.getPos().getBlockPos(x, y, z), Blocks.DIRT_PATH.getDefaultState(), false);
                }
            }
        }
    }

    public static boolean isInsideIsengard(int x, int z) {
        Vec2f coordinates = new Vec2f(x, z);
        float distance = (float) Math.sqrt(centerOrthanc.distanceSquared(coordinates));
        return distance <= isengardRingRadius - isengardRingThickness;
    }
    // endregion
}
