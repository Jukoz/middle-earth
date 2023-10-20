package net.jukoz.me.entity.dwarves.durin;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
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
public class DurinDwarfRenderer extends BipedEntityRenderer<DurinDwarfEntity, DurinDwarfModel<DurinDwarfEntity>> {
    private static final String PATH = "textures/entities/dwarves/durin/";
    private static final float SIZE = 0.78f;

    public DurinDwarfRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new DurinDwarfModel(ctx.getPart(ModEntityModelLayers.DWARF)), 0.5f);
        this.addFeature(new ArmorFeatureRenderer<>(this, new DurinDwarfModel(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)), new DurinDwarfModel(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)), ctx.getModelManager()));
    }

    public static final Map<DurinDwarfVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(DurinDwarfVariant.class), (resourceLocation) -> {
                resourceLocation.put(DurinDwarfVariant.GINGER,
                        PATH + "dwarf1.png");
                resourceLocation.put(DurinDwarfVariant.BlACK,
                        PATH + "dwarf2.png");
                resourceLocation.put(DurinDwarfVariant.OLD,
                        PATH + "dwarf3.png");
                resourceLocation.put(DurinDwarfVariant.DARK_BLONDE,
                        PATH + "dwarf4.png");
                resourceLocation.put(DurinDwarfVariant.BLACK_BALD,
                        PATH + "dwarf5.png");
                resourceLocation.put(DurinDwarfVariant.GINGER_NORI,
                        PATH + "dwarf6.png");
                resourceLocation.put(DurinDwarfVariant.BLACK_NORI,
                        PATH + "dwarf7.png");
                resourceLocation.put(DurinDwarfVariant.DARK_BLONDE_NORI,
                        PATH + "dwarf8.png");
            });

    @Override
    public Identifier getTexture(DurinDwarfEntity entity) {
        return new Identifier(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    @Override
    public void render(DurinDwarfEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        poseStack.scale(SIZE, SIZE, SIZE);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
