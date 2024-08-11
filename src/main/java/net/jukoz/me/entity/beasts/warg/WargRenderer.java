package net.jukoz.me.entity.beasts.warg;

import com.google.common.collect.Maps;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.warg.features.WargArmorFeatureRenderer;
import net.jukoz.me.entity.beasts.warg.features.WargEyesFeatureRenderer;
import net.jukoz.me.entity.beasts.warg.features.WargSaddleFeatureRenderer;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class WargRenderer extends MobEntityRenderer<WargEntity, WargModel> {
    private static final String PATH = "textures/entities/warg/";

    public WargRenderer(EntityRendererFactory.Context context) {
        super(context, new WargModel(context.getPart(ModEntityModelLayers.WARG)), 0.8f);
        this.addFeature(new WargEyesFeatureRenderer(this));
        this.addFeature(new WargArmorFeatureRenderer(this, context.getModelLoader()));
        this.addFeature(new WargSaddleFeatureRenderer(this, context.getModelLoader()));
    }

    public static final Map<WargVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(WargVariant.class), (map) -> {
                map.put(WargVariant.BROWN,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "warg_brown.png"));
                map.put(WargVariant.BLACK,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "warg_black.png"));
                map.put(WargVariant.GRAY,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "warg_gray.png"));
                map.put(WargVariant.LIGHT_GRAY,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "warg_light_gray.png"));
                map.put(WargVariant.GRAY_FACE,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "warg_gray_face.png"));
                map.put(WargVariant.RED_BALD,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "warg_red_bald.png"));
                map.put(WargVariant.TAN,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "warg_tan.png"));
                map.put(WargVariant.TAN_GRAY,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "warg_tan_gray.png"));
            });

    @Override
    public Identifier getTexture(WargEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }
}
