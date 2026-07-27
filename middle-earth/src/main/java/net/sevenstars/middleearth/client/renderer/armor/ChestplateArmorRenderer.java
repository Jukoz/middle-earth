package net.sevenstars.middleearth.client.renderer.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.client.model.equipment.CustomChestplateModel;
import net.sevenstars.middleearth.client.model.equipment.chest.ChestplateAddonModel;
import net.sevenstars.middleearth.client.renderer.RenderResourceCache;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.BackAttachmentDataComponent;
import net.sevenstars.middleearth.item.items.armor.CustomChestplateItem;
import net.sevenstars.middleearth.item.utils.armor.ArmorModelsME;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;

public class ChestplateArmorRenderer implements ArmorRenderer {

    private final CustomChestplateModel customChestplateModel = new CustomChestplateModel(CustomChestplateModel.getTexturedModelData().bakeRoot());;

    private ChestplateAddonModel chestplateAddonModel;

    public ChestplateArmorRenderer() {
    }

    public ChestplateArmorRenderer(ChestplateAddonModel chestplateModel) {
        this.chestplateAddonModel = chestplateModel;
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {
        boolean dyeable = false;

        if (slot == EquipmentSlot.CHEST) {
            contextModel.copyPropertiesTo(customChestplateModel);
            customChestplateModel.setAllVisible(false);
            customChestplateModel.body.visible = true;
            customChestplateModel.rightArm.visible = true;
            customChestplateModel.leftArm.visible = true;
            customChestplateModel.rightLeg.visible = true;
            customChestplateModel.leftLeg.visible = true;

            if (stack.is(ItemTags.DYEABLE)) {
                dyeable = true;
            }

            RenderResourceCache.ArmorTextures armorTextures = RenderResourceCache.armor(stack.getItem());
            ModArmorRenderer.renderArmor(
                    matrices, vertexConsumers, light, stack, customChestplateModel, armorTextures.base(), dyeable);

            if (this.chestplateAddonModel != null) {
                contextModel.copyPropertiesTo(this.chestplateAddonModel);
                this.chestplateAddonModel.setAllVisible(false);
                this.chestplateAddonModel.body.visible = true;
                this.chestplateAddonModel.rightArm.visible = true;
                this.chestplateAddonModel.leftArm.visible = true;
                ModArmorRenderer.renderArmor(
                        matrices, vertexConsumers, light, stack, this.chestplateAddonModel,
                        armorTextures.chestplateAddition(), dyeable);
            }

            BackAttachmentDataComponent capeDataComponent = stack.get(DataComponentTypesME.BACK_ATTACHMENT_DATA);
            if (capeDataComponent != null) {
                ChestplateAddonModel capeModel = ArmorModelsME.ModBackAttachmentPairedModels.valueOf(capeDataComponent.backAttachment().getName().toUpperCase()).getModel().getArmoredModel();
                contextModel.copyPropertiesTo(capeModel);
                capeModel.setAllVisible(false);
                capeModel.body.visible = true;
                capeModel.rightArm.visible = true;
                capeModel.leftArm.visible = true;
                capeModel.rightLeg.visible = true;
                capeModel.leftLeg.visible = true;
                capeModel.setupAnim(entity, entity.walkAnimation.position(), entity.walkAnimation.speed(), entity.tickCount, contextModel.head.yRot, contextModel.head.xRot);

                var attachment = capeDataComponent.backAttachment();
                var attachmentTextures = RenderResourceCache.backAttachment(attachment);
                if (DyeablePiecesME.dyeableBackAttachments.containsKey(capeDataComponent.getBackAttachment())) {
                    BackAttachmentRenderer.renderDyeableBackAttachment(
                            matrices, vertexConsumers, light, stack, capeModel, attachmentTextures.base(), true);
                    if (DyeablePiecesME.dyeableBackAttachments.get(attachment)){
                        ArmorRenderer.renderPart(
                                matrices, vertexConsumers, light, stack, capeModel, attachmentTextures.overlay());
                    }
                } else {
                    ArmorRenderer.renderPart(
                            matrices, vertexConsumers, light, stack, capeModel, attachmentTextures.base());
                }}
        }
    }
}
