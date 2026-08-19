package net.sevenstars.middleearth.block.special.fire_of_orthanc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;

public class FireOfOrthancEntityRenderer extends EntityRenderer<FireOfOrthancEntity> {
    private final BlockRenderDispatcher blockRenderManager;

    public FireOfOrthancEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.shadowRadius = 0.5F;
        this.blockRenderManager = ctx.getBlockRenderDispatcher();
    }

    @Override
    public void render(FireOfOrthancEntity fireOfOrthancEntity, float yaw, float tickDelta, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int light) {
        BlockState blockState = ModDecorativeBlocks.FIRE_OF_ORTHANC.defaultBlockState();
        if (blockState.getRenderShape() != RenderShape.MODEL) {
            return;
        }
        Level world = fireOfOrthancEntity.level();
        if (blockState == world.getBlockState(fireOfOrthancEntity.blockPosition()) || blockState.getRenderShape() == RenderShape.INVISIBLE) {
            return;
        }
        matrixStack.pushPose();
        matrixStack.mulPose(Axis.YP.rotationDegrees(-90.0F));

        float scale = ((float) fireOfOrthancEntity.tickCount / 36) + 1.0f;
        scale = Math.min(1.2f, scale);
        matrixStack.scale(scale, scale, scale);

        matrixStack.translate(-0.5, 0.0, -0.5);

        this.blockRenderManager.renderSingleBlock(blockState, matrixStack, vertexConsumerProvider, light, OverlayTexture.NO_OVERLAY);
        matrixStack.popPose();
        super.render(fireOfOrthancEntity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
    }

    @Override
    public ResourceLocation getTextureLocation(FireOfOrthancEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
