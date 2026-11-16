package net.sevenstars.middleearth.client.renderer.armor;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.registry.tag.ItemTags;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.model.equipment.CustomChestplateModel;
import net.sevenstars.middleearth.client.model.equipment.chest.ChestplateAddonModel;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.BackAttachmentDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ArmorModelsME;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ChestplateArmorRenderer implements ArmorRenderer {

    private final CustomChestplateModel customChestplateModel = new CustomChestplateModel(CustomChestplateModel.getTexturedModelData().createModel());;

    private ChestplateAddonModel chestplateAddonModel;

    public ChestplateArmorRenderer() {
    }

    public ChestplateArmorRenderer(ChestplateAddonModel chestplateModel) {
        this.chestplateAddonModel = chestplateModel;
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, ItemStack stack, BipedEntityRenderState state, EquipmentSlot slot, int light, BipedEntityModel<BipedEntityRenderState> contextModel) {
        boolean dyeable = false;

        if (slot == EquipmentSlot.CHEST) {
            customChestplateModel.setVisible(false);
            customChestplateModel.body.visible = true;
            customChestplateModel.rightArm.visible = true;
            customChestplateModel.leftArm.visible = true;
            customChestplateModel.rightLeg.visible = true;
            customChestplateModel.leftLeg.visible = true;

            if (stack.isIn(ItemTags.DYEABLE)) {
                dyeable = true;
            }

            String texture = "textures/models/armor/" + Registries.ITEM.getId(stack.getItem()).getPath() + ".png";
            CustomArmorRenderer.renderArmor(matrices, queue, light, stack, customChestplateModel, state, contextModel, Identifier.of(MiddleEarth.MOD_ID, texture), dyeable);

            if (this.chestplateAddonModel != null) {
                this.chestplateAddonModel.setVisible(false);
                this.chestplateAddonModel.body.visible = true;
                this.chestplateAddonModel.rightArm.visible = true;
                this.chestplateAddonModel.leftArm.visible = true;
                if(texture.contains("_chestplate.png")){
                    CustomArmorRenderer.renderArmor(matrices, queue, light, stack, this.chestplateAddonModel, state, contextModel, Identifier.of(MiddleEarth.MOD_ID, texture.replaceAll("_chestplate.png", "_addition.png")), dyeable);
                } else {
                    CustomArmorRenderer.renderArmor(matrices, queue, light, stack, this.chestplateAddonModel, state, contextModel, Identifier.of(MiddleEarth.MOD_ID, texture.replaceAll(".png", "_addition.png")), dyeable);
                }
            }

            BackAttachmentDataComponent capeDataComponent = stack.get(DataComponentTypesME.BACK_ATTACHMENT_DATA);
            if (capeDataComponent != null) {
                ChestplateAddonModel capeModel = ArmorModelsME.CustomBackAttachmentPairedModels.valueOf(capeDataComponent.backAttachment().getName().toUpperCase()).getModel().getArmoredModel();
                capeModel.setVisible(false);
                capeModel.body.visible = true;
                capeModel.rightArm.visible = true;
                capeModel.leftArm.visible = true;
                capeModel.rightLeg.visible = true;
                capeModel.leftLeg.visible = true;

                if (DyeablePiecesME.dyeableBackAttachments.containsKey(capeDataComponent.getBackAttachment())) {
                    BackAttachmentRenderer.renderDyeableBackAttachment(matrices, queue, light, stack, capeModel, state, Identifier.of(MiddleEarth.MOD_ID, "textures/models/back_attachment/" + capeDataComponent.backAttachment().getName() + ".png"), true);
                    if (DyeablePiecesME.dyeableBackAttachments.get(capeDataComponent.backAttachment()).booleanValue()){
                        CustomArmorRenderer.renderTranslucentPiece(matrices, queue, light, stack, capeModel, state, null, Identifier.of(MiddleEarth.MOD_ID, "textures/models/back_attachment/" + capeDataComponent.backAttachment().getName() + "_overlay.png"));
                    }
                } else {
                    CustomArmorRenderer.renderTranslucentPiece(matrices, queue, light, stack, capeModel, state, null, Identifier.of(MiddleEarth.MOD_ID, "textures/models/back_attachment/" + capeDataComponent.backAttachment().getName() + ".png"));
                }}
        }
    }
}
