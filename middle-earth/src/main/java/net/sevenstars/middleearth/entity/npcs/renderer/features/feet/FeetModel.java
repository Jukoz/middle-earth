package net.sevenstars.middleearth.entity.npcs.renderer.features.feet;

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

public class FeetModel extends HierarchicalModel<NpcEntity> {
    private final ModelPart root;
    public final ModelPart toeCubeLeft;
    public final ModelPart toeCubeRight;

    public final ModelPart rightLeg;
    public final ModelPart leftLeg;

    public FeetModel(ModelPart modelPart) {
        this.root = modelPart;
        this.leftLeg = modelPart.getChild("left_leg");
        this.rightLeg = modelPart.getChild("right_leg");

        this.toeCubeLeft = leftLeg.getChild("feet");
        this.toeCubeRight = rightLeg.getChild("feet");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        var rightLeg = modelPartData.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0,0).addBox(-2.0F, 0.0F, -2.0F, 0.0F, 12.0F, 0.0F, CubeDeformation.NONE), PartPose.offset(-1.9F, 12.0F, 0.0F));
        var leftLeg = modelPartData.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0,0).addBox(-2.0F, 0.0F, -2.0F, 0.0F, 0.0F, 0.0F,  CubeDeformation.NONE), PartPose.offset(1.9F, 12.0F, 0.0F));

        rightLeg.addOrReplaceChild("feet",
                                CubeListBuilder.create().texOffs(0, 4).mirror().addBox(0,0,0,4,2,2, CubeDeformation.NONE),
                                PartPose.offset(-2f,10f,-4f));

        leftLeg.addOrReplaceChild("feet",
                                CubeListBuilder.create().texOffs(0, 4).addBox(0,0,0,4,2,2, CubeDeformation.NONE),
                                PartPose.offset(-2f,10f,-4f));

        return LayerDefinition.create(modelData, 16, 16);
    }

    @Override
    public void setupAnim(NpcEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.rightLeg.resetPose();
        this.leftLeg.resetPose();
        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.rightLeg.yRot = 0.005F;
        this.leftLeg.yRot = -0.005F;
        this.rightLeg.zRot = 0.005F;
        this.leftLeg.zRot = -0.005F;
        if (entity.isPassenger()) {
            this.rightLeg.xRot = -1.4137167F;
            this.rightLeg.yRot = ((float)Math.PI / 10F);
            this.rightLeg.zRot = 0.07853982F;
            this.leftLeg.xRot = -1.4137167F;
            this.leftLeg.yRot = (-(float)Math.PI / 10F);
            this.leftLeg.zRot = -0.07853982F;
        }
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
