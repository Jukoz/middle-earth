package net.sevenstars.middleearth.entity.beasts.trolls.stone;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class StoneTrollModel extends HierarchicalModel<StoneTrollEntity> {
    private final ModelPart r;
    private final ModelPart head;

    public StoneTrollModel(ModelPart root) {
        this.r = root.getChild("r");
        this.head = r.getChild("upperbody").getChild("upperbodynoarms").getChild("head");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition r = modelPartData.addOrReplaceChild("r", CubeListBuilder.create(), PartPose.offset(0.0F, 26.0F, 0.0F));

        PartDefinition upperbody = r.addOrReplaceChild("upperbody", CubeListBuilder.create(), PartPose.offset(0.0F, -20.0F, 1.0F));

        PartDefinition arms = upperbody.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, -1.0F));

        PartDefinition right_arm = arms.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(28, 82).addBox(-5.0F, -2.0F, -4.0F, 6.0F, 36.0F, 8.0F, new CubeDeformation(0.2F))
                .texOffs(84, 96).addBox(-5.0F, -2.0F, -4.0F, 6.0F, 36.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-14.0F, -46.0F, 2.0F));

        PartDefinition left_arm = arms.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 82).addBox(-3.0F, -2.0F, -4.0F, 6.0F, 36.0F, 8.0F, new CubeDeformation(0.2F))
                .texOffs(56, 96).mirror().addBox(-3.0F, -2.0F, -4.0F, 6.0F, 36.0F, 8.0F, new CubeDeformation(0.0F)).mirror(), PartPose.offset(15.0F, -46.0F, 2.0F));

        PartDefinition upperbodynoarms = upperbody.addOrReplaceChild("upperbodynoarms", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -1.0F));

        PartDefinition head = upperbodynoarms.addOrReplaceChild("head", CubeListBuilder.create().texOffs(74, 0).addBox(-6.5F, -9.6306F, -6.7491F, 13.0F, 14.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(62, 70).addBox(-6.5F, -9.6306F, -6.7491F, 13.0F, 14.0F, 12.0F, new CubeDeformation(0.2F))
                .texOffs(0, 0).mirror().addBox(6.4F, -5.6306F, -1.7491F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 5).addBox(-10.4F, -5.6306F, -1.7491F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -27.3694F, -1.2509F));

        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(62, 0).addBox(-2.5F, -1.5F, -3.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.1306F, -4.7491F, -0.3054F, 0.0F, 0.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(126, 29).addBox(-4.5F, -2.0F, -4.0F, 9.0F, 4.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 3.3694F, -2.7491F));

        PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(192, 158).addBox(-33.0F, -55.2728F, -0.4462F, 13.0F, 19.0F, 12.0F, new CubeDeformation(0.1F)), PartPose.offset(26.5F, 51.3694F, -5.7491F));

        PartDefinition hair1 = hair.addOrReplaceChild("hair1", CubeListBuilder.create().texOffs(204, 105).addBox(-6.3F, -22.2272F, -5.9945F, 13.0F, 19.0F, 12.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-26.7F, -37.5249F, 16.9945F, 0.5236F, 0.0F, 0.0F));

        PartDefinition torso = upperbodynoarms.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 41).addBox(-13.0F, -50.0F, -4.0F, 25.0F, 29.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-13.0F, -50.0F, -4.0F, 25.0F, 29.0F, 12.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 22.0F, 0.0F));

        PartDefinition frontloin = torso.addOrReplaceChild("frontloin", CubeListBuilder.create().texOffs(74, 48).addBox(-12.5F, -1.5F, -0.5F, 25.0F, 21.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offset(-0.5F, -26.5F, -3.504F));

        PartDefinition backloin = torso.addOrReplaceChild("backloin", CubeListBuilder.create().texOffs(74, 26).addBox(-12.5F, -1.5F, -0.5F, 25.0F, 21.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offset(-0.5F, -26.5F, 7.85F));

        PartDefinition bodynoarmswithlegs = upperbodynoarms.addOrReplaceChild("bodynoarmswithlegs", CubeListBuilder.create(), PartPose.offset(0.0F, -11.0F, 0.0F));

        PartDefinition legs = r.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_leg = legs.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(126, 0).addBox(-5.0F, 0.5F, -5.0F, 10.0F, 19.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(112, 99).addBox(-5.0F, 0.5F, -5.0F, 10.0F, 19.0F, 10.0F, new CubeDeformation(0.2F)), PartPose.offset(-7.0F, -21.5F, 2.0F));

        PartDefinition left_leg = legs.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 126).addBox(-5.0F, 0.5F, -5.0F, 10.0F, 19.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(112, 70).addBox(-5.0F, 0.5F, -5.0F, 10.0F, 19.0F, 10.0F, new CubeDeformation(0.2F)), PartPose.offset(6.0F, -21.5F, 2.0F));

        PartDefinition club = modelPartData.addOrReplaceChild("club", CubeListBuilder.create(), PartPose.offsetAndRotation(-15.3115F, 10.3091F, -12.2077F, -0.1309F, 0.0F, 0.0873F));

        PartDefinition cube_r1 = club.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 158).mirror().addBox(-4.5F, -4.5F, -22.0F, 9.0F, 9.0F, 22.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(17, 194).addBox(-3.5F, -3.5F, 0.0F, 7.0F, 7.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1993F, 0.0145F, -0.0979F));
        return LayerDefinition.create(modelData, 256, 256);
    }
    @Override
    public void setupAnim(StoneTrollEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.setHeadAngles(netHeadYaw, headPitch);
        this.animateWalk(StoneTrollAnimations.WALK, limbSwing, limbSwingAmount, 1.0F, 2.5F);
        this.animate(entity.attackAnimationState, StoneTrollAnimations.ATTACK, ageInTicks);
        this.animate(entity.chargeAnimationState, StoneTrollAnimations.CHARGE, ageInTicks);
        this.animate(entity.throwingAnimationState, StoneTrollAnimations.THROW, ageInTicks);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30.0F, 30.0F);
        headPitch = Mth.clamp(headPitch, -25.0F, 40.0F);

        this.head.yRot = headYaw * 0.017453292F;
        this.head.xRot = headPitch * 0.017453292F;
    }

    @Override
    public ModelPart root() {
        return this.r;
    }
}
