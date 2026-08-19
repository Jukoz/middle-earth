package net.sevenstars.middleearth.entity.beasts.trolls.stone;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

public class StoneTrollRenderer extends MobRenderer<StoneTrollEntity, StoneTrollModel> {
    private static final String PATH = "textures/entities/trolls/stone/stone_troll1.png";

    public StoneTrollRenderer(EntityRendererProvider.Context context) {
        super(context, new StoneTrollModel(context.bakeLayer(EntityModelLayersME.STONE_TROLL)), 1.1f);
    }

    @Override
    public ResourceLocation getTextureLocation(StoneTrollEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH);
    }
}
