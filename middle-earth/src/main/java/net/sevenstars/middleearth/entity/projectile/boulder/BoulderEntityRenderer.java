package net.sevenstars.middleearth.entity.projectile.boulder;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class BoulderEntityRenderer extends EntityRenderer<BoulderEntity, EntityRenderState> {
    private final BlockRenderManager blockRenderManager;
    public BoulderEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.blockRenderManager = ctx.getBlockRenderManager();
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }
    /*
    @Override
    public Identifier getTexture(EntityRenderState state) {
        return Identifier.of("minecraft", "textures/block/stone.png");
    }


    public void render(BoulderEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if(entity.getOwner() == null) {
            return;
        }
        BlockState blockState = Blocks.STONE.getDefaultState();
        if (blockState.getRenderType() != BlockRenderType.MODEL) {
            return;
        }
        World world = entity.getWorld();
        if (blockState == world.getBlockState(entity.getBlockPos()) || blockState.getRenderType() == BlockRenderType.INVISIBLE) {
            return;
        }
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch()) + 90.0F));
        BlockPos blockPos = BlockPos.ofFloored(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());
        matrices.translate(-0.5, 0.0, -0.5);

        this.blockRenderManager.getModelRenderer().render(world, this.blockRenderManager.getModel(blockState), blockState, blockPos, matrices, vertexConsumers.getBuffer(RenderLayers.getMovingBlockLayer(blockState)), false, Random.create(), blockState.getRenderingSeed(entity.getOwner().getBlockPos()), OverlayTexture.DEFAULT_UV);
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

     */
}
