package net.sevenstars.middleearth.entity.pheasant;

import com.google.common.collect.Maps;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class PheasantRenderer extends MobEntityRenderer<PheasantEntity, PheasantEntityRenderState, PheasantModel> {
    private static final String PATH = "textures/entities/pheasants/";
    private static final float SIZE = 1f;

    public PheasantRenderer(EntityRendererFactory.Context context) {
        super(context, new PheasantModel(context.getPart(ModEntityModelLayers.PHEASANT)), 0.35F);
    }

    @Override
    public PheasantEntityRenderState createRenderState() {
        return new PheasantEntityRenderState();
    }

    public static final Map<PheasantVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(PheasantVariant.class), (map) -> {
                map.put(PheasantVariant.MALE,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "pheasant1m.png"));
                map.put(PheasantVariant.FEMALE,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "pheasant1f.png"));
            });

    @Override
    public Identifier getTexture(PheasantEntityRenderState state) {
        return LOCATION_BY_VARIANT.get(state.variant);
    }
}
