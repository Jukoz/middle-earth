package net.sevenstars.middleearth.block.special.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class TallMushroomBlock extends DoublePlantBlock {
    public TallMushroomBlock(Properties settings) {
        super(settings);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            BlockState blockState = world.getBlockState(pos.below());
            return blockState.is(this) && blockState.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
        if (state.is(BlockTags.MUSHROOM_GROW_BLOCK)) {
            return true;
        }
        BlockPos floor = pos.below();
        boolean dark = world.getRawBrightness(floor, 0) < 13;
        boolean opaque = world.getBlockState(floor).isSolidRender(world, floor);
        return dark & opaque;
    }
}
