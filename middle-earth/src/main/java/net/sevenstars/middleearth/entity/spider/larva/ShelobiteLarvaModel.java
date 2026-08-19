package net.sevenstars.middleearth.entity.spider.larva;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ShelobiteLarvaModel extends HierarchicalModel<ShelobiteLarvaEntity> {
    private final ModelPart root;

    public ShelobiteLarvaModel(ModelPart root) {
        this.root = root.getChild("root");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 23.5F, 5.0F));

        PartDefinition abdomen = root.addOrReplaceChild("abdomen", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5F, -3.0F));

        PartDefinition cube_r1 = abdomen.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.0F, -1.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.2F, 0.5F, 0.1745F, 0.0F, 0.0F));

        PartDefinition core = root.addOrReplaceChild("core", CubeListBuilder.create().texOffs(0, 11).addBox(-1.5F, -0.95F, -1.2F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.05F, -5.8F));

        PartDefinition leg_left = core.addOrReplaceChild("leg_left", CubeListBuilder.create(), PartPose.offset(1.5F, -0.45F, 0.3F));

        PartDefinition leg_left_front = leg_left.addOrReplaceChild("leg_left_front", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.2617F, 0.65F, 0.0804F, -1.5708F, 1.0472F, 0.0F));

        PartDefinition cube_r2 = leg_left_front.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 17).addBox(-0.1608F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1608F, 0.0804F, 0.5F, -1.5708F, 0.2182F, 0.0F));

        PartDefinition leg_left_center_front = leg_left.addOrReplaceChild("leg_left_center_front", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.2617F, 0.65F, 0.0804F, -1.6411F, 0.6474F, -0.2534F));

        PartDefinition cube_r3 = leg_left_center_front.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 17).addBox(-0.1608F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));

        PartDefinition leg_left_center_back = leg_left.addOrReplaceChild("leg_left_center_back", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.2617F, 0.65F, 0.0804F, -1.5327F, -0.1704F, -0.2214F));

        PartDefinition cube_r4 = leg_left_center_back.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 17).addBox(-0.1608F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));

        PartDefinition leg_left_back = leg_left.addOrReplaceChild("leg_left_back", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.2617F, 0.65F, 0.0804F, -1.4338F, -0.5522F, -0.257F));

        PartDefinition cube_r5 = leg_left_back.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 17).addBox(-0.1608F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));

        PartDefinition leg_right = core.addOrReplaceChild("leg_right", CubeListBuilder.create(), PartPose.offset(-1.5F, -0.45F, 0.3F));

        PartDefinition leg_right_front = leg_right.addOrReplaceChild("leg_right_front", CubeListBuilder.create(), PartPose.offsetAndRotation(0.2617F, 0.65F, 0.0804F, -1.5708F, -1.0472F, 0.0F));

        PartDefinition cube_r6 = leg_right_front.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 17).mirror().addBox(-3.8392F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.1608F, 0.0804F, 0.5F, -1.5708F, -0.2182F, 0.0F));

        PartDefinition leg_right_center_front = leg_right.addOrReplaceChild("leg_right_center_front", CubeListBuilder.create(), PartPose.offsetAndRotation(0.2617F, 0.65F, 0.0804F, -1.6411F, -0.6474F, 0.2534F));

        PartDefinition cube_r7 = leg_right_center_front.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 17).mirror().addBox(-3.8392F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));

        PartDefinition leg_right_center_back = leg_right.addOrReplaceChild("leg_right_center_back", CubeListBuilder.create(), PartPose.offsetAndRotation(0.2617F, 0.65F, 0.0804F, -1.5327F, 0.1704F, 0.2214F));

        PartDefinition cube_r8 = leg_right_center_back.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 17).mirror().addBox(-3.8392F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));

        PartDefinition leg_right_back = leg_right.addOrReplaceChild("leg_right_back", CubeListBuilder.create(), PartPose.offsetAndRotation(0.2617F, 0.65F, 0.0804F, -1.4338F, 0.5522F, 0.257F));

        PartDefinition cube_r9 = leg_right_back.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 17).mirror().addBox(-3.8392F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 32, 32);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(ShelobiteLarvaEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.root.getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(ShelobiteLarvaAnimations.SHELOBITE_LARVA_WALK,
                limbSwing, limbSwingAmount, 3.0F, 2.5F);
        this.animate(entity.biteAnimation, ShelobiteLarvaAnimations.SHELOBITE_LARVA_BITE,
                ageInTicks, 1.25F);
    }
}
