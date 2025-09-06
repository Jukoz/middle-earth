package net.sevenstars.middleearth.entity.beasts.great_horn;

import net.minecraft.client.render.entity.AgeableMobEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;

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

    @Override
    public Identifier getTexture(GreatHornEntityRenderState state) {
        return Identifier.of(MiddleEarth.MOD_ID, PATH + "white_great_horn.png");
    }

    @Override
    public void updateRenderState(GreatHornEntity greatHornEntity, GreatHornEntityRenderState state, float f) {
        super.updateRenderState(greatHornEntity, state, f);

        state.idleAnimationState.copyFrom(greatHornEntity.idleAnimationState);
        state.earWiggleAnimationState.copyFrom(greatHornEntity.earWigglingAnimationState);
        state.gallopAnimationState.copyFrom(greatHornEntity.gallopAnimationState);
        state.bowAnimationState.copyFrom(greatHornEntity.bowAnimationState);
        state.saddle = greatHornEntity.hasSaddleEquipped();
        state.hasRider = greatHornEntity.hasPlayerRider();
    }
}
