package net.sevenstars.middleearth.client.renderer.armor;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.registry.tag.ItemTags;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.model.equipment.CustomLeggingsModel;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class LeggingsArmorRenderer implements ArmorRenderer {

    private final CustomLeggingsModel customLeggingsModel = new CustomLeggingsModel(CustomLeggingsModel.getTexturedModelData().createModel());

    public LeggingsArmorRenderer() {
    }


    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, ItemStack stack, BipedEntityRenderState state, EquipmentSlot slot, int light, BipedEntityModel<BipedEntityRenderState> contextModel) {
        boolean dyeable = false;

        if (slot == EquipmentSlot.LEGS) {
            customLeggingsModel.setVisible(false);
            customLeggingsModel.body.visible = true;
            customLeggingsModel.rightLeg.visible = true;
            customLeggingsModel.leftLeg.visible = true;

            if (stack.isIn(ItemTags.DYEABLE)) {
                dyeable = true;
            }

            String texture = "textures/models/armor/" + Registries.ITEM.getId(stack.getItem()).getPath() + ".png";
            CustomArmorRenderer.renderArmor(matrices, queue, light, stack, customLeggingsModel, state, contextModel, Identifier.of(MiddleEarth.MOD_ID, texture), dyeable);
        }
    }
}
