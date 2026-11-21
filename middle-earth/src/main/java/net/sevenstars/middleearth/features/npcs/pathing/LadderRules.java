package net.sevenstars.middleearth.features.npcs.pathing;

import net.minecraft.block.BlockState;
import net.minecraft.block.ScaffoldingBlock;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;

final class LadderRules {
    // Spare room needed to squeeze past a ladder's board
    private static final double SQUEEZE_MARGIN = 0.05;

    private final PathTerrain terrain;

    LadderRules(PathTerrain terrain) {
        this.terrain = terrain;
    }

    // Mobs never crouch, so scaffolding's top is floor. Flat ladders don't climb
    boolean isClimbable(int x, int y, int z) {
        BlockState state = terrain.stateAt(x, y, z);
        return state.isIn(BlockTags.CLIMBABLE) && !(state.getBlock() instanceof ScaffoldingBlock)
                && !(state.contains(Properties.BLOCK_FACE) && state.get(Properties.BLOCK_FACE) == BlockFace.FLOOR);
    }

    // Above the top needs a floor to step off onto, unlike a one-block garden fence
    boolean isLadderCell(int x, int y, int z) {
        if(!terrain.isPassable(x, y, z) || !terrain.isPassable(x, y + 1, z))
            return false;
        return isClimbable(x, y, z) || isClimbable(x, y - 1, z) && hasLanding(x, y, z);
    }

    private boolean hasLanding(int x, int y, int z) {
        for(Direction side : Direction.Type.HORIZONTAL) {
            int nextX = x + side.getOffsetX();
            int nextZ = z + side.getOffsetZ();
            for(int up = 0; up <= 1; up++) {
                int floor = y + up - 1;
                if(terrain.isPassable(nextX, floor + 1, nextZ) && terrain.isPassable(nextX, floor + 2, nextZ) && !terrain.isPassable(nextX, floor, nextZ) && !isClimbable(nextX, floor, nextZ))
                    return true;
            }
        }
        return false;
    }

    // Vanilla treats a ladder as solid and the air above one as floor
    boolean isTopWithoutLanding(PathNode next) {
        return isClimbable(next.x, next.y - 1, next.z) && !isClimbable(next.x, next.y, next.z) && !hasLanding(next.x, next.y, next.z);
    }

    // A wide body catches on a ladder board's end, so enter and leave from the front
    boolean catchesOnBoard(PathNode from, PathNode next) {
        int stepX = next.x - from.x;
        int stepZ = next.z - from.z;
        return next.y <= from.y && (isPastBoard(next.x, next.y, next.z, stepX, stepZ, true) || isPastBoard(from.x, from.y, from.z, stepX, stepZ, false));
    }

    // True if the ladder's board blocks the step. Side comes from the shape, not a facing
    boolean isPastBoard(int x, int y, int z, int stepX, int stepZ, boolean entering) {
        MobEntity entity = terrain.entity();
        if(entity == null || !terrain.stateAt(x, y, z).isIn(BlockTags.CLIMBABLE))
            return false;
        VoxelShape shape = terrain.shapeAt(x, y, z);
        if(shape.isEmpty())
            return false;
        double half = BoardRules.FAR_HALF;
        int boardX = shape.getMax(Direction.Axis.X) <= half ? -1 : shape.getMin(Direction.Axis.X) >= half ? 1 : 0;
        int boardZ = shape.getMax(Direction.Axis.Z) <= half ? -1 : shape.getMin(Direction.Axis.Z) >= half ? 1 : 0;
        if(boardX == 0 && boardZ == 0)
            return false;
        int through = entering ? -1 : 1;
        if(boardX != 0 && stepX == through * boardX || boardZ != 0 && stepZ == through * boardZ)
            return true;
        boolean along = boardX != 0 ? stepZ != 0 : stepX != 0;
        if(!along)
            return false;
        double board = boardX != 0 ? shape.getMax(Direction.Axis.X) - shape.getMin(Direction.Axis.X) : shape.getMax(Direction.Axis.Z) - shape.getMin(Direction.Axis.Z);
        return entity.getWidth() > 1 - board - SQUEEZE_MARGIN;
    }
}
