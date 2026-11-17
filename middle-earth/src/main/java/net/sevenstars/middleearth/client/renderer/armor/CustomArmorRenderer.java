package net.sevenstars.middleearth.client.renderer.armor;

import org.jetbrains.annotations.Nullable;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.component.type.DyedColorComponent;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.entity.model.BipedEntityModel;
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
                            S state, @Nullable BipedEntityModel<BipedEntityRenderState> contextModel, Identifier texture, boolean dyeable) {
        if(dyeable){
            renderDyeable(matrices, queue, light, stack, model, state, contextModel, texture);
            if (DyeablePiecesME.dyeablePieces.getOrDefault(stack.getItem(), false)) {
                submit(matrices, queue, light, stack, model, state, contextModel, RenderLayers.armorCutoutNoCull(
                        Identifier.of(MiddleEarth.MOD_ID, texture.getPath().replaceAll(".png", "_overlay.png"))), -1, 1);
            }
        } else {
            submit(matrices, queue, light, stack, model, state, contextModel, RenderLayers.armorCutoutNoCull(texture), -1, 0);
        }
    }

    static <S extends BipedEntityRenderState> void renderDyeable(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, ItemStack stack,
                                                                 Model<? super S> model, S state, @Nullable BipedEntityModel<BipedEntityRenderState> contextModel, Identifier texture) {
        int color = DyedColorComponent.getColor(stack, DyedColorComponent.DEFAULT_COLOR);
        submit(matrices, queue, light, stack, model, state, contextModel, RenderLayers.armorCutoutNoCull(texture), color, 0);
    }

    static <S extends BipedEntityRenderState> void renderTranslucentPiece(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, ItemStack stack, Model<? super S> model, S state, @Nullable BipedEntityModel<BipedEntityRenderState> contextModel, Identifier texture) {
        submit(matrices, queue, light, stack, model, state, contextModel, RenderLayers.entityTranslucent(texture), 0xFFFFFFFF, 0);
    }

    static <S extends BipedEntityRenderState> void submit(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, ItemStack stack,
                                                                  Model<? super S> model, S state, @Nullable BipedEntityModel<BipedEntityRenderState> contextModel, RenderLayer layer, int tint, int batch) {
        submitPiece(matrices, queue, light, model, state, contextModel, layer, tint, batch);
        if (stack.hasGlint()) {
            submitPiece(matrices, queue, light, model, state, contextModel, RenderLayers.armorEntityGlint(), -1, batch);
        }
    }

    // The queue poses the model when it draws it, so the wearer's pose is copied then rather than now
    private static <S extends BipedEntityRenderState> void submitPiece(MatrixStack matrices, OrderedRenderCommandQueue queue, int light,
                                                                       Model<? super S> model, S state, @Nullable BipedEntityModel<BipedEntityRenderState> contextModel, RenderLayer layer, int tint, int batch) {
        if (contextModel == null) {
            queue.getBatchingQueue(batch).submitModel(model, state, matrices, layer, light, OverlayTexture.DEFAULT_UV, tint, null, 0, null);
        } else {
            ArmorRenderer.submitTransformCopyingModel(contextModel, state, model, state, false, queue.getBatchingQueue(batch), matrices, layer, light,
                    OverlayTexture.DEFAULT_UV, tint, null, 0, null);
        }
    }
}
