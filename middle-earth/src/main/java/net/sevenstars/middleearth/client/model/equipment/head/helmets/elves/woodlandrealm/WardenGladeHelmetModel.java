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

public class WardenGladeHelmetModel extends HelmetAddonModel {
    private static final float Y_OFFSET = 0.75f;

    public WardenGladeHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition addon = head.addOrReplaceChild("addon", CubeListBuilder.create(), PartPose.offset(0.0F, 1.75F, -0.25F));

        addon.addOrReplaceChild("central_spine", CubeListBuilder.create().texOffs(0, 45).addBox(-5.0F, 2.0F + Y_OFFSET, 0.0F, 1.0F, 10.0F, 9.0F,
                new CubeDeformation(0.1F)), PartPose.offsetAndRotation(4.5F, -3.25F, -7.5F, 1.5708F, 0.0F, 0.0F));

        PartDefinition crest = addon.addOrReplaceChild("crest", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, 1.08F));

        crest.addOrReplaceChild("crest_left", CubeListBuilder.create().texOffs(46, 51).addBox(-3.0F, -6.0F + Y_OFFSET, 0.0F, 9.0F, 12.0F, 0.0F,
                new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.654F, -15.0819F, -2.0603F, -0.4363F, -0.6109F, 0.2094F));

        crest.addOrReplaceChild("crest_right", CubeListBuilder.create().texOffs(46, 51).mirror().addBox(-5.0F, -6.0F + Y_OFFSET, 0.0F, 9.0F, 12.0F, 0.0F,
                new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.404F, -14.8899F, -1.4963F, -0.4363F, 0.6109F, -0.2094F));

        addon.addOrReplaceChild("lower_extension", CubeListBuilder.create().texOffs(0, 11)
                .addBox(-4.0F, -11.5F + Y_OFFSET, -3.8F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.55F))
                .texOffs(32, 11).addBox(-4.0F, -10.0F + Y_OFFSET, -4.5F, 8.0F, 8.0F, 8.0F,
                        new CubeDeformation(0.9F)), PartPose.offset(0.0F, 9.8F, 0.0F));

        addon.addOrReplaceChild("addon_eye", CubeListBuilder.create()
                        .texOffs(46, 63).addBox(-4.5F, -4.3F + Y_OFFSET, -4.475F, 9.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}