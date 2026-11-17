package net.sevenstars.middleearth.features.storage.block.coffers;

import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.block.entity.state.ChestBlockEntityRenderState;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteHolder;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

public class SpruceCofferEntityRenderer<T extends ChestBlockEntity> extends ChestBlockEntityRenderer<T> {
    private static final SpriteIdentifier TEXTURE = new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE, MiddleEarth.idFilePath("model", "spruce_coffer"));
    private static final String BASE = "bottom";
    private static final String LID = "lid";

    private final SpriteHolder spriteHolder;
    private final CofferModel model;

    public SpruceCofferEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
        ModelPart modelPart = ctx.getLayerModelPart(EntityModelLayersME.SPRUCE_COFFER);
        this.spriteHolder = ctx.spriteHolder();
        this.model = new CofferModel(modelPart, true);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData lid = modelPartData.addChild("lid", ModelPartBuilder.create().uv(0, 45)
                .cuboid(0.0F, -3.0F, -8.0F, 16.0F, 3.0F, 16.0F,
                new Dilation(0.0F)), ModelTransform.origin(-8.0F, 16.0F, 0.0F));

        ModelPartData lock_r1 = lid.addChild("lock_r1", ModelPartBuilder.create().uv(0, 1)
                .cuboid(-2.0F, -4.0F, -1.0F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.of(16.0F, 1.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData body = modelPartData.addChild("bottom", ModelPartBuilder.create().uv(0, 38)
                        .cuboid(5.0F, -3.0F, -8.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(12, 38).cuboid(-8.0F, -3.0F, -8.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(24, 38).cuboid(-8.0F, -3.0F, 5.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(36, 38).cuboid(5.0F, -3.0F, 5.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 36).cuboid(-5.0F, -3.0F, -8.0F, 10.0F, 2.0F, 0.0F, new Dilation(0.0F))
                .uv(20, 36).cuboid(-5.0F, -3.0F, 8.0F, 10.0F, 2.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 24).cuboid(8.0F, -3.0F, -5.0F, 0.0F, 2.0F, 10.0F, new Dilation(0.0F))
                .uv(20, 24).cuboid(-8.0F, -3.0F, -5.0F, 0.0F, 2.0F, 10.0F, new Dilation(0.0F))
                .uv(0, 3).cuboid(-8.0F, -8.0F, -8.0F, 16.0F, 5.0F, 16.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(ChestBlockEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        RenderLayer renderLayer = TEXTURE.getRenderLayer(RenderLayers::entityCutout);
        Sprite sprite = spriteHolder.getSprite(TEXTURE);

        float g = state.lidAnimationProgress;
        g = 1.0F - g;
        g = 1.0F - g * g * g;

        matrices.push();
        float rotation = state.yaw;
        matrices.translate(0.5D, 1.5D, 0.5D);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
        if (rotation == 0.0F || rotation == 180.0F) {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-rotation - 90));
        } else {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-rotation + 90));
        }

        queue.submitModel(model, g, matrices, renderLayer, state.lightmapCoordinates, OverlayTexture.DEFAULT_UV, -1, sprite, 0, state.crumblingOverlay);

        matrices.pop();
    }
}
