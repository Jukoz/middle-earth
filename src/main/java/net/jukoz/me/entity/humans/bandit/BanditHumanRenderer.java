package net.jukoz.me.entity.humans.bandit;

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

public class BanditHumanRenderer extends BipedEntityRenderer<BanditHumanEntity, BanditHumanModel<BanditHumanEntity>> {
    private static final String PATH = "textures/entities/humans/bandits/";

    public BanditHumanRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BanditHumanModel<>(ctx.getPart(ModEntityModelLayers.HUMAN)), 0.5f);
        this.addFeature(new ArmorFeatureRenderer<>(this, new BanditHumanModel(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)),
                new BanditHumanModel(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)), ctx.getModelManager()));

    }

    @Override
    public Identifier getTexture(BanditHumanEntity entity) {
        return new Identifier(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    public static final Map<BanditHumanVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(BanditHumanVariant.class), (resourceLocation) -> {
                resourceLocation.put(BanditHumanVariant.LIGHT_BROWN_RED,
                        PATH + "bandit1.png");
                resourceLocation.put(BanditHumanVariant.PALE_BLUE_YELLOW,
                        PATH + "bandit2.png");
                resourceLocation.put(BanditHumanVariant.PALE_GREY_ORANGE,
                        PATH + "bandit3.png");
            });

    @Override
    public void render(BanditHumanEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        poseStack.scale(1.0f, 1.0f, 1.0f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
