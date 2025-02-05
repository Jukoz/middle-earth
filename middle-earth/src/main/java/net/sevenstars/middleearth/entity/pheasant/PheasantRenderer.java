package net.sevenstars.middleearth.entity.pheasant;

import com.google.common.collect.Maps;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class PheasantRenderer extends MobEntityRenderer<PheasantEntity, PheasantModel> {
    private static final String PATH = "textures/entities/pheasants/";
    private static final float SIZE = 1f;

    public PheasantRenderer(EntityRendererFactory.Context context) {
        super(context, new PheasantModel(context.getPart(ModEntityModelLayers.PHEASANT)), 0.35F);

    }

    public static final Map<PheasantVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(PheasantVariant.class), (map) -> {
                map.put(PheasantVariant.MALE,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "pheasant1m.png"));
                map.put(PheasantVariant.FEMALE,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "pheasant1f.png"));
            });

    @Override
    public Identifier getTexture(PheasantEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    public void render(PheasantEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        if(entity.isBaby()) {
            float initialBabySize = (SIZE / 2);
            float size = initialBabySize + ((SIZE - initialBabySize) / 24000) * entity.age;
            poseStack.scale(size, size, size);
        } else {
            poseStack.scale(SIZE, SIZE, SIZE);
        }
        poseStack.scale(1, 1, 1);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
