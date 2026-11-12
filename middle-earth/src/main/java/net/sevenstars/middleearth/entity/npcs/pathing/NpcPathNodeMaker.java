package net.sevenstars.middleearth.entity.npcs.pathing;

import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

// Land pathfinding knows nothing of ladders: a mob only ever gets onto one by accident. This adds the way up and down them
public class NpcPathNodeMaker extends LandPathNodeMaker {
    // Makes a walk on the flat win over a climb of the same length
    private static final float CLIMB_PENALTY = 1f;

    private final BlockPos.Mutable probe = new BlockPos.Mutable();

    @Override
    public PathNode getStart() {
        // The usual start is the ground under the mob, which is the wrong end of the ladder for one half way up
        BlockPos pos = entity.getBlockPos();
        if(entity.isClimbing() || isLadderCell(pos.getX(), pos.getY(), pos.getZ()))
            return getStart(pos);
        return super.getStart();
    }

    @Override
    public int getSuccessors(PathNode[] successors, PathNode node) {
        int count = super.getSuccessors(successors, node);
        if(isLadderCell(node.x, node.y, node.z)) {
            if(isLadderCell(node.x, node.y + 1, node.z))
                count = add(successors, count, node.x, node.y + 1, node.z);
            if(isClimbable(node.x, node.y - 1, node.z) && isPassable(node.x, node.y - 1, node.z))
                count = add(successors, count, node.x, node.y - 1, node.z);
        }
        // The top of a shaft has no floor, so walking into it has to be offered too
        for(Direction side : Direction.Type.HORIZONTAL) {
            int x = node.x + side.getOffsetX();
            int z = node.z + side.getOffsetZ();
            if(isLadderCell(x, node.y, z))
                count = add(successors, count, x, node.y, z);
        }
        return count;
    }

    private int add(PathNode[] successors, int count, int x, int y, int z) {
        if(count >= successors.length)
            return count;
        PathNode node = getNode(x, y, z);
        if(node == null || node.visited)
            return count;
        for(int i = 0; i < count; i++) {
            if(successors[i] == node)
                return count;
        }
        node.type = PathNodeType.WALKABLE;
        node.penalty = Math.max(node.penalty, CLIMB_PENALTY);
        successors[count] = node;
        return count + 1;
    }

    // A place on a ladder, or just above its top, with room for the body
    private boolean isLadderCell(int x, int y, int z) {
        return (isClimbable(x, y, z) || isClimbable(x, y - 1, z)) && isPassable(x, y, z) && isPassable(x, y + 1, z);
    }

    private boolean isClimbable(int x, int y, int z) {
        return context.getBlockState(probe.set(x, y, z)).isIn(BlockTags.CLIMBABLE);
    }

    private boolean isPassable(int x, int y, int z) {
        BlockState state = context.getBlockState(probe.set(x, y, z));
        // A ladder is a thin board with a shape of its own, which is no obstacle to somebody climbing it
        if(isHatch(state) || state.isIn(BlockTags.CLIMBABLE))
            return true;
        return state.getCollisionShape(context.getWorld(), probe).isEmpty();
    }

    // The gate or trapdoor closing a loft, which the npc opens on its way
    public static boolean isHatch(BlockState state) {
        return state.getBlock() instanceof FenceGateBlock || (state.getBlock() instanceof TrapdoorBlock && state.isIn(BlockTags.WOODEN_TRAPDOORS));
    }
}
