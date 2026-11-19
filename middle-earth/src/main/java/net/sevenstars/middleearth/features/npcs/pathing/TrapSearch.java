package net.sevenstars.middleearth.features.npcs.pathing;

import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayFIFOQueue;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.Heightmap;
import net.sevenstars.middleearth.block.special.NpcDoor;

// Finds pits and cellars a step down would leave no way back out of
final class TrapSearch {
    // Canopy is ~200 positions, a cave beside a hall 300 to 1000+
    private static final int SEARCH = 256;
    private static final int SEARCH_COVERED = 1500;
    private static final int ROOF_HEIGHT = 2;
    private static final int MAXIMUM_DROP = 3;
    private static final int BESIDE_DISTANCE = 1;
    private static final byte TRAP = 1;
    private static final byte OPEN = 2;

    private final PathTerrain terrain;
    private final LadderRules ladders;
    // By landing, then by the node stepped from, so two pairs never share a key
    private final Long2ObjectOpenHashMap<Long2ByteOpenHashMap> known = new Long2ObjectOpenHashMap<>();
    private final LongOpenHashSet seen = new LongOpenHashSet();
    private final LongArrayFIFOQueue open = new LongArrayFIFOQueue();

    TrapSearch(PathTerrain terrain, LadderRules ladders) {
        this.terrain = terrain;
        this.ladders = ladders;
    }

    void clear() {
        known.clear();
    }

    // Don't step down into a pit or cellar with no way back up
    boolean isTrapStep(PathNode from, PathNode next) {
        return next.y < from.y && isTrap(next.x, next.y, next.z, from);
    }

    private boolean isTrap(int startX, int startY, int startZ, PathNode from) {
        // Every node around a landing asks, so cache
        long landing = BlockPos.asLong(startX, startY, startZ);
        Long2ByteOpenHashMap fromLanding = known.get(landing);
        if(fromLanding == null) {
            fromLanding = new Long2ByteOpenHashMap();
            known.put(landing, fromLanding);
        }
        long origin = BlockPos.asLong(from.x, from.y, from.z);
        byte result = fromLanding.get(origin);
        if(result != 0)
            return result == TRAP;
        boolean trap = search(startX, startY, startZ, from);
        fromLanding.put(origin, trap ? TRAP : OPEN);
        return trap;
    }

    // Same height elsewhere doesn't count, e.g. a cave rising at its far end
    private boolean search(int startX, int startY, int startZ, PathNode from) {
        seen.clear();
        open.clear();
        visit(startX, startY, startZ);
        boolean covered = isUnderRoof(startX, startY, startZ);
        int limit = covered ? SEARCH_COVERED : SEARCH;
        while(!open.isEmpty()) {
            if(seen.size() > limit)
                return covered;
            long cell = open.dequeueLong();
            int x = BlockPos.unpackLongX(cell);
            int y = BlockPos.unpackLongY(cell);
            int z = BlockPos.unpackLongZ(cell);
            if(y >= from.y && Math.abs(x - from.x) <= BESIDE_DISTANCE && Math.abs(z - from.z) <= BESIDE_DISTANCE)
                return false;
            if(isWater(x, y + 1, z) || ladders.isLadderCell(x, y + 1, z))
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
        MobEntity entity = terrain.entity();
        return entity != null && entity.getEntityWorld().getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z) > y + ROOF_HEIGHT;
    }

    private void visit(int x, int y, int z) {
        long cell = BlockPos.asLong(x, y, z);
        if(seen.add(cell))
            open.enqueue(cell);
    }

    private boolean isWalkedThrough(int x, int y, int z) {
        BlockState state = terrain.stateAt(x, y, z);
        if(terrain.isLockedShut(state, x, y, z))
            return false;
        if(NpcPathNodeMaker.isHatch(state) || BoardRules.isOpenBoard(state) || state.isIn(BlockTags.CLIMBABLE) || state.isIn(BlockTags.WOODEN_DOORS))
            return true;
        if(state.getBlock() instanceof NpcDoor)
            return true;
        VoxelShape shape = terrain.shapeAt(x, y, z);
        return shape.isEmpty() || shape.getMax(Direction.Axis.Y) <= PathTerrain.LOW_STEP;
    }

    private boolean isWater(int x, int y, int z) {
        return terrain.stateAt(x, y, z).getFluidState().isIn(FluidTags.WATER);
    }
}
