package net.sevenstars.middleearth.commands.custom;

import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class PieceLayout {
    private static final double FIRST_SPREAD = 1.5;
    private static final double SPREAD_STEP = 0.25;
    private static final double MAXIMUM_SPREAD = 6;

    static String describe(BlockBox box) {
        return "%d,%d,%d to %d,%d,%d".formatted(box.getMinX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ());
    }

    static Stream<ChunkPos> chunksOf(BlockBox box) {
        return ChunkPos.stream(new ChunkPos(box.getMinX() >> 4, box.getMinZ() >> 4), new ChunkPos(box.getMaxX() >> 4, box.getMaxZ() >> 4));
    }

    static BlockBox boundsOf(List<StructureEditSession.Piece> pieces) {
        return BlockBox.encompass(pieces.stream().map(StructureEditSession.Piece::box).toList()).orElseThrow();
    }

    // Moves groups outward from the middle until none are within the gap. A cellar stays under its house, lifted off by the gap
    static List<StructureEditSession.Piece> spread(List<StructureEditSession.Piece> assembled, int gap) {
        List<List<StructureEditSession.Piece>> groups = attached(assembled);
        List<BlockBox> bounds = groups.stream().map(PieceLayout::boundsOf).toList();
        BlockPos middle = boundsOf(assembled).getCenter();
        int[] movesX = new int[groups.size()];
        int[] movesZ = new int[groups.size()];
        List<BlockBox> moved = new ArrayList<>(bounds);
        boolean apart = tooClose(moved, gap) < 0;
        for(double factor = FIRST_SPREAD; factor <= MAXIMUM_SPREAD && !apart; factor += SPREAD_STEP) {
            for(int i = 0; i < bounds.size(); i++) {
                BlockPos center = bounds.get(i).getCenter();
                movesX[i] = (int) Math.round((center.getX() - middle.getX()) * (factor - 1));
                movesZ[i] = (int) Math.round((center.getZ() - middle.getZ()) * (factor - 1));
                moved.set(i, bounds.get(i).offset(movesX[i], 0, movesZ[i]));
            }
            apart = tooClose(moved, gap) < 0;
        }
        if(!apart)
            settle(moved, movesX, gap);
        Map<StructureEditSession.Piece, Integer> groupOf = new IdentityHashMap<>();
        for(int i = 0; i < groups.size(); i++) {
            for(StructureEditSession.Piece piece : groups.get(i))
                groupOf.put(piece, i);
        }
        Map<StructureEditSession.Piece, Integer> lifts = liftsOf(groups, gap);
        List<StructureEditSession.Piece> spread = new ArrayList<>();
        for(StructureEditSession.Piece piece : assembled) {
            int i = groupOf.get(piece);
            int lift = lifts.get(piece);
            spread.add(new StructureEditSession.Piece(piece.template(), piece.origin().add(movesX[i], lift, movesZ[i]), piece.rotation(), piece.box().offset(movesX[i], lift, movesZ[i]), piece.loaded()));
        }
        return spread;
    }

    // Still too close after the biggest spread: later groups slide along x until clear of the earlier ones
    private static void settle(List<BlockBox> moved, int[] movesX, int gap) {
        List<BlockBox> settled = new ArrayList<>();
        for(int i = 0; i < moved.size(); i++) {
            settled.add(moved.get(i));
            int other;
            while((other = tooClose(settled, gap)) >= 0 && other < i) {
                int shift = settled.get(other).getMaxX() + gap + 1 - settled.get(i).getMinX();
                movesX[i] += shift;
                settled.set(i, settled.get(i).offset(shift, 0, 0));
            }
        }
    }

    private static Map<StructureEditSession.Piece, Integer> liftsOf(List<List<StructureEditSession.Piece>> groups, int gap) {
        Map<StructureEditSession.Piece, Integer> lifts = new IdentityHashMap<>();
        for(List<StructureEditSession.Piece> group : groups) {
            List<StructureEditSession.Piece> rising = new ArrayList<>(group);
            rising.sort(Comparator.comparingInt(piece -> piece.box().getMinY()));
            for(int i = 0; i < rising.size(); i++) {
                BlockBox box = rising.get(i).box();
                int lift = 0;
                for(int j = 0; j < i; j++) {
                    BlockBox under = rising.get(j).box();
                    if(overlapsFromAbove(box, under))
                        lift = Math.max(lift, lifts.get(rising.get(j)) + gap);
                }
                lifts.put(rising.get(i), lift);
            }
        }
        return lifts;
    }

    private static boolean overlapsFromAbove(BlockBox box, BlockBox under) {
        return sharesFootprint(box, under) && under.getMaxY() < box.getMinY();
    }

    private static boolean sharesFootprint(BlockBox box, BlockBox other) {
        return box.getMinX() <= other.getMaxX() && box.getMaxX() >= other.getMinX() && box.getMinZ() <= other.getMaxZ() && box.getMaxZ() >= other.getMinZ();
    }

    // Pieces whose footprints overlap without touching, like a cellar under a house, move together
    private static List<List<StructureEditSession.Piece>> attached(List<StructureEditSession.Piece> pieces) {
        int[] group = new int[pieces.size()];
        for(int i = 0; i < group.length; i++)
            group[i] = i;
        for(int i = 0; i < pieces.size(); i++) {
            BlockBox box = pieces.get(i).box();
            for(int j = i + 1; j < pieces.size(); j++) {
                BlockBox other = pieces.get(j).box();
                if(sharesFootprint(box, other) && !box.intersects(other)) {
                    int from = group[j];
                    int to = group[i];
                    for(int k = 0; k < group.length; k++) {
                        if(group[k] == from)
                            group[k] = to;
                    }
                }
            }
        }
        Map<Integer, List<StructureEditSession.Piece>> groups = new LinkedHashMap<>();
        for(int i = 0; i < pieces.size(); i++)
            groups.computeIfAbsent(group[i], key -> new ArrayList<>()).add(pieces.get(i));
        return new ArrayList<>(groups.values());
    }

    // Earlier of the first two boxes within the gap, or -1
    private static int tooClose(List<BlockBox> boxes, int gap) {
        for(int i = 0; i < boxes.size(); i++) {
            BlockBox box = boxes.get(i);
            for(int j = i + 1; j < boxes.size(); j++) {
                BlockBox other = boxes.get(j);
                if(box.getMinX() - gap <= other.getMaxX() && box.getMaxX() + gap >= other.getMinX()
                        && box.getMinZ() - gap <= other.getMaxZ() && box.getMaxZ() + gap >= other.getMinZ())
                    return i;
            }
        }
        return -1;
    }
}
