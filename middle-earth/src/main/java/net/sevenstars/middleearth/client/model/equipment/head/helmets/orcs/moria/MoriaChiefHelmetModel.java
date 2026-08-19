package net.sevenstars.middleearth.client.model.equipment.head.helmets.orcs.moria;

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

public class MoriaChiefHelmetModel extends HelmetAddonModel {

    public MoriaChiefHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("horizontal_crest", CubeListBuilder.create()
                .texOffs(31, 15).addBox(-8.0F, -16.25F, 0.0F, 16.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("bottom_jaw", CubeListBuilder.create()
                .texOffs(0, 28).addBox(-8.4497F, -9.75F, 2.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.3F)),
                PartPose.offsetAndRotation(0.3498F, 4.5F, -8.9142F, 0.6603F, 0.6603F, 0.4439F));

        head.addOrReplaceChild("horns_right", CubeListBuilder.create()
                .texOffs(44, 32).addBox(-7.0F, -10.25F, -4.0F, 3.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(47, 50).addBox(-7.0F, -5.25F, 0.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(44, 55).mirror().addBox(-7.0F, -4.25F, -3.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(49, 45).mirror().addBox(-9.0F, -4.25F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("horns_left", CubeListBuilder.create()
                .texOffs(44, 32).mirror().addBox(4.0F, -10.25F, -4.0F, 3.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(47, 50).mirror().addBox(4.0F, -5.25F, 0.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(44, 55).addBox(4.0F, -4.25F, -3.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(49, 45).addBox(7.0F, -4.25F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("top_spike", CubeListBuilder.create()
                .texOffs(0, 59).addBox(-2.5F, -2.75F, 0.0F, 5.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 54).addBox(0.0F, -2.75F, -2.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -11.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        head.addOrReplaceChild("eye_cover", CubeListBuilder.create()
                .texOffs(10, 60).addBox(-4.5F, -4.55F, -4.5F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}