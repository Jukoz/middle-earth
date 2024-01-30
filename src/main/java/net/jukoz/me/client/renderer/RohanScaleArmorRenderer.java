package net.jukoz.me.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.MiddleEarthClient;
import net.jukoz.me.client.model.equipment.RohanTier4ArmourModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class RohanScaleArmorRenderer implements ArmorRenderer {
    private static final Identifier ROHAN_4_HELMET_ADDON = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/rohan_scale_helmet_addon.png");
    private static final Identifier ROHAN_SCALE_LAYER_0 = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/rohan_scale_layer_0.png");
    private static final Identifier ROHAN_SCALE_LAYER_1 = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/rohan_scale_layer_1.png");
    private static final Identifier ROHAN_SCALE_LAYER_2 = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/rohan_scale_layer_2.png");

    private static RohanTier4ArmourModel<LivingEntity> armorModel;
    private static RohanTier4ArmourModel<LivingEntity> helmetModel;

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        if (armorModel == null) {
            armorModel = new RohanTier4ArmourModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.ROHAN_SCALE_ARMOR_MODEL_LAYER));
            helmetModel = new RohanTier4ArmourModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.ROHAN_SCALE_ARMOR_MODEL_LAYER));
        }
        if (slot == EquipmentSlot.HEAD) {
            contextModel.copyBipedStateTo(helmetModel);
            helmetModel.setVisible(false);
            helmetModel.headornament.visible = true;
            helmetModel.head.visible = true;
            helmetModel.hat.visible = true;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, helmetModel, ROHAN_SCALE_LAYER_1);
        } else if (slot == EquipmentSlot.CHEST) {
            contextModel.copyBipedStateTo(helmetModel);
            armorModel.setVisible(false);
            armorModel.body_undersuit.visible = true;
            armorModel.body.visible = true;
            armorModel.rightArm.visible = true;
            armorModel.leftArm.visible = true;
            armorModel.right_arm_undersuit.visible = true;
            armorModel.left_arm_undersuit.visible = true;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, armorModel, ROHAN_SCALE_LAYER_1);
        } else if (slot == EquipmentSlot.LEGS) {
            contextModel.copyBipedStateTo(helmetModel);
            armorModel.setVisible(false);
            armorModel.rightLeg.visible = true;
            armorModel.leftLeg.visible = true;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, armorModel, ROHAN_SCALE_LAYER_2);
        } else if (slot == EquipmentSlot.FEET) {
            contextModel.copyBipedStateTo(helmetModel);
            armorModel.setVisible(false);
            armorModel.rightLeg.visible = true;
            armorModel.leftLeg.visible = true;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, armorModel, ROHAN_SCALE_LAYER_1);
        } /*else {
            boolean helmet = stack.getItem() == helmetItem;
            contextModel.copyBipedStateTo(armorModel);
            armorModel.setVisible(false);
            armorModel.head.visible = slot == EquipmentSlot.HEAD;
            armorModel.body.visible = slot == EquipmentSlot.CHEST;
            armorModel.leftArm.visible = slot == EquipmentSlot.CHEST;
            armorModel.rightArm.visible = slot == EquipmentSlot.CHEST;
            armorModel.leftLeg.visible = slot == EquipmentSlot.LEGS;
            armorModel.rightLeg.visible = slot == EquipmentSlot.LEGS;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, armorModel, helmet  ? ROHAN_4_HELMET_ADDON : ROHAN_SCALE_LAYER_0);
        }*/
    }
}
