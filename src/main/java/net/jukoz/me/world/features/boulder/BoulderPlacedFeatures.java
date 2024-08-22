package net.jukoz.me.world.features.boulder;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.world.features.vegetation.ModVegetationConfiguredFeatures;
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
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.List;

public class BoulderPlacedFeatures {
    public static final RegistryKey<PlacedFeature> ANDESITE_BOULDER = registerKey("andesite_boulder");
    public static final RegistryKey<PlacedFeature> CALCITE_BOULDER = registerKey("calcite_boulder");
    public static final RegistryKey<PlacedFeature> DIORITE_BOULDER = registerKey("diorite_boulder");
    public static final RegistryKey<PlacedFeature> GRANITE_BOULDER = registerKey("granite_boulder");
    public static final RegistryKey<PlacedFeature> LIMESTONE_BOULDER = registerKey("limestone_boulder");
    public static final RegistryKey<PlacedFeature> MIRKWOOD_ROOTS_BOULDER = registerKey("mirkwood_roots_boulder");
    public static final RegistryKey<PlacedFeature> SANDSTONE_BOULDER = registerKey("sandstone_boulder");
    public static final RegistryKey<PlacedFeature> STONE_BOULDER = registerKey("stone_boulder");
    public static final RegistryKey<PlacedFeature> MOSSY_BOULDER = registerKey("mossy_boulder");

    public static final RegistryKey<PlacedFeature> SMALL_BOULDER_ANDESITE   = registerKey("small_boulder_andesite");
    public static final RegistryKey<PlacedFeature> MEDIUM_BOULDER_ANDESITE  = registerKey("medium_boulder_andesite");
    public static final RegistryKey<PlacedFeature> BIG_BOULDER_ANDESITE     = registerKey("big_boulder_andesite");

    public static final RegistryKey<PlacedFeature> SMALL_BOULDER_ASHEN_STONE    = registerKey("small_boulder_ashen_stone");
    public static final RegistryKey<PlacedFeature> MEDIUM_BOULDER_ASHEN_STONE   = registerKey("medium_boulder_ashen_stone");
    public static final RegistryKey<PlacedFeature> BIG_BOULDER_ASHEN_STONE      = registerKey("big_boulder_ashen_stone");

    public static final RegistryKey<PlacedFeature> SMALL_BOULDER_BASALT     = registerKey("small_boulder_basalt");
    public static final RegistryKey<PlacedFeature> MEDIUM_BOULDER_BASALT    = registerKey("medium_boulder_basalt");
    public static final RegistryKey<PlacedFeature> BIG_BOULDER_BASALT       = registerKey("big_boulder_basalt");

    public static final RegistryKey<PlacedFeature> SMALL_BOULDER_BLUE_TUFF  = registerKey("small_boulder_blue_tuff");
    public static final RegistryKey<PlacedFeature> MEDIUM_BOULDER_BLUE_TUFF = registerKey("medium_boulder_blue_tuff");
    public static final RegistryKey<PlacedFeature> BIG_BOULDER_BLUE_TUFF    = registerKey("big_boulder_blue_tuff");

    public static final RegistryKey<PlacedFeature> SMALL_BOULDER_CALCITE    = registerKey("small_boulder_calcite");
    public static final RegistryKey<PlacedFeature> MEDIUM_BOULDER_CALCITE   = registerKey("medium_boulder_calcite");
    public static final RegistryKey<PlacedFeature> BIG_BOULDER_CALCITE      = registerKey("big_boulder_calcite");

    public static final RegistryKey<PlacedFeature> SMALL_BOULDER_DIORITE    = registerKey("small_boulder_diorite");
    public static final RegistryKey<PlacedFeature> MEDIUM_BOULDER_DIORITE   = registerKey("medium_boulder_diorite");
    public static final RegistryKey<PlacedFeature> BIG_BOULDER_DIORITE      = registerKey("big_boulder_diorite");

    public static final RegistryKey<PlacedFeature> SMALL_BOULDER_DOLOMITE   = registerKey("small_boulder_dolomite");
    public static final RegistryKey<PlacedFeature> MEDIUM_BOULDER_DOLOMITE  = registerKey("medium_boulder_dolomite");
    public static final RegistryKey<PlacedFeature> BIG_BOULDER_DOLOMITE     = registerKey("big_boulder_dolomite");

    public static final RegistryKey<PlacedFeature> SMALL_BOULDER_GNEISS     = registerKey("small_boulder_gneiss");
    public static final RegistryKey<PlacedFeature> MEDIUM_BOULDER_GNEISS    = registerKey("medium_boulder_gneiss");
    public static final RegistryKey<PlacedFeature> BIG_BOULDER_GNEISS       = registerKey("big_boulder_gneiss");

    public static final RegistryKey<PlacedFeature> SMALL_BOULDER_GRANITE    = registerKey("small_boulder_granite");
    public static final RegistryKey<PlacedFeature> MEDIUM_BOULDER_GRANITE   = registerKey("medium_boulder_granite");
    public static final RegistryKey<PlacedFeature> BIG_BOULDER_GRANITE      = registerKey("big_boulder_granite");

    public static final RegistryKey<PlacedFeature> SMALL_BOULDER_HEMATITE   = registerKey("small_boulder_hematite");
    public static final RegistryKey<PlacedFeature> MEDIUM_BOULDER_HEMATITE  = registerKey("medium_boulder_hematite");
    public static final RegistryKey<PlacedFeature> BIG_BOULDER_HEMATITE     = registerKey("big_boulder_hematite");

    public static final RegistryKey<PlacedFeature> SMALL_BOULDER_IRONSTONE  = registerKey("small_boulder_ironstone");
    public static final RegistryKey<PlacedFeature> MEDIUM_BOULDER_IRONSTONE = registerKey("medium_boulder_ironstone");
    public static final RegistryKey<PlacedFeature> BIG_BOULDER_IRONSTONE    = registerKey("big_boulder_ironstone");

    public static final RegistryKey<PlacedFeature> SMALL_BOULDER_LIMESTONE  = registerKey("small_boulder_limestone");
    public static final RegistryKey<PlacedFeature> MEDIUM_BOULDER_LIMESTONE = registerKey("medium_boulder_limestone");
    public static final RegistryKey<PlacedFeature> BIG_BOULDER_LIMESTONE    = registerKey("big_boulder_limestone");

    public static final RegistryKey<PlacedFeature> SMALL_BOULDER_MOSSY_STONE    = registerKey("small_boulder_mossy_stone");
    public static final RegistryKey<PlacedFeature> MEDIUM_BOULDER_MOSSY_STONE   = registerKey("medium_boulder_mossy_stone");
    public static final RegistryKey<PlacedFeature> BIG_BOULDER_MOSSY_STONE      = registerKey("big_boulder_mossy_stone");

    public static final RegistryKey<PlacedFeature> SMALL_BOULDER_SANDSTONE  = registerKey("small_boulder_sandstone");
    public static final RegistryKey<PlacedFeature> MEDIUM_BOULDER_SANDSTONE = registerKey("medium_boulder_sandstone");
    public static final RegistryKey<PlacedFeature> BIG_BOULDER_SANDSTONE    = registerKey("big_boulder_mossy_sandstone");

    public static final RegistryKey<PlacedFeature> SMALL_BOULDER_STONE  = registerKey("small_boulder_stone");
    public static final RegistryKey<PlacedFeature> MEDIUM_BOULDER_STONE = registerKey("medium_boulder_stone");
    public static final RegistryKey<PlacedFeature> BIG_BOULDER_STONE    = registerKey("big_boulder_stone");

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> andesite = registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.ANDESITE_BOULDER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> calcite = registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.CALCITE_BOULDER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> diorite = registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.DIORITE_BOULDER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> granite = registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.GRANITE_BOULDER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> limestone = registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.LIMESTONE_BOULDER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mirkwoodRoots = registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MIRKWOOD_ROOTS_BOULDER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> sandStone = registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.STONE_BOULDER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> stone = registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.STONE_BOULDER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mossy = registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MOSSY_BOULDER);

        PlacementModifier common = PlacedFeatures.createCountExtraModifier(0, 0.5f, 1);
        PlacementModifier uncommon = PlacedFeatures.createCountExtraModifier(0, 0.25f, 1);
        PlacementModifier sparse = PlacedFeatures.createCountExtraModifier(0, 0.025f, 1);
        PlacementModifier scarce = PlacedFeatures.createCountExtraModifier(0, 0.02f, 1);
        PlacementModifier rare = PlacedFeatures.createCountExtraModifier(0, 0.01f, 1);
        PlacementModifier veryRare = PlacedFeatures.createCountExtraModifier(0, 0.005f, 1);

        PlacedFeatures.register(featureRegisterable, ANDESITE_BOULDER, andesite, uncommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, CALCITE_BOULDER, calcite, uncommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, DIORITE_BOULDER, diorite, uncommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, GRANITE_BOULDER, granite, uncommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, LIMESTONE_BOULDER, limestone, uncommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, MIRKWOOD_ROOTS_BOULDER, mirkwoodRoots, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, STONE_BOULDER, stone, uncommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, SANDSTONE_BOULDER, sandStone, uncommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, MOSSY_BOULDER, mossy, uncommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

    
        PlacedFeatures.register(featureRegisterable, SMALL_BOULDER_ANDESITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_ANDESITE), 
                sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, MEDIUM_BOULDER_ANDESITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_ANDESITE),
                rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, BIG_BOULDER_ANDESITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_ANDESITE),
                veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, SMALL_BOULDER_ASHEN_STONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_ASHEN_STONE),
                sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, MEDIUM_BOULDER_ASHEN_STONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_ASHEN_STONE),
                rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, BIG_BOULDER_ASHEN_STONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_ASHEN_STONE),
                veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, SMALL_BOULDER_BASALT, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_BASALT),
                sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, MEDIUM_BOULDER_BASALT, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_BASALT),
                rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, BIG_BOULDER_BASALT, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_BASALT),
                veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, SMALL_BOULDER_BLUE_TUFF, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_BLUE_TUFF),
                sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, MEDIUM_BOULDER_BLUE_TUFF, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_BLUE_TUFF),
                rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, BIG_BOULDER_BLUE_TUFF, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_BLUE_TUFF),
                veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, SMALL_BOULDER_CALCITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_CALCITE),
                scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, MEDIUM_BOULDER_CALCITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_CALCITE),
                rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, BIG_BOULDER_CALCITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_CALCITE),
                veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, SMALL_BOULDER_DIORITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_DIORITE),
                sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, MEDIUM_BOULDER_DIORITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_DIORITE),
                rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, BIG_BOULDER_DIORITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_DIORITE),
                veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, SMALL_BOULDER_DOLOMITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_DOLOMITE),
                sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, MEDIUM_BOULDER_DOLOMITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_DOLOMITE),
                rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, BIG_BOULDER_DOLOMITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_DOLOMITE),
                veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, SMALL_BOULDER_GNEISS, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_GNEISS),
                sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, MEDIUM_BOULDER_GNEISS, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_GNEISS),
                rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, BIG_BOULDER_GNEISS, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_GNEISS),
                veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, SMALL_BOULDER_GRANITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_GRANITE),
                sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, MEDIUM_BOULDER_GRANITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_GRANITE),
                rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, BIG_BOULDER_GRANITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_GRANITE),
                veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, SMALL_BOULDER_HEMATITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_HEMATITE),
                sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, MEDIUM_BOULDER_HEMATITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_HEMATITE),
                rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, BIG_BOULDER_HEMATITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_HEMATITE),
                veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, SMALL_BOULDER_IRONSTONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_IRONSTONE),
                sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, MEDIUM_BOULDER_IRONSTONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_IRONSTONE),
                rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, BIG_BOULDER_IRONSTONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_IRONSTONE),
                veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, SMALL_BOULDER_LIMESTONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_LIMESTONE),
                sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, MEDIUM_BOULDER_LIMESTONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_LIMESTONE),
                rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, BIG_BOULDER_LIMESTONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_LIMESTONE),
                veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, SMALL_BOULDER_MOSSY_STONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_MOSSY_STONE),
                sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, MEDIUM_BOULDER_MOSSY_STONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_MOSSY_STONE),
                rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, BIG_BOULDER_MOSSY_STONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_MOSSY_STONE),
                veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, SMALL_BOULDER_SANDSTONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_SANDSTONE),
                sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, MEDIUM_BOULDER_SANDSTONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_SANDSTONE),
                rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, BIG_BOULDER_SANDSTONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_SANDSTONE),
                veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, SMALL_BOULDER_STONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_STONE),
                sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, MEDIUM_BOULDER_STONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_STONE),
                rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, BIG_BOULDER_STONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_STONE),
                veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MiddleEarth.MOD_ID, name));
    }
}
