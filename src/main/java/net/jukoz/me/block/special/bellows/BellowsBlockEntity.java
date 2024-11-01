package net.jukoz.me.block.special.bellows;

import net.jukoz.me.block.ModBlockEntities;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.block.special.forge.ForgeBlockEntity;
import net.jukoz.me.sound.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BellowsBlockEntity extends BlockEntity {
    public static final int MAX_TICKS = 30;
    public static final int AVERAGE_PARTICLES = 3;
    public static final int PARTICLE_AMOUNT_MODIFIER = 2;

    public static Random RANDOM;

    public int animationProgress;
    private static final String ID = "bellows";
    public boolean pumping;

    //TODO Sync client/server animation + sound

    public BellowsBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BELLOWS, pos, state);
        if(RANDOM == null)
            RANDOM = new Random();
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public boolean tryPumpingBellow(BlockState state, World world, BlockPos pos, BellowsBlockEntity blockEntity, Direction direction, Entity entity) {
        if(blockEntity.animationProgress == 0) {
            if (!world.isClient){
                if(blockEntity.activate(direction)){
                    BlockPos forgePos = pos.offset(state.get(BellowsBlock.FACING));
                    if(world.getBlockState(forgePos).getBlock() == ModDecorativeBlocks.FORGE) {
                        ForgeBlockEntity forgeBlockEntity = (ForgeBlockEntity) world.getBlockEntity(forgePos);
                        if(forgeBlockEntity != null) {
                            forgeBlockEntity.bellowsBoost();
                        }
                    }
                    world.playSound((PlayerEntity)null,  pos, ModSounds.BELLOWS_PUSH, SoundCategory.BLOCKS, 2.0F, 1.0F);
                    world.emitGameEvent(entity, GameEvent.BLOCK_CHANGE, pos);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean activate(Direction direction) {
        if (!this.pumping) {
            this.pumping = true;
            this.animationProgress = 0;
            if(this.world != null){
                BlockPos blockPos = this.getPos();
                this.world.addSyncedBlockEvent(blockPos, this.getCachedState().getBlock(), 1, direction.getId());
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        if (type == 1) {
            this.pumping = true;
            this.animationProgress = 0;
            return true;
        } else {
            return super.onSyncedBlockEvent(type, data);
        }
    }

    private static void tick(BellowsBlockEntity blockEntity) {
        if(blockEntity.pumping) {
            ++blockEntity.animationProgress;
            if(blockEntity.animationProgress > MAX_TICKS) {
                blockEntity.pumping = false;
                blockEntity.animationProgress = 0;
            }
        }
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, BellowsBlockEntity blockEntity) {
        // Only occurs if it's the initial tick
        if(blockEntity.pumping && blockEntity.animationProgress == 0)
        {
            Vec3i directionVec = state.get(BellowsBlock.FACING).getVector();
            Vec3d center = pos.toCenterPos();

            int particleAmount = RANDOM.nextInt(AVERAGE_PARTICLES - PARTICLE_AMOUNT_MODIFIER, AVERAGE_PARTICLES + PARTICLE_AMOUNT_MODIFIER);
            for(int i = 0; i < particleAmount; i++){
                world.addParticle(ParticleTypes.POOF,
                        center.getX() + directionVec.getX() * 0.4f,
                        center.getY() - 0.2f,
                        center.getZ() + directionVec.getZ() * 0.4f,
                        0, 0, 0);
            }
        }

        tick(blockEntity);
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, BellowsBlockEntity blockEntity) {
        tick(blockEntity);
    }
}
