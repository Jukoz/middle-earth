package net.sevenstars.middleearth.block.special.coffers;

import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
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

public class WillowCofferEntityRenderer<T extends ChestBlockEntity> extends ChestBlockEntityRenderer<T> {
    private static final SpriteIdentifier TEXTURE = new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE, MiddleEarth.idFilePath("model", "willow_coffer"));
    private static final String BODY = "body";
    private static final String LID = "lid";

    private final SpriteHolder spriteHolder;
    private final CofferModel model;

    public WillowCofferEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
        ModelPart modelPart = ctx.getLayerModelPart(EntityModelLayersME.WILLOW_COFFER);
        this.spriteHolder = ctx.spriteHolder();
        this.model = new CofferModel(modelPart, false);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(BODY, ModelPartBuilder.create()
                        .uv(0, 36).cuboid(-1.0F, -4.0F, -1.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                        .uv(16, 36).cuboid(-1.0F, -4.0F, 7.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                        .uv(32, 36).cuboid(11.0F, -4.0F, 7.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                        .uv(0, 44).cuboid(11.0F, -4.0F, -1.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                        .uv(1, 0).cuboid(-1.0F, -13.0F, -1.0F, 16.0F, 9.0F, 12.0F, new Dilation(0.0F)),
                ModelTransform.origin(-7.0F, 24.0F, -5.0F));

        modelPartData.addChild(LID, ModelPartBuilder.create()
                        .uv(1, 21).cuboid(-8.0F, -3.0F, -12.0F, 16.0F, 3.0F, 12.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 11.0F, 6.0F));

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
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotation - 180));

        queue.submitModel(model, g, matrices, renderLayer, state.lightmapCoordinates, OverlayTexture.DEFAULT_UV, -1, sprite, 0, state.crumblingOverlay);

        matrices.pop();
    }
}
