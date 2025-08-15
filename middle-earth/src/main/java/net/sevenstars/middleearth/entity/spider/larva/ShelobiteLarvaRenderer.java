package net.sevenstars.middleearth.entity.spider.larva;


import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerRenderState;

public class ShelobiteLarvaRenderer extends MobEntityRenderer<ShelobiteLarvaEntity, ShelobiteScuttlerRenderState, ShelobiteLarvaModel> {
    private static final String PATH = "textures/entities/spiders/";

    public ShelobiteLarvaRenderer(EntityRendererFactory.Context context) {
        this(context, 0.2F, ModEntityModelLayers.SHELOBITE_LARVA);
    }

    @Override
    public ShelobiteScuttlerRenderState createRenderState() {
        return new ShelobiteScuttlerRenderState();
    }

    protected ShelobiteLarvaRenderer(EntityRendererFactory.Context ctx, float shadowRadius, EntityModelLayer layer) {
        super(ctx, new ShelobiteLarvaModel(ctx.getPart(layer)), shadowRadius);
    }


    @Override
    public Identifier getTexture(ShelobiteScuttlerRenderState state) {
        return state.spiderVariant.assetInfo().larva().texturePath();
    }

    public void updateRenderState(ShelobiteLarvaEntity larvaEntity, ShelobiteScuttlerRenderState shelobiteScuttlerEntityRenderState, float f) {
        super.updateRenderState(larvaEntity, shelobiteScuttlerEntityRenderState, f);
        shelobiteScuttlerEntityRenderState.walkAnimationState.copyFrom(larvaEntity.walkingAnimation);
        shelobiteScuttlerEntityRenderState.biteAnimationState.copyFrom(larvaEntity.biteAnimation);
        shelobiteScuttlerEntityRenderState.spiderVariant = larvaEntity.getVariant();
    }

    @Override
    public void render(ShelobiteScuttlerRenderState livingEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(livingEntityRenderState, matrixStack, vertexConsumerProvider, i);
    }
}
