package net.sevenstars.middleearth.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.dataComponents.CustomDyeableDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ModDyeablePieces;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModArmorRenderer implements ArmorRenderer {

    public ModArmorRenderer() {
    }

    static void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack stack, Model model, Identifier texture, boolean dyeable){
        if(dyeable){
            renderDyeable(matrices, vertexConsumers, light, stack, model, texture);
            if(ModDyeablePieces.dyeablePieces.get(stack.getItem())) {
                ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, Identifier.of(MiddleEarth.MOD_ID, texture.getPath().replaceAll(".png", "_overlay.png")));
            }
        } else {
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, texture);
        }
    }

    static void renderDyeable(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack stack, Model model, Identifier texture) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(texture), stack.hasGlint());
        int color = CustomDyeableDataComponent.getColor(stack, CustomDyeableDataComponent.DEFAULT_COLOR);
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, color);
    }

    static void renderTranslucentPiece(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack stack, Model model, Identifier texture) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getEntityTranslucent(texture), stack.hasGlint());
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 0xFFFFFFFF);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, BipedEntityRenderState bipedEntityRenderState, EquipmentSlot slot, int light, BipedEntityModel<BipedEntityRenderState> contextModel) {

    }
}
