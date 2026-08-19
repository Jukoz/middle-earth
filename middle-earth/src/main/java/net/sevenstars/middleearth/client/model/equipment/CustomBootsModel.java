package net.sevenstars.middleearth.client.model.equipment;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;

public class CustomBootsModel<T extends LivingEntity> extends HumanoidModel<T> {

    public CustomBootsModel(ModelPart root) {
        super(root, RenderType::armorCutoutNoCull);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();


        PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightLeg = modelPartData.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        rightLeg.addOrReplaceChild("rightBoot", CubeListBuilder.create()
                        .texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.9F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        rightLeg.addOrReplaceChild("rightInnerBoot", CubeListBuilder.create()
                        .texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.7F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftLeg = modelPartData.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        leftLeg.addOrReplaceChild("leftBoot", CubeListBuilder.create()
                        .texOffs(16, 48).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.9F)).mirror(false),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        leftLeg.addOrReplaceChild("leftInnerBoot", CubeListBuilder.create()
                        .texOffs(0, 32).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.7F)).mirror(false),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}
