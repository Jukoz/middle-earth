package net.sevenstars.middleearth.block.special.pots;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LootablePotBlockEntityRenderer implements BlockEntityRenderer<DecoratedPotBlockEntity> {
    private static final float POSITIVE_WOBBLE_SCALE = 0.015625F;
    private final DecoratedPotRenderer vanillaRenderer;
    private final BlockRenderDispatcher blockRenderManager;

    public LootablePotBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.vanillaRenderer = new DecoratedPotRenderer(context);
        this.blockRenderManager = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(DecoratedPotBlockEntity entity, float tickProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        BlockState state = entity.getBlockState();
        if (!(state.getBlock() instanceof LootablePotBlock)) {
            vanillaRenderer.render(entity, tickProgress, matrices, vertexConsumers, light, overlay);
            return;
        }

        matrices.pushPose();
        applyWobble(entity, tickProgress, matrices);
        blockRenderManager.getModelRenderer().renderModel(
                matrices.last(),
                vertexConsumers.getBuffer(ItemBlockRenderTypes.getRenderType(state, false)),
                state,
                blockRenderManager.getBlockModel(state),
                1.0F,
                1.0F,
                1.0F,
                light,
                overlay
        );
        matrices.popPose();
    }

    private static void applyWobble(DecoratedPotBlockEntity entity, float tickProgress, PoseStack matrices) {
        DecoratedPotBlockEntity.WobbleStyle wobbleType = entity.lastWobbleStyle;
        if (wobbleType == null || entity.getLevel() == null) {
            return;
        }

        float progress = ((float) (entity.getLevel().getGameTime() - entity.wobbleStartedAtTick) + tickProgress) / wobbleType.duration;
        if (progress < 0.0F || progress > 1.0F) {
            return;
        }

        matrices.translate(0.5D, 0.0D, 0.5D);
        if (wobbleType == DecoratedPotBlockEntity.WobbleStyle.POSITIVE) {
            float angle = progress * Mth.TWO_PI;
            float xRotation = -1.5F * (Mth.cos(angle) + 0.5F) * Mth.sin(angle / 2.0F);
            matrices.rotateAround(Axis.XP.rotation(xRotation * POSITIVE_WOBBLE_SCALE), 0.0F, 0.0F, 0.0F);
            matrices.rotateAround(Axis.ZP.rotation(Mth.sin(angle) * POSITIVE_WOBBLE_SCALE), 0.0F, 0.0F, 0.0F);
        } else {
            float yaw = Mth.sin(-progress * 3.0F * Mth.PI) * 0.125F;
            float strength = 1.0F - progress;
            matrices.rotateAround(Axis.YP.rotation(yaw * strength), 0.0F, 0.0F, 0.0F);
        }
        matrices.translate(-0.5D, 0.0D, -0.5D);
    }
}
