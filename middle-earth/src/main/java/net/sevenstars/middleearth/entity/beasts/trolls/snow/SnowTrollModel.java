package net.sevenstars.middleearth.entity.beasts.trolls.snow;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
public class SnowTrollModel extends HierarchicalModel<SnowTrollEntity> {
    private final ModelPart troll;
    private final ModelPart head;

    public SnowTrollModel(ModelPart root) {
        this.troll = root.getChild("roots");
        this.head = troll.getChild("torso").getChild("head");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition roots = modelPartData.addOrReplaceChild("roots", CubeListBuilder.create(), PartPose.offset(0.0F, 26.0F, 0.0F));

        PartDefinition legs = roots.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_leg = legs.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(126, 98).mirror().addBox(-4.0F, 1.0F, -5.0F, 10.0F, 19.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(118, 69).addBox(-4.0F, 1.0F, -5.0F, 10.0F, 19.0F, 10.0F, new CubeDeformation(0.2F)), PartPose.offset(-8.0F, -22.0F, 5.0F));

        PartDefinition left_leg = legs.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(122, 0).addBox(-6.0F, 0.9848F, -4.8264F, 10.0F, 19.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(100, 40).addBox(-6.0F, 0.9848F, -4.8264F, 10.0F, 19.0F, 10.0F, new CubeDeformation(0.2F)), PartPose.offset(7.0F, -22.0F, 5.0F));

        PartDefinition torso = roots.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 41).addBox(-12.5F, -28.5F, -6.0865F, 25.0F, 29.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-12.5F, -28.5F, -6.0865F, 25.0F, 29.0F, 12.0F, new CubeDeformation(0.2F))
                .texOffs(74, 0).addBox(-5.5F, -29.5F, -2.0905F, 11.0F, 27.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -21.5F, 5.0865F, 1.0472F, 0.0F, 0.0F));

        PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 82).addBox(-7.0F, -11.0F, -12.0F, 13.0F, 14.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(56, 64).addBox(-7.0F, -11.0F, -12.0F, 13.0F, 24.0F, 18.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.5F, -24.5F, -1.0865F, -1.0036F, 0.0F, 0.0F));

        PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 5).mirror().addBox(-4.0F, -2.0F, -0.5F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.0F, -5.0F, -6.5F, 0.0F, 0.829F, 0.0F));

        PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -2.0F, -0.5F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -5.0F, -6.5F, 0.0F, -0.7854F, 0.0F));

        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(62, 0).addBox(-2.5F, -1.5F, -3.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -4.5F, -10.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(66, 48).addBox(-4.5F, -3.3653F, -4.8333F, 9.0F, 2.0F, 0.0F, new CubeDeformation(0.3F))
                .texOffs(62, 41).addBox(-4.5F, -0.7693F, -4.8333F, 9.0F, 2.0F, 4.0F, new CubeDeformation(0.3F))
                .texOffs(126, 127).addBox(-4.5F, -2.8653F, -4.8333F, 9.0F, 10.0F, 7.0F, new CubeDeformation(0.5F)), PartPose.offset(-0.5F, 3.8653F, -7.1667F));

        PartDefinition arms = torso.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, 7.5F, -20.0865F, -1.0472F, 0.0F, 0.0F));

        PartDefinition right_arm = arms.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(70, 106).addBox(-6.0F, -3.0F, -3.0F, 6.0F, 36.0F, 8.0F, new CubeDeformation(0.2F))
                .texOffs(0, 108).mirror().addBox(-6.0F, -3.0F, -3.0F, 6.0F, 36.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-13.0F, -32.0F, -18.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition left_arm = arms.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(42, 106).addBox(-1.0F, -3.0F, -2.0F, 6.0F, 36.0F, 8.0F, new CubeDeformation(0.2F))
                .texOffs(98, 106).addBox(-1.0F, -3.0F, -2.0F, 6.0F, 36.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, -32.0F, -17.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition bone = torso.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void setupAnim(SnowTrollEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.setHeadAngles(netHeadYaw, headPitch);
        this.animateWalk(SnowTrollAnimations.WALKING, limbSwing, limbSwingAmount, 1.0F, 2.5F);
        this.animate(entity.attackAnimationState, SnowTrollAnimations.ATTACK, ageInTicks);
        this.animate(entity.chargeAnimationState, SnowTrollAnimations.CHARGING, ageInTicks);
        this.animate(entity.throwingAnimationState, SnowTrollAnimations.THROWING, ageInTicks);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30.0F, 30.0F);
        headPitch = Mth.clamp(headPitch, -25.0F, 40.0F) + -60.0F;

        this.head.yRot = headYaw * 0.017453292F;
        this.head.xRot = headPitch * 0.017453292F;
    }

    @Override
    public ModelPart root() {
        return this.troll;
    }
}
