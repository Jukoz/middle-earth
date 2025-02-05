package net.sevenstars.middleearth.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class DesertPlantBlock extends CustomPlantBlock {
    public static final MapCodec<DesertPlantBlock> CODEC = DesertPlantBlock.createCodec(DesertPlantBlock::new);

    public DesertPlantBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.DIRT) || floor.isIn(BlockTags.SAND) || floor.isOf(Blocks.FARMLAND);
    }
}