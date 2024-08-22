package net.jukoz.me.entity.swan;

import com.google.common.collect.Maps;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.jukoz.me.entity.pheasant.PheasantEntity;
import net.jukoz.me.entity.pheasant.PheasantModel;
import net.jukoz.me.entity.pheasant.PheasantVariant;
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

public class SwanRenderer extends MobEntityRenderer<SwanEntity, SwanModel> {
    private static final String PATH = "textures/entities/swan/";
    private static final float SIZE = 1f;

    public SwanRenderer(EntityRendererFactory.Context context) {
        this(context, 0.35F, ModEntityModelLayers.SWAN);
    }

    protected SwanRenderer(EntityRendererFactory.Context ctx, float shadowRadius, EntityModelLayer layer) {
        super(ctx, new SwanModel(ctx.getPart(layer)), shadowRadius);
    }

    protected float getLyingAngle(SwanEntity swanEntity) {
        return 180.0F;
    }

    public static final Map<SwanVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(SwanVariant.class), (map) -> {
                map.put(SwanVariant.WHITE,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "white_swan.png"));
                map.put(SwanVariant.BLACK,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "black_swan.png"));
                map.put(SwanVariant.TRUMPETER,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "trumpeter_swan.png"));
                map.put(SwanVariant.WHOOPER,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "whooper_swan.png"));
            });

    @Override
    public Identifier getTexture(SwanEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public void render(SwanEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
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
}
