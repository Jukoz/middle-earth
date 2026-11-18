package net.sevenstars.middleearth.features.storage.block.coffers;

import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.render.block.entity.state.ChestBlockEntityRenderState;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.texture.SpriteHolder;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.sevenstars.middleearth.MiddleEarth;

public class CofferEntityRenderer<T extends ChestBlockEntity> extends ChestBlockEntityRenderer<T> {
    private final SpriteHolder spriteHolder;
    private final CofferModel model;
    private final SpriteIdentifier sprite;
    // The spruce coffer is modelled side-on, so its lid rolls open and it is turned a quarter.
    private final boolean sideways;

    public CofferEntityRenderer(BlockEntityRendererFactory.Context ctx, EntityModelLayer layer, String texture, boolean sideways) {
        super(ctx);
        this.spriteHolder = ctx.spriteHolder();
        this.model = new CofferModel(ctx.getLayerModelPart(layer), sideways);
        this.sprite = new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE, MiddleEarth.idFilePath("model", texture));
        this.sideways = sideways;
    }

    public static <T extends ChestBlockEntity> BlockEntityRendererFactory<T, ChestBlockEntityRenderState> of(EntityModelLayer layer, String texture, boolean sideways) {
        return ctx -> new CofferEntityRenderer<>(ctx, layer, texture, sideways);
    }

    @Override
    public void render(ChestBlockEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        RenderLayer renderLayer = sprite.getRenderLayer(RenderLayers::entityCutout);
        float openness = 1.0F - state.lidAnimationProgress;
        openness = 1.0F - openness * openness * openness;

        matrices.push();
        float rotation = state.yaw;
        matrices.translate(0.5D, 1.5D, 0.5D);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
        if (!sideways) {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotation - 180));
        } else if (rotation == 0.0F || rotation == 180.0F) {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-rotation - 90));
        } else {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-rotation + 90));
        }
        queue.submitModel(model, openness, matrices, renderLayer, state.lightmapCoordinates, OverlayTexture.DEFAULT_UV, -1,
                spriteHolder.getSprite(sprite), 0, state.crumblingOverlay);
        matrices.pop();
    }
}
