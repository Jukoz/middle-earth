package net.sevenstars.middleearth.entity.beasts.trolls.cave;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import net.sevenstars.middleearth.entity.beasts.trolls.TrollEntityRenderState;

public class CaveTrollRenderer extends MobEntityRenderer<CaveTrollEntity, TrollEntityRenderState, CaveTrollEntityModel> {
    private static final String PATH = "textures/entities/trolls/cave/cave_troll_green.png";
    public CaveTrollRenderer(EntityRendererFactory.Context context) {
        super(context, new CaveTrollEntityModel(context.getPart(ModEntityModelLayers.CAVE_TROLL)), 1.1f);
    }

    @Override
    public Identifier getTexture(TrollEntityRenderState state) {
        return Identifier.of(MiddleEarth.MOD_ID, PATH);
    }

    @Override
    public TrollEntityRenderState createRenderState() {
        return new TrollEntityRenderState();
    }

    @Override
    public void updateRenderState(CaveTrollEntity troll, TrollEntityRenderState state, float f) {
        super.updateRenderState(troll, state, f);

        state.sleepingAnimationState = troll.sleepingAnimationState;
    }
}
