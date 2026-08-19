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
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;

public class IsenUrukPlateHelmetModel extends HelmetAddonModel {

    public IsenUrukPlateHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        return LayerDefinition.create(modelData, 64, 64);
    }

    public static MeshDefinition getModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("fan", CubeListBuilder.create()
                .texOffs(36, 29).addBox(-7.0F, -7.75F, 0.0F, 14.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -15.0F, 4.0F, 2.5744F, 0.0F, 0.0F));

        PartDefinition side_spikes = head.addOrReplaceChild("side_spikes", CubeListBuilder.create(),
                PartPose.offset(6.75F, -1.65F, -1.0F));

        side_spikes.addOrReplaceChild("spikes_right", CubeListBuilder.create()
                        .texOffs(43, 53).addBox(-2.7F, 2.25F, -7.0F, 5.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5672F));
        side_spikes.addOrReplaceChild("spikes_left", CubeListBuilder.create()
                        .texOffs(43, 53).mirror().addBox(-2.3F, 2.25F, -7.0F, 5.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(-13.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5672F));

        modelPartData.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return modelData;
    }
}