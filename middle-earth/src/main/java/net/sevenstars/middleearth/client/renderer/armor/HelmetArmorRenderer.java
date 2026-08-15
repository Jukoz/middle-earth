package net.sevenstars.middleearth.client.renderer.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.client.model.equipment.CustomHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;
import net.sevenstars.middleearth.client.renderer.RenderResourceCache;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.dataComponents.HelmetAttachmentDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ArmorModelsME;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;

public class HelmetArmorRenderer implements ArmorRenderer {

    private final CustomHelmetModel customHelmetModel = new CustomHelmetModel(CustomHelmetModel.getTexturedModelData().bakeRoot());
    private HelmetAddonModel helmetAddonModel;

    public HelmetArmorRenderer() {
    }

    public HelmetArmorRenderer(HelmetAddonModel helmetModel) {
        this.helmetAddonModel = helmetModel;
    }


    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {
        boolean dyeable = false;

        if (slot == EquipmentSlot.HEAD) {
            contextModel.copyPropertiesTo(customHelmetModel);
            customHelmetModel.setAllVisible(false);
            customHelmetModel.head.visible = true;
            customHelmetModel.hat.visible = true;
            customHelmetModel.body.visible = true;
            customHelmetModel.leftArm.visible = true;
            customHelmetModel.rightArm.visible = true;

            if(stack.is(ItemTags.DYEABLE)) {
                dyeable = true;
            }

            RenderResourceCache.ArmorTextures armorTextures = RenderResourceCache.armor(stack.getItem());
            ModArmorRenderer.renderArmor(
                    matrices, vertexConsumers, light, stack, customHelmetModel, armorTextures.base(), dyeable);

            if (this.helmetAddonModel != null) {
                contextModel.copyPropertiesTo(this.helmetAddonModel);
                this.helmetAddonModel.setAllVisible(false);
                this.helmetAddonModel.head.visible = true;
                if (stack.is(EquipmentItemsME.GLASSES) || stack.is(EquipmentItemsME.DWARVEN_MONOCLE)) {
                    ModArmorRenderer.renderTranslucentPiece(matrices, vertexConsumers, light, stack,
                            this.helmetAddonModel, armorTextures.helmetAddition().base());
                } else {
                    ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, this.helmetAddonModel,
                            armorTextures.helmetAddition(), dyeable);
                }
            }

            HelmetAttachmentDataComponent hoodDataComponent = stack.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA);

            if(hoodDataComponent != null) {
                var attachment = hoodDataComponent.helmetAttachment();
                var attachmentTextures = RenderResourceCache.helmetAttachment(attachment, hoodDataComponent.down());
                HelmetAddonModel helmetAttachmentModel;
                if (hoodDataComponent.down()){
                    helmetAttachmentModel = ArmorModelsME.ModHelmetAttachmentPairedModels
                            .valueOf(attachment.name()).getModel().getArmoredDownModel();
                } else {
                    helmetAttachmentModel = ArmorModelsME.ModHelmetAttachmentPairedModels
                            .valueOf(attachment.name()).getModel().getArmoredModel();
                }
                contextModel.copyPropertiesTo(helmetAttachmentModel);
                helmetAttachmentModel.setAllVisible(false);
                helmetAttachmentModel.head.visible = true;
                helmetAttachmentModel.hat.visible = true;
                if (DyeablePiecesME.dyeableHelmetAttachments.containsKey(hoodDataComponent.getHelmetAttachment())) {
                    HelmetAttachmentRenderer.renderDyeableHelmetAttachment(
                            matrices, vertexConsumers, light, stack, helmetAttachmentModel,
                            attachmentTextures.base(), true);
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
