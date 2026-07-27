package net.sevenstars.of_beasts_and_wild_things.compat.farm;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.resources.ResourceLocation;

public final class FarmAnimalVariantClientState {
    private static volatile Map<FarmAnimalKind, Map<ResourceLocation, VariantVisual>> definitions =
            createDefaults();

    private FarmAnimalVariantClientState() {
    }

    public static void replace(List<FarmAnimalVariantDefinition.Synced> syncedDefinitions) {
        EnumMap<FarmAnimalKind, Map<ResourceLocation, VariantVisual>> replacement =
                new EnumMap<>(FarmAnimalKind.class);
        for (FarmAnimalKind kind : FarmAnimalKind.values()) {
            replacement.put(kind, new TreeMap<>());
        }
        for (FarmAnimalVariantDefinition.Synced definition : syncedDefinitions) {
            replacement.get(definition.kind()).put(definition.id(), VariantVisual.from(definition));
        }
        replacement.replaceAll((kind, values) -> Map.copyOf(values));
        definitions = Map.copyOf(replacement);
    }

    public static VisualModel visualModel(FarmAnimalKind kind, ResourceLocation variant) {
        VariantVisual definition = definitions.getOrDefault(kind, Map.of()).get(variant);
        return definition == null ? VisualModel.LEGACY : definition.model();
    }

    public static ResourceLocation texture(FarmAnimalKind kind, ResourceLocation variant) {
        VariantVisual definition = definitions.getOrDefault(kind, Map.of()).get(variant);
        return definition == null ? kind.defaultTexture() : definition.texture();
    }

    private static Map<FarmAnimalKind, Map<ResourceLocation, VariantVisual>> createDefaults() {
        EnumMap<FarmAnimalKind, Map<ResourceLocation, VariantVisual>> defaults =
                new EnumMap<>(FarmAnimalKind.class);
        ResourceLocation temperate = ResourceLocation.withDefaultNamespace("temperate");
        for (FarmAnimalKind kind : FarmAnimalKind.values()) {
            defaults.put(kind, Map.of(
                    temperate,
                    new VariantVisual(VisualModel.LEGACY, kind.defaultTexture())
            ));
        }
        return Map.copyOf(defaults);
    }

    public enum VisualModel {
        LEGACY,
        NORMAL,
        COLD,
        WARM
    }

    private record VariantVisual(VisualModel model, ResourceLocation texture) {
        private static VariantVisual from(FarmAnimalVariantDefinition.Synced definition) {
            if (definition.legacyVisualFallback()) {
                return new VariantVisual(VisualModel.LEGACY, definition.kind().defaultTexture());
            }
            VisualModel visualModel = switch (definition.model()) {
                case NORMAL -> VisualModel.NORMAL;
                case COLD -> VisualModel.COLD;
                case WARM -> VisualModel.WARM;
            };
            ResourceLocation asset = definition.assetId();
            ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(
                    asset.getNamespace(),
                    "textures/" + asset.getPath() + ".png"
            );
            return new VariantVisual(visualModel, texture);
        }
    }
}
