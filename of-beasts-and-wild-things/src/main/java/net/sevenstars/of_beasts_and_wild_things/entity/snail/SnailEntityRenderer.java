package net.sevenstars.of_beasts_and_wild_things.entity.snail;

import com.google.common.collect.Maps;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.entity.model.ModEntityModelLayers;

import java.util.Map;

public class SnailEntityRenderer extends MobEntityRenderer<SnailEntity, SnailEntityRenderState, SnailEntityModel> {
    private static final String PATH = "textures/entity/snail/";

    public SnailEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new SnailEntityModel(context.getPart(ModEntityModelLayers.SNAIL)), 0.2f);
    }

    public static final Map<SnailEntityVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(SnailEntityVariant.class), (map) -> {
                map.put(SnailEntityVariant.GREEN,
                        PATH + "snail_green.png");
                map.put(SnailEntityVariant.PALE_GREEN,
                        PATH + "snail_pale_green.png");
                map.put(SnailEntityVariant.BROWN,
                        PATH + "snail_brown.png");
                map.put(SnailEntityVariant.GRAY,
                        PATH + "snail_gray.png");

            });

    @Override
    public Identifier getTexture(SnailEntityRenderState state) {
        return Identifier.of(OfBeastsAndWildThings.MOD_ID, LOCATION_BY_VARIANT.get(state.variant));
    }

    @Override
    public SnailEntityRenderState createRenderState() {
        return new SnailEntityRenderState();
    }

    public void updateRenderState(SnailEntity snailEntity, SnailEntityRenderState snailEntityRenderState, float f) {
        super.updateRenderState(snailEntity, snailEntityRenderState, f);
        snailEntityRenderState.variant = snailEntity.getVariant();
    }
}
