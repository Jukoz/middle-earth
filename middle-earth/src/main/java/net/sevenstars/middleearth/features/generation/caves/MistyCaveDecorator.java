package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;
import net.sevenstars.middleearth.features.generation.biomes.caves.DepthBands;
import net.sevenstars.middleearth.features.generation.chunkgen.MiddleEarthChunkGenerator;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;
import net.sevenstars.middleearth.features.generation.noise.SimplexNoise;

public final class MistyCaveDecorator {
    private static final float ICE_MIN_WEIGHT = 0.5f;

    private static final float WORM_INNER = 0.8f; // squared share of the lake radius
    private static final float WORM_CLUSTER = 0.45f;
    private static final int WORM_CHANCE = 60; // out of 256, inside a cluster
    private static final float WORM_CLUSTER_SCALE = 11;
    private static final int WORM_HASH_SALT = 4801;
    private static final int WORM_MAX_LENGTH = 6;

    private static final float ICE_PATCH_SCALE = 7;
    private static final float ICE_PATCH_MIN = -0.35f;
    private static final float ICE_FLOOR_COLDNESS = 0.85f; // chance per unit of cold
    private static final float ICE_WALL_COLDNESS = 0.35f;
    private static final int ICE_PACKED_CHANCE = 115; // out of 256, else snow
    private static final int SNOW_LAYER_CHANCE = 150; // out of 256, on a snow block floor
    private static final int SNOW_MAX_LAYERS = 3;
    private static final int CHUNK = 16;

    private MistyCaveDecorator() {
    }

    // weights and surface heights are indexed local x * 16 + local z
    public static void decorate(Chunk chunk, BlockBuffer blocks, MistyCaves caves, Lake[] lakes, int[] surfaceY, float[] weights, Entrance[] entrances) {
        int startX = chunk.getPos().getStartX();
        int startZ = chunk.getPos().getStartZ();
        BlockPos.Mutable pos = new BlockPos.Mutable();
        for (Lake lake : lakes) glowWorms(chunk, blocks, caves, lake, startX, startZ, pos);
        DepthBands.Edges[] edges = new DepthBands.Edges[CHUNK * CHUNK];
        for (int lx = 0; lx < CHUNK; lx++) {
            for (int lz = 0; lz < CHUNK; lz++) {
                int index = lx * CHUNK + lz;
                if (weights[index] < ICE_MIN_WEIGHT) continue;
                int x = startX + lx;
                int z = startZ + lz;
                edges[index] = DepthBands.Edges.of(surfaceY[index], DepthBands.deepFloor(MiddleEarthMapRuntime.getInstance(), x, z, caves.seed), MiddleEarthChunkGenerator.caveBlendNoise(x, z, caves.seed));
                ice(chunk, blocks, caves, x, z, lx, lz, surfaceY[index], edges[index].upperBottom(), pos, entrances);
            }
        }
        new MistyCaveSetPieces(chunk, blocks, caves, lakes, surfaceY, weights, edges).place();
    }

    private static void glowWorms(Chunk chunk, BlockBuffer blocks, MistyCaves caves, Lake lake, int startX, int startZ, BlockPos.Mutable pos) {
        BlockState worm = NatureBlockRegistryME.GLOWWORM_MAIN.getDefaultState();
        BlockState webbing = NatureBlockRegistryME.GLOWWORM_WEBBING.getDefaultState();
        int top = Math.min(lake.waterTop + (int) lake.ceilingMax() + 3, chunk.getTopYInclusive() - 1);
        for (int lx = 0; lx < CHUNK; lx++) {
            for (int lz = 0; lz < CHUNK; lz++) {
                int x = startX + lx;
                int z = startZ + lz;
                float nx = (x - lake.x) / lake.rx;
                float nz = (z - lake.z) / lake.rz;
                if (nx * nx + nz * nz > WORM_INNER) continue;
                float cluster = (float) SimplexNoise.noise((x + caves.offsetX) / WORM_CLUSTER_SCALE, (z + caves.offsetZ) / WORM_CLUSTER_SCALE);
                if (cluster < WORM_CLUSTER) continue;
                int h = MistyCaves.mix(x + caves.offsetX, z + caves.offsetZ, WORM_HASH_SALT);
                if ((h & 0xFF) >= WORM_CHANCE) continue;
                for (int y = lake.waterTop + 1; y <= top; y++) {
                    if (!blocks.get(x, y, z).isAir()) break;
                    BlockState above = blocks.get(x, y + 1, z);
                    pos.set(x, y + 1, z);
                    if (above.isAir() || !above.getFluidState().isEmpty()) continue;
                    if (!above.isFullCube(chunk, pos)) break;
                    int length = 1 + ((h >>> 8) & 0xFF) % WORM_MAX_LENGTH;
                    for (int i = 0; i < length; i++) {
                        pos.set(x, y - i, z);
                        if (y - i <= lake.waterTop || !blocks.get(x, y - i, z).isAir()) break;
                        blocks.put(x, y - i, z, i == length - 1 ? webbing : worm);
                    }
                    break;
                }
            }
        }
    }

    // entrances dress their own rock
    private static boolean entranceRock(Entrance[] entrances, int x, int y, int z) {
        for (Entrance entrance : entrances) {
            if (!entrance.inBox(x, y, z)) continue;
            if (entrance.carves(x, y + 1, z) || entrance.carves(x + 1, y, z) || entrance.carves(x - 1, y, z)
                    || entrance.carves(x, y, z + 1) || entrance.carves(x, y, z - 1)) return true;
        }
        return false;
    }

    // the cold reaches down through the upper band, fading out at its bottom
    private static void ice(Chunk chunk, BlockBuffer blocks, MistyCaves caves, int x, int z, int lx, int lz, int surface, int upperBottom, BlockPos.Mutable pos, Entrance[] entrances) {
        int bottom = Math.max(chunk.getBottomY() + 1, upperBottom);
        int depth = Math.max(1, surface - upperBottom);
        int top = Math.min(surface - 2, chunk.getTopYInclusive() - 2);
        // the block above is read once and carried to the next step; it is replaced when a snow layer goes there
        BlockState carried = null;
        for (int y = bottom; y <= top; y++) {
            BlockState state = carried != null ? carried : blocks.get(x, y, z);
            carried = null;
            if (state.isAir() || !state.getFluidState().isEmpty() || !state.isFullCube(chunk, pos.set(x, y, z))) continue;
            BlockState above = blocks.get(x, y + 1, z);
            carried = above;
            boolean floor = above.isAir();
            boolean wall = !floor
                    && ((lx > 0 && blocks.get(x - 1, y, z).isAir())
                    || (lx < 15 && blocks.get(x + 1, y, z).isAir())
                    || (lz > 0 && blocks.get(x, y, z - 1).isAir())
                    || (lz < 15 && blocks.get(x, y, z + 1).isAir()));
            if (!floor && !wall) continue;
            if (entrances.length > 0 && entranceRock(entrances, x, y, z)) continue;
            float cold = 1 - (surface - y) / (float) depth;
            float patch = (float) SimplexNoise.noise((x + caves.offsetX) / ICE_PATCH_SCALE, (y + caves.offsetY) / ICE_PATCH_SCALE, (z + caves.offsetZ) / ICE_PATCH_SCALE);
            if (patch < ICE_PATCH_MIN) continue;
            int h = MistyCaves.mix(x + caves.offsetX, y + caves.offsetY, z + caves.offsetZ);
            BlockState result = iceBlock(h, cold, floor);
            if (result == null) continue;
            blocks.put(x, y, z, result);
            // layers do not survive on ice
            if (floor && result.isOf(Blocks.SNOW_BLOCK) && ((h >>> 16) & 0xFF) < SNOW_LAYER_CHANCE) {
                int layers = 1 + ((h >>> 24) & 0xFF) % SNOW_MAX_LAYERS;
                carried = Blocks.SNOW.getDefaultState().with(SnowBlock.LAYERS, layers);
                blocks.put(x, y + 1, z, carried);
            }
        }
    }

    // null where the roll leaves the rock bare
    private static BlockState iceBlock(int h, float cold, boolean floor) {
        float roll = (h & 0xFF) / 255f;
        boolean packed = ((h >>> 8) & 0xFF) < ICE_PACKED_CHANCE;
        if (roll >= cold * (floor ? ICE_FLOOR_COLDNESS : ICE_WALL_COLDNESS)) return null;
        return packed ? Blocks.PACKED_ICE.getDefaultState() : Blocks.SNOW_BLOCK.getDefaultState();
    }
}
