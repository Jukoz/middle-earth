package net.sevenstars.middleearth.client.renderer.light;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.RotationAxis;
import net.sevenstars.middleearth.light.HeldLight;
import net.sevenstars.middleearth.light.HeldLights;
import net.sevenstars.middleearth.resources.datas.held_lights.HeldLightData;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

/** Draws a held light's {@code held_block} placed model in the off hand in place of its item model. */
@Environment(EnvType.CLIENT)
public final class HeldBlocks {
    private static final float LIT_DROP = 1.5F;
    private static final float RAISED_ROT = 16;

    private HeldBlocks() {
    }

    private static @Nullable BlockState state(HeldLightData data, ItemStack stack) {
        if (data.heldBlock().isEmpty()) {
            return null;
        }
        Block block = Registries.BLOCK.getOptionalValue(data.heldBlock().get()).orElse(Blocks.AIR);
        if (block == Blocks.AIR) {
            return null;
        }
        BlockState state = block.getDefaultState();
        if (state.contains(Properties.LIT)) {
            state = state.with(Properties.LIT, !data.light().toggled() || HeldLights.isOn(stack));
        }
        if (state.contains(Properties.HANGING)) {
            state = state.with(Properties.HANGING, false);
        }
        return state;
    }

    public static boolean renderThirdPerson(ItemStack stack, MatrixStack matrices, VertexConsumerProvider consumers, int light) {
        HeldLightData data = HeldLights.data(true, stack);
        BlockState state = data == null ? null : state(data, stack);
        if (state == null) {
            return false;
        }
        HeldLightData.Display display = data.display();
        Vector3f grip = data.grip();
        matrices.push();
        if (data.light().hold() == HeldLight.Hold.GAZE) {
            boolean lit = !data.light().toggled() || HeldLights.isOn(stack);
            matrices.translate(0, lit ? -LIT_DROP / 16 : 0, 0);
        } else {
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(RAISED_ROT));
        }
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(display.turn()));
        matrices.scale(display.thirdScale(), display.thirdScale(), display.thirdScale());
        matrices.translate(-grip.x() / 16, -grip.y() / 16, -grip.z() / 16);
        render(state, matrices, consumers, light);
        matrices.pop();
        return true;
    }

    public static boolean renderFirstPerson(ItemStack stack, MatrixStack matrices, VertexConsumerProvider consumers, int light) {
        HeldLightData data = HeldLights.data(true, stack);
        BlockState state = data == null ? null : state(data, stack);
        if (state == null) {
            return false;
        }
        HeldLightData.Display display = data.display();
        matrices.push();
        matrices.translate(0, display.firstY() / 16, 0);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(display.turn()));
        matrices.scale(display.firstScale(), display.firstScale(), display.firstScale());
        matrices.translate(-0.5F, -0.5F, -0.5F);
        render(state, matrices, consumers, light);
        matrices.pop();
        return true;
    }

    private static void render(BlockState state, MatrixStack matrices, VertexConsumerProvider consumers, int light) {
        MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(state, matrices, consumers, light,
            OverlayTexture.DEFAULT_UV);
    }
}
