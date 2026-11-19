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
import net.sevenstars.middleearth.features.generation.biomes.caves.DeepThemes;
import net.sevenstars.middleearth.features.generation.biomes.caves.DepthBands;

final class MistyCaveSetPieces {
    private static final int ATTEMPTS = 4;
    private static final float MIN_WEIGHT = 0.9f;
    private static final int FLOOR_CLEARANCE = 5; // above the deep floor
    private static final float SCAFFOLD_MIDDLE_SHARE = 0.2f; // the top of the middle band that scaffolds reach into
    private static final int MAX_FLOORS = 16;
    private static final int FLOOR_TRIES = 6;
    private static final int CHUNK = 16;
    private static final float NEAR_LAKE_RADIUS_SQ = 2.4f; // in lake radii, squared
    private static final int MAX_WALL_DISTANCE = 8;

    private static final int BONES = 0;
    private static final int SCAFFOLD = 1;
    private static final int KIT = 2;

    // out of 256, per attempt; the filters on top of these are in place()
    private static final int BONES_CHANCE = 24;
    private static final int SCAFFOLD_CHANCE = 16;
    private static final int KIT_CHANCE = 3;

    private static final Direction[] DIRECTIONS = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    private final Chunk chunk;
    private final BlockBuffer blocks;
    private final MistyCaves caves;
    private final Lake[] lakes;
    private final int[] surfaceY;
    private final float[] weights;
    private final DepthBands.Edges[] edges;
    private final int startX;
    private final int startZ;
    private final int topY;
    private final int[] floors = new int[MAX_FLOORS];
    private final BlockPos.Mutable pos = new BlockPos.Mutable();

    MistyCaveSetPieces(Chunk chunk, BlockBuffer blocks, MistyCaves caves, Lake[] lakes, int[] surfaceY, float[] weights, DepthBands.Edges[] edges) {
        this.chunk = chunk;
        this.blocks = blocks;
        this.caves = caves;
        this.lakes = lakes;
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
            if (roll >= BONES_CHANCE + SCAFFOLD_CHANCE + KIT_CHANCE) continue;
            int type = roll < BONES_CHANCE ? BONES : roll < BONES_CHANCE + SCAFFOLD_CHANCE ? SCAFFOLD : KIT;
            DepthBands.Edges column = edges[lx * CHUNK + lz];
            if (column == null) continue;
            attempt(h, type, startX + lx, startZ + lz, lx * CHUNK + lz, column);
        }
    }

    private void attempt(int h, int type, int x, int z, int index, DepthBands.Edges column) {
        boolean nearLake = type == BONES && nearLake(x, z);
        // bones in the middle band and the deeps, scaffolds in the upper band and the top of the middle one, kits in the middle and mithril bands
        int low = switch (type) {
            case BONES -> column.floor() + FLOOR_CLEARANCE;
            case SCAFFOLD -> column.top(DepthBands.Band.MIDDLE) - (int) (SCAFFOLD_MIDDLE_SHARE * column.thickness(DepthBands.Band.MIDDLE));
            default -> column.mithrilBottom();
        };
        int high = type == SCAFFOLD ? topY : column.top(DepthBands.Band.MIDDLE) - 1;
        int count = pickFloors(x, z, surfaceY[index], low, high);
        if (count == 0) return;
        int g = MistyCaves.mix(h, 1, 0);
        int first = ((h >>> 20) & 0xFFF) % count;
        int tries = type == SCAFFOLD ? Math.min(count, FLOOR_TRIES) : 1;
        for (int i = 0; i < tries; i++) {
            int floor = floors[(first + i) % count];
            int seed = MistyCaves.mix(g, 2, i);
            boolean placed = switch (type) {
                case BONES -> maybeBones(x, floor, z, g, column, nearLake);
                case SCAFFOLD -> (floor >= column.upperBottom() || (seed & 1) != 0) && scaffold(x, floor, z, seed);
                default -> {
                    kit(x, floor, z, g);
                    yield true;
                }
            };
            if (placed) break;
        }
    }

    private boolean nearLake(int x, int z) {
        boolean near = false;
        for (Lake lake : lakes) {
            float nx = (x - lake.x) / lake.rx;
            float nz = (z - lake.z) / lake.rz;
            near |= nx * nx + nz * nz < NEAR_LAKE_RADIUS_SQ;
        }
        return near;
    }

    private boolean maybeBones(int x, int floor, int z, int g, DepthBands.Edges column, boolean nearLake) {
        // only the gnawed deeps have bones
        boolean foreign = floor < column.mithrilBottom() && column.floor() < DepthBands.SHALLOW_FLOOR && DeepThemes.at(x, z, caves.seed) != DeepThemes.Theme.GNAWED;
        if (foreign || !(nearLake || floor < column.mithrilBottom() || (g & 3) == 0)) return false;
        bones(x, floor, z, g);
        return true;
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

    // The platform against the wall, in a frame of "depth" towards the wall and "off" along it
    private record Frame(int x, int y, int z, Direction toWall, int from, int length, int platformY, int inner, int outer) {
        int dx() {
            return toWall.getOffsetX();
        }

        int dz() {
            return toWall.getOffsetZ();
        }

        int cx(int depth, int off) {
            return x + dx() * depth + Math.abs(dz()) * off;
        }

        int cz(int depth, int off) {
            return z + dz() * depth + Math.abs(dx()) * off;
        }

        int endOff(int end) {
            return end == 0 ? from : from + length - 1;
        }
    }

    private boolean scaffold(int x, int y, int z, int h, Direction toWall) {
        int dx = toWall.getOffsetX();
        int dz = toWall.getOffsetZ();
        int wall = 1;
        while (wall <= MAX_WALL_DISTANCE && air(x + dx * wall, y + 1, z + dz * wall)) wall++;
        if (wall < 2 || wall > MAX_WALL_DISTANCE || !solid(x + dx * wall, y + 1, z + dz * wall)) return false;
        int length = 3 + ((h >>> 2) & 3);
        int height = 3 + ((h >>> 4) & 3);
        Frame frame = new Frame(x, y, z, toWall, -(length / 2), length, y + height, wall - 1, wall - 2);
        int supported = deckSupport(frame, wall);
        if (supported < 0) return false;
        boolean[] post = new boolean[2];
        int posts = 0;
        for (int end = 0; end < 2; end++) {
            post[end] = postFits(frame, frame.endOff(end));
            if (post[end]) posts++;
        }
        if (posts == 0 || supported < Math.max(1, length / 3)) return false;

        buildPosts(frame, post);
        buildDeck(frame);
        if (!buildLadder(frame)) hangChain(frame, h);
        furnish(frame, h);
        return true;
    }

    // blocks of the deck that touch the wall, or -1 where something is in the way
    private int deckSupport(Frame f, int wall) {
        int supported = 0;
        for (int u = 0; u < f.length; u++) {
            int off = f.from + u;
            for (int depth = f.outer; depth <= f.inner; depth++) {
                for (int yy = f.platformY; yy <= f.platformY + 2; yy++) {
                    if (!air(f.cx(depth, off), yy, f.cz(depth, off))) return -1;
                }
            }
            if (solid(f.cx(wall, off), f.platformY, f.cz(wall, off))) supported++;
        }
        return supported;
    }

    private boolean postFits(Frame f, int off) {
        int cx = f.cx(f.outer, off);
        int cz = f.cz(f.outer, off);
        boolean ok = solid(cx, f.y - 1, cz);
        for (int yy = f.y; yy < f.platformY && ok; yy++) ok = air(cx, yy, cz);
        return ok;
    }

    private void buildPosts(Frame f, boolean[] post) {
        BlockState log = WoodBlockSetRegistryME.DEADWOOD_SET.logBlocks.log().getDefaultState();
        for (int end = 0; end < 2; end++) {
            if (!post[end]) continue;
            int off = f.endOff(end);
            for (int yy = f.y; yy < f.platformY; yy++) place(f.cx(f.outer, off), yy, f.cz(f.outer, off), log);
        }
    }

    private void buildDeck(Frame f) {
        BlockState planks = WoodBlockSetRegistryME.DEADWOOD_SET.planksBlocks.base().getDefaultState();
        for (int u = 0; u < f.length; u++) {
            int off = f.from + u;
            for (int depth = f.outer; depth <= f.inner; depth++) place(f.cx(depth, off), f.platformY, f.cz(depth, off), planks);
        }
    }

    // beside either end of the deck, against the wall
    private boolean buildLadder(Frame f) {
        for (int end = 0; end < 2; end++) {
            int off = end == 0 ? f.from - 1 : f.from + f.length;
            int cx = f.cx(f.inner, off);
            int cz = f.cz(f.inner, off);
            boolean fits = true;
            for (int yy = f.y; yy <= f.platformY && fits; yy++) {
                fits = air(cx, yy, cz) && solid(cx + f.dx(), yy, cz + f.dz());
            }
            if (!fits) continue;
            BlockState state = Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, f.toWall.getOpposite());
            for (int yy = f.y; yy <= f.platformY; yy++) place(cx, yy, cz, state);
            return true;
        }
        return false;
    }

    private void hangChain(Frame f, int h) {
        int off = f.from + 1;
        int cx = f.cx(f.outer, off);
        int cz = f.cz(f.outer, off);
        int chain = 2 + ((h >>> 5) & 1);
        for (int i = 1; i <= chain; i++) place(cx, f.platformY - i, cz, Blocks.IRON_CHAIN.getDefaultState());
        if (((h >>> 6) & 1) == 0) place(cx, f.platformY - chain - 1, cz, Blocks.LANTERN.getDefaultState().with(LanternBlock.HANGING, true));
    }

    private void furnish(Frame f, int h) {
        int barrelOff = f.from + (MistyCaves.mix(h, 70, 0) & 0xFF) % f.length;
        place(f.cx(f.inner, barrelOff), f.platformY + 1, f.cz(f.inner, barrelOff), Blocks.BARREL.getDefaultState());
        if (((h >>> 7) & 3) == 0) {
            int lampOff = f.from + (MistyCaves.mix(h, 71, 0) & 0xFF) % f.length;
            place(f.cx(f.outer, lampOff), f.platformY + 1, f.cz(f.outer, lampOff), Blocks.LANTERN.getDefaultState());
        }
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
        pos.set(x, y, z);
        // ProtoChunk does not create block entities itself
        if (state.hasBlockEntity() && state.getBlock() instanceof BlockEntityProvider provider) {
            BlockEntity entity = provider.createBlockEntity(pos.toImmutable(), state);
            if (entity != null) chunk.setBlockEntity(entity);
        }
        return true;
    }
}
