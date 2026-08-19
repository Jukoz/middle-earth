package net.sevenstars.middleearth.entity.npcs.renderer.features.ear;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

public class EarModel extends HierarchicalModel<NpcEntity> {
    private final ModelPart root;
    public final ModelPart ears;
    public final ModelPart planeFlatLeft;
    public final ModelPart planeFlatRight;

    public EarModel(ModelPart modelPart) {
        this.root = modelPart;
        this.ears = modelPart.getChild("ears");
        this.planeFlatLeft = this.ears.getChild("ear_left");
        this.planeFlatRight = this.ears.getChild("ear_right");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition ears = modelPartData.addOrReplaceChild(
                "ears", CubeListBuilder.create(), PartPose.ZERO);
        ears.addOrReplaceChild(
                "ear_right",
                CubeListBuilder.create().texOffs(0, 6)
                        .addBox(3.5F, -8.0F, 2.0F, 7.0F, 6.0F, 0.0F, CubeDeformation.NONE),
                PartPose.ZERO);
        ears.addOrReplaceChild(
                "ear_left",
                CubeListBuilder.create().texOffs(0, 6)
                        .addBox(3.5F, -8.0F, -2.0F, 7.0F, 6.0F, 0.0F, CubeDeformation.NONE),
                PartPose.ZERO);

        return LayerDefinition.create(modelData, 16, 16);
    }

    @Override
    public void setupAnim(NpcEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.ears.resetPose();
        this.planeFlatLeft.resetPose();
        this.planeFlatRight.resetPose();
        float swimAmount = entity.getSwimAmount(0.0F);
        this.ears.xRot = headPitch * Mth.DEG_TO_RAD;
        this.ears.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        if (entity.isFallFlying()) {
            this.ears.xRot = -0.7853982f;
        } else if (swimAmount > 0.0f) {
            this.ears.xRot = ModelUtils.rotlerpRad(this.ears.xRot, -0.7853982f, swimAmount);
        }

        this.planeFlatLeft.yRot = (float)Math.toRadians(-20);
        this.planeFlatRight.yRot = (float)Math.toRadians(-160);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
