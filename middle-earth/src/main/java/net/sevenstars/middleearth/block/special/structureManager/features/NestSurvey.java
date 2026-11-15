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

// Where an npc can stand and walk about the blocks of a building: the floor beside a bed or a chest, and the ground around
public class NestSurvey {
    // A bed, a slab or a carpet is no wall, though a body cannot stand in it
    private static final double LOW_ENOUGH_TO_PASS = 0.6;
    // A carpet or a thin layer, stood on rather than in the way
    private static final double UNDERFOOT = 0.2;
    private static final double EYE_HEIGHT = 1.6;

    // From the generated data, or read from the template itself where there is none yet or a builder changed it since
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

    // Null when the bed is gone, or its chunk is not loaded
    public static @Nullable BlockPos standFor(ServerWorld world, BlockPos bed) {
        if(!world.isChunkLoaded(bed.getX() >> 4, bed.getZ() >> 4))
            return null;
        BlockState state = world.getBlockState(bed);
        if(!(state.getBlock() instanceof BedBlock) || state.get(BedBlock.PART) != BedPart.HEAD)
            return null;
        return standBeside(world, bed, state);
    }

    // The floor next to a barrel or a chest, on the side closest to the given place. Null when it is walled in
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

    // The nearest floor a few blocks off from which the block can be seen, for a block with none beside it
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

    // A bed is no place to walk to. The floor next to it is
    private static @Nullable BlockPos standBeside(ServerWorld world, BlockPos head, BlockState state) {
        BlockPos foot = head.offset(state.get(BedBlock.FACING).getOpposite());
        BlockPos behindBoard = null;
        for(BlockPos half : new BlockPos[]{foot, head}) {
            for(Direction side : Direction.Type.HORIZONTAL) {
                BlockPos beside = half.offset(side);
                if(beside.equals(head) || beside.equals(foot))
                    continue;
                // The inner bed of a pair is got into across the other one
                if(world.getBlockState(beside).getBlock() instanceof BedBlock)
                    beside = beside.offset(side);
                for(BlockPos spot : new BlockPos[]{beside, beside.down()}) {
                    if(!isStandable(world, spot))
                        continue;
                    // An open trapdoor between the floor and the bed is its footboard, and the floor behind one is often shut in
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
        // An upper bunk has its floor two blocks down, a bed let into the ground has it a block up
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

    // An open trapdoor in the block with its board on the given side
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

    // A stair the right way up is walked on like any floor: the only way to a bed let into the ground can be its step
    private static boolean isFloor(ServerWorld world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.isSideSolidFullSquare(world, pos, Direction.UP)
                || state.getBlock() instanceof StairsBlock && state.get(StairsBlock.HALF) == BlockHalf.BOTTOM;
    }

    // Nothing in the way of a body: the block is empty, or holds an open trapdoor's board against one side. A carpet lies
    // under the feet
    private static boolean isRoomFor(ServerWorld world, BlockPos pos, boolean feet) {
        BlockState state = world.getBlockState(pos);
        if(state.getBlock() instanceof TrapdoorBlock && state.get(TrapdoorBlock.OPEN))
            return true;
        VoxelShape shape = state.getCollisionShape(world, pos);
        return shape.isEmpty() || feet && shape.getMax(Direction.Axis.Y) <= UNDERFOOT;
    }

    // The nearest spot around with a full block to stand on, nothing in the way of a body and no water: somewhere a path
    // can start from again. Only a spot open space leads to from where it stands, never one through a wall or a shut door
    public static @Nullable BlockPos freeFloorNear(ServerWorld world, BlockPos pos, int radius, int depth) {
        Set<BlockPos> open = openAround(world, pos, radius, depth);
        BlockPos best = null;
        for(BlockPos spot : open) {
            // Not down a hole, and not onto a ledge with no floor beside it to walk on from
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

    // The blocks around that can be got to from the two a body stands in without going through anything taller than a bed
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
