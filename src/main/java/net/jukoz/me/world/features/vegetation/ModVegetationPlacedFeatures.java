package net.jukoz.me.world.features.vegetation;

import net.jukoz.me.MiddleEarth;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationConfiguredFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModVegetationPlacedFeatures {
    public static final RegistryKey<PlacedFeature> WATER_DELTA = registerKey("water_delta");
    public static final RegistryKey<PlacedFeature> ABUNDANT_WATER_DELTA = registerKey("abundant_water_delta");
    public static final RegistryKey<PlacedFeature> FLOWER_ALLIUM = registerKey("flower_allium");
    public static final RegistryKey<PlacedFeature> FLOWER_AZURE_BLUET = registerKey("flower_azure_bluet");
    public static final RegistryKey<PlacedFeature> FLOWER_AZURE_BLUET_RARE = registerKey("flower_azure_bluet_rare");
    public static final RegistryKey<PlacedFeature> FLOWER_BLUE_ORCHID = registerKey("flower_blue_orchid");
    public static final RegistryKey<PlacedFeature> FLOWER_CORNFLOWER = registerKey("flower_cornflower");
    public static final RegistryKey<PlacedFeature> FLOWER_CORNFLOWER_COMMON = registerKey("flower_cornflower_common");
    public static final RegistryKey<PlacedFeature> FLOWER_DORWINION = registerKey("flower_dorwinion");
    public static final RegistryKey<PlacedFeature> FLOWER_GREEN_JEWEL = registerKey("flower_green_jewel");
    public static final RegistryKey<PlacedFeature> FLOWER_LILAC = registerKey("flower_lilac");
    public static final RegistryKey<PlacedFeature> FLOWER_LEBENNIN = registerKey("flower_lebennin");
    public static final RegistryKey<PlacedFeature> FLOWER_LOSSARNACH = registerKey("flower_lossarnach");
    public static final RegistryKey<PlacedFeature> FLOWER_LOSSARNACH_COMMON = registerKey("flower_lossarnach_common");
    public static final RegistryKey<PlacedFeature> FLOWER_MEADOW = registerKey("flower_meadow");
    public static final RegistryKey<PlacedFeature> FLOWER_MALLOS = registerKey("flower_mallos");
    public static final RegistryKey<PlacedFeature> FLOWER_ELANOR = registerKey("flower_elanor");

    public static final RegistryKey<PlacedFeature> FLOWERS_LIGHT_BLUE = registerKey("flowers_light_blue");
    public static final RegistryKey<PlacedFeature> FLOWERS_MAGENTA = registerKey("flowers_magenta");
    public static final RegistryKey<PlacedFeature> FLOWERS_ORANGE = registerKey("flowers_orange");
    public static final RegistryKey<PlacedFeature> FLOWERS_PINK = registerKey("flowers_pink");
    public static final RegistryKey<PlacedFeature> FLOWERS_PURPLE = registerKey("flowers_purple");
    public static final RegistryKey<PlacedFeature> FLOWERS_RED = registerKey("flowers_red");
    public static final RegistryKey<PlacedFeature> FLOWERS_WHITE = registerKey("flowers_white");
    public static final RegistryKey<PlacedFeature> FLOWERS_YELLOW = registerKey("flowers_yellow");

    // region FIELDS
    public static final RegistryKey<PlacedFeature> FIELD_HEATHER = registerKey("field_heather");
    // endregion
    public static final RegistryKey<PlacedFeature> MIRKWOOD_VINES = registerKey("mirkwood_vines");

    // region GROWTH
    public static final RegistryKey<PlacedFeature> AZALEA_GROWTH = registerKey("azalea_growth");
    public static final RegistryKey<PlacedFeature> IVY_GROWTH = registerKey("ivy_growth");
    public static final RegistryKey<PlacedFeature> LILAC_FLOWER_GROWTH = registerKey("lilac_flower_growth");
    public static final RegistryKey<PlacedFeature> RED_FLOWER_GROWTH = registerKey("red_flower_growth");
    public static final RegistryKey<PlacedFeature> YELLOW_FLOWER_GROWTH = registerKey("yellow_flower_growth");
    public static final RegistryKey<PlacedFeature> PINK_FLOWER_GROWTH = registerKey("pink_flower_growth");
    public static final RegistryKey<PlacedFeature> WHITE_FLOWER_GROWTH = registerKey("white_flower_growth");
    // endregion

    // region FOLIAGE
    public static final RegistryKey<PlacedFeature> PATCH_BASALT = registerKey("patch_basalt");
    public static final RegistryKey<PlacedFeature> PATCH_BASALT_RARE = registerKey("patch_basalt_rare");
    public static final RegistryKey<PlacedFeature> PATCH_BLACKSTONE = registerKey("patch_blackstone");
    public static final RegistryKey<PlacedFeature> PATCH_BEACH_GRASS = registerKey("patch_beach_grass");
    public static final RegistryKey<PlacedFeature> PATCH_PUMICE = registerKey("patch_pumice");
    public static final RegistryKey<PlacedFeature> PATCH_PUMICE_SPARSE = registerKey("patch_pumice_sparse");
    public static final RegistryKey<PlacedFeature> PUMICE_COLUMN = registerKey("pumice_column");
    public static final RegistryKey<PlacedFeature> PUMICE_COLUMN_RARE = registerKey("pumice_column_rare");
    public static final RegistryKey<PlacedFeature> PUMICE_COLUMN_LARGE = registerKey("pumice_column_large");
    public static final RegistryKey<PlacedFeature> PATCH_BRACKEN = registerKey("patch_bracken");
    public static final RegistryKey<PlacedFeature> PATCH_COBWEB = registerKey("patch_cobweb");
    public static final RegistryKey<PlacedFeature> PATCH_BROWN_GRASS = registerKey("patch_brown_grass");
    public static final RegistryKey<PlacedFeature> PATCH_SHORT_BULRUSH = registerKey("patch_short_bulrush");
    public static final RegistryKey<PlacedFeature> PATCH_TALL_BULRUSH = registerKey("patch_tall_bulrush");
    public static final RegistryKey<PlacedFeature> PATCH_SHORT_CATTAIL = registerKey("patch_short_cattail");
    public static final RegistryKey<PlacedFeature> PATCH_TALL_CATTAIL = registerKey("patch_tall_cattail");
    public static final RegistryKey<PlacedFeature> PATCH_COASTAL_PANIC_GRASS = registerKey("patch_coastal_panic_grass");
    public static final RegistryKey<PlacedFeature> PATCH_CORRUPTED_MOSS = registerKey("patch_corrupted_moss");
    public static final RegistryKey<PlacedFeature> PATCH_CORRUPTED_MOSS_CARPET = registerKey("patch_corrupted_moss_carpet");
    public static final RegistryKey<PlacedFeature> PATCH_DEAD_RUSHES = registerKey("patch_dead_rushes");
    public static final RegistryKey<PlacedFeature> PATCH_DUCKWEED = registerKey("patch_duckweed");
    public static final RegistryKey<PlacedFeature> PATCH_DYING_GRASS = registerKey("patch_dying_grass");
    public static final RegistryKey<PlacedFeature> PATCH_DRY_GRASS = registerKey("patch_dry_grass");
    public static final RegistryKey<PlacedFeature> PATCH_FALSE_OATGRASS = registerKey("patch_false_oatgrass");
    public static final RegistryKey<PlacedFeature> PATCH_FALLEN_LEAVES = registerKey("patch_fallen_leaves");
    public static final RegistryKey<PlacedFeature> PATCH_FALLEN_MALLORN_LEAVES = registerKey("patch_fallen_mallorn_leaves");
    public static final RegistryKey<PlacedFeature> PATCH_FALLEN_MIRKWOOD_LEAVES = registerKey("patch_fallen_mirkwood_leaves");
    public static final RegistryKey<PlacedFeature> PATCH_FOREST_MOSS = registerKey("patch_forest_moss");
    public static final RegistryKey<PlacedFeature> PATCH_FOREST_MOSS_CARPET = registerKey("patch_forest_moss_carpet");
    public static final RegistryKey<PlacedFeature> PATCH_RARE_FOREST_MOSS = registerKey("patch_rare_forest_moss");
    public static final RegistryKey<PlacedFeature> PATCH_GRASS = registerKey("patch_grass");
    public static final RegistryKey<PlacedFeature> PATCH_GRIM_GRASS = registerKey("patch_grim_grass");
    public static final RegistryKey<PlacedFeature> PATCH_GREEN_SHRUB = registerKey("patch_green_shrub");
    public static final RegistryKey<PlacedFeature> PATCH_HEATH = registerKey("patch_heath");
    public static final RegistryKey<PlacedFeature> PATCH_COMMON_HEATH = registerKey("patch_common_heath");
    public static final RegistryKey<PlacedFeature> PATCH_HEATHER = registerKey("patch_heather");
    public static final RegistryKey<PlacedFeature> PATCH_COMMON_HEATHER = registerKey("patch_common_heather");
    public static final RegistryKey<PlacedFeature> PATCH_RARE_HEATHER = registerKey("patch_rare_heather");
    public static final RegistryKey<PlacedFeature> PATCH_DEAD_HEATHER = registerKey("patch_dead_heather");
    public static final RegistryKey<PlacedFeature> PATCH_HOROKAKA = registerKey("patch_horokaka");
    public static final RegistryKey<PlacedFeature> PATCH_GIANT_HOROKAKA = registerKey("patch_giant_horokaka");
    public static final RegistryKey<PlacedFeature> PATCH_RED_HEATHER = registerKey("patch_red_heather");
    public static final RegistryKey<PlacedFeature> PATCH_SCORCHED_GRASS = registerKey("patch_scorched_grass");
    public static final RegistryKey<PlacedFeature> PATCH_SCORCHED_SHRUB = registerKey("patch_scorched_shrub");
    public static final RegistryKey<PlacedFeature> PATCH_SCORCHED_TUFT = registerKey("patch_scorched_tuft");
    public static final RegistryKey<PlacedFeature> PATCH_COMMON_SCORCHED_GRASS = registerKey("patch_common_scorched_grass");
    public static final RegistryKey<PlacedFeature> PATCH_COMMON_SCORCHED_SHRUB = registerKey("patch_common_scorched_shrub");
    public static final RegistryKey<PlacedFeature> PATCH_COMMON_SCORCHED_TUFT = registerKey("patch_common_scorched_tuft");
    public static final RegistryKey<PlacedFeature> PATCH_SEDUM = registerKey("patch_sedum");
    public static final RegistryKey<PlacedFeature> PATCH_SEDUM_YELLOW = registerKey("patch_sedum_yellow");
    public static final RegistryKey<PlacedFeature> PATCH_SHRIVELED_SHRUB = registerKey("patch_shriveled_shrub");
    public static final RegistryKey<PlacedFeature> PATCH_STRAWBERRY_BUSH = registerKey("patch_strawberry_bush");
    public static final RegistryKey<PlacedFeature> PATCH_TALL_GRASS = registerKey("patch_tall_grass");
    public static final RegistryKey<PlacedFeature> PATCH_COMMON_TALL_GRASS = registerKey("patch_common_tall_grass");
    public static final RegistryKey<PlacedFeature> PATCH_TOUGH_BERRY_BUSH = registerKey("patch_tough_berry_bush");
    public static final RegistryKey<PlacedFeature> PATCH_COMMON_TOUGH_BERRY_BUSH = registerKey("patch_common_tough_berry_bush");
    public static final RegistryKey<PlacedFeature> PATCH_TOUGH_BERRY_BUSH_RARE = registerKey("patch_tough_berry_bush_rare");
    public static final RegistryKey<PlacedFeature> PATCH_TUFT_GRASS = registerKey("patch_tuft_grass");
    public static final RegistryKey<PlacedFeature> PATCH_LILY_PAD = registerKey("patch_lily_pad");
    public static final RegistryKey<PlacedFeature> PATCH_LILY_PADS = registerKey("patch_lily_pads");
    public static final RegistryKey<PlacedFeature> PATCH_SMALL_LILY_PADS = registerKey("patch_small_lily_pads");
    public static final RegistryKey<PlacedFeature> PATCH_SMALL_FLOWERING_LILY_PADS = registerKey("patch_small_flowering_lily_pads");
    public static final RegistryKey<PlacedFeature> PATCH_MIRKWOOD = registerKey("patch_mirkwood");
    public static final RegistryKey<PlacedFeature> PATCH_MIRKWOOD_ROOTS = registerKey("patch_mirkwood_roots");
    public static final RegistryKey<PlacedFeature> PATCH_MOSS = registerKey("patch_moss");
    public static final RegistryKey<PlacedFeature> PATCH_MOSS_CARPET = registerKey("patch_moss_carpet");
    public static final RegistryKey<PlacedFeature> PATCH_REEDS = registerKey("patch_reeds");
    public static final RegistryKey<PlacedFeature> PATCH_STICKY_SNOW = registerKey("patch_sticky_snow");
    public static final RegistryKey<PlacedFeature> PATCH_SPIDER_EGGS = registerKey("patch_spider_eggs");
    public static final RegistryKey<PlacedFeature> PATCH_TAN_SHRUB = registerKey("patch_tan_shrub");;
    public static final RegistryKey<PlacedFeature> PATCH_WHEAT_GRASS = registerKey("patch_wheat_grass");
    public static final RegistryKey<PlacedFeature> PATCH_SPARSE_WHEAT_GRASS = registerKey("patch_uncommon_wheat_grass");
    public static final RegistryKey<PlacedFeature> PATCH_COMMON_WHEAT_GRASS = registerKey("patch_common_wheat_grass");
    public static final RegistryKey<PlacedFeature> PATCH_WILD_GRASS = registerKey("patch_wild_grass");
    public static final RegistryKey<PlacedFeature> PATCH_WILDER_GRASS = registerKey("patch_wilder_grass");
    public static final RegistryKey<PlacedFeature> PATCH_RARE_WILDER_GRASS = registerKey("patch_rare_wilder_grass");
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

    static PlacementModifier overflowing = PlacedFeatures.createCountExtraModifier(5, 0.5f, 1);
    static PlacementModifier abundant = PlacedFeatures.createCountExtraModifier(4, 0.5f, 1);
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

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> flowerAllium = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_ALLIUM);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> flowerAzureBluet = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_AZURE_BLUET);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> flowerBlueOrchid = registryEntryLookup.getOrThrow(VegetationConfiguredFeatures.FLOWER_SWAMP);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> flowerMeadow = registryEntryLookup.getOrThrow(VegetationConfiguredFeatures.FLOWER_MEADOW);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> flowerCornflower = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_CORNFLOWER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> flowerDorwinion = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_DORWINION);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> flowerGreenJewel = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_GREEN_JEWEL);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> flowerLilac = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_LILAC);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> flowerLebennin = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_LEBENNIN);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> flowerLossarnach = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_LOSSARNACH);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> flowerMallos = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_MALLOS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> flowerElanor = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_ELANOR);

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> flowersLightBlue = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWERS_LIGHT_BLUE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> flowersMagenta = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWERS_MAGENTA);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> flowersOrange = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWERS_ORANGE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> flowersPink = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWERS_PINK);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> flowersPurple = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWERS_PURPLE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> flowersRed = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWERS_RED);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> flowersWhite = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWERS_WHITE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> flowersYellow = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWERS_YELLOW);

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> fieldHeather = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FIELD_HEATHER);

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mirkwoodVines = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.MIRKWOOD_VINES);

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> growthAzalea = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.AZALEA_GROWTH);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> growthIvy = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.IVY_GROWTH);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> growthLilacFlower = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.LILAC_FLOWER_GROWTH);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> growthRedFlower = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.RED_FLOWER_GROWTH);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> growthYellowFlower = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.YELLOW_FLOWER_GROWTH);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> growthPinkFlower = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PINK_FLOWER_GROWTH);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> growthWhiteFlower = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.WHITE_FLOWER_GROWTH);

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> basalt = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_BASALT);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> blackStone = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_BLACKSTONE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> beachGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_BEACH_GRASS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> pumice = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_PUMICE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> pumiceColumn = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PUMICE_COLUMN);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> pumiceColumnLarge = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PUMICE_COLUMN_LARGE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> brownGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_BROWN_GRASS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> bracken = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_BRACKEN);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> cobweb = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_COBWEB);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> shortBulrush = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SHORT_BULRUSH);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> tallBulrush = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_TALL_BULRUSH);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> shortCattail = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SHORT_CATTAIL);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> tallCattail = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_TALL_CATTAIL);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> coastalPanicGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_COASTAL_PANIC_GRASS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> corruptedMoss = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_CORRUPTED_MOSS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> corruptedMossCarpet = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_CORRUPTED_MOSS_CARPET);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> deadRushes = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_DEAD_RUSHES);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> dyingGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_DYING_GRASS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> dryGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_DRY_GRASS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> duckweed = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_DUCKWEED);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> falseOatgrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_FALSE_OATGRASS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> fallenLeaves = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_FALLEN_LEAVES);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> fallenMallornLeaves = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_FALLEN_MALLORN_LEAVES);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> fallenMirkwoodLeaves = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_FALLEN_MIRKWOOD_LEAVES);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> forestMoss = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_FOREST_MOSS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> forestMossCarpet = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_FOREST_MOSS_CARPET);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> greenShrub = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_GREEN_SHRUB);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> grass = registryEntryLookup.getOrThrow(VegetationConfiguredFeatures.PATCH_GRASS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> grimGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_GRIM_GRASS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> heath = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_HEATH);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> heather = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_HEATHER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> deadHeather = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_DEAD_HEATHER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> horokaka = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_HOROKAKA);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> giantHorokaka = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_GIANT_HOROKAKA);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> redHeather = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_RED_HEATHER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> lilyPad = registryEntryLookup.getOrThrow(VegetationConfiguredFeatures.PATCH_WATERLILY);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> lilyPads = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_LILY_PADS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> smallLilyPads = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SMALL_LILY_PADS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> smallFloweringLilyPads = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SMALL_FLOWERING_LILY_PADS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mirkwoodRoots = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MIRKWOOD_ROOTS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mirkwood = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MIRKWOOD);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> moss = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MOSS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mossCarpet = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MOSS_CARPET);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> reeds = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_REEDS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> stickySnow = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_STICKY_SNOW);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> strawBerries = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_STRAWBERRY_BUSH);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> scorchedGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SCORCHED_GRASS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> scorchedShrub = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SCORCHED_SHRUB);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> scorchedTuft = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SCORCHED_TUFT);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> sedum = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SEDUM);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> sedumYellow = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SEDUM_YELLOW);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> shriveledShrubs = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SHRIVELED_SHRUB);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> spiderEggs = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SPIDER_EGGS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> tanShrub = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_TAN_SHRUB);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> tallGrass = registryEntryLookup.getOrThrow(VegetationConfiguredFeatures.PATCH_TALL_GRASS);
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
        PlacedFeatures.register(featureRegisterable, ABUNDANT_WATER_DELTA, waterDelta, abundant, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWER_ALLIUM, flowerAllium, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWER_AZURE_BLUET, flowerAzureBluet, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWER_AZURE_BLUET_RARE, flowerAzureBluet, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWER_BLUE_ORCHID, flowerBlueOrchid, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWER_MEADOW, flowerMeadow, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWER_DORWINION, flowerDorwinion, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWER_CORNFLOWER, flowerCornflower, veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWER_CORNFLOWER_COMMON, flowerCornflower, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWER_GREEN_JEWEL, flowerGreenJewel, veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWER_LILAC, flowerLilac, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWER_LEBENNIN, flowerLebennin, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWER_LOSSARNACH, flowerLossarnach, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWER_LOSSARNACH_COMMON, flowerLossarnach, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWER_MALLOS, flowerMallos, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWER_ELANOR, flowerElanor, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

        PlacedFeatures.register(featureRegisterable, FLOWERS_LIGHT_BLUE, flowersLightBlue, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWERS_MAGENTA, flowersMagenta, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWERS_ORANGE, flowersOrange, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWERS_PINK, flowersPink, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWERS_PURPLE, flowersPurple, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWERS_RED, flowersRed, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWERS_WHITE, flowersWhite, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FLOWERS_YELLOW, flowersYellow, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

        PlacedFeatures.register(featureRegisterable, FIELD_HEATHER, fieldHeather, common, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

        PlacedFeatures.register(featureRegisterable, MIRKWOOD_VINES, mirkwoodVines, CountPlacementModifier.of(188), SquarePlacementModifier.of(),
                HeightRangePlacementModifier.uniform(YOffset.fixed(64), YOffset.fixed(256)), EnvironmentScanPlacementModifier.of(Direction.UP, BlockPredicate.eitherOf(BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.matchingBlockTag(BlockTags.LEAVES)),
                        BlockPredicate.IS_AIR, 5), RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(-1)), BiomePlacementModifier.of());

        PlacedFeatures.register(featureRegisterable, AZALEA_GROWTH, growthAzalea, CountPlacementModifier.of(UniformIntProvider.create(204, 255)), PlacedFeatures.BOTTOM_TO_120_RANGE,
                SquarePlacementModifier.of(), SurfaceThresholdFilterPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13), BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, IVY_GROWTH, growthIvy, CountPlacementModifier.of(UniformIntProvider.create(16, 32)), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, LILAC_FLOWER_GROWTH, growthLilacFlower, CountPlacementModifier.of(UniformIntProvider.create(16, 32)), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, RED_FLOWER_GROWTH, growthRedFlower, CountPlacementModifier.of(UniformIntProvider.create(16, 32)), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, YELLOW_FLOWER_GROWTH, growthYellowFlower, CountPlacementModifier.of(UniformIntProvider.create(16, 32)), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PINK_FLOWER_GROWTH, growthPinkFlower, CountPlacementModifier.of(UniformIntProvider.create(16, 32)), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, WHITE_FLOWER_GROWTH, growthWhiteFlower, CountPlacementModifier.of(UniformIntProvider.create(16, 32)), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());


        PlacedFeatures.register(featureRegisterable, PATCH_BASALT, basalt, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_BASALT_RARE, basalt, veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_BLACKSTONE, blackStone, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_BEACH_GRASS, beachGrass, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_PUMICE, pumice, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_PUMICE_SPARSE, pumice, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PUMICE_COLUMN, pumiceColumn, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PUMICE_COLUMN_RARE, pumiceColumn, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PUMICE_COLUMN_LARGE, pumiceColumnLarge, veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_BRACKEN, bracken, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_COBWEB, cobweb, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_BROWN_GRASS, brownGrass, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_SHORT_CATTAIL, shortCattail, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_TALL_CATTAIL, tallCattail, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_SHORT_BULRUSH, shortBulrush, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_TALL_BULRUSH, tallBulrush, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_COASTAL_PANIC_GRASS, coastalPanicGrass, veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_CORRUPTED_MOSS_CARPET, corruptedMossCarpet, uncommon, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_CORRUPTED_MOSS, corruptedMoss, CountPlacementModifier.of(UniformIntProvider.create(15, 25)), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_DEAD_RUSHES, deadRushes, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_DUCKWEED, duckweed, abundant, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_DYING_GRASS, dyingGrass, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_DRY_GRASS, dryGrass, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_FALSE_OATGRASS, falseOatgrass, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_FALLEN_LEAVES, fallenLeaves, common, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_FALLEN_MALLORN_LEAVES, fallenMallornLeaves, common, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_FALLEN_MIRKWOOD_LEAVES, fallenMirkwoodLeaves, common, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_FOREST_MOSS, forestMoss, CountPlacementModifier.of(UniformIntProvider.create(15, 30)), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_FOREST_MOSS_CARPET, forestMossCarpet, uncommon, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_GRASS, grass, overflowing, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_GRIM_GRASS, grimGrass, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_GREEN_SHRUB, greenShrub, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_RARE_FOREST_MOSS, forestMoss, CountPlacementModifier.of(UniformIntProvider.create(5, 10)), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_HEATH, heath, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_COMMON_HEATH, heath, uncommon, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_HEATHER, heather, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_COMMON_HEATHER, heather, uncommon, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_RARE_HEATHER, heather, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_DEAD_HEATHER, deadHeather, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_HOROKAKA, horokaka, veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_GIANT_HOROKAKA, giantHorokaka, wildBushRarity, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_RED_HEATHER, redHeather, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_LILY_PAD, lilyPad, common, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_LILY_PADS, lilyPads, common, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_SMALL_LILY_PADS, smallLilyPads, common, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_SMALL_FLOWERING_LILY_PADS, smallFloweringLilyPads, common, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_MIRKWOOD, mirkwood, RarityFilterPlacementModifier.of(25), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_MIRKWOOD_ROOTS, mirkwoodRoots, sparse, CountPlacementModifier.of(UniformIntProvider.create(0, 2)));
        PlacedFeatures.register(featureRegisterable, PATCH_MOSS, moss, CountPlacementModifier.of(UniformIntProvider.create(15, 30)), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_MOSS_CARPET, mossCarpet, uncommon, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_SCORCHED_GRASS, scorchedGrass, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_SCORCHED_SHRUB, scorchedShrub, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_SCORCHED_TUFT, scorchedTuft, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_COMMON_SCORCHED_GRASS, scorchedGrass, common, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_COMMON_SCORCHED_SHRUB, scorchedShrub, common, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_COMMON_SCORCHED_TUFT, scorchedTuft, common, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_SEDUM, sedum, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_SEDUM_YELLOW, sedumYellow, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_SHRIVELED_SHRUB, shriveledShrubs, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_SPIDER_EGGS, spiderEggs, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_STRAWBERRY_BUSH, strawBerries, veryRare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_TALL_GRASS, tallGrass, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_COMMON_TALL_GRASS, tallGrass, common, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_TAN_SHRUB, tanShrub, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_TOUGH_BERRY_BUSH, toughBerries, RarityFilterPlacementModifier.of(48), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_COMMON_TOUGH_BERRY_BUSH, toughBerries, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_TOUGH_BERRY_BUSH_RARE, tuftGrass, RarityFilterPlacementModifier.of(320), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_TUFT_GRASS, tuftGrass, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_REEDS, reeds, CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_STICKY_SNOW, stickySnow, CountPlacementModifier.of(UniformIntProvider.create(48, 80)), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WHEAT_GRASS, wheatGrass, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_SPARSE_WHEAT_GRASS, wheatGrass, sparse, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_COMMON_WHEAT_GRASS, wheatGrass, uncommon, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILD_GRASS, wildGrass, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_WILDER_GRASS, wilderGrass, scarce, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PATCH_RARE_WILDER_GRASS, wilderGrass, rare, SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
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
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MiddleEarth.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
