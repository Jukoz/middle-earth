package net.sevenstars.of_beasts_and_wild_things.compat.farm.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;

public final class FarmAnimalVariantModels {
    public static final ModelLayerLocation COLD_CHICKEN =
            new ModelLayerLocation(OfBeastsAndWildThings.of("cold_chicken"), "main");
    public static final ModelLayerLocation COLD_COW =
            new ModelLayerLocation(OfBeastsAndWildThings.of("cold_cow"), "main");

    private FarmAnimalVariantModels() {
    }

    public static LayerDefinition coldChickenLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F)
                        .texOffs(44, 0).addBox(-3.0F, -7.0F, -2.015F, 6.0F, 3.0F, 4.0F),
                PartPose.offset(0.0F, 15.0F, -4.0F)
        );
        root.addOrReplaceChild(
                "beak",
                CubeListBuilder.create().texOffs(14, 0)
                        .addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F),
                PartPose.offset(0.0F, 15.0F, -4.0F)
        );
        root.addOrReplaceChild(
                "red_thing",
                CubeListBuilder.create().texOffs(14, 4)
                        .addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F),
                PartPose.offset(0.0F, 15.0F, -4.0F)
        );
        root.addOrReplaceChild(
                "body",
                CubeListBuilder.create()
                        .texOffs(0, 9).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F)
                        .texOffs(38, 9).addBox(0.0F, 3.0F, -1.0F, 0.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 1.5707964F, 0.0F, 0.0F)
        );
        CubeListBuilder leg = CubeListBuilder.create().texOffs(26, 0)
                .addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F);
        root.addOrReplaceChild("right_leg", leg, PartPose.offset(-2.0F, 19.0F, 1.0F));
        root.addOrReplaceChild("left_leg", leg, PartPose.offset(1.0F, 19.0F, 1.0F));
        root.addOrReplaceChild(
                "right_wing",
                CubeListBuilder.create().texOffs(24, 13)
                        .addBox(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F),
                PartPose.offset(-4.0F, 13.0F, 0.0F)
        );
        root.addOrReplaceChild(
                "left_wing",
                CubeListBuilder.create().texOffs(24, 13)
                        .addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F),
                PartPose.offset(4.0F, 13.0F, 0.0F)
        );
        return LayerDefinition.create(mesh, 64, 32);
    }

    public static LayerDefinition coldCowLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition head = root.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F)
                        .texOffs(9, 33).addBox(-3.0F, 1.0F, -7.0F, 6.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 4.0F, -8.0F)
        );
        head.addOrReplaceChild(
                "right_horn",
                CubeListBuilder.create().texOffs(0, 40)
                        .addBox(-1.5F, -4.5F, -0.5F, 2.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(-4.5F, -2.5F, -3.5F, 1.5708F, 0.0F, 0.0F)
        );
        head.addOrReplaceChild(
                "left_horn",
                CubeListBuilder.create().texOffs(0, 32)
                        .addBox(-1.5F, -3.0F, -0.5F, 2.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(5.5F, -2.5F, -5.0F, 1.5708F, 0.0F, 0.0F)
        );
        root.addOrReplaceChild(
                "body",
                CubeListBuilder.create()
                        .texOffs(20, 32).addBox(
                                -6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F,
                                new CubeDeformation(0.5F)
                        )
                        .texOffs(18, 4).addBox(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F)
                        .texOffs(52, 0).addBox(-2.0F, 2.0F, -8.0F, 4.0F, 6.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, 1.5707964F, 0.0F, 0.0F)
        );
        CubeListBuilder leg = CubeListBuilder.create().texOffs(0, 16)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F);
        root.addOrReplaceChild("right_hind_leg", leg, PartPose.offset(-4.0F, 12.0F, 7.0F));
        root.addOrReplaceChild("left_hind_leg", leg, PartPose.offset(4.0F, 12.0F, 7.0F));
        root.addOrReplaceChild("right_front_leg", leg, PartPose.offset(-4.0F, 12.0F, -6.0F));
        root.addOrReplaceChild("left_front_leg", leg, PartPose.offset(4.0F, 12.0F, -6.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }
}
