package net.sevenstars.middleearth.entity.npcs.pathing;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

public class NpcNavigation extends MobNavigation {
    public NpcNavigation(MobEntity mob, World world) {
        super(mob, world);
    }

    @Override
    protected PathNodeNavigator createPathNodeNavigator(int range) {
        this.nodeMaker = new NpcPathNodeMaker();
        return new PathNodeNavigator(this.nodeMaker, range);
    }

    // A ladder is a board along one side of its block. Steered to the middle of the block, an npc a little wider than the
    // room beside the board walks into the end of it, and stays there. It is aimed at the middle of the free room instead
    @Override
    public void tick() {
        super.tick();
        if(isIdle() || currentPath == null || currentPath.isFinished())
            return;
        BlockPos node = currentPath.getCurrentNodePos();
        BlockState state = world.getBlockState(node);
        if(!state.isIn(BlockTags.CLIMBABLE))
            return;
        VoxelShape shape = state.getCollisionShape(world, node);
        if(shape.isEmpty())
            return;
        Vec3d target = currentPath.getNodePosition(entity);
        double x = node.getX() + freeMiddle(shape.getMin(Direction.Axis.X), shape.getMax(Direction.Axis.X));
        double z = node.getZ() + freeMiddle(shape.getMin(Direction.Axis.Z), shape.getMax(Direction.Axis.Z));
        entity.getMoveControl().moveTo(x, adjustTargetY(target), z, speed);
    }

    // Along one axis, the middle of what a shape against one side of the block leaves free
    private static double freeMiddle(double min, double max) {
        if(min <= 0 && max < 0.5)
            return (max + 1) / 2;
        if(max >= 1 && min > 0.5)
            return min / 2;
        return 0.5;
    }

    // Off the ground on a ladder is still somewhere a path can be followed from
    @Override
    protected boolean isAtValidPosition() {
        return super.isAtValidPosition() || this.entity.isClimbing();
    }
}
