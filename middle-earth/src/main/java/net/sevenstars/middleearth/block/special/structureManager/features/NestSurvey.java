package net.sevenstars.middleearth.block.special.structureManager.features;

import net.sevenstars.middleearth.registries.content.structurefeatures.StructureFeaturesRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.block.enums.BedPart;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.registry.Registry;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.util.BlockMirror;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Finds where an npc can stand and walk in a building: the floor beside a bed or a chest, and the ground around
public class NestSurvey {
    // A bed, a slab or a carpet isn't a wall, even though an npc can't stand in it
    private static final double LOW_ENOUGH_TO_PASS = 0.6;
    // A carpet or other thin layer is stood on, not an obstacle
    private static final double UNDERFOOT = 0.2;
    private static final double EYE_HEIGHT = 1.6;

    // Uses the generated data, or reads the template itself if there is no generated data yet or a builder has edited it since
    public static @Nullable StructureFeatureData dataOf(ServerWorld world, Registry<StructureFeatureData> registry, PoolStructurePiece piece) {
        if(!(piece.getPoolElement() instanceof SinglePoolElement element) || element.location.left().isEmpty())
            return null;
        Identifier template = element.location.left().get();
        StructureFeatureData generated = registry.get(template);
        if(generated != null && !StructureFeaturesRegistry.isEdited(template))
            return generated;
        StructureFeatureData live = StructureFeaturesRegistry.live(world.getStructureTemplateManager(), template);
        return live != null ? live : generated;
    }

    public static BlockPos placedAt(PoolStructurePiece piece, BlockPos inTemplate) {
        return StructureTemplate.transformAround(inTemplate, BlockMirror.NONE, piece.getRotation(), BlockPos.ORIGIN).add(piece.getPos());
    }

    // Null when the bed is gone or its chunk isn't loaded
    public static @Nullable BlockPos standFor(ServerWorld world, BlockPos bed) {
        if(!world.isChunkLoaded(bed.getX() >> 4, bed.getZ() >> 4))
            return null;
        BlockState state = world.getBlockState(bed);
        if(!(state.getBlock() instanceof BedBlock) || state.get(BedBlock.PART) != BedPart.HEAD)
            return null;
        return standBeside(world, bed, state);
    }

    // The floor next to a barrel or chest, on the side closest to the given position. Null when it's walled in
    public static @Nullable BlockPos standNextTo(ServerWorld world, BlockPos block, BlockPos closeTo) {
        BlockPos best = null;
        for(Direction side : Direction.Type.HORIZONTAL) {
            BlockPos beside = block.offset(side);
            for(BlockPos spot : new BlockPos[]{beside, beside.down()}) {
                if(isStandable(world, spot) && (best == null || spot.getSquaredDistance(closeTo) < best.getSquaredDistance(closeTo))) {
                    best = spot.toImmutable();
                    break;
                }
            }
        }
        return best;
    }

    // For a block with no floor beside it, the nearest floor within a few blocks that has a line of sight to the block
    public static @Nullable BlockPos standInSight(ServerWorld world, BlockPos block, BlockPos closeTo, int radius) {
        BlockPos best = null;
        Vec3d middle = Vec3d.ofCenter(block);
        for(BlockPos spot : BlockPos.iterate(block.add(-radius, -1, -radius), block.add(radius, 1, radius))) {
            if(spot.getX() == block.getX() && spot.getZ() == block.getZ() || !isStandable(world, spot))
                continue;
            Vec3d eye = new Vec3d(spot.getX() + 0.5, spot.getY() + EYE_HEIGHT, spot.getZ() + 0.5);
            BlockHitResult hit = world.raycast(new RaycastContext(eye, middle, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, ShapeContext.absent()));
            if(hit.getType() != HitResult.Type.MISS && !hit.getBlockPos().equals(block))
                continue;
            if(best == null || spot.getSquaredDistance(closeTo) < best.getSquaredDistance(closeTo))
                best = spot.toImmutable();
        }
        return best;
    }

    // An npc can't walk onto a bed, so this finds the floor next to it
    private static @Nullable BlockPos standBeside(ServerWorld world, BlockPos head, BlockState state) {
        BlockPos foot = head.offset(state.get(BedBlock.FACING).getOpposite());
        BlockPos behindBoard = null;
        for(BlockPos half : new BlockPos[]{foot, head}) {
            for(Direction side : Direction.Type.HORIZONTAL) {
                BlockPos beside = half.offset(side);
                if(beside.equals(head) || beside.equals(foot))
                    continue;
                // The inner bed of a pair is reached across the other one
                if(world.getBlockState(beside).getBlock() instanceof BedBlock)
                    beside = beside.offset(side);
                for(BlockPos spot : new BlockPos[]{beside, beside.down()}) {
                    if(!isStandable(world, spot))
                        continue;
                    // An open trapdoor between the floor and the bed acts as a footboard, and the floor behind one is often walled in
                    if(!isBoardTowards(world, spot, side.getOpposite()))
                        return spot;
                    if(behindBoard == null)
                        behindBoard = spot;
                    break;
                }
            }
        }
        if(behindBoard != null)
            return behindBoard;
        // An upper bunk has its floor two blocks down, and a bed sunk into the ground has it one block up
        for(BlockPos half : new BlockPos[]{foot, head}) {
            for(Direction side : Direction.Type.HORIZONTAL) {
                BlockPos beside = half.offset(side);
                if(beside.equals(head) || beside.equals(foot))
                    continue;
                if(isStandable(world, beside.down(2)))
                    return beside.down(2);
                if(isStandable(world, beside.up()))
                    return beside.up();
            }
        }
        return null;
    }

    // True for an open trapdoor in this block whose panel is on the given side
    private static boolean isBoardTowards(ServerWorld world, BlockPos pos, Direction side) {
        BlockState state = world.getBlockState(pos);
        if(!(state.getBlock() instanceof TrapdoorBlock) || !state.get(TrapdoorBlock.OPEN))
            return false;
        VoxelShape shape = state.getCollisionShape(world, pos);
        if(shape.isEmpty())
            return false;
        Direction.Axis axis = side.getAxis();
        return side.getDirection() == Direction.AxisDirection.POSITIVE ? shape.getMin(axis) >= 0.5 : shape.getMax(axis) <= 0.5;
    }

    private static boolean isStandable(ServerWorld world, BlockPos pos) {
        if(!world.isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4))
            return false;
        return isRoomFor(world, pos, true) && isRoomFor(world, pos.up(), false) && isFloor(world, pos.down());
    }

    // A right-way-up stair counts as floor, since the only way to a bed sunk into the ground can be its step
    private static boolean isFloor(ServerWorld world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.isSideSolidFullSquare(world, pos, Direction.UP)
                || state.getBlock() instanceof StairsBlock && state.get(StairsBlock.HALF) == BlockHalf.BOTTOM;
    }

    // Nothing blocks an npc's body here: the block is empty, or holds an open trapdoor's panel against one side. A
    // carpet counts as underfoot
    private static boolean isRoomFor(ServerWorld world, BlockPos pos, boolean feet) {
        BlockState state = world.getBlockState(pos);
        if(state.getBlock() instanceof TrapdoorBlock && state.get(TrapdoorBlock.OPEN))
            return true;
        VoxelShape shape = state.getCollisionShape(world, pos);
        return shape.isEmpty() || feet && shape.getMax(Direction.Axis.Y) <= UNDERFOOT;
    }

    // The nearest spot with a full block to stand on, nothing in the way of a body and no water, so a path can start
    // from there again. Only spots reachable through open space from the given position count, never one through a
    // wall or a shut door
    public static @Nullable BlockPos freeFloorNear(ServerWorld world, BlockPos pos, int radius, int depth) {
        Set<BlockPos> open = openAround(world, pos, radius, depth);
        BlockPos best = null;
        for(BlockPos spot : open) {
            // Skip holes, and ledges with no floor beside them to walk on from
            if(spot.equals(pos) || !open.contains(spot.up()) || spot.getY() < pos.getY() - 1 || !hasFloorBeside(world, spot))
                continue;
            BlockPos below = spot.down();
            boolean standable = world.getBlockState(spot).getCollisionShape(world, spot).isEmpty()
                    && world.getBlockState(spot.up()).getCollisionShape(world, spot.up()).isEmpty()
                    && world.getFluidState(spot).isEmpty()
                    && world.getBlockState(below).isSideSolidFullSquare(world, below, Direction.UP);
            if(standable && (best == null || spot.getSquaredDistance(pos) < best.getSquaredDistance(pos)))
                best = spot;
        }
        return best;
    }

    private static boolean hasFloorBeside(ServerWorld world, BlockPos spot) {
        for(Direction side : Direction.Type.HORIZONTAL) {
            if(isStandable(world, spot.offset(side)))
                return true;
        }
        return false;
    }

    // The nearby blocks reachable from the two blocks a body occupies, without passing through anything taller than a bed
    private static Set<BlockPos> openAround(ServerWorld world, BlockPos pos, int radius, int depth) {
        Set<BlockPos> seen = new HashSet<>(List.of(pos, pos.up()));
        ArrayDeque<BlockPos> queue = new ArrayDeque<>(seen);
        while(!queue.isEmpty()) {
            BlockPos cell = queue.poll();
            for(Direction side : Direction.values()) {
                BlockPos next = cell.offset(side);
                if(Math.abs(next.getX() - pos.getX()) > radius || Math.abs(next.getZ() - pos.getZ()) > radius
                        || next.getY() < pos.getY() - depth || next.getY() > pos.getY() + 2 || seen.contains(next)
                        || !world.isChunkLoaded(next.getX() >> 4, next.getZ() >> 4))
                    continue;
                VoxelShape shape = world.getBlockState(next).getCollisionShape(world, next);
                if(!shape.isEmpty() && shape.getMax(Direction.Axis.Y) > LOW_ENOUGH_TO_PASS)
                    continue;
                seen.add(next);
                queue.add(next);
            }
        }
        return seen;
    }
}
