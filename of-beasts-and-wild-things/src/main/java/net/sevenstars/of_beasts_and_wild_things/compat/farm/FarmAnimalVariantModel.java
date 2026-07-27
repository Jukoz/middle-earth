package net.sevenstars.of_beasts_and_wild_things.compat.farm;

import java.util.Locale;

public enum FarmAnimalVariantModel {
    NORMAL,
    COLD;

    public static FarmAnimalVariantModel parse(String value) {
        return switch (value.toLowerCase(Locale.ROOT)) {
            case "normal" -> NORMAL;
            case "cold" -> COLD;
            default -> throw new IllegalArgumentException("Unknown farm animal model: " + value);
        };
    }
}
