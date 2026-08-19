package net.sevenstars.middleearth.block.special.dirts;

import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;

public class CustomGrassBlock extends GrassBlock {

    private final Block target;

    public CustomGrassBlock(Properties settings, Block target) {
        super(settings);
        this.target = target;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!canBeGrass(state, world, pos)) {
            world.setBlockAndUpdate(pos, this.target.defaultBlockState());
        } else {
            if (world.getMaxLocalRawBrightness(pos.above()) >= 9) {
                BlockState blockState = this.defaultBlockState();

                for(int i = 0; i < 4; ++i) {
                    BlockPos blockPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    if (world.getBlockState(blockPos).is(this.target) && canPropagate(blockState, world, blockPos)) {
                        world.setBlockAndUpdate(blockPos, blockState.setValue(SNOWY, world.getBlockState(blockPos.above()).is(BlockTags.SNOW)));
                    }
                }
            }

        }
    }

    private static boolean canPropagate(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.above();
        return canBeGrass(state, world, pos) && !world.getFluidState(blockPos).is(FluidTags.WATER);
    }

    private static boolean canBeGrass(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.above();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.is(Blocks.SNOW) && (Integer)blockState.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        } else if (blockState.getFluidState().getAmount() == 8) {
            return false;
        } else {
            int i = LightEngine.getLightBlockInto(
                    world, state, pos, blockState, blockPos, Direction.UP, blockState.getLightBlock(world, blockPos)
            );
            return i < 15;
        }
    }
}
