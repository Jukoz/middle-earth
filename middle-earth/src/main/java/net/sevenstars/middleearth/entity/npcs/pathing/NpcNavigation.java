package net.sevenstars.middleearth.entity.npcs.pathing;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class NpcNavigation extends MobNavigation {
    // Minimum node budget for a search, before the range multiplier. The game's default of 16 per block of follow range
    // runs out on the way around a long building, and the search then ends on the near side of the wall
    private static final int SEARCH_NODES = 2048;
    private static final float PATH_LENGTH = 64;
    private static final float LONG_PATH_LENGTH = 96;
    // For planning a whole route at once, through the halls of a stronghold and up its stairs
    private static final float PLAN_LENGTH = 256;
    private static final float PLAN_SEARCH = 16;
    // The range the game passes for a search to a single block
    private static final int FOLLOW_TARGET_RANGE = 8;

    // The game's own multiplier is private, so keep a copy
    private float searchMultiplier = 1;
    private boolean planning = false;

    public NpcNavigation(MobEntity mob, World world) {
        super(mob, world);
    }

    // Searches don't follow paths longer than this (the follow range by default). The route around a house to its door,
    // or up the back stairs to a loft, is longer than the default 32 blocks
    @Override
    protected @Nullable Path findPathToAny(Set<BlockPos> positions, int range, boolean useHeadPos, int distance, float followRange) {
        // A search with more nodes, such as the walk to bed, is also allowed a longer path
        float length = planning ? PLAN_LENGTH : searchMultiplier > 1 ? LONG_PATH_LENGTH : PATH_LENGTH;
        return super.findPathToAny(positions, range, useHeadPos, distance, Math.max(followRange, length));
    }

    // The game moves the target up when it sits in a block it considers solid. The spot to stand at beside a bed can be
    // in the block of an open trapdoor, which isn't really solid, so search for that block itself
    @Override
    public @Nullable Path findPathTo(BlockPos target, int distance) {
        // Reading the block state in an unloaded chunk would load it
        if(!world.isChunkLoaded(target.getX() >> 4, target.getZ() >> 4))
            return null;
        if(isBoard(world.getBlockState(target)))
            return findPathTo(Set.of(target), FOLLOW_TARGET_RANGE, false, distance);
        return super.findPathTo(target, distance);
    }

    // Finds the whole route to a place in one search, with many more nodes and practically no length limit, so it can be
    // walked a stretch at a time. Null when no path reaches the target
    public @Nullable Path plan(BlockPos target) {
        planning = true;
        float before = searchMultiplier;
        setRangeMultiplier(PLAN_SEARCH);
        try {
            Path path = findPathTo(target, 1);
            return path != null && path.reachesTarget() ? path : null;
        } finally {
            setRangeMultiplier(before);
            planning = false;
        }
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

    // A ladder is a board along one side of its block, like an open trapdoor. If an npc aims for the middle of the block
    // and is a little wider than the room beside the board, it walks into the end of the board and gets stuck. So aim for
    // the middle of the free room instead
    @Override
    public void tick() {
        super.tick();
        if(isIdle() || currentPath == null || currentPath.isFinished())
            return;
        BlockPos node = currentPath.getCurrentNodePos();
        // There can be a board at the feet and another at head height, such as a ladder turning a corner or a shutter over
        // a ladder. Both narrow the room the body has to pass through
        BlockState feet = world.getBlockState(node);
        BlockState head = world.getBlockState(node.up());
        VoxelShape low = isBoard(feet) ? feet.getCollisionShape(world, node) : VoxelShapes.empty();
        VoxelShape high = isBoard(head) ? head.getCollisionShape(world, node.up()) : VoxelShapes.empty();
        if(low.isEmpty() && high.isEmpty())
            return;
        Vec3d target = currentPath.getNodePosition(entity);
        double x = node.getX() + freeMiddle(low, high, Direction.Axis.X);
        double z = node.getZ() + freeMiddle(low, high, Direction.Axis.Z);
        entity.getMoveControl().moveTo(x, adjustTargetY(target), z, speed);
    }

    private static boolean isBoard(BlockState state) {
        return state.isIn(BlockTags.CLIMBABLE) || NpcPathNodeMaker.isOpenBoard(state);
    }

    // The middle of the room the shapes leave free along one axis, between the sides of the block
    private static double freeMiddle(VoxelShape low, VoxelShape high, Direction.Axis axis) {
        double from = 0;
        double to = 1;
        for(VoxelShape shape : new VoxelShape[]{low, high}) {
            if(shape.isEmpty())
                continue;
            if(shape.getMin(axis) <= 0 && shape.getMax(axis) < NpcPathNodeMaker.FAR_HALF)
                from = Math.max(from, shape.getMax(axis));
            else if(shape.getMax(axis) >= 1 && shape.getMin(axis) > NpcPathNodeMaker.FAR_HALF)
                to = Math.min(to, shape.getMin(axis));
        }
        return (from + to) / 2;
    }

    // A path can be followed from a ladder even though the npc is off the ground
    @Override
    protected boolean isAtValidPosition() {
        return super.isAtValidPosition() || this.entity.isClimbing();
    }
}
