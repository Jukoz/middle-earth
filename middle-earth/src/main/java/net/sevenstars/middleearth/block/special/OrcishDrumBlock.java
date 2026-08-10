package net.sevenstars.middleearth.block.special;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.sevenstars.middleearth.sound.SoundsME;

public class OrcishDrumBlock extends Block {

    public OrcishDrumBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.getBlockState(pos.up()).isAir()) {
            Random random = world.getRandom();
            world.addSyncedBlockEvent(pos, this, 0, 0);
            world.playSound(player, pos, SoundsME.ORC_DRUM, SoundCategory.BLOCKS, 1.0F + random.nextFloat(), (float)(0.75F + (random.nextFloat() * 0.4)));
            world.emitGameEvent(player, GameEvent.BLOCK_ACTIVATE, pos);
            return ActionResult.SUCCESS;
        }

        return super.onUse(state, world, pos, player, hit);
    }
}
