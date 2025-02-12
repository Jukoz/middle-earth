package net.sevenstars.of_beasts_and_wild_things.entity.pheasant;

import com.google.common.collect.Maps;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.entity.model.ModEntityModelLayers;

import java.util.Map;

public class PheasantEntityRenderer extends MobEntityRenderer<PheasantEntity, PheasantEntityRenderState, PheasantEntityModel> {
    private static final String PATH = "textures/entity/pheasant/";

    public PheasantEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new PheasantEntityModel(context.getPart(ModEntityModelLayers.PHEASANT)), 0.2f);
    }

    public static final Map<PheasantEntityVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(PheasantEntityVariant.class), (map) -> {
                map.put(PheasantEntityVariant.MALE,
                        PATH + "pheasant_male.png");
                map.put(PheasantEntityVariant.FEMALE,
                        PATH + "pheasant_female.png");
            });

    @Override
    public Identifier getTexture(PheasantEntityRenderState state) {
        return Identifier.of(OfBeastsAndWildThings.MOD_ID, LOCATION_BY_VARIANT.get(state.variant));
    }

    @Override
    public PheasantEntityRenderState createRenderState() {
        return new PheasantEntityRenderState();
    }

    public void updateRenderState(PheasantEntity pheasantEntity, PheasantEntityRenderState pheasantEntityRenderState, float f) {
        super.updateRenderState(pheasantEntity, pheasantEntityRenderState, f);
        pheasantEntityRenderState.variant = pheasantEntity.getVariant();
    }
}