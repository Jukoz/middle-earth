package net.sevenstars.of_beasts_and_wild_things.entity.pheasant;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.entity.model.EntityModelLayersWT;

import java.util.Map;

public class PheasantEntityRenderer extends MobRenderer<PheasantEntity, PheasantEntityModel> {
    private static final String PATH = "textures/entity/pheasant/";

    public PheasantEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new PheasantEntityModel(context.bakeLayer(EntityModelLayersWT.PHEASANT)), 0.2f);
    }

    public static final Map<PheasantEntityVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(PheasantEntityVariant.class), (map) -> {
                map.put(PheasantEntityVariant.MALE,
                        PATH + "pheasant_male.png");
                map.put(PheasantEntityVariant.FEMALE,
                        PATH + "pheasant_female.png");
            });

    @Override
    public ResourceLocation getTextureLocation(PheasantEntity entity) {
        return OfBeastsAndWildThings.of(LOCATION_BY_VARIANT.get(entity.getVariant()));
    }
}
