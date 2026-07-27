package net.sevenstars.middleearth.client.model.equipment.head.helmets.humans.gondor;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.EggHelmetModel;

public class KingsGuardHelmetModel extends EggHelmetModel {

    public KingsGuardHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        //+0.75
        modelData.getRoot().addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create()
                        .texOffs(50, 0).mirror().addBox(2.0F, -19.55F, 0.0F, 7.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                        .texOffs(50, 16).addBox(-9.0F, -19.55F, 0.0F, 7.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));


        return LayerDefinition.create(modelData, 64, 64);
    }
}