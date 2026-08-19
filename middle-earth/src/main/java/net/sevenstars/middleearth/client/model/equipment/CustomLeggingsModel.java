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

public class CustomLeggingsModel<T extends LivingEntity> extends HumanoidModel<T> {

    public CustomLeggingsModel(ModelPart root) {
        super(root, RenderType::armorCutoutNoCull);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = modelPartData.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        body.addOrReplaceChild("waist", CubeListBuilder.create()
                        .texOffs(16, 16).addBox(-4.0F, -0.0F, -2.1F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.3F)),
                PartPose.offset(0.0F, -0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create()
                .texOffs(0, 16).addBox(-2.1F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F)),
                PartPose.offset(-1.9F, -11.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create()
                .texOffs(0, 32).mirror().addBox(-1.968F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F)).mirror(false),
                PartPose.offset(1.9F, -11.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}
