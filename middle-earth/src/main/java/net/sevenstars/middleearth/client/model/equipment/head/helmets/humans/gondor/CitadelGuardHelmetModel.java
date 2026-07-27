package net.sevenstars.middleearth.client.model.equipment.head.helmets.humans.gondor;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.EggHelmetModel;

public class CitadelGuardHelmetModel extends EggHelmetModel {

    public CitadelGuardHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        //+0.75
        PartDefinition head = modelData.getRoot().addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("side_left", CubeListBuilder.create()
                .texOffs(40, 32).addBox(-0.5F, -9.75F, 0.284F, 6.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(5.0F, -6.0F, -2.5F, 0.0F, -1.3963F, 0.0F));
        head.addOrReplaceChild("side_right", CubeListBuilder.create()
                .texOffs(52, 32).addBox(-5.25F, -9.75F, 0.252F, 6.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-5.0F, -6.0F, -2.5F, 0.0F, 1.3963F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}