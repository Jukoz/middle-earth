package net.sevenstars.middleearth.client.renderer.block;

import net.sevenstars.middleearth.block.special.lapidarytable.LapidaryTableBlock;
import net.sevenstars.middleearth.block.special.lapidarytable.LapidaryTableBlockEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

/**
 * Turns the Lapidary Table's crank and grinding wheel while a gem is being cut. Idle, the block model
 * already shows them standing still and this draws nothing.
 * <p>
 * Speeds follow the Blockbench "spin" animation: the handle turns 360 degrees a second and the wheel, its
 * child, 720 more. Both turn about the same axis (parallel to x, through y 10.5 / z 8 in model pixels).
 */
@Environment(EnvType.CLIENT)
public class LapidaryTableRenderer implements BlockEntityRenderer<LapidaryTableBlockEntity> {
    private static final float AXIS_Y = 10.5F / 16.0F;
    private static final float AXIS_Z = 8.0F / 16.0F;
    private static final float HANDLE_DEGREES_PER_TICK = 360.0F / 20.0F;
    private static final float WHEEL_DEGREES_PER_TICK = HANDLE_DEGREES_PER_TICK + 720.0F / 20.0F;

    private final BlockRenderManager blocks;

    public LapidaryTableRenderer(BlockEntityRendererFactory.Context context) {
        this.blocks = context.getRenderManager();
    }

    @Override
    public void render(LapidaryTableBlockEntity table, float tickProgress, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
        BlockState state = table.getCachedState();
        if (!state.contains(LapidaryTableBlock.CUTTING) || !state.get(LapidaryTableBlock.CUTTING) || table.getWorld() == null) {
            return;
        }
        float ticks = (table.getWorld().getTime() % 360L) + tickProgress;
        BlockState parts = state.with(LapidaryTableBlock.CUTTING, false).with(LapidaryTableBlock.FACING, Direction.NORTH);

        matrices.push();
        // Same turn as the blockstate's "y" rotation for this facing (models are drawn facing north).
        matrices.translate(0.5F, 0.0F, 0.5F);
        matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(yRotation(state.get(LapidaryTableBlock.FACING))));
        matrices.translate(-0.5F, 0.0F, -0.5F);

        renderSpinning(parts.with(LapidaryTableBlock.PART, LapidaryTableBlock.Part.HANDLE),
            ticks * HANDLE_DEGREES_PER_TICK, matrices, vertexConsumers, light, overlay);
        renderSpinning(parts.with(LapidaryTableBlock.PART, LapidaryTableBlock.Part.WHEEL),
            ticks * WHEEL_DEGREES_PER_TICK, matrices, vertexConsumers, light, overlay);
        matrices.pop();
    }

    private void renderSpinning(BlockState part, float degrees, MatrixStack matrices,
                                VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.translate(0.0F, AXIS_Y, AXIS_Z);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(degrees));
        matrices.translate(0.0F, -AXIS_Y, -AXIS_Z);
        blocks.renderBlockAsEntity(part, matrices, vertexConsumers, light, overlay);
        matrices.pop();
    }

    private static float yRotation(Direction facing) {
        return switch (facing) {
            case EAST -> 90.0F;
            case SOUTH -> 180.0F;
            case WEST -> 270.0F;
            default -> 0.0F;
        };
    }
}
