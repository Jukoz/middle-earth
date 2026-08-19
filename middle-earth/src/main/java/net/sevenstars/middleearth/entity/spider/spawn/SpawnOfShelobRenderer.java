package net.sevenstars.middleearth.entity.spider.spawn;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.spider.SpiderVariant;

public class SpawnOfShelobRenderer extends MobRenderer<SpawnOfShelobEntity, SpawnOfShelobModel> {

    public SpawnOfShelobRenderer(EntityRendererProvider.Context context) {
        this(context, 0.75F, EntityModelLayersME.SPAWN_OF_SHELOB);
    }

    protected SpawnOfShelobRenderer(EntityRendererProvider.Context ctx, float shadowRadius, ModelLayerLocation layer) {
        super(ctx, new SpawnOfShelobModel(ctx.bakeLayer(layer)), shadowRadius);
    }

    @Override
    public ResourceLocation getTextureLocation(SpawnOfShelobEntity entity) {
        return SpiderVariant.texture(entity.getVariant().assetInfo().spawnOfShelob());
    }
}
