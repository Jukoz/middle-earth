package net.sevenstars.middleearth.entity.npcs.renderer;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.HumanoidArm;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;

public class NpcEntityModel extends HumanoidModel<NpcEntity> {
    private static final String LEFT_SLEEVE = "left_sleeve";
    private static final String RIGHT_SLEEVE = "right_sleeve";
    private static final String LEFT_PANTS = "left_pants";
    private static final String RIGHT_PANTS = "right_pants";
    private final List<ModelPart> parts;
    public final ModelPart leftSleeve;
    public final ModelPart rightSleeve;
    public final ModelPart leftPants;
    public final ModelPart rightPants;
    public final ModelPart jacket;

    public NpcEntityModel(ModelPart modelPart) {
        super(modelPart, RenderType::entityTranslucent);
        this.leftSleeve = this.leftArm.getChild("left_sleeve");
        this.rightSleeve = this.rightArm.getChild("right_sleeve");
        this.leftPants = this.leftLeg.getChild("left_pants");
        this.rightPants = this.rightLeg.getChild("right_pants");
        this.jacket = this.body.getChild("jacket");
        this.parts = List.of(this.head, this.body, this.leftArm, this.rightArm, this.leftLeg, this.rightLeg);
    }

    public static LayerDefinition getTexturedModelData(CubeDeformation dilation) {
        MeshDefinition modelData = HumanoidModel.createMesh(dilation, 0.0F);
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition modelPartData2;
        PartDefinition modelPartData3;

        modelPartData2 = modelPartData.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), PartPose.offset(5.0F, 2.0F, 0.0F));
        modelPartData3 = modelPartData.getChild("right_arm");
        modelPartData2.addOrReplaceChild(LEFT_SLEEVE, CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.extend(0.25F)), PartPose.ZERO);
        modelPartData3.addOrReplaceChild(RIGHT_SLEEVE, CubeListBuilder.create().texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.extend(0.25F)), PartPose.ZERO);

        modelPartData2 = modelPartData.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), PartPose.offset(1.9F, 12.0F, 0.0F));
        modelPartData3 = modelPartData.getChild("right_leg");
        modelPartData2.addOrReplaceChild(LEFT_PANTS, CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.extend(0.25F)), PartPose.ZERO);
        modelPartData3.addOrReplaceChild(RIGHT_PANTS, CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation.extend(0.25F)), PartPose.ZERO);
        PartDefinition modelPartData4 = modelPartData.getChild("body");
        modelPartData4.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, dilation.extend(0.25F)), PartPose.ZERO);
        return LayerDefinition.create(modelData, 64, 64);
    }

    public void setupAnim(NpcEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.leftArmPose = NpcEntityRenderer.getArmPose(
                entity, entity.getOffhandItem(), net.minecraft.world.InteractionHand.OFF_HAND);
        this.rightArmPose = NpcEntityRenderer.getArmPose(
                entity, entity.getMainHandItem(), net.minecraft.world.InteractionHand.MAIN_HAND);
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    public void setAllVisible(boolean visible) {
        super.setAllVisible(visible);
        this.leftSleeve.visible = visible;
        this.rightSleeve.visible = visible;
        this.leftPants.visible = visible;
        this.rightPants.visible = visible;
        this.jacket.visible = visible;
    }

    public void translateToHand(HumanoidArm arm, PoseStack matrices) {
        super.translateToHand(arm, matrices);
    }
}
