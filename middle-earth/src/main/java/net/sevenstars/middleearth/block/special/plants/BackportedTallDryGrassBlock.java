package net.sevenstars.middleearth.block.special.plants;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;

public final class BackportedTallDryGrassBlock extends BackportedDryVegetationBlock implements BonemealableBlock {
    public static final MapCodec<BackportedTallDryGrassBlock> CODEC = simpleCodec(BackportedTallDryGrassBlock::new);
    private static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public BackportedTallDryGrassBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<BackportedTallDryGrassBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return BackportedPlantSpread.canSpread(level, pos, ModNatureBlocks.SHORT_DRY_GRASS.defaultBlockState());
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockState shortGrass = ModNatureBlocks.SHORT_DRY_GRASS.defaultBlockState();
        BackportedPlantSpread.findSpreadPosition(level, pos, shortGrass)
                .ifPresent(candidate -> level.setBlock(candidate, shortGrass, Block.UPDATE_ALL));
    }
}
