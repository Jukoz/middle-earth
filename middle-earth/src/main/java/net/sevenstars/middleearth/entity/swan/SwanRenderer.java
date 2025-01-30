package net.sevenstars.middleearth.entity.swan;

import com.google.common.collect.Maps;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class SwanRenderer extends MobEntityRenderer<SwanEntity, SwanEntityRenderState, SwanModel> {
    private static final String PATH = "textures/entities/swan/";

    public SwanRenderer(EntityRendererFactory.Context context) {
        this(context, 0.35F, ModEntityModelLayers.SWAN);
    }

    @Override
    public SwanEntityRenderState createRenderState() {
        return new SwanEntityRenderState();
    }

    protected SwanRenderer(EntityRendererFactory.Context ctx, float shadowRadius, EntityModelLayer layer) {
        super(ctx, new SwanModel(ctx.getPart(layer)), shadowRadius);
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
    public Identifier getTexture(SwanEntityRenderState state) {
        return LOCATION_BY_VARIANT.get(state.variant);
    }
}
