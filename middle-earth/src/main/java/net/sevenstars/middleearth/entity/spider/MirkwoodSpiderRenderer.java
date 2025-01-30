package net.sevenstars.middleearth.entity.spider;


import com.google.common.collect.Maps;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.sevenstars.middleearth.entity.snail.SnailEntity;
import net.sevenstars.middleearth.entity.snail.SnailEntityRenderState;

import java.util.Map;

public class MirkwoodSpiderRenderer extends MobEntityRenderer<MirkwoodSpiderEntity, MirkwoodSpiderEntityRenderState, MirkwoodSpiderModel> {
    private static final String PATH = "textures/entities/spiders/";

    public MirkwoodSpiderRenderer(EntityRendererFactory.Context context) {
        this(context, 0.45F, ModEntityModelLayers.SPIDER);
    }

    @Override
    public MirkwoodSpiderEntityRenderState createRenderState() {
        return new MirkwoodSpiderEntityRenderState();
    }

    protected MirkwoodSpiderRenderer(EntityRendererFactory.Context ctx, float shadowRadius, EntityModelLayer layer) {
        super(ctx, new MirkwoodSpiderModel(ctx.getPart(layer)), shadowRadius);
    }

    public static final Map<MirkwoodSpiderVariants, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MirkwoodSpiderVariants.class), (resourceLocation) -> {
                resourceLocation.put(MirkwoodSpiderVariants.BLACK,
                        PATH + "spider1.png");
                resourceLocation.put(MirkwoodSpiderVariants.BROWN,
                        PATH + "spider2.png");
                resourceLocation.put(MirkwoodSpiderVariants.DARK_GREEN,
                        PATH + "spider3.png");
            });

    @Override
    public Identifier getTexture(MirkwoodSpiderEntityRenderState state) {
        return Identifier.of(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(state.variant));
    }

    public void updateRenderState(MirkwoodSpiderEntity mirkwoodSpiderEntity, MirkwoodSpiderEntityRenderState mirkwoodSpiderEntityRenderState, float f) {
        super.updateRenderState(mirkwoodSpiderEntity, mirkwoodSpiderEntityRenderState, f);
        mirkwoodSpiderEntityRenderState.variant = mirkwoodSpiderEntity.getVariant();
    }
}
