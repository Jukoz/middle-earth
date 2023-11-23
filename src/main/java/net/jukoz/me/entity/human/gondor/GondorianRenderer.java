package net.jukoz.me.entity.human.gondor;

import com.google.common.collect.Maps;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class GondorianRenderer extends BipedEntityRenderer<GondorianEntity, GondorianModel<GondorianEntity>> {
    private static final String PATH = "textures/entities/humans/gondor/";
    public GondorianRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new GondorianModel<>(ctx.getPart(EntityModelLayers.PLAYER)), 0.5f);
        this.addFeature(new ArmorFeatureRenderer<>(this,
                new GondorianModel<>(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)),
                new GondorianModel<>(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)),
                ctx.getModelManager()));
    }

    public static final Map<GondorianVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(GondorianVariant.class), (resourceLocation) -> {
                resourceLocation.put(GondorianVariant.MAN,
                        PATH + "man.png");
                resourceLocation.put(GondorianVariant.WOMAN,
                        PATH + "woman.png");
            });

    @Override
    public Identifier getTexture(GondorianEntity entity) {
        return new Identifier(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    @Override
    public void render(GondorianEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.scale(0.92f, 0.92f, 0.92f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
