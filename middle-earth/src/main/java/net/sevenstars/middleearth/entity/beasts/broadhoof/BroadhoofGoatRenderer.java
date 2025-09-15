package net.sevenstars.middleearth.entity.beasts.broadhoof;

import com.google.common.collect.Maps;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ModEntityModelLayers;
import net.sevenstars.middleearth.entity.beasts.broadhoof.features.BroadhoofGoatArmorFeatureRenderer;
import net.sevenstars.middleearth.entity.beasts.broadhoof.features.BroadhoofGoatArmorModel;
import net.sevenstars.middleearth.entity.beasts.broadhoof.features.BroadhoofGoatSaddleFeatureRenderer;

import java.util.Map;

public class BroadhoofGoatRenderer extends MobEntityRenderer<BroadhoofGoatEntity, BroadhoofGoatEntityRenderState, BroadhoofGoatModel> {
    private static final String PATH = "textures/entities/broadhoof_goat/";
    private static final float SIZE = 1f;

    public BroadhoofGoatRenderer(EntityRendererFactory.Context context) {
        super(context, new BroadhoofGoatModel(context.getPart(ModEntityModelLayers.BROADHOOF_GOAT)), 0.8f);
        this.addFeature(
                new SaddleFeatureRenderer<>(
                        this,
                        context.getEquipmentRenderer(),
                        new BroadhoofGoatModel(context.getPart(ModEntityModelLayers.BROADHOOF_GOAT_ARMOR)),
                        EquipmentModel.LayerType.HORSE_BODY,
                        broadhoofGoatEntityRenderState -> broadhoofGoatEntityRenderState.armor

                )
        );
        this.addFeature(new BroadhoofGoatSaddleFeatureRenderer(this, context.getEntityModels(), context.getEquipmentRenderer()));
    }

    @Override
    public BroadhoofGoatEntityRenderState createRenderState() {
        return new BroadhoofGoatEntityRenderState();
    }

    public static final Map<BroadhoofGoatVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(BroadhoofGoatVariant.class), (map) -> {
                map.put(BroadhoofGoatVariant.GRAY,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_gray.png"));
                map.put(BroadhoofGoatVariant.GRAY_BEARD,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_gray_beard.png"));
                map.put(BroadhoofGoatVariant.GRAY_BEARD_YOUNG,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_gray_beard_young.png"));
                map.put(BroadhoofGoatVariant.PATCHED,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_patched.png"));
                map.put(BroadhoofGoatVariant.RED,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_red.png"));
                map.put(BroadhoofGoatVariant.RED_WITH_PATCH,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_red_with_patch.png"));
                map.put(BroadhoofGoatVariant.RED_WITH_SPOTS,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_red_with_spots.png"));
                map.put(BroadhoofGoatVariant.WHITE,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_white.png"));
                map.put(BroadhoofGoatVariant.BLACK,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_black.png"));
                map.put(BroadhoofGoatVariant.BLACK_MASK,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_black_mask.png"));
                map.put(BroadhoofGoatVariant.BROWN,
                        Identifier.of(MiddleEarth.MOD_ID, PATH + "broadhoof_goat_brown.png"));

            });

    @Override
    public void updateRenderState(BroadhoofGoatEntity goat, BroadhoofGoatEntityRenderState state, float f) {
        super.updateRenderState(goat, state, f);

        state.variant = goat.getVariant();
        state.horns = goat.getHorns();
        state.hasLeftHorn = goat.hasLeftHorn();
        state.hasRightHorn = goat.hasRightHorn();
        state.beardBrushed = goat.hasBrushedBeard();

        state.isSprinting = goat.isSprinting();
        state.isCharging = goat.isCharging();
        state.isTame = goat.isTame();
        state.conrollingPassenger = goat.getControllingPassenger();
        state.saddle = goat.getEquippedStack(EquipmentSlot.SADDLE);
        state.armor = goat.getBodyArmor();

        state.chargeAnimationState = goat.chargeAnimationState;
        state.startSittingAnimationState = goat.startSittingAnimationState;
        state.stopSittingAnimationState = goat.stopSittingAnimationState;
        state.sittingAnimationState = goat.sittingAnimationState;
        state.attackAnimationState = goat.attackAnimationState;
        state.idleAnimationState = goat.idleAnimationState;
        state.jumpAnimationState = goat.jumpAnimationState;

    }

    @Override
    public Identifier getTexture(BroadhoofGoatEntityRenderState state) {
        return LOCATION_BY_VARIANT.get(state.variant);
    }
}
