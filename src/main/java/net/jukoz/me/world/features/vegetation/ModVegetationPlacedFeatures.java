package net.jukoz.me.world.features.vegetation;

import net.jukoz.me.MiddleEarth;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationConfiguredFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.List;

public class ModVegetationPlacedFeatures {
    public static final RegistryKey<PlacedFeature> PATCH_MALLOS = registerKey("patch_mallos");
    public static final RegistryKey<PlacedFeature> PATCH_TOUGH_BERRY_BUSH = registerKey("patch_tough_berry_bush");
    public static final RegistryKey<PlacedFeature> PATCH_TOUGH_BERRY_BUSH_RARE = registerKey("patch_tough_berry_bush_rare");
    public static final RegistryKey<PlacedFeature> PATCH_MIRKWOOD = registerKey("patch_mirkwood");
    public static final RegistryKey<PlacedFeature> PATCH_MORDOR_LICHEN = registerKey("patch_mordor_lichen");
    // PlacedFeatures.of
    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mallos = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MALLOS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> toughBerries = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_TOUGH_BERRY_BUSH);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mirkwood = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MIRKWOOD);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mordor_lichen = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MORDOR_LICHEN);

        PlacedFeatures.register(featureRegisterable, PATCH_MALLOS, mallos, RarityFilterPlacementModifier.of(48), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_TOUGH_BERRY_BUSH, toughBerries, RarityFilterPlacementModifier.of(48), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_TOUGH_BERRY_BUSH_RARE, toughBerries, RarityFilterPlacementModifier.of(320), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_MIRKWOOD, mirkwood, RarityFilterPlacementModifier.of(25), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_MORDOR_LICHEN, mordor_lichen, RarityFilterPlacementModifier.of(25), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(MiddleEarth.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
