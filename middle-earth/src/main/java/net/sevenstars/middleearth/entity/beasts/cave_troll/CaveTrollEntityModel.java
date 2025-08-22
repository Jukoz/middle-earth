package net.sevenstars.middleearth.entity.beasts.cave_troll;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollAnimations;
import net.sevenstars.middleearth.entity.beasts.trolls.TrollEntityRenderState;

public class CaveTrollEntityModel extends EntityModel<CaveTrollEntityRenderState> {
    private final Animation walkingAnimation;
    private final Animation chaseAnimation;
    private final Animation scavengingAnimation;
    private final Animation startSittingAnimation;
    private final Animation stopSittingAnimation;
    private final Animation startSleepingAnimation;
    private final Animation sleepingAnimation;
    private final Animation stopSleepingAnimation;

    private final ModelPart rightArm;
    private final ModelPart chest;
    private final ModelPart upperBody;
    private final ModelPart rootChild;

    protected CaveTrollEntityModel(ModelPart root) {
        super(root);

        rightArm = root.getChild("root").getChild("body_no_legs").getChild("ArmRight");
        chest = root.getChild("root").getChild("body_no_legs").getChild("body_no_limbs");
        upperBody = root.getChild("root").getChild("body_no_legs");
        rootChild = root.getChild("root");


        walkingAnimation = CaveTrollAnimations.PASSIVE_WALK.createAnimation(root);
        chaseAnimation = CaveTrollAnimations.RUN.createAnimation(root);
        scavengingAnimation = CaveTrollAnimations.SITTING_SLEEP.createAnimation(root);
        startSittingAnimation = CaveTrollAnimations.STANDING_TO_SITTING.createAnimation(root);
        stopSittingAnimation = CaveTrollAnimations.STANDING_FROM_SITTING.createAnimation(root);
        startSleepingAnimation = CaveTrollAnimations.SLEEP_LAYING_DOWN.createAnimation(root);
        sleepingAnimation = CaveTrollAnimations.SLEEP_LAYING_DOWN.createAnimation(root);
        stopSleepingAnimation = CaveTrollAnimations.SLEEP_LAYING_DOWN_STANDING_UP.createAnimation(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 24.0F, 0.0F));

        ModelPartData body_no_legs = root.addChild("body_no_legs", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -15.3333F, -0.5F));

        ModelPartData body_no_limbs = body_no_legs.addChild("body_no_limbs", ModelPartBuilder.create(), ModelTransform.origin(-0.5F, 0.3333F, 0.5F));

        ModelPartData body = body_no_limbs.addChild("body", ModelPartBuilder.create().uv(156, 199).cuboid(-16.0F, -40.0F, -9.0F, 33.0F, 40.0F, 17.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData Loincloth_Front = body.addChild("Loincloth_Front", ModelPartBuilder.create().uv(190, 122).cuboid(-17.0F, 0.0F, 0.0F, 33.0F, 7.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(1.0F, 0.0F, -9.0F));

        ModelPartData Loincloth_Back = body_no_limbs.addChild("Loincloth_Back", ModelPartBuilder.create().uv(190, 134).cuboid(-17.0F, 0.0F, 0.0F, 33.0F, 7.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(1.0F, 0.0F, 8.0F));

        ModelPartData Head = body_no_limbs.addChild("Head", ModelPartBuilder.create().uv(172, 161).cuboid(-8.5F, -8.5F, -8.5F, 17.0F, 17.0F, 17.0F, new Dilation(0.0F)), ModelTransform.origin(0.5F, -34.5F, -8.5F));

        ModelPartData jaw = Head.addChild("jaw", ModelPartBuilder.create().uv(143, 144).cuboid(-8.5F, -1.5F, -5.5F, 17.0F, 4.0F, 6.0F, new Dilation(0.01F))
                .uv(201, 150).cuboid(-4.5F, -4.5F, -5.5F, 9.0F, 3.0F, 1.0F, new Dilation(0.01F))
                .uv(166, 162).cuboid(4.5F, -5.5F, -5.5F, 2.0F, 4.0F, 2.0F, new Dilation(0.01F))
                .uv(166, 162).mirrored().cuboid(-6.5F, -5.5F, -5.5F, 2.0F, 4.0F, 2.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(0.0F, 6.0F, -4.0F, 0.0436F, 0.0F, 0.0F));

        ModelPartData ArmRight = body_no_legs.addChild("ArmRight", ModelPartBuilder.create().uv(62, 171).cuboid(-10.0F, -3.5F, -5.0F, 10.0F, 41.0F, 10.0F, new Dilation(0.0F)), ModelTransform.origin(-16.5F, -34.1667F, 0.5F));

        ModelPartData HandRight = ArmRight.addChild("HandRight", ModelPartBuilder.create(), ModelTransform.origin(-9.0F, 39.5F, -3.0F));

        ModelPartData HandRight_FingerBase_1 = HandRight.addChild("HandRight_FingerBase_1", ModelPartBuilder.create().uv(117, 154).cuboid(-2.5F, 3.0F, -1.5F, 5.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(1.5F, -1.0F, -0.5F));

        ModelPartData HandRight_Finger_1 = HandRight_FingerBase_1.addChild("HandRight_Finger_1", ModelPartBuilder.create().uv(119, 162).cuboid(-4.0F, -5.9F, -2.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
                .uv(122, 149).cuboid(1.0F, -1.6F, -1.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(1.5F, 4.9F, 0.5F));

        ModelPartData HandRight_FingerBase_2 = HandRight.addChild("HandRight_FingerBase_2", ModelPartBuilder.create(), ModelTransform.origin(48.9F, 0.5F, 8.1F));

        ModelPartData HandRight_Finger_2 = HandRight_FingerBase_2.addChild("HandRight_Finger_2", ModelPartBuilder.create(), ModelTransform.origin(-45.9F, 3.4F, -4.1F));

        ModelPartData HandRight_FingerBase_3 = HandRight.addChild("HandRight_FingerBase_3", ModelPartBuilder.create().uv(117, 154).cuboid(-2.5F, 3.0F, -1.5F, 5.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(1.5F, -1.0F, 6.5F));

        ModelPartData HandRight_Finger_3 = HandRight_FingerBase_3.addChild("HandRight_Finger_3", ModelPartBuilder.create().uv(119, 162).cuboid(-4.0F, -5.9F, -2.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
                .uv(122, 149).cuboid(1.0F, -1.6F, -1.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(1.5F, 4.9F, 0.5F));

        ModelPartData HandRight_Thumb = HandRight.addChild("HandRight_Thumb", ModelPartBuilder.create().uv(95, 163).cuboid(-1.75F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(97, 157).cuboid(-0.75F, 1.5F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(-0.02F)), ModelTransform.origin(7.15F, -1.0F, 0.6F));

       ModelPartData ArmLeft = body_no_legs.addChild("ArmLeft", ModelPartBuilder.create().uv(105, 171).cuboid(0.0F, -3.5F, -5.0F, 10.0F, 41.0F, 10.0F, new Dilation(0.0F)), ModelTransform.origin(16.5F, -34.1667F, 0.5F));

        ModelPartData HandLeft = ArmLeft.addChild("HandLeft", ModelPartBuilder.create(), ModelTransform.origin(-42.0F, 39.5F, -4.0F));

        ModelPartData HandLeft_FingerBase_1 = HandLeft.addChild("HandLeft_FingerBase_1", ModelPartBuilder.create().uv(119, 162).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(50.5F, -2.0F, 0.5F));

        ModelPartData HandLeft_Finger_1 = HandLeft_FingerBase_1.addChild("HandLeft_Finger_1", ModelPartBuilder.create().uv(117, 154).cuboid(44.0F, -1.9F, -1.0F, 5.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(122, 149).cuboid(43.0F, -1.6F, -0.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(-47.5F, 5.9F, -0.5F));

        ModelPartData HandLeft_FingerBase_2 = HandLeft.addChild("HandLeft_FingerBase_2", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 3.5F));

        ModelPartData HandLeft_Finger_2 = HandLeft_FingerBase_2.addChild("HandLeft_Finger_2", ModelPartBuilder.create(), ModelTransform.origin(3.0F, 3.9F, 0.0F));

        ModelPartData HandLeft_FingerBase_3 = HandLeft.addChild("HandLeft_FingerBase_3", ModelPartBuilder.create().uv(119, 162).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(50.5F, -2.0F, 7.5F));

        ModelPartData HandLeft_Finger_3 = HandLeft_FingerBase_3.addChild("HandLeft_Finger_3", ModelPartBuilder.create().uv(117, 154).cuboid(44.0F, -1.9F, -1.0F, 5.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(122, 149).cuboid(43.0F, -1.6F, -0.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(-47.5F, 5.9F, -0.5F));

        ModelPartData HandLeft_Thumb = HandLeft.addChild("HandLeft_Thumb", ModelPartBuilder.create().uv(95, 163).cuboid(-1.25F, -0.5F, -1.5F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(97, 157).cuboid(-1.25F, 2.5F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(-0.02F)), ModelTransform.origin(43.65F, -2.0F, 1.6F));

        ModelPartData LegRight = root.addChild("LegRight", ModelPartBuilder.create().uv(44, 228).cuboid(-6.5F, -0.5F, -6.5F, 13.0F, 15.0F, 13.0F, new Dilation(0.0F)), ModelTransform.origin(-9.0F, -14.5F, -1.5F));

        ModelPartData LegLeft = root.addChild("LegLeft", ModelPartBuilder.create().uv(100, 228).cuboid(-6.5F, -0.5F, -6.5F, 13.0F, 15.0F, 13.0F, new Dilation(0.0F)), ModelTransform.origin(9.0F, -14.5F, -1.5F));
        return TexturedModelData.of(modelData, 256, 256);
    }

    @Override
    public void setAngles(CaveTrollEntityRenderState state) {
        super.setAngles(state);

        if(!state.isSprinting && !state.isControlled) {
            this.walkingAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 8.0f, 12.5f);
        }
        else {
            this.chaseAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 2.0f, 2.0f);
        }

        this.scavengingAnimation.apply(state.scavengingAnimationState, state.age);
        this.startSittingAnimation.apply(state.startSittingAnimationState, state.age);
        this.stopSittingAnimation.apply(state.stopSittingAnimationState, state.age);
        this.startSleepingAnimation.apply(state.startSleepingAnimationState, state.age);
        this.sleepingAnimation.apply(state.sleepingAnimationState, state.age);
        this.stopSleepingAnimation.apply(state.stopSleepingANimationState, state.age);
    }

    public void setArmAngle(MatrixStack matrices) {
        this.getRootPart().applyTransform(matrices);
        this.rootChild.applyTransform(matrices);
        this.upperBody.applyTransform(matrices);
        this.rightArm.applyTransform(matrices);
    }
}
