package net.sevenstars.middleearth.client.renderer.armor;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.registry.tag.ItemTags;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.model.equipment.CustomBootsModel;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class BootsArmorRenderer implements ArmorRenderer {

    private final CustomBootsModel customBootsModel = new CustomBootsModel(CustomBootsModel.getTexturedModelData().createModel());

    public BootsArmorRenderer() {
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, ItemStack stack, BipedEntityRenderState state, EquipmentSlot slot, int light, BipedEntityModel<BipedEntityRenderState> contextModel) {
        boolean dyeable = false;

        if (slot == EquipmentSlot.FEET) {
            customBootsModel.setVisible(false);
            customBootsModel.rightLeg.visible = true;
            customBootsModel.leftLeg.visible = true;

            if (stack.isIn(ItemTags.DYEABLE)) {
                dyeable = true;
            }

            String texture = "textures/models/armor/" + Registries.ITEM.getId(stack.getItem()).getPath() + ".png";
            CustomArmorRenderer.renderArmor(matrices, queue, light, stack, customBootsModel, state, contextModel, Identifier.of(MiddleEarth.MOD_ID, texture), dyeable);
        }
    }
}
