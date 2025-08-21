package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.render.entity.AgeableMobEntityRenderer;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.client.renderer.ArmedEntityRenderStateAccess;
import net.sevenstars.middleearth.entity.spider.EnwebbedFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityRenderer.class)
public abstract class BipedEntityRendererMixin<T extends MobEntity, S extends BipedEntityRenderState, M extends BipedEntityModel<S>>
        extends AgeableMobEntityRenderer<T, S, M> {


    public BipedEntityRendererMixin(EntityRendererFactory.Context context, M model, M babyModel, float shadowRadius) {
        super(context, model, babyModel, shadowRadius);
    }

    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;Lnet/minecraft/client/render/entity/model/BipedEntityModel;Lnet/minecraft/client/render/entity/model/BipedEntityModel;FLnet/minecraft/client/render/entity/feature/HeadFeatureRenderer$HeadTransformation;)V")
    public <S extends BipedEntityRenderState, M extends BipedEntityModel<S>>
    void BipedEntityRenderer(EntityRendererFactory.Context context, BipedEntityModel model, BipedEntityModel babyModel,
                             float scale, HeadFeatureRenderer.HeadTransformation headTransformation, CallbackInfo ci) {

        this.addFeature(new EnwebbedFeatureRenderer<>(this, context.getEntityModels(), context.getEquipmentRenderer()));
    }


    @Inject(at = @At("TAIL"), method = "updateRenderState(Lnet/minecraft/entity/mob/MobEntity;Lnet/minecraft/client/render/entity/state/BipedEntityRenderState;F)V")
    private <T extends MobEntity, S extends BipedEntityRenderState>
    void positionRightArm(T mobEntity, S bipedEntityRenderState, float f, CallbackInfo ci) {
        ItemStack mainHandStack = mobEntity.getMainHandStack();
        ItemStack offHandStack = mobEntity.getOffHandStack();
        ((ArmedEntityRenderStateAccess)bipedEntityRenderState).setMainHandStack(mainHandStack);
        ((ArmedEntityRenderStateAccess)bipedEntityRenderState).setOffHandStack(offHandStack);
    }

    @Override
    public Identifier getTexture(S state) {
        return null;
    }

    @Override
    public S createRenderState() {
        return null;
    }
}
