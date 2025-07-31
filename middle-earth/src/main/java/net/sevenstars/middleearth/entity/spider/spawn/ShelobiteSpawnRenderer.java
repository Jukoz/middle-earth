package net.sevenstars.middleearth.entity.spider.spawn;


import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerEntity;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerModel;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerRenderState;

public class ShelobiteSpawnRenderer extends MobEntityRenderer<ShelobiteSpawnEntity, ShelobiteScuttlerRenderState, ShelobiteScuttlerModel> {
    private static final String PATH = "textures/entities/spiders/";

    public ShelobiteSpawnRenderer(EntityRendererFactory.Context context) {
        this(context, 0.75F, ModEntityModelLayers.SHELOBITE_SCUTTLER);
    }

    @Override
    public ShelobiteScuttlerRenderState createRenderState() {
        return new ShelobiteScuttlerRenderState();
    }

    protected ShelobiteSpawnRenderer(EntityRendererFactory.Context ctx, float shadowRadius, EntityModelLayer layer) {
        super(ctx, new ShelobiteScuttlerModel(ctx.getPart(layer)), shadowRadius);
    }


    @Override
    public Identifier getTexture(ShelobiteScuttlerRenderState state) {
        return Identifier.of(MiddleEarth.MOD_ID, PATH + "mirkwood_shelobite_scuttler.png");
    }

    public void updateRenderState(ShelobiteSpawnEntity shelobiteSpawnEntity, ShelobiteScuttlerRenderState shelobiteScuttlerEntityRenderState, float f) {
        super.updateRenderState(shelobiteSpawnEntity, shelobiteScuttlerEntityRenderState, f);
        shelobiteScuttlerEntityRenderState.idleAnimationState.copyFrom(shelobiteSpawnEntity.idleAnimation);
        shelobiteScuttlerEntityRenderState.walkAnimationState.copyFrom(shelobiteSpawnEntity.walkingAnimation);
        shelobiteScuttlerEntityRenderState.biteAnimationState.copyFrom(shelobiteSpawnEntity.biteAnimation);
        shelobiteScuttlerEntityRenderState.pounceAnimationState.copyFrom(shelobiteSpawnEntity.pounceAnimation);
    }
}
