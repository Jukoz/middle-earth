package net.sevenstars.middleearth.block.special.pots;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.DecoratedPotBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class LootablePotBlockEntityRenderer implements BlockEntityRenderer<DecoratedPotBlockEntity> {
    private static final float POSITIVE_WOBBLE_SCALE = 0.015625F;
    private final DecoratedPotBlockEntityRenderer vanillaRenderer;
    private final BlockRenderManager blockRenderManager;

    public LootablePotBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.vanillaRenderer = new DecoratedPotBlockEntityRenderer(context);
        this.blockRenderManager = context.getRenderManager();
    }

    @Override
    public void render(DecoratedPotBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
        BlockState state = entity.getCachedState();
        if (!(state.getBlock() instanceof LootablePotBlock)) {
            vanillaRenderer.render(entity, tickProgress, matrices, vertexConsumers, light, overlay, cameraPos);
            return;
        }

        matrices.push();
        applyWobble(entity, tickProgress, matrices);
        BlockModelRenderer.render(
                matrices.peek(),
                vertexConsumers.getBuffer(RenderLayers.getEntityBlockLayer(state)),
                blockRenderManager.getModel(state),
                1.0F,
                1.0F,
                1.0F,
                light,
                overlay
        );
        matrices.pop();
    }

    private static void applyWobble(DecoratedPotBlockEntity entity, float tickProgress, MatrixStack matrices) {
        DecoratedPotBlockEntity.WobbleType wobbleType = entity.lastWobbleType;
        if (wobbleType == null || entity.getWorld() == null) {
            return;
        }

        float progress = ((float) (entity.getWorld().getTime() - entity.lastWobbleTime) + tickProgress) / wobbleType.lengthInTicks;
        if (progress < 0.0F || progress > 1.0F) {
            return;
        }

        matrices.translate(0.5D, 0.0D, 0.5D);
        if (wobbleType == DecoratedPotBlockEntity.WobbleType.POSITIVE) {
            float angle = progress * MathHelper.TAU;
            float xRotation = -1.5F * (MathHelper.cos(angle) + 0.5F) * MathHelper.sin(angle / 2.0F);
            matrices.multiply(RotationAxis.POSITIVE_X.rotation(xRotation * POSITIVE_WOBBLE_SCALE), 0.0F, 0.0F, 0.0F);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotation(MathHelper.sin(angle) * POSITIVE_WOBBLE_SCALE), 0.0F, 0.0F, 0.0F);
        } else {
            float yaw = MathHelper.sin(-progress * 3.0F * MathHelper.PI) * 0.125F;
            float strength = 1.0F - progress;
            matrices.multiply(RotationAxis.POSITIVE_Y.rotation(yaw * strength), 0.0F, 0.0F, 0.0F);
        }
        matrices.translate(-0.5D, 0.0D, -0.5D);
    }
}
