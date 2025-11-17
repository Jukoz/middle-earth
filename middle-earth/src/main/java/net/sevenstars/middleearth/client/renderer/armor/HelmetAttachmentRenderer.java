package net.sevenstars.middleearth.client.renderer.armor;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.component.type.DyedColorComponent;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.HelmetAttachmentDataComponent;
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

public class HelmetAttachmentRenderer implements ArmorRenderer {

    public HelmetAttachmentRenderer() {
    }

    static void renderDyeableHelmetAttachment(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack stack, Model model, Identifier texture, boolean helmet) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(texture), stack.hasGlint());
        int color;
        if (helmet){
            color =  ColorHelper.fullAlpha(stack.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA).helmetAttachmentColor());
        } else {
            color = DyedColorComponent.getColor(stack, DyedColorComponent.DEFAULT_COLOR);
        }
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, color);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, BipedEntityRenderState bipedEntityRenderState, EquipmentSlot slot, int light, BipedEntityModel<BipedEntityRenderState> contextModel) {

        if (slot == EquipmentSlot.HEAD) {
            HelmetAttachmentDataComponent helmetAttachmentDataComponent = stack.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA);

            if(helmetAttachmentDataComponent != null) {
                Identifier texture;
                HelmetAddonModel helmetAttachmentModel;
                if (helmetAttachmentDataComponent.down()){
                    texture = Identifier.of(MiddleEarth.MOD_ID, "textures/models/helmet_attachment/" + helmetAttachmentDataComponent.helmetAttachment().getName().toLowerCase() + "_down.png");
                    helmetAttachmentModel = ArmorModelsME.ModHelmetAttachmentPairedModels.valueOf(helmetAttachmentDataComponent.helmetAttachment().getName().toUpperCase()).getModel().getUnarmoredDownModel();
                } else {
                    texture = Identifier.of(MiddleEarth.MOD_ID, "textures/models/helmet_attachment/" + helmetAttachmentDataComponent.helmetAttachment().getName().toLowerCase() + ".png");
                    helmetAttachmentModel = ArmorModelsME.ModHelmetAttachmentPairedModels.valueOf(helmetAttachmentDataComponent.helmetAttachment().getName().toUpperCase()).getModel().getUnarmoredModel();
                }
                contextModel.copyTransforms(helmetAttachmentModel);
                helmetAttachmentModel.setVisible(false);
                helmetAttachmentModel.head.visible = true;
                helmetAttachmentModel.hat.visible = true;
                if (DyeablePiecesME.dyeableHelmetAttachments.containsKey(helmetAttachmentDataComponent.getHelmetAttachment())) {
                    renderDyeableHelmetAttachment(matrices, vertexConsumers, light, stack, helmetAttachmentModel, texture, false);
                    if (DyeablePiecesME.dyeableHelmetAttachments.get(helmetAttachmentDataComponent.helmetAttachment())){
                        ModArmorRenderer.renderTranslucentPiece(matrices, vertexConsumers, light, stack, helmetAttachmentModel, Identifier.of(MiddleEarth.MOD_ID, texture.getPath().replaceAll(".png", "_overlay.png")));
                    }
                } else {
                    ModArmorRenderer.renderTranslucentPiece(matrices, vertexConsumers, light, stack, helmetAttachmentModel, texture);
                }
            }
        }
    }
}

