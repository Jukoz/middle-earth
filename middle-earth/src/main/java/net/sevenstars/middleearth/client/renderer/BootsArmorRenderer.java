package net.sevenstars.middleearth.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.MiddleEarthClient;
import net.sevenstars.middleearth.client.model.equipment.CustomBootsModel;
import net.sevenstars.middleearth.recipe.ModTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class BootsArmorRenderer implements ArmorRenderer {

    private CustomBootsModel customBootsModel;

    public BootsArmorRenderer() {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, BipedEntityRenderState bipedEntityRenderState, EquipmentSlot slot, int light, BipedEntityModel<BipedEntityRenderState> contextModel) {
        this.customBootsModel = new CustomBootsModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(MiddleEarthClient.CUSTOM_ARMOR_BOOTS));

        boolean dyeable = false;

        if (slot == EquipmentSlot.FEET) {
            contextModel.copyTransforms(customBootsModel);
            customBootsModel.setVisible(false);
            customBootsModel.rightLeg.visible = true;
            customBootsModel.leftLeg.visible = true;

            if (stack.isIn(ModTags.DYEABLE)) {
                dyeable = true;
            }

            String texture = "textures/models/armor/" + Registries.ITEM.getId(stack.getItem()).getPath() + ".png";
            ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, customBootsModel, Identifier.of(MiddleEarth.MOD_ID, texture), dyeable);
        }
    }
}
