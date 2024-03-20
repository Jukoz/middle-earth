package net.jukoz.me.world.features.vegetation;

import net.jukoz.me.MiddleEarth;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationConfiguredFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModVegetationPlacedFeatures {
    public static final RegistryKey<PlacedFeature> WATER_DELTA = registerKey("water_delta");
    public static final RegistryKey<PlacedFeature> FLOWER_MEADOW = registerKey("flower_meadow");
    public static final RegistryKey<PlacedFeature> FLOWER_GREEN_JEWEL = registerKey("green_jewel_flower");

    // region FOLIAGE
    public static final RegistryKey<PlacedFeature> PATCH_BASALT = registerKey("patch_basalt");
    public static final RegistryKey<PlacedFeature> PATCH_BLACKSTONE = registerKey("patch_blackstone");
    public static final RegistryKey<PlacedFeature> PATCH_BROWN_GRASS = registerKey("patch_brown_grass");
    public static final RegistryKey<PlacedFeature> PATCH_CORRUPTED_MOSS = registerKey("patch_corrupted_moss");
    public static final RegistryKey<PlacedFeature> PATCH_CORRUPTED_MOSS_CARPET = registerKey("patch_corrupted_moss_carpet");
    public static final RegistryKey<PlacedFeature> PATCH_DYING_GRASS = registerKey("patch_dying_grass");
    public static final RegistryKey<PlacedFeature> PATCH_DRY_GRASS = registerKey("patch_dry_grass");
    public static final RegistryKey<PlacedFeature> PATCH_FOREST_MOSS = registerKey("patch_forest_moss");
    public static final RegistryKey<PlacedFeature> PATCH_FOREST_MOSS_CARPET = registerKey("patch_forest_moss_carpet");
    public static final RegistryKey<PlacedFeature> PATCH_RARE_FOREST_MOSS = registerKey("patch_rare_forest_moss");
    public static final RegistryKey<PlacedFeature> PATCH_GREEN_SHRUB = registerKey("patch_green_shrub");
    public static final RegistryKey<PlacedFeature> PATCH_HEATHER = registerKey("patch_heather");
    public static final RegistryKey<PlacedFeature> PATCH_RARE_HEATHER = registerKey("patch_rare_heather");
    public static final RegistryKey<PlacedFeature> PATCH_HEATHER_BUSH = registerKey("patch_heather_bush");
    public static final RegistryKey<PlacedFeature> PATCH_RED_HEATHER = registerKey("patch_red_heather");
    public static final RegistryKey<PlacedFeature> PATCH_MALLOS = registerKey("patch_mallos");
    public static final RegistryKey<PlacedFeature> PATCH_STRAWBERRY_BUSH = registerKey("patch_strawberry_bush");
    public static final RegistryKey<PlacedFeature> PATCH_TOUGH_BERRY_BUSH = registerKey("patch_tough_berry_bush");
    public static final RegistryKey<PlacedFeature> PATCH_COMMON_TOUGH_BERRY_BUSH = registerKey("patch_common_tough_berry_bush");
    public static final RegistryKey<PlacedFeature> PATCH_TOUGH_BERRY_BUSH_RARE = registerKey("patch_tough_berry_bush_rare");
    public static final RegistryKey<PlacedFeature> PATCH_TUFT_GRASS = registerKey("patch_tuft_grass");
    public static final RegistryKey<PlacedFeature> PATCH_MIRKWOOD = registerKey("patch_mirkwood");
    public static final RegistryKey<PlacedFeature> PATCH_MIRKWOOD_ROOTS = registerKey("patch_mirkwood_roots");
    public static final RegistryKey<PlacedFeature> PATCH_MORDOR_LICHEN = registerKey("patch_mordor_lichen");
    public static final RegistryKey<PlacedFeature> PATCH_REEDS = registerKey("patch_reeds");
    public static final RegistryKey<PlacedFeature> PATCH_TAN_SHRUB = registerKey("patch_tan_shrub");;
    public static final RegistryKey<PlacedFeature> PATCH_WHEAT_GRASS = registerKey("patch_wheat_grass");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_GRASS = registerKey("patch_wild_grass");
    public static final RegistryKey<PlacedFeature> PATCH_WILDER_GRASS = registerKey("patch_wilder_grass");
    public static final RegistryKey<PlacedFeature> PATCH_YELLOW_FLOWER = registerKey("patch_yellow_flower");
    // endregion

    // region MUSHROOMS
    public static final RegistryKey<PlacedFeature> PATCH_BROWN_BOLETE = registerKey("patch_brown_bolete");
    public static final RegistryKey<PlacedFeature> PATCH_BROWN_BOLETE_TILLER = registerKey("patch_brown_bolete_tiller");
    public static final RegistryKey<PlacedFeature> PATCH_MORSEL = registerKey("patch_morsel");
    public static final RegistryKey<PlacedFeature> PATCH_MORSEL_TILLER = registerKey("patch_morsel_tiller");
    public static final RegistryKey<PlacedFeature> PATCH_WHITE_MUSHROOM = registerKey("patch_white_mushroom");
    public static final RegistryKey<PlacedFeature> PATCH_WHITE_MUSHROOM_TILLER = registerKey("patch_white_mushroom_tiller");
    public static final RegistryKey<PlacedFeature> PATCH_RARE_MORSEL = registerKey("patch_rare_morsel");
    public static final RegistryKey<PlacedFeature> PATCH_RARE_MORSEL_TILLER = registerKey("patch_rare_morsel_tiller");
    public static final RegistryKey<PlacedFeature> PATCH_RARE_WHITE_MUSHROOM = registerKey("patch_rare_white_mushroom");
    public static final RegistryKey<PlacedFeature> PATCH_RARE_WHITE_MUSHROOM_TILLER = registerKey("patch_rare_white_mushroom_tiller");
    // endregion

    // region WILD CROPS
    public static final RegistryKey<PlacedFeature> PATCH_WILD_BEETROOT = registerKey("patch_wild_beetroot");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_BELL_PEPPER = registerKey("patch_wild_bell_pepper");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_CARROT = registerKey("patch_wild_carrot");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_CUCUMBER = registerKey("patch_wild_cucumber");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_FLAX = registerKey("patch_wild_flax");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_GARLIC = registerKey("patch_wild_garlic");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_LEEK = registerKey("patch_wild_leek");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_LETTUCE = registerKey("patch_wild_lettuce");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_ONION = registerKey("patch_wild_onion");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_PIPEWEED = registerKey("patch_wild_pipeweed");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_POTATO = registerKey("patch_wild_potato");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_TOMATO = registerKey("patch_wild_tomato");
    // endregion

    static PlacementModifier common = PlacedFeatures.createCountExtraModifier(2, 0.5f, 1);
    static PlacementModifier uncommon = PlacedFeatures.createCountExtraModifier(1, 0.2f, 1);
    static PlacementModifier sparse = PlacedFeatures.createCountExtraModifier(0, 0.5f, 1);
    static PlacementModifier scarce = PlacedFeatures.createCountExtraModifier(0, 0.2f, 1);
    static PlacementModifier rare = PlacedFeatures.createCountExtraModifier(0, 0.1f, 1);
    static PlacementModifier veryRare = PlacedFeatures.createCountExtraModifier(0, 0.05f, 1);
    static PlacementModifier wildBushRarity = PlacedFeatures.createCountExtraModifier(0, 0.01f, 1);

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> waterDelta = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.WATER_DELTA);

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> flowerGreenJewel = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_GREEN_JEWEL);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> flowerMeadow = registryEntryLookup.getOrThrow(VegetationConfiguredFeatures.FLOWER_MEADOW);

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> basalt = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_BASALT);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> blackStone = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_BLACKSTONE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> brownGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_BROWN_GRASS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> corruptedMoss = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_CORRUPTED_MOSS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> corruptedMossCarpet = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_CORRUPTED_MOSS_CARPET);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> dyingGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_DYING_GRASS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> dryGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_DRY_GRASS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> forestMoss = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_FOREST_MOSS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> forestMossCarpet = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_FOREST_MOSS_CARPET);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> greenShrub = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_GREEN_SHRUB);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> heather = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_HEATHER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> heatherBush = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_HEATHER_BUSH);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> redHeather = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_HEATHER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mallos = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_MALLOS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mirkwoodRoots = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MIRKWOOD_ROOTS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mirkwood = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MIRKWOOD);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mordorLichen = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MORDOR_LICHEN);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> reeds = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_REEDS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> strawBerries = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_STRAWBERRY_BUSH);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> tanShrub = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_TAN_SHRUB);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> toughBerries = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_TOUGH_BERRY_BUSH);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> tuftGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_TUFT_GRASS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wheatGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WHEAT_GRASS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_GRASS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wilderGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILDER_GRASS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> yellowFlower = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_YELLOW_FLOWER);

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> bolete = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_BROWN_BOLETE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> boleteTiller = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_BROWN_BOLETE_TILLER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> morsel = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MORSEL);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> morselTiller = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MORSEL_TILLER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> whiteMushroom = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WHITE_MUSHROOM);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> whiteMushroomTiller = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WHITE_MUSHROOM_TILLER);

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildBeetroot = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_BEETROOT);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildBellPepper = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_BELL_PEPPER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildCarrot = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_CARROT);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildCucumber = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_CUCUMBER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildFlax = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_FLAX);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildGarlic = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_GARLIC);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildLeek = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_LEEK);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildLettuce = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_LETTUCE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildOnion = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_ONION);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildPipeweed = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_PIPEWEED);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildPotato = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_POTATO);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> wildTomato = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_TOMATO);

        PlacedFeatures.register(featureRegisterable, WATER_DELTA, waterDelta, common, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWER_MEADOW, flowerMeadow, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWER_GREEN_JEWEL, flowerGreenJewel, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

        PlacedFeatures.register(featureRegisterable, PATCH_BASALT, basalt, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_BLACKSTONE, blackStone, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_BROWN_GRASS, brownGrass, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_CORRUPTED_MOSS_CARPET, corruptedMossCarpet, uncommon, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_CORRUPTED_MOSS, corruptedMoss, CountPlacementModifier.of(UniformIntProvider.create(15, 25)), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_DYING_GRASS, dyingGrass, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_DRY_GRASS, dryGrass, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_FOREST_MOSS, forestMoss, CountPlacementModifier.of(UniformIntProvider.create(15, 30)), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_FOREST_MOSS_CARPET, forestMossCarpet, uncommon, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_GREEN_SHRUB, greenShrub, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_RARE_FOREST_MOSS, forestMoss, CountPlacementModifier.of(UniformIntProvider.create(5, 10)), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_HEATHER, heather, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_RARE_HEATHER, heather, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_HEATHER_BUSH, heatherBush, veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_RED_HEATHER, redHeather, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_MALLOS, mallos, RarityFilterPlacementModifier.of(48), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_MIRKWOOD, mirkwood, RarityFilterPlacementModifier.of(25), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_MIRKWOOD_ROOTS, mirkwoodRoots, sparse, CountPlacementModifier.of(UniformIntProvider.create(0, 2)));
        PlacedFeatures.register(featureRegisterable, PATCH_MORDOR_LICHEN, mordorLichen, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_STRAWBERRY_BUSH, strawBerries, veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_TAN_SHRUB, tanShrub, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_TOUGH_BERRY_BUSH, toughBerries, RarityFilterPlacementModifier.of(48), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_COMMON_TOUGH_BERRY_BUSH, toughBerries, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_TOUGH_BERRY_BUSH_RARE, tuftGrass, RarityFilterPlacementModifier.of(320), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_TUFT_GRASS, tuftGrass, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_REEDS, reeds, CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WHEAT_GRASS, wheatGrass, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_GRASS, wildGrass, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILDER_GRASS, wilderGrass, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_YELLOW_FLOWER, yellowFlower, veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

        PlacedFeatures.register(featureRegisterable, PATCH_BROWN_BOLETE, bolete, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_BROWN_BOLETE_TILLER, boleteTiller, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_MORSEL, morsel, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_MORSEL_TILLER, morselTiller, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WHITE_MUSHROOM, whiteMushroom, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WHITE_MUSHROOM_TILLER, whiteMushroomTiller, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_RARE_MORSEL, morsel, veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_RARE_MORSEL_TILLER, morselTiller, veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_RARE_WHITE_MUSHROOM, whiteMushroom, veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_RARE_WHITE_MUSHROOM_TILLER, whiteMushroomTiller, veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

        PlacedFeatures.register(featureRegisterable, PATCH_WILD_BEETROOT, wildBeetroot, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_BELL_PEPPER, wildBellPepper, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_CARROT, wildCarrot, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_CUCUMBER, wildCucumber, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_FLAX, wildFlax, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_GARLIC, wildGarlic, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_LEEK, wildLeek, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_LETTUCE, wildLettuce, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_ONION, wildOnion, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_PIPEWEED, wildPipeweed, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_POTATO, wildPotato, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_TOMATO, wildTomato, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(MiddleEarth.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
