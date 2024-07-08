package net.jukoz.me.entity.humans.gondor;

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

public class GondorHumanRenderer extends BipedEntityRenderer<GondorHumanEntity, GondorHumanModel<GondorHumanEntity>> {
    private static final String PATH = "textures/entities/humans/gondor/";

    public GondorHumanRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new GondorHumanModel<>(ctx.getPart(ModEntityModelLayers.HUMAN)), 0.5f);
        this.addFeature(new ArmorFeatureRenderer<>(this, new GondorHumanModel(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)),
                new GondorHumanModel(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)), ctx.getModelManager()));

    }

    @Override
    public Identifier getTexture(GondorHumanEntity entity) {
        return Identifier.of(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    public static final Map<GondorHumanVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(GondorHumanVariant.class), (resourceLocation) -> {
                resourceLocation.put(GondorHumanVariant.LIGHT_BROWN_RED,
                        PATH + "gondor1.png");
                resourceLocation.put(GondorHumanVariant.PALE_BLUE_YELLOW,
                        PATH + "gondor2.png");
                resourceLocation.put(GondorHumanVariant.PALE_GREY_ORANGE,
                        PATH + "gondor3.png");
            });

    @Override
    public void render(GondorHumanEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        poseStack.scale(0.95f, 0.95f, 0.95f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
