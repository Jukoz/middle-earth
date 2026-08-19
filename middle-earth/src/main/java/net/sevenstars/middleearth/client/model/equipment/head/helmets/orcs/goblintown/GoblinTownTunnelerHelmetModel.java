package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.goblintown;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.FlapHelmetModel;

public class GoblinTownTunnelerHelmetModel extends FlapHelmetModel {

    public GoblinTownTunnelerHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Candle = head.addOrReplaceChild("candle", CubeListBuilder.create()
                .texOffs(0, 22).mirror().addBox(-2.6F, -13.05F, -1.6F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 22).mirror().addBox(0.4F, -12.05F, 0.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offset(0.0F, 0.3F, 0.0F));

        Candle.addOrReplaceChild("flame_0", CubeListBuilder.create()
                        .texOffs(0, 21).mirror().addBox(0.0F, 0.75F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(1.4F, -13.8F, 1.5F, 0.0F, 0.7854F, 0.0F));
        Candle.addOrReplaceChild("flame_1", CubeListBuilder.create()
                        .texOffs(0, 21).mirror().addBox(0.0F, 0.75F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(1.4F, -13.8F, 1.5F, 0.0F, -0.7854F, 0.0F));
        Candle.addOrReplaceChild("flame_2", CubeListBuilder.create()
                        .texOffs(0, 21).mirror().addBox(0.0F, 0.75F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(-1.6F, -14.8F, -0.6F, 0.0F, -0.7854F, 0.0F));
        Candle.addOrReplaceChild("flame_3", CubeListBuilder.create()
                        .texOffs(0, 21).mirror().addBox(0.0F, 0.75F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(-1.6F, -14.8F, -0.6F, 0.0F, 0.7854F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}