package net.jukoz.me.entity.orcs.misties;

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

public class MistyOrcRenderer extends BipedEntityRenderer<MistyOrcEntity, MistyOrcModel<MistyOrcEntity>> {
    private static final String PATH = "textures/entities/orcs/misties/";

    public MistyOrcRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new MistyOrcModel<>(ctx.getPart(ModEntityModelLayers.ORC)), 0.5f);
        this.addFeature(new ArmorFeatureRenderer<>(this, new MistyOrcModel(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)),
                new MistyOrcModel(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)), ctx.getModelManager()));

    }

    @Override
    public Identifier getTexture(MistyOrcEntity entity) {
        return new Identifier(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    public static final Map<MistyOrcVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MistyOrcVariant.class), (resourceLocation) -> {
                resourceLocation.put(MistyOrcVariant.LIGHT_BROWN_RED,
                        PATH + "orc1.png");
                resourceLocation.put(MistyOrcVariant.PALE_BLUE_YELLOW,
                        PATH + "orc2.png");
                resourceLocation.put(MistyOrcVariant.PALE_GREY_ORANGE,
                        PATH + "orc3.png");
            });

    @Override
    public void render(MistyOrcEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        poseStack.scale(0.75f, 0.75f, 0.75f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
