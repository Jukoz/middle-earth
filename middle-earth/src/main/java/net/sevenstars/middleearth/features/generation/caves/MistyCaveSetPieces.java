package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.SkullBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.chunk.Chunk;
import net.sevenstars.middleearth.features.building.WoodBlockSetRegistryME;

final class MistyCaveSetPieces {
    private static final int ATTEMPTS = 4;
    private static final float MIN_WEIGHT = 0.9f;
    private static final int MIN_Y = -53; // the mithril band is -54 and below
    private static final int DEEP_Y = -30;
    private static final int MAX_FLOORS = 16;
    private static final int MAX_BUILT_Y = 60; // keeps kits off the high rift floors
    private static final int FLOOR_TRIES = 6;

    // out of 256, per attempt; the filters on top of these are in place()
    private static final int BONES_CHANCE = 24;
    private static final int SCAFFOLD_CHANCE = 16;
    private static final int KIT_CHANCE = 3;

    private static final Direction[] DIRECTIONS = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    private final Chunk chunk;
    private final MistyCaves caves;
    private final MistyCaves.Lake[] lakes;
    private final int[] surfaceY;
    private final float[] weights;
    private final int startX;
    private final int startZ;
    private final int topY;
    private final int[] floors = new int[MAX_FLOORS];
    private final BlockPos.Mutable pos = new BlockPos.Mutable();

    MistyCaveSetPieces(Chunk chunk, MistyCaves caves, MistyCaves.Lake[] lakes, int[] surfaceY, float[] weights) {
        this.chunk = chunk;
        this.caves = caves;
        this.lakes = lakes;
        this.surfaceY = surfaceY;
        this.weights = weights;
        this.startX = chunk.getPos().getStartX();
        this.startZ = chunk.getPos().getStartZ();
        this.topY = chunk.getTopYInclusive() - 1;
    }

    void place() {
        for (int attempt = 0; attempt < ATTEMPTS; attempt++) {
            int h = MistyCaves.mix(startX + caves.offsetX, startZ + caves.offsetZ, 7000 + attempt);
            int lx = (h >>> 8) & 15;
            int lz = (h >>> 12) & 15;
            if (weights[lx * 16 + lz] < MIN_WEIGHT) continue;
            int x = startX + lx;
            int z = startZ + lz;
            int roll = h & 0xFF;
            int bonesEnd = BONES_CHANCE;
            int scaffoldEnd = bonesEnd + SCAFFOLD_CHANCE;
            int kitEnd = scaffoldEnd + KIT_CHANCE;
            if (roll >= kitEnd) continue;
            int type = roll < bonesEnd ? 0 : roll < scaffoldEnd ? 1 : 2;

            boolean nearLake = false;
            if (type == 0) {
                for (MistyCaves.Lake lake : lakes) {
                    float nx = (x - lake.x) / lake.rx;
                    float nz = (z - lake.z) / lake.rz;
                    nearLake |= nx * nx + nz * nz < 2.4f;
                }
            }

            int count = pickFloors(x, z, surfaceY[lx * 16 + lz], type == 2 ? MAX_BUILT_Y : topY);
            if (count == 0) continue;
            int g = MistyCaves.mix(h, 1, 0);
            int first = ((h >>> 20) & 0xFFF) % count;
            int tries = type == 1 ? Math.min(count, FLOOR_TRIES) : 1;
            for (int i = 0; i < tries; i++) {
                int floor = floors[(first + i) % count];
                int seed = MistyCaves.mix(g, 2, i);
                boolean placed = false;
                switch (type) {
                    case 0 -> {
                        if (nearLake || floor < DEEP_Y || (g & 3) == 0) {
                            bones(x, floor, z, g);
                            placed = true;
                        }
                    }
                    case 1 -> placed = (floor >= 0 || (seed & 1) != 0) && scaffold(x, floor, z, seed);
                    default -> {
                        kit(x, floor, z, g);
                        placed = true;
                    }
                }
                if (placed) break;
            }
        }
    }

    // fills floors with air-above-a-full-block cells of the column
    private int pickFloors(int x, int z, int surface, int limit) {
        int count = 0;
        int top = Math.min(surface - 6, limit);
        for (int y = top; y >= MIN_Y && count < MAX_FLOORS; y--) {
            if (air(x, y, z) && solid(x, y - 1, z)) floors[count++] = y;
        }
        return count;
    }

    // BONES

    private void bones(int x, int y, int z, int h) {
        int count = 4 + ((h >>> 1) & 3);
        for (int i = 0; i < count; i++) {
            int g = MistyCaves.mix(h, 50, i);
            int px = x + (g & 0xFF) % 5 - 2;
            int pz = z + ((g >>> 8) & 0xFF) % 5 - 2;
            if (!air(px, y, pz) || !solid(px, y - 1, pz)) continue;
            int kind = (g >>> 16) & 7;
            if (kind < 3) {
                place(px, y, pz, Blocks.BONE_BLOCK.getDefaultState().with(PillarBlock.AXIS, Direction.Axis.values()[((g >>> 20) & 0xFF) % 3]));
            } else if (kind < 5) {
                place(px, y, pz, skull(g));
            } else {
                if (place(px, y, pz, Blocks.BONE_BLOCK.getDefaultState())) place(px, y + 1, pz, skull(g));
            }
        }
        int webs = 0;
        for (int dx = -3; dx <= 3 && webs < 2; dx++) {
            for (int dz = -3; dz <= 3 && webs < 2; dz++) {
                int wx = x + dx;
                int wz = z + dz;
                if ((MistyCaves.mix(h, 60 + dx, dz) & 7) != 0 || !air(wx, y, wz)) continue;
                boolean cornerX = solid(wx - 1, y, wz) || solid(wx + 1, y, wz);
                boolean cornerZ = solid(wx, y, wz - 1) || solid(wx, y, wz + 1);
                if (cornerX && cornerZ && place(wx, y, wz, Blocks.COBWEB.getDefaultState())) webs++;
            }
        }
    }

    private BlockState skull(int g) {
        return Blocks.SKELETON_SKULL.getDefaultState().with(SkullBlock.ROTATION, (g >>> 24) & 15);
    }

    // SCAVENGER SCAFFOLDS

    private boolean scaffold(int x, int y, int z, int h) {
        int start = h & 3;
        for (int t = 0; t < 4; t++) {
            if (scaffold(x, y, z, h, DIRECTIONS[(start + t) & 3])) return true;
        }
        return false;
    }

    private boolean scaffold(int x, int y, int z, int h, Direction toWall) {
        int dx = toWall.getOffsetX();
        int dz = toWall.getOffsetZ();
        int px = Math.abs(dz);
        int pz = Math.abs(dx);
        int wall = 1;
        while (wall <= 8 && air(x + dx * wall, y + 1, z + dz * wall)) wall++;
        if (wall < 2 || wall > 8 || !solid(x + dx * wall, y + 1, z + dz * wall)) return false;
        int length = 3 + ((h >>> 2) & 3);
        int from = -(length / 2);
        int height = 3 + ((h >>> 4) & 3);
        int platformY = y + height;
        int inner = wall - 1;
        int outer = wall - 2;
        int supported = 0;
        for (int u = 0; u < length; u++) {
            int off = from + u;
            for (int depth = outer; depth <= inner; depth++) {
                int cx = x + dx * depth + px * off;
                int cz = z + dz * depth + pz * off;
                for (int yy = platformY; yy <= platformY + 2; yy++) {
                    if (!air(cx, yy, cz)) return false;
                }
            }
            if (solid(x + dx * wall + px * off, platformY, z + dz * wall + pz * off)) supported++;
        }
        boolean[] post = new boolean[2];
        int posts = 0;
        for (int end = 0; end < 2; end++) {
            int off = end == 0 ? from : from + length - 1;
            int cx = x + dx * outer + px * off;
            int cz = z + dz * outer + pz * off;
            boolean ok = solid(cx, y - 1, cz);
            for (int yy = y; yy < platformY && ok; yy++) ok = air(cx, yy, cz);
            post[end] = ok;
            if (ok) posts++;
        }
        if (posts == 0 || supported < Math.max(1, length / 3)) return false;

        var wood = WoodBlockSetRegistryME.DEADWOOD_SET;
        BlockState log = wood.logBlocks.log().getDefaultState();
        BlockState planks = wood.planksBlocks.base().getDefaultState();
        for (int end = 0; end < 2; end++) {
            if (!post[end]) continue;
            int off = end == 0 ? from : from + length - 1;
            for (int yy = y; yy < platformY; yy++) place(x + dx * outer + px * off, yy, z + dz * outer + pz * off, log);
        }
        for (int u = 0; u < length; u++) {
            int off = from + u;
            for (int depth = outer; depth <= inner; depth++) place(x + dx * depth + px * off, platformY, z + dz * depth + pz * off, planks);
        }

        boolean ladder = false;
        for (int end = 0; end < 2 && !ladder; end++) {
            int off = end == 0 ? from - 1 : from + length;
            int cx = x + dx * inner + px * off;
            int cz = z + dz * inner + pz * off;
            boolean fits = true;
            for (int yy = y; yy <= platformY && fits; yy++) {
                fits = air(cx, yy, cz) && solid(cx + dx, yy, cz + dz);
            }
            if (!fits) continue;
            BlockState state = Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, toWall.getOpposite());
            for (int yy = y; yy <= platformY; yy++) place(cx, yy, cz, state);
            ladder = true;
        }
        if (!ladder) {
            int off = from + 1;
            int cx = x + dx * outer + px * off;
            int cz = z + dz * outer + pz * off;
            int chain = 2 + ((h >>> 5) & 1);
            for (int i = 1; i <= chain; i++) place(cx, platformY - i, cz, Blocks.IRON_CHAIN.getDefaultState());
            if (((h >>> 6) & 1) == 0) place(cx, platformY - chain - 1, cz, Blocks.LANTERN.getDefaultState().with(LanternBlock.HANGING, true));
        }

        int barrelOff = from + (MistyCaves.mix(h, 70, 0) & 0xFF) % length;
        place(x + dx * inner + px * barrelOff, platformY + 1, z + dz * inner + pz * barrelOff, Blocks.BARREL.getDefaultState());
        if (((h >>> 7) & 3) == 0) {
            int lampOff = from + (MistyCaves.mix(h, 71, 0) & 0xFF) % length;
            place(x + dx * outer + px * lampOff, platformY + 1, z + dz * outer + pz * lampOff, Blocks.LANTERN.getDefaultState());
        }
        return true;
    }

    // MINER'S KIT

    private void kit(int x, int y, int z, int h) {
        BlockState[] items = {
                (h & 1) == 0 ? Blocks.ANVIL.getDefaultState().with(AnvilBlock.FACING, DIRECTIONS[(h >>> 1) & 3])
                        : Blocks.CHIPPED_ANVIL.getDefaultState().with(AnvilBlock.FACING, DIRECTIONS[(h >>> 1) & 3]),
                Blocks.BARREL.getDefaultState(),
                Blocks.RAW_IRON_BLOCK.getDefaultState(),
                Blocks.COBBLESTONE.getDefaultState(),
                Blocks.COAL_ORE.getDefaultState()};
        int next = 0;
        int rotate = (h >>> 3) & 7;
        for (int i = 0; i < 9 && next < items.length; i++) {
            int cell = (i + rotate) % 9;
            int cx = x + cell % 3 - 1;
            int cz = z + cell / 3 - 1;
            if (!air(cx, y, cz) || !solid(cx, y - 1, cz)) continue;
            place(cx, y, cz, items[next]);
            if (next == 1) place(cx, y + 1, cz, Blocks.LANTERN.getDefaultState());
            next++;
        }
    }

    // WORLD ACCESS

    private boolean inside(int x, int z) {
        return x >= startX && x < startX + 16 && z >= startZ && z < startZ + 16;
    }

    private boolean air(int x, int y, int z) {
        return inside(x, z) && y >= MIN_Y && y <= topY && chunk.getBlockState(pos.set(x, y, z)).isAir();
    }

    private boolean solid(int x, int y, int z) {
        if (!inside(x, z) || y < chunk.getBottomY() || y > topY) return false;
        BlockState state = chunk.getBlockState(pos.set(x, y, z));
        return !state.isAir() && state.getFluidState().isEmpty() && state.isFullCube(chunk, pos);
    }

    private boolean place(int x, int y, int z, BlockState state) {
        if (!air(x, y, z)) return false;
        chunk.setBlockState(pos.set(x, y, z), state);
        // ProtoChunk does not create block entities itself
        if (state.hasBlockEntity() && state.getBlock() instanceof BlockEntityProvider provider) {
            BlockEntity entity = provider.createBlockEntity(pos.toImmutable(), state);
            if (entity != null) chunk.setBlockEntity(entity);
        }
        return true;
    }
}
