package net.sevenstars.of_beasts_and_wild_things.compat.farm;

import java.util.Locale;

public enum FarmAnimalVariantModel {
    NORMAL,
    COLD,
    WARM;

    public static FarmAnimalVariantModel parse(FarmAnimalKind kind, String value) {
        FarmAnimalVariantModel model = switch (value.toLowerCase(Locale.ROOT)) {
            case "normal" -> NORMAL;
            case "cold" -> COLD;
            case "warm" -> WARM;
            default -> throw new IllegalArgumentException("Unknown farm animal model: " + value);
        };
        if (model == WARM && kind != FarmAnimalKind.COW) {
            throw new IllegalArgumentException("Warm model is only supported for cows");
        }
        return model;
    }
}
