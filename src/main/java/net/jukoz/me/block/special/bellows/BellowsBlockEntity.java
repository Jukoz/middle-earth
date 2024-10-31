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
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BellowsBlockEntity extends BlockEntity {
    public static final int MAX_TICKS = 30;
    public int animationProgress = 0;
    public boolean animating = false;
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

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putInt(ID + ".animation", this.animationProgress);
        nbt.putBoolean(ID + ".animating", this.animating);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.animationProgress = nbt.getInt(ID + ".animation");
        this.animating = nbt.getBoolean(ID + ".animating");
    }

    public void pumpBellows(BlockState state, World world, BlockPos pos, BellowsBlockEntity bellowsBlockEntity) {
        if(bellowsBlockEntity.animationProgress == 0) {
            bellowsBlockEntity.animating = true;
            Vec3i direction = state.get(BellowsBlock.FACING).getVector();
            Vec3d center = pos.toCenterPos();

            Random random = new Random();
            world.playSound(null, pos, ModSounds.BELLOWS_PUSH, SoundCategory.BLOCKS, 0.95F + random.nextFloat() * 0.05F, 0.95F + random.nextFloat() * 0.05F);
            BlockPos forgePos = pos.offset(state.get(BellowsBlock.FACING));
            if(world.getBlockState(forgePos).getBlock() == ModDecorativeBlocks.FORGE) {
                ForgeBlockEntity forgeBlockEntity = (ForgeBlockEntity) world.getBlockEntity(forgePos);
                if(forgeBlockEntity != null) {
                    forgeBlockEntity.bellowsBoost();
                }
            }

            world.addParticle(ParticleTypes.POOF,
                    center.getX() + direction.getX() * 0.4f,
                    center.getY() - 0.2f,
                    center.getZ() + direction.getZ() * 0.4f,
                    0, 0, 0);
            System.out.println("pumped");
        }
    }

    public void update() {
        markDirty();
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, BellowsBlockEntity entity) {
        if(entity.animating) {
            entity.animationProgress++;
            entity.update();
            if(entity.animationProgress > MAX_TICKS) {
                entity.animationProgress = 0;
                entity.animating = false;
                entity.update();
            }
        }
    }
}
