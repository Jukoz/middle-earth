package net.sevenstars.middleearth.features.smithing.block.bellows;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.*;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteHolder;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.model.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class BellowsBlockEntityRenderer implements BlockEntityRenderer<BellowsBlockEntity, BellowsBlockEntityRenderState>  {
    private static final Identifier TEXTURE = MiddleEarth.id ("textures/entity/bellows.png");
    private final BellowsModel model;
    private final SpriteHolder sprites;

    public BellowsBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.model = new BellowsModel(ctx.getLayerModelPart(EntityModelLayersME.BELLOWS));
        this.sprites = ctx.spriteHolder();
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("bottom", ModelPartBuilder.create().uv(0, 12)
                .cuboid(-9.0F, -1.0F, 14.0F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(10, 12).cuboid(-9.0F, -3.0F, 1.0F, 2.0F, 2.0F, 2.0F,
                        new Dilation(0.0F))
                .uv(1, 1).mirrored().cuboid(-13.0F, -1.0F, 4.0F, 10.0F, 1.0F, 10.0F,
                        new Dilation(0.0F)).mirrored(false)
                .uv(1, 1).mirrored().cuboid(-13.0F, -3.0F, 4.0F, 10.0F, 1.0F, 10.0F,
                        new Dilation(0.0F)).mirrored(false)
                .uv(0, 17).cuboid(-10.0F, -4.0F, 3.0F, 4.0F, 4.0F, 3.0F,
                        new Dilation(0.0F)), ModelTransform.origin(8.0F, 24.0F, -9.0F));

        modelPartData.addChild("top", ModelPartBuilder.create().uv(0, 0)
                .cuboid(-5.0F, 0.0F, 0.0F, 10.0F, 1.0F, 11.0F, new Dilation(0.0F))
                .uv(18, 12).cuboid(-1.0F, 0.0F, 11.0F, 2.0F, 1.0F, 4.0F,
                        new Dilation(0.0F)), ModelTransform.of(0.0F, 19.0F, -5.0F, 0.3316F, 0.0F, 0.0F));

        modelPartData.addChild("cavity", ModelPartBuilder.create().uv(10, 17).mirrored()
                .cuboid(-4.0F, -7.0F, -4.0F, 8.0F, 6.0F, 8.0F,
                        new Dilation(0.0F)).mirrored(false), ModelTransform.origin(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(modelData, 48, 48);
    }

    @Override
    public void render(BellowsBlockEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        float rotation = state.blockState.get(ChestBlock.FACING).getPositiveHorizontalDegrees();

        SpriteIdentifier spriteId = getSpriteIdentifier(state.animationProgress);
        Sprite sprite = this.sprites.getSprite(spriteId);
        RenderLayer layer = spriteId.getRenderLayer(RenderLayers::entityCutout);

        matrices.push();
        matrices.translate(0.5D, 1.5D, 0.5D);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-rotation));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));

        queue.submitModel(this.model, state, matrices, layer, state.lightmapCoordinates,
                OverlayTexture.DEFAULT_UV, -1, sprite, 0, state.crumblingOverlay);
        matrices.pop();
    }

    private SpriteIdentifier getSpriteIdentifier(float animationProgress){
        int spriteState = (int) Math.max(0, Math.min(2, animationProgress * 7.5f));
        return new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE,
                Identifier.of(MiddleEarth.MOD_ID, "model/bellows/bellows_" + spriteState));
    }

    @Override
    public BellowsBlockEntityRenderState createRenderState() {
        return new BellowsBlockEntityRenderState();
    }

    @Override
    public void updateRenderState(BellowsBlockEntity blockEntity, BellowsBlockEntityRenderState state, float tickProgress, Vec3d cameraPos, ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay) {
        state.pumping = blockEntity.pumping;
        float animationProgress = 0;
        if (blockEntity.pumping) {
            animationProgress = blockEntity.animationProgress;
            if (animationProgress > (float) BellowsBlockEntity.MAX_TICKS / 2) animationProgress = BellowsBlockEntity.MAX_TICKS - animationProgress;
            animationProgress /= BellowsBlockEntity.MAX_TICKS;
        }
        state.animationProgress = animationProgress;
        BlockEntityRenderer.super.updateRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
    }
}
