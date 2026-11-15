package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.HitResult;
import net.sevenstars.middleearth.client.TrollRiderCamera;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "findCrosshairTarget", at = @At("HEAD"), cancellable = true)
    private void targetAlongTrollCamera(Entity camera, double blockInteractionRange, double entityInteractionRange, float tickProgress, CallbackInfoReturnable<HitResult> cir) {
        if(TrollRiderCamera.isActive(this.client)) {
            HitResult target = TrollRiderCamera.findCrosshairTarget(camera, blockInteractionRange, entityInteractionRange, tickProgress);
            if(target != null) {
                cir.setReturnValue(target);
            }
        }
    }
}
