package net.sevenstars.middleearth.block.special.pots;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.DecoratedPotBlockEntityRenderer;
import net.minecraft.client.render.block.entity.state.DecoratedPotBlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class LootablePotBlockEntityRenderer implements BlockEntityRenderer<DecoratedPotBlockEntity, DecoratedPotBlockEntityRenderState> {
    private static final float POSITIVE_WOBBLE_SCALE = 0.015625F;
    private final DecoratedPotBlockEntityRenderer vanillaRenderer;

    public LootablePotBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.vanillaRenderer = new DecoratedPotBlockEntityRenderer(context);
    }

    @Override
    public DecoratedPotBlockEntityRenderState createRenderState() {
        return new DecoratedPotBlockEntityRenderState();
    }

    @Override
    public void updateRenderState(DecoratedPotBlockEntity entity, DecoratedPotBlockEntityRenderState state, float tickProgress,
                                  Vec3d cameraPos, ModelCommandRenderer.@Nullable CrumblingOverlayCommand crumblingOverlay) {
        vanillaRenderer.updateRenderState(entity, state, tickProgress, cameraPos, crumblingOverlay);
        // The vanilla update does not copy the wobble type, but the render reads it
        state.wobbleType = entity.lastWobbleType;
    }

    @Override
    public void render(DecoratedPotBlockEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        if (!(state.blockState.getBlock() instanceof LootablePotBlock)) {
            vanillaRenderer.render(state, matrices, queue, cameraState);
            return;
        }

        matrices.push();
        applyWobble(state, matrices);
        queue.submitBlock(matrices, state.blockState, state.lightmapCoordinates, OverlayTexture.DEFAULT_UV, 0);
        matrices.pop();
    }

    private static void applyWobble(DecoratedPotBlockEntityRenderState state, MatrixStack matrices) {
        float progress = state.wobbleAnimationProgress;
        if (state.wobbleType == null || progress < 0.0F || progress > 1.0F) {
            return;
        }

        if (state.wobbleType == DecoratedPotBlockEntity.WobbleType.POSITIVE) {
            float angle = progress * MathHelper.TAU;
            float xRotation = -1.5F * (MathHelper.cos(angle) + 0.5F) * MathHelper.sin(angle / 2.0F);
            matrices.multiply(RotationAxis.POSITIVE_X.rotation(xRotation * POSITIVE_WOBBLE_SCALE), 0.5F, 0.0F, 0.5F);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotation(MathHelper.sin(angle) * POSITIVE_WOBBLE_SCALE), 0.5F, 0.0F, 0.5F);
        } else {
            float yaw = MathHelper.sin(-progress * 3.0F * MathHelper.PI) * 0.125F;
            float strength = 1.0F - progress;
            matrices.multiply(RotationAxis.POSITIVE_Y.rotation(yaw * strength), 0.5F, 0.0F, 0.5F);
        }
    }
}
