package net.jukoz.me.block.special.beds;

import net.jukoz.me.block.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

public class CustomBedBlock extends BedBlock {

    public CustomBedBlock(DyeColor color, Settings settings) {
        super(color, settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CustomBedBlockEntity(ModBlockEntities.BED, pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}