package net.jukoz.me.entity.dwarves.longbeards;

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
public class LongbeardDwarfRenderer extends BipedEntityRenderer<LongbeardDwarfEntity, LongbeardDwarfModel<LongbeardDwarfEntity>> {
    private static final String PATH = "textures/entities/dwarves/durin/";
    private static final float SIZE = 0.78f;

    public LongbeardDwarfRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new LongbeardDwarfModel(ctx.getPart(ModEntityModelLayers.DWARF)), 0.5f);
        this.addFeature(new ArmorFeatureRenderer<>(this, new LongbeardDwarfModel(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)), new LongbeardDwarfModel(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)), ctx.getModelManager()));
    }

    public static final Map<LongbeardDwarfVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(LongbeardDwarfVariant.class), (resourceLocation) -> {
                resourceLocation.put(LongbeardDwarfVariant.GINGER,
                        PATH + "dwarf1.png");
                resourceLocation.put(LongbeardDwarfVariant.BLACK,
                        PATH + "dwarf2.png");
                resourceLocation.put(LongbeardDwarfVariant.OLD,
                        PATH + "dwarf3.png");
                resourceLocation.put(LongbeardDwarfVariant.DARK_BLONDE,
                        PATH + "dwarf4.png");
                resourceLocation.put(LongbeardDwarfVariant.BLACK_BALD,
                        PATH + "dwarf5.png");
                resourceLocation.put(LongbeardDwarfVariant.GINGER_NORI,
                        PATH + "dwarf6.png");
                resourceLocation.put(LongbeardDwarfVariant.BLACK_NORI,
                        PATH + "dwarf7.png");
                resourceLocation.put(LongbeardDwarfVariant.DARK_BLONDE_NORI,
                        PATH + "dwarf8.png");
            });

    @Override
    public Identifier getTexture(LongbeardDwarfEntity entity) {
        return Identifier.of(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    @Override
    public void render(LongbeardDwarfEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        poseStack.scale(SIZE, SIZE, SIZE);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
