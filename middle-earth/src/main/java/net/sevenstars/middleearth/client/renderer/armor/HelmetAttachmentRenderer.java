package net.sevenstars.middleearth.client.renderer.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;
import net.sevenstars.middleearth.client.renderer.RenderResourceCache;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.HelmetAttachmentDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ArmorModelsME;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

public class HelmetAttachmentRenderer implements ArmorRenderer {

    public HelmetAttachmentRenderer() {
    }

    static void renderDyeableHelmetAttachment(PoseStack matrices, MultiBufferSource vertexConsumers, int light, ItemStack stack, Model model, ResourceLocation texture, boolean helmet) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumers, RenderType.armorCutoutNoCull(texture), stack.hasFoil());
        int color;
        if (helmet){
            color =  (0xFF000000 | (stack.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA).helmetAttachmentColor() & 0xFFFFFF));
        } else {
            color = DyedItemColor.getOrDefault(stack, DyedItemColor.LEATHER_COLOR);
        }
        model.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, color);
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {

        if (slot == EquipmentSlot.HEAD) {
            HelmetAttachmentDataComponent helmetAttachmentDataComponent = stack.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA);

            if(helmetAttachmentDataComponent != null) {
                var attachment = helmetAttachmentDataComponent.helmetAttachment();
                var attachmentTextures =
                        RenderResourceCache.helmetAttachment(attachment, helmetAttachmentDataComponent.down());
                HelmetAddonModel helmetAttachmentModel;
                if (helmetAttachmentDataComponent.down()){
                    helmetAttachmentModel = ArmorModelsME.ModHelmetAttachmentPairedModels
                            .valueOf(attachment.name()).getModel().getUnarmoredDownModel();
                } else {
                    helmetAttachmentModel = ArmorModelsME.ModHelmetAttachmentPairedModels
                            .valueOf(attachment.name()).getModel().getUnarmoredModel();
                }
                contextModel.copyPropertiesTo(helmetAttachmentModel);
                helmetAttachmentModel.setAllVisible(false);
                helmetAttachmentModel.head.visible = true;
                helmetAttachmentModel.hat.visible = true;
                if (DyeablePiecesME.dyeableHelmetAttachments.containsKey(helmetAttachmentDataComponent.getHelmetAttachment())) {
                    renderDyeableHelmetAttachment(
                            matrices, vertexConsumers, light, stack, helmetAttachmentModel,
                            attachmentTextures.base(), false);
                    if (DyeablePiecesME.dyeableHelmetAttachments.get(attachment)){
                        ModArmorRenderer.renderTranslucentPiece(
                                matrices, vertexConsumers, light, stack, helmetAttachmentModel,
                                attachmentTextures.overlay());
                    }
                } else {
                    ModArmorRenderer.renderTranslucentPiece(
                            matrices, vertexConsumers, light, stack, helmetAttachmentModel,
                            attachmentTextures.base());
                }
            }
        }
    }
}

