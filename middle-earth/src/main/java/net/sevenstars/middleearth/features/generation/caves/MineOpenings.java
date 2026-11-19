package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// the jigsaws of a list of pieces, flattened in piece order; the ones of a piece are next to each other
final class MineOpenings {
    final List<BlockPos> positions = new ArrayList<>();
    final List<Direction> facings = new ArrayList<>();
    final List<Integer> owners = new ArrayList<>();
    private final int[] firstOf;
    private final Map<BlockPos, Integer> firstAt = new HashMap<>();

    MineOpenings(MineSession session, List<StructurePiece> pieces) {
        firstOf = new int[pieces.size() + 1];
        for (int i = 0; i < pieces.size(); i++) {
            firstOf[i] = positions.size();
            if (!(pieces.get(i) instanceof PoolStructurePiece pool)) continue;
            for (MineSession.Opening opening : session.openingsOf(pool)) {
                firstAt.putIfAbsent(opening.pos(), positions.size());
                positions.add(opening.pos());
                facings.add(opening.facing());
                owners.add(i);
            }
        }
        firstOf[pieces.size()] = positions.size();
    }

    int size() {
        return positions.size();
    }

    // range of the openings of one piece
    int from(int piece) {
        return firstOf[piece];
    }

    int to(int piece) {
        return firstOf[piece + 1];
    }

    // first opening on that cell, or -1
    int indexAt(BlockPos pos) {
        return firstAt.getOrDefault(pos, -1);
    }

    // true when another jigsaw sits on the cell in front of this opening and faces it
    boolean connected(int index) {
        int other = indexAt(positions.get(index).offset(facings.get(index)));
        return other >= 0 && facings.get(other) == facings.get(index).getOpposite();
    }
}
