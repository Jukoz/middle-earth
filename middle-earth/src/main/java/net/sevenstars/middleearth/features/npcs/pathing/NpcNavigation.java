package net.sevenstars.middleearth.features.npcs.pathing;

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
    // Minimum node budget. The default (16 per block of range) runs out going round a long building
    private static final int SEARCH_NODES = 2048;
    private static final float PATH_LENGTH = 64;
    private static final float LONG_PATH_LENGTH = 96;
    private static final float PLAN_LENGTH = 256;
    private static final float PLAN_SEARCH = 16;
    private static final int FOLLOW_TARGET_RANGE = 8;

    // The game's multiplier is private, so keep a copy
    private float searchMultiplier = 1;
    private boolean planning = false;

    public NpcNavigation(MobEntity mob, World world) {
        super(mob, world);
    }

    // Path length cap, follow range by default. Round a house to its door or loft is longer
    @Override
    protected @Nullable Path findPathToAny(Set<BlockPos> positions, int range, boolean useHeadPos, int distance, float followRange) {
        float length = planning ? PLAN_LENGTH : searchMultiplier > 1 ? LONG_PATH_LENGTH : PATH_LENGTH;
        return super.findPathToAny(positions, range, useHeadPos, distance, Math.max(followRange, length));
    }

    // The game moves a target out of a block it thinks is solid, but an open trapdoor's isn't,
    // so search for that block itself
    @Override
    public @Nullable Path findPathTo(BlockPos target, int distance) {
        // Reading a block in an unloaded chunk would load it
        if(!world.isChunkLoaded(target.getX() >> 4, target.getZ() >> 4))
            return null;
        if(isBoard(world.getBlockState(target)))
            return findPathTo(Set.of(target), FOLLOW_TARGET_RANGE, false, distance);
        return super.findPathTo(target, distance);
    }

    // Whole route in one search, with almost no length limit, to walk in stretches. Null if unreachable
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

    // A ladder board sits on one side, so aim for the middle of the free room or a wide npc sticks
    @Override
    public void tick() {
        super.tick();
        if(isIdle() || currentPath == null || currentPath.isFinished())
            return;
        BlockPos node = currentPath.getCurrentNodePos();
        // A second board can sit at head height (corner ladder, shutter), narrowing the room too
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

    @Override
    protected boolean isAtValidPosition() {
        return super.isAtValidPosition() || this.entity.isClimbing();
    }
}
