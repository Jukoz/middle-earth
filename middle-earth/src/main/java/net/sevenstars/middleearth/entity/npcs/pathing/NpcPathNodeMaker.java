package net.sevenstars.middleearth.entity.npcs.pathing;

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
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.utils.BlockTagsME;

// Land pathfinding knows nothing of ladders: a mob only ever gets onto one by accident. This adds the way up and down them
public class NpcPathNodeMaker extends LandPathNodeMaker {
    // Makes a walk on the flat win over a climb of the same length
    private static final float CLIMB_PENALTY = 1f;
    // A step on farmland, for anyone not out farming: a way round a field of a dozen rows is the shorter for it
    private static final float FIELD_PENALTY = 8f;
    // Places looked at below a step down before the land there is taken to be open, outdoors and under a roof. A canopy of
    // leaves is a couple of hundred, a cave beside a hall a few hundred to well over a thousand
    private static final int TRAP_SEARCH = 256;
    private static final int TRAP_SEARCH_COVERED = 1500;
    private static final int ROOF_HEIGHT = 2;
    private static final int MAXIMUM_DROP = 3;
    // Of a block, for a mob with no step height of its own: the attribute's default
    private static final double DEFAULT_STEP = 0.6;
    private static final double DEFAULT_HEADROOM = 0.8;
    // A rise underfoot smaller than this is no rise
    private static final double LIFT = 0.01;
    // A carpet or a layer of snow
    private static final double LOW_STEP = 0.2;
    // Room to spare between a body and the end of a ladder's board, below which it is taken not to fit past
    private static final double SQUEEZE_MARGIN = 0.05;
    // Highest the feet go in one step up, the game's own limit, a little under the height of a jump
    private static final double MAXIMUM_RISE = 1.125;
    // How far across its block a board has to lie to be on the far side of it
    private static final double FAR_HALF = 0.5;
    private static final int BODY_BLOCKS = 2;
    // Blocks to the side of where the step came down that still count as beside it
    private static final int BESIDE_DISTANCE = 1;
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
        if(state.getBlock() instanceof NpcDoor door && NpcDoor.opensFor(entity.getWorld(), probe, state, entity)) {
            if(door.isOpen(state))
                return PathNodeType.DOOR_OPEN;
            return door.canMove(context.getWorld(), probe, state) ? PathNodeType.DOOR_WOOD_CLOSED : PathNodeType.BLOCKED;
        }
        if(state.getBlock() instanceof ScaffoldingBlock)
            return PathNodeType.BLOCKED;
        // The braziers, bonfires and fire bowls of the mod burn whoever walks into them, as a campfire does, and a thorny
        // berry bush pricks
        if(state.getBlock() instanceof AbstractToggleableFireBlock && state.get(AbstractToggleableFireBlock.LIT))
            return PathNodeType.DAMAGE_FIRE;
        if(state.getBlock() instanceof ToughBerryBushBlock)
            return PathNodeType.DAMAGE_OTHER;
        // The game takes any trapdoor for something to stand on at its own height. An open one is a board at the side of an
        // empty block, a floor only where there is one below it, and nothing at all to stand on top of
        BlockState below = context.getBlockState(new BlockPos(x, y - 1, z));
        if(isOpenBoard(state)) {
            BlockPos under = new BlockPos(x, y - 1, z);
            return !below.getCollisionShape(context.getWorld(), under).isEmpty() && !isOpenBoard(below) ? PathNodeType.WALKABLE : PathNodeType.OPEN;
        }
        if(state.isAir() && isOpenBoard(below))
            return PathNodeType.OPEN;
        // A trapdoor shut against the top of a doorway as its lintel leaves room for a body below it. Where the feet would be
        // in it, the step is taken out again in removeFeetUnderLintels
        if(isOverhead(context, state, x, y, z))
            return PathNodeType.OPEN;
        // A door they are to leave alone is a wall while it is shut, whichever kind of door the game takes it for
        if(isLockedShut(state, probe.set(x, y, z)))
            return PathNodeType.BLOCKED;
        // The game takes a shut gate for a fence for good. One across a way on the level is opened on the way, as a door is
        if(state.getBlock() instanceof FenceGateBlock && !state.get(FenceGateBlock.OPEN) && isGateAcrossLevelWay(context, x, y, z, state))
            return PathNodeType.DOOR_WOOD_CLOSED;
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
        // The top of a shaft has no floor, so walking into it has to be offered too, by the same way in as any other step
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

    // Fields are walked by those working them, and everybody else goes round
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

    // No step down is taken without a way back up. From where the step lands, everywhere that can be walked to is looked
    // at: if none of it leads back to the height the step came from, it is a well, a pit or a cellar with no stairs
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
        // The same landing is asked about from every node around it
        long key = BlockPos.asLong(startX, startY, startZ) * 31 + BlockPos.asLong(from.x, from.y, from.z);
        byte known = traps.get(key);
        if(known != 0)
            return known == TRAP;
        boolean trap = searchTrap(startX, startY, startZ, from);
        traps.put(key, trap ? TRAP : OPEN);
        return trap;
    }

    // A way out is one back up beside the step it came down. Getting as high somewhere else is no way out, as of a cave that
    // rises at its far end. Under a roof a large space below is a cave or a lower hall, outdoors it is open country
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
            // Swimming up, and climbing
            if(isWater(x, y + 1, z) || isLadderCell(x, y + 1, z))
                visit(x, y + 1, z);
            for(Direction side : Direction.Type.HORIZONTAL) {
                int nextX = x + side.getOffsetX();
                int nextZ = z + side.getOffsetZ();
                if(isWalkedThrough(nextX, y, nextZ) && isWalkedThrough(nextX, y + 1, nextZ)) {
                    // Along the floor, or down to it where it falls away. Off an edge with no floor within a drop is no way
                    int floor = y;
                    while(floor > y - MAXIMUM_DROP && isWalkedThrough(nextX, floor - 1, nextZ) && !isWater(nextX, floor, nextZ))
                        floor--;
                    if(isWalkedThrough(nextX, floor - 1, nextZ) && !isWater(nextX, floor, nextZ))
                        continue;
                    visit(nextX, floor, nextZ);
                } else if(isWalkedThrough(x, y + 2, z) && isWalkedThrough(nextX, y + 1, nextZ) && isWalkedThrough(nextX, y + 2, nextZ)) {
                    // One block up is a step
                    visit(nextX, y + 1, nextZ);
                }
            }
        }
        return true;
    }

    private boolean isUnderRoof(int x, int y, int z) {
        return entity != null && entity.getWorld().getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z) > y + ROOF_HEIGHT;
    }

    private void visit(int x, int y, int z) {
        long cell = BlockPos.asLong(x, y, z);
        if(seen.add(cell))
            open.enqueue(cell);
    }

    // What a way out leads through: free space, a door that can be opened, a carpet on the floor
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
                || state.isIn(BlockTags.WALLS) || state.getBlock() instanceof NpcDoor || isOpenBoard(state);
    }

    // A trapdoor standing open is a board against one side of its block, of stone as much as of wood. Shutters at the sides
    // of a doorway leave the way through clear
    public static boolean isOpenBoard(BlockState state) {
        return state.getBlock() instanceof TrapdoorBlock && state.get(TrapdoorBlock.OPEN);
    }

    // Along such a board is a way, through it is not
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
            // A step down goes past the board at the height it left from, before it drops
            if(next.y < from.y && boardTowards(next.x, from.y + up, next.z, -stepX, -stepZ))
                return true;
        }
        return false;
    }

    // An open trapdoor in the block with its board on the side a step goes out by
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

    // Room to walk on either side of the gate, on floors of one height. A gate set along the edge of a stair or a drop is a
    // railing, and stays one
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

    // A shape high up in its block, under which there is room for a head
    private boolean isOverhead(PathContext context, BlockState state, int x, int y, int z) {
        if(state.isAir() || isLeftToTheGame(state))
            return false;
        BlockPos.Mutable at = new BlockPos.Mutable(x, y, z);
        VoxelShape shape = state.getCollisionShape(context.getWorld(), at);
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

    // A cushion or a rug underfoot lifts the body a little within its block, and a board or a beam just over the head stops
    // it there. The game looks for this only on a step up of a whole block
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

    // How much of the block over the feet a body fills
    private double headroom() {
        return entity == null ? DEFAULT_HEADROOM : entity.getHeight() - 1;
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
            // Down a step as much as on the level: a stair below the board of a railing is no way through it
            if(next.y <= from.y && (isPastBoard(next.x, next.y, next.z, stepX, stepZ, true) || isPastBoard(from.x, from.y, from.z, stepX, stepZ, false)))
                continue;
            successors[kept++] = next;
        }
        return kept;
    }

    // Into the block (entering) or out of it. Straight through the board is no way at all, which also keeps paths out of the
    // ladders set round the top of a tower as a railing. The side of the board is read from its shape, as ladders of the mod
    // do not all say which way they face
    private boolean isPastBoard(int x, int y, int z, int stepX, int stepZ, boolean entering) {
        BlockState state = context.getBlockState(probe.set(x, y, z));
        if(entity == null || !state.isIn(BlockTags.CLIMBABLE))
            return false;
        VoxelShape shape = state.getCollisionShape(context.getWorld(), probe);
        if(shape.isEmpty())
            return false;
        // The side of the block the board stands against, -1 or 1 along its axis, 0 along the other
        int boardX = shape.getMax(Direction.Axis.X) <= FAR_HALF ? -1 : shape.getMin(Direction.Axis.X) >= FAR_HALF ? 1 : 0;
        int boardZ = shape.getMax(Direction.Axis.Z) <= FAR_HALF ? -1 : shape.getMin(Direction.Axis.Z) >= FAR_HALF ? 1 : 0;
        if(boardX == 0 && boardZ == 0)
            return false;
        // In from the board's side or out by it
        int through = entering ? -1 : 1;
        if(boardX != 0 && stepX == through * boardX || boardZ != 0 && stepZ == through * boardZ)
            return true;
        // Along the board, past its end, a body wider than the room beside it catches
        boolean along = boardX != 0 ? stepZ != 0 : stepX != 0;
        if(!along)
            return false;
        double board = boardX != 0 ? shape.getMax(Direction.Axis.X) - shape.getMin(Direction.Axis.X) : shape.getMax(Direction.Axis.Z) - shape.getMin(Direction.Axis.Z);
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

    // Where the feet are when standing at a node: on whatever lies low in its block, or else on the block below. With
    // nothing solid below, a swimmer or somebody on a ladder, they are taken to be in the node's own block: counted from a
    // block lower, the step out of the water onto the bank looked like two blocks, and only ways on through the water were left
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

    // A place on a ladder, or just above its top, with room for the body. Above the top is only a place where there is a floor
    // to step off onto: a ladder one block high set up as a garden fence has none
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

    // The game takes a ladder for a solid block, and the air over the top of one for floor
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

    // Scaffolding is climbed, but only crouching is the way down, and a mob never crouches: its top is a floor
    // Nor is a ladder laid flat on the floor a way up, only something to walk over
    private boolean isClimbable(int x, int y, int z) {
        BlockState state = context.getBlockState(probe.set(x, y, z));
        return state.isIn(BlockTags.CLIMBABLE) && !(state.getBlock() instanceof ScaffoldingBlock)
                && !(state.contains(Properties.BLOCK_FACE) && state.get(Properties.BLOCK_FACE) == BlockFace.FLOOR);
    }

    private boolean isPassable(int x, int y, int z) {
        BlockState state = context.getBlockState(probe.set(x, y, z));
        // A ladder is a thin board with a shape of its own, which is no obstacle to somebody climbing it
        if(isHatch(state) || state.isIn(BlockTags.CLIMBABLE))
            return true;
        return state.getCollisionShape(context.getWorld(), probe).isEmpty();
    }

    // Only what opens is asked about, or every block of every path would be looked up among the marked doors
    private boolean isLockedShut(BlockState state, BlockPos pos) {
        if(!(state.getBlock() instanceof NpcDoor) && !state.contains(Properties.OPEN))
            return false;
        if(NpcDoor.opensFor(entity.getWorld(), pos, state, entity))
            return false;
        if(state.getBlock() instanceof NpcDoor door)
            return !door.isOpen(state);
        return state.contains(Properties.OPEN) && !state.get(Properties.OPEN);
    }

    public static boolean isFarmland(BlockState state) {
        return state.getBlock() instanceof FarmlandBlock || state.isIn(BlockTagsME.FARMLANDS);
    }

    // The gate or trapdoor closing a loft, which the npc opens on its way
    public static boolean isHatch(BlockState state) {
        return state.getBlock() instanceof FenceGateBlock || (state.getBlock() instanceof TrapdoorBlock && state.isIn(BlockTags.WOODEN_TRAPDOORS));
    }
}
