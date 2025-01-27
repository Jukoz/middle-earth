package net.sevenstars.middleearth.block.special;

import net.minecraft.block.BlockState;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class TallMushroomBlock extends TallPlantBlock {
    public TallMushroomBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            BlockState blockState = world.getBlockState(pos.down());
            return blockState.isOf(this) && blockState.get(HALF) == DoubleBlockHalf.LOWER;
        }
        if (state.isIn(BlockTags.MUSHROOM_GROW_BLOCK)) {
            return true;
        }
        BlockPos floor = pos.down();
        boolean dark = world.getBaseLightLevel(floor, 0) < 13;
        boolean opaque = world.getBlockState(floor).isOpaqueFullCube(world, floor);
        return dark & opaque;
    }
}
