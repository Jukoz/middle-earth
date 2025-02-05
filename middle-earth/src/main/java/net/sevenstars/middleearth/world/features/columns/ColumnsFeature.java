package net.sevenstars.middleearth.world.features.columns;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class ColumnsFeature extends Feature<ColumnsFeatureConfig> {
    private static final ImmutableList<Block> CANNOT_REPLACE_BLOCKS;

    public ColumnsFeature(Codec<ColumnsFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<ColumnsFeatureConfig> context) {
        int i = context.getGenerator().getSeaLevel();
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();
        ColumnsFeatureConfig ColumnsFeatureConfig = (ColumnsFeatureConfig)context.getConfig();
        if (!canPlaceAt(structureWorldAccess, i, blockPos.mutableCopy())) {
            return false;
        } else {
            int j = ColumnsFeatureConfig.getHeight().get(random);
            boolean bl = random.nextFloat() < 0.9F;
            int k = Math.min(j, bl ? 5 : 8);
            int l = bl ? 50 : 15;
            boolean bl2 = false;
            Iterator var12 = BlockPos.iterateRandomly(random, l, blockPos.getX() - k, blockPos.getY(), blockPos.getZ() - k, blockPos.getX() + k, blockPos.getY(), blockPos.getZ() + k).iterator();

            while(var12.hasNext()) {
                BlockPos blockPos2 = (BlockPos)var12.next();
                int m = j - blockPos2.getManhattanDistance(blockPos);
                if (m >= 0) {
                    bl2 |= this.placeColumn(structureWorldAccess, i, blockPos2, m, ColumnsFeatureConfig.getReach().get(random), context.getConfig().getBlockState());
                }
            }

            return bl2;
        }
    }

    private boolean placeColumn(WorldAccess world, int seaLevel, BlockPos pos, int height, int reach, BlockState blockState) {
        boolean bl = false;
        Iterator var7 = BlockPos.iterate(pos.getX() - reach, pos.getY(), pos.getZ() - reach, pos.getX() + reach, pos.getY(), pos.getZ() + reach).iterator();

        while(true) {
            int i;
            BlockPos blockPos2;
            do {
                if (!var7.hasNext()) {
                    return bl;
                }

                BlockPos blockPos = (BlockPos)var7.next();
                i = blockPos.getManhattanDistance(pos);
                blockPos2 = isAirOrLavaOcean(world, seaLevel, blockPos) ? moveDownToGround(world, seaLevel, blockPos.mutableCopy(), i) : moveUpToAir(world, blockPos.mutableCopy(), i);
            } while(blockPos2 == null);

            int j = height - i / 2;

            for(BlockPos.Mutable mutable = blockPos2.mutableCopy(); j >= 0; --j) {
                if (isAirOrLavaOcean(world, seaLevel, mutable)) {
                    this.setBlockState(world, mutable, blockState);
                    mutable.move(Direction.UP);
                    bl = true;
                } else {
                    if (!world.getBlockState(mutable).isOf(blockState.getBlock())) {
                        break;
                    }

                    mutable.move(Direction.UP);
                }
            }
        }
    }

    @Nullable
    private static BlockPos moveDownToGround(WorldAccess world, int seaLevel, BlockPos.Mutable mutablePos, int distance) {
        while(mutablePos.getY() > world.getBottomY() + 1 && distance > 0) {
            --distance;
            if (canPlaceAt(world, seaLevel, mutablePos)) {
                return mutablePos;
            }

            mutablePos.move(Direction.DOWN);
        }

        return null;
    }

    private static boolean canPlaceAt(WorldAccess world, int seaLevel, BlockPos.Mutable mutablePos) {
        if (!isAirOrLavaOcean(world, seaLevel, mutablePos)) {
            return false;
        } else {
            BlockState blockState = world.getBlockState(mutablePos.move(Direction.DOWN));
            mutablePos.move(Direction.UP);
            return !blockState.isAir() && !CANNOT_REPLACE_BLOCKS.contains(blockState.getBlock());
        }
    }

    @Nullable
    private static BlockPos moveUpToAir(WorldAccess world, BlockPos.Mutable mutablePos, int distance) {
        while(mutablePos.getY() < world.getTopY() && distance > 0) {
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

    private static boolean isAirOrLavaOcean(WorldAccess world, int seaLevel, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.isAir() || blockState.isOf(Blocks.LAVA) && pos.getY() <= seaLevel;
    }

    static {
        CANNOT_REPLACE_BLOCKS = ImmutableList.of(Blocks.LAVA, Blocks.BEDROCK, Blocks.MAGMA_BLOCK, Blocks.SOUL_SAND,
                Blocks.CHEST, Blocks.SPAWNER);
    }
}
