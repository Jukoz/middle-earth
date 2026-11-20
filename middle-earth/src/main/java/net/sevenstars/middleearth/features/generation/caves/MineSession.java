package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.block.JigsawBlock;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.structure.Structure;
import net.sevenstars.middleearth.features.generation.biomes.caves.DepthBands;
import net.sevenstars.middleearth.features.generation.chunkgen.CaveProbe;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

// what a mine start needs while it grows: the rock probe, the depth bands of its columns and the chunks it may fill
final class MineSession {
    // a structure's pieces are only placed in chunks within 8 chunks of its start chunk
    private static final int WINDOW_CHUNKS = 8;
    private static final int TOP_MARGIN = 10; // of the middle band, kept clear above the mine
    private static final float CAP_FLOOR_SHARE = 0.7f;
    private static final float FLOOR_MAX_AIR = 0.02f; // of the floor blocks
    private static final int FLOOR_MAX_POCKETS = 2;
    private static final float VOLUME_MAX_AIR = 0.45f; // of the rock a piece is cut into
    private static final int VOLUME_STEP = 2; // the volume is sampled every other cell

    record Opening(BlockPos pos, Direction facing) {
    }

    final Structure.Context context;
    final CaveProbe probe;
    final BlockBox window;
    // pieces of each bridge and the mine beyond it, by the piece the bridge starts from; they go when that piece does
    final Map<StructurePiece, List<StructurePiece>> links = new IdentityHashMap<>();
    private final MistyCaves caves;
    private final Map<Long, DepthBands.Edges> edges = new HashMap<>();
    private final Map<PoolStructurePiece, List<Opening>> openings = new IdentityHashMap<>();

    MineSession(Structure.Context context, CaveProbe probe, MistyCaves caves) {
        this.context = context;
        this.probe = probe;
        this.caves = caves;
        ChunkPos chunk = context.chunkPos();
        this.window = new BlockBox((chunk.x - WINDOW_CHUNKS) << 4, context.world().getBottomY(), (chunk.z - WINDOW_CHUNKS) << 4,
                ((chunk.x + WINDOW_CHUNKS) << 4) + 15, context.world().getTopYInclusive(), ((chunk.z + WINDOW_CHUNKS) << 4) + 15);
    }

    // the jigsaws of a piece in the order the template gives them; pieces never move, so this is read once
    List<Opening> openingsOf(PoolStructurePiece piece) {
        return openings.computeIfAbsent(piece, key -> piece.getPoolElement()
                .getStructureBlockInfos(context.structureTemplateManager(), piece.getPos(), piece.getRotation(), Random.create(0)).stream()
                .map((StructureTemplate.JigsawBlockInfo jigsaw) -> new Opening(jigsaw.info().pos(), JigsawBlock.getFacing(jigsaw.info().state()))).toList());
    }

    // most of the row under the box is rock
    boolean floored(BlockBox box) {
        int solid = 0;
        int total = 0;
        for (int x = box.getMinX(); x <= box.getMaxX(); x++) {
            for (int z = box.getMinZ(); z <= box.getMaxZ(); z++) {
                total++;
                if (probe.solid(x, box.getMinY() - 1, z)) solid++;
            }
        }
        return solid >= total * CAP_FLOOR_SHARE;
    }

    // a cap or a piece started to seal an exit: inside the limits and with rock under it
    boolean capFits(BlockBox box, BlockPos origin, int[] floor) {
        return fits(box, origin, floor) && floored(box);
    }

    boolean inBand(BlockBox box) {
        return !outOfBounds(box);
    }

    // the piece has to lie inside the window and between the top of the middle band and the bottom of the mithril band
    boolean outOfBounds(BlockBox box) {
        if (box.getMinX() < window.getMinX() || box.getMaxX() > window.getMaxX() || box.getMinZ() < window.getMinZ() || box.getMaxZ() > window.getMaxZ()) return true;
        int x = (box.getMinX() + box.getMaxX()) / 2;
        int z = (box.getMinZ() + box.getMaxZ()) / 2;
        DepthBands.Edges column = edges.computeIfAbsent(ChunkPos.toLong(x >> 4, z >> 4), key -> caves.edgesAt(MiddleEarthMapRuntime.getInstance(), x, z));
        return box.getMaxY() > column.top(DepthBands.Band.MIDDLE) - TOP_MARGIN || box.getMinY() < column.bottom(DepthBands.Band.MITHRIL);
    }

    // inside the limits, rock under every floor column, not much air in the volume; a cave pocket in it is dug through
    // a floor over a drop is not built, so pieces stop short of a chasm; the processor fills a one block dip over rock
    boolean fits(BlockBox box, BlockPos origin, int[] floor) {
        if (outOfBounds(box)) return false;
        return floorHolds(box, origin, floor) && volumeHolds(box);
    }

    private boolean floorHolds(BlockBox box, BlockPos origin, int[] floor) {
        int air = 0;
        if (floor.length == 0) {
            int under = box.getMinY() - 1;
            float allowed = (box.getMaxX() - box.getMinX() + 1) * (box.getMaxZ() - box.getMinZ() + 1) * FLOOR_MAX_AIR;
            for (int x = box.getMinX(); x <= box.getMaxX(); x++) {
                for (int z = box.getMinZ(); z <= box.getMaxZ(); z++) {
                    if (!probe.solid(x, under, z) && ++air > allowed) return false;
                }
            }
            return true;
        }
        // a few floor blocks over a pocket may stay, never a run of them
        int allowed = Math.min(FLOOR_MAX_POCKETS, (int) (floor.length / 3 * FLOOR_MAX_AIR));
        for (int i = 0; i < floor.length; i += 3) {
            if (!probe.solid(origin.getX() + floor[i], origin.getY() + floor[i + 1] - 1, origin.getZ() + floor[i + 2]) && ++air > allowed) return false;
        }
        return true;
    }

    private boolean volumeHolds(BlockBox box) {
        int layers = box.getMaxY() > box.getMinY() ? (box.getMaxY() - box.getMinY() - 1) / VOLUME_STEP + 1 : 0;
        int columns = (box.getMaxX() - box.getMinX()) / VOLUME_STEP + 1;
        int rows = (box.getMaxZ() - box.getMinZ()) / VOLUME_STEP + 1;
        float allowed = layers * columns * rows * VOLUME_MAX_AIR;
        int air = 0;
        for (int y = box.getMinY() + 1; y <= box.getMaxY(); y += VOLUME_STEP) {
            for (int x = box.getMinX(); x <= box.getMaxX(); x += VOLUME_STEP) {
                for (int z = box.getMinZ(); z <= box.getMaxZ(); z += VOLUME_STEP) {
                    if (!probe.solid(x, y, z) && ++air > allowed) return false;
                }
            }
        }
        return true;
    }
}
