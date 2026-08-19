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

public class DwarvenMinerHelmetModel extends HelmetAddonModel {

    public DwarvenMinerHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("flaps", CubeListBuilder.create()
                .texOffs(0, 20).addBox(-4.0F, 1.55F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.9F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_candle = head.addOrReplaceChild("right_candle", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-1.0F, -1.25F, 1.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(20, 12).addBox(-1.0F, -1.25F, 5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(0, 14).addBox(-1.0F, -1.25F, -3.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(8, 0).addBox(-2.0F, -1.25F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 22).addBox(-1.0F, -5.25F, -3.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-5.5F, -4.5F, -4.5F));
        right_candle.addOrReplaceChild("flame_right_0", CubeListBuilder.create()
                .texOffs(0, 21).addBox(0.0F, 0.75F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -7.0F, -2.0F, 0.0F, 0.7854F, 0.0F));
        right_candle.addOrReplaceChild("flame_right_1", CubeListBuilder.create()
                .texOffs(0, 21).addBox(0.0F, 0.75F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -7.0F, -2.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition left_candle = head.addOrReplaceChild("left_candle", CubeListBuilder.create()
                        .texOffs(0, 0).mirror().addBox(-1.0F, -1.25F, 1.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(20, 12).mirror().addBox(-1.0F, -1.25F, 5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false)
                .texOffs(0, 14).mirror().addBox(-1.0F, -1.25F, -3.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(8, 0).mirror().addBox(-2.0F, -1.25F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 22).mirror().addBox(-1.0F, -5.25F, -3.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offset(5.5F, -4.5F, -4.5F));
        left_candle.addOrReplaceChild("flame_left_0", CubeListBuilder.create()
                .texOffs(0, 21).mirror().addBox(0.0F, 0.75F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, -7.0F, -2.0F, 0.0F, -0.7854F, 0.0F));
        left_candle.addOrReplaceChild("flame_left_1", CubeListBuilder.create()
                .texOffs(0, 21).mirror().addBox(0.0F, 0.75F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, -7.0F, -2.0F, 0.0F, 0.7854F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}