package net.sevenstars.middleearth.block.special.shapingAnvil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;
import org.junit.jupiter.api.Test;

class ShapingProgressTest {
    @Test
    void coldSmithingAlwaysAdvancesExactlyOnePoint() {
        Random random = new Random(1234L);
        for (int level = 0; level <= 10; level++) {
            assertEquals(1, ShapingProgress.roll(random::nextInt, 0, level));
            assertEquals(1, ShapingProgress.roll(random::nextInt, -1, level));
        }
    }

    @Test
    void heatedSmithingRespectsBlessingProgressBounds() {
        for (int level = 0; level <= 3; level++) {
            Random random = new Random(5678L + level);
            int minimum = 7 + level;
            int maximumExclusive = 14 + 2 * level;
            for (int i = 0; i < 1_000; i++) {
                int progress = ShapingProgress.roll(random::nextInt, 100, level);
                assertTrue(progress >= minimum);
                assertTrue(progress < maximumExclusive);
            }
        }
    }
}
