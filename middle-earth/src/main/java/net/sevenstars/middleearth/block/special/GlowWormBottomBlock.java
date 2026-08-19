package net.sevenstars.middleearth.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;

public class GlowWormBottomBlock extends GrowingPlantHeadBlock {
    public static final MapCodec<GlowWormBottomBlock> CODEC = GlowWormBottomBlock.simpleCodec(GlowWormBottomBlock::new);

    protected static final VoxelShape SHAPE = Block.box(4.0, 9.0, 4.0, 12.0, 16.0, 12.0);

    public GlowWormBottomBlock(Properties settings) {
        super(settings, Direction.DOWN, SHAPE, false, 0.05);
    }

    @Override
    protected MapCodec<? extends GrowingPlantHeadBlock> codec() {
        return CODEC;
    }

    protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
        return random.nextInt(5);
    }

    protected Block getBodyBlock() {
        return ModNatureBlocks.GLOWWORM_MAIN;
    }

    protected boolean canGrowInto(BlockState state) {
        return NetherVines.isValidGrowthState(state);
    }
}
