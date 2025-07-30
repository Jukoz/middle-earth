package net.sevenstars.middleearth.entity.spider.larva;


import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerEntity;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerModel;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerRenderState;

public class ShelobiteLarvaRenderer extends MobEntityRenderer<ShelobiteLarvaEntity, ShelobiteScuttlerRenderState, ShelobiteScuttlerModel> {
    private static final String PATH = "textures/entities/spiders/";

    public ShelobiteLarvaRenderer(EntityRendererFactory.Context context) {
        this(context, 0.2F, ModEntityModelLayers.SPIDER);
    }

    @Override
    public ShelobiteScuttlerRenderState createRenderState() {
        return new ShelobiteScuttlerRenderState();
    }

    protected ShelobiteLarvaRenderer(EntityRendererFactory.Context ctx, float shadowRadius, EntityModelLayer layer) {
        super(ctx, new ShelobiteScuttlerModel(ctx.getPart(layer)), shadowRadius);
    }


    @Override
    public Identifier getTexture(ShelobiteScuttlerRenderState state) {
        return Identifier.of(MiddleEarth.MOD_ID, PATH + "mirkwood_shelobite_scuttler.png");
    }

    public void updateRenderState(ShelobiteLarvaEntity larvaEntity, ShelobiteScuttlerRenderState shelobiteScuttlerEntityRenderState, float f) {
        super.updateRenderState(larvaEntity, shelobiteScuttlerEntityRenderState, f);
        shelobiteScuttlerEntityRenderState.idleAnimationState.copyFrom(larvaEntity.idleAnimation);
        shelobiteScuttlerEntityRenderState.walkAnimationState.copyFrom(larvaEntity.walkingAnimation);
        shelobiteScuttlerEntityRenderState.biteAnimationState.copyFrom(larvaEntity.biteAnimation);
    }

    @Override
    public void render(ShelobiteScuttlerRenderState livingEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.scale(0.4f, 0.4f, 0.4f);
        matrixStack.pop();
        super.render(livingEntityRenderState, matrixStack, vertexConsumerProvider, i);
    }
}
