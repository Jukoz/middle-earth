package net.sevenstars.of_beasts_and_wild_things.entity.deer;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class DeerEntityModel extends HierarchicalModel<DeerEntity> {
    private final ModelPart root;

    protected DeerEntityModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 11.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(40, 53).addBox(-5.0F, -6.0F, 2.0F, 10.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition upper_body = body.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(1, 48).addBox(-4.0F, -6.0F, -8.0F, 8.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 8.0F));

        PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(0, 46).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.48F, 0.0F, 0.0F));

        PartDefinition leg_front_left = body.addOrReplaceChild("leg_front_left", CubeListBuilder.create().texOffs(31, 24).addBox(-2.0F, 0.0F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 2.0F, -5.5F));

        PartDefinition leg_front_right = body.addOrReplaceChild("leg_front_right", CubeListBuilder.create().texOffs(44, 24).addBox(-1.0F, 0.0F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 2.0F, -5.5F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -11.0F, -4.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(1, 15).addBox(-2.0F, -9.0F, -7.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(25, 7).addBox(1.0F, -20.0F, 1.5F, 10.0F, 9.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(25, 7).mirror().addBox(-11.0F, -20.0F, 1.5F, 10.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -2.0F, -7.0F));

        PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offset(2.0F, -9.0F, 1.0F));

        PartDefinition left_ear_r1 = left_ear.addOrReplaceChild("left_ear_r1", CubeListBuilder.create().texOffs(21, 1).addBox(0.0F, -3.0F, -1.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offset(-2.0F, -9.0F, 1.0F));

        PartDefinition right_ear_r1 = right_ear.addOrReplaceChild("right_ear_r1", CubeListBuilder.create().texOffs(32, 1).addBox(-4.0F, -3.0F, -1.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition neck = head.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(4, 27).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition leg_back_left = root.addOrReplaceChild("leg_back_left", CubeListBuilder.create().texOffs(31, 38).addBox(-2.0F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 5.0F, 6.5F));

        PartDefinition leg_back_right = root.addOrReplaceChild("leg_back_right", CubeListBuilder.create().texOffs(44, 38).addBox(-1.0F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 5.0F, 6.5F));
        return LayerDefinition.create(modelData, 80, 80);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(DeerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        this.root.getAllParts().forEach(ModelPart::resetPose);
        if (entity.getPose() == net.minecraft.world.entity.Pose.SHOOTING) {
            this.animateWalk(DeerEntityAnimations.RUN, limbSwing, limbSwingAmount, 1.5F, 1.0F);
        }
        else {
            this.animateWalk(DeerEntityAnimations.WALK, limbSwing, limbSwingAmount, 2.0F, 1.0F);
        }
    }
}
