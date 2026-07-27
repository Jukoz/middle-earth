package net.sevenstars.of_beasts_and_wild_things.entity.snail;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

import static java.lang.Math.PI;

public class SnailEntityModel extends HierarchicalModel<SnailEntity> {
    private final ModelPart root;

    protected SnailEntityModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition snail = modelPartData.addOrReplaceChild("snail", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition body = snail.addOrReplaceChild("body", CubeListBuilder.create().texOffs(12, 22).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, -1).addBox(-1.0F, -4.0F, -4.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, -1).mirror().addBox(1.0F, -4.0F, -4.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 3.0F, 0.0F));
        return LayerDefinition.create(modelData, 32, 32);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(SnailEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        this.root.getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(SnailEntityAnimations.CRAWL, limbSwing, limbSwingAmount, 20.0F, 30.0F);
        this.root.getChild("snail").xRot = -(float) PI / 2
                * ((float) entity.getClimbingTicks() / SnailEntity.CLIMBING_TIME_TRANSITION);
    }
}
