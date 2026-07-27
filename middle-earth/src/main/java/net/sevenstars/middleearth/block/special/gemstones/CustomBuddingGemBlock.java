package net.sevenstars.middleearth.block.special.gemstones;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class CustomBuddingGemBlock extends BuddingAmethystBlock {

    private static final Direction[] DIRECTIONS = Direction.values();

    private final List<Block> clusters;

    public CustomBuddingGemBlock(Properties settings, List<Block> clustersIn) {
        super(settings);
        clusters = clustersIn;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextInt(5) == 0) {
            Direction direction = UPDATE_SHAPE_ORDER[random.nextInt(UPDATE_SHAPE_ORDER.length)];
            BlockPos blockPos = pos.relative(direction);
            BlockState blockState = world.getBlockState(blockPos);
            Block block = null;
            if (canClusterGrowAtState(blockState)) {
                block = clusters.get(0);
            } else if (blockState.is(clusters.get(0)) && blockState.getValue(AmethystClusterBlock.FACING) == direction) {
                block = clusters.get(1);
            } else if (blockState.is(clusters.get(1)) && blockState.getValue(AmethystClusterBlock.FACING) == direction) {
                block = clusters.get(2);
            } else if (blockState.is(clusters.get(2)) && blockState.getValue(AmethystClusterBlock.FACING) == direction) {
                block = clusters.get(3);
            }



            if (block != null) {
                BlockState blockState2 = (BlockState)((BlockState)block.defaultBlockState().setValue(AmethystClusterBlock.FACING, direction)).setValue(AmethystClusterBlock.WATERLOGGED, blockState.getFluidState().getType() == Fluids.WATER);
                world.setBlockAndUpdate(blockPos, blockState2);
            }

        }
    }
}
