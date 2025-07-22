package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.client.renderer.ArmedEntityRenderStateAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityRenderer.class)
public class BipedEntityRendererMixin {

    @Inject(at = @At("TAIL"), method = "updateRenderState(Lnet/minecraft/entity/mob/MobEntity;Lnet/minecraft/client/render/entity/state/BipedEntityRenderState;F)V")
    private <T extends MobEntity, S extends BipedEntityRenderState>
    void positionRightArm(T mobEntity, S bipedEntityRenderState, float f, CallbackInfo ci) {
        ItemStack mainHandStack = mobEntity.getMainHandStack();
        ItemStack offHandStack = mobEntity.getOffHandStack();
        ((ArmedEntityRenderStateAccess)bipedEntityRenderState).setMainHandStack(mainHandStack);
        ((ArmedEntityRenderStateAccess)bipedEntityRenderState).setOffHandStack(offHandStack);
    }
}
