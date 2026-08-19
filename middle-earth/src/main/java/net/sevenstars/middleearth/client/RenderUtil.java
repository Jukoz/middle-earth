package net.sevenstars.middleearth.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;

public final class RenderUtil {
    private RenderUtil() {
    }

    public static void renderCutoutTexture(EntityModel<?> model, PoseStack matrices,
                                           MultiBufferSource vertexConsumers, ResourceLocation texture,
                                           int light, int overlay) {
        renderModel(model, matrices, vertexConsumers.getBuffer(RenderType.entityCutoutNoCull(texture)), light, overlay);
    }

    public static void renderEmissiveTexture(EntityModel<?> model, PoseStack matrices,
                                             MultiBufferSource vertexConsumers, ResourceLocation texture,
                                             int light, int overlay) {
        renderModel(model, matrices, vertexConsumers.getBuffer(RenderType.entityTranslucentEmissive(texture)), light, overlay);
    }

    public static void renderAtlasTexture(TextureAtlas atlas, EntityModel<?> model, PoseStack matrices,
                                          VertexConsumer vertexConsumer, ResourceLocation textureId, int light, int overlay) {
        TextureAtlasSprite sprite = atlas.getSprite(textureId);
        if(sprite != null) {
            VertexConsumer textureSpecificVertexConsumer = sprite.wrap(vertexConsumer);
            renderModel(model, matrices, textureSpecificVertexConsumer, light, overlay);
        }
    }

    public static void renderModel(EntityModel<?> model, PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay) {
        model.renderToBuffer(matrices, vertexConsumer, light, overlay);
    }
}
