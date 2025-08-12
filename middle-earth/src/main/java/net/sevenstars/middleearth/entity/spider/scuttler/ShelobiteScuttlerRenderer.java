package net.sevenstars.middleearth.entity.spider.scuttler;


import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;

public class ShelobiteScuttlerRenderer extends MobEntityRenderer<ShelobiteScuttlerEntity, ShelobiteScuttlerRenderState, ShelobiteScuttlerModel> {

    public ShelobiteScuttlerRenderer(EntityRendererFactory.Context context) {
        this(context, 0.45F, ModEntityModelLayers.SHELOBITE_SCUTTLER);
    }

    @Override
    public ShelobiteScuttlerRenderState createRenderState() {
        return new ShelobiteScuttlerRenderState();
    }

    protected ShelobiteScuttlerRenderer(EntityRendererFactory.Context ctx, float shadowRadius, EntityModelLayer layer) {
        super(ctx, new ShelobiteScuttlerModel(ctx.getPart(layer)), shadowRadius);
    }


    @Override
    public Identifier getTexture(ShelobiteScuttlerRenderState state) {
        return state.spiderVariant.assetInfo().texturePath();
    }

    public void updateRenderState(ShelobiteScuttlerEntity shelobiteScuttlerEntity, ShelobiteScuttlerRenderState shelobiteScuttlerEntityRenderState, float f) {
        super.updateRenderState(shelobiteScuttlerEntity, shelobiteScuttlerEntityRenderState, f);
        shelobiteScuttlerEntityRenderState.idleAnimationState.copyFrom(shelobiteScuttlerEntity.idleAnimation);
        shelobiteScuttlerEntityRenderState.walkAnimationState.copyFrom(shelobiteScuttlerEntity.walkingAnimation);
        shelobiteScuttlerEntityRenderState.biteAnimationState.copyFrom(shelobiteScuttlerEntity.biteAnimation);
        shelobiteScuttlerEntityRenderState.pounceAnimationState.copyFrom(shelobiteScuttlerEntity.pounceAnimation);
        shelobiteScuttlerEntityRenderState.climbingTicks = shelobiteScuttlerEntity.getClimbingTicks();
        shelobiteScuttlerEntityRenderState.leapingTicks = shelobiteScuttlerEntity.getLeapingTicks();
        shelobiteScuttlerEntityRenderState.spiderVariant = shelobiteScuttlerEntity.getVariant();
    }
}
