package net.jukoz.me.block.special.gemstones;

import net.jukoz.me.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

public class RedAgateBuddingGemBlock extends BuddingAmethystBlock {

    private static final Direction[] DIRECTIONS = Direction.values();

    public RedAgateBuddingGemBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(5) == 0) {
            Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
            BlockPos blockPos = pos.offset(direction);
            BlockState blockState = world.getBlockState(blockPos);
            Block block = null;
            if (canGrowIn(blockState)) {
                block = ModBlocks.SMALL_RED_AGATE_BUD;
            } else if (blockState.isOf(ModBlocks.SMALL_RED_AGATE_BUD) && blockState.get(AmethystClusterBlock.FACING) == direction) {
                block = ModBlocks.MEDIUM_RED_AGATE_BUD;
            } else if (blockState.isOf(ModBlocks.MEDIUM_RED_AGATE_BUD) && blockState.get(AmethystClusterBlock.FACING) == direction) {
                block = ModBlocks.LARGE_RED_AGATE_BUD;
            } else if (blockState.isOf(ModBlocks.LARGE_RED_AGATE_BUD) && blockState.get(AmethystClusterBlock.FACING) == direction) {
                block = ModBlocks.RED_AGATE_CLUSTER;
            }

            if (block != null) {
                BlockState blockState2 = (BlockState)((BlockState)block.getDefaultState().with(AmethystClusterBlock.FACING, direction)).with(AmethystClusterBlock.WATERLOGGED, blockState.getFluidState().getFluid() == Fluids.WATER);
                world.setBlockState(blockPos, blockState2);
            }

        }
    }
}
