package net.jukoz.me.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.MiddleEarthClient;
import net.jukoz.me.client.model.equipment.CustomBootsModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class DegradedBootsArmorRenderer implements ArmorRenderer {

    private CustomBootsModel<LivingEntity> customBootsModel;

    public DegradedBootsArmorRenderer() {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        this.customBootsModel = new CustomBootsModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CUSTOM_ARMOR_BOOTS));

        boolean dyeable = false;
        String texture;

        if (slot == EquipmentSlot.FEET) {
            contextModel.copyBipedStateTo(customBootsModel);
            customBootsModel.setVisible(false);
            customBootsModel.rightLeg.visible = true;
            customBootsModel.leftLeg.visible = true;

            if (stack.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "dyeable")))) {
                dyeable = true;
            }

            if((double)stack.getDamage()/(double)stack.getMaxDamage() > 0.5){
                texture = "textures/models/armor/degraded_" + Registries.ITEM.getId(stack.getItem()).getPath() + ".png";
            } else {
                texture = "textures/models/armor/" + Registries.ITEM.getId(stack.getItem()).getPath() + ".png";
            }
            ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, customBootsModel, Identifier.of(MiddleEarth.MOD_ID, texture), dyeable);
        }
    }
}
