package net.sevenstars.middleearth.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public final class RenderUtil {
    private RenderUtil() {
    }

    public static void renderCutoutTexture(EntityModel<?> model, MatrixStack matrices,
                                           VertexConsumerProvider vertexConsumers, Identifier texture,
                                           int light, int overlay) {
        renderModel(model, matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(texture)), light, overlay);
    }

    public static void renderEmissiveTexture(EntityModel<?> model, MatrixStack matrices,
                                             VertexConsumerProvider vertexConsumers, Identifier texture,
                                             int light, int overlay) {
        renderModel(model, matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucentEmissive(texture)), light, overlay);
    }

    public static void renderAtlasTexture(SpriteAtlasTexture atlas, EntityModel<?> model, MatrixStack matrices,
                                          VertexConsumer vertexConsumer, Identifier textureId, int light, int overlay) {
        Sprite sprite = atlas.getSprite(textureId);
        if(sprite != null) {
            VertexConsumer textureSpecificVertexConsumer = sprite.getTextureSpecificVertexConsumer(vertexConsumer);
            renderModel(model, matrices, textureSpecificVertexConsumer, light, overlay);
        }
    }

    public static void renderModel(EntityModel<?> model, MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay) {
        model.render(matrices, vertexConsumer, light, overlay);
    }
}
