package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.chunk.Chunk;
import net.sevenstars.middleearth.features.generation.noise.SimplexNoise;

// river water and air written over the surface layers, a gravel bed and moss at the waterline; this chunk only
final class RiverCaveDressing {
    private static final BlockState WATER = Blocks.WATER.getDefaultState();
    private static final BlockState AIR = Blocks.AIR.getDefaultState();
    private static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
    private static final BlockState MOSS = Blocks.MOSSY_COBBLESTONE.getDefaultState();
    private static final int[][] SIDES = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private RiverCaveDressing() {
    }

    static void dress(RiverCaves.Near near, Chunk chunk, BlockBuffer blocks) {
        int startX = chunk.getPos().getStartX();
        int startZ = chunk.getPos().getStartZ();
        int lowY = Integer.MAX_VALUE;
        int highY = Integer.MIN_VALUE;
        for (RiverCaves.Piece p : near.pieces) {
            lowY = Math.min(lowY, p.water - 9);
            highY = Math.max(highY, p.water + 18);
        }
        // states from lowY - 1 to highY + 1, for the bed below and the moss beside
        int height = highY - lowY + 3;
        byte[] states = new byte[16 * 16 * height];
        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                int x = startX + lx;
                int z = startZ + lz;
                int column = (lx * 16 + lz) * height;
                for (int y = lowY - 1; y <= highY + 1; y++) states[column + y - lowY + 1] = (byte) near.state(x, y, z);
                for (int y = lowY; y <= highY; y++) {
                    int state = states[column + y - lowY + 1];
                    if (state == RiverCaves.WATER) {
                        blocks.put(x, y, z, WATER);
                    } else if (state == RiverCaves.AIR && !blocks.get(x, y, z).isAir()) {
                        blocks.put(x, y, z, AIR);
                    }
                }
            }
        }
        for (int lx = 0; lx < 16; lx++) {
            for (int lz = 0; lz < 16; lz++) {
                int x = startX + lx;
                int z = startZ + lz;
                int column = (lx * 16 + lz) * height;
                for (int y = lowY; y <= highY; y++) {
                    if (states[column + y - lowY + 1] != RiverCaves.WATER) continue;
                    if (states[column + y - lowY] == RiverCaves.NONE && solid(blocks.get(x, y - 1, z))
                            && SimplexNoise.noise((x + near.caves.noiseX) / 3.0, (z + near.caves.noiseZ) / 3.0) > -0.4) {
                        blocks.put(x, y - 1, z, GRAVEL);
                    }
                    for (int[] d : SIDES) {
                        int nlx = lx + d[0];
                        int nlz = lz + d[1];
                        if (nlx < 0 || nlx >= 16 || nlz < 0 || nlz >= 16) continue;
                        int nx = x + d[0];
                        int nz = z + d[1];
                        int neighbour = (nlx * 16 + nlz) * height;
                        for (int ny = y; ny <= y + 1; ny++) {
                            if (states[neighbour + ny - lowY + 1] != RiverCaves.NONE || !solid(blocks.get(nx, ny, nz))) continue;
                            if (SimplexNoise.noise((nx + near.caves.noiseZ) / 4.0, ny / 3.0, (nz + near.caves.noiseX) / 4.0) > 0.25) blocks.put(nx, ny, nz, MOSS);
                        }
                    }
                }
            }
        }
    }

    private static boolean solid(BlockState state) {
        return !state.isAir() && state.getFluidState().isEmpty();
    }
}
