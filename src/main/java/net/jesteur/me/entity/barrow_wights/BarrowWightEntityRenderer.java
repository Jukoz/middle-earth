package net.jesteur.me.entity.barrow_wights;

import com.google.common.collect.Maps;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class BarrowWightEntityRenderer extends MobEntityRenderer<BarrowWightEntity, BarrowWightModel> {
    private static final String PATH = "textures/entities/barrow_wights/";

    public BarrowWightEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BarrowWightModel(ctx.getPart(ModEntityModelLayers.BARROW_WIGHT)), 0.5f);
    }

    @Override
    public Identifier getTexture(BarrowWightEntity entity) {
        return new Identifier(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    public static final Map<BarrowWightVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(BarrowWightVariant.class), (resourceLocation) -> {
                resourceLocation.put(BarrowWightVariant.BASIC, PATH + "barrow_wight0.png");
            });
}
