package net.sevenstars.of_beasts_and_wild_things.entity.snail;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.entity.model.EntityModelLayersWT;

import java.util.Map;

public class SnailEntityRenderer extends MobRenderer<SnailEntity, SnailEntityModel> {
    private static final String PATH = "textures/entity/snail/";

    public SnailEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new SnailEntityModel(context.bakeLayer(EntityModelLayersWT.SNAIL)), 0.2f);
    }

    public static final Map<SnailEntityVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(SnailEntityVariant.class), (map) -> {
                map.put(SnailEntityVariant.GREEN,
                        PATH + "snail_green.png");
                map.put(SnailEntityVariant.PALE_GREEN,
                        PATH + "snail_pale_green.png");
                map.put(SnailEntityVariant.BROWN,
                        PATH + "snail_brown.png");
                map.put(SnailEntityVariant.GRAY,
                        PATH + "snail_gray.png");

            });

    @Override
    public ResourceLocation getTextureLocation(SnailEntity entity) {
        return OfBeastsAndWildThings.of(LOCATION_BY_VARIANT.get(entity.getVariant()));
    }
}
