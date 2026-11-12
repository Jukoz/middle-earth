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

/**
 * A shaded lantern in the off hand is carried at the side, hanging from the fist by its handle: the arm hangs down
 * and the lantern hangs straight below the fist however the arm swings. (Lit, it is carried out along the gaze.)
 */
@Environment(EnvType.CLIENT)
public final class HangingLanterns {
    /** The held item's turn on the arm, as the held item feature gives it (x -90, then y 180 degrees). */
    private static final Quaternionf ON_ARM = new Quaternionf().rotationX(-MathHelper.HALF_PI).rotateY(MathHelper.PI);
    /** The arm's turn at which a lantern's held model hangs upright: straight out before the holder. */
    private static final Quaternionf UPRIGHT = new Quaternionf().rotationX(-MathHelper.HALF_PI);

    private HangingLanterns() {
    }

    /** Whether this off hand stack is a lantern carried by its handle and shaded. */
    public static boolean hangs(ItemStack stack) {
        return stack != null && HeldLights.of(stack) instanceof HeldLight.Glow glow && glow.toggled()
            && glow.hold() == HeldLight.Hold.GAZE && !HeldLights.isOn(stack);
    }

    /**
     * Turns the held item's frame (at the fist) so the lantern hangs upright whatever the arm's turn: undoes the
     * arm's pitch, yaw and roll and turns it as for an arm held straight out, where its held model is upright.
     */
    public static void hang(MatrixStack matrices, ModelPart arm) {
        Quaternionf armTurn = new Quaternionf().rotationZYX(arm.roll, arm.yaw, arm.pitch);
        Quaternionf turn = new Quaternionf(ON_ARM).conjugate()
            .mul(armTurn.conjugate())
            .mul(UPRIGHT)
            .mul(ON_ARM);
        matrices.multiply(turn);
    }
}
