package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.HitResult;
import net.sevenstars.middleearth.client.TrollRiderCamera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    @Inject(method = "getCrosshairTarget(FLnet/minecraft/entity/Entity;)Lnet/minecraft/util/hit/HitResult;", at = @At("HEAD"), cancellable = true)
    private void targetAlongTrollCamera(float tickProgress, Entity camera, CallbackInfoReturnable<HitResult> cir) {
        if(TrollRiderCamera.isActive(MinecraftClient.getInstance())) {
            ClientPlayerEntity player = (ClientPlayerEntity)(Object)this;
            HitResult target = TrollRiderCamera.findCrosshairTarget(camera, player.getBlockInteractionRange(), player.getEntityInteractionRange(), tickProgress);
            if(target != null) {
                cir.setReturnValue(target);
            }
        }
    }
}
