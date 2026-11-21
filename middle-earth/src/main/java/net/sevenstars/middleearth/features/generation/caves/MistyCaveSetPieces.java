package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.chunk.Chunk;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.block.special.SkeletonBlock;
import net.sevenstars.middleearth.features.generation.biomes.caves.DepthBands;

// the remains of those who came this way: skull piles, bone piles, and someone sat down against a wall
final class MistyCaveSetPieces {
    private static final int ATTEMPTS = 4;
    private static final float MIN_WEIGHT = 0.9f;
    private static final int FLOOR_CLEARANCE = 5; // above the deep floor
    private static final int MAX_FLOORS = 16;
    private static final int FLOOR_TRIES = 6;
    private static final int CHUNK = 16;
    private static final int WALL_SEARCH = 3;

    private static final int SKULLS = 0;
    private static final int BONES = 1;
    private static final int SITTER = 2;

    // out of 256, per attempt
    private static final int SKULLS_CHANCE = 6;
    private static final int BONES_CHANCE = 8;
    private static final int SITTER_CHANCE = 6;

    // skeleton poses: 1-3 sit, 4 collapsed with its skull, 5-6 ribcages, 7 loose bones, 8-9 skulls
    private static final int[] SITTING = {1, 2, 3};
    private static final int[] REMAINS = {4, 5, 6, 7};
    private static final int[] HEAPED_SKULLS = {8, 9};

    private static final Direction[] DIRECTIONS = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    private final Chunk chunk;
    private final BlockBuffer blocks;
    private final MistyCaves caves;
    private final int[] surfaceY;
    private final float[] weights;
    private final DepthBands.Edges[] edges;
    private final int startX;
    private final int startZ;
    private final int topY;
    private final int[] floors = new int[MAX_FLOORS];
    private final BlockPos.Mutable pos = new BlockPos.Mutable();

    MistyCaveSetPieces(Chunk chunk, BlockBuffer blocks, MistyCaves caves, int[] surfaceY, float[] weights, DepthBands.Edges[] edges) {
        this.chunk = chunk;
        this.blocks = blocks;
        this.caves = caves;
        this.surfaceY = surfaceY;
        this.weights = weights;
        this.edges = edges;
        this.startX = chunk.getPos().getStartX();
        this.startZ = chunk.getPos().getStartZ();
        this.topY = chunk.getTopYInclusive() - 1;
    }

    void place() {
        for (int attempt = 0; attempt < ATTEMPTS; attempt++) {
            int h = MistyCaves.mix(startX + caves.offsetX, startZ + caves.offsetZ, 7000 + attempt);
            int lx = (h >>> 8) & 15;
            int lz = (h >>> 12) & 15;
            if (weights[lx * CHUNK + lz] < MIN_WEIGHT) continue;
            int roll = h & 0xFF;
            if (roll >= SKULLS_CHANCE + BONES_CHANCE + SITTER_CHANCE) continue;
            int type = roll < SKULLS_CHANCE ? SKULLS : roll < SKULLS_CHANCE + BONES_CHANCE ? BONES : SITTER;
            DepthBands.Edges column = edges[lx * CHUNK + lz];
            if (column == null) continue;
            attempt(h, type, startX + lx, startZ + lz, lx * CHUNK + lz, column);
        }
    }

    private void attempt(int h, int type, int x, int z, int index, DepthBands.Edges column) {
        int count = pickFloors(x, z, surfaceY[index], column.floor() + FLOOR_CLEARANCE, topY);
        if (count == 0) return;
        int g = MistyCaves.mix(h, 1, 0);
        int first = ((h >>> 20) & 0xFFF) % count;
        for (int i = 0; i < Math.min(count, FLOOR_TRIES); i++) {
            int floor = floors[(first + i) % count];
            int seed = MistyCaves.mix(g, 2, i);
            boolean placed = switch (type) {
                case SKULLS -> pile(x, floor, z, seed, HEAPED_SKULLS, 1);
                case BONES -> pile(x, floor, z, seed, REMAINS, 2);
                default -> sitter(x, floor, z, seed);
            };
            if (placed) break;
        }
    }

    // fills floors with air-above-a-full-block cells of the column
    private int pickFloors(int x, int z, int surface, int low, int high) {
        int count = 0;
        int top = Math.min(surface - 6, high);
        for (int y = top; y >= low && count < MAX_FLOORS; y--) {
            if (air(x, y, z) && solid(x, y - 1, z)) floors[count++] = y;
        }
        return count;
    }

    // a few of the given poses scattered round a spot
    private boolean pile(int x, int y, int z, int h, int[] poses, int least) {
        int count = least + ((h >>> 1) & 1) + ((h >>> 2) & 1);
        int placed = 0;
        for (int i = 0; i < count * 3 && placed < count; i++) {
            int g = MistyCaves.mix(h, 50, i);
            int px = x + (g & 0xFF) % 3 - 1;
            int pz = z + ((g >>> 8) & 0xFF) % 3 - 1;
            if (!air(px, y, pz) || !solid(px, y - 1, pz)) continue;
            if (place(px, y, pz, skeleton(poses[((g >>> 16) & 0xFF) % poses.length], DIRECTIONS[(g >>> 24) & 3]))) placed++;
        }
        return placed > 0;
    }

    // someone sat against the rock and never got up, loose bones beside them
    private boolean sitter(int x, int y, int z, int h) {
        for (int i = 0; i < 16; i++) {
            int g = MistyCaves.mix(h, 80, i);
            int px = x + (g & 0xFF) % (2 * WALL_SEARCH + 1) - WALL_SEARCH;
            int pz = z + ((g >>> 8) & 0xFF) % (2 * WALL_SEARCH + 1) - WALL_SEARCH;
            if (!air(px, y, pz) || !air(px, y + 1, pz) || !solid(px, y - 1, pz)) continue;
            int start = (g >>> 16) & 3;
            for (int d = 0; d < 4; d++) {
                Direction toWall = DIRECTIONS[(start + d) & 3];
                if (!solid(px + toWall.getOffsetX(), y, pz + toWall.getOffsetZ()) || !solid(px + toWall.getOffsetX(), y + 1, pz + toWall.getOffsetZ())) continue;
                place(px, y, pz, skeleton(SITTING[((g >>> 20) & 0xFF) % SITTING.length], toWall.getOpposite()));
                Direction aside = toWall.rotateYClockwise();
                int bx = px + aside.getOffsetX() * (((g >>> 28) & 1) == 0 ? 1 : -1);
                int bz = pz + aside.getOffsetZ() * (((g >>> 28) & 1) == 0 ? 1 : -1);
                if (((g >>> 29) & 1) == 0 && solid(bx, y - 1, bz)) place(bx, y, bz, skeleton(7, DIRECTIONS[(g >>> 30) & 3]));
                return true;
            }
        }
        return false;
    }

    static BlockState skeleton(int pose, Direction facing) {
        return DecorativeBlockRegistryME.SKELETON.getDefaultState().with(SkeletonBlock.POSE, pose).with(SkeletonBlock.HORIZONTAL_FACING, facing);
    }

    // WORLD ACCESS

    private boolean inside(int x, int z) {
        return x >= startX && x < startX + 16 && z >= startZ && z < startZ + 16;
    }

    private boolean air(int x, int y, int z) {
        return inside(x, z) && y >= chunk.getBottomY() && y <= topY && blocks.get(x, y, z).isAir();
    }

    private boolean solid(int x, int y, int z) {
        if (!inside(x, z) || y < chunk.getBottomY() || y > topY) return false;
        BlockState state = blocks.get(x, y, z);
        return !state.isAir() && state.getFluidState().isEmpty() && state.isFullCube(chunk, pos.set(x, y, z));
    }

    private boolean place(int x, int y, int z, BlockState state) {
        if (!air(x, y, z)) return false;
        blocks.put(x, y, z, state);
        return true;
    }
}
