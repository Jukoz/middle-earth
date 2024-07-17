package net.jukoz.me.block.special;

import com.mojang.serialization.MapCodec;
import net.jukoz.me.block.ModNatureBlocks;
import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineLogic;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;

public class MirkwoodVinesBottomBlock extends AbstractPlantStemBlock {
    public static final MapCodec<MirkwoodVinesBottomBlock> CODEC = MirkwoodVinesBottomBlock.createCodec(MirkwoodVinesBottomBlock::new);

    protected static final VoxelShape SHAPE = Block.createCuboidShape(4.0, 9.0, 4.0, 12.0, 16.0, 12.0);

    public MirkwoodVinesBottomBlock(Settings settings) {
        super(settings, Direction.DOWN, SHAPE, false, 0.05);
    }

    @Override
    protected MapCodec<? extends AbstractPlantStemBlock> getCodec() {
        return CODEC;
    }

    protected int getGrowthLength(Random random) {
        return random.nextInt(5);
    }

    protected Block getPlant() {
        return ModNatureBlocks.MIRKWOOD_VINES_PLANT;
    }

    protected boolean chooseStemState(BlockState state) {
        return VineLogic.isValidForWeepingStem(state);
    }
}
