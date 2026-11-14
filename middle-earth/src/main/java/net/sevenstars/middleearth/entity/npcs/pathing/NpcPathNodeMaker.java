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
    // Of a block. Beds and bottom slabs are stepped on, a chair back or a table top is not
    private static final double MAXIMUM_STEP = 0.75;
    // A carpet or a layer of snow
    private static final double LOW_STEP = 0.2;
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

    // The game takes every block that is not a full cube for open floor, and walks its mobs into tables, half walls and
    // the lining of a well. Whatever has a shape of its own too tall to step on is in the way, whichever block it is
    private boolean isObstacle(PathContext context, BlockState state) {
        if(state.isAir() || !state.getFluidState().isEmpty() || state.getBlock() instanceof StairsBlock || state.getBlock() instanceof SlabBlock)
            return false;
        // These the game knows how to deal with, or this class does
        if(state.isIn(BlockTags.CLIMBABLE) || state.isIn(BlockTags.DOORS) || state.isIn(BlockTags.TRAPDOORS) || state.isIn(BlockTags.FENCE_GATES)
                || state.isIn(BlockTags.FENCES) || state.isIn(BlockTags.WALLS) || state.getBlock() instanceof NpcDoor)
            return false;
        VoxelShape shape = state.getCollisionShape(context.getWorld(), probe);
        if(shape.isEmpty() || Block.isShapeFullCube(shape) || shape.getMax(Direction.Axis.Y) <= MAXIMUM_STEP)
            return false;
        // A path or a tilled field is a block a little short of full, and ground like any other
        boolean ground = shape.getMin(Direction.Axis.Y) == 0 && shape.getMin(Direction.Axis.X) == 0 && shape.getMax(Direction.Axis.X) == 1
                && shape.getMin(Direction.Axis.Z) == 0 && shape.getMax(Direction.Axis.Z) == 1 && shape.getBoundingBoxes().size() == 1;
        return !ground;
    }

    // Nor is the top of a table a way to walk
    private int removeObstacleTops(PathNode[] successors, int count) {
        int kept = 0;
        for(int i = 0; i < count; i++) {
            PathNode next = successors[i];
            if(isObstacle(context, context.getBlockState(probe.set(next.x, next.y - 1, next.z))))
                continue;
            successors[kept++] = next;
        }
        return kept;
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
