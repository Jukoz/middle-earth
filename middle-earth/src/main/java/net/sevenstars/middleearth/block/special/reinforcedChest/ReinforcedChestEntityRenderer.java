package net.sevenstars.middleearth.block.special.reinforcedChest;

import net.minecraft.util.math.RotationAxis;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
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
import net.minecraft.util.Identifier;

public class ReinforcedChestEntityRenderer<T extends ChestBlockEntity> extends ChestBlockEntityRenderer<T> {

    private static final String BASE = "bottom";
    private static final String LID = "lid";
    private static final String LATCH = "lock";
    private static final SpriteIdentifier TEXTURE = new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE,
            Identifier.of(MiddleEarth.MOD_ID, "model/reinforced_chest"));
    private final SpriteHolder spriteHolder;
    private final ReinforcedChestModel model;

    public ReinforcedChestEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
        ModelPart modelPart = ctx.getLayerModelPart(EntityModelLayersME.REINFORCED_CHEST);
        this.spriteHolder = ctx.spriteHolder();
        this.model = new ReinforcedChestModel(modelPart);
    }


    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(BASE, ModelPartBuilder.create().uv(0, 18).cuboid(-8.0F, 0.0F, -6.0F, 16.0F, 10.0F, 12.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(LID, ModelPartBuilder.create().uv(0, 1).cuboid(-2.0F, -3.0F, -13.0F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 10.0F, 6.0F));
        modelPartData.addChild(LATCH, ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, 0.0F, -12.0F, 16.0F, 6.0F, 12.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 10.0F, 6.0F));
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
        matrices.translate(0.5D, 0.0D, 0.5D);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-state.yaw));

        queue.submitModel(model, g, matrices, renderLayer, state.lightmapCoordinates, OverlayTexture.DEFAULT_UV, -1, sprite, 0, state.crumblingOverlay);

        matrices.pop();
    }
}
