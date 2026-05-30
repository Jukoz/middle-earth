package net.sevenstars.middleearth.block.special.plants;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.sevenstars.middleearth.particles.ModParticleTypes;

public class MistweedPlantBlock extends CustomPlantBlock implements Fertilizable {
    public static final MapCodec<CustomPlantBlock> CODEC = createCodec(MistweedPlantBlock::new);

    public MistweedPlantBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<CustomPlantBlock> getCodec() {
        return CODEC;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextDouble() <= 0.3) {
            double d = (double)pos.getX() + random.nextDouble() * 16.0 - 8.0;
            double e = (double)pos.getY() + random.nextDouble() * 5.0;
            double f = (double)pos.getZ() + random.nextDouble() * 16.0 - 8.0;
            world.addParticleClient(ModParticleTypes.BIOME_FOG_PARTICLE, d, e, f, 0.0, 0.0, 0.0);
        }
        super.randomDisplayTick(state, world, pos, random);
    }

    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return Fertilizable.canSpread(world, pos, state);
    }

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        Fertilizable.findPosToSpreadTo(world, pos, state).ifPresent((blockPos) -> {
            world.setBlockState(blockPos, this.getDefaultState());
        });
    }
}
