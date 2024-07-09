package net.jukoz.me.entity.spider;


import com.google.common.collect.Maps;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class MirkwoodSpiderRenderer extends MobEntityRenderer<MirkwoodSpiderEntity, MirkwoodSpiderModel> {
    private static final String PATH = "textures/entities/spiders/";

    public MirkwoodSpiderRenderer(EntityRendererFactory.Context context) {
        this(context, 0.45F, ModEntityModelLayers.SPIDER);
    }

    protected MirkwoodSpiderRenderer(EntityRendererFactory.Context ctx, float shadowRadius, EntityModelLayer layer) {
        super(ctx, new MirkwoodSpiderModel(ctx.getPart(layer)), shadowRadius);
    }

    @Override
    public Identifier getTexture(MirkwoodSpiderEntity entity) {
        return Identifier.of(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
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
    public void render(MirkwoodSpiderEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.age > MirkwoodSpiderEntity.ADULT_AGE){
            poseStack.scale(1,1,1);
        } else {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
