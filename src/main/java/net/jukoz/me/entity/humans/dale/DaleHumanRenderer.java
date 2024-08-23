package net.jukoz.me.entity.humans.dale;

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

public class DaleHumanRenderer extends BipedEntityRenderer<DaleHumanEntity, DaleHumanModel<DaleHumanEntity>> {
    private static final String PATH = "textures/entities/humans/dale/";

    public DaleHumanRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new DaleHumanModel<>(ctx.getPart(ModEntityModelLayers.HUMAN)), 0.5f);
        this.addFeature(new ArmorFeatureRenderer<>(this, new DaleHumanModel<>(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)),
                new DaleHumanModel<>(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)), ctx.getModelManager()));

    }

    @Override
    public Identifier getTexture(DaleHumanEntity entity) {
        return Identifier.of(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    public static final Map<DaleHumanVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(DaleHumanVariant.class), (resourceLocation) -> {
                resourceLocation.put(DaleHumanVariant.LIGHT_BROWN_RED,
                        PATH + "dale1.png");
                resourceLocation.put(DaleHumanVariant.PALE_BLUE_YELLOW,
                        PATH + "dale2.png");
                resourceLocation.put(DaleHumanVariant.PALE_GREY_ORANGE,
                        PATH + "dale3.png");
            });

    @Override
    public void render(DaleHumanEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        poseStack.scale(0.95f, 0.95f, 0.95f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
