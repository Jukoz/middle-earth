package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.isengard;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class IsenUrukReinforcedHelmetModel extends IsenUrukCrestHelmetModel {

    public IsenUrukReinforcedHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();

        PartDefinition head = modelData.getRoot().addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("flaps", CubeListBuilder.create()
                        .texOffs(35, 3).addBox(-4.0F, 0.75F, -4.0F, 8.0F, 5.0F, 5.0F, new CubeDeformation(0.55F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("snout", CubeListBuilder.create()
                .texOffs(0, 13).addBox(-4.0F, -0.25F, -1.5F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -2.8F, -4.5F, 0.5236F, 0.0F, 0.0F));

        PartDefinition helmet_sides = head.addOrReplaceChild("helmet_sides", CubeListBuilder.create(),
                PartPose.offset(0.0F, -1.0F, 0.0F));

        helmet_sides.addOrReplaceChild("side_right", CubeListBuilder.create()
                .texOffs(15, 53).mirror().addBox(-2.3F, 2.25F, -7.0F, 5.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(-6.75F, -1.65F, 1.0F, 0.0F, 0.0F, -0.5672F));

        helmet_sides.addOrReplaceChild("side_left", CubeListBuilder.create()
                .texOffs(15, 53).addBox(-2.7F, 2.25F, -7.0F, 5.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(6.75F, -1.65F, 1.0F, 0.0F, 0.0F, 0.5672F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}