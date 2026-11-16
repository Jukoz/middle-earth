package net.sevenstars.middleearth.client.renderer.light;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.sevenstars.middleearth.light.HeldLight;
import net.sevenstars.middleearth.light.HeldLights;
import net.sevenstars.middleearth.resources.datas.held_lights.HeldLightData;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/** First-person off hand gripping a lantern by its handle: raised while lit, lowered out of view while shaded. */
@Environment(EnvType.CLIENT)
public final class GrippedLights {
    private static final float HOLD_X = 0.74F, HOLD_Y = -0.5F, HOLD_Z = -1.15F;
    private static final float ROLL = 45;
    private static final float HANG = 0.14F;
    private static final float LOWER = 1.3F, LOWER_MS = 300;
    private static float raised = -1;
    private static long raisedAt;
    private static Item raisedItem;
    private GrippedLights() {
    }

    public static boolean gripped(ItemStack stack) {
        return HeldLights.of(true, stack) instanceof HeldLight light && light.toggled() && light.hold() == HeldLight.Hold.GAZE;
    }

    public static void render(HeldItemRenderer renderer, EntityRenderDispatcher dispatcher, AbstractClientPlayerEntity player,
                              Arm arm, float swingProgress, float equipProgress, ItemStack stack, MatrixStack matrices,
                              VertexConsumerProvider consumers, int light) {
        boolean right = arm == Arm.RIGHT;
        float f = right ? 1 : -1;
        boolean slim = player.getSkinTextures().model() == SkinTextures.Model.SLIM;

        float up = raise(stack);
        if (up <= 0) {
            return;
        }
        up = up * up * (3 - 2 * up);

        Vector3f restFist = fist(f, slim, 0, equipProgress);
        Vector3f hold = new Vector3f(f * HOLD_X, HOLD_Y - (1 - up) * LOWER + equipProgress * -0.6F, HOLD_Z);
        Quaternionf roll = RotationAxis.POSITIVE_Z.rotationDegrees(f * ROLL);
        Vector3f fist = fist(f, slim, swingProgress, equipProgress).sub(restFist).rotate(roll).add(hold);

        if (dispatcher.getRenderer(player) instanceof PlayerEntityRenderer playerRenderer) {
            matrices.push();
            matrices.translate(hold.x, hold.y, hold.z);
            matrices.multiply(roll);
            matrices.translate(-restFist.x, -restFist.y, -restFist.z);
            armPose(matrices, f, swingProgress, equipProgress);
            if (right) {
                playerRenderer.renderRightArm(matrices, consumers, light, player.getSkinTextures().texture(),
                    player.isPartVisible(PlayerModelPart.RIGHT_SLEEVE));
            } else {
                playerRenderer.renderLeftArm(matrices, consumers, light, player.getSkinTextures().texture(),
                    player.isPartVisible(PlayerModelPart.LEFT_SLEEVE));
            }
            matrices.pop();
        }

        HeldLightData data = HeldLights.data(true, stack);
        HeldLightData.Display display = data != null ? data.display() : HeldLightData.Display.DEFAULT;
        float grip = (data != null ? data.grip() : HeldLightData.DEFAULT_GRIP).y();
        matrices.push();
        float gripUp = display.firstY() / 16 + display.firstScale() * (grip / 16 - 0.5F);
        matrices.translate(fist.x, fist.y - gripUp - HANG, fist.z);
        renderer.renderItem(player, stack, right ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
            matrices, consumers, light);
        matrices.pop();
    }

    /** Eases the arm between raised (lit) and lowered (shaded) over time. */
    private static float raise(ItemStack stack) {
        float target = HeldLights.isOn(stack) ? 1 : 0;
        long now = Util.getMeasuringTimeMs();
        if (raised < 0 || stack.getItem() != raisedItem) {
            raised = target;
        } else {
            float step = (now - raisedAt) / LOWER_MS;
            raised = raised < target ? Math.min(target, raised + step) : Math.max(target, raised - step);
        }
        raisedAt = now;
        raisedItem = stack.getItem();
        return raised;
    }

    /** Where the fist ends up under the first-person arm pose. */
    private static Vector3f fist(float f, boolean slim, float swingProgress, float equipProgress) {
        MatrixStack pose = new MatrixStack();
        armPose(pose, f, swingProgress, equipProgress);
        float fistX = -f * (slim ? 5.5F : 6.0F), fistY = slim ? 10.5F : 10.0F;
        return pose.peek().getPositionMatrix().transformPosition(fistX / 16, fistY / 16, 0, new Vector3f());
    }

    /** Vanilla's first-person arm transform, including swing and equip. */
    private static void armPose(MatrixStack matrices, float f, float swingProgress, float equipProgress) {
        float g = MathHelper.sqrt(swingProgress);
        float h = -0.3F * MathHelper.sin(g * MathHelper.PI);
        float i = 0.4F * MathHelper.sin(g * MathHelper.TAU);
        float j = -0.4F * MathHelper.sin(swingProgress * MathHelper.PI);
        matrices.translate(f * (h + 0.64000005F), i - 0.6F + equipProgress * -0.6F, j - 0.71999997F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(f * 45.0F));
        float k = MathHelper.sin(swingProgress * swingProgress * MathHelper.PI);
        float l = MathHelper.sin(g * MathHelper.PI);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(f * l * 70.0F));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(f * k * -20.0F));
        matrices.translate(f * -1.0F, 3.6F, 3.5F);
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(f * 120.0F));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(200.0F));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(f * -135.0F));
        matrices.translate(f * 5.6F, 0.0F, 0.0F);
    }
}
