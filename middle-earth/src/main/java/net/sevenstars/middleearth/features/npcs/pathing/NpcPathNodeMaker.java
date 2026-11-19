package net.sevenstars.middleearth.features.npcs.pathing;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.ScaffoldingBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathContext;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.chunk.ChunkCache;
import net.sevenstars.middleearth.block.special.NpcDoor;
import net.sevenstars.middleearth.block.special.fireBlocks.AbstractToggleableFireBlock;
import net.sevenstars.middleearth.block.special.plants.ToughBerryBushBlock;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.utils.BlockTagsME;

import java.util.List;

// Vanilla misses ladders, our doors, open trapdoors and furniture
public class NpcPathNodeMaker extends LandPathNodeMaker {
    private static final float CLIMB_PENALTY = 1f;
    private static final float FIELD_PENALTY = 8f;
    private static final double DEFAULT_STEP = 0.6;
    private static final double DEFAULT_HEADROOM = 0.8;
    // Smaller rises are ignored
    private static final double LIFT = 0.01;
    // Vanilla's limit, a bit under a jump
    private static final double MAXIMUM_RISE = 1.125;
    static final double FAR_HALF = BoardRules.FAR_HALF;

    @FunctionalInterface
    private interface SuccessorFilter {
        boolean keeps(PathNode from, PathNode next);
    }

    private final BlockPos.Mutable probe = new BlockPos.Mutable();
    private final PathTerrain terrain = new PathTerrain();
    private final LadderRules ladders = new LadderRules(terrain);
    private final BoardRules boards = new BoardRules(terrain);
    private final TrapSearch traps = new TrapSearch(terrain, ladders);
    // Applied in this order to every step the game offers
    private final List<SuccessorFilter> filters = List.of(
            (from, next) -> !traps.isTrapStep(from, next),
            (from, next) -> !cannotStandOn(context, context.getBlockState(probe.set(next.x, next.y - 1, next.z))),
            (from, next) -> !ladders.catchesOnBoard(from, next),
            this::isReachableRise,
            (from, next) -> !boards.crossesBoard(from, next),
            (from, next) -> !isOverhead(context, context.getBlockState(probe.set(next.x, next.y, next.z)), next.x, next.y, next.z),
            this::hasHeadroom,
            (from, next) -> !ladders.isTopWithoutLanding(next)
    );

    @Override
    public void init(ChunkCache cachedWorld, MobEntity entity) {
        super.init(cachedWorld, entity);
        terrain.bind(context, entity);
    }

    @Override
    public PathNode getStart() {
        // Ground is the wrong start for a mob halfway up a ladder
        BlockPos pos = entity.getBlockPos();
        if(entity.isClimbing() || ladders.isLadderCell(pos.getX(), pos.getY(), pos.getZ()))
            return getStart(pos);
        return super.getStart();
    }

    @Override
    public PathNodeType getDefaultNodeType(MobEntity mob, BlockPos pos) {
        MobEntity before = entity;
        entity = mob;
        terrain.setEntity(mob);
        try {
            return super.getDefaultNodeType(mob, pos);
        } finally {
            entity = before;
            terrain.setEntity(before);
        }
    }

    // Vanilla doesn't see our doors, so a shut one looks like a wall
    @Override
    public PathNodeType getDefaultNodeType(PathContext context, int x, int y, int z) {
        BlockState state = context.getBlockState(probe.set(x, y, z));
        if(state.getBlock() instanceof NpcDoor door && NpcDoor.opensFor(entity.getEntityWorld(), probe, state, entity)) {
            if(door.isOpen(state))
                return PathNodeType.DOOR_OPEN;
            return door.canMove(context.getWorld(), probe, state) ? PathNodeType.DOOR_WOOD_CLOSED : PathNodeType.BLOCKED;
        }
        if(state.getBlock() instanceof ScaffoldingBlock)
            return PathNodeType.BLOCKED;
        if(state.getBlock() instanceof AbstractToggleableFireBlock && state.get(AbstractToggleableFireBlock.LIT))
            return PathNodeType.DAMAGE_FIRE;
        if(state.getBlock() instanceof ToughBerryBushBlock)
            return PathNodeType.DAMAGE_OTHER;
        // Open trapdoor is a board, so it's only floor with something below it
        BlockState below = context.getBlockState(probe.set(x, y - 1, z));
        if(isOpenBoard(state))
            return !below.getCollisionShape(context.getWorld(), probe).isEmpty() && !isOpenBoard(below) ? PathNodeType.WALKABLE : PathNodeType.OPEN;
        if(state.isAir() && isOpenBoard(below))
            return PathNodeType.OPEN;
        probe.set(x, y, z);
        // Shut trapdoor as a lintel leaves room below; see the lintel filter
        if(isOverhead(context, state, x, y, z))
            return PathNodeType.OPEN;
        if(terrain.isLockedShut(state, probe.set(x, y, z)))
            return PathNodeType.BLOCKED;
        // Vanilla treats a shut gate as a fence; on a level route we open it
        if(state.getBlock() instanceof FenceGateBlock && !state.get(FenceGateBlock.OPEN) && isGateAcrossLevelWay(context, x, y, z, state))
            return PathNodeType.DOOR_WOOD_CLOSED;
        if(isObstacle(context, state))
            return PathNodeType.BLOCKED;
        return super.getDefaultNodeType(context, x, y, z);
    }

    @Override
    public int getSuccessors(PathNode[] successors, PathNode node) {
        int offered = super.getSuccessors(successors, node);
        int count = 0;
        for(int i = 0; i < offered; i++) {
            PathNode next = successors[i];
            if(keeps(node, next))
                successors[count++] = next;
        }
        keepOffFields(successors, count);
        if(ladders.isLadderCell(node.x, node.y, node.z)) {
            if(ladders.isLadderCell(node.x, node.y + 1, node.z))
                count = add(successors, count, node.x, node.y + 1, node.z);
            if(ladders.isClimbable(node.x, node.y - 1, node.z) && terrain.isPassable(node.x, node.y - 1, node.z))
                count = add(successors, count, node.x, node.y - 1, node.z);
        }
        // A shaft top has no floor, so offer walking into it too
        for(Direction side : Direction.Type.HORIZONTAL) {
            int x = node.x + side.getOffsetX();
            int z = node.z + side.getOffsetZ();
            if(ladders.isLadderCell(x, node.y, z) && !ladders.isPastBoard(x, node.y, z, side.getOffsetX(), side.getOffsetZ(), true)
                    && !ladders.isPastBoard(node.x, node.y, node.z, side.getOffsetX(), side.getOffsetZ(), false))
                count = add(successors, count, x, node.y, z);
        }
        return count;
    }

    @Override
    public void clear() {
        traps.clear();
        super.clear();
    }

    private boolean keeps(PathNode from, PathNode next) {
        for(int i = 0; i < filters.size(); i++) {
            if(!filters.get(i).keeps(from, next))
                return false;
        }
        return true;
    }

    private void keepOffFields(PathNode[] successors, int count) {
        if(!(entity instanceof NpcEntity npc) || npc.isFarming())
            return;
        for(int i = 0; i < count; i++) {
            PathNode next = successors[i];
            BlockState floor = context.getBlockState(probe.set(next.x, next.y - 1, next.z));
            if(isFarmland(floor))
                next.penalty = Math.max(next.penalty, FIELD_PENALTY);
        }
    }

    // Vanilla treats any non-full block as floor, so mobs walk into rocks and stools.
    // Solid ground taller than a step is jumped at; furniture is always walked around
    private boolean isObstacle(PathContext context, BlockState state) {
        if(isLeftToTheGame(state))
            return false;
        VoxelShape shape = state.getCollisionShape(context.getWorld(), probe);
        if(shape.isEmpty() || Block.isShapeFullCube(shape))
            return false;
        double top = shape.getMax(Direction.Axis.Y);
        if(top <= PathTerrain.LOW_STEP)
            return false;
        return !isGround(shape) || top > stepHeight();
    }

    private boolean cannotStandOn(PathContext context, BlockState state) {
        if(isLeftToTheGame(state))
            return false;
        VoxelShape shape = state.getCollisionShape(context.getWorld(), probe);
        return !shape.isEmpty() && !Block.isShapeFullCube(shape) && shape.getMax(Direction.Axis.Y) > PathTerrain.LOW_STEP && !isGround(shape);
    }

    private static boolean isGround(VoxelShape shape) {
        return shape.getBoundingBoxes().size() == 1 && shape.getMin(Direction.Axis.X) == 0 && shape.getMax(Direction.Axis.X) == 1
                && shape.getMin(Direction.Axis.Z) == 0 && shape.getMax(Direction.Axis.Z) == 1;
    }

    // Blocks vanilla or we already handle; an unopenable trapdoor is judged by shape
    private static boolean isLeftToTheGame(BlockState state) {
        return state.isAir() || !state.getFluidState().isEmpty() || state.getBlock() instanceof StairsBlock || state.getBlock() instanceof SlabBlock
                || state.isIn(BlockTags.CLIMBABLE) || state.isIn(BlockTags.DOORS) || isHatch(state) || state.isIn(BlockTags.FENCES)
                || state.isIn(BlockTags.WALLS) || state.getBlock() instanceof NpcDoor || isOpenBoard(state);
    }

    public static boolean isOpenBoard(BlockState state) {
        return BoardRules.isOpenBoard(state);
    }

    // A gate along a stair or drop edge is a railing and stays one
    private static boolean isGateAcrossLevelWay(PathContext context, int x, int y, int z, BlockState gate) {
        Direction facing = gate.get(FenceGateBlock.FACING);
        double[] floors = new double[2];
        for(int side = 0; side < 2; side++) {
            int sign = side == 0 ? 1 : -1;
            BlockPos.Mutable at = new BlockPos.Mutable(x + facing.getOffsetX() * sign, y, z + facing.getOffsetZ() * sign);
            if(!context.getBlockState(at).getCollisionShape(context.getWorld(), at).isEmpty())
                return false;
            at.move(Direction.DOWN);
            VoxelShape below = context.getBlockState(at).getCollisionShape(context.getWorld(), at);
            if(below.isEmpty())
                return false;
            floors[side] = below.getMax(Direction.Axis.Y);
        }
        return Math.abs(floors[0] - floors[1]) < PathTerrain.LOW_STEP;
    }

    private boolean isOverhead(PathContext context, BlockState state, int x, int y, int z) {
        if(isLeftToTheGame(state))
            return false;
        VoxelShape shape = state.getCollisionShape(context.getWorld(), probe.set(x, y, z));
        return !shape.isEmpty() && shape.getMin(Direction.Axis.Y) >= headroom();
    }

    // Vanilla checks headroom only for a whole-block step up
    private boolean hasHeadroom(PathNode from, PathNode next) {
        if(entity == null || next.y != from.y)
            return true;
        double feet = terrain.feetAt(next.x, next.y, next.z);
        if(feet <= terrain.feetAt(from.x, from.y, from.z) + LIFT)
            return true;
        double half = entity.getWidth() / 2;
        Box body = new Box(from.x + 0.5 - half, feet, from.z + 0.5 - half, from.x + 0.5 + half, feet + entity.getHeight(), from.z + 0.5 + half);
        return context.getWorld().isSpaceEmpty(body);
    }

    private double headroom() {
        return entity == null ? DEFAULT_HEADROOM : entity.getHeight() - 1;
    }

    private double stepHeight() {
        return entity == null ? DEFAULT_STEP : entity.getStepHeight();
    }

    // A full block plus a rock on top is more than a jump; vanilla can't see inside the node
    private boolean isReachableRise(PathNode from, PathNode next) {
        return next.y <= from.y || terrain.feetAt(next.x, next.y, next.z) - terrain.feetAt(from.x, from.y, from.z) <= MAXIMUM_RISE;
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

    public static boolean isFarmland(BlockState state) {
        return state.getBlock() instanceof FarmlandBlock || state.isIn(BlockTagsME.FARMLANDS);
    }

    public static boolean isHatch(BlockState state) {
        return state.getBlock() instanceof FenceGateBlock || (state.getBlock() instanceof TrapdoorBlock && state.isIn(BlockTags.WOODEN_TRAPDOORS));
    }
}
