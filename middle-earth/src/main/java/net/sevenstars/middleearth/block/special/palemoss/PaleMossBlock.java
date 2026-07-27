package net.sevenstars.middleearth.block.special.palemoss;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.world.features.tree.ModTreeConfiguredFeatures;

public final class PaleMossBlock extends Block implements BonemealableBlock {
    public static final MapCodec<PaleMossBlock> CODEC = simpleCodec(PaleMossBlock::new);

    public PaleMossBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<PaleMossBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return level.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        level.registryAccess().registry(Registries.CONFIGURED_FEATURE)
                .flatMap(registry -> registry.getHolder(ModTreeConfiguredFeatures.PALE_MOSS_PATCH_BONEMEAL_KEY))
                .ifPresent(feature -> feature.value().place(
                        level, level.getChunkSource().getGenerator(), random, pos.above()));
    }

    @Override
    public Type getType() {
        return Type.NEIGHBOR_SPREADER;
    }
}
