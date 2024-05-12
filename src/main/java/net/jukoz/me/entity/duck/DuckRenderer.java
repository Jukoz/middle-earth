package net.jukoz.me.entity.duck;

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

public class DuckRenderer extends MobEntityRenderer<DuckEntity, DuckModel<DuckEntity>> {
    private static final String PATH = "textures/entities/duck/";
    private static final float SIZE = 1f;

    public DuckRenderer(EntityRendererFactory.Context context) {
        this(context, 0.35F, ModEntityModelLayers.DUCK);
    }

    protected DuckRenderer(EntityRendererFactory.Context ctx, float shadowRadius, EntityModelLayer layer) {
        super(ctx, new DuckModel(ctx.getPart(layer)), shadowRadius);
    }

    protected float getLyingAngle(DuckEntity duckEntity) {
        return 180.0F;
    }

    public static final Map<DuckVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(DuckVariant.class), (resourceLocation) -> {
                resourceLocation.put(DuckVariant.GRAY,
                        PATH + "duck1.png");
                resourceLocation.put(DuckVariant.BROWN,
                        PATH + "duck2.png");
            });

    public Identifier getTexture(DuckEntity entity) {
        return new Identifier(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    @Override
    public void render(DuckEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
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
