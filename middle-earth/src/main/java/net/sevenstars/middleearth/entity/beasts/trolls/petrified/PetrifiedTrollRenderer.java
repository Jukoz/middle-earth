package net.sevenstars.middleearth.entity.beasts.trolls.petrified;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class PetrifiedTrollRenderer extends MobEntityRenderer<PetrifiedTrollEntity, LivingEntityRenderState, PetrifiedTrollModel> {
    private static final String PATH = "textures/entities/trolls/stone/";

    public PetrifiedTrollRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PetrifiedTrollModel(ctx.getPart(ModEntityModelLayers.PETRIFIED_TROLL)), 0.0f);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return null;
    }

    @Override
    public Identifier getTexture(LivingEntityRenderState state) {
        return Identifier.of(MiddleEarth.MOD_ID, PATH + "petrified_stone_troll.png");
    }
}
