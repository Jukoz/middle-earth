package net.jukoz.me.entity.beasts.trolls.cave;

import com.google.common.collect.Maps;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class CaveTrollRenderer extends MobEntityRenderer<CaveTrollEntity, CaveTrollModel> {
    private static final String PATH = "textures/entities/trolls/cave/";

    public CaveTrollRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new CaveTrollModel(ctx.getPart(ModEntityModelLayers.CAVE_TROLL)), 1.1f);
    }

    @Override
    public Identifier getTexture(CaveTrollEntity entity) {
        return new Identifier(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    public static final Map<CaveTrollVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(CaveTrollVariant.class), (resourceLocation) -> {
                resourceLocation.put(CaveTrollVariant.GREY_YELLOW,
                        PATH + "stone_troll1.png");
                resourceLocation.put(CaveTrollVariant.GREY_BLUE,
                        PATH + "troll2.png");
                resourceLocation.put(CaveTrollVariant.GREY_STONE,
                        PATH + "troll3.png");
            });

    public void render(CaveTrollEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        poseStack.scale(1, 1, 1);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
