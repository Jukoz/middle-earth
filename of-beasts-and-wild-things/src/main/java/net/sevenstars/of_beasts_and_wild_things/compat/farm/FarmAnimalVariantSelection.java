package net.sevenstars.of_beasts_and_wild_things.compat.farm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.IntUnaryOperator;

final class FarmAnimalVariantSelection {
    private FarmAnimalVariantSelection() {
    }

    static <T> List<T> eligible(List<Candidate<T>> candidates) {
        List<Candidate<T>> eligible = new ArrayList<>(candidates);
        eligible.sort(Comparator.comparingInt((Candidate<T> value) -> value.priority()).reversed());

        int highestPassingPriority = Integer.MIN_VALUE;
        Iterator<Candidate<T>> iterator = eligible.iterator();
        while (iterator.hasNext()) {
            Candidate<T> value = iterator.next();
            if (value.priority() < highestPassingPriority) {
                iterator.remove();
            } else if (value.condition().getAsBoolean()) {
                highestPassingPriority = value.priority();
            } else {
                iterator.remove();
            }
        }
        return eligible.stream().map(Candidate::value).toList();
    }

    static <T> T choose(List<T> values, IntUnaryOperator nextInt) {
        return values.get(nextInt.applyAsInt(values.size()));
    }

    record Candidate<T>(T value, int priority, BooleanSupplier condition) {
    }
}
