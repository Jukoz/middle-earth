package net.sevenstars.middleearth.entity.spider.scuttler;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.spider.SpiderVariant;

public class ShelobiteScuttlerRenderer extends MobRenderer<ShelobiteScuttlerEntity, ShelobiteScuttlerModel> {

    public ShelobiteScuttlerRenderer(EntityRendererProvider.Context context) {
        this(context, 0.45F, EntityModelLayersME.SHELOBITE_SCUTTLER);
    }

    protected ShelobiteScuttlerRenderer(EntityRendererProvider.Context ctx, float shadowRadius, ModelLayerLocation layer) {
        super(ctx, new ShelobiteScuttlerModel(ctx.bakeLayer(layer)), shadowRadius);
    }

    @Override
    public ResourceLocation getTextureLocation(ShelobiteScuttlerEntity entity) {
        return SpiderVariant.texture(entity.getVariant().assetInfo().scuttler());
    }
}
