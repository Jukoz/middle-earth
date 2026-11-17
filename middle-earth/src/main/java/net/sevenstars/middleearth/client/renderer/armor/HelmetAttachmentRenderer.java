package net.sevenstars.middleearth.client.renderer.armor;

import org.jetbrains.annotations.Nullable;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.component.type.DyedColorComponent;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.HelmetAttachmentDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ArmorModelsME;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentModelsME;

public class HelmetAttachmentRenderer implements ArmorRenderer {

    public HelmetAttachmentRenderer() {
    }

    static <S extends BipedEntityRenderState> void renderDyeableHelmetAttachment(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, ItemStack stack,
                                                                                 Model<? super S> model, S state, @Nullable BipedEntityModel<BipedEntityRenderState> contextModel, Identifier texture, boolean helmet) {
        int color;
        if (helmet) {
            color = ColorHelper.fullAlpha(stack.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA).helmetAttachmentColor());
        } else {
            color = DyedColorComponent.getColor(stack, DyedColorComponent.DEFAULT_COLOR);
        }
        CustomArmorRenderer.submit(matrices, queue, light, stack, model, state, contextModel, RenderLayers.armorCutoutNoCull(texture), color, 0);
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, ItemStack stack, BipedEntityRenderState state, EquipmentSlot slot,
                       int light, BipedEntityModel<BipedEntityRenderState> contextModel) {
        if (slot != EquipmentSlot.HEAD) return;

        HelmetAttachmentDataComponent data = stack.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA);
        if (data == null) return;

        String name = data.helmetAttachment().getName();
        HelmetAttachmentModelsME helmetAddonModel = ArmorModelsME.CustomHelmetAttachmentPairedModels.valueOf(name.toUpperCase()).getModel();
        Identifier texture;
        HelmetAddonModel helmetAttachmentModel;
        if (data.down()) {
            texture = Identifier.of(MiddleEarth.MOD_ID, "textures/models/helmet_attachment/" + name.toLowerCase() + "_down.png");
            helmetAttachmentModel = helmetAddonModel.getUnarmoredDownModel();
        } else {
            texture = Identifier.of(MiddleEarth.MOD_ID, "textures/models/helmet_attachment/" + name.toLowerCase() + ".png");
            helmetAttachmentModel = helmetAddonModel.getUnarmoredModel();
        }

        helmetAttachmentModel.setVisible(false);
        helmetAttachmentModel.head.visible = true;
        helmetAttachmentModel.hat.visible = true;

        if (DyeablePiecesME.dyeableHelmetAttachments.containsKey(data.getHelmetAttachment())) {
            renderDyeableHelmetAttachment(matrices, queue, light, stack, helmetAttachmentModel, state, contextModel, texture, false);
            if (DyeablePiecesME.dyeableHelmetAttachments.get(data.helmetAttachment())) {
                CustomArmorRenderer.renderTranslucentPiece(matrices, queue, light, stack, helmetAttachmentModel, state, contextModel,
                        Identifier.of(MiddleEarth.MOD_ID, texture.getPath().replaceAll(".png", "_overlay.png")));
            }
        } else {
            CustomArmorRenderer.renderTranslucentPiece(matrices, queue, light, stack, helmetAttachmentModel, state, contextModel, texture);
        }
    }
}

