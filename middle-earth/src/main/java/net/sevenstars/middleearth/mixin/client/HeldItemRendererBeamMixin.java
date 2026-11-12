package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.client.renderer.light.LampBeams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Notes where your own off-hand lamp or flame is drawn, so its beam leaves the lens you see and its flame burns at the tip. */
@Mixin(HeldItemRenderer.class)
public class HeldItemRendererBeamMixin {
    @Inject(at = @At("HEAD"), method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    private void middleearth$noteLamp(LivingEntity entity, ItemStack stack, ItemDisplayContext context, MatrixStack matrices,
                                      VertexConsumerProvider consumers, int light, CallbackInfo ci) {
        if (context.isFirstPerson()) {
            LampBeams.noteFirstPerson(stack, context.isLeftHand(), matrices.peek().getPositionMatrix());
            net.sevenstars.middleearth.client.renderer.light.HeldFlames.noteFirstPerson(stack, context.isLeftHand(), matrices.peek().getPositionMatrix());
        }
    }
}
