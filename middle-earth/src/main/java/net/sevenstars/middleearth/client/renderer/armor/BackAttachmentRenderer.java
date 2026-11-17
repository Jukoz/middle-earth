package net.sevenstars.middleearth.client.renderer.armor;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.component.type.DyedColorComponent;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.model.equipment.chest.ChestplateAddonModel;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.BackAttachmentDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ArmorModelsME;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

public class BackAttachmentRenderer implements ArmorRenderer {

    public BackAttachmentRenderer() {
    }

    static <S extends BipedEntityRenderState> void renderDyeableBackAttachment(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, ItemStack stack, Model<? super S> model, S state, Identifier texture, boolean chestplate) {
        int color;
        if (chestplate){
            color =  ColorHelper.fullAlpha(stack.get(DataComponentTypesME.BACK_ATTACHMENT_DATA).backAttachmentColor());
        } else {
            color = DyedColorComponent.getColor(stack, DyedColorComponent.DEFAULT_COLOR);
        }
        CustomArmorRenderer.submit(matrices, queue, light, stack, model, state, null, RenderLayers.armorCutoutNoCull(texture), color, 0);
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, ItemStack stack, BipedEntityRenderState state, EquipmentSlot slot, int light, BipedEntityModel<BipedEntityRenderState> contextModel) {
        if (slot == EquipmentSlot.CHEST) {
            BackAttachmentDataComponent backAttachmentDataComponent = stack.get(DataComponentTypesME.BACK_ATTACHMENT_DATA);

            if (backAttachmentDataComponent != null) {
                ChestplateAddonModel backAttachmentModel = ArmorModelsME.CustomBackAttachmentPairedModels.valueOf(backAttachmentDataComponent.backAttachment().getName().toUpperCase()).getModel().getUnarmoredModel();
                backAttachmentModel.setVisible(false);
                backAttachmentModel.body.visible = true;
                backAttachmentModel.rightArm.visible = true;
                backAttachmentModel.leftArm.visible = true;
                backAttachmentModel.rightLeg.visible = true;
                backAttachmentModel.leftLeg.visible = true;

                if (DyeablePiecesME.dyeableBackAttachments.containsKey(backAttachmentDataComponent.getBackAttachment())) {
                    renderDyeableBackAttachment(matrices, queue, light, stack, backAttachmentModel, state, Identifier.of(MiddleEarth.MOD_ID, "textures/models/back_attachment/" + backAttachmentDataComponent.backAttachment().getName() + ".png"), false);
                    if (DyeablePiecesME.dyeableBackAttachments.get(backAttachmentDataComponent.backAttachment())){
                        CustomArmorRenderer.renderTranslucentPiece(matrices, queue, light, stack, backAttachmentModel, state, null, Identifier.of(MiddleEarth.MOD_ID, "textures/models/back_attachment/" + backAttachmentDataComponent.backAttachment().getName() + "_overlay.png"));
                    }
                } else {
                    CustomArmorRenderer.renderTranslucentPiece(matrices, queue, light, stack, backAttachmentModel, state, null, Identifier.of(MiddleEarth.MOD_ID, "textures/models/back_attachment/" + backAttachmentDataComponent.backAttachment().getName() + ".png"));
                }
            }
        }
    }
}
