package net.sevenstars.of_beasts_and_wild_things.compat.farm;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.resources.ResourceLocation;

public final class FarmAnimalVariantClientState {
    private static volatile Map<FarmAnimalKind, Map<ResourceLocation, FarmAnimalVariantDefinition.Synced>> definitions =
            createDefaults();

    private FarmAnimalVariantClientState() {
    }

    public static void replace(List<FarmAnimalVariantDefinition.Synced> syncedDefinitions) {
        EnumMap<FarmAnimalKind, Map<ResourceLocation, FarmAnimalVariantDefinition.Synced>> replacement =
                new EnumMap<>(FarmAnimalKind.class);
        for (FarmAnimalKind kind : FarmAnimalKind.values()) {
            replacement.put(kind, new TreeMap<>());
        }
        for (FarmAnimalVariantDefinition.Synced definition : syncedDefinitions) {
            replacement.get(definition.kind()).put(definition.id(), definition);
        }
        replacement.replaceAll((kind, values) -> Map.copyOf(values));
        definitions = Map.copyOf(replacement);
    }

    public static FarmAnimalVariantModel model(FarmAnimalKind kind, ResourceLocation variant) {
        FarmAnimalVariantDefinition.Synced definition = definitions.getOrDefault(kind, Map.of()).get(variant);
        return definition == null ? FarmAnimalVariantModel.NORMAL : definition.model();
    }

    public static ResourceLocation texture(FarmAnimalKind kind, ResourceLocation variant) {
        FarmAnimalVariantDefinition.Synced definition = definitions.getOrDefault(kind, Map.of()).get(variant);
        if (definition == null || definition.legacyVisualFallback()) {
            return kind.defaultTexture();
        }
        ResourceLocation asset = definition.assetId();
        return ResourceLocation.fromNamespaceAndPath(asset.getNamespace(), "textures/" + asset.getPath() + ".png");
    }

    private static Map<FarmAnimalKind, Map<ResourceLocation, FarmAnimalVariantDefinition.Synced>> createDefaults() {
        EnumMap<FarmAnimalKind, Map<ResourceLocation, FarmAnimalVariantDefinition.Synced>> defaults =
                new EnumMap<>(FarmAnimalKind.class);
        ResourceLocation temperate = ResourceLocation.withDefaultNamespace("temperate");
        for (FarmAnimalKind kind : FarmAnimalKind.values()) {
            defaults.put(kind, Map.of(
                    temperate,
                    new FarmAnimalVariantDefinition.Synced(
                            kind,
                            temperate,
                            ResourceLocation.withDefaultNamespace("entity/" + kind.name().toLowerCase() + "/temperate"),
                            FarmAnimalVariantModel.NORMAL,
                            true
                    )
            ));
        }
        return Map.copyOf(defaults);
    }
}
