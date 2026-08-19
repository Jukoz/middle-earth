package net.sevenstars.middleearth.entity.spider.larva;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.spider.SpiderVariant;

public class ShelobiteLarvaRenderer extends MobRenderer<ShelobiteLarvaEntity, ShelobiteLarvaModel> {

    public ShelobiteLarvaRenderer(EntityRendererProvider.Context context) {
        this(context, 0.2F, EntityModelLayersME.SHELOBITE_LARVA);
    }

    protected ShelobiteLarvaRenderer(EntityRendererProvider.Context ctx, float shadowRadius, ModelLayerLocation layer) {
        super(ctx, new ShelobiteLarvaModel(ctx.bakeLayer(layer)), shadowRadius);
    }


    @Override
    public ResourceLocation getTextureLocation(ShelobiteLarvaEntity entity) {
        return SpiderVariant.texture(entity.getVariant().assetInfo().larva());
    }
}
