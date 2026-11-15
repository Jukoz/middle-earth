package net.sevenstars.middleearth.client.renderer.armor;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.component.type.DyedColorComponent;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class CustomArmorRenderer implements ArmorRenderer {

    public CustomArmorRenderer() {
    }

    @Override
    public void render(MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, ItemStack itemStack, BipedEntityRenderState bipedEntityRenderState, EquipmentSlot equipmentSlot, int i, BipedEntityModel<BipedEntityRenderState> bipedEntityModel) {
        // keep empty
    }

    static <S extends BipedEntityRenderState> void renderArmor(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, ItemStack stack, Model<? super S> model,
                            S state, Identifier texture, boolean dyeable) {
        if(dyeable){
            renderDyeable(matrices, queue, light, stack, model, state, texture);
            if (DyeablePiecesME.dyeablePieces.getOrDefault(stack.getItem(), false)) {
                submit(matrices, queue, light, stack, model, state, RenderLayers.armorCutoutNoCull(
                        Identifier.of(MiddleEarth.MOD_ID, texture.getPath().replaceAll(".png", "_overlay.png"))), -1);
            }
        } else {
            submit(matrices, queue, light, stack, model, state, RenderLayers.armorCutoutNoCull(texture), -1);
        }
    }

    static <S extends BipedEntityRenderState> void renderDyeable(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, ItemStack stack,
                                                                 Model<? super S> model, S state, Identifier texture) {
        int color = DyedColorComponent.getColor(stack, DyedColorComponent.DEFAULT_COLOR);
        submit(matrices, queue, light, stack, model, state, RenderLayers.armorCutoutNoCull(texture), color);
    }

    static <S extends BipedEntityRenderState> void renderTranslucentPiece(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, ItemStack stack, Model<? super S> model, S state, Identifier texture) {
        submit(matrices, queue, light, stack, model, state, RenderLayers.entityTranslucent(texture), 0xFFFFFFFF);
    }

    static <S extends BipedEntityRenderState> void submit(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, ItemStack stack,
                                                                  Model<? super S> model, S state, RenderLayer layer, int tint) {
        queue.submitModel(model, state, matrices, layer, light, OverlayTexture.DEFAULT_UV, tint, null, 0, null);
        if (stack.hasGlint()) {
            queue.submitModel(model, state, matrices, RenderLayers.armorEntityGlint(), light, OverlayTexture.DEFAULT_UV,
                    -1, null, 0, null);
        }
    }
}
