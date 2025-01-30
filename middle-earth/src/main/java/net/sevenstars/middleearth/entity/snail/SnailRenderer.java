package net.sevenstars.middleearth.entity.snail;

import com.google.common.collect.Maps;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class SnailRenderer extends MobEntityRenderer<SnailEntity, SnailEntityRenderState, SnailEntityModel> {

    private static final String PATH = "textures/entities/snails/";

    public SnailRenderer(EntityRendererFactory.Context context) {
        super(context, new SnailEntityModel(context.getPart(ModEntityModelLayers.SNAIL)), 0.2f);
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
    public Identifier getTexture(SnailEntityRenderState state) {
        return Identifier.of(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(state.variant));
    }

    public SnailEntityRenderState createRenderState() {
        return new SnailEntityRenderState();
    }

    public void updateRenderState(SnailEntity snailEntity, SnailEntityRenderState snailEntityRenderState, float f) {
        super.updateRenderState(snailEntity, snailEntityRenderState, f);
        snailEntityRenderState.variant = snailEntity.getVariant();
    }
}
