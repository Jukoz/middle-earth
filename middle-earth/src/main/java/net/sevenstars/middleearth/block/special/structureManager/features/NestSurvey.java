package net.sevenstars.middleearth.block.special.structureManager.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.enums.BedPart;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.registry.Registry;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.util.BlockMirror;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.structurefeatures.BuildFeatures;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.UUID;

// One look through the building of a nest, for its beds and for a place to put things
public class NestSurvey {
    // A piece larger than this is a courtyard or a hall, not one household
    private static final int MAXIMUM_PIECE_WIDTH = 40;
    private static final int MAXIMUM_PIECE_HEIGHT = 32;
    private static final int FALLBACK_RADIUS = 10;
    private static final int FALLBACK_DEPTH = 3;
    private static final int FALLBACK_HEIGHT = 8;
    private static final int MAXIMUM_BEDS = 16;
    // A bed, a slab or a carpet is no wall, though a body cannot stand in it
    private static final double LOW_ENOUGH_TO_PASS = 0.6;
    // A carpet or a thin layer, stood on rather than in the way
    private static final double UNDERFOOT = 0.2;

    public record Bed(BlockPos pos, Optional<BlockPos> stand, Optional<UUID> owner) {
        public static final Codec<Bed> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BlockPos.CODEC.fieldOf("pos").forGetter(Bed::pos),
                BlockPos.CODEC.optionalFieldOf("stand").forGetter(Bed::stand),
                Uuids.CODEC.optionalFieldOf("owner").forGetter(Bed::owner)
        ).apply(instance, Bed::new));

        public Bed withOwner(@Nullable UUID owner) {
            return new Bed(pos, stand, Optional.ofNullable(owner));
        }
    }

    public record Result(List<Bed> beds, Optional<BlockPos> store) {}

    // The beds given to this nest when data was generated. Null for a nest no template knows, a player's own building for one
    public static @Nullable Result fromData(ServerWorld world, BlockPos origin) {
        // Asking for the structure of an unloaded place would load it
        if(!world.isChunkLoaded(origin.getX() >> 4, origin.getZ() >> 4))
            return null;
        Optional<Registry<StructureFeatureData>> registry = world.getRegistryManager().getOptional(DynamicRegistriesME.STRUCTURE_FEATURES);
        StructureStart start = world.getStructureAccessor().getStructureContaining(origin, entry -> true);
        if(registry.isEmpty() || start == null)
            return null;
        for(StructurePiece piece : start.getChildren()) {
            if(!(piece instanceof PoolStructurePiece placed) || !piece.getBoundingBox().contains(origin))
                continue;
            StructureFeatureData data = dataOf(registry.get(), placed);
            if(data == null)
                continue;
            // Pieces overlap, the right one is the one that has a nest exactly here
            for(StructureFeatureData.Nest nest : data.nests()) {
                if(!placedAt(placed, nest.pos()).equals(origin))
                    continue;
                List<Bed> beds = new ArrayList<>();
                for(BlockPos bed : nest.beds())
                    beds.add(new Bed(placedAt(placed, bed), Optional.empty(), Optional.empty()));
                return new Result(beds, nest.store().map(store -> placedAt(placed, store)));
            }
        }
        return null;
    }

    public static @Nullable StructureFeatureData dataOf(Registry<StructureFeatureData> registry, PoolStructurePiece piece) {
        if(!(piece.getPoolElement() instanceof SinglePoolElement element) || element.location.left().isEmpty())
            return null;
        return registry.get(element.location.left().get());
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

    // Null while the building is not loaded
    public static @Nullable Result survey(ServerWorld world, BlockPos origin) {
        if(!world.isChunkLoaded(origin.getX() >> 4, origin.getZ() >> 4))
            return null;
        BlockBox box = buildingOf(world, origin);
        List<Bed> beds = new ArrayList<>();
        BlockPos store = null;
        for(BlockPos pos : BlockPos.iterate(box.getMinX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ())) {
            if(!world.isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4))
                return null;
            BlockState state = world.getBlockState(pos);
            if(state.isIn(BuildFeatures.BED.getTag())) {
                if(state.get(BedBlock.PART) == BedPart.HEAD && beds.size() < MAXIMUM_BEDS)
                    beds.add(new Bed(pos.toImmutable(), Optional.ofNullable(standBeside(world, pos, state)), Optional.empty()));
            } else if(state.isIn(BuildFeatures.STORE.getTag())) {
                if(store == null || pos.getSquaredDistance(origin) < store.getSquaredDistance(origin))
                    store = pos.toImmutable();
            }
        }
        // Nearest first, so that the household fills the beds around its own nest
        beds.sort((a, b) -> Double.compare(a.pos().getSquaredDistance(origin), b.pos().getSquaredDistance(origin)));
        return new Result(beds, Optional.ofNullable(store));
    }

    private static BlockBox buildingOf(ServerWorld world, BlockPos origin) {
        StructureStart start = world.getStructureAccessor().getStructureContaining(origin, entry -> true);
        BlockBox best = null;
        if(start != null && start.hasChildren()) {
            for(StructurePiece piece : start.getChildren()) {
                BlockBox box = piece.getBoundingBox();
                boolean household = box.getBlockCountX() <= MAXIMUM_PIECE_WIDTH && box.getBlockCountZ() <= MAXIMUM_PIECE_WIDTH && box.getBlockCountY() <= MAXIMUM_PIECE_HEIGHT;
                if(household && box.contains(origin) && (best == null || volume(box) < volume(best)))
                    best = box;
            }
        }
        if(best != null)
            return best;
        return new BlockBox(origin.getX() - FALLBACK_RADIUS, origin.getY() - FALLBACK_DEPTH, origin.getZ() - FALLBACK_RADIUS,
                origin.getX() + FALLBACK_RADIUS, origin.getY() + FALLBACK_HEIGHT, origin.getZ() + FALLBACK_RADIUS);
    }

    private static long volume(BlockBox box) {
        return (long) box.getBlockCountX() * box.getBlockCountY() * box.getBlockCountZ();
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

    // A bed is no place to walk to. The floor next to it is
    private static @Nullable BlockPos standBeside(ServerWorld world, BlockPos head, BlockState state) {
        BlockPos foot = head.offset(state.get(BedBlock.FACING).getOpposite());
        for(BlockPos half : new BlockPos[]{foot, head}) {
            for(Direction side : Direction.Type.HORIZONTAL) {
                BlockPos beside = half.offset(side);
                if(beside.equals(head) || beside.equals(foot))
                    continue;
                // The inner bed of a pair is got into across the other one
                if(world.getBlockState(beside).getBlock() instanceof BedBlock)
                    beside = beside.offset(side);
                for(BlockPos spot : new BlockPos[]{beside, beside.down()}) {
                    if(isStandable(world, spot))
                        return spot;
                }
            }
        }
        return null;
    }

    private static boolean isStandable(ServerWorld world, BlockPos pos) {
        if(!world.isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4))
            return false;
        BlockPos below = pos.down();
        return isRoomFor(world, pos, true) && isRoomFor(world, pos.up(), false)
                && world.getBlockState(below).isSideSolidFullSquare(world, below, Direction.UP);
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
            if(spot.equals(pos) || !open.contains(spot.up()))
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
