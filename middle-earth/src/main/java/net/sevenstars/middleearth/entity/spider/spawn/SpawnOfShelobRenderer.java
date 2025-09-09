package net.sevenstars.middleearth.entity.spider.spawn;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;

public class SpawnOfShelobRenderer extends MobEntityRenderer<SpawnOfShelobEntity, SpawnOfShelobRenderState, SpawnOfShelobModel> {
    private static final String PATH = "textures/entities/spiders/";

    public SpawnOfShelobRenderer(EntityRendererFactory.Context context) {
        this(context, 0.75F, ModEntityModelLayers.SPAWN_OF_SHELOB);
    }

    @Override
    public SpawnOfShelobRenderState createRenderState() {
        return new SpawnOfShelobRenderState();
    }

    protected SpawnOfShelobRenderer(EntityRendererFactory.Context ctx, float shadowRadius, EntityModelLayer layer) {
        super(ctx, new SpawnOfShelobModel(ctx.getPart(layer)), shadowRadius);
    }

    @Override
    public Identifier getTexture(SpawnOfShelobRenderState state) {
        return state.spiderVariant.assetInfo().spawnOfShelob().texturePath();
    }

    public void updateRenderState(SpawnOfShelobEntity spawnofShelobEntity, SpawnOfShelobRenderState shelobiteScuttlerEntityRenderState, float f) {
        super.updateRenderState(spawnofShelobEntity, shelobiteScuttlerEntityRenderState, f);
        shelobiteScuttlerEntityRenderState.idleAnimationState.copyFrom(spawnofShelobEntity.idleAnimation);
        shelobiteScuttlerEntityRenderState.walkAnimationState.copyFrom(spawnofShelobEntity.walkingAnimation);
        shelobiteScuttlerEntityRenderState.biteAnimationState.copyFrom(spawnofShelobEntity.biteAnimation);
        shelobiteScuttlerEntityRenderState.blockAnimationState.copyFrom(spawnofShelobEntity.blockAnimation);
        shelobiteScuttlerEntityRenderState.pounceAnimationState.copyFrom(spawnofShelobEntity.pounceAnimation);
        shelobiteScuttlerEntityRenderState.timelineTicks = spawnofShelobEntity.getTimelineTicks();
        shelobiteScuttlerEntityRenderState.climbingTicks = spawnofShelobEntity.getClimbingTicks();
        shelobiteScuttlerEntityRenderState.leapingTicks = spawnofShelobEntity.getLeapingTicks();
        shelobiteScuttlerEntityRenderState.spiderVariant = spawnofShelobEntity.getVariant();
    }
}
