package net.sevenstars.middleearth.entity.beasts.trolls.petrified;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class PetrifiedTrollModel extends HierarchicalModel<PetrifiedTrollEntity> {
    private final ModelPart r;
    private final ModelPart head;
    public PetrifiedTrollModel(ModelPart root) {
        this.r = root.getChild("r");
        this.head = r.getChild("upperbody").getChild("upperbodynoarms").getChild("head");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition r = modelPartData.addOrReplaceChild("r", CubeListBuilder.create(), PartPose.offset(0.0F, 26.0F, 0.0F));

        PartDefinition upperBody = r.addOrReplaceChild("upperbody", CubeListBuilder.create(), PartPose.offset(0.0F, -20.0F, 1.0F));

        PartDefinition arms = upperBody.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, -1.0F));

        PartDefinition rightarm = arms.addOrReplaceChild("rightarm", CubeListBuilder.create().texOffs(28, 82).addBox(-5.0F, -2.0F, -4.0F, 6.0F, 36.0F, 8.0F, new CubeDeformation(0.2F))
                .texOffs(84, 96).addBox(-5.0F, -2.0F, -4.0F, 6.0F, 36.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-14.0F, -46.0F, 2.0F));

        PartDefinition leftArm = arms.addOrReplaceChild("leftarm", CubeListBuilder.create().texOffs(0, 82).mirror().addBox(-3.0F, -2.0F, -4.0F, 6.0F, 36.0F, 8.0F, new CubeDeformation(0.2F))
                .texOffs(56, 96).mirror().addBox(-3.0F, -2.0F, -4.0F, 6.0F, 36.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(15.0F, -46.0F, 2.0F));

        PartDefinition upperBodyNoArms = upperBody.addOrReplaceChild("upperbodynoarms", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -1.0F));

        PartDefinition head = upperBodyNoArms.addOrReplaceChild("head", CubeListBuilder.create().texOffs(74, 0).addBox(-6.5F, -9.6306F, -6.7491F, 13.0F, 14.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(62, 70).addBox(-6.5F, -9.6306F, -6.7491F, 13.0F, 14.0F, 12.0F, new CubeDeformation(0.2F))
                .texOffs(0, 0).mirror().addBox(6.4F, -5.6306F, -1.7491F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 5).addBox(-10.4F, -5.6306F, -1.7491F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -27.3694F, -1.2509F));

        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(62, 0).addBox(-2.5F, -1.5F, -3.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.1306F, -4.7491F, -0.3054F, 0.0F, 0.0F));

        PartDefinition jaw = head.addOrReplaceChild("Jaw", CubeListBuilder.create().texOffs(126, 29).addBox(-4.5F, -2.0F, -4.0F, 9.0F, 4.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 3.3694F, -2.7491F));

        PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(192, 158).addBox(-33.0F, -55.2728F, -0.4462F, 13.0F, 19.0F, 12.0F, new CubeDeformation(0.1F)), PartPose.offset(26.5F, 51.3694F, -5.7491F));

        PartDefinition hair1 = hair.addOrReplaceChild("hair1", CubeListBuilder.create().texOffs(204, 105).addBox(-6.3F, -22.2272F, -5.9945F, 13.0F, 19.0F, 12.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-26.7F, -37.5249F, 16.9945F, 0.5236F, 0.0F, 0.0F));

        PartDefinition torso = upperBodyNoArms.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 41).addBox(-13.0F, -50.0F, -4.0F, 25.0F, 29.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-13.0F, -50.0F, -4.0F, 25.0F, 29.0F, 12.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 22.0F, 0.0F));

        PartDefinition frontloin = torso.addOrReplaceChild("frontloin", CubeListBuilder.create().texOffs(74, 48).addBox(-12.5F, -1.5F, -0.5F, 25.0F, 21.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offset(-0.5F, -26.5F, -3.504F));

        PartDefinition backloin = torso.addOrReplaceChild("backloin", CubeListBuilder.create().texOffs(74, 26).addBox(-12.5F, -1.5F, -0.5F, 25.0F, 21.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offset(-0.5F, -26.5F, 7.85F));

        PartDefinition bodyNoArms = upperBodyNoArms.addOrReplaceChild("bodynoarmswithlegs", CubeListBuilder.create(), PartPose.offset(0.0F, -11.0F, 0.0F));

        PartDefinition legs = r.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightleg = legs.addOrReplaceChild("rightleg", CubeListBuilder.create().texOffs(126, 0).addBox(-5.0F, 0.5F, -5.0F, 10.0F, 19.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(112, 99).addBox(-5.0F, 0.5F, -5.0F, 10.0F, 19.0F, 10.0F, new CubeDeformation(0.2F)), PartPose.offset(-7.0F, -21.5F, 2.0F));

        PartDefinition leftleg = legs.addOrReplaceChild("leftleg", CubeListBuilder.create().texOffs(0, 126).addBox(-5.0F, 0.5F, -5.0F, 10.0F, 19.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(112, 70).addBox(-5.0F, 0.5F, -5.0F, 10.0F, 19.0F, 10.0F, new CubeDeformation(0.2F)), PartPose.offset(6.0F, -21.5F, 2.0F));

        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void setupAnim(PetrifiedTrollEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.setHeadAngles(netHeadYaw, headPitch);
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
