package net.sevenstars.middleearth.client.renderer.armor;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.model.equipment.CustomHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.ArmorVariantDataComponent;
import net.sevenstars.middleearth.item.dataComponents.HelmetAttachmentDataComponent;
import net.sevenstars.middleearth.item.dataComponents.SeasonDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ArmorModelsME;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;

public class HelmetVariantsRenderer implements ArmorRenderer {
    private final CustomHelmetModel customHelmetModel = new CustomHelmetModel(CustomHelmetModel.getTexturedModelData().createModel());
    private HelmetAddonModel helmetAddonModel;

    public HelmetVariantsRenderer(HelmetAddonModel helmetModel) {
        this.helmetAddonModel = helmetModel;
    }


    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, BipedEntityRenderState bipedEntityRenderState, EquipmentSlot slot, int light, BipedEntityModel<BipedEntityRenderState> contextModel) {
        boolean dyeable = false;

        if (slot == EquipmentSlot.HEAD) {
            contextModel.copyTransforms(customHelmetModel);
            customHelmetModel.setVisible(false);
            customHelmetModel.head.visible = true;
            customHelmetModel.hat.visible = true;
            customHelmetModel.body.visible = true;
            customHelmetModel.leftArm.visible = true;
            customHelmetModel.rightArm.visible = true;

            if(stack.isIn(ItemTags.DYEABLE)) {
                dyeable = true;
            }

            ArmorVariantDataComponent armorVariantDataComponent = stack.getComponents().get(DataComponentTypesME.ARMOR_VARIANT_DATA);
            int variant = 0;
            if(armorVariantDataComponent != null) variant = armorVariantDataComponent.id();
            String texture = "textures/models/armor/" + Registries.ITEM.getId(stack.getItem()).getPath() + ".png";

            ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, customHelmetModel, Identifier.of(MiddleEarth.MOD_ID, texture), dyeable);
            if (this.helmetAddonModel != null) {
                contextModel.copyTransforms(this.helmetAddonModel);
                this.helmetAddonModel.setVisible(false);
                this.helmetAddonModel.head.visible = true;

                if(texture.contains("_helmet.png")){
                    if(variant > 0) {
                        String newTex = texture.replaceAll("_helmet.png", "_addition_" + variant + ".png");
                        ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, this.helmetAddonModel,
                                Identifier.of(MiddleEarth.MOD_ID, newTex), dyeable);
                    } else {
                        ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, this.helmetAddonModel,
                                Identifier.of(MiddleEarth.MOD_ID, texture.replaceAll("_helmet.png", "_addition.png")), dyeable);
                    }
                } else {
                    ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, this.helmetAddonModel, Identifier.of(MiddleEarth.MOD_ID, texture.replaceAll(".png", "_addition.png")), dyeable);
                }
            }

            HelmetAttachmentDataComponent hoodDataComponent = stack.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA);

            if(hoodDataComponent != null) {
                Identifier textureHelmetAttachment;
                HelmetAddonModel helmetAttachmentModel;
                if (hoodDataComponent.down()){
                    textureHelmetAttachment = Identifier.of(MiddleEarth.MOD_ID, "textures/models/helmet_attachment/" + hoodDataComponent.helmetAttachment().getName().toLowerCase() + "_down.png");
                    helmetAttachmentModel = ArmorModelsME.ModHelmetAttachmentPairedModels.valueOf(hoodDataComponent.helmetAttachment().getName().toUpperCase()).getModel().getArmoredDownModel();
                } else {
                    textureHelmetAttachment = Identifier.of(MiddleEarth.MOD_ID, "textures/models/helmet_attachment/" + hoodDataComponent.helmetAttachment().getName().toLowerCase() + ".png");
                    helmetAttachmentModel = ArmorModelsME.ModHelmetAttachmentPairedModels.valueOf(hoodDataComponent.helmetAttachment().getName().toUpperCase()).getModel().getArmoredModel();
                }
                contextModel.copyTransforms(helmetAttachmentModel);
                helmetAttachmentModel.setVisible(false);
                helmetAttachmentModel.head.visible = true;
                helmetAttachmentModel.hat.visible = true;
                if (DyeablePiecesME.dyeableHelmetAttachments.containsKey(hoodDataComponent.getHelmetAttachment())) {
                    HelmetAttachmentRenderer.renderDyeableHelmetAttachment(matrices, vertexConsumers, light, stack, helmetAttachmentModel, textureHelmetAttachment, true);
                    if (DyeablePiecesME.dyeableHelmetAttachments.get(hoodDataComponent.helmetAttachment())){
                        ModArmorRenderer.renderTranslucentPiece(matrices, vertexConsumers, light, stack, helmetAttachmentModel, Identifier.of(MiddleEarth.MOD_ID, textureHelmetAttachment.getPath().replaceAll(".png", "_overlay.png")));
                    }
                } else {
                    ModArmorRenderer.renderTranslucentPiece(matrices, vertexConsumers, light, stack, helmetAttachmentModel, textureHelmetAttachment);
                }
            }
        }
    }
}
