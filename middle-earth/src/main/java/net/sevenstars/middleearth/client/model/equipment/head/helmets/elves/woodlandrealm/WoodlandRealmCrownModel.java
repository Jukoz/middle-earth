package net.sevenstars.middleearth.client.model.equipment.head.helmets.elves.woodlandrealm;

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

public class WoodlandRealmCrownModel extends HelmetAddonModel {
    ModelPart crown;

    public WoodlandRealmCrownModel(ModelPart root) {
        super(root);

        ModelPart helmet = root.getChild("head");
        ModelPart addon = helmet.getChild("addon");
        this.crown = addon.getChild("crown");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition addon = head.addOrReplaceChild("addon", CubeListBuilder.create(), PartPose.offset(0.0F, 1.75F, -0.25F));

        PartDefinition crown = addon.addOrReplaceChild("crown", CubeListBuilder.create().texOffs(41, 28)
                .addBox(-1.9077F, -8.5F, 3.3333F, 4.0F, 15.0F, 0.0F,
                        new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0909F, -11.0F, 2.4137F, -0.3491F, 0.0F, 0.0F));

        PartDefinition crown_side_right = crown.addOrReplaceChild("crown_side_right", CubeListBuilder.create().texOffs(26, 28)
                .addBox(-4.0F, -7.5F, 0.0F, 7.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-3.9077F, -1.0F, 1.3333F, 0.0F, -0.7418F, 0.0F));

        PartDefinition crown_side_left = crown.addOrReplaceChild("crown_side_left", CubeListBuilder.create().texOffs(50, 28)
                .mirror().addBox(-3.0F, -7.5F, 0.0F, 7.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(4.1154F, -1.0F, 1.3335F, 0.0F, 0.7418F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}