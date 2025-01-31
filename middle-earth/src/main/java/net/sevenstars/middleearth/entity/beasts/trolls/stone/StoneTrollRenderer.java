package net.sevenstars.middleearth.entity.beasts.trolls.stone;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.trolls.TrollEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.trolls.snow.SnowTrollEntity;
import net.sevenstars.middleearth.entity.model.ModEntityModelLayers;

public class StoneTrollRenderer extends MobEntityRenderer<SnowTrollEntity, TrollEntityRenderState, StoneTrollModel> {
    private static final String PATH = "textures/entities/trolls/stone/";

    public StoneTrollRenderer(EntityRendererFactory.Context context) {
        super(context, new StoneTrollModel(context.getPart(ModEntityModelLayers.STONE_TROLL)), 1.1f);
    }

    @Override
    public TrollEntityRenderState createRenderState() {
        return new TrollEntityRenderState();
    }
    @Override
    public Identifier getTexture(TrollEntityRenderState state) {
        return Identifier.of(MiddleEarth.MOD_ID, PATH);
    }
}
