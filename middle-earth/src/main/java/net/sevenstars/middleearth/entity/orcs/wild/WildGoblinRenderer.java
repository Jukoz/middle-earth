package net.sevenstars.middleearth.entity.orcs.wild;

import com.google.common.collect.Maps;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class WildGoblinRenderer extends BipedEntityRenderer<WildGoblinEntity, WildGoblinModel<WildGoblinEntity>> {
    private static final String PATH = "textures/entities/orcs/wild/";

    public WildGoblinRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new WildGoblinModel<>(ctx.getPart(ModEntityModelLayers.ORC)), 0.5f);
        this.addFeature(new ArmorFeatureRenderer<>(this, new WildGoblinModel(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)),
                new WildGoblinModel(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)), ctx.getModelManager()));

    }

    @Override
    public Identifier getTexture(WildGoblinEntity entity) {
        return Identifier.of(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    public static final Map<WildGoblinVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(WildGoblinVariant.class), (resourceLocation) -> {
                resourceLocation.put(WildGoblinVariant.LIGHT_BROWN_RED,
                        PATH + "orc1.png");
                resourceLocation.put(WildGoblinVariant.PALE_BLUE_YELLOW,
                        PATH + "orc2.png");
                resourceLocation.put(WildGoblinVariant.PALE_GREY_ORANGE,
                        PATH + "orc3.png");
            });

    @Override
    public void render(WildGoblinEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        poseStack.scale(0.8f, 0.8f, 0.8f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
