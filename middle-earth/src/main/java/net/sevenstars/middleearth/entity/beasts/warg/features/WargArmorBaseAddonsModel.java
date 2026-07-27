package net.sevenstars.middleearth.entity.beasts.warg.features;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class WargArmorBaseAddonsModel extends WargArmorModel {
    private final ModelPart warg;
    public WargArmorBaseAddonsModel(ModelPart root) {
        super(root);
        this.warg = root.getChild("root");
    }

    public static LayerDefinition getTexturedModelDataSpine() {
        MeshDefinition modelData = getArmorModelData();
        PartDefinition front_armor = modelData.getRoot().getChild("root").getChild(PartNames.BODY).getChild("upper_body").getChild("body_no_legs").getChild("body_fur").getChild("body_armor").getChild("front_armor");

        PartDefinition addons_front_armor = front_armor.addOrReplaceChild("addons_front_armor", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition spine = addons_front_armor.addOrReplaceChild("spine", CubeListBuilder.create().texOffs(8, 0).addBox(2.0F, -2.4F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 0).addBox(-1.0F, -2.1F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));
        PartDefinition spine_04_r1 = spine.addOrReplaceChild("spine_04_r1", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -0.7F, 0.0F, 0.0F, 0.0F, 0.0873F));
        PartDefinition spine_01_r1 = spine.addOrReplaceChild("spine_01_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -1.2F, 0.0F, 0.0F, 0.0F, 0.1309F));

        return LayerDefinition.create(modelData, 128, 128);
    }
}
