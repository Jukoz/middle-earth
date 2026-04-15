package net.sevenstars.middleearth.entity.beasts.cave_troll;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.cave_troll.feature.CaveTrollDroolFeatureRenderer;
import net.sevenstars.middleearth.entity.beasts.cave_troll.feature.CaveTrollHeldItemFeatureRenderer;
import net.sevenstars.middleearth.entity.beasts.cave_troll.feature.CaveTrollSaddleFeatureRenderer;

public class CaveTrollRenderer extends MobEntityRenderer<CaveTrollEntity, CaveTrollEntityRenderState, CaveTrollEntityModel> {
    private static final String TEXTURE = "textures/entities/trolls/cave/cave_troll_green.png";
    private static final String TEXTURE_ANGRY = "textures/entities/trolls/cave/cave_troll_green_red_eyes.png";
    public CaveTrollRenderer(EntityRendererFactory.Context context) {
        super(context, new CaveTrollEntityModel(context.getPart(EntityModelLayersME.CAVE_TROLL)), 1.1f);
        this.addFeature(new CaveTrollDroolFeatureRenderer(this));
        this.addFeature(new CaveTrollSaddleFeatureRenderer(this, context.getEntityModels(), context.getEquipmentRenderer()));
        this.addFeature(new CaveTrollHeldItemFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(CaveTrollEntityRenderState state) {
        return state.tameness > 25 && !state.isEnraged ? Identifier.of(MiddleEarth.MOD_ID, TEXTURE) : Identifier.of(MiddleEarth.MOD_ID, TEXTURE_ANGRY);
    }

    @Override
    public CaveTrollEntityRenderState createRenderState() {
        return new CaveTrollEntityRenderState();
    }

    @Override
    public void updateRenderState(CaveTrollEntity troll, CaveTrollEntityRenderState state, float f) {
        super.updateRenderState(troll, state, f);
        CaveTrollEntityRenderState.updateRenderState(troll, state, this.itemModelResolver);

        state.chaseAnimationState = troll.chaseAnimationState;
        state.scavengingAnimationState = troll.scavengingAnimationState;
        state.startSittingAnimationState = troll.startSittingAnimationState;
        state.stopSittingAnimationState = troll.stopSittingAnimationState;
        state.startSleepingAnimationState = troll.startSleepingAnimationState;
        state.sleepingAnimationState = troll.sleepingAnimationState;
        state.stopSleepingAnimationState = troll.stopSleepingAnimationState;
        state.roaringAnimationState = troll.roaringAnimationState;
        state.smashingAnimationState = troll.smashingAnimationState;
        state.isSprinting = troll.isSprinting();
        state.isCharging = troll.isCharging();
        state.isEnraged = troll.isEnraged();
        state.isTame = troll.isTame();
        state.conrollingPassenger = troll.getControllingPassenger();
        state.saddle = troll.getEquippedStack(EquipmentSlot.SADDLE);
        state.tameness = troll.getTameness();
    }
}
