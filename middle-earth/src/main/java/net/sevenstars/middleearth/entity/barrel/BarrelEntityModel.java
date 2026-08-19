package net.sevenstars.middleearth.entity.barrel;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public final class BarrelEntityModel {
    private BarrelEntityModel() {}

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition main = modelPartData.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 15)
                .addBox(-7.0F, -16.0F, -7.0F, 2.0F, 15.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(32, 15).addBox(5.0F, -16.0F, -7.0F, 2.0F, 15.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 44).addBox(-5.0F, -16.0F, -7.0F, 10.0F, 15.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 44).addBox(-5.0F, -16.0F, 5.0F, 10.0F, 15.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-7.0F, -1.0F, -7.0F, 14.0F, 1.0F, 14.0F,
                        new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }

    public static LayerDefinition getBaseTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild("water_patch", CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-6.0F, 0.0F, -6.0F, 12.0F, 6.0F, 12.0F),
                PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, 0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 0, 0);
    }
}
