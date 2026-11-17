package net.sevenstars.middleearth.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public final class RenderUtil {
    private RenderUtil() {
    }

    public static <S extends EntityRenderState> void renderCutoutTexture(EntityModel<S> model, S state, MatrixStack matrices,
                                           OrderedRenderCommandQueue queue, Identifier texture,
                                           int light, int overlay) {
        renderModel(model, state, matrices, queue, RenderLayers.entityCutoutNoCull(texture), light, overlay, null);
    }

    public static <S extends EntityRenderState> void renderEmissiveTexture(EntityModel<S> model, S state, MatrixStack matrices,
                                             OrderedRenderCommandQueue queue, Identifier texture,
                                             int light, int overlay) {
        renderModel(model, state, matrices, queue, RenderLayers.entityTranslucentEmissive(texture), light, overlay, null);
    }

    public static <S extends EntityRenderState> void renderAtlasTexture(SpriteAtlasTexture atlas, EntityModel<S> model, S state, MatrixStack matrices,
                                          OrderedRenderCommandQueue queue, RenderLayer layer, Identifier textureId, int light, int overlay) {
        Sprite sprite = atlas.getSprite(textureId);
        if(sprite != null) {
            renderModel(model, state, matrices, queue, layer, light, overlay, sprite);
        }
    }

    public static <S extends EntityRenderState> void renderModel(EntityModel<S> model, S state, MatrixStack matrices, OrderedRenderCommandQueue queue,
                                   RenderLayer layer, int light, int overlay, Sprite sprite) {
        queue.submitModel(model, state, matrices, layer, light, overlay, -1, sprite, state.outlineColor, null);
    }

    public static <S extends EntityRenderState> void renderArmorTexture(Model<? super S> model, S state, MatrixStack matrices,
                                          OrderedRenderCommandQueue queue, Identifier texture, boolean glint, int light) {
        renderArmorTexture(model, state, matrices, queue, texture, glint, light, -1, 0);
    }

    public static <S extends EntityRenderState> void renderArmorTexture(Model<? super S> model, S state, MatrixStack matrices,
                                          OrderedRenderCommandQueue queue, Identifier texture, boolean glint, int light, int color) {
        renderArmorTexture(model, state, matrices, queue, texture, glint, light, color, 0);
    }

    // Opaque commands within one batch are not drawn in submission order, so a layer that must cover another goes on a later batch
    public static <S extends EntityRenderState> void renderArmorTexture(Model<? super S> model, S state, MatrixStack matrices,
                                          OrderedRenderCommandQueue queue, Identifier texture, boolean glint, int light, int color, int batch) {
        queue.getBatchingQueue(batch).submitModel(model, state, matrices, RenderLayers.armorCutoutNoCull(texture), light, OverlayTexture.DEFAULT_UV, color, null, state.outlineColor, null);
        if (glint) {
            queue.getBatchingQueue(batch).submitModel(model, state, matrices, RenderLayers.armorEntityGlint(), light, OverlayTexture.DEFAULT_UV, -1, null, 0, null);
        }
    }

    public static <S extends EntityRenderState> void renderTranslucentTexture(Model<? super S> model, S state, MatrixStack matrices,
                                                OrderedRenderCommandQueue queue, Identifier texture, int light, int overlay) {
        queue.submitModel(model, state, matrices, RenderLayers.entityTranslucent(texture), light, overlay, -1, null, state.outlineColor, null);
    }
}
