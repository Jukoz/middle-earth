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

public class WargArmorTopAddonsModel extends WargArmorModel {

    private final ModelPart warg;
    public WargArmorTopAddonsModel(ModelPart root) {
        super(root);
        this.warg = root.getChild("root");
    }

    public static LayerDefinition getTexturedModelDataFront() {
        MeshDefinition modelData = getArmorModelData();
        PartDefinition body_armor = modelData.getRoot().getChild("root").getChild(PartNames.BODY).getChild("upper_body").getChild("body_no_legs").getChild("body_fur").getChild("body_armor");

        PartDefinition armor_addons = body_armor.addOrReplaceChild("armor_addons", CubeListBuilder.create(), PartPose.offset(-5.0F, -5.0F, -4.0F));
        PartDefinition front_addons = armor_addons.addOrReplaceChild("front_addons", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition skull2 = front_addons.addOrReplaceChild("skull2", CubeListBuilder.create().texOffs(6, 43).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.1F))
                .texOffs(79, 48).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.2F))
                .texOffs(6, 30).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(6, 60).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(13.0F, -4.0F, 3.0F, 0.0F, -1.5708F, 0.0F));

        return LayerDefinition.create(modelData, 128, 128);
    }



    public static LayerDefinition getTexturedModelDataBack() {
        MeshDefinition modelData = getArmorModelData();
        PartDefinition body_armor = modelData.getRoot().getChild("root").getChild(PartNames.BODY).getChild("upper_body").getChild("body_no_legs").getChild("body_fur").getChild("body_armor");

        PartDefinition armor_addons = body_armor.addOrReplaceChild("armor_addons", CubeListBuilder.create(), PartPose.offset(-5.0F, -5.0F, -4.0F));

        PartDefinition back_addons = armor_addons.addOrReplaceChild("back_addons", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(-1.0F, -9.0F, -1.0F, 2.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(6, 60).addBox(-1.0F, -23.0F, 2.0F, 2.0F, 23.0F, 2.0F, new CubeDeformation(-0.1F))
                .texOffs(20, 63).addBox(-1.0F, -27.0F, 3.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = back_addons.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(4, 99).addBox(-16.0F, -8.5F, 0.0F, 17.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -21.5F, 3.0F, 0.0F, 0.0F, -1.4399F));

        PartDefinition cube_r2 = back_addons.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(19, 81).addBox(-1.0F, -8.5F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-2.0F, -21.5F, 3.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition skull = back_addons.addOrReplaceChild("skull", CubeListBuilder.create().texOffs(6, 43).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.1F))
                .texOffs(6, 30).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.0F, 3.0F, 0.0F, -1.5708F, 0.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }
}
