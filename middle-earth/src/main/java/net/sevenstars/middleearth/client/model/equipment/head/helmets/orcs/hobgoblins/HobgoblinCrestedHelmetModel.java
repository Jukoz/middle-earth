package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.hobgoblins;

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

public class HobgoblinCrestedHelmetModel extends HelmetAddonModel {

    public HobgoblinCrestedHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition crest = head.addOrReplaceChild("crest", CubeListBuilder.create()
                .texOffs(54, 49).addBox(-0.5F, -3.1368F, -0.8666F, 4.0F, 14.0F, 1.0F, new CubeDeformation(0.1F)),
                PartPose.offset(-1.5F, -11.5976F, -4.4512F));

        crest.addOrReplaceChild("crest_left", CubeListBuilder.create()
                .texOffs(38, 53).mirror().addBox(-4.0F, -4.75F, 0.0F, 8.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(5.2366F, 0.5132F, 0.3928F, 0.0F, -0.3491F, 0.0F));

        crest.addOrReplaceChild("crest_right", CubeListBuilder.create()
                .texOffs(38, 53).addBox(-5.0F, -3.25F, 0.0F, 8.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.3312F, -0.9868F, 0.0562F, 0.0F, 0.3491F, 0.0F));


        modelPartData.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}