package net.jukoz.me.world.features.misc;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.world.features.vegetation.ModVegetationConfiguredFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class MiscPlacedFeatures {

    public static final RegistryKey<PlacedFeature> LAVA_MAGMA_POOL = registerKey("lava_magma_pool");
    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> lavalMagmaPool = registryEntryLookup.getOrThrow(MiscConfiguredFeatures.LAVA_MAGMA_POOL);
        PlacedFeatures.register(featureRegisterable, LAVA_MAGMA_POOL, lavalMagmaPool, PlacedFeatures.createCountExtraModifier(0, 0.05f, 1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(MiddleEarth.MOD_ID, name));
    }
}
