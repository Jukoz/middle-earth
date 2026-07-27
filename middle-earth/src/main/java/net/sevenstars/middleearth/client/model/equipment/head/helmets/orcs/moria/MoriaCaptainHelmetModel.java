package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.moria;

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

public class MoriaCaptainHelmetModel extends MoriaScreecherHelmetModel {

    public MoriaCaptainHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("side_spikes", CubeListBuilder.create()
                .texOffs(48, 13).addBox(-4.0F, -3.25F, -1.0F, 8.0F, 10.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(47, 25).addBox(-21.0F, -3.25F, -1.0F, 8.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(8.5F, -7.2F, -1.0F, -0.4363F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}