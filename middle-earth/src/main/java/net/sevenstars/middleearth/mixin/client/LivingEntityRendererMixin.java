package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.client.renderer.ArmedEntityRenderStateAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    private final static float VERTICAL_ANGLE = -1.4f;

    @Inject(at = @At("TAIL"), method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V")
    private <T extends LivingEntity, S extends LivingEntityRenderState>
    void updateRenderState(T livingEntity, S livingEntityRenderState, float f, CallbackInfo ci) {
        ItemStack mainHandStack = livingEntity.getMainHandStack();
        ItemStack offHandStack = livingEntity.getOffHandStack();
        ((ArmedEntityRenderStateAccess)livingEntityRenderState).setMainHandStack(mainHandStack);
        ((ArmedEntityRenderStateAccess)livingEntityRenderState).setOffHandStack(offHandStack);
    }
}
