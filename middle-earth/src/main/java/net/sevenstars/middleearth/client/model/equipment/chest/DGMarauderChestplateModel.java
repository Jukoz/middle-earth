package net.sevenstars.middleearth.client.model.equipment.chest;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class DGMarauderChestplateModel extends BlackCastellanChestplateModel {

    public DGMarauderChestplateModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();

        PartDefinition body = modelData.getRoot().addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        body.addOrReplaceChild("spike_right", CubeListBuilder.create()
                .texOffs(36, 0).addBox(-10.25F, -14.5F, 0.252F, 11.0F, 19.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.8F, 1.0F, 0.75F, 0.0F, 0.6545F, -0.3491F));

        body.addOrReplaceChild("spike_left", CubeListBuilder.create()
                .texOffs(36, 0).mirror().addBox(-0.5F, -14.5F, 0.284F, 11.0F, 19.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(1.764F, 1.0F, 0.5F, 0.0F, -0.6545F, 0.3491F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}