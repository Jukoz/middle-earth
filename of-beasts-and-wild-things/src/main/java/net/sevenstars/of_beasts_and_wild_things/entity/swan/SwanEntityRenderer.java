package net.sevenstars.of_beasts_and_wild_things.entity.swan;

import com.google.common.collect.Maps;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.ModMemoryModules;
import net.sevenstars.of_beasts_and_wild_things.entity.deer.DeerEntityModel;
import net.sevenstars.of_beasts_and_wild_things.entity.model.ModEntityModelLayers;

import java.util.Map;
import java.util.Optional;

public class SwanEntityRenderer  extends MobEntityRenderer<SwanEntity, SwanEntityRenderState, SwanEntityModel> {
    private static final String PATH = "textures/entity/swan/";

    public SwanEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new SwanEntityModel(context.getPart(ModEntityModelLayers.SWAN)), 0.5f);
    }

    public static final Map<SwanEntityVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(SwanEntityVariant.class), (map) -> {
                map.put(SwanEntityVariant.WHITE,
                        PATH + "swan_white.png");
                map.put(SwanEntityVariant.BLACK,
                        PATH + "swan_black.png");
                map.put(SwanEntityVariant.TRUMPETER,
                        PATH + "swan_trumpeter.png");
                map.put(SwanEntityVariant.WHOOPER,
                        PATH + "swan_whooper.png");
            });

    @Override
    public Identifier getTexture(SwanEntityRenderState state) {
        return Identifier.of(OfBeastsAndWildThings.MOD_ID, LOCATION_BY_VARIANT.get(state.variant));
    }

    @Override
    public SwanEntityRenderState createRenderState() {
        return new SwanEntityRenderState();
    }

    @Override
    public void updateRenderState(SwanEntity swan, SwanEntityRenderState swanEntityRenderState, float f) {
        super.updateRenderState(swan, swanEntityRenderState, f);
        swanEntityRenderState.variant = swan.getVariant();
        swanEntityRenderState.sleepingAnimationState = swan.sleepingAnimationState;
        swanEntityRenderState.swimmingAnimationState = swan.swimmingAnimationState;
        swanEntityRenderState.intimidateAnimationState = swan.intimidateAnimationState;
        swanEntityRenderState.eatAnimationState = swan.eatAnimationState;
    }
}
