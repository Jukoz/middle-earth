package net.sevenstars.of_beasts_and_wild_things.compat.farm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class FarmAnimalVariantSelectionTest {
    @Test
    void keepsOnlyHighestPassingPriorityAndDoesNotEvaluateLowerPriorityConditions() {
        AtomicInteger lowerPriorityCalls = new AtomicInteger();

        List<String> eligible = FarmAnimalVariantSelection.eligible(List.of(
                new FarmAnimalVariantSelection.Candidate<>("high-false", 2, () -> false),
                new FarmAnimalVariantSelection.Candidate<>("high-first", 1, () -> true),
                new FarmAnimalVariantSelection.Candidate<>("high-second", 1, () -> true),
                new FarmAnimalVariantSelection.Candidate<>("low", 0, () -> {
                    lowerPriorityCalls.incrementAndGet();
                    return true;
                })
        ));

        assertEquals(List.of("high-first", "high-second"), eligible);
        assertEquals(0, lowerPriorityCalls.get());
    }

    @Test
    void preservesStableOrderAndDuplicateSelectorWeighting() {
        List<String> eligible = FarmAnimalVariantSelection.eligible(List.of(
                new FarmAnimalVariantSelection.Candidate<>("first", 0, () -> true),
                new FarmAnimalVariantSelection.Candidate<>("weighted", 0, () -> true),
                new FarmAnimalVariantSelection.Candidate<>("weighted", 0, () -> true),
                new FarmAnimalVariantSelection.Candidate<>("removed", 0, () -> false)
        ));

        assertEquals(List.of("first", "weighted", "weighted"), eligible);
    }

    @Test
    void alwaysPerformsOneBoundedRandomDrawEvenForSingletonPools() {
        AtomicInteger calls = new AtomicInteger();
        AtomicInteger bound = new AtomicInteger();

        String selected = FarmAnimalVariantSelection.choose(List.of("only"), size -> {
            calls.incrementAndGet();
            bound.set(size);
            return 0;
        });

        assertEquals("only", selected);
        assertEquals(1, calls.get());
        assertEquals(1, bound.get());
    }
}
