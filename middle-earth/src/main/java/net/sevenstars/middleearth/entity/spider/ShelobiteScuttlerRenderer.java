package net.sevenstars.middleearth.entity.spider;


import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;

public class ShelobiteScuttlerRenderer extends MobEntityRenderer<ShelobiteScuttlerEntity, ShelobiteScuttlerRenderState, ShelobiteScuttlerModel> {
    private static final String PATH = "textures/entities/spiders/";

    public ShelobiteScuttlerRenderer(EntityRendererFactory.Context context) {
        this(context, 0.45F, ModEntityModelLayers.SPIDER);
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
        return Identifier.of(MiddleEarth.MOD_ID, PATH + "mirkwood_shelobite_scuttler.png");
    }

    public void updateRenderState(ShelobiteScuttlerEntity shelobiteScuttlerEntity, ShelobiteScuttlerRenderState shelobiteScuttlerEntityRenderState, float f) {
        super.updateRenderState(shelobiteScuttlerEntity, shelobiteScuttlerEntityRenderState, f);
        shelobiteScuttlerEntityRenderState.idleAnimationState.copyFrom(shelobiteScuttlerEntity.idleAnimation);
        shelobiteScuttlerEntityRenderState.walkAnimationState.copyFrom(shelobiteScuttlerEntity.walkingAnimation);
        shelobiteScuttlerEntityRenderState.biteAnimationState.copyFrom(shelobiteScuttlerEntity.biteAnimation);
        shelobiteScuttlerEntityRenderState.pounceAnimationState.copyFrom(shelobiteScuttlerEntity.pounceAnimation);
    }
}
