package net.sevenstars.middleearth.block.special.plants;

import net.sevenstars.middleearth.block.registration.StoneBlockSetRegistryME;
import net.minecraft.block.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.sevenstars.middleearth.utils.BlockTagsME;

public class MordorPricklyPlantBlock extends PricklyPlantBlock {

    public MordorPricklyPlantBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return  floor.isIn(BlockTags.DIRT) || floor.isIn(BlockTags.SAND) || floor.isOf(StoneBlockSetRegistryME.ASHENSTONE_SET.baseBlocks.base())
                || floor.isOf(Blocks.BASALT) || floor.isIn(BlockTagsME.FARMLANDS);
    }
}