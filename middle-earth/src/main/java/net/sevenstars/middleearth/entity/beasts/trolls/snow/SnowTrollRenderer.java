package net.sevenstars.middleearth.entity.beasts.trolls.snow;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

public class SnowTrollRenderer extends MobRenderer<SnowTrollEntity, SnowTrollModel> {
    private static final String PATH = "textures/entities/trolls/snow/snow_troll1.png";

    public SnowTrollRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SnowTrollModel(ctx.bakeLayer(EntityModelLayersME.SNOW_TROLL)), 1.1f);
    }

    @Override
    public ResourceLocation getTextureLocation(SnowTrollEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH);
    }
}
