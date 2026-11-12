package net.sevenstars.middleearth.block.special.structureManager.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.enums.BedPart;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
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
            Block block = state.getBlock();
            if(block instanceof BedBlock) {
                if(state.get(BedBlock.PART) == BedPart.HEAD && beds.size() < MAXIMUM_BEDS)
                    beds.add(new Bed(pos.toImmutable(), Optional.ofNullable(standBeside(world, pos, state)), Optional.empty()));
            } else if(block instanceof BarrelBlock || block instanceof ChestBlock || block instanceof CampfireBlock) {
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

    // A bed is no place to walk to. The floor next to it is
    private static @Nullable BlockPos standBeside(ServerWorld world, BlockPos head, BlockState state) {
        BlockPos foot = head.offset(state.get(BedBlock.FACING).getOpposite());
        for(BlockPos half : new BlockPos[]{foot, head}) {
            for(Direction side : Direction.Type.HORIZONTAL) {
                BlockPos beside = half.offset(side);
                if(beside.equals(head) || beside.equals(foot))
                    continue;
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
        return world.getBlockState(pos).getCollisionShape(world, pos).isEmpty()
                && world.getBlockState(pos.up()).getCollisionShape(world, pos.up()).isEmpty()
                && world.getBlockState(below).isSideSolidFullSquare(world, below, Direction.UP);
    }
}
