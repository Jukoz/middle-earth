package net.jesteur.me.entity.elves.galadhrim;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.entity.hobbits.shire.ShireHobbitModel;
import net.jesteur.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

@Environment(value= EnvType.CLIENT)
public class GaladhrimElfRenderer extends BipedEntityRenderer<GaladhrimElfEntity, GaladhrimElfModel<GaladhrimElfEntity>> {
    private static final String PATH = "textures/entities/elves/galadhrim/";

    public GaladhrimElfRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new GaladhrimElfModel<>(ctx.getPart(ModEntityModelLayers.ELF)), 0.5f);
        this.addFeature(new ArmorFeatureRenderer<>(this, new GaladhrimElfModel(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)), new GaladhrimElfModel(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)), ctx.getModelManager()));
    }

    public static final Map<GaladhrimElfVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(GaladhrimElfVariant.class), (resourceLocation) -> {
                resourceLocation.put(GaladhrimElfVariant.SILVER_TEAL_BLUE_WOMAN,
                          PATH + "elf1.png");
                resourceLocation.put(GaladhrimElfVariant.SILVER_CYAN_BLUE_WOMAN,
                        PATH + "elf2.png");
                resourceLocation.put(GaladhrimElfVariant.SILVER_LIME_BLUE_WOMAN,
                        PATH + "elf3.png");
            });

    @Override
    public Identifier getTexture(GaladhrimElfEntity entity) {
        return new Identifier(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    @Override
    public void render(GaladhrimElfEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        poseStack.scale(0.92f, 1.02f, 0.92f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
