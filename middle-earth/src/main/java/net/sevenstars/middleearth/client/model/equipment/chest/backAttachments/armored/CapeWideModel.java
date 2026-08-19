package net.sevenstars.middleearth.client.model.equipment.chest.backAttachments.armored;

import net.sevenstars.middleearth.client.model.equipment.chest.backAttachments.CloakCapeModel;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class CapeWideModel extends CloakCapeModel {

    public CapeWideModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = modelPartData.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        body.addOrReplaceChild("cape_shoulder", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -23.5F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(1.1F)),
                PartPose.offset(0.0F, 23.0F, 0.016F));

        PartDefinition cape = body.addOrReplaceChild("cape", CubeListBuilder.create()
                .texOffs(0, 32).mirror().addBox(-9F, -1.0F, -3F, 18.0F, 13.0F, 6.0F, new CubeDeformation(0.4F)).mirror(false)
                .texOffs(0, 94).mirror().addBox(-9F, -1.0F, -3F, 18.0F, 13.0F, 6.0F, new CubeDeformation(0.39F)).mirror(false),
                PartPose.offset(0.0F, 0.0f, 0.0f));

        cape.addOrReplaceChild("cape_low", CubeListBuilder.create()
                .texOffs(0, 51).mirror().addBox(-9F, -0.8F, -3F, 18.0F, 13.0F, 6.0F, new CubeDeformation(0.4F)).mirror(false)
                .texOffs(0, 113).mirror().addBox(-9F, -0.8F, -3F, 18.0F, 13.0F, 6.0F, new CubeDeformation(0.39F)).mirror(false),
                PartPose.offset(0.0f, 13.3158f, 0.0f));

        PartDefinition right_arm = modelPartData.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        right_arm.addOrReplaceChild("right_arm_shoulder_cape", CubeListBuilder.create().texOffs(24, 16).addBox(-4.0F, -2.5F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.91F)),
                PartPose.offset(-0.0F, -0.0F, 0.0F));

        PartDefinition left_arm = modelPartData.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        left_arm.addOrReplaceChild("left_arm_shoulder_cape", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(0.0F, -2.5F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.91F)).mirror(false),
                PartPose.offset(0.0F, -0.0F, 0.0F));

        PartDefinition right_leg = modelPartData.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        right_leg.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(72, 16).addBox(-2.1F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.1F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_leg = modelPartData.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        left_leg.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(56, 16).mirror().addBox(-1.968F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.1F)).mirror(false),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 128, 128);
    }
}
