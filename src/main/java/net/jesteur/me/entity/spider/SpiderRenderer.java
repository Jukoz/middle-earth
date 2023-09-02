package net.jesteur.me.entity.spider;


import com.google.common.collect.Maps;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.entity.model.ModEntityModelLayers;
import net.jesteur.me.entity.trolls.cave.CaveTrollVariant;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class SpiderRenderer extends MobEntityRenderer<SpiderEntity, SpiderModel> {
    private static final String PATH = "textures/entities/spiders/";

    public SpiderRenderer(EntityRendererFactory.Context context) {
        this(context, 0.45F, ModEntityModelLayers.SPIDER);
    }

    protected SpiderRenderer(EntityRendererFactory.Context ctx, float shadowRadius, EntityModelLayer layer) {
        super(ctx, new SpiderModel(ctx.getPart(layer)), shadowRadius);
    }

    @Override
    public Identifier getTexture(SpiderEntity entity) {
        return new Identifier(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    public static final Map<SpiderVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(SpiderVariant.class), (resourceLocation) -> {
                resourceLocation.put(SpiderVariant.BLACK,
                        PATH + "spider1.png");
                resourceLocation.put(SpiderVariant.BROWN,
                        PATH + "spider2.png");
                resourceLocation.put(SpiderVariant.DARK_GREEN,
                        PATH + "spider3.png");
            });

    @Override
    public void render(SpiderEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
