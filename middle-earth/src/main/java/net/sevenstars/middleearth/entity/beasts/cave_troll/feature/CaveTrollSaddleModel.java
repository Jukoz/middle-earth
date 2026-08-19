package net.sevenstars.middleearth.entity.beasts.cave_troll.feature;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityModel;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;

public class CaveTrollSaddleModel extends CaveTrollEntityModel {
    private final ModelPart reins;

    protected CaveTrollSaddleModel(ModelPart root) {
        super(root);

        reins = root.getChild("root").getChild("body_no_legs").getChild("body_no_limbs").getChild("Head").getChild("Saddle_Reigns");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        PartDefinition modelPartData = modelData.getRoot().getChild("root").getChild("body_no_legs").getChild("body_no_limbs");

        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition armor = body.addOrReplaceChild("armor", CubeListBuilder.create().texOffs(91, 51)
                .addBox(-16.0F, -40.0F, -9.0F, 33.0F, 40.0F, 17.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition saddle_platform = modelPartData.addOrReplaceChild("saddle_platform", CubeListBuilder.create().texOffs(0, 0).addBox(-7.5F, 1.0F, -7.5F, 15.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 77).addBox(-7.5F, -17.0F, -7.5F, 15.0F, 18.0F, 16.0F,
                        new CubeDeformation(0.0F)), PartPose.offset(0.5F, -47.0F, 6.5F));

        PartDefinition log2_r1 = saddle_platform.addOrReplaceChild("log2_r1", CubeListBuilder.create().texOffs(67, 25).addBox(-1.5F, -1.5F, 3.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4F, 4.5F, 9.0F, 3.1416F, 0.0F, 3.1416F));

        PartDefinition log1_r1 = saddle_platform.addOrReplaceChild("log1_r1", CubeListBuilder.create().texOffs(53, 0).addBox(-1.5F, -1.5F, -10.0F, 4.0F, 4.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5F, -2.5F, 0.0F, -1.5708F, 0.0F));

        PartDefinition platform = modelPartData.addOrReplaceChild("platform", CubeListBuilder.create().texOffs(106, 15).addBox(-15.5F, 12.0F, 1.5F, 31.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(89, 28).addBox(-19.0F, 10.0F, 1.6F, 38.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(77, 114).addBox(-19.0F, 1.0F, 1.6F, 38.0F, 9.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -48.0F, 6.5F));

        PartDefinition log2_r2 = platform.addOrReplaceChild("log2_r2", CubeListBuilder.create().texOffs(111, 0).addBox(-1.5F, -1.5F, -1.0F, 4.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.5F, 13.5F, 14.5F, 3.1416F, 0.0F, 3.1416F));

        PartDefinition log1_r2 = platform.addOrReplaceChild("log1_r2", CubeListBuilder.create().texOffs(142, 0).addBox(-1.5F, -1.5F, -1.0F, 4.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.5F, 13.5F, 14.5F, 3.1416F, 0.0F, 3.1416F));

        PartDefinition Head = modelPartData.addOrReplaceChild("Head", CubeListBuilder.create(), PartPose.offset(0.5F, -34.5F, -8.5F));

        PartDefinition Saddle_Reigns = Head.addOrReplaceChild("Saddle_Reigns", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r1 = Saddle_Reigns.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 43).addBox(-5.0F, -22.0F, -2.0F, 10.0F, 22.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -4.0F, -0.6981F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void setupAnim(CaveTrollEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        reins.visible = entity.getControllingPassenger() != null;
    }
}
