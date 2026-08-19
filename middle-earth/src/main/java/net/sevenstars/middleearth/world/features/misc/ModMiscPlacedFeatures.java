package net.sevenstars.middleearth.world.features.misc;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.sevenstars.middleearth.MiddleEarth;

public class ModMiscPlacedFeatures {

    public static final ResourceKey<PlacedFeature> LAVA_MAGMA_POOL = registerKey("lava_magma_pool");
    public static final ResourceKey<PlacedFeature> SMALL_BASALT_COLUMNS = registerKey("small_basalt_columns");
    public static final ResourceKey<PlacedFeature> SMALL_PUMICE_COLUMNS = registerKey("small_pumice_columns");
    public static final ResourceKey<PlacedFeature> LARGE_PUMICE_COLUMNS = registerKey("large_pumice_columns");

    static PlacementModifier uncommon = PlacementUtils.countExtra(1, 0.2f, 1);
    static PlacementModifier sparse = PlacementUtils.countExtra(0, 0.5f, 1);

    public static void bootstrap(BootstrapContext<PlacedFeature> featureRegisterable) {
        HolderGetter<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.lookup(Registries.CONFIGURED_FEATURE);

        PlacementUtils.register(featureRegisterable, LAVA_MAGMA_POOL, registryEntryLookup.getOrThrow(ModMiscConfiguredFeatures.LAVA_MAGMA_POOL),
                PlacementUtils.countExtra(0, 0.05f, 1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

        PlacementUtils.register(featureRegisterable, SMALL_BASALT_COLUMNS, registryEntryLookup.getOrThrow(NetherFeatures.SMALL_BASALT_COLUMNS),
                CountOnEveryLayerPlacement.of(1), BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, SMALL_PUMICE_COLUMNS, registryEntryLookup.getOrThrow(ModMiscConfiguredFeatures.SMALL_PUMICE_COLUMNS),
                CountOnEveryLayerPlacement.of(2), BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, LARGE_PUMICE_COLUMNS, registryEntryLookup.getOrThrow(ModMiscConfiguredFeatures.LARGE_PUMICE_COLUMNS),
                CountOnEveryLayerPlacement.of(1), BiomeFilter.biome());
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, name));
    }
}
