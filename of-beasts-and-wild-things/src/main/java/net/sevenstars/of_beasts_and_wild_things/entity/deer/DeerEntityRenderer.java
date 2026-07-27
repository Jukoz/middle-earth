package net.sevenstars.of_beasts_and_wild_things.entity.deer;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.entity.model.EntityModelLayersWT;

import java.util.Map;

public class DeerEntityRenderer extends MobRenderer<DeerEntity, DeerEntityModel> {
    private static final String PATH = "textures/entity/deer/";

    public DeerEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new DeerEntityModel(context.bakeLayer(EntityModelLayersWT.DEER)), 0.5f);
    }

    public static final Map<DeerEntityVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(DeerEntityVariant.class), (map) -> {
                map.put(DeerEntityVariant.SPOTS,
                        PATH + "deer_spots.png");
                map.put(DeerEntityVariant.NO_SPOTS,
                        PATH + "deer_no_spots.png");
            });

    @Override
    public ResourceLocation getTextureLocation(DeerEntity entity) {
        return OfBeastsAndWildThings.of(LOCATION_BY_VARIANT.get(entity.getVariant()));
    }
}
