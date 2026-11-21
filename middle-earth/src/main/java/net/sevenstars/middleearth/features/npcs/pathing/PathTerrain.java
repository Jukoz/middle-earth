package net.sevenstars.middleearth.features.npcs.pathing;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.pathing.PathContext;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.sevenstars.middleearth.block.special.NpcDoor;
import org.jetbrains.annotations.Nullable;

// What the path rules read from the search's cached world, for the mob being searched
final class PathTerrain {
    // Carpet or a layer of snow
    static final double LOW_STEP = 0.2;

    private final BlockPos.Mutable probe = new BlockPos.Mutable();
    private PathContext context;
    private MobEntity entity;

    void bind(PathContext context, MobEntity entity) {
        this.context = context;
        this.entity = entity;
    }

    void setEntity(MobEntity entity) {
        this.entity = entity;
    }

    @Nullable MobEntity entity() {
        return entity;
    }

    BlockState stateAt(int x, int y, int z) {
        return context.getBlockState(probe.set(x, y, z));
    }

    VoxelShape shapeAt(int x, int y, int z) {
        return context.getBlockState(probe.set(x, y, z)).getCollisionShape(context.getWorld(), probe);
    }

    boolean isPassable(int x, int y, int z) {
        BlockState state = context.getBlockState(probe.set(x, y, z));
        if(NpcPathNodeMaker.isHatch(state) || state.isIn(BlockTags.CLIMBABLE))
            return true;
        return state.getCollisionShape(context.getWorld(), probe).isEmpty();
    }

    // Swimming or on a ladder the feet are in the node's block, else leaving water looks too high
    double feetAt(int x, int y, int z) {
        VoxelShape inside = shapeAt(x, y, z);
        if(!inside.isEmpty() && inside.getMax(Direction.Axis.Y) < 1)
            return y + inside.getMax(Direction.Axis.Y);
        VoxelShape below = shapeAt(x, y - 1, z);
        return below.isEmpty() ? y : y - 1 + below.getMax(Direction.Axis.Y);
    }

    boolean isLockedShut(BlockState state, int x, int y, int z) {
        return isLockedShut(state, probe.set(x, y, z));
    }

    // Only openable blocks, or every path block gets looked up among the doors
    boolean isLockedShut(BlockState state, BlockPos pos) {
        if(!(state.getBlock() instanceof NpcDoor) && !state.contains(Properties.OPEN))
            return false;
        if(NpcDoor.opensFor(entity.getEntityWorld(), pos, state, entity))
            return false;
        if(state.getBlock() instanceof NpcDoor door)
            return !door.isOpen(state);
        return !state.get(Properties.OPEN);
    }
}
