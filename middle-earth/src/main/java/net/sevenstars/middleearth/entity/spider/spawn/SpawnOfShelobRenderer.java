package net.sevenstars.middleearth.entity.spider.spawn;


import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerRenderState;

public class SpawnOfShelobRenderer extends MobEntityRenderer<SpawnOfShelobEntity, ShelobiteScuttlerRenderState, SpawnOfShelobModel> {
    private static final String PATH = "textures/entities/spiders/";

    public SpawnOfShelobRenderer(EntityRendererFactory.Context context) {
        this(context, 0.75F, ModEntityModelLayers.SPAWN_OF_SHELOB);
    }

    @Override
    public ShelobiteScuttlerRenderState createRenderState() {
        return new ShelobiteScuttlerRenderState();
    }

    protected SpawnOfShelobRenderer(EntityRendererFactory.Context ctx, float shadowRadius, EntityModelLayer layer) {
        super(ctx, new SpawnOfShelobModel(ctx.getPart(layer)), shadowRadius);
    }


    @Override
    public Identifier getTexture(ShelobiteScuttlerRenderState state) {
        return Identifier.of(MiddleEarth.MOD_ID, PATH + "mirkwood_shelobite_spawn.png");
    }

    public void updateRenderState(SpawnOfShelobEntity spawnofShelobEntity, ShelobiteScuttlerRenderState shelobiteScuttlerEntityRenderState, float f) {
        super.updateRenderState(spawnofShelobEntity, shelobiteScuttlerEntityRenderState, f);
        shelobiteScuttlerEntityRenderState.idleAnimationState.copyFrom(spawnofShelobEntity.idleAnimation);
        shelobiteScuttlerEntityRenderState.walkAnimationState.copyFrom(spawnofShelobEntity.walkingAnimation);
        shelobiteScuttlerEntityRenderState.biteAnimationState.copyFrom(spawnofShelobEntity.biteAnimation);
        //shelobiteScuttlerEntityRenderState.pounceAnimationState.copyFrom(shelobiteSpawnEntity.pounceAnimation);
    }
}
