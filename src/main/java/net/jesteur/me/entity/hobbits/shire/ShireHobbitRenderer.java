package net.jesteur.me.entity.hobbits.shire;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.entity.dwarves.durin.DurinDwarfEntity;
import net.jesteur.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

@Environment(value= EnvType.CLIENT)
public class ShireHobbitRenderer extends MobEntityRenderer<ShireHobbitEntity, ShireHobbitModel<ShireHobbitEntity>> {
    private static final String PATH = "textures/entities/hobbits/shire/";
    private static final float SIZE = 0.65f;

    public ShireHobbitRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ShireHobbitModel<>(ctx.getPart(ModEntityModelLayers.HOBBIT)), 0.5f);
        this.addFeature(new ArmorFeatureRenderer<>(this, new ShireHobbitModel(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)), new ShireHobbitModel(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)), ctx.getModelManager()));
    }

    public static final Map<ShireHobbitVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(ShireHobbitVariant.class), (resourceLocation) -> {
                resourceLocation.put(ShireHobbitVariant.GINGER_WHITE_BLUE,
                        PATH + "hobbit1.png");
                resourceLocation.put(ShireHobbitVariant.DARK_BLONDE_CYAN_BROWN,
                        PATH + "hobbit2.png");
                resourceLocation.put(ShireHobbitVariant.GINGER_GREEN_BROWN,
                        PATH + "hobbit3.png");
                resourceLocation.put(ShireHobbitVariant.SAM,
                        PATH + "sam.png");
                resourceLocation.put(ShireHobbitVariant.FRODO,
                        PATH + "frodo.png");
            });

    @Override
    public Identifier getTexture(ShireHobbitEntity entity) {
        return new Identifier(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    public void render(ShireHobbitEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        poseStack.scale(SIZE, SIZE, SIZE);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
