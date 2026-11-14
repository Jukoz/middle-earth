package net.sevenstars.middleearth.entity.npcs.pathing;

import net.minecraft.block.BlockState;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class NpcNavigation extends MobNavigation {
    // Nodes a search may look at. The game's 16 per block of follow range runs out on the way round a long building, and
    // the search then ends on the near side of the wall
    private static final int SEARCH_NODES = 2048;
    private static final float PATH_LENGTH = 64;
    private static final float LONG_PATH_LENGTH = 96;

    // The game keeps its own to itself
    private float searchMultiplier = 1;

    public NpcNavigation(MobEntity mob, World world) {
        super(mob, world);
    }

    // The search follows no path longer than this, the follow range by default. At 32 blocks the way round a house to its
    // door, or up the stair at the back to a loft, was longer than any path looked at
    @Override
    protected @Nullable Path findPathToAny(Set<BlockPos> positions, int range, boolean useHeadPos, int distance, float followRange) {
        // A search given more nodes, as on the way to bed, may follow a longer way too
        float length = searchMultiplier > 1 ? LONG_PATH_LENGTH : PATH_LENGTH;
        return super.findPathToAny(positions, range, useHeadPos, distance, Math.max(followRange, length));
    }

    @Override
    public void setRangeMultiplier(float rangeMultiplier) {
        super.setRangeMultiplier(rangeMultiplier);
        searchMultiplier = rangeMultiplier;
    }

    @Override
    public void resetRangeMultiplier() {
        super.resetRangeMultiplier();
        searchMultiplier = 1;
    }

    @Override
    protected PathNodeNavigator createPathNodeNavigator(int range) {
        this.nodeMaker = new NpcPathNodeMaker();
        return new NpcPathNodeNavigator(this.nodeMaker, Math.max(range, SEARCH_NODES));
    }

    // A ladder is a board along one side of its block, as an open trapdoor is. Steered to the middle of the block, an npc a
    // little wider than the room beside the board walks into the end of it, and stays there. It is aimed at the middle of the
    // free room instead
    @Override
    public void tick() {
        super.tick();
        if(isIdle() || currentPath == null || currentPath.isFinished())
            return;
        BlockPos node = currentPath.getCurrentNodePos();
        // The board of a shutter can hang at head height only
        BlockPos board = isBoard(world.getBlockState(node)) ? node : isBoard(world.getBlockState(node.up())) ? node.up() : null;
        if(board == null)
            return;
        VoxelShape shape = world.getBlockState(board).getCollisionShape(world, board);
        if(shape.isEmpty())
            return;
        Vec3d target = currentPath.getNodePosition(entity);
        double x = node.getX() + freeMiddle(shape.getMin(Direction.Axis.X), shape.getMax(Direction.Axis.X));
        double z = node.getZ() + freeMiddle(shape.getMin(Direction.Axis.Z), shape.getMax(Direction.Axis.Z));
        entity.getMoveControl().moveTo(x, adjustTargetY(target), z, speed);
    }

    private static boolean isBoard(BlockState state) {
        return state.isIn(BlockTags.CLIMBABLE) || state.getBlock() instanceof TrapdoorBlock && state.get(TrapdoorBlock.OPEN);
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
