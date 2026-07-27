package net.sevenstars.middleearth.client.renderer.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.sevenstars.middleearth.client.model.equipment.chest.ChestplateAddonModel;
import net.sevenstars.middleearth.client.renderer.RenderResourceCache;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.BackAttachmentDataComponent;
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

public class BackAttachmentRenderer implements ArmorRenderer {

    public BackAttachmentRenderer() {
    }

    static void renderDyeableBackAttachment(PoseStack matrices, MultiBufferSource vertexConsumers, int light, ItemStack stack, Model model, ResourceLocation texture, boolean chestplate) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumers, RenderType.armorCutoutNoCull(texture), stack.hasFoil());
        int color;
        if (chestplate){
            color =  (0xFF000000 | (stack.get(DataComponentTypesME.BACK_ATTACHMENT_DATA).backAttachmentColor() & 0xFFFFFF));
        } else {
            color = DyedItemColor.getOrDefault(stack, DyedItemColor.LEATHER_COLOR);
        }
        model.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, color);
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {
        if (slot == EquipmentSlot.CHEST) {
            BackAttachmentDataComponent backAttachmentDataComponent = stack.get(DataComponentTypesME.BACK_ATTACHMENT_DATA);

            if (backAttachmentDataComponent != null) {
                var attachment = backAttachmentDataComponent.backAttachment();
                var attachmentTextures = RenderResourceCache.backAttachment(attachment);
                ChestplateAddonModel backAttachmentModel = ArmorModelsME.ModBackAttachmentPairedModels
                        .valueOf(attachment.name()).getModel().getUnarmoredModel();
                contextModel.copyPropertiesTo(backAttachmentModel);
                backAttachmentModel.setAllVisible(false);
                backAttachmentModel.body.visible = true;
                backAttachmentModel.rightArm.visible = true;
                backAttachmentModel.leftArm.visible = true;
                backAttachmentModel.rightLeg.visible = true;
                backAttachmentModel.leftLeg.visible = true;
                backAttachmentModel.setupAnim(entity, entity.walkAnimation.position(), entity.walkAnimation.speed(), entity.tickCount, contextModel.head.yRot, contextModel.head.xRot);

                if (DyeablePiecesME.dyeableBackAttachments.containsKey(backAttachmentDataComponent.getBackAttachment())) {
                    renderDyeableBackAttachment(
                            matrices, vertexConsumers, light, stack, backAttachmentModel,
                            attachmentTextures.base(), false);
                    if (DyeablePiecesME.dyeableBackAttachments.get(attachment)){
                        ArmorRenderer.renderPart(
                                matrices, vertexConsumers, light, stack, backAttachmentModel,
                                attachmentTextures.overlay());
                    }
                } else {
                    ArmorRenderer.renderPart(
                            matrices, vertexConsumers, light, stack, backAttachmentModel,
                            attachmentTextures.base());
                }
            }
        }
    }
}
