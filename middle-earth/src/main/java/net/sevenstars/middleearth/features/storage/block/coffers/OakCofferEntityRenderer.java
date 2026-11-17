package net.sevenstars.middleearth.features.storage.block.coffers;

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

public class OakCofferEntityRenderer<T extends ChestBlockEntity> extends ChestBlockEntityRenderer<T> {
    private static final SpriteIdentifier TEXTURE = new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE, MiddleEarth.idFilePath("model", "oak_coffer"));
    private static final String BODY = "body";
    private static final String LID = "lid";

    private final SpriteHolder spriteHolder;
    private final CofferModel model;

    public OakCofferEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
        ModelPart modelPart = ctx.getLayerModelPart(EntityModelLayersME.OAK_COFFER);
        this.spriteHolder = ctx.spriteHolder();
        this.model = new CofferModel(modelPart, false);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData lid = modelPartData.addChild(LID, ModelPartBuilder.create(),
                ModelTransform.origin(0.0F, 13.0F, 6.0F));

        lid.addChild("lid_r1", ModelPartBuilder.create()
                        .uv(0, 9).cuboid(-12.0F, -3.0F, -16.0F, 12.0F, 3.0F, 16.0F, new Dilation(0.0F)),
                ModelTransform.of(-8.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData body = modelPartData.addChild(BODY, ModelPartBuilder.create()
                        .uv(0, 56).cuboid(-11.0F, 0.0F, -14.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
                        .uv(26, 56).cuboid(-11.0F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
                        .uv(39, 56).cuboid(-2.0F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
                        .uv(13, 56).cuboid(-2.0F, 0.0F, -14.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
                        .uv(0, 32).cuboid(-11.0F, -7.0F, -14.0F, 12.0F, 7.0F, 14.0F, new Dilation(0.0F)),
                ModelTransform.of(-7.0F, 20.0F, 5.0F, 0.0F, -1.5708F, 0.0F));

        body.addChild("cube_r1", ModelPartBuilder.create()
                        .uv(0, -2).cuboid(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.of(-5.0F, -5.0F, -14.0F, -1.5708F, 1.2654F, -1.5708F));

        body.addChild("cube_r2", ModelPartBuilder.create()
                        .uv(0, -2).mirrored().cuboid(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 4.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(-5.0F, -5.0F, 0.0F, 1.5708F, 1.2654F, 1.5708F));

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
