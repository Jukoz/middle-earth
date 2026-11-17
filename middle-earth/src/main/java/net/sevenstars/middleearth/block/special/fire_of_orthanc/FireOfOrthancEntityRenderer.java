package net.sevenstars.middleearth.block.special.fire_of_orthanc;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;

public class FireOfOrthancEntityRenderer extends EntityRenderer<FireOfOrthancEntity, FireOfOrthancEntityRenderState> {
    public FireOfOrthancEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.shadowRadius = 0.5F;
    }

    @Override
    public FireOfOrthancEntityRenderState createRenderState() {
        return new FireOfOrthancEntityRenderState();
    }

    @Override
    public void updateRenderState(FireOfOrthancEntity entity, FireOfOrthancEntityRenderState state, float tickProgress) {
        super.updateRenderState(entity, state, tickProgress);
        BlockState blockState = DecorativeBlockRegistryME.FIRE_OF_ORTHANC.getDefaultState();
        state.visible = blockState.getRenderType() == BlockRenderType.MODEL
                && blockState != entity.getEntityWorld().getBlockState(entity.getBlockPos());
    }

    @Override
    public void render(FireOfOrthancEntityRenderState state, MatrixStack matrixStack, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        if (!state.visible) {
            return;
        }
        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0F));

        float scale = (state.age / 36) + 1.0f;
        scale = Math.min(1.2f, scale);
        matrixStack.scale(scale, scale, scale);

        matrixStack.translate(-0.5, 0.0, -0.5);

        queue.submitBlock(matrixStack, DecorativeBlockRegistryME.FIRE_OF_ORTHANC.getDefaultState(), state.light, OverlayTexture.DEFAULT_UV, state.outlineColor);
        matrixStack.pop();
        super.render(state, matrixStack, queue, cameraState);
    }
}
