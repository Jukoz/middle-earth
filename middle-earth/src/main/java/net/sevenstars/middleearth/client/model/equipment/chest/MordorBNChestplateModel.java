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

public class MordorBNChestplateModel extends HaltChestplateModel {

    public MordorBNChestplateModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();

        PartDefinition body = modelData.getRoot().addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        body.addOrReplaceChild("chest_addon", CubeListBuilder.create()
                .texOffs(0, 41).addBox(-4.5F, -1.0F, -2.3F, 9.0F, 12.0F, 4.0F, new CubeDeformation(0.51F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}