package net.sevenstars.middleearth.entity.trader;

import com.google.common.collect.Maps;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.of_beasts_and_wild_things.entity.model.EntityModelLayersWT;
import net.sevenstars.of_beasts_and_wild_things.entity.snail.SnailEntityModel;
import net.sevenstars.of_beasts_and_wild_things.entity.snail.SnailEntityRenderState;
import net.sevenstars.of_beasts_and_wild_things.entity.snail.SnailEntityVariant;

import java.util.Map;

public class SnailTraderRenderer extends MobEntityRenderer<SnailTrader, SnailEntityRenderState, SnailEntityModel> {
    private static final String PATH = "textures/entities/snails/";

    public SnailTraderRenderer(EntityRendererFactory.Context context) {
        super(context, new SnailEntityModel(context.getPart(EntityModelLayersWT.SNAIL)), 0.2f);
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
        return Identifier.of(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(state.variant));
    }

    public SnailEntityRenderState createRenderState() {
        return new SnailEntityRenderState();
    }

    public void updateRenderState(SnailTrader snailEntity, SnailEntityRenderState snailEntityRenderState, float f) {
        super.updateRenderState(snailEntity, snailEntityRenderState, f);
        snailEntityRenderState.variant = snailEntity.getVariant();
    }
}
