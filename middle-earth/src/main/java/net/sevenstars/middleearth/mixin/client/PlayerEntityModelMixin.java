package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.renderer.ArmedEntityRenderStateAccess;
import net.sevenstars.middleearth.entity.spider.EnwebbedFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityModel.class)
public abstract class PlayerEntityModelMixin extends BipedEntityModel<PlayerEntityRenderState> {

    public PlayerEntityModelMixin(ModelPart modelPart) {
        super(modelPart);
    }

    @Inject(at = @At("TAIL"), method = "setAngles(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;)V")
    private <T extends PlayerEntityRenderState> void positionRightArm(PlayerEntityRenderState playerEntityRenderState, CallbackInfo ci) {
        ArmedEntityRenderStateAccess renderStateAccess = ((ArmedEntityRenderStateAccess)playerEntityRenderState);
        if(renderStateAccess.isRestrained()) {
            restrainedAnimation();
        }
    }

    private void restrainedAnimation() {
        this.rightArm.pitch = 0.0F;
        this.rightArm.yaw = 0.0F;
        this.leftArm.pitch = 0.0F;
        this.leftArm.yaw = 0.0F;
    }
}
