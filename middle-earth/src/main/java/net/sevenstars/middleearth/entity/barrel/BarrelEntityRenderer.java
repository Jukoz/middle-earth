package net.sevenstars.middleearth.entity.barrel;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.BoatEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import org.joml.Quaternionf;

public class BarrelEntityRenderer extends EntityRenderer<BarrelEntity, BoatEntityRenderState> {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/entities/reinforced_barrel/reinforced_barrel.png");
    private ModelPart modelPart;
    private final Model.SinglePartModel waterMaskModel;

    public BarrelEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        modelPart = context.getPart(EntityModelLayersME.REINFORCED_BARREL);
        this.waterMaskModel = new Model.SinglePartModel(context.getPart(EntityModelLayersME.REINFORCED_BARREL_WATER_MASK), (id) -> {
            return RenderLayers.waterMask();
        });
        this.shadowRadius = 0.6F;
    }

    @Override
    public BoatEntityRenderState createRenderState() {
        return new BoatEntityRenderState();
    }

    @Override
    public void render(BoatEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraRenderState) {
        matrices.push();
        matrices.scale(1.35f, 1.35f, 1.35f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - state.yaw));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0F));

        float f = state.damageWobbleTicks;
        if (f > 0.0F) {
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(MathHelper.sin(f) * f * state.damageWobbleStrength / 10.0F * (float)state.damageWobbleSide));
        }

        if (!state.submergedInWater && !MathHelper.approximatelyEquals(state.bubbleWobble, 0.0F)) {
            matrices.multiply((new Quaternionf()).setAngleAxis(state.bubbleWobble * 0.017453292F, 1.0F, 0.0F, 1.0F));
        }

        matrices.translate(0f, -1.4f, 0f);
        queue.submitModelPart(modelPart, matrices, RenderLayers.entityCutout(TEXTURE), state.light, OverlayTexture.DEFAULT_UV, null, false, false, -1, null, state.outlineColor);
        this.renderWaterMask(state, matrices, queue);
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

    protected void renderWaterMask(BoatEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue) {
        if (!state.submergedInWater) {
            queue.submitModel(this.waterMaskModel, Unit.INSTANCE, matrices, this.waterMaskModel.getLayer(TEXTURE), state.light, OverlayTexture.DEFAULT_UV, state.outlineColor, null);
        }

    }
}
