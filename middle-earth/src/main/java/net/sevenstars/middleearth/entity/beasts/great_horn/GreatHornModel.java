package net.sevenstars.middleearth.entity.beasts.great_horn;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class GreatHornModel extends HierarchicalModel<GreatHornEntity> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart frontHalf;
    private final ModelPart headNeck;
    private final ModelPart topHead;
    private final ModelPart rightAntler;
    private final ModelPart leftAntler;
    private final ModelPart beard;

    public GreatHornModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.frontHalf = this.body.getChild("front_half");
        this.headNeck = this.frontHalf.getChild("head_neck");
        this.topHead = this.headNeck.getChild("top_head");
        this.rightAntler = this.topHead.getChild("right_antler");
        this.leftAntler = this.topHead.getChild("left_antler");
        this.beard = this.topHead.getChild("beard");

    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, -10.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -8.5F, 12.0F));

        PartDefinition front_half = body.addOrReplaceChild("front_half", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -13.0F));

        PartDefinition front_body = front_half.addOrReplaceChild("front_body", CubeListBuilder.create().texOffs(0, 15).addBox(-6.5F, -8.0F, -6.5F, 13.0F, 16.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(0, 15).addBox(-6.5F, -8.0F, -6.5F, 13.0F, 16.0F, 13.0F, new CubeDeformation(0.2F))
                .texOffs(37, 133).addBox(-6.5F, 8.4F, -6.5F, 13.0F, 4.0F, 13.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.5F, 2.5F));

        PartDefinition saddle = front_body.addOrReplaceChild("saddle", CubeListBuilder.create().texOffs(88, 50).addBox(-7.0F, -1.0F, 2.0F, 14.0F, 22.0F, 6.0F, new CubeDeformation(0.3F))
                .texOffs(84, 18).addBox(-7.0F, -2.0F, -6.6F, 14.0F, 23.0F, 8.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, -6.5F, 6.5F));

        PartDefinition seat_back_r1 = saddle.addOrReplaceChild("seat_back_r1", CubeListBuilder.create().texOffs(49, 78).addBox(-4.5F, -4.5F, -1.0F, 9.0F, 5.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.0F, 1.0F, 8.4F, -0.2182F, 0.0F, 0.0F));

        PartDefinition frontLeftLeg = front_half.addOrReplaceChild("front_left_leg", CubeListBuilder.create().texOffs(0, 108).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 108).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(4.5F, 8.5F, -1.0F));

        PartDefinition frontRightLeg = front_half.addOrReplaceChild("front_right_leg", CubeListBuilder.create().texOffs(16, 108).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 108).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(-4.5F, 8.5F, -1.0F));

        PartDefinition head_neck = front_half.addOrReplaceChild("head_neck", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, -3.0F));

        PartDefinition neck = head_neck.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(40, 0).addBox(-3.5F, -6.0F, -2.0F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(40, 0).addBox(-3.5F, -6.0F, -2.0F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(53, 53).addBox(-3.5F, -6.0F, -2.0F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, -1.5F, -2.0F));

        PartDefinition top_head = head_neck.addOrReplaceChild("top_head", CubeListBuilder.create().texOffs(96, 0).addBox(-3.5F, -7.0F, -6.0F, 7.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(96, 0).addBox(-3.5F, -7.0F, -6.0F, 7.0F, 7.0F, 9.0F, new CubeDeformation(0.2F))
                .texOffs(51, 35).addBox(-3.5F, -7.0F, -6.0F, 7.0F, 7.0F, 9.0F, new CubeDeformation(0.3F))
                .texOffs(69, 0).addBox(-2.5F, -4.0F, -11.0F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(69, 0).addBox(-2.5F, -4.0F, -11.0F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.2F))
                .texOffs(52, 26).addBox(-2.5F, -4.0F, -11.0F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.3F))
                .texOffs(86, 99).addBox(3.9F, -2.0F, -7.0F, 0.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(86, 91).addBox(-3.9F, -2.0F, -7.0F, 0.0F, 7.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.5F, 0.0F));

        PartDefinition earLeft = top_head.addOrReplaceChild("ear_left", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -4.0F, -1.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(0.0F, -4.0F, -1.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(2.5F, -6.0F, 1.0F, -0.7854F, 0.4363F, 0.0F));

        PartDefinition earRight = top_head.addOrReplaceChild("ear_right", CubeListBuilder.create().texOffs(9, 0).addBox(-0.5F, -4.0F, -1.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(9, 0).addBox(-0.5F, -4.0F, -1.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-3.0F, -6.0F, 1.0F, -0.7854F, -0.4363F, 0.0F));

        PartDefinition right_antler = top_head.addOrReplaceChild("right_antler", CubeListBuilder.create().texOffs(34, 120).mirror().addBox(-8.0F, 0.0F, -2.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(34, 120).mirror().addBox(-8.0F, 0.0F, -2.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -7.0F, 0.0F, 0.1812F, -0.3808F, 0.4252F));

        PartDefinition tip_r_armor_r1 = right_antler.addOrReplaceChild("tip_r_armor_r1", CubeListBuilder.create().texOffs(34, 124).mirror().addBox(-8.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-7.0F, 1.0F, 0.425F, 0.0F, 1.5708F, 0.0F));

        PartDefinition tip_r_r1 = right_antler.addOrReplaceChild("tip_r_r1", CubeListBuilder.create().texOffs(34, 124).mirror().addBox(-8.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.0F, 1.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition tip_r_horn_r1 = right_antler.addOrReplaceChild("tip_r_horn_r1", CubeListBuilder.create().texOffs(27, 87).mirror().addBox(-16.0F, 0.0F, -12.0F, 16.0F, 0.0F, 23.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.0F, 0.0F, 7.0F, 0.0F, 0.0F, 0.6109F));

        PartDefinition base_r_horn_r1 = right_antler.addOrReplaceChild("base_r_horn_r1", CubeListBuilder.create().texOffs(27, 112).addBox(-5.0F, 0.0F, -7.0F, 11.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, 0.0F, -2.0F, -1.1781F, 0.0F, 0.0F));

        PartDefinition left_antler = top_head.addOrReplaceChild("left_antler", CubeListBuilder.create().texOffs(54, 120).addBox(0.0F, 0.0F, -2.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(54, 120).addBox(0.0F, 0.0F, -2.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(3.0F, -7.0F, 0.0F, 0.1812F, 0.3808F, -0.4252F));

        PartDefinition base_l_horn_r1 = left_antler.addOrReplaceChild("base_l_horn_r1", CubeListBuilder.create().texOffs(50, 112).addBox(-6.0F, 0.0F, -7.0F, 11.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 0.0F, -2.0F, -1.1781F, 0.0F, 0.0F));

        PartDefinition tip_l_horn_r1 = left_antler.addOrReplaceChild("tip_l_horn_r1", CubeListBuilder.create().texOffs(62, 87).addBox(0.0F, 0.0F, -12.0F, 16.0F, 0.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 0.0F, 7.0F, 0.0F, 0.0F, -0.6109F));

        PartDefinition tip_l_armor_r1 = left_antler.addOrReplaceChild("tip_l_armor_r1", CubeListBuilder.create().texOffs(54, 124).addBox(0.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(7.0F, 1.0F, 0.375F, 0.0F, -1.5708F, 0.0F));

        PartDefinition tip_l_r1 = left_antler.addOrReplaceChild("tip_l_r1", CubeListBuilder.create().texOffs(54, 124).addBox(0.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 1.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition beard = top_head.addOrReplaceChild("beard", CubeListBuilder.create().texOffs(69, 3).addBox(0.0F, -1.0F, -6.0F, 0.0F, 18.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -5.0F));

        PartDefinition back_body = front_half.addOrReplaceChild("back_body", CubeListBuilder.create().texOffs(0, 44).addBox(-5.5F, -4.55F, -3.6F, 11.0F, 13.0F, 14.0F, new CubeDeformation(0.2F))
                .texOffs(0, 44).addBox(-5.5F, -4.5F, -4.0F, 11.0F, 13.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 128).addBox(-5.5F, 8.825F, -3.6F, 11.0F, 4.0F, 14.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, 13.0F));

        PartDefinition tail = back_body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -4.5F, 10.0F));

        PartDefinition tail_armor_r1 = tail.addOrReplaceChild("tail_armor_r1", CubeListBuilder.create().texOffs(39, 47).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.2F))
                .texOffs(39, 47).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition backLeftLeg = root.addOrReplaceChild("back_left_leg", CubeListBuilder.create().texOffs(0, 72).addBox(-2.25F, -4.25F, -3.75F, 5.0F, 10.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 72).addBox(-2.25F, -4.25F, -3.75F, 5.0F, 10.0F, 7.0F, new CubeDeformation(0.2F))
                .texOffs(4, 89).addBox(-2.25F, 5.75F, 0.25F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(4, 89).addBox(-2.25F, 5.75F, 0.25F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(4.75F, -4.75F, 19.75F));

        PartDefinition backRightLeg = root.addOrReplaceChild("back_right_leg", CubeListBuilder.create().texOffs(24, 72).addBox(-2.75F, -4.25F, -3.75F, 5.0F, 10.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(24, 72).addBox(-2.75F, -4.25F, -3.75F, 5.0F, 10.0F, 7.0F, new CubeDeformation(0.2F))
                .texOffs(28, 89).addBox(-1.75F, 5.75F, 0.25F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(28, 89).addBox(-1.75F, 5.75F, 0.25F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(-4.75F, -4.75F, 19.75F));
        return LayerDefinition.create(modelData, 128, 160);
    }

    @Override
    public void setupAnim(GreatHornEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animate(entity.idleAnimationState, GreatHornAnimations.IDLE, ageInTicks);
        this.animate(entity.earWigglingAnimationState, GreatHornAnimations.EAR_WIGGLE, ageInTicks);

        if(entity.attackAnimationState.isStarted()) {
            this.animate(entity.attackAnimationState, GreatHornAnimations.ATTACK, ageInTicks);
        }

        if(entity.bowAnimationState.isStarted()) {
            this.animate(entity.bowAnimationState, GreatHornAnimations.BOW, ageInTicks);
        } else {
            if(entity.gallopAnimationState.isStarted()) {
                this.animateWalk(GreatHornAnimations.GALLOP, limbSwing, limbSwingAmount, 1.0F, 2.5F);
            } else {
                this.animateWalk(GreatHornAnimations.WALK, limbSwing, limbSwingAmount, 2.75F, 2.5F);
            }
        }

        leftAntler.visible = true;
        rightAntler.visible = true;
        beard.skipDraw = false;
        if(entity.isBaby()) {
            leftAntler.visible = false;
            rightAntler.visible = false;
            beard.skipDraw = true;
        } else if(entity.hasCustomName() && "elkebies".equalsIgnoreCase(entity.getName().getString())) {
            leftAntler.visible = false;
            rightAntler.visible = false;
        }
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
