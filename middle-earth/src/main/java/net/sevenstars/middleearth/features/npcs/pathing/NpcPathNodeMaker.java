package net.sevenstars.middleearth.features.npcs.pathing;

import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayFIFOQueue;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.ScaffoldingBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathContext;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.Heightmap;
import net.sevenstars.middleearth.block.special.NpcDoor;
import net.sevenstars.middleearth.block.special.fireBlocks.AbstractToggleableFireBlock;
import net.sevenstars.middleearth.block.special.plants.ToughBerryBushBlock;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.utils.BlockTagsME;

// Vanilla misses ladders, our doors, open trapdoors and furniture
public class NpcPathNodeMaker extends LandPathNodeMaker {
    private static final float CLIMB_PENALTY = 1f;
    private static final float FIELD_PENALTY = 8f;
    // Canopy is ~200 positions, a cave beside a hall 300 to 1000+
    private static final int TRAP_SEARCH = 256;
    private static final int TRAP_SEARCH_COVERED = 1500;
    private static final int ROOF_HEIGHT = 2;
    private static final int MAXIMUM_DROP = 3;
    private static final double DEFAULT_STEP = 0.6;
    private static final double DEFAULT_HEADROOM = 0.8;
    // Smaller rises are ignored
    private static final double LIFT = 0.01;
    // Carpet or a layer of snow
    private static final double LOW_STEP = 0.2;
    // Spare room needed to squeeze past a ladder's board
    private static final double SQUEEZE_MARGIN = 0.05;
    // Vanilla's limit, a bit under a jump
    private static final double MAXIMUM_RISE = 1.125;
    static final double FAR_HALF = 0.5;
    private static final int BODY_BLOCKS = 2;
    private static final int BESIDE_DISTANCE = 1;
    private static final byte TRAP = 1;
    private static final byte OPEN = 2;

    private final BlockPos.Mutable probe = new BlockPos.Mutable();
    private final Long2ByteOpenHashMap traps = new Long2ByteOpenHashMap();
    private final LongOpenHashSet seen = new LongOpenHashSet();
    private final LongArrayFIFOQueue open = new LongArrayFIFOQueue();

    @Override
    public PathNode getStart() {
        // Ground is the wrong start for a mob halfway up a ladder
        BlockPos pos = entity.getBlockPos();
        if(entity.isClimbing() || isLadderCell(pos.getX(), pos.getY(), pos.getZ()))
            return getStart(pos);
        return super.getStart();
    }

    @Override
    public PathNodeType getDefaultNodeType(MobEntity mob, BlockPos pos) {
        MobEntity before = entity;
        entity = mob;
        try {
            return super.getDefaultNodeType(mob, pos);
        } finally {
            entity = before;
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
        // Shut trapdoor as a lintel leaves room below; see removeFeetUnderLintels
        if(isOverhead(context, state, x, y, z))
            return PathNodeType.OPEN;
        if(isLockedShut(state, probe.set(x, y, z)))
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
        int count = super.getSuccessors(successors, node);
        count = removeTraps(successors, count, node);
        count = removeObstacleTops(successors, count);
        count = removeSideStepsAtLadders(successors, count, node);
        count = removeTooHighSteps(successors, count, node);
        count = removeStepsThroughBoards(successors, count, node);
        count = removeFeetUnderLintels(successors, count);
        count = removeHeadBumps(successors, count, node);
        count = removeLadderTops(successors, count);
        keepOffFields(successors, count);
        if(isLadderCell(node.x, node.y, node.z)) {
            if(isLadderCell(node.x, node.y + 1, node.z))
                count = add(successors, count, node.x, node.y + 1, node.z);
            if(isClimbable(node.x, node.y - 1, node.z) && isPassable(node.x, node.y - 1, node.z))
                count = add(successors, count, node.x, node.y - 1, node.z);
        }
        // A shaft top has no floor, so offer walking into it too
        for(Direction side : Direction.Type.HORIZONTAL) {
            int x = node.x + side.getOffsetX();
            int z = node.z + side.getOffsetZ();
            if(isLadderCell(x, node.y, z) && !isPastBoard(x, node.y, z, side.getOffsetX(), side.getOffsetZ(), true)
                    && !isPastBoard(node.x, node.y, node.z, side.getOffsetX(), side.getOffsetZ(), false))
                count = add(successors, count, x, node.y, z);
        }
        return count;
    }

    @Override
    public void clear() {
        traps.clear();
        super.clear();
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

    // Don't step down into a pit or cellar with no way back up
    private int removeTraps(PathNode[] successors, int count, PathNode from) {
        int kept = 0;
        for(int i = 0; i < count; i++) {
            PathNode next = successors[i];
            if(next.y < from.y && isTrap(next.x, next.y, next.z, from))
                continue;
            successors[kept++] = next;
        }
        return kept;
    }

    private boolean isTrap(int startX, int startY, int startZ, PathNode from) {
        // Every node around a landing asks, so cache
        long key = BlockPos.asLong(startX, startY, startZ) * 31 + BlockPos.asLong(from.x, from.y, from.z);
        byte known = traps.get(key);
        if(known != 0)
            return known == TRAP;
        boolean trap = searchTrap(startX, startY, startZ, from);
        traps.put(key, trap ? TRAP : OPEN);
        return trap;
    }

    // Same height elsewhere doesn't count, e.g. a cave rising at its far end
    private boolean searchTrap(int startX, int startY, int startZ, PathNode from) {
        seen.clear();
        open.clear();
        visit(startX, startY, startZ);
        boolean covered = isUnderRoof(startX, startY, startZ);
        int limit = covered ? TRAP_SEARCH_COVERED : TRAP_SEARCH;
        while(!open.isEmpty()) {
            if(seen.size() > limit)
                return covered;
            long cell = open.dequeueLong();
            int x = BlockPos.unpackLongX(cell);
            int y = BlockPos.unpackLongY(cell);
            int z = BlockPos.unpackLongZ(cell);
            if(y >= from.y && Math.abs(x - from.x) <= BESIDE_DISTANCE && Math.abs(z - from.z) <= BESIDE_DISTANCE)
                return false;
            if(isWater(x, y + 1, z) || isLadderCell(x, y + 1, z))
                visit(x, y + 1, z);
            for(Direction side : Direction.Type.HORIZONTAL) {
                int nextX = x + side.getOffsetX();
                int nextZ = z + side.getOffsetZ();
                if(isWalkedThrough(nextX, y, nextZ) && isWalkedThrough(nextX, y + 1, nextZ)) {
                    int floor = y;
                    while(floor > y - MAXIMUM_DROP && isWalkedThrough(nextX, floor - 1, nextZ) && !isWater(nextX, floor, nextZ))
                        floor--;
                    if(isWalkedThrough(nextX, floor - 1, nextZ) && !isWater(nextX, floor, nextZ))
                        continue;
                    visit(nextX, floor, nextZ);
                } else if(isWalkedThrough(x, y + 2, z) && isWalkedThrough(nextX, y + 1, nextZ) && isWalkedThrough(nextX, y + 2, nextZ)) {
                    visit(nextX, y + 1, nextZ);
                }
            }
        }
        return true;
    }

    private boolean isUnderRoof(int x, int y, int z) {
        return entity != null && entity.getEntityWorld().getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z) > y + ROOF_HEIGHT;
    }

    private void visit(int x, int y, int z) {
        long cell = BlockPos.asLong(x, y, z);
        if(seen.add(cell))
            open.enqueue(cell);
    }

    private boolean isWalkedThrough(int x, int y, int z) {
        BlockState state = context.getBlockState(probe.set(x, y, z));
        if(isLockedShut(state, probe))
            return false;
        if(isHatch(state) || isOpenBoard(state) || state.isIn(BlockTags.CLIMBABLE) || state.isIn(BlockTags.WOODEN_DOORS))
            return true;
        if(state.getBlock() instanceof NpcDoor)
            return true;
        VoxelShape shape = state.getCollisionShape(context.getWorld(), probe);
        return shape.isEmpty() || shape.getMax(Direction.Axis.Y) <= LOW_STEP;
    }

    private boolean isWater(int x, int y, int z) {
        return context.getBlockState(probe.set(x, y, z)).getFluidState().isIn(FluidTags.WATER);
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
        if(top <= LOW_STEP)
            return false;
        return !isGround(shape) || top > stepHeight();
    }

    private boolean cannotStandOn(PathContext context, BlockState state) {
        if(isLeftToTheGame(state))
            return false;
        VoxelShape shape = state.getCollisionShape(context.getWorld(), probe);
        return !shape.isEmpty() && !Block.isShapeFullCube(shape) && shape.getMax(Direction.Axis.Y) > LOW_STEP && !isGround(shape);
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
        return state.getBlock() instanceof TrapdoorBlock && state.get(TrapdoorBlock.OPEN);
    }

    private int removeStepsThroughBoards(PathNode[] successors, int count, PathNode from) {
        int kept = 0;
        for(int i = 0; i < count; i++) {
            PathNode next = successors[i];
            int stepX = Integer.signum(next.x - from.x);
            int stepZ = Integer.signum(next.z - from.z);
            if(crossesBoard(from, next, stepX, stepZ))
                continue;
            successors[kept++] = next;
        }
        return kept;
    }

    private boolean crossesBoard(PathNode from, PathNode next, int stepX, int stepZ) {
        for(int up = 0; up < BODY_BLOCKS; up++) {
            if(boardTowards(from.x, from.y + up, from.z, stepX, stepZ) || boardTowards(next.x, next.y + up, next.z, -stepX, -stepZ))
                return true;
            if(next.y < from.y && boardTowards(next.x, from.y + up, next.z, -stepX, -stepZ))
                return true;
        }
        return false;
    }

    private boolean boardTowards(int x, int y, int z, int stepX, int stepZ) {
        BlockState state = context.getBlockState(probe.set(x, y, z));
        if(!isOpenBoard(state))
            return false;
        VoxelShape shape = state.getCollisionShape(context.getWorld(), probe);
        if(shape.isEmpty())
            return false;
        return stepX > 0 && shape.getMin(Direction.Axis.X) >= FAR_HALF || stepX < 0 && shape.getMax(Direction.Axis.X) <= FAR_HALF
                || stepZ > 0 && shape.getMin(Direction.Axis.Z) >= FAR_HALF || stepZ < 0 && shape.getMax(Direction.Axis.Z) <= FAR_HALF;
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
        return Math.abs(floors[0] - floors[1]) < LOW_STEP;
    }

    private boolean isOverhead(PathContext context, BlockState state, int x, int y, int z) {
        if(isLeftToTheGame(state))
            return false;
        VoxelShape shape = state.getCollisionShape(context.getWorld(), probe.set(x, y, z));
        return !shape.isEmpty() && shape.getMin(Direction.Axis.Y) >= headroom();
    }

    private int removeFeetUnderLintels(PathNode[] successors, int count) {
        int kept = 0;
        for(int i = 0; i < count; i++) {
            PathNode next = successors[i];
            if(isOverhead(context, context.getBlockState(probe.set(next.x, next.y, next.z)), next.x, next.y, next.z))
                continue;
            successors[kept++] = next;
        }
        return kept;
    }

    // Vanilla checks headroom only for a whole-block step up
    private int removeHeadBumps(PathNode[] successors, int count, PathNode from) {
        if(entity == null)
            return count;
        double fromFeet = feetAt(from.x, from.y, from.z);
        double half = entity.getWidth() / 2;
        int kept = 0;
        for(int i = 0; i < count; i++) {
            PathNode next = successors[i];
            double feet = feetAt(next.x, next.y, next.z);
            if(next.y == from.y && feet > fromFeet + LIFT) {
                Box body = new Box(from.x + 0.5 - half, feet, from.z + 0.5 - half, from.x + 0.5 + half, feet + entity.getHeight(), from.z + 0.5 + half);
                if(!context.getWorld().isSpaceEmpty(body))
                    continue;
            }
            successors[kept++] = next;
        }
        return kept;
    }

    private double headroom() {
        return entity == null ? DEFAULT_HEADROOM : entity.getHeight() - 1;
    }

    private double stepHeight() {
        return entity == null ? DEFAULT_STEP : entity.getStepHeight();
    }

    private int removeObstacleTops(PathNode[] successors, int count) {
        int kept = 0;
        for(int i = 0; i < count; i++) {
            PathNode next = successors[i];
            if(cannotStandOn(context, context.getBlockState(probe.set(next.x, next.y - 1, next.z))))
                continue;
            successors[kept++] = next;
        }
        return kept;
    }

    // A wide body catches on a ladder board's end, so enter and leave from the front
    private int removeSideStepsAtLadders(PathNode[] successors, int count, PathNode from) {
        int kept = 0;
        for(int i = 0; i < count; i++) {
            PathNode next = successors[i];
            int stepX = next.x - from.x;
            int stepZ = next.z - from.z;
            if(next.y <= from.y && (isPastBoard(next.x, next.y, next.z, stepX, stepZ, true) || isPastBoard(from.x, from.y, from.z, stepX, stepZ, false)))
                continue;
            successors[kept++] = next;
        }
        return kept;
    }

    // True if the ladder's board blocks the step. Side comes from the shape, not a facing
    private boolean isPastBoard(int x, int y, int z, int stepX, int stepZ, boolean entering) {
        BlockState state = context.getBlockState(probe.set(x, y, z));
        if(entity == null || !state.isIn(BlockTags.CLIMBABLE))
            return false;
        VoxelShape shape = state.getCollisionShape(context.getWorld(), probe);
        if(shape.isEmpty())
            return false;
        int boardX = shape.getMax(Direction.Axis.X) <= FAR_HALF ? -1 : shape.getMin(Direction.Axis.X) >= FAR_HALF ? 1 : 0;
        int boardZ = shape.getMax(Direction.Axis.Z) <= FAR_HALF ? -1 : shape.getMin(Direction.Axis.Z) >= FAR_HALF ? 1 : 0;
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

    // A full block plus a rock on top is more than a jump; vanilla can't see inside the node
    private int removeTooHighSteps(PathNode[] successors, int count, PathNode from) {
        double fromFeet = feetAt(from.x, from.y, from.z);
        int kept = 0;
        for(int i = 0; i < count; i++) {
            PathNode next = successors[i];
            if(next.y > from.y && feetAt(next.x, next.y, next.z) - fromFeet > MAXIMUM_RISE)
                continue;
            successors[kept++] = next;
        }
        return kept;
    }

    // Swimming or on a ladder the feet are in the node's block, else leaving water looks too high
    private double feetAt(int x, int y, int z) {
        VoxelShape inside = context.getBlockState(probe.set(x, y, z)).getCollisionShape(context.getWorld(), probe);
        if(!inside.isEmpty() && inside.getMax(Direction.Axis.Y) < 1)
            return y + inside.getMax(Direction.Axis.Y);
        VoxelShape below = context.getBlockState(probe.set(x, y - 1, z)).getCollisionShape(context.getWorld(), probe);
        return below.isEmpty() ? y : y - 1 + below.getMax(Direction.Axis.Y);
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

    // Above the top needs a floor to step off onto, unlike a one-block garden fence
    private boolean isLadderCell(int x, int y, int z) {
        if(!isPassable(x, y, z) || !isPassable(x, y + 1, z))
            return false;
        return isClimbable(x, y, z) || isClimbable(x, y - 1, z) && hasLanding(x, y, z);
    }

    private boolean hasLanding(int x, int y, int z) {
        for(Direction side : Direction.Type.HORIZONTAL) {
            int nextX = x + side.getOffsetX();
            int nextZ = z + side.getOffsetZ();
            for(int up = 0; up <= 1; up++) {
                int floor = y + up - 1;
                if(isPassable(nextX, floor + 1, nextZ) && isPassable(nextX, floor + 2, nextZ) && !isPassable(nextX, floor, nextZ) && !isClimbable(nextX, floor, nextZ))
                    return true;
            }
        }
        return false;
    }

    // Vanilla treats a ladder as solid and the air above one as floor
    private int removeLadderTops(PathNode[] successors, int count) {
        int kept = 0;
        for(int i = 0; i < count; i++) {
            PathNode next = successors[i];
            if(isClimbable(next.x, next.y - 1, next.z) && !isClimbable(next.x, next.y, next.z) && !hasLanding(next.x, next.y, next.z))
                continue;
            successors[kept++] = next;
        }
        return kept;
    }

    // Mobs never crouch, so scaffolding's top is floor. Flat ladders don't climb
    private boolean isClimbable(int x, int y, int z) {
        BlockState state = context.getBlockState(probe.set(x, y, z));
        return state.isIn(BlockTags.CLIMBABLE) && !(state.getBlock() instanceof ScaffoldingBlock)
                && !(state.contains(Properties.BLOCK_FACE) && state.get(Properties.BLOCK_FACE) == BlockFace.FLOOR);
    }

    private boolean isPassable(int x, int y, int z) {
        BlockState state = context.getBlockState(probe.set(x, y, z));
        if(isHatch(state) || state.isIn(BlockTags.CLIMBABLE))
            return true;
        return state.getCollisionShape(context.getWorld(), probe).isEmpty();
    }

    // Only openable blocks, or every path block gets looked up among the doors
    private boolean isLockedShut(BlockState state, BlockPos pos) {
        if(!(state.getBlock() instanceof NpcDoor) && !state.contains(Properties.OPEN))
            return false;
        if(NpcDoor.opensFor(entity.getEntityWorld(), pos, state, entity))
            return false;
        if(state.getBlock() instanceof NpcDoor door)
            return !door.isOpen(state);
        return !state.get(Properties.OPEN);
    }

    public static boolean isFarmland(BlockState state) {
        return state.getBlock() instanceof FarmlandBlock || state.isIn(BlockTagsME.FARMLANDS);
    }

    public static boolean isHatch(BlockState state) {
        return state.getBlock() instanceof FenceGateBlock || (state.getBlock() instanceof TrapdoorBlock && state.isIn(BlockTags.WOODEN_TRAPDOORS));
    }
}
