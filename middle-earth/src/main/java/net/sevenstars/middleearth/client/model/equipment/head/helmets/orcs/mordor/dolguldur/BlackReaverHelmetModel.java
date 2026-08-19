package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.mordor.dolguldur;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class BlackReaverHelmetModel extends HelmetAddonModel {

    public BlackReaverHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("horn_left", CubeListBuilder.create()
                .texOffs(0, -8).mirror().addBox(0.0F, -14.25F, -9.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(3.0F, 1.0F, -5.0F, -0.4363F, -0.6981F, 0.0F));

        head.addOrReplaceChild("horn_right", CubeListBuilder.create()
                .texOffs(0, -8).addBox(0.0F, -14.25F, -9.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-3.0F, 1.0F, -5.0F, -0.4363F, 0.6981F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}