package net.sevenstars.middleearth.client.model.equipment.chest.backAttachments.armored;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.sevenstars.middleearth.client.model.equipment.chest.ChestplateAddonModel;
import net.sevenstars.middleearth.client.model.equipment.chest.backAttachments.CloakCapeModel;

public class SkullSpikesModel extends ChestplateAddonModel {

    public SkullSpikesModel(ModelPart root) {
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

        PartDefinition spears = body.addOrReplaceChild("spears", CubeListBuilder.create(),
                PartPose.offset(0.0F, 4.0F, -0.5F));

        PartDefinition rightspear = spears.addOrReplaceChild("right_spear_0", CubeListBuilder.create(),
                PartPose.offsetAndRotation(-1.5F, 7.0F, 2.7F, -0.1745F, 0.0F, -0.3491F));
        rightspear.addOrReplaceChild("skull_right_0", CubeListBuilder.create().texOffs(40, 0).addBox(-3.0F, -41.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(40, 13).addBox(-3.0F, -41.0F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.2F)),
                PartPose.offset(0.0F, 10.0F, -0.2F));
        rightspear.addOrReplaceChild("skull_right_1", CubeListBuilder.create().texOffs(40, 0).addBox(-3.0F, -3.5F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(40, 13).addBox(-3.0F, -3.5F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.2F)),
                PartPose.offsetAndRotation(0.0F, -18.5F, -0.2F, 0.0F, 0.5236F, 0.0F));
        rightspear.addOrReplaceChild("cloth_right", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.3181F, -14.5411F, 1.0122F, 0.1745F, 0.0F, 0.3491F));

        PartDefinition spears2 = rightspear.addOrReplaceChild("right_spear_1", CubeListBuilder.create(),
                PartPose.offset(-2.5F, -0.5F, -3.7F));
        spears2.addOrReplaceChild("right_spear_2", CubeListBuilder.create().texOffs(16, 0).mirror().addBox(1.4329F, -23.6652F, 2.6678F, 2.0F, 31.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition leftspear = spears.addOrReplaceChild("left_spear_0", CubeListBuilder.create(),
                PartPose.offsetAndRotation(1.5F, 7.0F, 2.7F, -0.1745F, 0.0F, 0.3491F));
        leftspear.addOrReplaceChild("skull_left_0", CubeListBuilder.create().texOffs(40, 0).addBox(-3.0F, -3.5F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(40, 13).addBox(-3.0F, -3.5F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.2F)),
                PartPose.offsetAndRotation(0.0F, -27.5F, -0.2F, 0.0F, 0.5236F, 0.0F));
        leftspear.addOrReplaceChild("rib_left", CubeListBuilder.create().texOffs(39, 51).addBox(-4.0F, -5.4F, -2.0F, 8.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -18.5F, -0.2F));
        leftspear.addOrReplaceChild("cloth_left", CubeListBuilder.create().texOffs(24, 0).mirror().addBox(-1.0F, -1.5F, 0.0F, 2.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.6819F, -19.5411F, 1.0122F, 0.1745F, 0.0F, -0.3491F));

        PartDefinition spears6 = leftspear.addOrReplaceChild("left_spear_1", CubeListBuilder.create(),
                PartPose.offset(-2.5F, -0.5F, -3.7F));
        spears6.addOrReplaceChild("left_spear_2", CubeListBuilder.create().texOffs(8, 0).mirror().addBox(1.4329F, -23.6652F, 2.6678F, 2.0F, 31.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition centrespear = spears.addOrReplaceChild("center_spear_0", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 3.0F, 2.7F, -0.1309F, 0.0F, 0.0F));
        centrespear.addOrReplaceChild("skull_center", CubeListBuilder.create().texOffs(40, 0).addBox(-3.0F, -41.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(40, 13).addBox(-3.0F, -41.0F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.2F)),
                PartPose.offset(0.0F, 10.0F, -0.2F));
        centrespear.addOrReplaceChild("rib_center", CubeListBuilder.create().texOffs(36, 38).addBox(-5.0F, -5.4F, -1.0F, 10.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -18.5F, -0.2F));

        PartDefinition spears3 = centrespear.addOrReplaceChild("center_spear_1", CubeListBuilder.create(),
                PartPose.offset(-2.5F, -0.5F, -3.7F));
        spears3.addOrReplaceChild("center_spear_2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(1.4329F, -23.6652F, 2.6678F, 2.0F, 35.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}
