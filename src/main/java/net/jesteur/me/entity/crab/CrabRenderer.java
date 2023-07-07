package net.jesteur.me.entity.crab;

import com.google.common.collect.Maps;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class CrabRenderer extends MobEntityRenderer<CrabEntity, CrabModel> {
    private static final String PATH = "textures/entities/crabs/";
    private static final float SIZE = 1f;

    public CrabRenderer(EntityRendererFactory.Context context) {
        this(context, 0.35F, ModEntityModelLayers.CRAB);
    }

    protected CrabRenderer(EntityRendererFactory.Context ctx, float shadowRadius, EntityModelLayer layer) {
        super(ctx, new CrabModel(ctx.getPart(layer)), shadowRadius);
    }

    protected float getLyingAngle(CrabEntity crabEntity) {
        return 180.0F;
    }

    public static final Map<CrabVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(CrabVariant.class), (resourceLocation) -> {
                resourceLocation.put(CrabVariant.ORANGE,
                        PATH + "crab1.png");
                resourceLocation.put(CrabVariant.DARK_BROWN,
                        PATH + "crab2.png");
            });

    public Identifier getTexture(CrabEntity entity) {
        return new Identifier(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    @Override
    public void render(CrabEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isBaby()) {
            float initialBabySize = (SIZE / 4);
            float size = initialBabySize + ((SIZE - initialBabySize) / 24000) * entity.age;
            poseStack.scale(size, size, size);
        } else {
            poseStack.scale(SIZE, SIZE, SIZE);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
