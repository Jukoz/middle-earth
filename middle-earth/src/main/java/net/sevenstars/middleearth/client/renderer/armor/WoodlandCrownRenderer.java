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
import net.sevenstars.middleearth.item.dataComponents.BiomeDataComponent;
import net.sevenstars.middleearth.item.dataComponents.HelmetAttachmentDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ArmorModelsME;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;

public class WoodlandCrownRenderer implements ArmorRenderer {

    private final CustomHelmetModel customHelmetModel = new CustomHelmetModel(CustomHelmetModel.getTexturedModelData().createModel());
    private HelmetAddonModel helmetAddonModel;

    public WoodlandCrownRenderer() {
    }

    public WoodlandCrownRenderer(HelmetAddonModel helmetModel) {
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

            BiomeDataComponent biomeDataComponent = stack.getComponents().get(DataComponentTypesME.BIOME_DATA);
            Identifier identifier = null;
            if(biomeDataComponent != null) identifier = biomeDataComponent.biomeId();

            String texture = "textures/models/armor/woodland_realm_crown";

            String id = identifier.toString();
            if(id.contains("lebennin")) {
                texture += "_spring";
            } else if(id.contains("ithilien")) {
                texture += "_summer";
            } else if(id.contains("troll")) {
                texture += "_autumn";
            } else if(id.contains("foro")) {
                texture += "_winter";
            }

            texture += ".png";

            ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, customHelmetModel, Identifier.of(MiddleEarth.MOD_ID, texture), dyeable);
            if (this.helmetAddonModel != null) {
                contextModel.copyTransforms(this.helmetAddonModel);
                this.helmetAddonModel.setVisible(false);
                this.helmetAddonModel.head.visible = true;
                ModArmorRenderer.renderArmor(matrices, vertexConsumers, light, stack, this.helmetAddonModel, Identifier.of(MiddleEarth.MOD_ID, texture.replaceAll(".png", "_addition.png")), dyeable);
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
