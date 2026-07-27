package net.sevenstars.of_beasts_and_wild_things.compat.farm;

import net.minecraft.network.syncher.EntityDataAccessor;
public final class FarmAnimalVariantData {
    private static EntityDataAccessor<String> chicken;
    private static EntityDataAccessor<String> cow;
    private static EntityDataAccessor<String> pig;

    private FarmAnimalVariantData() {
    }

    public static synchronized EntityDataAccessor<String> register(
            FarmAnimalKind kind,
            EntityDataAccessor<String> accessor
    ) {
        EntityDataAccessor<String> current = accessor(kind);
        if (current != null && current != accessor) {
            throw new IllegalStateException("Farm animal variant accessor already registered for " + kind);
        }
        switch (kind) {
            case CHICKEN -> chicken = accessor;
            case COW -> cow = accessor;
            case PIG -> pig = accessor;
        }
        return accessor;
    }

    public static EntityDataAccessor<String> accessor(FarmAnimalKind kind) {
        return switch (kind) {
            case CHICKEN -> chicken;
            case COW -> cow;
            case PIG -> pig;
        };
    }
}
