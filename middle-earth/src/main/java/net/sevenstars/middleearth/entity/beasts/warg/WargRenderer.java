package net.sevenstars.middleearth.entity.beasts.warg;

import com.google.common.collect.Maps;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.warg.features.*;
import net.sevenstars.middleearth.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class WargRenderer extends MobEntityRenderer<WargEntity, WargEntityRenderState, WargModel> {
    private static final String PATH = "textures/entities/warg/";
    private static final float SIZE = 1f;

    public WargRenderer(EntityRendererFactory.Context context) {
        super(context, new WargModel(context.getPart(ModEntityModelLayers.WARG)), 0.8f);
        this.addFeature(new WargEyesFeatureRenderer(this));
        this.addFeature(new WargArmorFeatureRenderer(this, context.getModelLoader(), context.getEquipmentRenderer()));
        this.addFeature(new WargArmorSpineFeatureRenderer(this, context.getModelLoader(), context.getEquipmentRenderer()));
        this.addFeature(new WargArmorSideSkullsFeatureRenderer(this, context.getModelLoader(), context.getEquipmentRenderer()));
        this.addFeature(new WargSaddleFeatureRenderer(this, context.getModelLoader(), context.getEquipmentRenderer()));
        this.addFeature(new WargArmorFrontSkullFeatureRenderer(this, context.getModelLoader(), context.getEquipmentRenderer()));
        this.addFeature(new WargArmorBackSkullFeatureRenderer(this, context.getModelLoader(), context.getEquipmentRenderer()));
    }

    @Override
    public WargEntityRenderState createRenderState() {
        return new WargEntityRenderState();
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
    public Identifier getTexture(WargEntityRenderState state) {
        return LOCATION_BY_VARIANT.get(state.variant);
    }
}
