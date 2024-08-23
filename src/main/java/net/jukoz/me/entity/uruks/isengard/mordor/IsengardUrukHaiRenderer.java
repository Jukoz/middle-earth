package net.jukoz.me.entity.uruks.isengard.mordor;

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

public class IsengardUrukHaiRenderer extends BipedEntityRenderer<IsengardUrukHaiEntity, IsengardUrukHaiModel<IsengardUrukHaiEntity>> {
    private static final String PATH = "textures/entities/uruks/isengard/";

    public IsengardUrukHaiRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new IsengardUrukHaiModel<>(ctx.getPart(ModEntityModelLayers.URUK)), 0.5f);
        this.addFeature(new ArmorFeatureRenderer<>(this, new IsengardUrukHaiModel(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)),
                new IsengardUrukHaiModel(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)), ctx.getModelManager()));
    }

    @Override
    public Identifier getTexture(IsengardUrukHaiEntity entity) {
        return Identifier.of(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    public static final Map<IsengardUrukHaiVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(IsengardUrukHaiVariant.class), (resourceLocation) -> {
                resourceLocation.put(IsengardUrukHaiVariant.LIGHT_BROWN_RED,
                        PATH + "uruk1.png");
                resourceLocation.put(IsengardUrukHaiVariant.PALE_BLUE_YELLOW,
                        PATH + "uruk2.png");
                resourceLocation.put(IsengardUrukHaiVariant.PALE_GREY_ORANGE,
                        PATH + "uruk3.png");
            });

    @Override
    public void render(IsengardUrukHaiEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        poseStack.scale(0.92f, 0.92f, 0.92f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
