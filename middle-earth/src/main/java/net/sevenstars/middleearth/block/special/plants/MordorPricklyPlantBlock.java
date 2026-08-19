package net.sevenstars.middleearth.block.special.plants;

import com.mojang.serialization.MapCodec;
import net.sevenstars.middleearth.block.registration.StoneBlockSets;
import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.utils.BlockTagsME;

public class MordorPricklyPlantBlock extends PricklyPlantBlock {

    public MordorPricklyPlantBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return  floor.is(BlockTags.DIRT) || floor.is(BlockTags.SAND) || floor.is(StoneBlockSets.ASHENSTONE_SET.baseBlocks.base())
                || floor.is(Blocks.BASALT) || floor.is(BlockTagsME.FARMLANDS);
    }
}