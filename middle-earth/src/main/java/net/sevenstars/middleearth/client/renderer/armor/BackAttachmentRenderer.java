package net.sevenstars.middleearth.client.renderer.armor;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.model.equipment.chest.ChestplateAddonModel;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.BackAttachmentDataComponent;
import net.sevenstars.middleearth.item.dataComponents.CustomDyeableDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ArmorModelsME;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

public class BackAttachmentRenderer implements ArmorRenderer {

    private ChestplateAddonModel backAttachmentModel;

    public BackAttachmentRenderer() {
    }

    static void renderDyeableBackAttachment(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack stack, Model model, Identifier texture, boolean chestplate) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(texture), stack.hasGlint());
        int color;
        if (chestplate){
            color =  ColorHelper.fullAlpha(stack.get(DataComponentTypesME.BACK_ATTACHMENT_DATA).backAttachmentColor());
        } else {
            color = CustomDyeableDataComponent.getColor(stack, CustomDyeableDataComponent.DEFAULT_COLOR);
        }
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, color);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, BipedEntityRenderState bipedEntityRenderState, EquipmentSlot slot, int light, BipedEntityModel<BipedEntityRenderState> contextModel) {
        if (slot == EquipmentSlot.CHEST) {
            BackAttachmentDataComponent backAttachmentDataComponent = stack.get(DataComponentTypesME.BACK_ATTACHMENT_DATA);

            if (backAttachmentDataComponent != null) {
                this.backAttachmentModel = ArmorModelsME.ModBackAttachmentPairedModels.valueOf(backAttachmentDataComponent.backAttachment().getName().toUpperCase()).getModel().getUnarmoredModel();
                contextModel.copyTransforms(backAttachmentModel);
                backAttachmentModel.setVisible(false);
                backAttachmentModel.body.visible = true;
                backAttachmentModel.rightArm.visible = true;
                backAttachmentModel.leftArm.visible = true;
                backAttachmentModel.rightLeg.visible = true;
                backAttachmentModel.leftLeg.visible = true;
                //this.capeModel.setAngles(bipedEntityRenderState);

                if (DyeablePiecesME.dyeableBackAttachments.containsKey(backAttachmentDataComponent.getBackAttachment())) {
                    renderDyeableBackAttachment(matrices, vertexConsumers, light, stack, backAttachmentModel, Identifier.of(MiddleEarth.MOD_ID, "textures/models/back_attachment/" + backAttachmentDataComponent.backAttachment().getName() + ".png"), false);
                    if (DyeablePiecesME.dyeableBackAttachments.get(backAttachmentDataComponent.backAttachment())){
                        ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, backAttachmentModel, Identifier.of(MiddleEarth.MOD_ID, "textures/models/back_attachment/" + backAttachmentDataComponent.backAttachment().getName() + "_overlay.png"));
                    }
                } else {
                    ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, backAttachmentModel, Identifier.of(MiddleEarth.MOD_ID, "textures/models/back_attachment/" + backAttachmentDataComponent.backAttachment().getName() + ".png"));
                }
            }
        }
    }
}
