package net.sevenstars.middleearth.client.renderer.light;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.light.HeldLight;
import net.sevenstars.middleearth.light.HeldLights;
import org.joml.Quaternionf;

/** Shaded lanterns and beam lights hang straight down from the off-hand fist. */
@Environment(EnvType.CLIENT)
public final class HangingLanterns {
    private static final Quaternionf ON_ARM = new Quaternionf().rotationX(-MathHelper.HALF_PI).rotateY(MathHelper.PI);
    private static final Quaternionf UPRIGHT = new Quaternionf().rotationX(-MathHelper.HALF_PI);

    private HangingLanterns() {
    }

    public static boolean hangs(ItemStack stack) {
        return stack != null && HeldLights.of(true, stack) instanceof HeldLight light && light.toggled()
            && light.hold() == HeldLight.Hold.GAZE && !HeldLights.isOn(stack);
    }

    /** Undoes the arm's rotation so the item hangs straight down from the fist. */
    public static void hang(MatrixStack matrices, ModelPart arm) {
        Quaternionf armTurn = new Quaternionf().rotationZYX(arm.roll, arm.yaw, arm.pitch);
        Quaternionf turn = new Quaternionf(ON_ARM).conjugate()
            .mul(armTurn.conjugate())
            .mul(UPRIGHT)
            .mul(ON_ARM);
        matrices.multiply(turn);
    }
}
