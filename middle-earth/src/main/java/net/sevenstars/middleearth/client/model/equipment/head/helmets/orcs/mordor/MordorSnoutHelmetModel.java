package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.mordor;

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

public class MordorSnoutHelmetModel extends HelmetAddonModel {

    public MordorSnoutHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition snout = head.addOrReplaceChild("snout", CubeListBuilder.create(),
                //edit -0.75 for height, nothing else
                PartPose.offsetAndRotation(0.0F, -0.75F, -2.59F, 1.1345F, 0.0F, 0.0F));
        snout.addOrReplaceChild("snout_cube", CubeListBuilder.create()
                .texOffs(0, 16).addBox(-3.5F, -3.5F, -7.5F, 7.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        head.addOrReplaceChild("side_extension_left", CubeListBuilder.create()
                .texOffs(18, 0).addBox(0.0F, -2.25F, 1.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.offsetAndRotation(-4.0F, -6.0F, 0.2F, 0.0F, -0.4363F, 0.0F));

        head.addOrReplaceChild("side_extension_right", CubeListBuilder.create()
                .texOffs(29, 0).addBox(-1.0F, -2.25F, 1.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.offsetAndRotation(4.0F, -6.0F, 0.2F, 0.0F, 0.4363F, 0.0F));


        modelPartData.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);

    }
}