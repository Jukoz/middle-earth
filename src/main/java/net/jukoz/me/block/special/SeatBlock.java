package net.jukoz.me.block.special;

import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.seat.SeatEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SeatBlock extends Block {

    public SeatBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if(world.isClient){
            return ActionResult.SUCCESS;
        } else {
            SeatEntity seat = new SeatEntity(ModEntities.SEAT_ENTITY, world);
            seat.setPos(pos.getX() + 0.5, pos.getY()+ 0.5, pos.getZ()+ 0.5);
            world.spawnEntity(seat);
            player.startRiding(seat);
        }
        return super.onUse(state, world, pos, player, hit);
    }
}
