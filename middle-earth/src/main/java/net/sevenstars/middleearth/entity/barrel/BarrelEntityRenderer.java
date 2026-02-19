package net.sevenstars.middleearth.entity.barrel;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.BoatEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.special.ThinBarrelBlock;
import org.joml.Quaternionf;

public class BarrelEntityRenderer extends EntityRenderer<BarrelEntity, BoatEntityRenderState> {
    private final BlockRenderManager blockRenderManager;
    private BlockState blockState;

    public BarrelEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.blockState = ModDecorativeBlocks.THIN_BARREL.getDefaultState()
                .with(ThinBarrelBlock.FACING, Direction.UP).with(ThinBarrelBlock.OPEN, true);
        this.blockRenderManager = context.getBlockRenderManager();
        this.shadowRadius = 0.6F;
    }

    @Override
    public BoatEntityRenderState createRenderState() {
        return new BoatEntityRenderState();
    }

    @Override
    public void render(BoatEntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.scale(1.35f, 1.35f, 1.35f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - state.yaw));

        float f = state.damageWobbleTicks;
        if (f > 0.0F) {
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(MathHelper.sin(f) * f * state.damageWobbleStrength / 10.0F * (float)state.damageWobbleSide));
        }

        if (!state.submergedInWater && !MathHelper.approximatelyEquals(state.bubbleWobble, 0.0F)) {
            matrices.multiply((new Quaternionf()).setAngleAxis(state.bubbleWobble * 0.017453292F, 1.0F, 0.0F, 1.0F));
        }

        matrices.translate(-0.5f, -0.1f, -0.5f);
        this.blockRenderManager.renderBlockAsEntity(blockState, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV);
        matrices.pop();
    }

    public void updateRenderState(BarrelEntity barrelEntity, BoatEntityRenderState boatEntityRenderState, float f) {
        super.updateRenderState(barrelEntity, boatEntityRenderState, f);
        boatEntityRenderState.yaw = barrelEntity.getLerpedYaw(f);
        boatEntityRenderState.damageWobbleTicks = (float)barrelEntity.getDamageWobbleTicks() - f;
        boatEntityRenderState.damageWobbleSide = barrelEntity.getDamageWobbleSide();
        boatEntityRenderState.damageWobbleStrength = Math.max(barrelEntity.getDamageWobbleStrength() - f, 0.0F);
        boatEntityRenderState.bubbleWobble = barrelEntity.lerpBubbleWobble(f);
        boatEntityRenderState.submergedInWater = barrelEntity.isSubmergedInWater();
        boatEntityRenderState.leftPaddleAngle = barrelEntity.lerpPaddlePhase(0, f);
        boatEntityRenderState.rightPaddleAngle = barrelEntity.lerpPaddlePhase(1, f);
    }
}
