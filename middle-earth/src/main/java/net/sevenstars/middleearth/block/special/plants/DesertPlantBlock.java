package net.sevenstars.middleearth.block.special.plants;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.utils.BlockTagsME;

public class DesertPlantBlock extends CustomPlantBlock {
    public static final MapCodec<DesertPlantBlock> CODEC = DesertPlantBlock.simpleCodec(DesertPlantBlock::new);

    public DesertPlantBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.is(BlockTags.DIRT) || floor.is(BlockTags.SAND) || floor.is(BlockTagsME.FARMLANDS);
    }
}