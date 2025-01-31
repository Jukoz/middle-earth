package net.sevenstars.middleearth.block.special.fire_of_orthanc;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.ModDecorativeBlocks;

public class FireOfOrthancEntityRenderer extends EntityRenderer<FireOfOrthancEntity, FireOfOrthancEntityRenderState> {
    private final BlockRenderManager blockRenderManager;

    public FireOfOrthancEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.shadowRadius = 0.5F;
        this.blockRenderManager = ctx.getBlockRenderManager();
    }

    @Override
    public FireOfOrthancEntityRenderState createRenderState() {
        return new FireOfOrthancEntityRenderState();
    }


    public void render(FireOfOrthancEntity fireOfOrthancEntity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        BlockState blockState = ModDecorativeBlocks.FIRE_OF_ORTHANC.getDefaultState();
        if (blockState.getRenderType() != BlockRenderType.MODEL) {
            return;
        }
        World world = fireOfOrthancEntity.getWorld();
        if (blockState == world.getBlockState(fireOfOrthancEntity.getBlockPos()) || blockState.getRenderType() == BlockRenderType.INVISIBLE) {
            return;
        }
        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0F));

        float scale = ((float) fireOfOrthancEntity.age / 36) + 1.0f;
        scale = Math.min(1.2f, scale);
        matrixStack.scale(scale, scale, scale);

        BlockPos blockPos = BlockPos.ofFloored(fireOfOrthancEntity.getX(), fireOfOrthancEntity.getBoundingBox().maxY, fireOfOrthancEntity.getZ());
        matrixStack.translate(-0.5, 0.0, -0.5);

        this.blockRenderManager.getModelRenderer().render(world, this.blockRenderManager.getModel(blockState), blockState,
                blockPos, matrixStack, vertexConsumerProvider.getBuffer(RenderLayers.getMovingBlockLayer(blockState)), false,
                Random.create(), 0, OverlayTexture.DEFAULT_UV);
        matrixStack.pop();
        super.render(new FireOfOrthancEntityRenderState(), matrixStack, vertexConsumerProvider, light);
    }
}
