package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.sevenstars.middleearth.client.renderer.light.GrippedLights;
import net.sevenstars.middleearth.client.renderer.light.HeldBlocks;
import net.sevenstars.middleearth.client.renderer.light.HeldFlames;
import net.sevenstars.middleearth.client.renderer.light.LampBeams;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Notes where your own off-hand lamp or flame is drawn, so its beam leaves the lens you see and its flame burns at the
 * tip; draws your off hand gripping a lantern by its handle ({@link GrippedLights}); and draws an
 * off-hand light's held block in place of its item ({@link HeldBlocks}).
 */
@Mixin(HeldItemRenderer.class)
public class HeldItemRendererBeamMixin {
    @Shadow
    @Final
    private EntityRenderDispatcher entityRenderDispatcher;

    @Inject(at = @At("HEAD"), method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", cancellable = true)
    private void middleearth$noteLamp(LivingEntity entity, ItemStack stack, ItemDisplayContext context, MatrixStack matrices,
                                      VertexConsumerProvider consumers, int light, CallbackInfo ci) {
        if (context.isFirstPerson()) {
            LampBeams.noteFirstPerson(stack, context.isLeftHand(), matrices.peek().getPositionMatrix());
            HeldFlames.noteFirstPerson(stack, context.isLeftHand(), matrices.peek().getPositionMatrix());
            if (stack == entity.getOffHandStack() && HeldBlocks.renderFirstPerson(stack, matrices, consumers, light)) {
                ci.cancel();
            }
        }
    }

    /**
     * A lantern in the off hand is drawn held by its handle in your fist, with your arm. Left to vanilla
     * while invisible (it draws no arm then) or looking through a spyglass (it draws nothing then).
     */
    @Inject(at = @At("HEAD"), method = "renderFirstPersonItem", cancellable = true)
    private void middleearth$gripLight(AbstractClientPlayerEntity player, float tickProgress, float pitch, Hand hand,
                                       float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices,
                                       VertexConsumerProvider consumers, int light, CallbackInfo ci) {
        if (hand != Hand.OFF_HAND || player.isInvisible() || player.isUsingSpyglass() || !GrippedLights.gripped(item)) {
            return;
        }
        GrippedLights.render((HeldItemRenderer) (Object) this, entityRenderDispatcher, player, player.getMainArm().getOpposite(),
            swingProgress, equipProgress, item, matrices, consumers, light);
        ci.cancel();
    }
}
