package net.sevenstars.middleearth.client.model.equipment.head.helmets.dwarves;

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

public class EreborHornsHelmetModel extends HelmetAddonModel {

    public EreborHornsHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition addon_horns = head.addOrReplaceChild("addon_horns", CubeListBuilder.create(),
                PartPose.offset(-11.0F, -8.0208F, -3.4645F));

        PartDefinition right_addon_horn = addon_horns.addOrReplaceChild("right_addon_horn", CubeListBuilder.create(),
                PartPose.offsetAndRotation(-1.0F, 3.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition right_horn = right_addon_horn.addOrReplaceChild("right_horn", CubeListBuilder.create(),
                PartPose.offset(13.0F, -3.8284F, -0.4142F));

        right_horn.addOrReplaceChild("right_horn_0", CubeListBuilder.create()
                .texOffs(21, 59).addBox(-8.0F, 4.75F, -5.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        right_horn.addOrReplaceChild("right_horn_1", CubeListBuilder.create()
                .texOffs(20, 50).addBox(-8.0F, 4.75F, -5.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 2.1213F, 2.1213F, 0.7854F, 0.0F, 0.0F));
        right_horn.addOrReplaceChild("right_horn_2", CubeListBuilder.create()
                .texOffs(1, 52).addBox(-8.0F, 0.75F, -1.0F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 2.8284F, 1.4142F, 0.7854F, 0.0F, 0.0F));

        PartDefinition left_horn = right_addon_horn.addOrReplaceChild("left_horn", CubeListBuilder.create(),
                PartPose.offset(11.0F, -3.8284F, -0.4142F));

        left_horn.addOrReplaceChild("left_horn_0", CubeListBuilder.create()
                        .texOffs(21, 59).mirror().addBox(5.0F, 4.75F, -5.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        left_horn.addOrReplaceChild("left_horn_1", CubeListBuilder.create()
                        .texOffs(20, 50).mirror().addBox(4.0F, 4.75F, -5.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, 2.1213F, 2.1213F, 0.7854F, 0.0F, 0.0F));
        left_horn.addOrReplaceChild("left_horn_2", CubeListBuilder.create()
                        .texOffs(1, 52).mirror().addBox(4.0F, 0.75F, -1.0F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, 2.8284F, 1.4142F, 0.7854F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}