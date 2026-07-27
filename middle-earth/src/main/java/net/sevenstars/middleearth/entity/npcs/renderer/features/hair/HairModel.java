package net.sevenstars.middleearth.entity.npcs.renderer.features.hair;

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

public class HairModel extends HierarchicalModel<NpcEntity> {
    private final ModelPart root;
    // https://i.pinimg.com/736x/9c/56/05/9c560508ceba0bc87b9d5beda7391adc.jpg
    public static final String BEARD = "beard";
    public final ModelPart hair;
    public final ModelPart hairBase;
    public final ModelPart hairHat;
    public final ModelPart largeBeard;

    public HairModel(ModelPart modelPart) {
        this.root = modelPart;
        this.hair = modelPart.getChild("hair");
        this.hairBase = hair.getChild("hairBase");
        this.hairHat = hair.getChild("hairHat");
        this.largeBeard = hair.getChild("largeBeard");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition hairGroup = modelPartData.addOrReplaceChild("hair", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        hairGroup.addOrReplaceChild("hairBase", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, 0f, -4.0f, 8, 11, 8, CubeDeformation.NONE), PartPose.offset(0.0F, 0.0F, 0.0F));
        hairGroup.addOrReplaceChild("hairHat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0f, 1f, -4f, 8, 11, 8, CubeDeformation.NONE.extend(0.5f)), PartPose.offset(0.0F, 0.0F, 0.0F));
        hairGroup.addOrReplaceChild("largeBeard", CubeListBuilder.create().texOffs(37, 37).addBox(-6.5F, -3.5F, -4.1F,14.0F, 25.0F, -0.5F, CubeDeformation.NONE.extend(0.25f)), PartPose.ZERO);
        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void setupAnim(NpcEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.hair.resetPose();
        float swimAmount = entity.getSwimAmount(0.0F);
        this.hair.xRot = headPitch * Mth.DEG_TO_RAD;
        this.hair.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        if (entity.isFallFlying()) {
            this.hair.xRot = -0.7853982F;
        } else if (swimAmount > 0.0F) {
            this.hair.xRot = ModelUtils.rotlerpRad(this.hair.xRot, -0.7853982F, swimAmount);
        }
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
