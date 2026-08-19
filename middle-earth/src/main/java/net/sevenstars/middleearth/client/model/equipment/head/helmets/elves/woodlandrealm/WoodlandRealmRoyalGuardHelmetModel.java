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

public class WoodlandRealmRoyalGuardHelmetModel extends HelmetAddonModel {
    private static final float Y_OFFSET = 0.75f;

    public WoodlandRealmRoyalGuardHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition addon = head.addOrReplaceChild("addon", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition centralSpine = addon.addOrReplaceChild("central_spine", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = centralSpine.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 45)
                .addBox(-5.0F, 2.0F, 0.0F, 1.0F, 10.0F, 9.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(4.5F, -2.25F, -7.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition fin = addon.addOrReplaceChild("fin", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition fin1 = fin.addOrReplaceChild("fin_1", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-11.0F, -2.0F + Y_OFFSET, -4.0F, 21.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, -6.25F, -0.388F, 0.6021F, 0.0F, 0.0F));

        PartDefinition lowerExtension = addon.addOrReplaceChild("lower_extension", CubeListBuilder.create().texOffs(0, 11)
                .addBox(-4.0F, -13.8F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)).texOffs(32, 11)
                .addBox(-4.0F, -10.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.9F)), PartPose.offset(0.0F, 10.8F, 0.0F));


        head.addOrReplaceChild("addon_eye", CubeListBuilder.create()
                        .texOffs(46, 63).addBox(-4.5F, -3.55F, -4.475F, 9.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}