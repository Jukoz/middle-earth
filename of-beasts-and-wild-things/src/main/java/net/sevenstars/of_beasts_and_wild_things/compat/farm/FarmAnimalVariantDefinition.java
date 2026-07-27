package net.sevenstars.of_beasts_and_wild_things.compat.farm;

import java.util.List;
import net.minecraft.resources.ResourceLocation;

public record FarmAnimalVariantDefinition(
        FarmAnimalKind kind,
        ResourceLocation id,
        ResourceLocation assetId,
        FarmAnimalVariantModel model,
        List<FarmAnimalVariantSelector> selectors,
        boolean legacyVisualFallback
) {
    public FarmAnimalVariantDefinition {
        selectors = List.copyOf(selectors);
    }

    public record Synced(
            FarmAnimalKind kind,
            ResourceLocation id,
            ResourceLocation assetId,
            FarmAnimalVariantModel model,
            boolean legacyVisualFallback
    ) {
        public static Synced from(FarmAnimalVariantDefinition definition) {
            return new Synced(
                    definition.kind(),
                    definition.id(),
                    definition.assetId(),
                    definition.model(),
                    definition.legacyVisualFallback()
            );
        }
    }
}
