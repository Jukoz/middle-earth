package net.jukoz.me.world.features.misc;

import net.jukoz.me.MiddleEarth;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountMultilayerPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

public class ModMiscPlacedFeatures {

    public static final RegistryKey<PlacedFeature> LAVA_MAGMA_POOL = registerKey("lava_magma_pool");
    public static final RegistryKey<PlacedFeature> SMALL_BASALT_COLUMNS = registerKey("small_basalt_columns");
    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        PlacedFeatures.register(featureRegisterable, LAVA_MAGMA_POOL, registryEntryLookup.getOrThrow(ModMiscConfiguredFeatures.LAVA_MAGMA_POOL),
                PlacedFeatures.createCountExtraModifier(0, 0.05f, 1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

        PlacedFeatures.register(featureRegisterable, SMALL_BASALT_COLUMNS, registryEntryLookup.getOrThrow(NetherConfiguredFeatures.SMALL_BASALT_COLUMNS),
                CountMultilayerPlacementModifier.of(1), BiomePlacementModifier.of());
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MiddleEarth.MOD_ID, name));
    }
}
