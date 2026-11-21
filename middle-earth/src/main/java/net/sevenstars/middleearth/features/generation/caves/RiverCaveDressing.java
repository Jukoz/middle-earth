package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.chunk.Chunk;
import net.sevenstars.middleearth.features.generation.noise.SimplexNoise;

// the water and air of a river cave written again over whatever the surface layers put there, a gravel bed under the
// water and moss where it laps the rock. Every choice comes from a position, and only this chunk's blocks are written
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
            int water = water(p);
            lowY = Math.min(lowY, water - 9);
            highY = Math.max(highY, water + 18);
        }
        for (int x = startX; x < startX + 16; x++) {
            for (int z = startZ; z < startZ + 16; z++) {
                for (int y = lowY; y <= highY; y++) {
                    int state = near.state(x, y, z);
                    if (state == RiverCaves.WATER) {
                        blocks.put(x, y, z, WATER);
                    } else if (state == RiverCaves.AIR && !blocks.get(x, y, z).isAir()) {
                        blocks.put(x, y, z, AIR);
                    }
                }
            }
        }
        for (int x = startX; x < startX + 16; x++) {
            for (int z = startZ; z < startZ + 16; z++) {
                for (int y = lowY; y <= highY; y++) {
                    if (near.state(x, y, z) != RiverCaves.WATER) continue;
                    // the bed: gravel where the current left it
                    if (near.state(x, y - 1, z) == RiverCaves.NONE && solid(blocks.get(x, y - 1, z))
                            && SimplexNoise.noise((x + near.caves.noiseX) / 3.0, (z + near.caves.noiseZ) / 3.0) > -0.4) {
                        blocks.put(x, y - 1, z, GRAVEL);
                    }
                    // moss along the waterline
                    for (int[] d : SIDES) {
                        int nx = x + d[0];
                        int nz = z + d[1];
                        if (nx < startX || nx >= startX + 16 || nz < startZ || nz >= startZ + 16) continue;
                        for (int ny = y; ny <= y + 1; ny++) {
                            if (near.state(nx, ny, nz) != RiverCaves.NONE || !solid(blocks.get(nx, ny, nz))) continue;
                            if (SimplexNoise.noise((nx + near.caves.noiseZ) / 4.0, ny / 3.0, (nz + near.caves.noiseX) / 4.0) > 0.25) blocks.put(nx, ny, nz, MOSS);
                        }
                    }
                }
            }
        }
    }

    private static int water(RiverCaves.Piece p) {
        return switch (p) {
            case RiverCaves.Segment s -> s.water;
            case RiverCaves.Lake l -> l.water;
            case RiverCaves.Pool pool -> pool.water;
            default -> 64;
        };
    }

    private static boolean solid(BlockState state) {
        return !state.isAir() && state.getFluidState().isEmpty();
    }
}
