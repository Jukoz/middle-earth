package net.sevenstars.middleearth.block.special;

import com.mojang.serialization.MapCodec;
import net.sevenstars.middleearth.block.ModNatureBlocks;
import net.minecraft.block.AbstractPlantBlock;
import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.Block;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;

public class MirkwoodVinesBlock extends AbstractPlantBlock {
    public static final MapCodec<MirkwoodVinesBlock> CODEC = MirkwoodVinesBlock.createCodec(MirkwoodVinesBlock::new);
    public static final VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public MirkwoodVinesBlock(Settings settings) {
        super(settings, Direction.DOWN, SHAPE, false);
    }

    protected AbstractPlantStemBlock getStem() {
        return (AbstractPlantStemBlock) ModNatureBlocks.MIRKWOOD_VINES;
    }

    @Override
    protected MapCodec<MirkwoodVinesBlock> getCodec() {
        return CODEC;
    }
}
