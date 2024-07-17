package net.jukoz.me.block.special.fireBlocks;

import net.jukoz.me.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class BrazierBlockEntity extends ToggleableFireBlockEntity {

    public BrazierBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BIG_BRAZIER, pos, state);
    }
}
