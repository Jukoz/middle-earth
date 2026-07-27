package net.sevenstars.middleearth.entity.beasts.broadhoof.features;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatModel;

public class BroadhoofGoatArmorModel extends BroadhoofGoatModel {

    private final ModelPart broadhoofGoat;
    private final ModelPart head;

    public BroadhoofGoatArmorModel(ModelPart root) {
        super(root);

        this.broadhoofGoat = root.getChild("broadhoof_goat");
        this.head = broadhoofGoat.getChild(PartNames.BODY).getChild(PartNames.HEAD);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        PartDefinition root = modelData.getRoot().getChild("broadhoof_goat").getChild(PartNames.BODY);
        PartDefinition head = root.getChild(PartNames.HEAD);

        PartDefinition head_armor = head.addOrReplaceChild("head_armor", CubeListBuilder.create().texOffs(0, 1).addBox(0.9F, -11.0F, 2.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(0, 1).mirror().addBox(-7.9F, -11.0F, 2.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(2.0F, 0.0F, -8.0F));

        PartDefinition neck_plate_r1 = head_armor.addOrReplaceChild("neck_plate_r1", CubeListBuilder.create().texOffs(85, 5).addBox(-5.0F, -7.0F, 4.0F, 6.0F, 14.0F, 6.0F, new CubeDeformation(0.1F))
                .texOffs(44, 0).addBox(-5.0F, -7.0F, -1.0F, 6.0F, 6.0F, 11.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition side_hanging_plate_r1 = head_armor.addOrReplaceChild("side_hanging_plate_r1", CubeListBuilder.create().texOffs(77, 53).addBox(-5.0F, -4.0F, -1.0F, 6.0F, 5.0F, 11.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 2.0F, 2.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition body_armor = root.addOrReplaceChild("body_armor", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -3.0F, -20.0F, 12.0F, 11.0F, 20.0F, new CubeDeformation(0.2F))
                .texOffs(0, 89).addBox(-6.0F, 2.0F, -20.0F, 12.0F, 6.0F, 20.0F, new CubeDeformation(0.1F))
                .texOffs(0, 31).addBox(-7.0F, -5.0F, -21.0F, 14.0F, 16.0F, 13.0F, new CubeDeformation(0.2F))
                .texOffs(0, 60).addBox(-7.0F, -5.0F, -21.0F, 14.0F, 16.0F, 13.0F, new CubeDeformation(0.1F))
                .texOffs(108, 113).addBox(6.0F, -2.0F, -7.0F, 3.0F, 8.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(108, 98).addBox(-9.0F, -2.0F, -7.0F, 3.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 9.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }
}
