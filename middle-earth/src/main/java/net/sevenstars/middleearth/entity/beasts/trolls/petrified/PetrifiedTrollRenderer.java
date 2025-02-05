package net.sevenstars.middleearth.entity.beasts.trolls.petrified;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class PetrifiedTrollRenderer extends MobEntityRenderer<PetrifiedTrollEntity, PetrifiedTrollModel> {
    private static final String PATH = "textures/entities/trolls/stone/";

    public PetrifiedTrollRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PetrifiedTrollModel(ctx.getPart(ModEntityModelLayers.PETRIFIED_TROLL)), 0.0f);
    }

    @Override
    public Identifier getTexture(PetrifiedTrollEntity entity) {
        return Identifier.of(MiddleEarth.MOD_ID, PATH + "petrified_stone_troll.png");
    }
}
