package net.sevenstars.middleearth.world.features.misc;

import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.NetherConfiguredFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountMultilayerPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

public class ModMiscPlacedFeatures {

    public static final RegistryKey<PlacedFeature> LAVA_MAGMA_POOL = registerKey("lava_magma_pool");
    public static final RegistryKey<PlacedFeature> SMALL_BASALT_COLUMNS = registerKey("small_basalt_columns");
    public static final RegistryKey<PlacedFeature> SMALL_PUMICE_COLUMNS = registerKey("small_pumice_columns");
    public static final RegistryKey<PlacedFeature> LARGE_PUMICE_COLUMNS = registerKey("large_pumice_columns");

    static PlacementModifier uncommon = PlacedFeatures.createCountExtraModifier(1, 0.2f, 1);
    static PlacementModifier sparse = PlacedFeatures.createCountExtraModifier(0, 0.5f, 1);

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        PlacedFeatures.register(featureRegisterable, LAVA_MAGMA_POOL, registryEntryLookup.getOrThrow(ModMiscConfiguredFeatures.LAVA_MAGMA_POOL),
                PlacedFeatures.createCountExtraModifier(0, 0.05f, 1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

        PlacedFeatures.register(featureRegisterable, SMALL_BASALT_COLUMNS, registryEntryLookup.getOrThrow(NetherConfiguredFeatures.SMALL_BASALT_COLUMNS),
                CountMultilayerPlacementModifier.of(1), BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, SMALL_PUMICE_COLUMNS, registryEntryLookup.getOrThrow(ModMiscConfiguredFeatures.SMALL_PUMICE_COLUMNS),
                CountMultilayerPlacementModifier.of(2), BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, LARGE_PUMICE_COLUMNS, registryEntryLookup.getOrThrow(ModMiscConfiguredFeatures.LARGE_PUMICE_COLUMNS),
                CountMultilayerPlacementModifier.of(1), BiomePlacementModifier.of());
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MiddleEarth.MOD_ID, name));
    }
}
