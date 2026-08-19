package net.sevenstars.middleearth.entity.beasts.warg;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class WargModel extends HierarchicalModel<WargEntity> {
    private final ModelPart warg;
    private final ModelPart head;
    private final ModelPart mane;

    public WargModel(ModelPart root) {
        this.warg = root.getChild("root");
        ModelPart body = this.warg.getChild(PartNames.BODY);
        ModelPart upperBody = body.getChild("upper_body");
        ModelPart bodyNoLegs = upperBody.getChild("body_no_legs");
        this.head = bodyNoLegs.getChild(PartNames.HEAD);
        this.mane = bodyNoLegs.getChild("body_fur").getChild("mane");

    }

    public static LayerDefinition getTexturedModelData() {
        return LayerDefinition.create(getModelData(), 128, 128);
    }

    public static MeshDefinition getModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 6.0F, -2.0F, 0.0F, 1.5708F, 0.0F));
        PartDefinition body = root.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(), PartPose.offset(-2.6142F, 2.1138F, 1.5F));
        PartDefinition upperBody = body.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offset(-1.3858F, -1.1138F, -1.5F));
        PartDefinition bodyNoLegs = upperBody.addOrReplaceChild("body_no_legs", CubeListBuilder.create(), PartPose.offset(7.5F, -0.25F, 0.5F));
        PartDefinition bodyFur = bodyNoLegs.addOrReplaceChild("body_fur", CubeListBuilder.create().texOffs(0, 0).addBox(-6.5F, -7.5F, -6.0F, 14.0F, 15.0F, 13.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        bodyFur.addOrReplaceChild("mane", CubeListBuilder.create().texOffs(54, 15).addBox(-9.5F, -5.0F, 0.0F, 20.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -6.75F, 0.5F));
        bodyNoLegs.addOrReplaceChild("main_body", CubeListBuilder.create().texOffs(0, 49).addBox(-0.5F, -6.0F, -5.0F, 12.0F, 12.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.3F, 0.25F, 0.0F));

        PartDefinition head = bodyNoLegs.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create()
                .texOffs(74, 0).addBox(-1.5F, -3.25F, -5.0F, 10.0F, 5.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(74, 0).addBox(-1.5F, -3.25F, -5.0F, 10.0F, 5.0F, 10.0F, new CubeDeformation(0.025F))
                .texOffs(54, 31).addBox(-2.1142F, 1.6138F, -4.0F, 10.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(7.25F, -2.0F, 0.5F));
        head.addOrReplaceChild("snout", CubeListBuilder.create()
                .texOffs(54, 23).addBox(-9.5F, 0.5F, -2.5F, 15.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(46, 82).addBox(-0.644F, 4.522F, -2.5F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.05F)), PartPose.offset(8.0F, -1.5F, 0.5F));
        head.addOrReplaceChild("ear_right", CubeListBuilder.create(), PartPose.offsetAndRotation(4.0F, -2.25F, -4.478F, 0.0F, -0.8727F, 0.0F));
        head.addOrReplaceChild("ear_left", CubeListBuilder.create(), PartPose.offsetAndRotation(4.0F, -2.25F, 4.522F, 0.0F, 0.8727F, 0.0F));
        PartDefinition earRight = head.getChild("ear_right");
        earRight.addOrReplaceChild("earR_r1", CubeListBuilder.create().texOffs(82, 72).addBox(0.2802F, -4.8619F, -1.032F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, -0.022F, 0.0F, 0.0F, -1.0036F));
        PartDefinition earLeft = head.getChild("ear_left");
        earLeft.addOrReplaceChild("earL_r1", CubeListBuilder.create().texOffs(82, 79).addBox(0.2802F, -4.8619F, 0.01F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, 0.0F, 0.0F, 0.0F, -1.0036F));
        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create()
                .texOffs(54, 42).addBox(-5.5F, -0.25F, -2.0F, 15.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(46, 60).addBox(-5.564F, -1.75F, -2.0F, 15.0F, 2.0F, 4.0F, new CubeDeformation(-0.09F)), PartPose.offsetAndRotation(3.75F, 3.25F, 0.0F, 0.0F, 0.0F, 0.2618F));
        jaw.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(8, 78).addBox(-1.8F, 0.1F, -1.5F, 9.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -0.25F, 0.0F, 0.0F, 0.0F, -0.0349F));
        head.addOrReplaceChild("headmane", CubeListBuilder.create().texOffs(68, 66).addBox(-7.5F, -5.0F, 0.0F, 15.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -4.25F, 0.0F));

        PartDefinition waitTail = bodyNoLegs.addOrReplaceChild("wait_tail", CubeListBuilder.create().texOffs(0, 28).addBox(-0.3F, -2.0F, -5.5F, 16.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(-20.5F, -3.75F, 0.5F));
        waitTail.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.829F));
        PartDefinition tail = waitTail.getChild("tail");
        tail.addOrReplaceChild("bone fluff_r1", CubeListBuilder.create()
                .texOffs(0, 108).addBox(-13.0F, -5.0F, -0.75F, 15.0F, 2.0F, 1.0F, new CubeDeformation(0.4F))
                .texOffs(0, 72).addBox(-13.0F, -5.0F, -0.75F, 15.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(46, 49).addBox(-18.0F, -6.0F, -2.75F, 20.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5979F, 4.1769F, 0.25F, 0.0F, 0.0F, 0.1745F));

        upperBody.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(30, 72).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(9.0F, 4.0F, -2.5F));
        upperBody.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(30, 89).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(9.0F, 4.0F, 5.0F));

        PartDefinition backBody = body.addOrReplaceChild("back_body", CubeListBuilder.create(), PartPose.offset(2.3142F, -2.1138F, -0.5F));
        PartDefinition leftBackLeg = backBody.addOrReplaceChild("left_back_leg", CubeListBuilder.create().texOffs(46, 66).addBox(-3.5F, -1.0F, -1.0F, 7.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-14.7F, -2.0F, 3.5F));
        leftBackLeg.addOrReplaceChild("left_back_foot", CubeListBuilder.create().texOffs(0, 78).addBox(-2.0F, -2.0F, -1.5F, 4.0F, 12.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(-3.2F, 10.0F, 0.5F));
        PartDefinition rightBackLeg = backBody.addOrReplaceChild("right_back_leg", CubeListBuilder.create().texOffs(46, 66).addBox(-4.5F, -1.0F, -4.0F, 7.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-13.7F, -2.0F, -2.5F));
        rightBackLeg.addOrReplaceChild("right_back_foot", CubeListBuilder.create().texOffs(0, 93).addBox(-2.0F, -2.0F, -1.5F, 4.0F, 12.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(-4.2F, 10.0F, -1.5F));

        return modelData;
    }

    public static MeshDefinition getEmptyModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 6.0F, -2.0F, 0.0F, 1.5708F, 0.0F));
        PartDefinition body = root.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(), PartPose.offset(-2.6142F, 2.1138F, 1.5F));
        PartDefinition upperBody = body.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offset(-1.3858F, -1.1138F, -1.5F));
        PartDefinition bodyNoLegs = upperBody.addOrReplaceChild("body_no_legs", CubeListBuilder.create(), PartPose.offset(7.5F, -0.25F, 0.5F));
        PartDefinition bodyFur = bodyNoLegs.addOrReplaceChild("body_fur", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        bodyFur.addOrReplaceChild("mane", CubeListBuilder.create(), PartPose.offset(-2.0F, -6.75F, 0.5F));
        bodyNoLegs.addOrReplaceChild("main_body", CubeListBuilder.create(), PartPose.offset(-5.3F, 0.25F, 0.0F));

        PartDefinition head = bodyNoLegs.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(7.25F, -2.0F, 0.5F));
        head.addOrReplaceChild("snout", CubeListBuilder.create(), PartPose.offset(8.0F, -1.5F, 0.5F));
        head.addOrReplaceChild("ear_right", CubeListBuilder.create(), PartPose.offsetAndRotation(4.0F, -2.25F, -4.478F, 0.0F, -0.8727F, 0.0F));
        head.addOrReplaceChild("ear_left", CubeListBuilder.create(), PartPose.offsetAndRotation(4.0F, -2.25F, 4.522F, 0.0F, 0.8727F, 0.0F));
        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create(), PartPose.offsetAndRotation(3.75F, 3.25F, 0.0F, 0.0F, 0.0F, 0.2618F));
        jaw.addOrReplaceChild("tongue", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, -0.25F, 0.0F, 0.0F, 0.0F, -0.0349F));
        head.addOrReplaceChild("headmane", CubeListBuilder.create(), PartPose.offset(-1.0F, -4.25F, 0.0F));

        PartDefinition waitTail = bodyNoLegs.addOrReplaceChild("wait_tail", CubeListBuilder.create(), PartPose.offset(-20.5F, -3.75F, 0.5F));
        waitTail.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.829F));

        upperBody.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(9.0F, 4.0F, -2.5F));
        upperBody.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(9.0F, 4.0F, 5.0F));

        PartDefinition backBody = body.addOrReplaceChild("back_body", CubeListBuilder.create(), PartPose.offset(2.3142F, -2.1138F, -0.5F));
        PartDefinition leftBackLeg = backBody.addOrReplaceChild("left_back_leg", CubeListBuilder.create(), PartPose.offset(-14.7F, -2.0F, 3.5F));
        leftBackLeg.addOrReplaceChild("left_back_foot", CubeListBuilder.create(), PartPose.offset(-3.2F, 10.0F, 0.5F));
        PartDefinition rightBackLeg = backBody.addOrReplaceChild("right_back_leg", CubeListBuilder.create(), PartPose.offset(-13.7F, -2.0F, -2.5F));
        rightBackLeg.addOrReplaceChild("right_back_foot", CubeListBuilder.create(), PartPose.offset(-4.2F, 10.0F, -1.5F));

        return modelData;
    }

    @Override
    public void setupAnim(WargEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        if (!entity.hasControllingPassenger()) {
            this.head.yRot = Mth.clamp(netHeadYaw, -30.0F, 30.0F) * Mth.DEG_TO_RAD;
            this.head.xRot = Mth.clamp(headPitch, -25.0F, 40.0F) * Mth.DEG_TO_RAD;
        }

        boolean running = entity.isRunning() || entity.isSprinting()
                || (entity.getControllingPassenger() != null && entity.getControllingPassenger().isSprinting())
                || entity.isCharging();

        if(!running) {
            float speed = entity.getControllingPassenger() != null ? 2.0F : 3.5F;
            this.animateWalk(WargAnimations.WALK, limbSwing, limbSwingAmount, speed, 2.0F);
        }
        else {
            this.animateWalk(WargAnimations.RUN, limbSwing, limbSwingAmount, 1.4F, 1.2F);
        }

        this.mane.visible = !(entity.isSaddled() || entity.isWearingBodyArmor());
        this.animate(entity.idleAnimationState, WargAnimations.IDLE, ageInTicks);
        this.animate(entity.attackAnimationState, WargAnimations.BITE, ageInTicks);
        this.animate(entity.startSittingAnimationState, WargAnimations.STANDING_TO_SITTING, ageInTicks);
        this.animate(entity.stopSittingAnimationState, WargAnimations.SITTING_TO_STANDING, ageInTicks);
        this.animate(entity.sittingAnimationState, WargAnimations.SITTING, ageInTicks);
    }

    @Override
    public ModelPart root() {
        return this.warg;
    }
}
