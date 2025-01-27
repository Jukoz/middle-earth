package net.sevenstars.middleearth.block.special;

import com.mojang.serialization.MapCodec;
import net.sevenstars.middleearth.block.ModNatureBlocks;
import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineLogic;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;

public class GlowWormBottomBlock extends AbstractPlantStemBlock {
    public static final MapCodec<GlowWormBottomBlock> CODEC = GlowWormBottomBlock.createCodec(GlowWormBottomBlock::new);

    protected static final VoxelShape SHAPE = Block.createCuboidShape(4.0, 9.0, 4.0, 12.0, 16.0, 12.0);

    public GlowWormBottomBlock(Settings settings) {
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
        return ModNatureBlocks.GLOWWORM_MAIN;
    }

    protected boolean chooseStemState(BlockState state) {
        return VineLogic.isValidForWeepingStem(state);
    }
}
