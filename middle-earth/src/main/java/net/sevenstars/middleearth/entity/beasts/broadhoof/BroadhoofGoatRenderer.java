package net.sevenstars.middleearth.entity.beasts.broadhoof;

import com.google.common.collect.Maps;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.feature.HorseMarkingFeatureRenderer;
import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.HorseColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.broadhoof.features.BroadhoofGoatBeadsFeatureRenderer;
import net.sevenstars.middleearth.entity.beasts.broadhoof.features.BroadhoofGoatPatternFeatureRenderer;
import net.sevenstars.middleearth.entity.beasts.broadhoof.features.BroadhoofGoatSaddleFeatureRenderer;

import java.util.Map;

public class BroadhoofGoatRenderer extends MobEntityRenderer<BroadhoofGoatEntity, BroadhoofGoatEntityRenderState, BroadhoofGoatModel> {
    private static final String PATH = "textures/entities/broadhoof_goat/";
    private static final float SIZE = 1f;

    public BroadhoofGoatRenderer(EntityRendererFactory.Context context) {
        super(context, new BroadhoofGoatModel(context.getPart(EntityModelLayersME.BROADHOOF_GOAT)), 0.8f);
        this.addFeature(new BroadhoofGoatPatternFeatureRenderer(this));
        this.addFeature(new BroadhoofGoatBeadsFeatureRenderer(this));
        this.addFeature(
                new SaddleFeatureRenderer<>(
                        this,
                        context.getEquipmentRenderer(),
                        new BroadhoofGoatModel(context.getPart(EntityModelLayersME.BROADHOOF_GOAT_ARMOR)),
                        EquipmentModel.LayerType.HORSE_BODY,
                        broadhoofGoatEntityRenderState -> broadhoofGoatEntityRenderState.armor

                )
        );
        this.addFeature(new BroadhoofGoatSaddleFeatureRenderer(this, context.getEntityModels(), context.getEquipmentRenderer()));
    }

    @Override
    public void render(BroadhoofGoatEntityRenderState state, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if(state.baby) {
            matrixStack.scale(SIZE/2, SIZE/2, SIZE/2);
        }
        else {
            matrixStack.scale(SIZE, SIZE, SIZE);
        }

        super.render(state, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public BroadhoofGoatEntityRenderState createRenderState() {
        return new BroadhoofGoatEntityRenderState();
    }

    private static final Map<BroadhoofGoatColor, Identifier> TEXTURES = Maps.newEnumMap(
            Map.of(
                    BroadhoofGoatColor.WHITE,
                    MiddleEarth.of(PATH + "broadhoof_goat_white.png"),
                    BroadhoofGoatColor.LIGHT_GRAY,
                    MiddleEarth.of(PATH + "broadhoof_goat_light_gray.png"),
                    BroadhoofGoatColor.PALE,
                    MiddleEarth.of(PATH + "broadhoof_goat_pale.png"),
                    BroadhoofGoatColor.RED,
                    MiddleEarth.of(PATH + "broadhoof_goat_red.png"),
                    BroadhoofGoatColor.BROWN,
                    MiddleEarth.of(PATH + "broadhoof_goat_brown.png"),
                    BroadhoofGoatColor.GRAY,
                    MiddleEarth.of(PATH + "broadhoof_goat_gray.png"),
                    BroadhoofGoatColor.BLACK,
                    MiddleEarth.of(PATH + "broadhoof_goat_black.png")
            )
    );

    @Override
    public void updateRenderState(BroadhoofGoatEntity goat, BroadhoofGoatEntityRenderState state, float f) {
        super.updateRenderState(goat, state, f);

        state.color = goat.getGoatColor();
        state.pattern = goat.getPattern();
        state.armor = goat.getBodyArmor().copy();

        state.beads = goat.getGoatBeads();
        state.horns = goat.getHorns();
        state.hair = goat.hasHair();
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
        return TEXTURES.get(state.color);
    }
}
