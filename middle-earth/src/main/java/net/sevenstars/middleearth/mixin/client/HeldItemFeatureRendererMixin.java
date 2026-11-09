package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.sevenstars.middleearth.client.renderer.ArmedEntityRenderStateAccess;
import net.sevenstars.middleearth.client.renderer.light.HeldFlames;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Notes where a torch or candle in someone's off hand is drawn on them, for its flame. */
@Mixin(HeldItemFeatureRenderer.class)
public class HeldItemFeatureRendererMixin {
    @Inject(method = "renderItem", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/client/render/item/ItemRenderState;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V"))
    private void middleearth$noteFlame(ArmedEntityRenderState state, ItemRenderState itemState, Arm arm, MatrixStack matrices,
                                       VertexConsumerProvider consumers, int light, CallbackInfo ci) {
        if (arm != state.mainArm.getOpposite() || !(state instanceof ArmedEntityRenderStateAccess access)) {
            return;
        }
        ItemStack offHand = access.getOffHandStackItself();
        if (offHand != null && !offHand.isEmpty()) {
            HeldFlames.noteThirdPerson(offHand, matrices.peek().getPositionMatrix());
        }
    }
}
