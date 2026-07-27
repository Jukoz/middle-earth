package net.sevenstars.middleearth.client.model.equipment.head.helmets.humans.rohan;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class HammerhandHelmModel extends RohanHelmetModel {
    
    public HammerhandHelmModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition HornsR = head.addOrReplaceChild("horn_right_attachment", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-6.4F, -6.25F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        HornsR.addOrReplaceChild("horn_right", CubeListBuilder.create()
                .texOffs(50, 0).addBox(-3.5F, -11.25F, 0.0F, 7.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-5.5F, -5.0F, 0.0F, 0.0F, 0.0F, 0.1396F));

        PartDefinition HornsR2 = head.addOrReplaceChild("horn_left_attachment", CubeListBuilder.create()
                .texOffs(0, 0).addBox(4.6F, -6.25F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        HornsR2.addOrReplaceChild("horn_left", CubeListBuilder.create()
                .texOffs(50, 13).mirror().addBox(-3.5F, -11.25F, 0.0F, 7.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(5.7F, -5.0F, 0.0F, 0.0F, 0.0F, -0.1396F));

        modelPartData.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}