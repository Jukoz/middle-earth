package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;
import net.sevenstars.middleearth.features.generation.noise.SimplexNoise;

public final class MistyCaveDecorator {
    private static final int ICE_DEPTH = 28; // blocks below the surface
    private static final float ICE_MIN_WEIGHT = 0.5f;

    private static final float WORM_INNER = 0.8f; // squared share of the lake radius
    private static final float WORM_CLUSTER = 0.45f;
    private static final int WORM_CHANCE = 60; // out of 256, inside a cluster

    private MistyCaveDecorator() {
    }

    // weights and surface heights are indexed local x * 16 + local z
    public static void decorate(Chunk chunk, MistyCaves caves, MistyCaves.Lake[] lakes, int[] surfaceY, float[] weights) {
        int startX = chunk.getPos().getStartX();
        int startZ = chunk.getPos().getStartZ();
        BlockPos.Mutable pos = new BlockPos.Mutable();
        for (MistyCaves.Lake lake : lakes) glowWorms(chunk, caves, lake, startX, startZ, pos);
        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                if (weights[lx * 16 + lz] < ICE_MIN_WEIGHT) continue;
                ice(chunk, caves, startX + lx, startZ + lz, lx, lz, surfaceY[lx * 16 + lz], pos);
            }
        }
        new MistyCaveSetPieces(chunk, caves, lakes, surfaceY, weights).place();
    }

    private static void glowWorms(Chunk chunk, MistyCaves caves, MistyCaves.Lake lake, int startX, int startZ, BlockPos.Mutable pos) {
        BlockState worm = NatureBlockRegistryME.GLOWWORM_MAIN.getDefaultState();
        BlockState webbing = NatureBlockRegistryME.GLOWWORM_WEBBING.getDefaultState();
        int top = Math.min(lake.waterTop + (int) lake.ceilingMax() + 3, chunk.getTopYInclusive() - 1);
        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                int x = startX + lx;
                int z = startZ + lz;
                float nx = (x - lake.x) / lake.rx;
                float nz = (z - lake.z) / lake.rz;
                if (nx * nx + nz * nz > WORM_INNER) continue;
                float cluster = (float) SimplexNoise.noise((x + caves.offsetX) / 11f, (z + caves.offsetZ) / 11f);
                if (cluster < WORM_CLUSTER) continue;
                int h = MistyCaves.mix(x + caves.offsetX, z + caves.offsetZ, 4801);
                if ((h & 0xFF) >= WORM_CHANCE) continue;
                for (int y = lake.waterTop + 1; y <= top; y++) {
                    if (!chunk.getBlockState(pos.set(x, y, z)).isAir()) break;
                    BlockState above = chunk.getBlockState(pos.set(x, y + 1, z));
                    if (above.isAir() || !above.getFluidState().isEmpty()) continue;
                    if (!above.isFullCube(chunk, pos)) break;
                    int length = 1 + ((h >>> 8) & 0xFF) % 6;
                    for (int i = 0; i < length; i++) {
                        pos.set(x, y - i, z);
                        if (y - i <= lake.waterTop || !chunk.getBlockState(pos).isAir()) break;
                        chunk.setBlockState(pos, i == length - 1 ? webbing : worm);
                    }
                    break;
                }
            }
        }
    }

    private static void ice(Chunk chunk, MistyCaves caves, int x, int z, int lx, int lz, int surface, BlockPos.Mutable pos) {
        int bottom = Math.max(chunk.getBottomY() + 1, surface - ICE_DEPTH);
        int top = Math.min(surface - 2, chunk.getTopYInclusive() - 2);
        // the block above is read once and carried to the next step; it is replaced when a snow layer goes there
        BlockState carried = null;
        for (int y = bottom; y <= top; y++) {
            BlockState state = carried != null ? carried : stateAt(chunk, lx, y, lz);
            carried = null;
            if (state.isAir() || !state.getFluidState().isEmpty() || !state.isFullCube(chunk, pos.set(x, y, z))) continue;
            BlockState above = stateAt(chunk, lx, y + 1, lz);
            carried = above;
            boolean floor = above.isAir();
            boolean wall = !floor
                    && ((lx > 0 && stateAt(chunk, lx - 1, y, lz).isAir())
                    || (lx < 15 && stateAt(chunk, lx + 1, y, lz).isAir())
                    || (lz > 0 && stateAt(chunk, lx, y, lz - 1).isAir())
                    || (lz < 15 && stateAt(chunk, lx, y, lz + 1).isAir()));
            if (!floor && !wall) continue;
            float cold = 1 - (surface - y) / (float) ICE_DEPTH;
            float patch = (float) SimplexNoise.noise((x + caves.offsetX) / 7f, (y + caves.offsetY) / 7f, (z + caves.offsetZ) / 7f);
            if (patch < -0.35f) continue;
            int h = MistyCaves.mix(x + caves.offsetX, y + caves.offsetY, z + caves.offsetZ);
            float roll = (h & 0xFF) / 255f;
            boolean pick = ((h >>> 8) & 0xFF) < 115;
            BlockState result = null;
            if (floor) {
                if (roll < cold * 0.85f) result = pick ? Blocks.PACKED_ICE.getDefaultState() : Blocks.SNOW_BLOCK.getDefaultState();
            } else if (roll < cold * 0.35f) {
                result = pick ? Blocks.PACKED_ICE.getDefaultState() : Blocks.SNOW_BLOCK.getDefaultState();
            }
            if (result == null) continue;
            chunk.setBlockState(pos.set(x, y, z), result);
            // layers do not survive on ice
            if (floor && result.isOf(Blocks.SNOW_BLOCK) && ((h >>> 16) & 0xFF) < 150) {
                int layers = 1 + ((h >>> 24) & 0xFF) % 3;
                carried = Blocks.SNOW.getDefaultState().with(SnowBlock.LAYERS, layers);
                chunk.setBlockState(pos.set(x, y + 1, z), carried);
            }
        }
    }

    private static BlockState stateAt(Chunk chunk, int lx, int y, int lz) {
        ChunkSection section = chunk.getSection(chunk.getSectionIndex(y));
        return section.isEmpty() ? Blocks.AIR.getDefaultState() : section.getBlockState(lx, y & 15, lz);
    }
}
