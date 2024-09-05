package net.jukoz.me.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.block.special.SconceBlock;
import net.jukoz.me.block.special.WallSconceBlock;
import net.jukoz.me.block.special.fireBlocks.AbstractToggleableFireBlock;
import net.jukoz.me.entity.ModEntities;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PotionEntity.class)
public abstract class PotionEntityMixin extends ThrownItemEntity {

    public PotionEntityMixin(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "extinguishFire")
    private void onDestroyedByExplosion(BlockPos pos, CallbackInfo ci, @Local BlockState blockState) {

        if (AbstractToggleableFireBlock.isLitFireBlock(blockState) || SconceBlock.isLitSconce(blockState) || WallSconceBlock.isLitWallSconce(blockState)){
            this.getWorld().syncWorldEvent((PlayerEntity)null, 1009, pos, 0);
            AbstractToggleableFireBlock.extinguish(this.getOwner(), this.getWorld(), pos, blockState);
            this.getWorld().setBlockState(pos, (BlockState)blockState.with(CampfireBlock.LIT, false));
        }
    }
}
