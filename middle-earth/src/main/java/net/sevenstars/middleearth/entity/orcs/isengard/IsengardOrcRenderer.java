package net.sevenstars.middleearth.entity.orcs.isengard;

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

public class IsengardOrcRenderer extends BipedEntityRenderer<IsengardOrcEntity, IsengardOrcModel<IsengardOrcEntity>> {
    private static final String PATH = "textures/entities/orcs/isengard/";

    public IsengardOrcRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new IsengardOrcModel<>(ctx.getPart(ModEntityModelLayers.ORC)), 0.5f);
        this.addFeature(new ArmorFeatureRenderer<>(this, new IsengardOrcModel(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)),
                new IsengardOrcModel(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)), ctx.getModelManager()));

    }

    @Override
    public Identifier getTexture(IsengardOrcEntity entity) {
        return Identifier.of(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    public static final Map<IsengardOrcVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(IsengardOrcVariant.class), (resourceLocation) -> {
                resourceLocation.put(IsengardOrcVariant.LIGHT_BROWN_RED,
                        PATH + "orc1.png");
                resourceLocation.put(IsengardOrcVariant.PALE_BLUE_YELLOW,
                        PATH + "orc2.png");
                resourceLocation.put(IsengardOrcVariant.PALE_GREY_ORANGE,
                        PATH + "orc3.png");
            });

    @Override
    public void render(IsengardOrcEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        poseStack.scale(0.75f, 0.75f, 0.75f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
