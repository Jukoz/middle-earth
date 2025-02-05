package net.sevenstars.middleearth.block.special.beds;

import net.sevenstars.middleearth.block.ModBlockEntities;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
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