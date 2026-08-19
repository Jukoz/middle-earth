package net.sevenstars.middleearth.block.special.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;

public final class BerryHollyLeavesBlock extends ModLeavesBlock implements BonemealableBlock {
    public BerryHollyLeavesBlock(float leafParticleChance, Properties properties) {
        super(leafParticleChance, properties, true);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return state.getBlock() == this;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        level.setBlock(pos, copySharedProperties(state, ModNatureBlocks.BERRY_HOLLY_LEAVES.defaultBlockState()), Block.UPDATE_ALL);
    }
}
