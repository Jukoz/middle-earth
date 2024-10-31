package net.jukoz.me.block.special.bellows;

import net.jukoz.me.block.ModBlockEntities;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.block.special.forge.ForgeBlockEntity;
import net.jukoz.me.sound.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BellowsBlockEntity extends BlockEntity {
    public static final int MAX_TICKS = 30;
    public int animationProgress;
    public boolean animating;
    private static final String ID = "bellows";

    //TODO Sync client/server animation + sound

    public BellowsBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BELLOWS, pos, state);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public void pumpBellows(BlockState state, World world, BlockPos pos, BellowsBlockEntity blockEntity, Direction direction) {
        if(blockEntity.animationProgress == 0) {
            blockEntity.animating = true;
            if (!world.isClient){
                BlockPos forgePos = pos.offset(state.get(BellowsBlock.FACING));
                if(world.getBlockState(forgePos).getBlock() == ModDecorativeBlocks.FORGE) {
                    ForgeBlockEntity forgeBlockEntity = (ForgeBlockEntity) world.getBlockEntity(forgePos);
                    if(forgeBlockEntity != null) {
                        forgeBlockEntity.bellowsBoost();
                    }
                }

                System.out.println("pumped");
                this.world.addSyncedBlockEvent(pos, this.getCachedState().getBlock(), 1, direction.getId());
            }
        }
    }

    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        if (type == 1) {
            this.animationProgress = 0;
            this.animating = true;
            return true;
        } else {
            return super.onSyncedBlockEvent(type, data);
        }
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, BellowsBlockEntity entity) {
        if(entity.animating) {
            ++entity.animationProgress;
        }

        if(entity.animationProgress > MAX_TICKS) {
            entity.animating = false;
            entity.animationProgress = 0;
        }
    }
}
