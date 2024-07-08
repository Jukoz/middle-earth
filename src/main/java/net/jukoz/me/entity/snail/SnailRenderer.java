package net.jukoz.me.entity.snail;

import com.google.common.collect.Maps;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.crab.CrabModel;
import net.jukoz.me.entity.crab.CrabVariant;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class SnailRenderer extends MobEntityRenderer<SnailEntity, SnailModel<SnailEntity>> {

    private static final String PATH = "textures/entities/snails/";

    public SnailRenderer(EntityRendererFactory.Context context) {
        this(context, 0.2F, ModEntityModelLayers.SNAIL);
    }

    protected SnailRenderer(EntityRendererFactory.Context ctx, float shadowRadius, EntityModelLayer layer) {
        super(ctx, new SnailModel(ctx.getPart(layer)), shadowRadius);
    }

    public static final Map<SnailVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(SnailVariant.class), (resourceLocation) -> {
                resourceLocation.put(SnailVariant.WHITE,
                        PATH + "snail1.png");
                resourceLocation.put(SnailVariant.GREEN,
                        PATH + "snail2.png");
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
