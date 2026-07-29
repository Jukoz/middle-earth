package net.sevenstars.middleearth.world.features.vegetation;

import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;
import net.minecraft.world.level.material.Fluids;
import java.util.List;

public class ModVegetationPlacedFeatures {
    public static final ResourceKey<PlacedFeature> WATER_DELTA = registerKey("water_delta");
    public static final ResourceKey<PlacedFeature> ABUNDANT_WATER_DELTA = registerKey("abundant_water_delta");
    public static final ResourceKey<PlacedFeature> FLOWER_ALLIUM = registerKey("flower_allium");
    public static final ResourceKey<PlacedFeature> FLOWER_ATHELAS = registerKey("flower_athelas");
    public static final ResourceKey<PlacedFeature> FLOWER_AZURE_BLUET = registerKey("flower_azure_bluet");
    public static final ResourceKey<PlacedFeature> FLOWER_AZURE_BLUET_RARE = registerKey("flower_azure_bluet_rare");
    public static final ResourceKey<PlacedFeature> FLOWER_BLUE_GENTIAN = registerKey("flower_blue_gentian");
    public static final ResourceKey<PlacedFeature> FLOWER_BLUE_ORCHID = registerKey("flower_blue_orchid");
    public static final ResourceKey<PlacedFeature> FLOWER_CORNFLOWER = registerKey("flower_cornflower");
    public static final ResourceKey<PlacedFeature> FLOWER_CORNFLOWER_COMMON = registerKey("flower_cornflower_common");
    public static final ResourceKey<PlacedFeature> FLOWER_DORWINION = registerKey("flower_dorwinion");
    public static final ResourceKey<PlacedFeature> FLOWER_GREEN_JEWEL = registerKey("flower_green_jewel");
    public static final ResourceKey<PlacedFeature> FLOWER_LILAC = registerKey("flower_lilac");
    public static final ResourceKey<PlacedFeature> FLOWER_LEBENNIN = registerKey("flower_lebennin");
    public static final ResourceKey<PlacedFeature> FLOWER_LOSSARNACH = registerKey("flower_lossarnach");
    public static final ResourceKey<PlacedFeature> FLOWER_LOSSARNACH_COMMON = registerKey("flower_lossarnach_common");
    public static final ResourceKey<PlacedFeature> FLOWER_MEADOW = registerKey("flower_meadow");
    public static final ResourceKey<PlacedFeature> FLOWER_MALLOS = registerKey("flower_mallos");
    public static final ResourceKey<PlacedFeature> FLOWER_NOBLEWHITE = registerKey("flower_noblewhite");
    public static final ResourceKey<PlacedFeature> FLOWER_POPPY = registerKey("flower_poppy");
    public static final ResourceKey<PlacedFeature> FLOWER_ELANOR = registerKey("flower_elanor");
    public static final ResourceKey<PlacedFeature> FLOWER_NIPHREDIL = registerKey("flower_niphredil");
    public static final ResourceKey<PlacedFeature> FLOWER_SIMBELMYNE = registerKey("flower_simbelmyne");
    public static final ResourceKey<PlacedFeature> FLOWER_ROSE_BUSH = registerKey("flower_rose_bush");
    public static final ResourceKey<PlacedFeature> FLOWER_YELLOW = registerKey("patch_yellow_flower");
    public static final ResourceKey<PlacedFeature> RARE_FLOWER_YELLOW = registerKey("patch_rare_yellow_flower");
    public static final ResourceKey<PlacedFeature> PATCH_WILD_FLOWERS = registerKey("patch_wild_flowers");
    public static final ResourceKey<PlacedFeature> PATCH_LEAF_LITTER = registerKey("patch_leaf_litter");
    public static final ResourceKey<PlacedFeature> PATCH_DRY_GRASS_DESERT = registerKey("patch_dry_grass_desert");
    public static final ResourceKey<PlacedFeature> PATCH_FIREFLY_BUSH_SWAMP = registerKey("patch_firefly_bush_swamp");
    public static final ResourceKey<PlacedFeature> PATCH_FIREFLY_BUSH_SWAMP_NEAR_WATER = registerKey("patch_firefly_bush_near_water_swamp");

    public static final ResourceKey<PlacedFeature> FLOWERS_LIGHT_BLUE = registerKey("flowers_light_blue");
    public static final ResourceKey<PlacedFeature> RARE_FLOWERS_LIGHT_BLUE = registerKey("rare_flowers_light_blue");
    public static final ResourceKey<PlacedFeature> FLOWERS_MAGENTA = registerKey("flowers_magenta");
    public static final ResourceKey<PlacedFeature> FLOWERS_ORANGE = registerKey("flowers_orange");
    public static final ResourceKey<PlacedFeature> FLOWERS_PINK = registerKey("flowers_pink");
    public static final ResourceKey<PlacedFeature> FLOWERS_PURPLE = registerKey("flowers_purple");
    public static final ResourceKey<PlacedFeature> FLOWERS_RED = registerKey("flowers_red");
    public static final ResourceKey<PlacedFeature> FLOWERS_WHITE = registerKey("flowers_white");
    public static final ResourceKey<PlacedFeature> FLOWERS_YELLOW = registerKey("flowers_yellow");

    // region FIELDS
    public static final ResourceKey<PlacedFeature> FIELD_HEATHER = registerKey("field_heather");
    public static final ResourceKey<PlacedFeature> FIELD_DRY_HEATHER = registerKey("field_dry_heather");
    public static final ResourceKey<PlacedFeature> FIELD_LAVENDER = registerKey("field_lavender");
    public static final ResourceKey<PlacedFeature> FIELD_WILD_WHEAT = registerKey("field_wild_wheat");
    public static final ResourceKey<PlacedFeature> FIELD_DEAD_NORMAL_HEATHER = registerKey("field_dead_normal_heather");
    public static final ResourceKey<PlacedFeature> FIELD_SPARSE_DEAD_NORMAL_HEATHER = registerKey("field_sparse_dead_normal_heather");
    public static final ResourceKey<PlacedFeature> FIELD_BLUE_FESCUE = registerKey("field_blue_fescue");

    // endregion
    public static final ResourceKey<PlacedFeature> MIRKWOOD_VINES = registerKey("mirkwood_vines");
    public static final ResourceKey<PlacedFeature> WILLOW_VINES = registerKey("willow_vines");

    // region GROWTH
    public static final ResourceKey<PlacedFeature> AZALEA_GROWTH = registerKey("azalea_growth");
    public static final ResourceKey<PlacedFeature> DRY_GROWTH = registerKey("dry_growth");
    public static final ResourceKey<PlacedFeature> IVY_GROWTH = registerKey("ivy_growth");
    public static final ResourceKey<PlacedFeature> GREEN_GROWTH = registerKey("green_growth");
    public static final ResourceKey<PlacedFeature> THORNY_GROWTH = registerKey("thorny_growth");
    public static final ResourceKey<PlacedFeature> FROZEN_GROWTH = registerKey("frozen_growth");
    public static final ResourceKey<PlacedFeature> LILAC_FLOWER_GROWTH = registerKey("lilac_flower_growth");
    public static final ResourceKey<PlacedFeature> RED_FLOWER_GROWTH = registerKey("red_flower_growth");
    public static final ResourceKey<PlacedFeature> YELLOW_FLOWER_GROWTH = registerKey("yellow_flower_growth");
    public static final ResourceKey<PlacedFeature> PINK_FLOWER_GROWTH = registerKey("pink_flower_growth");
    public static final ResourceKey<PlacedFeature> WHITE_FLOWER_GROWTH = registerKey("white_flower_growth");
    // endregion

    // region FOLIAGE
    public static final ResourceKey<PlacedFeature> PATCH_BAMBOO = registerKey("patch_bamboo");
    public static final ResourceKey<PlacedFeature> PATCH_BASALT = registerKey("patch_basalt");
    public static final ResourceKey<PlacedFeature> PATCH_BASALT_RARE = registerKey("patch_basalt_rare");
    public static final ResourceKey<PlacedFeature> PATCH_BLACKSTONE = registerKey("patch_blackstone");
    public static final ResourceKey<PlacedFeature> PATCH_COMMON_BEACH_GRASS = registerKey("patch_common_beach_grass");
    public static final ResourceKey<PlacedFeature> PATCH_BEACH_GRASS = registerKey("patch_beach_grass");
    public static final ResourceKey<PlacedFeature> PATCH_BUSHES = registerKey("patch_bushes");
    public static final ResourceKey<PlacedFeature> PATCH_CLOVERS = registerKey("patch_clovers");
    public static final ResourceKey<PlacedFeature> PATCH_PUMICE = registerKey("patch_pumice");
    public static final ResourceKey<PlacedFeature> PATCH_PUMICE_SPARSE = registerKey("patch_pumice_sparse");
    public static final ResourceKey<PlacedFeature> PUMICE_COLUMN = registerKey("pumice_column");
    public static final ResourceKey<PlacedFeature> PUMICE_COLUMN_RARE = registerKey("pumice_column_rare");
    public static final ResourceKey<PlacedFeature> PUMICE_COLUMN_LARGE = registerKey("pumice_column_large");
    public static final ResourceKey<PlacedFeature> PATCH_BRACKEN = registerKey("patch_bracken");
    public static final ResourceKey<PlacedFeature> PATCH_GIANT_BUTTERBUR = registerKey("patch_giant_butterbur");
    public static final ResourceKey<PlacedFeature> PATCH_COBWEB = registerKey("patch_cobweb");
    public static final ResourceKey<PlacedFeature> PATCH_BROWN_GRASS = registerKey("patch_brown_grass");
    public static final ResourceKey<PlacedFeature> PATCH_SHORT_BULRUSH = registerKey("patch_short_bulrush");
    public static final ResourceKey<PlacedFeature> PATCH_TALL_BULRUSH = registerKey("patch_tall_bulrush");
    public static final ResourceKey<PlacedFeature> PATCH_SHORT_CATTAIL = registerKey("patch_short_cattail");
    public static final ResourceKey<PlacedFeature> PATCH_TALL_CATTAIL = registerKey("patch_tall_cattail");
    public static final ResourceKey<PlacedFeature> PATCH_COASTAL_PANIC_GRASS = registerKey("patch_coastal_panic_grass");
    public static final ResourceKey<PlacedFeature> PATCH_CORRUPTED_MOSS = registerKey("patch_corrupted_moss");
    public static final ResourceKey<PlacedFeature> PATCH_CORRUPTED_MOSS_CARPET = registerKey("patch_corrupted_moss_carpet");
    public static final ResourceKey<PlacedFeature> PATCH_DEAD_RUSHES = registerKey("patch_dead_rushes");
    public static final ResourceKey<PlacedFeature> PATCH_DUCKWEED = registerKey("patch_duckweed");
    public static final ResourceKey<PlacedFeature> PATCH_DYING_GRASS = registerKey("patch_dying_grass");
    public static final ResourceKey<PlacedFeature> PATCH_DRY_GRASS = registerKey("patch_dry_grass");
    public static final ResourceKey<PlacedFeature> PATCH_VERY_RARE_DRY_GRASS = registerKey("patch_very_raredry_grass");
    public static final ResourceKey<PlacedFeature> PATCH_SMALL_DRY_SHRUB = registerKey("patch_small_dry_shrub");
    public static final ResourceKey<PlacedFeature> PATCH_FALSE_OATGRASS = registerKey("patch_false_oatgrass");
    public static final ResourceKey<PlacedFeature> PATCH_FALLEN_LEAVES = registerKey("patch_fallen_leaves");
    public static final ResourceKey<PlacedFeature> PATCH_FALLEN_MALLORN_LEAVES = registerKey("patch_fallen_mallorn_leaves");
    public static final ResourceKey<PlacedFeature> PATCH_FALLEN_MIRKWOOD_LEAVES = registerKey("patch_fallen_mirkwood_leaves");
    public static final ResourceKey<PlacedFeature> PATCH_FLOATING_ICE = registerKey("patch_floating_ice");
    public static final ResourceKey<PlacedFeature> PATCH_FOREST_MOSS = registerKey("patch_forest_moss");
    public static final ResourceKey<PlacedFeature> PATCH_FOREST_MOSS_CARPET = registerKey("patch_forest_moss_carpet");
    public static final ResourceKey<PlacedFeature> PATCH_FROZEN_GRASS = registerKey("patch_frozen_grass");
    public static final ResourceKey<PlacedFeature> PATCH_FROZEN_SHRUB = registerKey("patch_frozen_shrub");
    public static final ResourceKey<PlacedFeature> PATCH_FROZEN_TUFT = registerKey("patch_frozen_tuft");
    public static final ResourceKey<PlacedFeature> PATCH_RARE_FOREST_MOSS = registerKey("patch_rare_forest_moss");
    public static final ResourceKey<PlacedFeature> PATCH_GRASS = registerKey("patch_grass");
    public static final ResourceKey<PlacedFeature> PATCH_GRIM_GRASS = registerKey("patch_grim_grass");
    public static final ResourceKey<PlacedFeature> PATCH_GREEN_SHRUB = registerKey("patch_green_shrub");
    public static final ResourceKey<PlacedFeature> PATCH_HEATH = registerKey("patch_heath");
    public static final ResourceKey<PlacedFeature> PATCH_COMMON_HEATH = registerKey("patch_common_heath");
    public static final ResourceKey<PlacedFeature> PATCH_SPARSE_HEATH = registerKey("patch_sparse_heath");
    public static final ResourceKey<PlacedFeature> PATCH_HEATHER = registerKey("patch_heather");
    public static final ResourceKey<PlacedFeature> PATCH_COMMON_HEATHER = registerKey("patch_common_heather");
    public static final ResourceKey<PlacedFeature> PATCH_RARE_HEATHER = registerKey("patch_rare_heather");
    public static final ResourceKey<PlacedFeature> PATCH_DEAD_HEATHER = registerKey("patch_dead_heather");
    public static final ResourceKey<PlacedFeature> PATCH_DRY_HEATHER = registerKey("patch_dry_heather");
    public static final ResourceKey<PlacedFeature> PATCH_RED_HEATHER = registerKey("patch_red_heather");
    public static final ResourceKey<PlacedFeature> PATCH_BLUE_LAVENDER = registerKey("patch_blue_lavender");
    public static final ResourceKey<PlacedFeature> PATCH_SPARSE_BLUE_LAVENDER = registerKey("patch_sparse_blue_lavender");
    public static final ResourceKey<PlacedFeature> PATCH_RARE_BLUE_LAVENDER = registerKey("patch_rare_blue_lavender");
    public static final ResourceKey<PlacedFeature> PATCH_VERY_RARE_BLUE_LAVENDER = registerKey("patch_very_rare_blue_lavender");
    public static final ResourceKey<PlacedFeature> PATCH_LAVENDER = registerKey("patch_lavender");
    public static final ResourceKey<PlacedFeature> PATCH_SPARSE_LAVENDER = registerKey("patch_sparse_lavender");
    public static final ResourceKey<PlacedFeature> PATCH_RARE_LAVENDER = registerKey("patch_rare_lavender");
    public static final ResourceKey<PlacedFeature> PATCH_VERY_RARE_LAVENDER = registerKey("patch_very_rare_lavender");
    public static final ResourceKey<PlacedFeature> PATCH_WHITE_LAVENDER = registerKey("patch_white_lavender");
    public static final ResourceKey<PlacedFeature> PATCH_SCARCE_WHITE_LAVENDER = registerKey("patch_sparse_white_lavender");
    public static final ResourceKey<PlacedFeature> PATCH_RARE_WHITE_LAVENDER = registerKey("patch_rare_white_lavender");
    public static final ResourceKey<PlacedFeature> PATCH_VERY_RARE_WHITE_LAVENDER = registerKey("patch_very_rare_white_lavender");
    public static final ResourceKey<PlacedFeature> PATCH_SCORCHED_GRASS = registerKey("patch_scorched_grass");
    public static final ResourceKey<PlacedFeature> PATCH_SCORCHED_SHRUB = registerKey("patch_scorched_shrub");
    public static final ResourceKey<PlacedFeature> PATCH_SCORCHED_TUFT = registerKey("patch_scorched_tuft");
    public static final ResourceKey<PlacedFeature> PATCH_COMMON_SCORCHED_GRASS = registerKey("patch_common_scorched_grass");
    public static final ResourceKey<PlacedFeature> PATCH_COMMON_SCORCHED_SHRUB = registerKey("patch_common_scorched_shrub");
    public static final ResourceKey<PlacedFeature> PATCH_COMMON_SCORCHED_TUFT = registerKey("patch_common_scorched_tuft");
    public static final ResourceKey<PlacedFeature> PATCH_SEDUM = registerKey("patch_sedum");
    public static final ResourceKey<PlacedFeature> PATCH_SEDUM_ORANGE = registerKey("patch_sedum_orange");
    public static final ResourceKey<PlacedFeature> PATCH_SEDUM_RED = registerKey("patch_sedum_red");
    public static final ResourceKey<PlacedFeature> PATCH_SEDUM_YELLOW = registerKey("patch_sedum_yellow");
    public static final ResourceKey<PlacedFeature> PATCH_SEDUMS = registerKey("patch_sedums");
    public static final ResourceKey<PlacedFeature> PATCH_SHRIVELED_SHRUB = registerKey("patch_shriveled_shrub");
    public static final ResourceKey<PlacedFeature> PATCH_LARGE_SHRIVELED_SHRUB = registerKey("patch_large_shriveled_shrub");
    public static final ResourceKey<PlacedFeature> PATCH_RUSHES = registerKey("patch_rushes");
    public static final ResourceKey<PlacedFeature> PATCH_SHORT_RUSHES = registerKey("patch_short_rushes");
    public static final ResourceKey<PlacedFeature> PATCH_STRAWBERRY_BUSH = registerKey("patch_strawberry_bush");
    public static final ResourceKey<PlacedFeature> PATCH_YELLOW_TROLLIUS = registerKey("patch_yellow_trollius");
    public static final ResourceKey<PlacedFeature> PATCH_TALL_GRASS = registerKey("patch_tall_grass");
    public static final ResourceKey<PlacedFeature> PATCH_COMMON_TALL_GRASS = registerKey("patch_common_tall_grass");
    public static final ResourceKey<PlacedFeature> PATCH_TEMPERATE_GRASS = registerKey("patch_temperate_grass");
    public static final ResourceKey<PlacedFeature> PATCH_SWEET_BERRY_BUSH_RARE = registerKey("patch_tsweet_berry_bush_rare");
    public static final ResourceKey<PlacedFeature> PATCH_TOUGH_BERRY_BUSH = registerKey("patch_tough_berry_bush");
    public static final ResourceKey<PlacedFeature> PATCH_COMMON_TOUGH_BERRY_BUSH = registerKey("patch_common_tough_berry_bush");
    public static final ResourceKey<PlacedFeature> PATCH_TOUGH_BERRY_BUSH_RARE = registerKey("patch_tough_berry_bush_rare");
    public static final ResourceKey<PlacedFeature> PATCH_TUFT_GRASS = registerKey("patch_tuft_grass");
    public static final ResourceKey<PlacedFeature> PATCH_SPARSE_GRASS = registerKey("patch_sparse_grass");
    public static final ResourceKey<PlacedFeature> PATCH_LILY_PAD = registerKey("patch_lily_pad");
    public static final ResourceKey<PlacedFeature> PATCH_LARGE_LILY_PAD = registerKey("patch_large_lily_pad");
    public static final ResourceKey<PlacedFeature> PATCH_LARGE_FLOWERING_LILY_PAD = registerKey("patch_large_flowering_lily_pad");
    public static final ResourceKey<PlacedFeature> PATCH_LILY_PADS = registerKey("patch_lily_pads");
    public static final ResourceKey<PlacedFeature> PATCH_FLOWERING_LILY_PADS = registerKey("patch_flowering_lily_pads");
    public static final ResourceKey<PlacedFeature> PATCH_SMALL_LILY_PADS = registerKey("patch_small_lily_pads");
    public static final ResourceKey<PlacedFeature> PATCH_SMALL_FLOWERING_LILY_PADS = registerKey("patch_small_flowering_lily_pads");
    public static final ResourceKey<PlacedFeature> PATCH_MIRKWOOD = registerKey("patch_mirkwood");
    public static final ResourceKey<PlacedFeature> PATCH_MIRKWOOD_ROOTS = registerKey("patch_mirkwood_roots");
    public static final ResourceKey<PlacedFeature> PATCH_MISTWEED = registerKey("patch_mistweed");
    public static final ResourceKey<PlacedFeature> PATCH_MOSS = registerKey("patch_moss");
    public static final ResourceKey<PlacedFeature> PATCH_MOSS_CARPET = registerKey("patch_moss_carpet");
    public static final ResourceKey<PlacedFeature> PATCH_HOGWEED = registerKey("patch_hogweed");
    public static final ResourceKey<PlacedFeature> PATCH_HOBBIT_SUNFLOWERS = registerKey("patch_hobbit_sunflowers");
    public static final ResourceKey<PlacedFeature> PATCH_BLUE_BIGLEAF_HYDRANGEA = registerKey("patch_blue_bigleaf_hydrangea");
    public static final ResourceKey<PlacedFeature> PATCH_PINK_BIGLEAF_HYDRANGEA = registerKey("patch_pink_bigleaf_hydrangea");
    public static final ResourceKey<PlacedFeature> PATCH_WHITE_BIGLEAF_HYDRANGEA = registerKey("patch_white_bigleaf_hydrangea");
    public static final ResourceKey<PlacedFeature> PATCH_CAMPION = registerKey("patch_campion");
    public static final ResourceKey<PlacedFeature> PATCH_REEDS = registerKey("patch_reeds");
    public static final ResourceKey<PlacedFeature> PATCH_SHORT_REEDS = registerKey("patch_short_reeds");
    public static final ResourceKey<PlacedFeature> PATCH_STICKY_SNOW = registerKey("patch_sticky_snow");
    public static final ResourceKey<PlacedFeature> PATCH_SPIDER_EGGS = registerKey("patch_spider_eggs");
    public static final ResourceKey<PlacedFeature> PATCH_TAN_SHRUB = registerKey("patch_tan_shrub");;
    public static final ResourceKey<PlacedFeature> PATCH_WHEAT_GRASS = registerKey("patch_wheat_grass");
    public static final ResourceKey<PlacedFeature> PATCH_SPARSE_WHEAT_GRASS = registerKey("patch_uncommon_wheat_grass");
    public static final ResourceKey<PlacedFeature> PATCH_COMMON_WHEAT_GRASS = registerKey("patch_common_wheat_grass");
    public static final ResourceKey<PlacedFeature> PATCH_MEADOW_GRASS = registerKey("patch_meadow_grass");
    public static final ResourceKey<PlacedFeature> PATCH_MIXED_WILD_WHEAT = registerKey("patch_common_mixed_wild_grass");

    public static final ResourceKey<PlacedFeature> PATCH_WILD_GRASS = registerKey("patch_wild_grass");
    public static final ResourceKey<PlacedFeature> PATCH_OCCASIONAL_WILD_GRASS = registerKey("patch_occasional_wild_grass");
    public static final ResourceKey<PlacedFeature> PATCH_WILDER_GRASS = registerKey("patch_wilder_grass");
    public static final ResourceKey<PlacedFeature> PATCH_RARE_WILDER_GRASS = registerKey("patch_rare_wilder_grass");

    public static final ResourceKey<PlacedFeature> PATCH_NETTLES = registerKey("patch_nettles");
    public static final ResourceKey<PlacedFeature> PATCH_THISTLE = registerKey("patch_thistle");
    public static final ResourceKey<PlacedFeature> PATCH_MORDOR_BRAMBLES = registerKey("patch_mordor_brambles");
    // endregion

    // region MUSHROOMS
    public static final ResourceKey<PlacedFeature> PATCH_BROWN_BOLETE = registerKey("patch_brown_bolete");
    public static final ResourceKey<PlacedFeature> PATCH_BROWN_BOLETE_TILLER = registerKey("patch_brown_bolete_tiller");
    public static final ResourceKey<PlacedFeature> PATCH_MORSEL = registerKey("patch_morsel");
    public static final ResourceKey<PlacedFeature> PATCH_MORSEL_TILLER = registerKey("patch_morsel_tiller");
    public static final ResourceKey<PlacedFeature> PATCH_WHITE_MUSHROOM = registerKey("patch_white_mushroom");
    public static final ResourceKey<PlacedFeature> PATCH_WHITE_MUSHROOM_TILLER = registerKey("patch_white_mushroom_tiller");
    public static final ResourceKey<PlacedFeature> PATCH_RARE_MORSEL = registerKey("patch_rare_morsel");
    public static final ResourceKey<PlacedFeature> PATCH_RARE_MORSEL_TILLER = registerKey("patch_rare_morsel_tiller");
    public static final ResourceKey<PlacedFeature> PATCH_RARE_WHITE_MUSHROOM = registerKey("patch_rare_white_mushroom");
    public static final ResourceKey<PlacedFeature> PATCH_RARE_WHITE_MUSHROOM_TILLER = registerKey("patch_rare_white_mushroom_tiller");
    // endregion

    // region WILD CROPS
    public static final ResourceKey<PlacedFeature> PATCH_WILD_BEETROOT = registerKey("patch_wild_beetroot");
    public static final ResourceKey<PlacedFeature> PATCH_WILD_BELL_PEPPER = registerKey("patch_wild_bell_pepper");
    public static final ResourceKey<PlacedFeature> PATCH_WILD_CARROT = registerKey("patch_wild_carrot");
    public static final ResourceKey<PlacedFeature> PATCH_WILD_CUCUMBER = registerKey("patch_wild_cucumber");
    public static final ResourceKey<PlacedFeature> PATCH_WILD_FLAX = registerKey("patch_wild_flax");
    public static final ResourceKey<PlacedFeature> PATCH_WILD_GARLIC = registerKey("patch_wild_garlic");
    public static final ResourceKey<PlacedFeature> PATCH_WILD_LEEK = registerKey("patch_wild_leek");
    public static final ResourceKey<PlacedFeature> PATCH_WILD_LETTUCE = registerKey("patch_wild_lettuce");
    public static final ResourceKey<PlacedFeature> PATCH_WILD_ONION = registerKey("patch_wild_onion");
    public static final ResourceKey<PlacedFeature> PATCH_WILD_PIPEWEED = registerKey("patch_wild_pipeweed");
    public static final ResourceKey<PlacedFeature> PATCH_WILD_POTATO = registerKey("patch_wild_potato");
    public static final ResourceKey<PlacedFeature> PATCH_WILD_TOMATO = registerKey("patch_wild_tomato");
    // endregion

    static PlacementModifier overflowing = PlacementUtils.countExtra(5, 0.5f, 1);
    static PlacementModifier abundant = PlacementUtils.countExtra(4, 0.5f, 1);
    static PlacementModifier common = PlacementUtils.countExtra(2, 0.5f, 1);
    static PlacementModifier uncommon = PlacementUtils.countExtra(1, 0.2f, 1);
    static PlacementModifier sparse = PlacementUtils.countExtra(0, 0.5f, 1);
    static PlacementModifier occasional = PlacementUtils.countExtra(0, 0.25f, 1);
    static PlacementModifier scarce = PlacementUtils.countExtra(0, 0.2f, 1);
    static PlacementModifier rare = PlacementUtils.countExtra(0, 0.1f, 1);
    static PlacementModifier veryRare = PlacementUtils.countExtra(0, 0.05f, 1);
    static PlacementModifier wildBushRarity = PlacementUtils.countExtra(0, 0.01f, 1);

    public static void bootstrap(BootstrapContext<PlacedFeature> featureRegisterable) {
        HolderGetter<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.lookup(Registries.CONFIGURED_FEATURE);
        Holder.Reference<ConfiguredFeature<?, ?>> waterDelta = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.WATER_DELTA);

        Holder.Reference<ConfiguredFeature<?, ?>> flowerAllium = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_ALLIUM);
        Holder.Reference<ConfiguredFeature<?, ?>> flowerAthelas = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_ATHELAS);
        Holder.Reference<ConfiguredFeature<?, ?>> flowerAzureBluet = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_AZURE_BLUET);
        Holder.Reference<ConfiguredFeature<?, ?>> flowerBlueGentian = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_BLUE_GENTIAN);
        Holder.Reference<ConfiguredFeature<?, ?>> flowerBlueOrchid = registryEntryLookup.getOrThrow(VegetationFeatures.FLOWER_SWAMP);
        Holder.Reference<ConfiguredFeature<?, ?>> flowerMeadow = registryEntryLookup.getOrThrow(VegetationFeatures.FLOWER_MEADOW);
        Holder.Reference<ConfiguredFeature<?, ?>> flowerCornflower = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_CORNFLOWER);
        Holder.Reference<ConfiguredFeature<?, ?>> flowerDorwinion = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_DORWINION);
        Holder.Reference<ConfiguredFeature<?, ?>> flowerGreenJewel = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_GREEN_JEWEL);
        Holder.Reference<ConfiguredFeature<?, ?>> flowerLilac = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_LILAC);
        Holder.Reference<ConfiguredFeature<?, ?>> flowerLebennin = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_LEBENNIN);
        Holder.Reference<ConfiguredFeature<?, ?>> flowerLossarnach = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_LOSSARNACH);
        Holder.Reference<ConfiguredFeature<?, ?>> flowerMallos = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_MALLOS);
        Holder.Reference<ConfiguredFeature<?, ?>> flowerNobleWhite = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_NOBLEWHITE);
        Holder.Reference<ConfiguredFeature<?, ?>> flowerPoppy = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_POPPY);
        Holder.Reference<ConfiguredFeature<?, ?>> flowerElanor = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_ELANOR);
        Holder.Reference<ConfiguredFeature<?, ?>> flowerNiphredil = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_NIPHREDIL);
        Holder.Reference<ConfiguredFeature<?, ?>> flowerSimbelmyne = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_SIMBELMYNE);
        Holder.Reference<ConfiguredFeature<?, ?>> flowerRoseBush = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWER_ROSE_BUSH);

        Holder.Reference<ConfiguredFeature<?, ?>> flowersLightBlue = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWERS_LIGHT_BLUE);
        Holder.Reference<ConfiguredFeature<?, ?>> flowersMagenta = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWERS_MAGENTA);
        Holder.Reference<ConfiguredFeature<?, ?>> flowersOrange = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWERS_ORANGE);
        Holder.Reference<ConfiguredFeature<?, ?>> flowersPink = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWERS_PINK);
        Holder.Reference<ConfiguredFeature<?, ?>> flowersPurple = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWERS_PURPLE);
        Holder.Reference<ConfiguredFeature<?, ?>> flowersRed = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWERS_RED);
        Holder.Reference<ConfiguredFeature<?, ?>> flowersWhite = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWERS_WHITE);
        Holder.Reference<ConfiguredFeature<?, ?>> flowersYellow = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FLOWERS_YELLOW);

        Holder.Reference<ConfiguredFeature<?, ?>> fieldHeather = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FIELD_HEATHER);
        Holder.Reference<ConfiguredFeature<?, ?>> fieldDryHeather = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FIELD_DRY_HEATHER);
        Holder.Reference<ConfiguredFeature<?, ?>> fieldLavender = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FIELD_LAVENDER);
        Holder.Reference<ConfiguredFeature<?, ?>> fieldWildWeather = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FIELD_WILD_WHEAT);

        Holder.Reference<ConfiguredFeature<?, ?>> mirkwoodVines = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.MIRKWOOD_VINES);
        Holder.Reference<ConfiguredFeature<?, ?>> willowVines = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.WILLOW_VINES);

        Holder.Reference<ConfiguredFeature<?, ?>> growthAzalea = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.AZALEA_GROWTH);
        Holder.Reference<ConfiguredFeature<?, ?>> growthDry = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.DRY_GROWTH);
        Holder.Reference<ConfiguredFeature<?, ?>> growthIvy = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.IVY_GROWTH);
        Holder.Reference<ConfiguredFeature<?, ?>> growthFrozen = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_FROZEN_GROWTH);
        Holder.Reference<ConfiguredFeature<?, ?>> growthGreen = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_GREEN_GROWTH);
        Holder.Reference<ConfiguredFeature<?, ?>> thornyGrowth = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_THORNY_GROWTH);
        Holder.Reference<ConfiguredFeature<?, ?>> growthLilacFlower = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.LILAC_FLOWER_GROWTH);
        Holder.Reference<ConfiguredFeature<?, ?>> growthRedFlower = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.RED_FLOWER_GROWTH);
        Holder.Reference<ConfiguredFeature<?, ?>> growthYellowFlower = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.YELLOW_FLOWER_GROWTH);
        Holder.Reference<ConfiguredFeature<?, ?>> growthPinkFlower = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PINK_FLOWER_GROWTH);
        Holder.Reference<ConfiguredFeature<?, ?>> growthWhiteFlower = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.WHITE_FLOWER_GROWTH);
        Holder.Reference<ConfiguredFeature<?, ?>> wildFlowers = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.WILDFLOWERS_BIRCH_FOREST);
        Holder.Reference<ConfiguredFeature<?, ?>> leafLitter = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_LEAF_LITTER);
        Holder.Reference<ConfiguredFeature<?, ?>> fireflyBush = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_FIREFLY_BUSH);

        Holder.Reference<ConfiguredFeature<?, ?>> bamboo = registryEntryLookup.getOrThrow(VegetationFeatures.BAMBOO_NO_PODZOL);
        Holder.Reference<ConfiguredFeature<?, ?>> basalt = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_BASALT);
        Holder.Reference<ConfiguredFeature<?, ?>> blackStone = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_BLACKSTONE);
        Holder.Reference<ConfiguredFeature<?, ?>> beachGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_BEACH_GRASS);
        Holder.Reference<ConfiguredFeature<?, ?>> bushes = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_BUSHES);
        Holder.Reference<ConfiguredFeature<?, ?>> clovers = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_CLOVERS);
        Holder.Reference<ConfiguredFeature<?, ?>> pumice = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_PUMICE);
        Holder.Reference<ConfiguredFeature<?, ?>> pumiceColumn = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PUMICE_COLUMN);
        Holder.Reference<ConfiguredFeature<?, ?>> pumiceColumnLarge = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PUMICE_COLUMN_LARGE);
        Holder.Reference<ConfiguredFeature<?, ?>> brownGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_BROWN_GRASS);
        Holder.Reference<ConfiguredFeature<?, ?>> bracken = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_BRACKEN);
        Holder.Reference<ConfiguredFeature<?, ?>> giantButterbur = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_GIANT_BUTTERBUR);
        Holder.Reference<ConfiguredFeature<?, ?>> fieldBlueFescue = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FIELD_BLUE_FESCUE);
        Holder.Reference<ConfiguredFeature<?, ?>> cobweb = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_COBWEB);
        Holder.Reference<ConfiguredFeature<?, ?>> shortBulrush = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SHORT_BULRUSH);
        Holder.Reference<ConfiguredFeature<?, ?>> tallBulrush = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_TALL_BULRUSH);
        Holder.Reference<ConfiguredFeature<?, ?>> shortCattail = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SHORT_CATTAIL);
        Holder.Reference<ConfiguredFeature<?, ?>> tallCattail = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_TALL_CATTAIL);
        Holder.Reference<ConfiguredFeature<?, ?>> coastalPanicGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_COASTAL_PANIC_GRASS);
        Holder.Reference<ConfiguredFeature<?, ?>> corruptedMoss = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_CORRUPTED_MOSS);
        Holder.Reference<ConfiguredFeature<?, ?>> corruptedMossCarpet = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_CORRUPTED_MOSS_CARPET);
        Holder.Reference<ConfiguredFeature<?, ?>> deadRushes = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_DEAD_RUSHES);
        Holder.Reference<ConfiguredFeature<?, ?>> dyingGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_DYING_GRASS);
        Holder.Reference<ConfiguredFeature<?, ?>> dryGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_DRY_GRASS);
        Holder.Reference<ConfiguredFeature<?, ?>> smallDryShrub = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SMALL_DRY_SHRUB);
        Holder.Reference<ConfiguredFeature<?, ?>> duckweed = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_DUCKWEED);
        Holder.Reference<ConfiguredFeature<?, ?>> falseOatgrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_FALSE_OATGRASS);
        Holder.Reference<ConfiguredFeature<?, ?>> fallenLeaves = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_FALLEN_LEAVES);
        Holder.Reference<ConfiguredFeature<?, ?>> fallenMallornLeaves = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_FALLEN_MALLORN_LEAVES);
        Holder.Reference<ConfiguredFeature<?, ?>> fallenMirkwoodLeaves = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_FALLEN_MIRKWOOD_LEAVES);
        Holder.Reference<ConfiguredFeature<?, ?>> floatingIce = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_FLOATING_ICE);
        Holder.Reference<ConfiguredFeature<?, ?>> forestMoss = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_FOREST_MOSS);
        Holder.Reference<ConfiguredFeature<?, ?>> forestMossCarpet = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_FOREST_MOSS_CARPET);
        Holder.Reference<ConfiguredFeature<?, ?>> frozenGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_FROZEN_GRASS);
        Holder.Reference<ConfiguredFeature<?, ?>> frozenShrub = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_FROZEN_SHRUB);
        Holder.Reference<ConfiguredFeature<?, ?>> frozenTuft = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_FROZEN_SHRUB);
        Holder.Reference<ConfiguredFeature<?, ?>> greenShrub = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_GREEN_SHRUB);
        Holder.Reference<ConfiguredFeature<?, ?>> grass = registryEntryLookup.getOrThrow(VegetationFeatures.PATCH_GRASS);
        Holder.Reference<ConfiguredFeature<?, ?>> grimGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_GRIM_GRASS);
        Holder.Reference<ConfiguredFeature<?, ?>> heath = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_HEATH);
        Holder.Reference<ConfiguredFeature<?, ?>> heather = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_HEATHER);
        Holder.Reference<ConfiguredFeature<?, ?>> deadHeather = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_DEAD_HEATHER);
        Holder.Reference<ConfiguredFeature<?, ?>> dryHeather = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_DRY_HEATHER);
        Holder.Reference<ConfiguredFeature<?, ?>> redHeather = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_RED_HEATHER);
        Holder.Reference<ConfiguredFeature<?, ?>> fieldDeadNormalHeather = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.FIELD_DEAD_NORMAL_HEATHER);
        Holder.Reference<ConfiguredFeature<?, ?>> blueLavender = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_BLUE_LAVENDER);
        Holder.Reference<ConfiguredFeature<?, ?>> lavender = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_LAVENDER);
        Holder.Reference<ConfiguredFeature<?, ?>> whiteLavender = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WHITE_LAVENDER);
        Holder.Reference<ConfiguredFeature<?, ?>> lilyPad = registryEntryLookup.getOrThrow(VegetationFeatures.PATCH_WATERLILY);
        Holder.Reference<ConfiguredFeature<?, ?>> largeLilyPad = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_LARGE_LILY_PAD);
        Holder.Reference<ConfiguredFeature<?, ?>> largeFloweringLilyPad = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_LARGE_FLOWERING_LILY_PAD);
        Holder.Reference<ConfiguredFeature<?, ?>> lilyPads = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_LILY_PADS);
        Holder.Reference<ConfiguredFeature<?, ?>> floweringLilyPads = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_FLOWERING_LILY_PADS);
        Holder.Reference<ConfiguredFeature<?, ?>> smallLilyPads = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SMALL_LILY_PADS);
        Holder.Reference<ConfiguredFeature<?, ?>> smallFloweringLilyPads = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SMALL_FLOWERING_LILY_PADS);
        Holder.Reference<ConfiguredFeature<?, ?>> mirkwoodRoots = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MIRKWOOD_ROOTS);
        Holder.Reference<ConfiguredFeature<?, ?>> mirkwood = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MIRKWOOD);
        Holder.Reference<ConfiguredFeature<?, ?>> mistweed = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MISTWEED);
        Holder.Reference<ConfiguredFeature<?, ?>> moss = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MOSS);
        Holder.Reference<ConfiguredFeature<?, ?>> mossCarpet = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MOSS_CARPET);
        Holder.Reference<ConfiguredFeature<?, ?>> hogweed = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_HOGWEED);
        Holder.Reference<ConfiguredFeature<?, ?>> hobbitSunflowers = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_HOBBIT_SUNFLOWERS);
        Holder.Reference<ConfiguredFeature<?, ?>> blueBigleafHydrangea = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_BLUE_BIGLEAF_HYDRANGEA);
        Holder.Reference<ConfiguredFeature<?, ?>> pinkBigleafHydrangea = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_PINK_BIGLEAF_HYDRANGEA);
        Holder.Reference<ConfiguredFeature<?, ?>> whiteBigleafHydrangea = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WHITE_BIGLEAF_HYDRANGEA);
        Holder.Reference<ConfiguredFeature<?, ?>> campion = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_CAMPION);
        Holder.Reference<ConfiguredFeature<?, ?>> reeds = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_REEDS);
        Holder.Reference<ConfiguredFeature<?, ?>> shortReeds = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SHORT_REEDS);
        Holder.Reference<ConfiguredFeature<?, ?>> stickySnow = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_STICKY_SNOW);
        Holder.Reference<ConfiguredFeature<?, ?>> strawBerries = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_STRAWBERRY_BUSH);
        Holder.Reference<ConfiguredFeature<?, ?>> scorchedGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SCORCHED_GRASS);
        Holder.Reference<ConfiguredFeature<?, ?>> scorchedShrub = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SCORCHED_SHRUB);
        Holder.Reference<ConfiguredFeature<?, ?>> scorchedTuft = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SCORCHED_TUFT);
        Holder.Reference<ConfiguredFeature<?, ?>> sedum = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SEDUM);
        Holder.Reference<ConfiguredFeature<?, ?>> sedumOrange = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SEDUM_ORANGE);
        Holder.Reference<ConfiguredFeature<?, ?>> sedumRed = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SEDUM_RED);
        Holder.Reference<ConfiguredFeature<?, ?>> sedumYellow = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SEDUM_YELLOW);
        Holder.Reference<ConfiguredFeature<?, ?>> sedums = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SEDUMS);
        Holder.Reference<ConfiguredFeature<?, ?>> shriveledShrubs = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SHRIVELED_SHRUB);
        Holder.Reference<ConfiguredFeature<?, ?>> largeShriveledShrubs = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_LARGE_SHRIVELED_SHRUB);
        Holder.Reference<ConfiguredFeature<?, ?>> rushes = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_RUSHES);
        Holder.Reference<ConfiguredFeature<?, ?>> shortRushes = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SHORT_RUSHES);
        Holder.Reference<ConfiguredFeature<?, ?>> spiderEggs = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SPIDER_EGGS);
        Holder.Reference<ConfiguredFeature<?, ?>> tanShrub = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_TAN_SHRUB);
        Holder.Reference<ConfiguredFeature<?, ?>> yellowTrollius = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_YELLOW_TROLLIUS);
        Holder.Reference<ConfiguredFeature<?, ?>> tallGrass = registryEntryLookup.getOrThrow(VegetationFeatures.PATCH_TALL_GRASS);
        Holder.Reference<ConfiguredFeature<?, ?>> temperateGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_TEMPERATE_GRASS);
        Holder.Reference<ConfiguredFeature<?, ?>> sweetBerries = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SWEET_BERRY_BUSH);
        Holder.Reference<ConfiguredFeature<?, ?>> toughBerries = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_TOUGH_BERRY_BUSH);
        Holder.Reference<ConfiguredFeature<?, ?>> tuftGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_TUFT_GRASS);
        Holder.Reference<ConfiguredFeature<?, ?>> sparseGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_SPARSE_GRASS);
        Holder.Reference<ConfiguredFeature<?, ?>> wheatGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WHEAT_GRASS);
        Holder.Reference<ConfiguredFeature<?, ?>> meadowGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MEADOW_GRASS);
        Holder.Reference<ConfiguredFeature<?, ?>> mixedWildWheat = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MIXED_WILD_WHEAT);
        Holder.Reference<ConfiguredFeature<?, ?>> wildGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_GRASS);
        Holder.Reference<ConfiguredFeature<?, ?>> wilderGrass = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILDER_GRASS);
        Holder.Reference<ConfiguredFeature<?, ?>> yellowFlower = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_YELLOW_FLOWER);

        Holder.Reference<ConfiguredFeature<?, ?>> nettles = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_NETTLES);
        Holder.Reference<ConfiguredFeature<?, ?>> thistle = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_THISTLE);
        Holder.Reference<ConfiguredFeature<?, ?>> mordorBrambles = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MORDOR_BRAMBLES);

        Holder.Reference<ConfiguredFeature<?, ?>> bolete = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_BROWN_BOLETE);
        Holder.Reference<ConfiguredFeature<?, ?>> boleteTiller = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_BROWN_BOLETE_TILLER);
        Holder.Reference<ConfiguredFeature<?, ?>> morsel = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MORSEL);
        Holder.Reference<ConfiguredFeature<?, ?>> morselTiller = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_MORSEL_TILLER);
        Holder.Reference<ConfiguredFeature<?, ?>> whiteMushroom = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WHITE_MUSHROOM);
        Holder.Reference<ConfiguredFeature<?, ?>> whiteMushroomTiller = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WHITE_MUSHROOM_TILLER);

        Holder.Reference<ConfiguredFeature<?, ?>> wildBeetroot = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_BEETROOT);
        Holder.Reference<ConfiguredFeature<?, ?>> wildBellPepper = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_BELL_PEPPER);
        Holder.Reference<ConfiguredFeature<?, ?>> wildCarrot = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_CARROT);
        Holder.Reference<ConfiguredFeature<?, ?>> wildCucumber = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_CUCUMBER);
        Holder.Reference<ConfiguredFeature<?, ?>> wildFlax = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_FLAX);
        Holder.Reference<ConfiguredFeature<?, ?>> wildGarlic = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_GARLIC);
        Holder.Reference<ConfiguredFeature<?, ?>> wildLeek = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_LEEK);
        Holder.Reference<ConfiguredFeature<?, ?>> wildLettuce = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_LETTUCE);
        Holder.Reference<ConfiguredFeature<?, ?>> wildOnion = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_ONION);
        Holder.Reference<ConfiguredFeature<?, ?>> wildPipeweed = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_PIPEWEED);
        Holder.Reference<ConfiguredFeature<?, ?>> wildPotato = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_POTATO);
        Holder.Reference<ConfiguredFeature<?, ?>> wildTomato = registryEntryLookup.getOrThrow(ModVegetationConfiguredFeatures.PATCH_WILD_TOMATO);

        PlacementUtils.register(featureRegisterable, WATER_DELTA, waterDelta, common, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, ABUNDANT_WATER_DELTA, waterDelta, abundant, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWER_ALLIUM, flowerAllium, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWER_ATHELAS, flowerAthelas, wildBushRarity, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWER_AZURE_BLUET, flowerAzureBluet, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWER_AZURE_BLUET_RARE, flowerAzureBluet, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWER_BLUE_GENTIAN, flowerBlueGentian, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWER_BLUE_ORCHID, flowerBlueOrchid, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWER_MEADOW, flowerMeadow, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWER_DORWINION, flowerDorwinion, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWER_CORNFLOWER, flowerCornflower, veryRare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWER_CORNFLOWER_COMMON, flowerCornflower, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWER_GREEN_JEWEL, flowerGreenJewel, veryRare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWER_LILAC, flowerLilac, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWER_LEBENNIN, flowerLebennin, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWER_LOSSARNACH, flowerLossarnach, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWER_LOSSARNACH_COMMON, flowerLossarnach, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWER_MALLOS, flowerMallos, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWER_NOBLEWHITE, flowerNobleWhite, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWER_POPPY, flowerPoppy, uncommon, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWER_ELANOR, flowerElanor, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWER_NIPHREDIL, flowerNiphredil, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWER_SIMBELMYNE, flowerSimbelmyne, veryRare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWER_ROSE_BUSH, flowerRoseBush, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWER_YELLOW, yellowFlower, occasional, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, RARE_FLOWER_YELLOW, yellowFlower, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

        PlacementUtils.register(featureRegisterable, FLOWERS_LIGHT_BLUE, flowersLightBlue, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, RARE_FLOWERS_LIGHT_BLUE, flowersLightBlue, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWERS_MAGENTA, flowersMagenta, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWERS_ORANGE, flowersOrange, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWERS_PINK, flowersPink, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWERS_PURPLE, flowersPurple, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWERS_RED, flowersRed, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWERS_WHITE, flowersWhite, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FLOWERS_YELLOW, flowersYellow, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

        PlacementUtils.register(featureRegisterable, FIELD_HEATHER, fieldHeather, common, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FIELD_DRY_HEATHER, fieldDryHeather, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FIELD_LAVENDER, fieldLavender, abundant, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FIELD_WILD_WHEAT, fieldWildWeather, common, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FIELD_DEAD_NORMAL_HEATHER, fieldDeadNormalHeather, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FIELD_SPARSE_DEAD_NORMAL_HEATHER, fieldDeadNormalHeather, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FIELD_BLUE_FESCUE, fieldBlueFescue, occasional, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

        PlacementUtils.register(featureRegisterable, MIRKWOOD_VINES, mirkwoodVines, CountPlacement.of(188), InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(64), VerticalAnchor.absolute(256)), EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.anyOf(BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.matchesTag(BlockTags.LEAVES)),
                        BlockPredicate.ONLY_IN_AIR_PREDICATE, 5), RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, WILLOW_VINES, willowVines, CountPlacement.of(256), InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(64), VerticalAnchor.absolute(256)), EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.anyOf(BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.matchesTag(BlockTags.LEAVES)),
                        BlockPredicate.ONLY_IN_AIR_PREDICATE, 5), RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BiomeFilter.biome());

        PlacementUtils.register(featureRegisterable, AZALEA_GROWTH, growthAzalea, CountPlacement.of(UniformInt.of(204, 255)), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                InSquarePlacement.spread(), SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13), BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, DRY_GROWTH, growthDry, CountPlacement.of(UniformInt.of(16, 32)), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, IVY_GROWTH, growthIvy, CountPlacement.of(UniformInt.of(16, 30)), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FROZEN_GROWTH, growthFrozen, CountPlacement.of(UniformInt.of(16, 32)), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, GREEN_GROWTH, growthGreen, CountPlacement.of(UniformInt.of(16, 31)), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, THORNY_GROWTH, thornyGrowth, CountPlacement.of(UniformInt.of(16, 31)), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, LILAC_FLOWER_GROWTH, growthLilacFlower, CountPlacement.of(UniformInt.of(16, 31)), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, RED_FLOWER_GROWTH, growthRedFlower, CountPlacement.of(UniformInt.of(16, 32)), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, YELLOW_FLOWER_GROWTH, growthYellowFlower, CountPlacement.of(UniformInt.of(15, 30)), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PINK_FLOWER_GROWTH, growthPinkFlower, CountPlacement.of(UniformInt.of(16, 31)), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, WHITE_FLOWER_GROWTH, growthWhiteFlower, CountPlacement.of(UniformInt.of(15, 30)), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_WILD_FLOWERS, wildFlowers, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_LEAF_LITTER, leafLitter,
                CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_FIREFLY_BUSH_SWAMP, fireflyBush,
                RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_FIREFLY_BUSH_SWAMP_NEAR_WATER, fireflyBush,
                CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome(),
                BlockPredicateFilter.forPredicate(fireflyBushNearWaterPredicate()));

        PlacementUtils.register(featureRegisterable, PATCH_BAMBOO, bamboo, common, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_BASALT, basalt, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_BASALT_RARE, basalt, veryRare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_BLACKSTONE, blackStone, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_COMMON_BEACH_GRASS, beachGrass, common, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_BEACH_GRASS, beachGrass, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_BUSHES, bushes, common, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_CLOVERS, clovers, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_PUMICE, pumice, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_PUMICE_SPARSE, pumice, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PUMICE_COLUMN, pumiceColumn, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PUMICE_COLUMN_RARE, pumiceColumn, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PUMICE_COLUMN_LARGE, pumiceColumnLarge, veryRare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_BRACKEN, bracken, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_GIANT_BUTTERBUR, giantButterbur, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_COBWEB, cobweb, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_BROWN_GRASS, brownGrass, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_SHORT_CATTAIL, shortCattail, occasional, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_TALL_CATTAIL, tallCattail, occasional, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_SHORT_BULRUSH, shortBulrush, occasional, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_TALL_BULRUSH, tallBulrush, occasional, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_COASTAL_PANIC_GRASS, coastalPanicGrass, veryRare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_CORRUPTED_MOSS_CARPET, corruptedMossCarpet, uncommon, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_CORRUPTED_MOSS, corruptedMoss, CountPlacement.of(UniformInt.of(15, 25)), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_DEAD_RUSHES, deadRushes, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_DUCKWEED, duckweed, overflowing, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_DYING_GRASS, dyingGrass, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_DRY_GRASS, dryGrass, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_DRY_GRASS_DESERT, dryGrass,
                RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_VERY_RARE_DRY_GRASS, dryGrass, veryRare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_SMALL_DRY_SHRUB, smallDryShrub, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_FALSE_OATGRASS, falseOatgrass, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_FALLEN_LEAVES, fallenLeaves, common, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_FALLEN_MALLORN_LEAVES, fallenMallornLeaves, common, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_FALLEN_MIRKWOOD_LEAVES, fallenMirkwoodLeaves, common, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_FLOATING_ICE, floatingIce, abundant, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_FOREST_MOSS, forestMoss, CountPlacement.of(UniformInt.of(15, 30)), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_FOREST_MOSS_CARPET, forestMossCarpet, uncommon, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_FROZEN_GRASS, frozenGrass, uncommon, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_FROZEN_SHRUB, frozenShrub, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_FROZEN_TUFT, frozenTuft, uncommon, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_GRASS, grass, overflowing, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_GRIM_GRASS, grimGrass, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_GREEN_SHRUB, greenShrub, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_RARE_FOREST_MOSS, forestMoss, CountPlacement.of(UniformInt.of(5, 10)), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_HEATH, heath, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_COMMON_HEATH, heath, uncommon, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_SPARSE_HEATH, heath, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_HEATHER, heather, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_COMMON_HEATHER, heather, uncommon, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_RARE_HEATHER, heather, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_DEAD_HEATHER, deadHeather, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_DRY_HEATHER, dryHeather, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_RED_HEATHER, redHeather, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_BLUE_LAVENDER, blueLavender, uncommon, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_SPARSE_BLUE_LAVENDER, blueLavender, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_RARE_BLUE_LAVENDER, blueLavender, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_VERY_RARE_BLUE_LAVENDER, blueLavender, wildBushRarity, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_LAVENDER, lavender, uncommon, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_SPARSE_LAVENDER, lavender, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_RARE_LAVENDER, lavender, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_VERY_RARE_LAVENDER, lavender, wildBushRarity, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_WHITE_LAVENDER, whiteLavender, uncommon, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_SCARCE_WHITE_LAVENDER, whiteLavender, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_RARE_WHITE_LAVENDER, whiteLavender, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_VERY_RARE_WHITE_LAVENDER, whiteLavender, wildBushRarity, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

        PlacementUtils.register(featureRegisterable, PATCH_LILY_PAD, lilyPad, common, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

        PlacementUtils.register(featureRegisterable, PATCH_LARGE_LILY_PAD, largeLilyPad, common, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_LARGE_FLOWERING_LILY_PAD, largeFloweringLilyPad, common, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

        PlacementUtils.register(featureRegisterable, PATCH_LILY_PADS, lilyPads, common, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_FLOWERING_LILY_PADS, floweringLilyPads, common, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

        PlacementUtils.register(featureRegisterable, PATCH_SMALL_LILY_PADS, smallLilyPads, common, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_SMALL_FLOWERING_LILY_PADS, smallFloweringLilyPads, common, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

        PlacementUtils.register(featureRegisterable, PATCH_MIRKWOOD, mirkwood, RarityFilter.onAverageOnceEvery(25), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_MIRKWOOD_ROOTS, mirkwoodRoots, sparse, CountPlacement.of(UniformInt.of(0, 2)));
        PlacementUtils.register(featureRegisterable, PATCH_MISTWEED, mistweed, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_MOSS, moss, CountPlacement.of(UniformInt.of(15, 30)), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_MOSS_CARPET, mossCarpet, uncommon, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_SCORCHED_GRASS, scorchedGrass, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_SCORCHED_SHRUB, scorchedShrub, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_SCORCHED_TUFT, scorchedTuft, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_COMMON_SCORCHED_GRASS, scorchedGrass, common, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_COMMON_SCORCHED_SHRUB, scorchedShrub, common, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_COMMON_SCORCHED_TUFT, scorchedTuft, common, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_SEDUM, sedum, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_SEDUM_ORANGE, sedumOrange, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_SEDUM_RED, sedumRed, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_SEDUM_YELLOW, sedumYellow, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_SEDUMS, sedums, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_SHRIVELED_SHRUB, shriveledShrubs, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_LARGE_SHRIVELED_SHRUB, largeShriveledShrubs, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_RUSHES, rushes, common, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_SHORT_RUSHES, shortRushes, common, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_SPIDER_EGGS, spiderEggs, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_STRAWBERRY_BUSH, strawBerries, veryRare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_YELLOW_TROLLIUS, yellowTrollius, veryRare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_TALL_GRASS, tallGrass, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_COMMON_TALL_GRASS, tallGrass, common, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_TEMPERATE_GRASS, temperateGrass, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_TAN_SHRUB, tanShrub, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_SWEET_BERRY_BUSH_RARE, sweetBerries, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_TOUGH_BERRY_BUSH, toughBerries, RarityFilter.onAverageOnceEvery(48), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_COMMON_TOUGH_BERRY_BUSH, toughBerries, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_TOUGH_BERRY_BUSH_RARE, tuftGrass, RarityFilter.onAverageOnceEvery(320), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_TUFT_GRASS, tuftGrass, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_SPARSE_GRASS, sparseGrass, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_HOGWEED, hogweed, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_HOBBIT_SUNFLOWERS, hobbitSunflowers, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_BLUE_BIGLEAF_HYDRANGEA, blueBigleafHydrangea, veryRare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_PINK_BIGLEAF_HYDRANGEA, pinkBigleafHydrangea, veryRare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_WHITE_BIGLEAF_HYDRANGEA, whiteBigleafHydrangea, veryRare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_CAMPION, campion, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_REEDS, reeds, CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_SHORT_REEDS, shortReeds, CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_STICKY_SNOW, stickySnow, CountPlacement.of(UniformInt.of(48, 80)), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_WHEAT_GRASS, wheatGrass, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_SPARSE_WHEAT_GRASS, wheatGrass, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_COMMON_WHEAT_GRASS, wheatGrass, uncommon, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_MEADOW_GRASS, meadowGrass, sparse, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_MIXED_WILD_WHEAT, mixedWildWheat, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_WILD_GRASS, wildGrass, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_OCCASIONAL_WILD_GRASS, wildGrass, occasional, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_WILDER_GRASS, wilderGrass, scarce, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_RARE_WILDER_GRASS, wilderGrass, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

        PlacementUtils.register(featureRegisterable, PATCH_NETTLES, nettles, veryRare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_THISTLE, thistle, veryRare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_MORDOR_BRAMBLES, mordorBrambles, veryRare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

        PlacementUtils.register(featureRegisterable, PATCH_BROWN_BOLETE, bolete, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_BROWN_BOLETE_TILLER, boleteTiller, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_MORSEL, morsel, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_MORSEL_TILLER, morselTiller, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_WHITE_MUSHROOM, whiteMushroom, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_WHITE_MUSHROOM_TILLER, whiteMushroomTiller, rare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_RARE_MORSEL, morsel, veryRare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_RARE_MORSEL_TILLER, morselTiller, veryRare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_RARE_WHITE_MUSHROOM, whiteMushroom, veryRare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_RARE_WHITE_MUSHROOM_TILLER, whiteMushroomTiller, veryRare, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

        PlacementUtils.register(featureRegisterable, PATCH_WILD_BEETROOT, wildBeetroot, wildBushRarity, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_WILD_BELL_PEPPER, wildBellPepper, wildBushRarity, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_WILD_CARROT, wildCarrot, wildBushRarity, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_WILD_CUCUMBER, wildCucumber, wildBushRarity, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_WILD_FLAX, wildFlax, wildBushRarity, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_WILD_GARLIC, wildGarlic, wildBushRarity, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_WILD_LEEK, wildLeek, wildBushRarity, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_WILD_LETTUCE, wildLettuce, wildBushRarity, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_WILD_ONION, wildOnion, wildBushRarity, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_WILD_PIPEWEED, wildPipeweed, wildBushRarity, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_WILD_POTATO, wildPotato, wildBushRarity, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PATCH_WILD_TOMATO, wildTomato, wildBushRarity, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, name));
    }

    public static ResourceKey<PlacedFeature> vanillaRegisterKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.parse(name));
    }

    private static BlockPredicate fireflyBushNearWaterPredicate() {
        return BlockPredicate.allOf(
                BlockPredicate.ONLY_IN_AIR_PREDICATE,
                BlockPredicate.wouldSurvive(
                        net.sevenstars.middleearth.block.registration.ModNatureBlocks.FIREFLY_BUSH.defaultBlockState(),
                        BlockPos.ZERO),
                BlockPredicate.anyOf(
                        BlockPredicate.matchesFluids(new BlockPos(1, -1, 0), Fluids.WATER, Fluids.FLOWING_WATER),
                        BlockPredicate.matchesFluids(new BlockPos(-1, -1, 0), Fluids.WATER, Fluids.FLOWING_WATER),
                        BlockPredicate.matchesFluids(new BlockPos(0, -1, 1), Fluids.WATER, Fluids.FLOWING_WATER),
                        BlockPredicate.matchesFluids(new BlockPos(0, -1, -1), Fluids.WATER, Fluids.FLOWING_WATER)));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
