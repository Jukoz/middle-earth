package net.sevenstars.middleearth.features.npcs.pathing;

import net.minecraft.block.BlockState;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;

// Open trapdoors stand like boards at one side of their block
final class BoardRules {
    static final double FAR_HALF = 0.5;
    private static final int BODY_BLOCKS = 2;

    private final PathTerrain terrain;

    BoardRules(PathTerrain terrain) {
        this.terrain = terrain;
    }

    static boolean isOpenBoard(BlockState state) {
        return state.getBlock() instanceof TrapdoorBlock && state.get(TrapdoorBlock.OPEN);
    }

    boolean crossesBoard(PathNode from, PathNode next) {
        int stepX = Integer.signum(next.x - from.x);
        int stepZ = Integer.signum(next.z - from.z);
        for(int up = 0; up < BODY_BLOCKS; up++) {
            if(boardTowards(from.x, from.y + up, from.z, stepX, stepZ) || boardTowards(next.x, next.y + up, next.z, -stepX, -stepZ))
                return true;
            if(next.y < from.y && boardTowards(next.x, from.y + up, next.z, -stepX, -stepZ))
                return true;
        }
        return false;
    }

    private boolean boardTowards(int x, int y, int z, int stepX, int stepZ) {
        if(!isOpenBoard(terrain.stateAt(x, y, z)))
            return false;
        VoxelShape shape = terrain.shapeAt(x, y, z);
        if(shape.isEmpty())
            return false;
        return stepX > 0 && shape.getMin(Direction.Axis.X) >= FAR_HALF || stepX < 0 && shape.getMax(Direction.Axis.X) <= FAR_HALF
                || stepZ > 0 && shape.getMin(Direction.Axis.Z) >= FAR_HALF || stepZ < 0 && shape.getMax(Direction.Axis.Z) <= FAR_HALF;
    }
}
