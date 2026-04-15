package net.sevenstars.middleearth.entity.barrow_wights;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class BarrowWightEntityRenderer extends MobEntityRenderer<BarrowWightEntity, LivingEntityRenderState, BarrowWightModel> {
    private static final String PATH = "textures/entities/barrow_wights/barrow_wight0.png";

    public BarrowWightEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BarrowWightModel(ctx.getPart(EntityModelLayersME.BARROW_WIGHT)), 0.5f);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
    @Override
    public Identifier getTexture(LivingEntityRenderState state) {
        return Identifier.of(MiddleEarth.MOD_ID, PATH);
    }
}
