package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.client.renderer.rings.RingBand;
import net.sevenstars.middleearth.client.renderer.rings.RingBandRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Fills the ring band into the player render state and draws it on the first-person main arm. */
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererRingMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityRenderState, PlayerEntityModel> {
    protected PlayerEntityRendererRingMixin(EntityRendererFactory.Context ctx, PlayerEntityModel model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "updateRenderState(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V",
            at = @At("TAIL"))
    private void middleearth$ringBandState(AbstractClientPlayerEntity player, PlayerEntityRenderState state, float tickProgress, CallbackInfo ci) {
        ((RingBandRenderer.RingBandRenderState) state).middleearth$setRingBand(RingBand.of(player));
    }

    @Inject(method = "renderArm", at = @At("TAIL"))
    private void middleearth$firstPersonRing(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Identifier skinTexture,
                                             ModelPart arm, boolean sleeveVisible, CallbackInfo ci) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) {
            return;
        }
        Arm side = arm == this.getModel().rightArm ? Arm.RIGHT : Arm.LEFT;
        if (side != player.getMainArm()) {
            return;
        }
        boolean slim = player.getSkinTextures().model() == SkinTextures.Model.SLIM;
        RingBandRenderer.renderOnArm(matrices, vertexConsumers, light, RingBand.of(player), arm, side, slim, false);
    }
}
