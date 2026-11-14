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
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class NpcNavigation extends MobNavigation {
    // Nodes a search may look at. The game's 16 per block of follow range runs out on the way round a long building, and
    // the search then ends on the near side of the wall
    private static final int SEARCH_NODES = 2048;
    private static final float PATH_LENGTH = 64;
    private static final float LONG_PATH_LENGTH = 96;
    // For a whole way planned at once, through the halls of a stronghold and up its stairs
    private static final float PLAN_LENGTH = 256;
    private static final float PLAN_SEARCH = 16;
    // What the game passes for a search to one block
    private static final int FOLLOW_TARGET_RANGE = 8;

    // The game keeps its own to itself
    private float searchMultiplier = 1;
    private boolean planning = false;

    public NpcNavigation(MobEntity mob, World world) {
        super(mob, world);
    }

    // The search follows no path longer than this, the follow range by default. At 32 blocks the way round a house to its
    // door, or up the stair at the back to a loft, was longer than any path looked at
    @Override
    protected @Nullable Path findPathToAny(Set<BlockPos> positions, int range, boolean useHeadPos, int distance, float followRange) {
        // A search given more nodes, as on the way to bed, may follow a longer way too
        float length = planning ? PLAN_LENGTH : searchMultiplier > 1 ? LONG_PATH_LENGTH : PATH_LENGTH;
        return super.findPathToAny(positions, range, useHeadPos, distance, Math.max(followRange, length));
    }

    // The game raises a target that stands in a block it takes for solid. A bed stand beside an open trapdoor is no such block,
    // and the search went for the block over it
    @Override
    public @Nullable Path findPathTo(BlockPos target, int distance) {
        if(isBoard(world.getBlockState(target)))
            return findPathTo(Set.of(target), FOLLOW_TARGET_RANGE, false, distance);
        return super.findPathTo(target, distance);
    }

    // The whole way to a place, looked for once with many more nodes and no limit to speak of on its length, to be walked a
    // stretch at a time. Null when the search found no way that gets there
    public @Nullable Path plan(BlockPos target) {
        planning = true;
        setRangeMultiplier(PLAN_SEARCH);
        try {
            Path path = findPathTo(target, 1);
            return path != null && path.reachesTarget() ? path : null;
        } finally {
            resetRangeMultiplier();
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

    // A ladder is a board along one side of its block, as an open trapdoor is. Steered to the middle of the block, an npc a
    // little wider than the room beside the board walks into the end of it, and stays there. It is aimed at the middle of the
    // free room instead
    @Override
    public void tick() {
        super.tick();
        if(isIdle() || currentPath == null || currentPath.isFinished())
            return;
        BlockPos node = currentPath.getCurrentNodePos();
        // A board at the feet and another at head height, as a ladder turning a corner or a shutter over a ladder, both narrow
        // the room the body has to pass through
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
        return state.isIn(BlockTags.CLIMBABLE) || state.getBlock() instanceof TrapdoorBlock && state.get(TrapdoorBlock.OPEN);
    }

    // Along one axis, the middle of the room shapes against the sides of the block leave free
    private static double freeMiddle(VoxelShape low, VoxelShape high, Direction.Axis axis) {
        double from = 0;
        double to = 1;
        for(VoxelShape shape : new VoxelShape[]{low, high}) {
            if(shape.isEmpty())
                continue;
            if(shape.getMin(axis) <= 0 && shape.getMax(axis) < 0.5)
                from = Math.max(from, shape.getMax(axis));
            else if(shape.getMax(axis) >= 1 && shape.getMin(axis) > 0.5)
                to = Math.min(to, shape.getMin(axis));
        }
        return (from + to) / 2;
    }

    // Off the ground on a ladder is still somewhere a path can be followed from
    @Override
    protected boolean isAtValidPosition() {
        return super.isAtValidPosition() || this.entity.isClimbing();
    }
}
