package net.jesteur.me.entity.hobbits;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

@Environment(value= EnvType.CLIENT)
public class HobbitRenderer extends MobEntityRenderer<HobbitEntity, HobbitAnimModel<HobbitEntity>> {
    private static final String PATH = "textures/entities/hobbits/";

    public HobbitRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new HobbitAnimModel<>(ctx.getPart(ModEntityModelLayers.HOBBIT)), 0.5f);
    }

    public static final Map<HobbitVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(HobbitVariant.class), (resourceLocation) -> {
                resourceLocation.put(HobbitVariant.GINGER_WHITE_BLUE,
                          PATH + "hobbit1.png");
                resourceLocation.put(HobbitVariant.DARK_BLONDE_CYAN_BROWN,
                        PATH + "hobbit2.png");
                resourceLocation.put(HobbitVariant.GINGER_GREEN_BROWN,
                        PATH + "hobbit3.png");
                resourceLocation.put(HobbitVariant.SAM,
                        PATH + "sam.png");
                resourceLocation.put(HobbitVariant.FRODO,
                        PATH + "frodo.png");
            });

    @Override
    public Identifier getTexture(HobbitEntity entity) {
        return new Identifier(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    @Override
    public void render(HobbitEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        poseStack.scale(0.65f, 0.65f, 0.65f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
