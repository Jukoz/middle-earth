package net.sevenstars.middleearth.client.model.equipment.head.helmetAttachments.unarmored;

import net.sevenstars.middleearth.client.model.equipment.head.helmetAttachments.CloakHoodModel;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class UnarmoredFurHoodDownModel extends CloakHoodModel {

    public UnarmoredFurHoodDownModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition hat = modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hood = hat.addOrReplaceChild("helmetAttachment", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.016F));

        PartDefinition furHood = hood.addOrReplaceChild("fur_hood", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.5F, -3.5F, 1.5708F, 0.0F, 0.0F));

        PartDefinition down = furHood.addOrReplaceChild("down", CubeListBuilder.create().texOffs(96, 0).addBox(-4.0F, -6.0F, 0.75F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.3F))
                .texOffs(64, 0).addBox(-4.0F, -5.91F, 0.75F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -6.0F, -1.1345F, 0.0F, 0.0F));

        PartDefinition hoodDown = down.addOrReplaceChild("hood_down", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.144F, 4.2857F, -1.716F, -1.5708F, 0.0F, 0.0F));

        hoodDown.addOrReplaceChild("top", CubeListBuilder.create().texOffs(112, 29).addBox(-2.872F, -11.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.5F))
                .texOffs(112, 26).addBox(-2.856F, -11.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.6F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        hoodDown.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(112, 20).addBox(-2.75F, -2.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.5F))
                .texOffs(112, 23).addBox(-2.718F, -2.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.6F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        hoodDown.addOrReplaceChild("left", CubeListBuilder.create().texOffs(100, 20).addBox(3.6F, -11.2857F, -5.3F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.5F))
                .texOffs(94, 20).addBox(3.6F, -11.2857F, -5.3F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.6F)), PartPose.offset(0.25F, 0.0F, 0.0F));
        hoodDown.addOrReplaceChild("right", CubeListBuilder.create().texOffs(100, 20).mirror().addBox(-4.35F, -11.2857F, -5.3F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.5F)).mirror(false)
                .texOffs(106, 20).addBox(-4.35F, -11.2857F, -5.3F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.6F)), PartPose.offset(-0.25F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 128, 128);
    }
}
