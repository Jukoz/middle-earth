package net.sevenstars.middleearth.client.renderer.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.sevenstars.middleearth.client.renderer.RenderResourceCache.TexturePair;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

public class ModArmorRenderer implements ArmorRenderer {

    public ModArmorRenderer() {
    }

    static void renderArmor(PoseStack matrices, MultiBufferSource vertexConsumers, int light,
                            ItemStack stack, Model model, TexturePair textures, boolean dyeable){
        if(dyeable){
            renderDyeable(matrices, vertexConsumers, light, stack, model, textures.base());
            if(DyeablePiecesME.dyeablePieces.get(stack.getItem())) {
                ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, textures.overlay());
            }
        } else {
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, textures.base());
        }
    }

    static void renderDyeable(PoseStack matrices, MultiBufferSource vertexConsumers, int light, ItemStack stack,
                               Model model, net.minecraft.resources.ResourceLocation texture) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumers, RenderType.armorCutoutNoCull(texture), stack.hasFoil());
        int color = DyedItemColor.getOrDefault(stack, DyedItemColor.LEATHER_COLOR);
        model.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, color);
    }

    static void renderTranslucentPiece(PoseStack matrices, MultiBufferSource vertexConsumers, int light,
                                       ItemStack stack, Model model,
                                       net.minecraft.resources.ResourceLocation texture) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumers, RenderType.entityTranslucent(texture), stack.hasFoil());
        model.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {

    }
}
