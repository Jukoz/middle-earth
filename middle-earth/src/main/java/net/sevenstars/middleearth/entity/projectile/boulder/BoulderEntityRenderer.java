package net.sevenstars.middleearth.entity.projectile.boulder;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class BoulderEntityRenderer extends EntityRenderer<BoulderEntity> {
    private static final ResourceLocation STONE_TEXTURE =
            ResourceLocation.withDefaultNamespace("textures/block/stone.png");
    private static final BlockState STONE_STATE = Blocks.STONE.defaultBlockState();

    private final BlockRenderDispatcher blockRenderManager;
    private final RandomSource random = RandomSource.create();
    private final BlockPos.MutableBlockPos renderPos = new BlockPos.MutableBlockPos();

    public BoulderEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.blockRenderManager = ctx.getBlockRenderDispatcher();
    }

    @Override
    public ResourceLocation getTextureLocation(BoulderEntity entity) {
        return STONE_TEXTURE;
    }

    @Override
    public void render(BoulderEntity entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        if(entity.getOwner() == null) {
            return;
        }
        BlockState blockState = STONE_STATE;
        if (blockState.getRenderShape() != RenderShape.MODEL) {
            return;
        }
        Level world = entity.level();
        if (blockState == world.getBlockState(entity.blockPosition()) || blockState.getRenderShape() == RenderShape.INVISIBLE) {
            return;
        }
        matrices.pushPose();
        matrices.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(tickDelta, entity.xRotO, entity.getXRot()) + 90.0F));
        this.renderPos.set(
                Mth.floor(entity.getX()),
                Mth.floor(entity.getBoundingBox().maxY),
                Mth.floor(entity.getZ()));
        matrices.translate(-0.5, 0.0, -0.5);
        this.blockRenderManager.getModelRenderer().tesselateBlock(
                world, this.blockRenderManager.getBlockModel(blockState), blockState, this.renderPos, matrices,
                vertexConsumers.getBuffer(ItemBlockRenderTypes.getMovingBlockRenderType(blockState)),
                false, this.random, blockState.getSeed(entity.getOwner().blockPosition()),
                OverlayTexture.NO_OVERLAY);
        matrices.popPose();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
