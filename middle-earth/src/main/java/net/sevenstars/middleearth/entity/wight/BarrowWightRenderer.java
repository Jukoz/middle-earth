package net.sevenstars.middleearth.entity.wight;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

public class BarrowWightRenderer extends MobEntityRenderer<BarrowWightEntity, LivingEntityRenderState, BarrowWightModel> {
    private static final String PATH = "textures/entities/barrow_wights/noble.png";

    public BarrowWightRenderer(EntityRendererFactory.Context context) {
        this(context, 0.4F, EntityModelLayersME.BARROW_WIGHT);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    protected BarrowWightRenderer(EntityRendererFactory.Context ctx, float shadowRadius, EntityModelLayer layer) {
        super(ctx, new BarrowWightModel(ctx.getPart(layer)), shadowRadius);
    }

    @Override
    public Identifier getTexture(LivingEntityRenderState state) {
        return MiddleEarth.of(PATH);
    }

    public void updateRenderState(BarrowWightEntity larvaEntity, LivingEntityRenderState shelobiteScuttlerLivingEntityRenderState, float f) {
        super.updateRenderState(larvaEntity, shelobiteScuttlerLivingEntityRenderState, f);
    }

    @Override
    public void render(LivingEntityRenderState livingLivingEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(livingLivingEntityRenderState, matrixStack, vertexConsumerProvider, i);
    }
}
