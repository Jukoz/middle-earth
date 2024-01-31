package net.jukoz.me.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.MiddleEarthClient;
import net.jukoz.me.client.model.equipment.InnerArmorModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.ArmorEntityModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModArmorRenderer implements ArmorRenderer {
    private static final Identifier ROHAN_SCALE_LAYER_0 = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/rohan_scale_layer_0.png");
    private static final Identifier ROHAN_SCALE_LAYER_1 = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/rohan_scale_layer_1.png");
    private static final Identifier ROHAN_SCALE_LAYER_2 = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/rohan_scale_layer_2.png");

    private static InnerArmorModel<LivingEntity> innerArmorModel;
    private static ArmorEntityModel<LivingEntity> armorModel;
    private static ArmorEntityModel<LivingEntity> legArmorModel;

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        if (innerArmorModel == null) {
            innerArmorModel = new InnerArmorModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.INNER_ARMOR_MODEL_LAYER));
            armorModel = new ArmorEntityModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EntityModelLayers.PLAYER_OUTER_ARMOR));
            legArmorModel = new ArmorEntityModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EntityModelLayers.PLAYER_INNER_ARMOR));
        }
        if (slot == EquipmentSlot.HEAD) {
            contextModel.copyBipedStateTo(armorModel);
            armorModel.setVisible(false);
            armorModel.head.visible = true;
            armorModel.hat.visible = true;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, armorModel, ROHAN_SCALE_LAYER_1);
        } else
            if (slot == EquipmentSlot.CHEST) {
            contextModel.copyBipedStateTo(innerArmorModel);
            innerArmorModel.setVisible(false);
            innerArmorModel.body.visible = true;
            innerArmorModel.rightArm.visible = true;
            innerArmorModel.leftArm.visible = true;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, innerArmorModel, ROHAN_SCALE_LAYER_0);
            contextModel.copyBipedStateTo(armorModel);
            armorModel.setVisible(false);
            armorModel.body.visible = true;
            armorModel.rightArm.visible = true;
            armorModel.leftArm.visible = true;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, armorModel, ROHAN_SCALE_LAYER_1);
        } else if (slot == EquipmentSlot.LEGS) {
            contextModel.copyBipedStateTo(innerArmorModel);
            innerArmorModel.setVisible(false);
            innerArmorModel.rightLeg.visible = true;
            innerArmorModel.leftLeg.visible = true;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, innerArmorModel, ROHAN_SCALE_LAYER_0);
            contextModel.copyBipedStateTo(legArmorModel);
            legArmorModel.setVisible(false);
            legArmorModel.body.visible = true;
            legArmorModel.rightLeg.visible = true;
            legArmorModel.leftLeg.visible = true;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, legArmorModel, ROHAN_SCALE_LAYER_2);
        } else if (slot == EquipmentSlot.FEET) {
            contextModel.copyBipedStateTo(armorModel);
            armorModel.setVisible(false);
            armorModel.rightLeg.visible = true;
            armorModel.leftLeg.visible = true;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, armorModel, ROHAN_SCALE_LAYER_1);
        }
    }
}
