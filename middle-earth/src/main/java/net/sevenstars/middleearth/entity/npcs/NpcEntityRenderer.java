package net.sevenstars.middleearth.entity.npcs;

import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import net.sevenstars.middleearth.entity.npcs.features.beards.NpcEntityBeardFeatureRenderer;

public class NpcEntityRenderer extends BipedEntityRenderer<NpcEntity, NpcEntityRenderState, NpcEntityModel> {
    private static final String PATH = "textures/entities/npcs/skins/";

    public NpcEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new NpcEntityModel(context.getPart(ModEntityModelLayers.NPC)), 0.7f);

        this.addFeature(new NpcEntityBeardFeatureRenderer(this, context.getEntityModels()));
    }

    @Override
    public Identifier getTexture(NpcEntityRenderState state) {
        return Identifier.of(MiddleEarth.MOD_ID, PATH + "common_pale.png");
    }

    // region RenderState
    @Override
    public NpcEntityRenderState createRenderState() {
        return new NpcEntityRenderState();
    }

    @Override
    public void updateRenderState(NpcEntity npcEntity, NpcEntityRenderState npcEntityRenderState, float f) {
        super.updateRenderState(npcEntity, npcEntityRenderState, f);
        npcEntityRenderState.beardType = npcEntity.getBeardType();
    }

    // endregion
}
