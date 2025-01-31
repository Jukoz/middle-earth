package net.sevenstars.middleearth.entity.beasts.trolls.snow;

import com.google.common.collect.Maps;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.trolls.TrollEntityRenderState;
import net.sevenstars.middleearth.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class SnowTrollRenderer extends MobEntityRenderer<SnowTrollEntity, TrollEntityRenderState, SnowTrollModel> {
    private static final String PATH = "textures/entities/trolls/snow/snow_troll1.png";

    public SnowTrollRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SnowTrollModel(ctx.getPart(ModEntityModelLayers.SNOW_TROLL)), 1.1f);
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
