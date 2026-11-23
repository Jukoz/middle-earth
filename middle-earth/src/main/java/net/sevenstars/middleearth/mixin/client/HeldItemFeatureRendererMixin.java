package net.sevenstars.middleearth.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.sevenstars.middleearth.client.renderer.ArmedEntityRenderStateAccess;
import net.sevenstars.middleearth.client.renderer.light.HangingLanterns;
import net.sevenstars.middleearth.client.renderer.light.HeldBlocks;
import net.sevenstars.middleearth.client.renderer.light.HeldFlames;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Third person: notes held flame positions, hangs shaded lanterns and draws held blocks. */
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
            if (HangingLanterns.hangs(offHand)
                && ((FeatureRenderer<?, ?>) (Object) this).getContextModel() instanceof BipedEntityModel<?> model) {
                HangingLanterns.hang(matrices, arm == Arm.LEFT ? model.leftArm : model.rightArm);
            }
            HeldFlames.noteThirdPerson(offHand, matrices.peek().getPositionMatrix());
        }
    }

    @WrapOperation(method = "renderItem", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/client/render/item/ItemRenderState;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V"))
    private void middleearth$heldBlock(ItemRenderState itemState, MatrixStack matrices, VertexConsumerProvider consumers,
                                       int light, int overlay, Operation<Void> original, ArmedEntityRenderState state,
                                       ItemRenderState itemStateArg, Arm arm, MatrixStack matricesArg,
                                       VertexConsumerProvider consumersArg, int lightArg) {
        if (arm == state.mainArm.getOpposite() && state instanceof ArmedEntityRenderStateAccess access
            && HeldBlocks.renderThirdPerson(access.getOffHandStackItself(), matrices, consumers, light)) {
            return;
        }
        original.call(itemState, matrices, consumers, light, overlay);
    }
}
