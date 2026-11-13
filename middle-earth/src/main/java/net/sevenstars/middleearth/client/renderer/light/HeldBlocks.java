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

/**
 * A light whose {@code held_light} entry names a {@code held_block}, drawn in the off hand as that block's placed
 * model (lit, or unlit while shaded), in place of the item's own model (a flat sprite, for most lanterns): so a data
 * pack alone gives another mod's lantern a held look. It is shown as Middle-earth's own held models are, by its
 * entry's grip and display, so its flames and first-person grip line up with it.
 */
@Environment(EnvType.CLIENT)
public final class HeldBlocks {
    /** How far below the fist a lit lantern's grip sits in third person (pixels), as Middle-earth's held lanterns. */
    private static final float LIT_DROP = 1.5F;
    /** How far a held-up light is turned on the arm (degrees on x), as Middle-earth's held models have it. */
    private static final float RAISED_ROT = 16;

    private HeldBlocks() {
    }

    /** The block state to draw for this stack in the hand, or null if its entry names no (known) block. */
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

    /**
     * Draws {@code stack}'s held block on a holder's arm (third person), the matrices as the held item feature has
     * them for the item: its grip brought to the fist, scaled, a held-up light turned on the arm. False if the
     * stack has no held block (draw the item as usual).
     */
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
            // Carried by its handle: lit, its grip a little below the fist; shaded, hanging from it.
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

    /**
     * Draws {@code stack}'s held block in your own off hand (first person), as an item's first-person display
     * would: lifted, turned and scaled about the model's middle. False if the stack has no held block.
     */
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
