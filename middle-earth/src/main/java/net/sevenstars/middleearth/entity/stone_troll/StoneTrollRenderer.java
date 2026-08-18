package net.sevenstars.middleearth.entity.stone_troll;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.trolls.TrollEntityRenderState;
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanEntity;
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanEntityRenderState;

public class StoneTrollRenderer extends MobEntityRenderer<StoneTrollEntity, StoneTrollRenderState, StoneTrollModel> {
    private static final String PATH = "textures/entities/trolls/stone/stone_troll_a.png";

    public StoneTrollRenderer(EntityRendererFactory.Context context) {
        super(context, new StoneTrollModel(context.getPart(EntityModelLayersME.STONE_TROLL)), 1.1f);
    }

    @Override
    public StoneTrollRenderState createRenderState() {
        return new StoneTrollRenderState();
    }
    @Override
    public Identifier getTexture(StoneTrollRenderState state) {
        return Identifier.of(MiddleEarth.MOD_ID, PATH);
    }

    @Override
    public void updateRenderState(StoneTrollEntity troll, StoneTrollRenderState stoneTrollRenderState, float f) {
        super.updateRenderState(troll, stoneTrollRenderState, f);
        stoneTrollRenderState.sleepingAnimationState = troll.sleepingAnimationState;
        stoneTrollRenderState.lieDownAnimationState = troll.lieDownAnimationState;
        stoneTrollRenderState.sitUpAnimationState = troll.sitUpAnimationState;
        stoneTrollRenderState.sitDownAnimationState = troll.sitDownAnimationState;
        stoneTrollRenderState.standUpAnimationState = troll.standUpAnimationState;
    }
}
