package net.jukoz.me.world.features.underground;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.world.chunkgen.MiddleEarthChunkGenerator;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class CavesPlacedFeatures {
    public static final RegistryKey<PlacedFeature> AMETHYST_GEODE = registerKey("amethyst_geode");
    public static final RegistryKey<PlacedFeature> RED_AGATE_GEODE = registerKey("red_agate_geode");

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> amethystGeode = registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.AMETHYST_GEODE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> redAgateGeode = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.RED_AGATE_GEODE);

        PlacedFeatures.register(featureRegisterable, AMETHYST_GEODE, amethystGeode, RarityFilterPlacementModifier.of(32), SquarePlacementModifier.of(),
                HeightRangePlacementModifier.uniform(YOffset.aboveBottom(6), YOffset.fixed(30)), BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, RED_AGATE_GEODE, redAgateGeode, RarityFilterPlacementModifier.of(40), SquarePlacementModifier.of(),
                HeightRangePlacementModifier.uniform(YOffset.aboveBottom(-16), YOffset.fixed(16)), BiomePlacementModifier.of());

    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(MiddleEarth.MOD_ID, name));
    }

    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }

    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacementModifier.of(count), heightModifier);
    }

    private static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
        return modifiers(RarityFilterPlacementModifier.of(chance), heightModifier);
    }
}
