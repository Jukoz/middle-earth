package net.sevenstars.middleearth.entity.beasts.warg;

import com.google.common.collect.Maps;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.warg.features.*;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class WargRenderer extends MobEntityRenderer<WargEntity, WargEntityRenderState, WargModel> {
    private static final String PATH = "textures/entities/warg/";
    private static final float SIZE = 1f;

    public WargRenderer(EntityRendererFactory.Context context) {
        super(context, new WargModel(context.getPart(EntityModelLayersME.WARG)), 0.8f);
        this.addFeature(new WargEyesFeatureRenderer(this));
        this.addFeature( // Armor Feature
                new SaddleFeatureRenderer<>(
                        this,
                        context.getEquipmentRenderer(),
                        new WargModel(context.getPart(EntityModelLayersME.WARG_ARMOR)),
                        EquipmentModel.LayerType.HORSE_BODY,
                        state -> state.armor
                )
        );
        this.addFeature(new WargArmorSpineFeatureRenderer(this, context.getEntityModels(), context.getEquipmentRenderer()));
        this.addFeature(new WargArmorSideSkullsFeatureRenderer(this, context.getEntityModels(), context.getEquipmentRenderer()));
        this.addFeature(new WargSaddleFeatureRenderer(this, context.getEntityModels(), context.getEquipmentRenderer()));
        this.addFeature(new WargArmorFrontSkullFeatureRenderer(this, context.getEntityModels(), context.getEquipmentRenderer()));
        this.addFeature(new WargArmorBackSkullFeatureRenderer(this, context.getEntityModels(), context.getEquipmentRenderer()));
    }

    @Override
    public void render(WargEntityRenderState state, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if(state.baby) {
            matrixStack.scale(SIZE/2, SIZE/2, SIZE/2);
        }
        else {
            matrixStack.scale(SIZE, SIZE, SIZE);
        }

        super.render(state, matrixStack, vertexConsumerProvider, i);
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

    @Override
    public void updateRenderState(WargEntity warg, WargEntityRenderState state, float f) {
        super.updateRenderState(warg, state, f);

        state.variant = warg.getVariant();

        state.isSprinting = warg.isSprinting();
        state.isCharging = warg.isCharging();
        state.isTame = warg.isTame();
        state.conrollingPassenger = warg.getControllingPassenger();
        state.saddle = warg.getEquippedStack(EquipmentSlot.SADDLE);
        state.armor = warg.getBodyArmor();

        state.chargeAnimationState = warg.chargeAnimationState;
        state.startSittingAnimationState = warg.startSittingAnimationState;
        state.stopSittingAnimationState = warg.stopSittingAnimationState;
        state.sittingAnimationState = warg.sittingAnimationState;
        state.attackAnimationState = warg.attackAnimationState;
        state.idleAnimationState = warg.idleAnimationState;
    }
}
