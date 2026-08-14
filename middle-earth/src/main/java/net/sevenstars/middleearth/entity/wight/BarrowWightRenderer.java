package net.sevenstars.middleearth.entity.wight;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

public class BarrowWightRenderer extends MobEntityRenderer<BarrowWightEntity, BarrowWightRenderState, BarrowWightModel> {
    private static final String PATH = "textures/entities/barrow_wights/noble.png";

    public BarrowWightRenderer(EntityRendererFactory.Context context) {
        this(context, 0.4F, EntityModelLayersME.BARROW_WIGHT);
    }

    @Override
    public BarrowWightRenderState createRenderState() {
        return new BarrowWightRenderState();
    }

    protected BarrowWightRenderer(EntityRendererFactory.Context ctx, float shadowRadius, EntityModelLayer layer) {
        super(ctx, new BarrowWightModel(ctx.getPart(layer)), shadowRadius);
    }

    @Override
    public Identifier getTexture(BarrowWightRenderState state) {
        return MiddleEarth.of(PATH);
    }

    public void updateRenderState(BarrowWightEntity barrowWightEntity, BarrowWightRenderState barrowWightRenderState, float f) {
        super.updateRenderState(barrowWightEntity, barrowWightRenderState, f);
        barrowWightRenderState.idleAnimationState.copyFrom(barrowWightEntity.idleAnimation);
        barrowWightRenderState.walkAnimationState.copyFrom(barrowWightEntity.walkingAnimation);
        barrowWightRenderState.attackAnimationState.copyFrom(barrowWightEntity.attackAnimation);
        barrowWightRenderState.screamAnimationState.copyFrom(barrowWightEntity.screamAnimation);
        barrowWightRenderState.incantationAnimationState.copyFrom(barrowWightEntity.incantationAnimation);
    }

    @Override
    public void render(BarrowWightRenderState livingBarrowWightRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(livingBarrowWightRenderState, matrixStack, vertexConsumerProvider, i);
    }
}
