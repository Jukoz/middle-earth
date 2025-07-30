package net.sevenstars.middleearth.entity.spider;


import com.google.common.collect.Maps;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;

import java.util.Map;

public class MirkwoodSpiderRenderer extends MobEntityRenderer<MirkwoodSpiderEntity, ShelobiteScuttlerRenderState, MirkwoodSpiderModel> {
    private static final String PATH = "textures/entities/spiders/";

    public MirkwoodSpiderRenderer(EntityRendererFactory.Context context) {
        this(context, 0.45F, ModEntityModelLayers.SPIDER);
    }

    @Override
    public ShelobiteScuttlerRenderState createRenderState() {
        return new ShelobiteScuttlerRenderState();
    }

    protected MirkwoodSpiderRenderer(EntityRendererFactory.Context ctx, float shadowRadius, EntityModelLayer layer) {
        super(ctx, new MirkwoodSpiderModel(ctx.getPart(layer)), shadowRadius);
    }


    @Override
    public Identifier getTexture(ShelobiteScuttlerRenderState state) {
        return Identifier.of(MiddleEarth.MOD_ID, PATH + "mirkwood_shelobite_scuttler.png");
    }

    public void updateRenderState(MirkwoodSpiderEntity shelobiteScuttlerEntity, ShelobiteScuttlerRenderState shelobiteScuttlerEntityRenderState, float f) {
        super.updateRenderState(shelobiteScuttlerEntity, shelobiteScuttlerEntityRenderState, f);
        shelobiteScuttlerEntityRenderState.idleAnimationState.copyFrom(shelobiteScuttlerEntity.idleAnimation);
        shelobiteScuttlerEntityRenderState.walkAnimationState.copyFrom(shelobiteScuttlerEntity.walkingAnimation);
        shelobiteScuttlerEntityRenderState.biteAnimationState.copyFrom(shelobiteScuttlerEntity.biteAnimation);
        shelobiteScuttlerEntityRenderState.pounceAnimationState.copyFrom(shelobiteScuttlerEntity.pounceAnimation);
    }
}
