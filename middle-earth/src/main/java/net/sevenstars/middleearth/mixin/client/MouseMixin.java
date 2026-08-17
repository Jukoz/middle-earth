package net.sevenstars.middleearth.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.entity.wight.BarrowWightEntity;
import net.sevenstars.middleearth.statusEffects.HallucinationStatusEffect;
import net.sevenstars.middleearth.statusEffects.ModStatusEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(Mouse.class)
public abstract class MouseMixin {
    LivingEntity lookAt;
    int id;

    @Inject(method ="updateMouse", at = @At("HEAD"), cancellable = true)
    private void injected(double timeDelta, CallbackInfo cir) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if ( player != null && player.hasStatusEffect(ModStatusEffects.HALLUCINATION)) {
            if (lookAt != null && !lookAt.isDead() && id != -1) {
                lookAt = (LivingEntity) player.getWorld().getEntityById(id);

                if(lookAt == null){
                    player.removeStatusEffect(ModStatusEffects.HALLUCINATION);
                    id = -1;
                    return;
                }

                double dX = lookAt.getX() - player.getX();
                double dY = lookAt.getY() - player.getY();
                double dZ = lookAt.getZ() - player.getZ();

                double g = Math.sqrt(dX * dX + dZ * dZ);

                float destPitch = MathHelper.wrapDegrees((float) (-(MathHelper.atan2(dY, g) * 57.2957763671875)));
                float destYaw = MathHelper.wrapDegrees((float) (MathHelper.atan2(dZ, dX) * 57.2957763671875) - 90.0f);

                // lerpFactor use :
                // 0 > x < 1 (Needs to be between 0 and 1)
                // Close to 0 means no fluidity, close to 1 means too much fluidity and lot of control for the player
                float lerpFactor = 0.85f;

                destPitch = MathHelper.lerp(lerpFactor, destPitch, player.getPitch());
                destYaw = MathHelper.lerp(lerpFactor, destYaw, player.getYaw());

                player.setPitch(destPitch);
                player.setYaw(destYaw);
                player.setYaw(destYaw);
                player.setHeadYaw(player.getYaw());
                player.lastPitch = player.getPitch();
                player.lastYaw = player.getYaw();
            } else if(id == -1){
                List<BarrowWightEntity> nearbyEntities = player.getWorld().getEntitiesByClass(BarrowWightEntity.class,
                        player.getBoundingBox().expand(32), LivingEntity::isAlive);
                float dist = Float.MAX_VALUE;
                BarrowWightEntity closestEntity = null;
                for(BarrowWightEntity barrowWightEntity : nearbyEntities) {
                    float newDist = barrowWightEntity.distanceTo(player);
                    if(newDist < dist) {
                        dist = newDist;
                        closestEntity = barrowWightEntity;
                    }
                }
                this.lookAt = closestEntity;

                if(lookAt != null)
                    this.id = lookAt.getId();
                cir.cancel();
            }
        } else {
            lookAt = null;
            if(id != -1) id = -1;
        }
    }
}
