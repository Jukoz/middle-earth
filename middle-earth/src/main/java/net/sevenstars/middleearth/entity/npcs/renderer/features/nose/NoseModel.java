package net.sevenstars.middleearth.entity.npcs.renderer.features.nose;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

public class NoseModel extends HierarchicalModel<NpcEntity> {
    private final ModelPart root;
    public final ModelPart noseRoot;

    public final ModelPart squareCube;
    public final ModelPart villagerCube;
    public final ModelPart largeHighCube;
    public final ModelPart largeCenterCube;

    public NoseModel(ModelPart modelPart) {
        this.root = modelPart;
        this.noseRoot = modelPart.getChild("noseRoot");
        this.squareCube = noseRoot.getChild("squareCube");
        this.villagerCube = noseRoot.getChild("villagerCube");

        this.largeHighCube = noseRoot.getChild("largeHighCube");
        this.largeCenterCube = noseRoot.getChild("largeCenterCube");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition cubeRootPart = modelPartData.addOrReplaceChild("noseRoot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition cubeNosePart = cubeRootPart.addOrReplaceChild("squareCube", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        cubeNosePart.addOrReplaceChild("cube", CubeListBuilder.create().texOffs(0, 2).addBox(-1.0f, -4f, -4.5f, 2, 2, 1, CubeDeformation.NONE), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition villagerCubePart = cubeRootPart.addOrReplaceChild("villagerCube", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        villagerCubePart.addOrReplaceChild("cube", CubeListBuilder.create().texOffs(0, 6).addBox(-1.0f, -4f, -6f, 2, 4, 2, CubeDeformation.NONE), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition largeHighCubePart = cubeRootPart.addOrReplaceChild("largeHighCube", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        largeHighCubePart.addOrReplaceChild("cube", CubeListBuilder.create().texOffs(11, 0).addBox(-2.0f, -6f, -5f, 4, 3, 1, CubeDeformation.NONE), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition largeCenterCubePart = cubeRootPart.addOrReplaceChild("largeCenterCube", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        largeCenterCubePart.addOrReplaceChild("cube", CubeListBuilder.create().texOffs(11, 5).addBox(-2.0f, -5f, -5f, 4, 3, 1, CubeDeformation.NONE), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 32, 32);
    }

    @Override
    public void setupAnim(NpcEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.noseRoot.resetPose();
        float swimAmount = entity.getSwimAmount(0.0F);
        this.noseRoot.xRot = headPitch * Mth.DEG_TO_RAD;
        this.noseRoot.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        if (entity.isFallFlying()) {
            this.noseRoot.xRot = -0.7853982F;
        } else if (swimAmount > 0.0F) {
            this.noseRoot.xRot = ModelUtils.rotlerpRad(this.noseRoot.xRot, -0.7853982F, swimAmount);
        }
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
