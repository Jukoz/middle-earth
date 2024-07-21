package net.jukoz.me.entity.beasts.warg;

import com.google.common.collect.Maps;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.warg.armor.WargArmorFeatureRenderer;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.jukoz.me.entity.pheasant.PheasantVariant;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class WargRenderer extends MobEntityRenderer<WargEntity, WargModel> {
    private static final String PATH = "textures/entities/warg/";

    public WargRenderer(EntityRendererFactory.Context context) {
        super(context, new WargModel(context.getPart(ModEntityModelLayers.WARG)), 0.8f);
        this.addFeature(new WargArmorFeatureRenderer(this, context.getModelLoader()));
    }

    public static final Map<WargVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(WargVariant.class), (map) -> {
                map.put(WargVariant.BROWN,
                        new Identifier(MiddleEarth.MOD_ID, PATH + "warg_brown.png"));
                map.put(WargVariant.WHITE,
                        new Identifier(MiddleEarth.MOD_ID, PATH + "warg_white.png"));
                map.put(WargVariant.GREY,
                        new Identifier(MiddleEarth.MOD_ID, PATH + "warg_grey.png"));
                map.put(WargVariant.BLACK,
                        new Identifier(MiddleEarth.MOD_ID, PATH + "warg_black.png"));
                map.put(WargVariant.LIGHT_GREY,
                        new Identifier(MiddleEarth.MOD_ID, PATH + "warg_light_grey.png"));
            });

    @Override
    public Identifier getTexture(WargEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }
}
