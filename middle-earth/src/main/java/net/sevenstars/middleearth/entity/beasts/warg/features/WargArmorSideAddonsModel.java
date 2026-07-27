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

public class WargArmorSideAddonsModel extends WargArmorModel {

    private final ModelPart warg;
    public WargArmorSideAddonsModel(ModelPart root) {
        super(root);

        this.warg = root.getChild("root");
    }

    public static LayerDefinition getTexturedModelDataSideSkulls() {
        MeshDefinition modelData = getArmorModelData();
        PartDefinition front_armor = modelData.getRoot().getChild("root").getChild(PartNames.BODY).getChild("upper_body").getChild("body_no_legs").getChild("body_fur").getChild("body_armor").getChild("front_armor");

        PartDefinition addons_front_armor = front_armor.addOrReplaceChild("addons_front_armor", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition skulls = addons_front_armor.addOrReplaceChild("skulls", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 1.0F));
        PartDefinition skull_01 = skulls.addOrReplaceChild("skull_01", CubeListBuilder.create(), PartPose.offsetAndRotation(0.092F, 2.1315F, 6.8137F, -2.7489F, -0.3054F, 3.1416F));
        PartDefinition cube_r1 = skull_01.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -6.0F, 0.0F, 2.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -2.0F, 0.0F, -0.7969F, -0.0393F, 0.3624F));
        PartDefinition skull_hat_r1 = skull_01.addOrReplaceChild("skull_hat_r1", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -3.5F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(-0.75F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, -0.2182F, 0.2182F, 0.0F));
        PartDefinition skull_r1 = skull_01.addOrReplaceChild("skull_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.5F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, -0.2182F, 0.2182F, 0.0F));
        PartDefinition skull_02 = skulls.addOrReplaceChild("skull_02", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.6F, 3.5F, 5.0F, 0.0F, 3.098F, 0.0F));
        PartDefinition cube_r2 = skull_02.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 32).addBox(-1.0F, -6.0F, 0.0F, 2.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.692F, -2.8685F, -1.8863F, -0.4037F, -0.2145F, -0.1479F));
        PartDefinition skull_hat_r2 = skull_02.addOrReplaceChild("skull_hat_r2", CubeListBuilder.create().texOffs(24, 32).addBox(-3.0F, -3.5F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(-0.75F))
                .texOffs(0, 32).addBox(-3.0F, -3.5F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(1.5129F, -0.8639F, -1.0609F, 0.1309F, -0.1745F, 0.0F));
        skull_02.addOrReplaceChild("back_body_no_legs", CubeListBuilder.create(), PartPose.offset(-4.5F, -3.0F, -6.0F));

        return LayerDefinition.create(modelData, 128, 128);
    }
}
