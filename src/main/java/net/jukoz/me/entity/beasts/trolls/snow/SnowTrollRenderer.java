package net.jukoz.me.entity.beasts.trolls.snow;

import com.google.common.collect.Maps;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class SnowTrollRenderer extends MobEntityRenderer<SnowTrollEntity, SnowTrollModel> {
    private static final String PATH = "textures/entities/trolls/snow/";

    public SnowTrollRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SnowTrollModel(ctx.getPart(ModEntityModelLayers.SNOW_TROLL)), 1.1f);
    }

    @Override
    public Identifier getTexture(SnowTrollEntity entity) {
        return Identifier.of(MiddleEarth.MOD_ID, PATH + "snow_troll1.png");
    }

    public static final Map<SnowTrollVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(SnowTrollVariant.class), (resourceLocation) -> {
                resourceLocation.put(SnowTrollVariant.SNOW,
                        PATH + "snow_troll1.png");
            });
}
