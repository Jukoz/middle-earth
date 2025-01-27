package net.sevenstars.middleearth.entity.uruks.mordor;

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

public class MordorBlackUrukRenderer extends BipedEntityRenderer<MordorBlackUrukEntity, MordorBlackUrukModel<MordorBlackUrukEntity>> {
    private static final String PATH = "textures/entities/uruks/mordor/";

    public MordorBlackUrukRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new MordorBlackUrukModel<>(ctx.getPart(ModEntityModelLayers.URUK)), 0.5f);
        this.addFeature(new ArmorFeatureRenderer<>(this, new MordorBlackUrukModel(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)),
                new MordorBlackUrukModel(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)), ctx.getModelManager()));
    }

    @Override
    public Identifier getTexture(MordorBlackUrukEntity entity) {
        return Identifier.of(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    public static final Map<MordorBlackUrukVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MordorBlackUrukVariant.class), (resourceLocation) -> {
                resourceLocation.put(MordorBlackUrukVariant.LIGHT_BROWN_RED,
                        PATH + "uruk1.png");
                resourceLocation.put(MordorBlackUrukVariant.PALE_BLUE_YELLOW,
                        PATH + "uruk2.png");
                resourceLocation.put(MordorBlackUrukVariant.PALE_GREY_ORANGE,
                        PATH + "uruk3.png");
            });

    @Override
    public void render(MordorBlackUrukEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        poseStack.scale(0.92f, 0.92f, 0.92f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
