package net.sevenstars.middleearth.block.special;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.sevenstars.middleearth.sound.SoundsME;

public class OrcishDrumBlock extends Block {

    public OrcishDrumBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (world.getBlockState(pos.above()).isAir()) {
            RandomSource random = world.getRandom();
            world.blockEvent(pos, this, 0, 0);
            world.playSound(player, pos, SoundsME.ORC_DRUM, SoundSource.BLOCKS, 1.0F + random.nextFloat(), (float)(0.75F + (random.nextFloat() * 0.4)));
            world.gameEvent(player, GameEvent.BLOCK_ACTIVATE, pos);
            return InteractionResult.SUCCESS;
        }

        return super.useWithoutItem(state, world, pos, player, hit);
    }
}
