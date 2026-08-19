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
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntity;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatModel;

public class BroadhoofGoatSaddleModel extends BroadhoofGoatModel {
    private final ModelPart reins;
    public BroadhoofGoatSaddleModel(ModelPart root) {
        super(root);

        this.reins = root.getChild("broadhoof_goat").getChild(PartNames.BODY).getChild(PartNames.HEAD).getChild("saddle_things").getChild("reins");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        PartDefinition root = modelData.getRoot().getChild("broadhoof_goat").getChild(PartNames.BODY);
        PartDefinition head = root.getChild(PartNames.HEAD);


        PartDefinition saddle = root.addOrReplaceChild("saddle", CubeListBuilder.create().texOffs(74, 19).addBox(-7.0F, -23.0F, 13.0F, 14.0F, 14.0F, 13.0F, new CubeDeformation(0.3F))
                .texOffs(92, 72).addBox(-7.0F, -25.0F, 25.0F, 14.0F, 4.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 12.0F, -21.0F));

        PartDefinition saddle_things = head.addOrReplaceChild("saddle_things", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition reins = saddle_things.addOrReplaceChild("reins", CubeListBuilder.create().texOffs(81, -3).mirror().addBox(-4.1F, -7.0F, -8.0F, 0.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(81, -3).addBox(4.1F, -7.0F, -8.0F, 0.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 1.0F, 0.48F, 0.0F, 0.0F));

        PartDefinition mouth_things = saddle_things.addOrReplaceChild("mouth_things", CubeListBuilder.create().texOffs(62, 50).mirror().addBox(-2.0F, -3.0F, 1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(62, 50).addBox(5.0F, -3.0F, 1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(66, 47).addBox(-1.0F, -7.0F, -1.0F, 6.0F, 7.0F, 11.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-2.0F, 0.0F, -8.0F, 0.7854F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
    public void setupAnim(BroadhoofGoatEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        this.reins.visible = entity.getControllingPassenger() != null;
    }
}
