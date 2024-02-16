package net.jukoz.me.entity.humans.rohan;

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

public class RohanHumanRenderer extends BipedEntityRenderer<RohanHumanEntity, RohanHumanModel<RohanHumanEntity>> {
    private static final String PATH = "textures/entities/humans/rohan/";

    public RohanHumanRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new RohanHumanModel<>(ctx.getPart(ModEntityModelLayers.HUMAN)), 0.5f);
        this.addFeature(new ArmorFeatureRenderer<>(this, new RohanHumanModel(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)),
                new RohanHumanModel(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)), ctx.getModelManager()));

    }

    @Override
    public Identifier getTexture(RohanHumanEntity entity) {
        return new Identifier(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    public static final Map<RohanHumanVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(RohanHumanVariant.class), (resourceLocation) -> {
                resourceLocation.put(RohanHumanVariant.LIGHT_BROWN_RED,
                        PATH + "rohan1.png");
                resourceLocation.put(RohanHumanVariant.PALE_BLUE_YELLOW,
                        PATH + "rohan2.png");
                resourceLocation.put(RohanHumanVariant.PALE_GREY_ORANGE,
                        PATH + "rohan3.png");
            });

    @Override
    public void render(RohanHumanEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        poseStack.scale(1.0f, 1.0f, 1.0f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
