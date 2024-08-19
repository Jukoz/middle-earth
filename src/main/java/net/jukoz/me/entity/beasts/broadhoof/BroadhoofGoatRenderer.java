package net.jukoz.me.entity.beasts.broadhoof;

import com.google.common.collect.Maps;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.broadhoof.features.BroadhoofGoatArmorFeatureRenderer;
import net.jukoz.me.entity.beasts.broadhoof.features.BroadhoofGoatSaddleFeatureRenderer;
import net.jukoz.me.entity.beasts.warg.WargEntity;
import net.jukoz.me.entity.beasts.warg.WargModel;
import net.jukoz.me.entity.beasts.warg.WargVariant;
import net.jukoz.me.entity.beasts.warg.features.WargArmorFeatureRenderer;
import net.jukoz.me.entity.beasts.warg.features.WargEyesFeatureRenderer;
import net.jukoz.me.entity.beasts.warg.features.WargSaddleFeatureRenderer;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class BroadhoofGoatRenderer extends MobEntityRenderer<BroadhoofGoatEntity,BroadhoofGoatModel> {
    private static final String PATH = "textures/entities/broadhoof_goat/";

    public BroadhoofGoatRenderer(EntityRendererFactory.Context context) {
        super(context, new BroadhoofGoatModel(context.getPart(ModEntityModelLayers.BROADHOOF_GOAT)), 0.8f);
        this.addFeature(new BroadhoofGoatArmorFeatureRenderer(this, context.getModelLoader()));
        this.addFeature(new BroadhoofGoatSaddleFeatureRenderer(this, context.getModelLoader()));
    }

    @Override
    public Identifier getTexture(BroadhoofGoatEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    public static final Map<BroadhoofGoatVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(BroadhoofGoatVariant.class), (map) -> {
                map.put(BroadhoofGoatVariant.GRAY,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_gray.png"));
                map.put(BroadhoofGoatVariant.GRAY_BEARD,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_gray_beard.png"));
                map.put(BroadhoofGoatVariant.GRAY_BEARD_YOUNG,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_gray_beard_young.png"));
                map.put(BroadhoofGoatVariant.PATCHED,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_patched.png"));
                map.put(BroadhoofGoatVariant.RED,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_red.png"));
                map.put(BroadhoofGoatVariant.RED_WITH_PATCH,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_red_with_patch.png"));
                map.put(BroadhoofGoatVariant.RED_WITH_SPOTS,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_red_with_spots.png"));
                map.put(BroadhoofGoatVariant.WHITE,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_white.png"));
                map.put(BroadhoofGoatVariant.BLACK,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_black.png"));
                map.put(BroadhoofGoatVariant.BLACK_MASK,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_black_mask.png"));
                map.put(BroadhoofGoatVariant.BROWN,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_brown.png"));

            });
}
