package net.jukoz.me.entity.balrog;

import com.google.common.collect.Maps;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class BalrogRenderer extends MobEntityRenderer<BalrogEntity, BalrogModel<BalrogEntity>> {
    private static final String PATH = "textures/entities/balrog/";
    private static final float SIZE = 2f;

    public BalrogRenderer(EntityRendererFactory.Context context) {
        this(context, 0.35F, ModEntityModelLayers.BALROG);
    }

    protected BalrogRenderer(EntityRendererFactory.Context ctx, float shadowRadius, EntityModelLayer layer) {
        super(ctx, new BalrogModel(ctx.getPart(layer)), shadowRadius);
    }

    protected float getLyingAngle(BalrogEntity balrogEntity) {
        return 180.0F;
    }

    public static final Map<BalrogVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(BalrogVariant.class), (resourceLocation) -> {
                resourceLocation.put(BalrogVariant.BASE,
                        PATH + "balrog1.png");
            });

    public Identifier getTexture(BalrogEntity entity) {
        return new Identifier(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    @Override
    public void render(BalrogEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.scale(SIZE, SIZE, SIZE);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
