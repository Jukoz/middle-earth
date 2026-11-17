package net.sevenstars.middleearth.block.special.skull;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.WallSkullBlock;
import net.minecraft.client.model.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.SkullBlockEntityModel;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.RotationPropertyHelper;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class OldSkullBlockEntityRenderer implements BlockEntityRenderer<OldSkullBlockEntity, OldSkullBlockEntityRenderer.State> {
    private static final RenderLayer RENDER_LAYER = RenderLayers.entityCutout(Identifier.of(MiddleEarth.MOD_ID, "textures/model/old_skull.png"));
    private final ModelPart skull;

    public OldSkullBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        ModelPart modelPart = ctx.getLayerModelPart(EntityModelLayersME.OLD_SKULL);
        this.skull = modelPart.getChild("skull");
    }

    @Nullable
    public static SkullBlockEntityModel getModels(LoadedEntityModels models) {
        return new SkullEntityModel(models.getModelPart(EntityModelLayers.SKELETON_SKULL));
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData skull = modelPartData.addChild("skull", ModelPartBuilder.create().uv(0, 0)
                .cuboid(-12.0F, -8.0F, 4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.origin(8.0F, 24.0F, -8.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void updateRenderState(OldSkullBlockEntity entity, State state, float tickProgress, Vec3d cameraPos,
                                  ModelCommandRenderer.@Nullable CrumblingOverlayCommand crumblingOverlay) {
        BlockEntityRenderer.super.updateRenderState(entity, state, tickProgress, cameraPos, crumblingOverlay);
        BlockState blockState = entity.getCachedState();
        boolean wall = blockState.getBlock() instanceof WallSkullBlock;
        state.facing = wall ? blockState.get(WallSkullBlock.FACING) : null;
        int rotation = wall ? RotationPropertyHelper.fromDirection(state.facing.getOpposite()) : blockState.get(SkullBlock.ROTATION);
        state.yaw = RotationPropertyHelper.toDegrees(rotation);
    }

    @Override
    public void render(State state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        renderSkull(state.facing, state.yaw, matrices, queue, state.lightmapCoordinates, skull, RENDER_LAYER);
    }

    public static void renderSkull(@Nullable Direction direction, float yaw, MatrixStack matrices,
                                   OrderedRenderCommandQueue queue, int light, ModelPart model, RenderLayer renderLayer) {
        matrices.push();
        if (direction == null) {
            matrices.translate(0.5F, 0.0F, 0.5F);
        } else {
            matrices.translate(0.5F - direction.getOffsetX() * 0.25F, 0.25F, 0.5F - direction.getOffsetZ() * 0.25F);
        }

        matrices.translate(0, 1.5f, 0);
        matrices.scale(-1.0F, -1.0F, 1.0F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yaw));
        queue.submitModelPart(model, matrices, renderLayer, light, OverlayTexture.DEFAULT_UV, null);
        matrices.pop();
    }

    public static class State extends BlockEntityRenderState {
        public @Nullable Direction facing;
        public float yaw;
    }
}
