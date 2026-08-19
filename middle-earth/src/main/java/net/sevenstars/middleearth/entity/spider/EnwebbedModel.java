package net.sevenstars.middleearth.entity.spider;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class EnwebbedModel<T extends LivingEntity> extends EntityModel<T> {
    private final ModelPart bigBody;
    private final ModelPart smallBody;

    public EnwebbedModel(ModelPart root) {
        this.bigBody = root.getChild("big_body");
        this.smallBody = root.getChild("small_body");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition dataRoot = modelData.getRoot();
        PartDefinition bigBody = dataRoot.addOrReplaceChild("big_body", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-8.0F, -23.5F, -2.0F, 16.0F, 11.0F, 4.0F, new CubeDeformation(1.15F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition smallBody = dataRoot.addOrReplaceChild("small_body", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-8.0F, -23.5F, -2.0F, 16.0F, 11.0F, 4.0F, new CubeDeformation(0.65F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean hasChestplate = !entity.getItemBySlot(EquipmentSlot.CHEST).isEmpty();
        bigBody.skipDraw = !hasChestplate;
        smallBody.skipDraw = hasChestplate;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack,
                               VertexConsumer vertexConsumer,
                               int packedLight, int packedOverlay, int packedColor) {
        this.bigBody.render(poseStack, vertexConsumer, packedLight, packedOverlay, packedColor);
        this.smallBody.render(poseStack, vertexConsumer, packedLight, packedOverlay, packedColor);
    }
}
