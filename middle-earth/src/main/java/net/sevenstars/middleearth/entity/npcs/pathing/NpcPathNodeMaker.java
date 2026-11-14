package net.sevenstars.middleearth.entity.npcs.pathing;

import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayFIFOQueue;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathContext;
import net.minecraft.entity.ai.pathing.PathNode;
import net.sevenstars.middleearth.block.special.NpcDoor;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.property.Properties;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

// Land pathfinding knows nothing of ladders: a mob only ever gets onto one by accident. This adds the way up and down them
public class NpcPathNodeMaker extends LandPathNodeMaker {
    // Makes a walk on the flat win over a climb of the same length
    private static final float CLIMB_PENALTY = 1f;
    // Places looked at below a step down before the land there is taken to be open
    private static final int TRAP_SEARCH = 48;
    private static final int MAXIMUM_DROP = 3;
    // Of a block, for a mob with no step height of its own: the attribute's default
    private static final double DEFAULT_STEP = 0.6;
    // A carpet or a layer of snow
    private static final double LOW_STEP = 0.2;
    // Room to spare between a body and the end of a ladder's board, below which it is taken not to fit past
    private static final double SQUEEZE_MARGIN = 0.05;
    // Highest the feet go in one step up, the game's own limit, a little under the height of a jump
    private static final double MAXIMUM_RISE = 1.125;
    private static final byte TRAP = 1;
    private static final byte OPEN = 2;

    private final BlockPos.Mutable probe = new BlockPos.Mutable();
    // What the look below a step down found, for the length of one search
    private final Long2ByteOpenHashMap traps = new Long2ByteOpenHashMap();
    private final LongOpenHashSet seen = new LongOpenHashSet();
    private final LongArrayFIFOQueue open = new LongArrayFIFOQueue();

    @Override
    public PathNode getStart() {
        // The usual start is the ground under the mob, which is the wrong end of the ladder for one half way up
        BlockPos pos = entity.getBlockPos();
        if(entity.isClimbing() || isLadderCell(pos.getX(), pos.getY(), pos.getZ()))
            return getStart(pos);
        return super.getStart();
    }

    // The doors of the mod are no doors to the game, which sees a wall where one is shut
    @Override
    public PathNodeType getDefaultNodeType(PathContext context, int x, int y, int z) {
        BlockState state = context.getBlockState(probe.set(x, y, z));
        if(state.getBlock() instanceof NpcDoor door && door.opensForNpcs(state)) {
            if(door.isOpen(state))
                return PathNodeType.DOOR_OPEN;
            return door.canMove(context.getWorld(), probe, state) ? PathNodeType.DOOR_WOOD_CLOSED : PathNodeType.BLOCKED;
        }
        // A door they are to leave alone is a wall while it is shut, whichever kind of door the game takes it for
        if(isLockedShut(state))
            return PathNodeType.BLOCKED;
        // Also asked outside a search, when this class has no context of its own
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

    // No step down is taken without a way back up. From where the step lands, everywhere that can be walked to is looked
    // at: if none of it leads back to the height the step came from, it is a well, a pit or a cellar with no stairs
    private int removeTraps(PathNode[] successors, int count, PathNode from) {
        int kept = 0;
        for(int i = 0; i < count; i++) {
            PathNode next = successors[i];
            if(next.y < from.y && isTrap(next.x, next.y, next.z, from.y))
                continue;
            successors[kept++] = next;
        }
        return kept;
    }

    private boolean isTrap(int startX, int startY, int startZ, int wayOut) {
        // The same landing is asked about from every node around it
        long key = BlockPos.asLong(startX, startY, startZ) ^ (wayOut * 0x9E3779B97F4A7C15L);
        byte known = traps.get(key);
        if(known != 0)
            return known == TRAP;
        boolean trap = searchTrap(startX, startY, startZ, wayOut);
        traps.put(key, trap ? TRAP : OPEN);
        return trap;
    }

    private boolean searchTrap(int startX, int startY, int startZ, int wayOut) {
        seen.clear();
        open.clear();
        visit(startX, startY, startZ);
        while(!open.isEmpty()) {
            // This much room is open country below a ledge, and no trap
            if(seen.size() > TRAP_SEARCH)
                return false;
            long cell = open.dequeueLong();
            int x = BlockPos.unpackLongX(cell);
            int y = BlockPos.unpackLongY(cell);
            int z = BlockPos.unpackLongZ(cell);
            if(y >= wayOut || isLadderCell(x, y, z))
                return false;
            // Swimming up
            if(isWater(x, y + 1, z))
                visit(x, y + 1, z);
            for(Direction side : Direction.Type.HORIZONTAL) {
                int nextX = x + side.getOffsetX();
                int nextZ = z + side.getOffsetZ();
                if(isWalkedThrough(nextX, y, nextZ) && isWalkedThrough(nextX, y + 1, nextZ)) {
                    // Along the floor, or down to it where it falls away
                    int floor = y;
                    while(floor > y - MAXIMUM_DROP && isWalkedThrough(nextX, floor - 1, nextZ) && !isWater(nextX, floor, nextZ))
                        floor--;
                    visit(nextX, floor, nextZ);
                } else if(isWalkedThrough(x, y + 2, z) && isWalkedThrough(nextX, y + 1, nextZ) && isWalkedThrough(nextX, y + 2, nextZ)) {
                    // One block up is a step
                    visit(nextX, y + 1, nextZ);
                }
            }
        }
        return true;
    }

    private void visit(int x, int y, int z) {
        long cell = BlockPos.asLong(x, y, z);
        if(seen.add(cell))
            open.enqueue(cell);
    }

    // What a way out leads through: free space, a door that can be opened, a carpet on the floor
    private boolean isWalkedThrough(int x, int y, int z) {
        BlockState state = context.getBlockState(probe.set(x, y, z));
        if(isLockedShut(state))
            return false;
        if(isHatch(state) || state.isIn(BlockTags.CLIMBABLE) || state.isIn(BlockTags.WOODEN_DOORS))
            return true;
        if(state.getBlock() instanceof NpcDoor door && door.opensForNpcs(state))
            return true;
        VoxelShape shape = state.getCollisionShape(context.getWorld(), probe);
        return shape.isEmpty() || shape.getMax(Direction.Axis.Y) <= LOW_STEP;
    }

    @Override
    public void clear() {
        traps.clear();
        super.clear();
    }

    private boolean isWater(int x, int y, int z) {
        return context.getBlockState(probe.set(x, y, z)).getFluidState().isIn(FluidTags.WATER);
    }

    // The game takes every block that is not a full cube for open floor, and walks its mobs straight into rocks, stools
    // and the lining of a well, where they stand pressed against it. At the height of the feet, solid ground taller than
    // the npc can step is in the way, and the path goes on top of it instead, which the npc jumps up to. Furniture and
    // other odd shapes are in the way at any height, to be walked around
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

    // Nor is the top of a table or a stool a way to walk, where a rock or a path is
    private boolean cannotStandOn(PathContext context, BlockState state) {
        if(isLeftToTheGame(state))
            return false;
        VoxelShape shape = state.getCollisionShape(context.getWorld(), probe);
        return !shape.isEmpty() && !Block.isShapeFullCube(shape) && shape.getMax(Direction.Axis.Y) > LOW_STEP && !isGround(shape);
    }

    // Solid all the way across, as a path, a tilled field, a slab or a rock is: something to stand on
    private static boolean isGround(VoxelShape shape) {
        return shape.getBoundingBoxes().size() == 1 && shape.getMin(Direction.Axis.X) == 0 && shape.getMax(Direction.Axis.X) == 1
                && shape.getMin(Direction.Axis.Z) == 0 && shape.getMax(Direction.Axis.Z) == 1;
    }

    // These the game knows how to deal with, or this class does. A trapdoor nobody can open by hand is not among them,
    // and is judged by its shape like any other block
    private static boolean isLeftToTheGame(BlockState state) {
        return state.isAir() || !state.getFluidState().isEmpty() || state.getBlock() instanceof StairsBlock || state.getBlock() instanceof SlabBlock
                || state.isIn(BlockTags.CLIMBABLE) || state.isIn(BlockTags.DOORS) || isHatch(state) || state.isIn(BlockTags.FENCES)
                || state.isIn(BlockTags.WALLS) || state.getBlock() instanceof NpcDoor;
    }

    // What the npc walks up without a jump, from its own attribute: anything higher it jumps onto
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

    // A ladder's board fills one side of its block, and a body wider than the room left beside it catches on the end of the
    // board when it comes along it. Such a body gets onto and off a ladder from the front only
    private int removeSideStepsAtLadders(PathNode[] successors, int count, PathNode from) {
        int kept = 0;
        for(int i = 0; i < count; i++) {
            PathNode next = successors[i];
            int stepX = next.x - from.x;
            int stepZ = next.z - from.z;
            if(next.y == from.y && (isPastBoard(next.x, next.y, next.z, stepX, stepZ) || isPastBoard(from.x, from.y, from.z, stepX, stepZ)))
                continue;
            successors[kept++] = next;
        }
        return kept;
    }

    private boolean isPastBoard(int x, int y, int z, int stepX, int stepZ) {
        BlockState state = context.getBlockState(probe.set(x, y, z));
        if(entity == null || !state.isIn(BlockTags.CLIMBABLE) || !state.contains(Properties.HORIZONTAL_FACING))
            return false;
        Direction facing = state.get(Properties.HORIZONTAL_FACING);
        boolean front = facing.getAxis() == Direction.Axis.X ? stepZ == 0 : stepX == 0;
        if(front)
            return false;
        VoxelShape shape = state.getCollisionShape(context.getWorld(), probe);
        double board = shape.isEmpty() ? 0 : shape.getMax(facing.getAxis()) - shape.getMin(facing.getAxis());
        return entity.getWidth() > 1 - board - SQUEEZE_MARGIN;
    }

    // A block up onto rocks or a slab lying on top of another is more than a jump, though either alone is less. The game
    // measures the step from the block under a node and never sees what lies in it, and the npc jumps at it for good
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

    // Where the feet are when standing at a node: on whatever lies low in its block, or else on the block below
    private double feetAt(int x, int y, int z) {
        VoxelShape inside = context.getBlockState(probe.set(x, y, z)).getCollisionShape(context.getWorld(), probe);
        if(!inside.isEmpty() && inside.getMax(Direction.Axis.Y) < 1)
            return y + inside.getMax(Direction.Axis.Y);
        VoxelShape below = context.getBlockState(probe.set(x, y - 1, z)).getCollisionShape(context.getWorld(), probe);
        return below.isEmpty() ? y - 1 : y - 1 + below.getMax(Direction.Axis.Y);
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

    private static boolean isLockedShut(BlockState state) {
        if(!state.isIn(NpcDoor.LOCKED))
            return false;
        if(state.getBlock() instanceof NpcDoor door)
            return !door.isOpen(state);
        return state.contains(Properties.OPEN) && !state.get(Properties.OPEN);
    }

    // The gate or trapdoor closing a loft, which the npc opens on its way
    public static boolean isHatch(BlockState state) {
        return state.getBlock() instanceof FenceGateBlock || (state.getBlock() instanceof TrapdoorBlock && state.isIn(BlockTags.WOODEN_TRAPDOORS));
    }
}
