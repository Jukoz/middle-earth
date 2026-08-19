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

public class MoriaScreecherHelmetModel extends HelmetAddonModel {

    public MoriaScreecherHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        return LayerDefinition.create(modelData, 64, 64);
    }

    public static MeshDefinition getModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("large_crest", CubeListBuilder.create()
                        .texOffs(28, -18).addBox(0.0F, -15.25F, -7.0F, 0.0F, 14.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("upper_jaw", CubeListBuilder.create()
                        .texOffs(0, 17).addBox(-5.3268F, -7.5878F, -1.8578F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.8268F, -5.3903F, -7.9422F, -0.5025F, 0.7273F, -0.357F));

        head.addOrReplaceChild("bottom_jaw", CubeListBuilder.create()
                .texOffs(0, 31).addBox(-8.4497F, -9.75F, 2.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.3F)),
                PartPose.offsetAndRotation(0.3498F, 4.5F, -8.9142F, 0.6603F, 0.6603F, 0.4439F));

        modelPartData.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return modelData;
    }
}