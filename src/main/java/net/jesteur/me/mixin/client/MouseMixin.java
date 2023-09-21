package net.jesteur.me.mixin.client;

import net.jesteur.me.entity.barrow_wights.BarrowWightEntity;
import net.jesteur.me.statusEffects.ModStatusEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
    LivingEntity lookAt;

    @Inject(method ="updateMouse()V", at = @At("HEAD"), cancellable = true)
    private void injected(CallbackInfo cir){
        PlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;
        if(player != null && player.hasStatusEffect(ModStatusEffects.HALLUCINATION)){
            if( lookAt != null  && !lookAt.isDead()){
                double dX = lookAt.getX() - player.getX(); // d
                double dY = lookAt.getY() - player.getY(); // e
                double dZ = lookAt.getZ() - player.getZ(); // f
                double g = Math.sqrt(dX * dX + dZ * dZ);

                float destPitch = MathHelper.wrapDegrees((float)(-(MathHelper.atan2(dY, g) * 57.2957763671875)));
                float destYaw = MathHelper.wrapDegrees((float)(MathHelper.atan2(dZ, dX) * 57.2957763671875) - 90.0f);

                destPitch = MathHelper.lerp(MinecraftClient.getInstance().getTickDelta()    , destPitch, player.getPitch());
                destYaw = MathHelper.lerp(MinecraftClient.getInstance().getTickDelta() , destYaw, player.getYaw());
                //player.changeLookDirection(1920/Math.sin(destYaw), 1080/Math.sin(destPitch)); cant figure this out.

                //destPitch = player.getPitch();
                //destYaw = player.getYaw();
                player.setPitch(destPitch);
                player.setYaw(destYaw);
                player.setHeadYaw(player.getYaw());
                player.prevPitch = player.getPitch();
                player.prevYaw = player.getYaw();

                //Vec3d currentLookAt = player.getCameraPosVec(0);
                //MinecraftClient.getInstance().player.sendMessage(Text.literal("Dest Yaw: " + destYaw + " while player: " + player.getYaw()));

            }
            else
                lookAt = player.getWorld().getClosestEntity(BarrowWightEntity.class, TargetPredicate.createNonAttackable(), null, player.getX(), player.getY(), player.getZ(), player.getBoundingBox().expand(12));
            cir.cancel();
        }
        else
            lookAt = null;
        }
    }


