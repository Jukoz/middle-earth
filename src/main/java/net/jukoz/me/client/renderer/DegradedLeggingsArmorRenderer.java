package net.jukoz.me.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.MiddleEarthClient;
import net.jukoz.me.client.model.equipment.CustomLeggingsModel;
import net.jukoz.me.recipe.ModTags;
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

public class DegradedLeggingsArmorRenderer implements ArmorRenderer {

    private CustomLeggingsModel<LivingEntity> customLeggingsModel;

    public DegradedLeggingsArmorRenderer() {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        this.customLeggingsModel = new CustomLeggingsModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CUSTOM_ARMOR_LEGGINGS));

        boolean dyeable = false;
        String texture;


        if (slot == EquipmentSlot.LEGS) {
            contextModel.copyBipedStateTo(customLeggingsModel);
            customLeggingsModel.setVisible(false);
            customLeggingsModel.body.visible = true;
            customLeggingsModel.rightLeg.visible = true;
            customLeggingsModel.leftLeg.visible = true;

            if (stack.isIn(ModTags.DYEABLE)) {
                dyeable = true;
            }

            if((double)stack.getDamage()/(double)stack.getMaxDamage() > 0.5){
                texture = "textures/models/armor/degraded_" + Registries.ITEM.getId(stack.getItem()).getPath() + ".png";
            } else {
                texture = "textures/models/armor/" + Registries.ITEM.getId(stack.getItem()).getPath() + ".png";
            }
            ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, customLeggingsModel, Identifier.of(MiddleEarth.MOD_ID, texture), dyeable);
        }
    }
}
