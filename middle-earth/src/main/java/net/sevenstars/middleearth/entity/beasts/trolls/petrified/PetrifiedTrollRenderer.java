package net.sevenstars.middleearth.entity.beasts.trolls.petrified;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

public class PetrifiedTrollRenderer extends MobRenderer<PetrifiedTrollEntity, PetrifiedTrollModel> {
    private static final String PATH = "textures/entities/trolls/stone/";

    public PetrifiedTrollRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new PetrifiedTrollModel(ctx.bakeLayer(EntityModelLayersME.PETRIFIED_TROLL)), 0.0f);
    }

    @Override
    public ResourceLocation getTextureLocation(PetrifiedTrollEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "petrified_stone_troll.png");
    }
}
