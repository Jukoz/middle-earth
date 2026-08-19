package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.world.entity.Mob;
import net.sevenstars.middleearth.entity.spider.EnwebbedFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidMobRenderer.class)
public abstract class BipedEntityRendererMixin<T extends Mob, M extends HumanoidModel<T>>
        extends MobRenderer<T, M> {
    protected BipedEntityRendererMixin(EntityRendererProvider.Context context, M model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @Inject(
            method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;"
                    + "Lnet/minecraft/client/model/HumanoidModel;FFFF)V",
            at = @At("TAIL")
    )
    private void middleEarth$addEnwebbedLayer(
            EntityRendererProvider.Context context,
            M model,
            float shadowRadius,
            float headScaleX,
            float headScaleY,
            float headScaleZ,
            CallbackInfo ci
    ) {
        this.addLayer(new EnwebbedFeatureRenderer<>(this, context.getModelSet()));
    }
}
