package net.sevenstars.middleearth.entity.beasts.great_horn;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntityRenderState;

public class GreatHornRenderer extends MobEntityRenderer<GreatHornEntity, GreatHornEntityRenderState, GreatHornModel> {
    private static final String PATH = "textures/entities/great_horn/";
    private static final float SIZE = 1f;

    public GreatHornRenderer(EntityRendererFactory.Context context) {
        super(context, new GreatHornModel(context.getPart(ModEntityModelLayers.GREAT_HORN)), 0.9f);
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
    }
}
