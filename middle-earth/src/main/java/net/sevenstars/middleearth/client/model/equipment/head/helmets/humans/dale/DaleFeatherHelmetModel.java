package net.sevenstars.middleearth.client.model.equipment.head.helmets.humans.dale;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class DaleFeatherHelmetModel extends DaleHelmetModel {

    public DaleFeatherHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("spike", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-0.5F, -0.75F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -10.5F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition feather_duster = head.addOrReplaceChild("feather_duster", CubeListBuilder.create(),
                PartPose.offset(0.0F, -11.0F, 0.0F));

        PartDefinition plumes = feather_duster.addOrReplaceChild("plumes", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition plume_0 = plumes.addOrReplaceChild("plumes_0", CubeListBuilder.create()
                .texOffs(0, 51).addBox(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.1369F, -0.5338F, 1.8835F));
        plume_0.addOrReplaceChild("plume_0", CubeListBuilder.create()
                .texOffs(0, 45).addBox(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition plume_1 = plumes.addOrReplaceChild("plumes_1", CubeListBuilder.create()
                .texOffs(14, 51).addBox(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3841F, -0.761F, 0.4898F));
        plume_1.addOrReplaceChild("plume_1", CubeListBuilder.create()
                .texOffs(14, 45).addBox(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition plume_2 = plumes.addOrReplaceChild("plumes_2", CubeListBuilder.create()
                .texOffs(28, 51).addBox(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.8254F, -0.5049F, 2.4806F));
        plume_2.addOrReplaceChild("plume_2", CubeListBuilder.create()
                .texOffs(28, 45).addBox(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition plume_3 = plumes.addOrReplaceChild("plumes_3", CubeListBuilder.create()
                .texOffs(42, 51).addBox(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.7925F, 0.5672F, 3.1416F));
        plume_3.addOrReplaceChild("plume_3", CubeListBuilder.create()
                .texOffs(42, 45).addBox(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition plume_4 = plumes.addOrReplaceChild("plumes_4", CubeListBuilder.create()
                .texOffs(42, 39).addBox(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.7964F, -0.9478F, 1.7551F));
        plume_4.addOrReplaceChild("plume_4", CubeListBuilder.create()
                .texOffs(42, 33).addBox(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        modelPartData.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}