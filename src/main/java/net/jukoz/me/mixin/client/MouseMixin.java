package net.jukoz.me.mixin.client;

import net.jukoz.me.entity.barrow_wights.BarrowWightEntity;
import net.jukoz.me.statusEffects.HallucinationStatusEffect;
import net.jukoz.me.statusEffects.ModStatusEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
    LivingEntity lookAt;
    int id;

    @Inject(method ="updateMouse", at = @At("HEAD"), cancellable = true)
    private void injected(double timeDelta ,CallbackInfo cir) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if ( player != null && player.hasStatusEffect(ModStatusEffects.HALLUCINATION)) {
            if (lookAt != null && !lookAt.isDead() && id != -1) {
                lookAt = (LivingEntity) player.getWorld().getEntityById(id);

                if(lookAt == null){
                    ((HallucinationStatusEffect)player.getActiveStatusEffects().get(ModStatusEffects.HALLUCINATION).getEffectType()).stop(player);
                    player.sendMessage(Text.literal("is dead _ " + player.getActiveStatusEffects().get(ModStatusEffects.HALLUCINATION).getDuration()));
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
                player.prevPitch = player.getPitch();
                player.prevYaw = player.getYaw();
            } else if(id == -1){

                this.lookAt = player.getWorld().getClosestEntity(BarrowWightEntity.class, TargetPredicate.createNonAttackable(), null, player.getX(), player.getY(), player.getZ(), player.getBoundingBox().expand(28));
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
