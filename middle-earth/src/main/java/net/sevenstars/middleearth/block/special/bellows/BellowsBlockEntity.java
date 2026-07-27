package net.sevenstars.middleearth.block.special.bellows;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;
import net.sevenstars.middleearth.block.special.forge.ForgeBlockEntity;
import net.sevenstars.middleearth.sound.SoundsME;
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
    
    public BellowsBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BELLOWS, pos, state);
        if(RANDOM == null)
            RANDOM = new Random();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public boolean tryPumpingBellow(BlockState state, Level world, BlockPos pos, BellowsBlockEntity blockEntity, Direction direction, Entity entity) {
        if(blockEntity.animationProgress == 0) {
            if (!world.isClientSide){
                if(blockEntity.activate(direction)){
                    BlockPos forgePos = pos.relative(state.getValue(BellowsBlock.FACING));
                    if(world.getBlockState(forgePos).is(TagKey.create(Registries.BLOCK, MiddleEarth.of("forge")))) {
                        ForgeBlockEntity forgeBlockEntity = (ForgeBlockEntity) world.getBlockEntity(forgePos);
                        if(forgeBlockEntity != null) {
                            forgeBlockEntity.bellowsBoost();
                        }
                    }
                    world.playSound((Player)null,  pos, SoundsME.BELLOWS_PUSH, SoundSource.BLOCKS, 2.0F, 1.0F);
                    world.gameEvent(entity, GameEvent.BLOCK_CHANGE, pos);
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
            if(this.level != null){
                BlockPos blockPos = this.getBlockPos();
                this.level.blockEvent(blockPos, this.getBlockState().getBlock(), 1, direction.get3DDataValue());
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean triggerEvent(int type, int data) {
        if (type == 1) {
            this.pumping = true;
            this.animationProgress = 0;
            return true;
        } else {
            return super.triggerEvent(type, data);
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

    public static void clientTick(Level world, BlockPos pos, BlockState state, BellowsBlockEntity blockEntity) {
        // Only occurs if it's the initial tick
        if(blockEntity.pumping && blockEntity.animationProgress == 0)
        {
            Vec3i directionVec = state.getValue(BellowsBlock.FACING).getNormal();
            Vec3 center = pos.getCenter();

            int particleAmount = RANDOM.nextInt(AVERAGE_PARTICLES - PARTICLE_AMOUNT_MODIFIER, AVERAGE_PARTICLES + PARTICLE_AMOUNT_MODIFIER);
            for(int i = 0; i < particleAmount; i++){
                world.addParticle(ParticleTypes.POOF,
                        center.x() + directionVec.getX() * 0.4f,
                        center.y() - 0.2f,
                        center.z() + directionVec.getZ() * 0.4f,
                        0, 0, 0);
            }
        }

        tick(blockEntity);
    }

    public static void serverTick(Level world, BlockPos pos, BlockState state, BellowsBlockEntity blockEntity) {
        tick(blockEntity);
    }
}
