package net.sevenstars.middleearth.block.special.shapingAnvil;

import java.util.function.IntUnaryOperator;

public final class ShapingProgress {
    private static final int COLD_PROGRESS = 1;
    private static final int HOT_MIN_PROGRESS = 7;
    private static final int HOT_MAX_PROGRESS_EXCLUSIVE = 14;

    private ShapingProgress() {
    }

    public static int roll(IntUnaryOperator nextInt, int temperature, int auleBlessingLevel) {
        if (temperature <= 0) {
            return COLD_PROGRESS;
        }

        int level = Math.max(0, auleBlessingLevel);
        int minimum = HOT_MIN_PROGRESS + level;
        int maximumExclusive = HOT_MAX_PROGRESS_EXCLUSIVE + 2 * level;
        return minimum + nextInt.applyAsInt(maximumExclusive - minimum);
    }
}
