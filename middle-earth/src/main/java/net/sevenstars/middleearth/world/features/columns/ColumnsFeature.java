package net.sevenstars.middleearth.world.features.columns;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class ColumnsFeature extends Feature<ColumnsFeatureConfig> {
    private static final ImmutableList<Block> CANNOT_REPLACE_BLOCKS;

    public ColumnsFeature(Codec<ColumnsFeatureConfig> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<ColumnsFeatureConfig> context) {
        int i = context.chunkGenerator().getSeaLevel();
        BlockPos blockPos = context.origin();
        WorldGenLevel structureWorldAccess = context.level();
        RandomSource random = context.random();
        ColumnsFeatureConfig ColumnsFeatureConfig = (ColumnsFeatureConfig)context.config();
        if (!canPlaceAt(structureWorldAccess, i, blockPos.mutable())) {
            return false;
        } else {
            int j = ColumnsFeatureConfig.getHeight().sample(random);
            boolean bl = random.nextFloat() < 0.9F;
            int k = Math.min(j, bl ? 5 : 8);
            int l = bl ? 50 : 15;
            boolean bl2 = false;
            Iterator var12 = BlockPos.randomBetweenClosed(random, l, blockPos.getX() - k, blockPos.getY(), blockPos.getZ() - k, blockPos.getX() + k, blockPos.getY(), blockPos.getZ() + k).iterator();

            while(var12.hasNext()) {
                BlockPos blockPos2 = (BlockPos)var12.next();
                int m = j - blockPos2.distManhattan(blockPos);
                if (m >= 0) {
                    bl2 |= this.placeColumn(structureWorldAccess, i, blockPos2, m, ColumnsFeatureConfig.getReach().sample(random), context.config().getBlockState());
                }
            }

            return bl2;
        }
    }

    private boolean placeColumn(LevelAccessor world, int seaLevel, BlockPos pos, int height, int reach, BlockState blockState) {
        boolean bl = false;
        Iterator var7 = BlockPos.betweenClosed(pos.getX() - reach, pos.getY(), pos.getZ() - reach, pos.getX() + reach, pos.getY(), pos.getZ() + reach).iterator();

        while(true) {
            int i;
            BlockPos blockPos2;
            do {
                if (!var7.hasNext()) {
                    return bl;
                }

                BlockPos blockPos = (BlockPos)var7.next();
                i = blockPos.distManhattan(pos);
                blockPos2 = isAirOrLavaOcean(world, seaLevel, blockPos) ? moveDownToGround(world, seaLevel, blockPos.mutable(), i) : moveUpToAir(world, blockPos.mutable(), i);
            } while(blockPos2 == null);

            int j = height - i / 2;

            for(BlockPos.MutableBlockPos mutable = blockPos2.mutable(); j >= 0; --j) {
                if (isAirOrLavaOcean(world, seaLevel, mutable)) {
                    this.setBlock(world, mutable, blockState);
                    mutable.move(Direction.UP);
                    bl = true;
                } else {
                    if (!world.getBlockState(mutable).is(blockState.getBlock())) {
                        break;
                    }

                    mutable.move(Direction.UP);
                }
            }
        }
    }

    @Nullable
    private static BlockPos moveDownToGround(LevelAccessor world, int seaLevel, BlockPos.MutableBlockPos mutablePos, int distance) {
        while(mutablePos.getY() > world.getMinBuildHeight() + 1 && distance > 0) {
            --distance;
            if (canPlaceAt(world, seaLevel, mutablePos)) {
                return mutablePos;
            }

            mutablePos.move(Direction.DOWN);
        }

        return null;
    }

    private static boolean canPlaceAt(LevelAccessor world, int seaLevel, BlockPos.MutableBlockPos mutablePos) {
        if (!isAirOrLavaOcean(world, seaLevel, mutablePos)) {
            return false;
        } else {
            BlockState blockState = world.getBlockState(mutablePos.move(Direction.DOWN));
            mutablePos.move(Direction.UP);
            return !blockState.isAir() && !CANNOT_REPLACE_BLOCKS.contains(blockState.getBlock());
        }
    }

    @Nullable
    private static BlockPos moveUpToAir(LevelAccessor world, BlockPos.MutableBlockPos mutablePos, int distance) {
        while(mutablePos.getY() < world.getMaxBuildHeight() && distance > 0) {
            --distance;
            BlockState blockState = world.getBlockState(mutablePos);
            if (CANNOT_REPLACE_BLOCKS.contains(blockState.getBlock())) {
                return null;
            }

            if (blockState.isAir()) {
                return mutablePos;
            }

            mutablePos.move(Direction.UP);
        }

        return null;
    }

    private static boolean isAirOrLavaOcean(LevelAccessor world, int seaLevel, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.isAir() || blockState.is(Blocks.LAVA) && pos.getY() <= seaLevel;
    }

    static {
        CANNOT_REPLACE_BLOCKS = ImmutableList.of(Blocks.LAVA, Blocks.BEDROCK, Blocks.MAGMA_BLOCK, Blocks.SOUL_SAND,
                Blocks.CHEST, Blocks.SPAWNER);
    }
}
