package net.jukoz.me.entity.snail;

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

public class SnailRenderer extends MobEntityRenderer<SnailEntity, SnailModel> {

    private static final String PATH = "textures/entities/snails/";

    public SnailRenderer(EntityRendererFactory.Context context) {
        this(context, 0.2F, ModEntityModelLayers.SNAIL);
    }

    protected SnailRenderer(EntityRendererFactory.Context ctx, float shadowRadius, EntityModelLayer layer) {
        super(ctx, new SnailModel(ctx.getPart(layer)), shadowRadius);
    }

    public static final Map<SnailVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(SnailVariant.class), (map) -> {
                map.put(SnailVariant.GREEN,
                        PATH + "snail_green.png");
                map.put(SnailVariant.PALE_GREEN,
                        PATH + "snail_pale_green.png");
                map.put(SnailVariant.BROWN,
                        PATH + "snail_brown.png");
                map.put(SnailVariant.GRAY,
                        PATH + "snail_gray.png");

            });

    @Override
    public Identifier getTexture(SnailEntity entity) {
        return Identifier.of(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    @Override
    public void render(SnailEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
