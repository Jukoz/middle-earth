package net.sevenstars.of_beasts_and_wild_things.entity.pheasant;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class PheasantEntityModel extends HierarchicalModel<PheasantEntity> {
    private final ModelPart root;

    public PheasantEntityModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition pheasant = modelPartData.addOrReplaceChild("pheasant", CubeListBuilder.create(), PartPose.offset(0.0F, 25.0F, 0.0F));

        PartDefinition upper_body = pheasant.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offset(-0.5F, -6.5F, 0.0F));

        PartDefinition body = upper_body.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 14).addBox(-2.5F, -2.5F, -3.5F, 5.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -1.5F));

        PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(2.5F, -2.5F, -3.0F));

        PartDefinition cube_r1 = left_wing.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(29, 7).addBox(0.0F, -1.5F, -1.5F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.513F, 1.4095F, 1.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(-2.5F, -2.5F, -3.0F));

        PartDefinition cube_r2 = right_wing.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(29, 7).mirror().addBox(0.0F, -1.5F, -1.5F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.513F, 1.4095F, 1.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -1.5F, -0.1F, 5.0F, 3.0F, 11.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, -1.0F, 3.5F));

        PartDefinition head_and_neck = upper_body.addOrReplaceChild("head_and_neck", CubeListBuilder.create(), PartPose.offset(0.5F, -1.5F, -5.0F));

        PartDefinition neck = head_and_neck.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(21, 0).addBox(-1.5F, -1.75F, -5.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -2.25F, 4.0F));

        PartDefinition head = head_and_neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(4, 6).addBox(-1.0F, -1.0F, -4.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(17, 14).addBox(-1.5F, -2.0F, -3.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(4, 27).addBox(1.5F, -4.0F, -3.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(4, 27).addBox(-1.499F, -4.0F, -3.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(20, 22).addBox(-1.5F, -3.0F, -3.5F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -4.0F, 0.5F));

        PartDefinition left_leg = pheasant.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 14).addBox(-0.1F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 3).addBox(-1.1F, 4.0F, -2.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -5.0F, -1.0F));

        PartDefinition right_leg = pheasant.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 26).addBox(-0.9F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.9F, 4.0F, -2.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -5.0F, -1.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(PheasantEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        this.root.getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(PheasantEntityAnimations.WALK, limbSwing, limbSwingAmount, 5.0F, 5.0F);
        this.animate(entity.idleAnimationState, PheasantEntityAnimations.IDLE, ageInTicks);
        this.animate(entity.diggingAnimationState, PheasantEntityAnimations.DIGGING, ageInTicks, 1.2F);
        this.animate(entity.flapAnimationState, PheasantEntityAnimations.FLAP, ageInTicks, 2.0F);
    }
}
