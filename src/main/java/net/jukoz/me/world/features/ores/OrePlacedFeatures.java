package net.jukoz.me.world.features.ores;

import net.jukoz.me.MiddleEarth;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

public class OrePlacedFeatures {
    public static final RegistryKey<PlacedFeature> COARSE_DIRT_ORE = registerKey("coarse_dirt_ore");
    public static final RegistryKey<PlacedFeature> CORRUPTED_MOSS_DISK = registerKey("corrupted_moss_disk");
    public static final RegistryKey<PlacedFeature> DRY_DIRT_ORE = registerKey("dry_dirt_ore");
    public static final RegistryKey<PlacedFeature> FOREST_MOSS_DISK = registerKey("forest_moss_disk");
    public static final RegistryKey<PlacedFeature> GRAVEL_ORE = registerKey("gravel_ore");
    public static final RegistryKey<PlacedFeature> OLD_PODZOL_ORE = registerKey("old_podzol_ore");
    public static final RegistryKey<PlacedFeature> MUD_ORE = registerKey("mud_ore");
    public static final RegistryKey<PlacedFeature> PODZOL_ORE = registerKey("podzol_ore");
    public static final RegistryKey<PlacedFeature> LIGHT_GRAY_CONCRETE_POWDER_ORE = registerKey("light_gray_concrete_powder_ore");

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> coarseDirt = registryEntryLookup.getOrThrow(OreConfiguredFeatures.COARSE_DIRT_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> corruptedMoss = registryEntryLookup.getOrThrow(OreConfiguredFeatures.CORRUPTED_MOSS_DISK);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> dryDirt = registryEntryLookup.getOrThrow(OreConfiguredFeatures.DRY_DIRT_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> forestMoss = registryEntryLookup.getOrThrow(OreConfiguredFeatures.FOREST_MOSS_DISK);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> gravel = registryEntryLookup.getOrThrow(OreConfiguredFeatures.GRAVEL_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> oldPodzol = registryEntryLookup.getOrThrow(OreConfiguredFeatures.OLD_PODZOL_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mud = registryEntryLookup.getOrThrow(OreConfiguredFeatures.MUD_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> podzol = registryEntryLookup.getOrThrow(OreConfiguredFeatures.PODZOL_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> lightGrayConcretePowder = registryEntryLookup.getOrThrow(OreConfiguredFeatures.LIGHT_GRAY_CONCRETE_POWDER_ORE);

        PlacementModifier veryCommon = RarityFilterPlacementModifier.of(1);
        PlacementModifier common = RarityFilterPlacementModifier.of(2);
        PlacementModifier uncommon = PlacedFeatures.createCountExtraModifier(0, 0.1f, 1);

        PlacedFeatures.register(featureRegisterable, COARSE_DIRT_ORE, coarseDirt, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, CORRUPTED_MOSS_DISK, corruptedMoss, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, DRY_DIRT_ORE, dryDirt, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FOREST_MOSS_DISK, forestMoss, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, GRAVEL_ORE, gravel, uncommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, OLD_PODZOL_ORE, oldPodzol, veryCommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, MUD_ORE, mud, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PODZOL_ORE, podzol, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, LIGHT_GRAY_CONCRETE_POWDER_ORE, lightGrayConcretePowder, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(MiddleEarth.MOD_ID, name));
    }
}
