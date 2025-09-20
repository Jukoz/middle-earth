package net.sevenstars.middleearth.entity.beasts.great_horn;

import com.google.common.collect.Maps;
import net.minecraft.client.render.entity.AgeableMobEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;

import java.util.Map;

public class GreatHornRenderer extends AgeableMobEntityRenderer<GreatHornEntity, GreatHornEntityRenderState, GreatHornModel> {
    private static final String PATH = "textures/entities/great_horn/";
    private static final float SIZE = 1f;

    public GreatHornRenderer(EntityRendererFactory.Context context) {
        super(context, new GreatHornModel(context.getPart(ModEntityModelLayers.GREAT_HORN)), new GreatHornModel(context.getPart(ModEntityModelLayers.GREAT_HORN_BABY)), 0.95f);
    }

    protected float getShadowRadius(GreatHornEntityRenderState greatHornEntityRenderState) {
        float f = super.getShadowRadius(greatHornEntityRenderState);
        return greatHornEntityRenderState.baby ? f * 0.8F : f;
    }

    @Override
    public GreatHornEntityRenderState createRenderState() {
        return new GreatHornEntityRenderState();
    }

    public static final Map<GreatHornVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(GreatHornVariant.class), (map) -> {
                map.put(GreatHornVariant.BROWN,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "brown_great_horn.png"));
                map.put(GreatHornVariant.TEMPERATE,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "temperate_great_horn.png"));
                map.put(GreatHornVariant.COLD,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "cold_great_horn.png"));
                map.put(GreatHornVariant.WHITE,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "white_great_horn.png"));

            });

    @Override
    public Identifier getTexture(GreatHornEntityRenderState state) {
        return LOCATION_BY_VARIANT.get(state.variant);
    }

    @Override
    public void updateRenderState(GreatHornEntity greatHornEntity, GreatHornEntityRenderState state, float f) {
        super.updateRenderState(greatHornEntity, state, f);

        state.variant = greatHornEntity.getVariant();
        state.idleAnimationState.copyFrom(greatHornEntity.idleAnimationState);
        state.earWiggleAnimationState.copyFrom(greatHornEntity.earWigglingAnimationState);
        state.gallopAnimationState.copyFrom(greatHornEntity.gallopAnimationState);
        state.bowAnimationState.copyFrom(greatHornEntity.bowAnimationState);
        state.saddle = greatHornEntity.hasSaddleEquipped();
        state.hasRider = greatHornEntity.hasPlayerRider();
    }
}
