package net.sevenstars.middleearth.entity.beasts.cave_troll;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class CaveTrollEntityModel extends HierarchicalModel<CaveTrollEntity> {
    private final ModelPart rightArm;
    private final ModelPart upperBody;
    private final ModelPart rootChild;
    private final ModelPart chain;

    protected CaveTrollEntityModel(ModelPart root) {
        rootChild = root.getChild("root");
        upperBody = rootChild.getChild("body_no_legs");
        rightArm = upperBody.getChild("ArmRight");
        chain = upperBody.getChild("body_no_limbs").getChild("Head").getChild("chain");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();

        return LayerDefinition.create(modelData, 256, 256);
    }

    public static MeshDefinition getModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition root = modelPartData.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_no_legs = root.addOrReplaceChild("body_no_legs", CubeListBuilder.create(), PartPose.offset(0.0F, -15.3333F, -0.5F));

        PartDefinition body_no_limbs = body_no_legs.addOrReplaceChild("body_no_limbs", CubeListBuilder.create(), PartPose.offset(-0.5F, 0.3333F, 0.5F));

        PartDefinition body = body_no_limbs.addOrReplaceChild("body", CubeListBuilder.create().texOffs(156, 199).addBox(-16.0F, -40.0F, -9.0F, 33.0F, 40.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Loincloth_Front = body.addOrReplaceChild("Loincloth_Front", CubeListBuilder.create().texOffs(190, 122).addBox(-17.0F, 0.0F, 0.0F, 33.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 0.0F, -9.0F));

        PartDefinition Loincloth_Back = body_no_limbs.addOrReplaceChild("Loincloth_Back", CubeListBuilder.create().texOffs(190, 134).addBox(-17.0F, 0.0F, 0.0F, 33.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 0.0F, 8.0F));

        PartDefinition Head = body_no_limbs.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(172, 161).addBox(-8.5F, -8.5F, -8.5F, 17.0F, 17.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -34.5F, -8.5F));

        PartDefinition chain = Head.addOrReplaceChild("chain", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition head_chain = chain.addOrReplaceChild("head_chain", CubeListBuilder.create().texOffs(0, 21).addBox(0.0F, -14.0F, -2.0F, 18.0F, 17.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(-9.0F, 5.5F, -1.5F));

        PartDefinition chain_hanging = chain.addOrReplaceChild("chain_hanging", CubeListBuilder.create().texOffs(45, 30).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 11.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 9.0F, -2.0F));

        PartDefinition jaw = Head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(143, 144).addBox(-8.5F, -1.5F, -5.5F, 17.0F, 4.0F, 6.0F, new CubeDeformation(0.01F))
                .texOffs(201, 150).addBox(-4.5F, -4.5F, -5.5F, 9.0F, 3.0F, 1.0F, new CubeDeformation(0.01F))
                .texOffs(166, 162).addBox(4.5F, -5.5F, -5.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.01F))
                .texOffs(166, 162).mirror().addBox(-6.5F, -5.5F, -5.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, 6.0F, -4.0F, 0.0436F, 0.0F, 0.0F));

        PartDefinition ArmRight = body_no_legs.addOrReplaceChild("ArmRight", CubeListBuilder.create().texOffs(62, 171).addBox(-10.0F, -3.5F, -5.0F, 10.0F, 41.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-16.5F, -34.1667F, 0.5F));

        PartDefinition HandRight = ArmRight.addOrReplaceChild("HandRight", CubeListBuilder.create(), PartPose.offset(-9.0F, 39.5F, -3.0F));

        PartDefinition HandRight_FingerBase_1 = HandRight.addOrReplaceChild("HandRight_FingerBase_1", CubeListBuilder.create().texOffs(117, 154).addBox(-2.5F, 3.0F, -1.5F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -1.0F, -0.5F));

        PartDefinition HandRight_Finger_1 = HandRight_FingerBase_1.addOrReplaceChild("HandRight_Finger_1", CubeListBuilder.create().texOffs(119, 162).addBox(-4.0F, -5.9F, -2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(122, 149).addBox(1.0F, -1.6F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 4.9F, 0.5F));

        PartDefinition HandRight_FingerBase_2 = HandRight.addOrReplaceChild("HandRight_FingerBase_2", CubeListBuilder.create(), PartPose.offset(48.9F, 0.5F, 8.1F));

        PartDefinition HandRight_Finger_2 = HandRight_FingerBase_2.addOrReplaceChild("HandRight_Finger_2", CubeListBuilder.create(), PartPose.offset(-45.9F, 3.4F, -4.1F));

        PartDefinition HandRight_FingerBase_3 = HandRight.addOrReplaceChild("HandRight_FingerBase_3", CubeListBuilder.create().texOffs(117, 154).addBox(-2.5F, 3.0F, -1.5F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -1.0F, 6.5F));

        PartDefinition HandRight_Finger_3 = HandRight_FingerBase_3.addOrReplaceChild("HandRight_Finger_3", CubeListBuilder.create().texOffs(119, 162).addBox(-4.0F, -5.9F, -2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(122, 149).addBox(1.0F, -1.6F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 4.9F, 0.5F));

        PartDefinition HandRight_Thumb = HandRight.addOrReplaceChild("HandRight_Thumb", CubeListBuilder.create().texOffs(95, 163).addBox(-1.75F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(97, 157).addBox(-0.75F, 1.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.02F)), PartPose.offset(7.15F, -1.0F, 0.6F));

        PartDefinition ArmLeft = body_no_legs.addOrReplaceChild("ArmLeft", CubeListBuilder.create().texOffs(105, 171).addBox(0.0F, -3.5F, -5.0F, 10.0F, 41.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(16.5F, -34.1667F, 0.5F));

        PartDefinition HandLeft = ArmLeft.addOrReplaceChild("HandLeft", CubeListBuilder.create(), PartPose.offset(-42.0F, 39.5F, -4.0F));

        PartDefinition HandLeft_FingerBase_1 = HandLeft.addOrReplaceChild("HandLeft_FingerBase_1", CubeListBuilder.create().texOffs(119, 162).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(50.5F, -2.0F, 0.5F));

        PartDefinition HandLeft_Finger_1 = HandLeft_FingerBase_1.addOrReplaceChild("HandLeft_Finger_1", CubeListBuilder.create().texOffs(117, 154).addBox(44.0F, -1.9F, -1.0F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(122, 149).addBox(43.0F, -1.6F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-47.5F, 5.9F, -0.5F));

        PartDefinition HandLeft_FingerBase_2 = HandLeft.addOrReplaceChild("HandLeft_FingerBase_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 3.5F));

        PartDefinition HandLeft_Finger_2 = HandLeft_FingerBase_2.addOrReplaceChild("HandLeft_Finger_2", CubeListBuilder.create(), PartPose.offset(3.0F, 3.9F, 0.0F));

        PartDefinition HandLeft_FingerBase_3 = HandLeft.addOrReplaceChild("HandLeft_FingerBase_3", CubeListBuilder.create().texOffs(119, 162).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(50.5F, -2.0F, 7.5F));

        PartDefinition HandLeft_Finger_3 = HandLeft_FingerBase_3.addOrReplaceChild("HandLeft_Finger_3", CubeListBuilder.create().texOffs(117, 154).addBox(44.0F, -1.9F, -1.0F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(122, 149).addBox(43.0F, -1.6F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-47.5F, 5.9F, -0.5F));

        PartDefinition HandLeft_Thumb = HandLeft.addOrReplaceChild("HandLeft_Thumb", CubeListBuilder.create().texOffs(95, 163).addBox(-1.25F, -0.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(97, 157).addBox(-1.25F, 2.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.02F)), PartPose.offset(43.65F, -2.0F, 1.6F));

        PartDefinition LegRight = root.addOrReplaceChild("LegRight", CubeListBuilder.create().texOffs(44, 228).addBox(-6.5F, -0.5F, -6.5F, 13.0F, 15.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, -14.5F, -1.5F));

        PartDefinition LegLeft = root.addOrReplaceChild("LegLeft", CubeListBuilder.create().texOffs(100, 228).addBox(-6.5F, -0.5F, -6.5F, 13.0F, 15.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(9.0F, -14.5F, -1.5F));

        return modelData;
    }

    @Override
    public void setupAnim(CaveTrollEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        chain.visible = entity.isTamed();

        if(!entity.isSprinting()
                && !(entity.getControllingPassenger() != null && entity.getControllingPassenger().isSprinting())
                && !entity.isCharging()) {
            this.animateWalk(CaveTrollAnimations.PASSIVE_WALK, limbSwing, limbSwingAmount, 10.0F, 10.0F);
        }
        else {
            this.animateWalk(CaveTrollAnimations.RUN, limbSwing, limbSwingAmount, 2.0F, 2.0F);
        }

        this.animate(entity.scavengingAnimationState, CaveTrollAnimations.INSPECT, ageInTicks);
        this.animate(entity.startSittingAnimationState, CaveTrollAnimations.STANDING_TO_SITTING, ageInTicks);
        this.animate(entity.stopSittingAnimationState, CaveTrollAnimations.STANDING_FROM_SITTING, ageInTicks);
        this.animate(entity.startSleepingAnimationState, CaveTrollAnimations.SLEEP_LAYING_DOWN, ageInTicks);
        this.animate(entity.sleepingAnimationState, CaveTrollAnimations.SLEEP_LAYING_DOWN, ageInTicks);
        this.animate(entity.stopSleepingAnimationState, CaveTrollAnimations.SLEEP_LAYING_DOWN_STANDING_UP, ageInTicks);
        this.animate(entity.roaringAnimationState, CaveTrollAnimations.SCREAM, ageInTicks);
        this.animate(entity.smashingAnimationState, CaveTrollAnimations.OVERHAND_SMASH, ageInTicks);
    }

    public void setArmAngle(PoseStack matrices) {
        this.root().translateAndRotate(matrices);
        this.rootChild.translateAndRotate(matrices);
        this.upperBody.translateAndRotate(matrices);
        this.rightArm.translateAndRotate(matrices);
    }

    @Override
    public ModelPart root() {
        return this.rootChild;
    }
}
