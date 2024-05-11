package net.jukoz.me.entity.goose;

import com.google.common.collect.Maps;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

import java.util.Map;

public class GooseRenderer extends MobEntityRenderer<GooseEntity, GooseModel<GooseEntity>> {
    private static final String PATH = "textures/entities/goose/";
    private static final float SIZE = 1f;

    public GooseRenderer(EntityRendererFactory.Context context) {
        this(context, 0.35F, ModEntityModelLayers.GOOSE);
        this.addFeature(new GooseHeldItemFeatureRenderer(this, context.getHeldItemRenderer()));
    }

    protected GooseRenderer(EntityRendererFactory.Context ctx, float shadowRadius, EntityModelLayer layer) {
        super(ctx, new GooseModel(ctx.getPart(layer)), shadowRadius);
    }

    protected float getLyingAngle(GooseEntity gooseEntity) {
        return 180.0F;
    }

    public static final Map<GooseVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(GooseVariant.class), (resourceLocation) -> {
                resourceLocation.put(GooseVariant.LIGHT_GRAY,
                        PATH + "goose1.png");
                resourceLocation.put(GooseVariant.GRAY,
                        PATH + "goose2.png");
                resourceLocation.put(GooseVariant.WHITE,
                        PATH + "goose3.png");
            });

    public Identifier getTexture(GooseEntity entity) {
        return new Identifier(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    @Override
    public void render(GooseEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isBaby()) {
            float initialBabySize = (SIZE / 2);
            float size = initialBabySize + ((SIZE - initialBabySize) / 24000) * entity.age;
            poseStack.scale(size, size, size);
        } else {
            poseStack.scale(SIZE, SIZE, SIZE);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    protected float getAnimationProgress(ChickenEntity chickenEntity, float f) {
        float g = MathHelper.lerp(f, chickenEntity.prevFlapProgress, chickenEntity.flapProgress);
        float h = MathHelper.lerp(f, chickenEntity.prevMaxWingDeviation, chickenEntity.maxWingDeviation);
        return (MathHelper.sin(g) + 1.0F) * h;
    }
}
