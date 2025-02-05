package net.sevenstars.middleearth.world.features.vegetation;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.ModBlocks;
import net.sevenstars.middleearth.block.ModNatureBlocks;
import net.sevenstars.middleearth.block.StoneBlockSets;
import net.sevenstars.middleearth.block.WoodBlockSets;
import net.sevenstars.middleearth.item.ModResourceItems;
import net.sevenstars.middleearth.world.features.columns.CaveColumnFeatureConfig;
import net.sevenstars.middleearth.world.features.underground.CavesConfiguredFeatures;
import net.sevenstars.middleearth.world.gen.ModFeatures;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.dynamic.Range;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BlockFilterPlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.DualNoiseBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.List;

public class ModVegetationConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> WATER_DELTA = registerKey("water_delta");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_ALLIUM = registerKey("flower_allium");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_AZURE_BLUET = registerKey("flower_azure_bluet");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_CORNFLOWER = registerKey("flower_cornflower");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_DORWINION = registerKey("flower_dorwinion");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_GREEN_JEWEL = registerKey("flower_green_jewel");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_LILAC = registerKey("flower_lilac");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_LEBENNIN = registerKey("flower_lebennin");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_LOSSARNACH = registerKey("flower_lossarnach");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_MALLOS = registerKey("flower_mallos");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_POPPY = registerKey("flower_poppy");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_ELANOR = registerKey("flower_elanor");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_ROSE_BUSH = registerKey("flower_rose_bush");

    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWERS_LIGHT_BLUE = registerKey("flowers_light_blue");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWERS_MAGENTA = registerKey("flowers_magenta");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWERS_ORANGE = registerKey("flowers_orange");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWERS_PINK = registerKey("flowers_pink");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWERS_PURPLE = registerKey("flowers_purple");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWERS_RED = registerKey("flowers_red");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWERS_WHITE = registerKey("flowers_white");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWERS_YELLOW = registerKey("flowers_yellow");

    // region FIELDS
    public static final RegistryKey<ConfiguredFeature<?, ?>> FIELD_HEATHER = registerKey("field_heather");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FIELD_LAVENDER = registerKey("field_lavender");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FIELD_WILD_WHEAT = registerKey("field_wild_wheat");

    // endregion
    public static final RegistryKey<ConfiguredFeature<?, ?>> MIRKWOOD_VINES = registerKey("mirkwood_vines");

    // region GROWTH
    public static final RegistryKey<ConfiguredFeature<?, ?>> AZALEA_GROWTH = registerKey("azalea_growth");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DRY_GROWTH = registerKey("dry_growth");
    public static final RegistryKey<ConfiguredFeature<?, ?>> IVY_GROWTH = registerKey("ivy_growth");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_FROZEN_GROWTH = registerKey("patch_frozen_growth");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_GREEN_GROWTH = registerKey("patch_green_growth");
    public static final RegistryKey<ConfiguredFeature<?, ?>> LILAC_FLOWER_GROWTH = registerKey("lilac_flower_growth");
    public static final RegistryKey<ConfiguredFeature<?, ?>> RED_FLOWER_GROWTH = registerKey("red_flower_growth");
    public static final RegistryKey<ConfiguredFeature<?, ?>> YELLOW_FLOWER_GROWTH = registerKey("yellow_flower_growth");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PINK_FLOWER_GROWTH = registerKey("pink_flower_growth");
    public static final RegistryKey<ConfiguredFeature<?, ?>> WHITE_FLOWER_GROWTH = registerKey("white_flower_growth");
    public static final RegistryEntryList.Direct<Block> BLOCKS_GROWTH = RegistryEntryList.of(Block::getRegistryEntry,
            Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.MOSSY_COBBLESTONE,
            Blocks.MOSS_BLOCK, Blocks.CLAY, Blocks.COBBLESTONE, Blocks.DEEPSLATE, Blocks.OAK_LOG, Blocks.BIRCH_LOG, Blocks.DARK_OAK_LOG, Blocks.SPRUCE_LOG,
            WoodBlockSets.BEECH.log(), WoodBlockSets.MAPLE.log(), WoodBlockSets.SILVER_MAPLE.log(), WoodBlockSets.HOLLY.log());
    // endregion

    // region FOLIAGE
    public static final  RegistryEntryList<Block> BLOCKS_MOSS = RegistryEntryList.of(Block::getRegistryEntry,
            Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.MOSSY_COBBLESTONE, Blocks.COBBLESTONE,
            Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.MOSS_BLOCK, Blocks.SPRUCE_LOG, Blocks.OAK_LOG, Blocks.BIRCH_LOG, Blocks.DARK_OAK_LOG,
            WoodBlockSets.PINE.log(), WoodBlockSets.LARCH.log(), WoodBlockSets.BEECH.log(), WoodBlockSets.MAPLE.log(),
            WoodBlockSets.SILVER_MAPLE.log(), WoodBlockSets.BLACK_LEBETHRON.log(), WoodBlockSets.WHITE_LEBETHRON.log());

    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_BASALT = registerKey("patch_basalt");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_BLACKSTONE = registerKey("patch_blackstone");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_PUMICE = registerKey("patch_pumice");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_COBWEB = registerKey("patch_cobweb");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_SPIDER_EGGS = registerKey("patch_spider_eggs");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PUMICE_COLUMN = registerKey("pumice_column");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PUMICE_COLUMN_LARGE = registerKey("pumice_column_large");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_BEACH_GRASS = registerKey("patch_beach_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_BRACKEN = registerKey("patch_bracken");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_BROWN_GRASS = registerKey("patch_brown_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_SHORT_BULRUSH = registerKey("patch_short_bulrush");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_TALL_BULRUSH = registerKey("patch_tall_bulrush");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_SHORT_CATTAIL = registerKey("patch_short_cattail");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_TALL_CATTAIL = registerKey("patch_tall_cattail");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_COASTAL_PANIC_GRASS = registerKey("patch_coastal_panic_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_CORRUPTED_MOSS = registerKey("patch_corrupted_moss");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_CORRUPTED_MOSS_CARPET = registerKey("patch_corrupted_moss_carpet");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_DEAD_RUSHES = registerKey("patch_dead_rushes");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_DRY_GRASS = registerKey("patch_dry_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_SMALL_DRY_SHRUB = registerKey("patch_small_dry_shrub");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_DUCKWEED = registerKey("patch_duckweed");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_DYING_GRASS = registerKey("patch_dying_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_FALLEN_LEAVES = registerKey("patch_fallen_leaves");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_FALLEN_MALLORN_LEAVES = registerKey("patch_fallen_mallorn_leaves");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_FALLEN_MIRKWOOD_LEAVES = registerKey("patch_fallen_mirkwood_leaves");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_FALSE_OATGRASS = registerKey("patch_false_oatgrass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_FLOATING_ICE = registerKey("patch_floating_ice");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_FOREST_MOSS = registerKey("patch_forest_moss");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_FOREST_MOSS_CARPET = registerKey("patch_forest_moss_carpet");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_FROZEN_GRASS = registerKey("patch_frozen_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_FROZEN_SHRUB = registerKey("patch_frozen_shrub");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_FROZEN_TUFT = registerKey("patch_frozen_tuft");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_GREEN_SHRUB = registerKey("patch_green_shrub");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_GRIM_GRASS = registerKey("patch_grim_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_HEATH = registerKey("patch_heath");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_HEATHER = registerKey("patch_heather");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_DEAD_HEATHER = registerKey("patch_dead_heather");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_DRY_HEATHER = registerKey("patch_dry_heather");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_HOROKAKA = registerKey("patch_horokaka");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_GIANT_HOROKAKA = registerKey("patch_giant_horokaka");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_RED_HEATHER = registerKey("patch_red_heather");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_LAVENDER = registerKey("patch_lavender");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_REEDS = registerKey("patch_reeds");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_STICKY_SNOW = registerKey("patch_sticky_snow");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_LILY_PADS = registerKey("patch_lily_pads");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_SMALL_LILY_PADS = registerKey("patch_small_lily_pads");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_SMALL_FLOWERING_LILY_PADS = registerKey("patch_small_flowering_lily_pads");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_MIRKWOOD = registerKey("patch_mirkwood");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_MIRKWOOD_ROOTS = registerKey("patch_mirkwood_roots");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_MOSS = registerKey("patch_moss");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_MOSS_CARPET = registerKey("patch_moss_carpet");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_SCORCHED_GRASS = registerKey("patch_scorched_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_SCORCHED_SHRUB = registerKey("patch_scorched_shrub");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_SCORCHED_TUFT = registerKey("patch_scorched_tuft");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_SEDUM = registerKey("patch_sedum");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_SEDUM_YELLOW = registerKey("patch_sedum_yellow");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_SHRIVELED_SHRUB = registerKey("patch_shriveled_shrub");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_STRAWBERRY_BUSH = registerKey("patch_strawberry_bush");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_TAN_SHRUB = registerKey("patch_tan_shrub");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_YELLOW_TROLLIUS = registerKey("patch_yellow_trollius");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_TEMPERATE_GRASS = registerKey("patch_temperate_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_TOUGH_BERRY_BUSH = registerKey("patch_tough_berry_bush");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_TUFT_GRASS = registerKey("patch_tuft_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WHEAT_GRASS = registerKey("patch_wheat_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_MIXED_WILD_WHEAT = registerKey("patch_mixed_wild_wheat");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WILD_GRASS = registerKey("patch_wild_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WILDER_GRASS = registerKey("patch_wilder_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_YELLOW_FLOWER = registerKey("patch_yellow_flower");
    // endregion

    // region MUSHROOMS
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_BROWN_BOLETE = registerKey("patch_brown_bolete");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_BROWN_BOLETE_TILLER = registerKey("patch_brown_bolete_tiller");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_MORSEL = registerKey("patch_morsel");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_MORSEL_TILLER = registerKey("patch_morsel_tiller");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WHITE_MUSHROOM = registerKey("patch_white_mushroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WHITE_MUSHROOM_TILLER = registerKey("patch_white_mushroom_tiller");

    // endregion

    // region WILD CROPS
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WILD_BEETROOT = registerKey("patch_wild_beetroot");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WILD_BELL_PEPPER = registerKey("patch_wild_bell_pepper");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WILD_CARROT = registerKey("patch_wild_carrot");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WILD_CUCUMBER = registerKey("patch_wild_cucumber");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WILD_FLAX = registerKey("patch_wild_flax");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WILD_GARLIC = registerKey("patch_wild_garlic");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WILD_LEEK = registerKey("patch_wild_leek");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WILD_LETTUCE = registerKey("patch_wild_lettuce");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WILD_ONION = registerKey("patch_wild_onion");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WILD_PIPEWEED = registerKey("patch_wild_pipeweed");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WILD_POTATO = registerKey("patch_wild_potato");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WILD_TOMATO = registerKey("patch_wild_tomato");
    // endregion


    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        ConfiguredFeatures.register(featureRegisterable, WATER_DELTA, ModFeatures.DELTA_FEATURE,
                new DeltaFeatureConfig(Blocks.WATER.getDefaultState(), Blocks.GRASS_BLOCK.getDefaultState(), UniformIntProvider.create(3, 7), UniformIntProvider.create(0, 2)));

        ConfiguredFeatures.register(featureRegisterable, FIELD_HEATHER, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.HEATHER))));

        ConfiguredFeatures.register(featureRegisterable, FIELD_LAVENDER, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.LAVENDER))));

        ConfiguredFeatures.register(featureRegisterable, FIELD_WILD_WHEAT, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.RANDOM_PATCH,
                        new RandomPatchFeatureConfig(8, 3, 2,
                                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                                        new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                                                .add(ModNatureBlocks.TALL_WILD_WHEAT.getDefaultState(), 1)
                                                .add(ModNatureBlocks.WILD_WHEAT.getDefaultState(), 3))
                                )))));

        ConfiguredFeatures.register(featureRegisterable, MIRKWOOD_VINES, ModFeatures.MIRKWOOD_VINE, new DefaultFeatureConfig());

        ConfiguredFeatures.register(featureRegisterable, FLOWER_ALLIUM, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.ALLIUM))));
        ConfiguredFeatures.register(featureRegisterable, FLOWER_AZURE_BLUET, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.AZURE_BLUET))));
        ConfiguredFeatures.register(featureRegisterable, FLOWER_CORNFLOWER, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.CORNFLOWER))));
        ConfiguredFeatures.register(featureRegisterable, FLOWER_GREEN_JEWEL, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.GREEN_JEWEL_CORNFLOWER))));
        ConfiguredFeatures.register(featureRegisterable, FLOWER_LILAC, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.LILAC))));
        ConfiguredFeatures.register(featureRegisterable, FLOWER_LEBENNIN, Feature.FLOWER,
                new RandomPatchFeatureConfig(96, 6, 2, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(new DualNoiseBlockStateProvider(new Range<>(1, 3), new DoublePerlinNoiseSampler.NoiseParameters(-10, 1.0), 1.0f, 2143L,
                                new DoublePerlinNoiseSampler.NoiseParameters(-3, 1.0), 1.0f,
                                List.of(Blocks.TALL_GRASS.getDefaultState(), Blocks.RED_TULIP.getDefaultState(), Blocks.POPPY.getDefaultState(), Blocks.AZURE_BLUET.getDefaultState(),
                                        ModNatureBlocks.LIGHT_BLUE_FLOWERS.getDefaultState(), Blocks.CORNFLOWER.getDefaultState(), Blocks.OXEYE_DAISY.getDefaultState(), Blocks.SHORT_GRASS.getDefaultState()))))));
        ConfiguredFeatures.register(featureRegisterable, FLOWER_LOSSARNACH, Feature.FLOWER,
                new RandomPatchFeatureConfig(96, 6, 2, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(new DualNoiseBlockStateProvider(new Range<>(1, 3), new DoublePerlinNoiseSampler.NoiseParameters(-10, 1.0), 1.0f, 2241L,
                                new DoublePerlinNoiseSampler.NoiseParameters(-3, 1.0), 1.0f,
                                List.of(Blocks.TALL_GRASS.getDefaultState(), Blocks.RED_TULIP.getDefaultState(), Blocks.POPPY.getDefaultState(), Blocks.PINK_TULIP.getDefaultState(),
                                        Blocks.ROSE_BUSH.getDefaultState(), Blocks.ORANGE_TULIP.getDefaultState(), Blocks.OXEYE_DAISY.getDefaultState(),
                                        Blocks.LILY_OF_THE_VALLEY.getDefaultState(), Blocks.ALLIUM.getDefaultState(), Blocks.SHORT_GRASS.getDefaultState()))))));
        ConfiguredFeatures.register(featureRegisterable, FLOWER_DORWINION, Feature.FLOWER,
                new RandomPatchFeatureConfig(96, 6, 2, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(new DualNoiseBlockStateProvider(new Range<>(1, 3), new DoublePerlinNoiseSampler.NoiseParameters(-10, 1.0), 1.0f, 2345L,
                                new DoublePerlinNoiseSampler.NoiseParameters(-3, 1.0), 1.0f,
                                List.of(Blocks.TALL_GRASS.getDefaultState(), Blocks.PINK_TULIP.getDefaultState(), Blocks.WHITE_TULIP.getDefaultState(), Blocks.AZURE_BLUET.getDefaultState(),
                                        Blocks.ALLIUM.getDefaultState(), Blocks.CORNFLOWER.getDefaultState(), Blocks.LILY_OF_THE_VALLEY.getDefaultState(), Blocks.SHORT_GRASS.getDefaultState()))))));
        ConfiguredFeatures.register(featureRegisterable, FLOWER_MALLOS, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.MALLOS))));
        ConfiguredFeatures.register(featureRegisterable, FLOWER_POPPY, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.POPPY))));
        ConfiguredFeatures.register(featureRegisterable, FLOWER_ELANOR, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.ELANOR))));
        ConfiguredFeatures.register(featureRegisterable, FLOWER_ROSE_BUSH, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.ROSE_BUSH))));

        ConfiguredFeatures.register(featureRegisterable, FLOWERS_LIGHT_BLUE, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.LIGHT_BLUE_FLOWERS))));
        ConfiguredFeatures.register(featureRegisterable, FLOWERS_MAGENTA, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.MAGENTA_FLOWERS))));
        ConfiguredFeatures.register(featureRegisterable, FLOWERS_ORANGE, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.ORANGE_FLOWERS))));
        ConfiguredFeatures.register(featureRegisterable, FLOWERS_PINK, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.PINK_FLOWERS))));
        ConfiguredFeatures.register(featureRegisterable, FLOWERS_PURPLE, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.PURPLE_FLOWERS))));
        ConfiguredFeatures.register(featureRegisterable, FLOWERS_RED, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.RED_FLOWERS))));
        ConfiguredFeatures.register(featureRegisterable, FLOWERS_WHITE, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WHITE_FLOWERS))));
        ConfiguredFeatures.register(featureRegisterable, FLOWERS_YELLOW, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.YELLOW_FLOWERS))));


        ConfiguredFeatures.register(featureRegisterable, AZALEA_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock)ModNatureBlocks.AZALEA_FLOWER_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        ConfiguredFeatures.register(featureRegisterable, DRY_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock)ModNatureBlocks.DRY_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        ConfiguredFeatures.register(featureRegisterable, IVY_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock)ModNatureBlocks.IVY_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        ConfiguredFeatures.register(featureRegisterable, PATCH_FROZEN_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock)ModNatureBlocks.FROZEN_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        ConfiguredFeatures.register(featureRegisterable, PATCH_GREEN_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock)ModNatureBlocks.GREEN_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        ConfiguredFeatures.register(featureRegisterable, LILAC_FLOWER_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock)ModNatureBlocks.LILAC_FLOWER_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        ConfiguredFeatures.register(featureRegisterable, RED_FLOWER_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock)ModNatureBlocks.RED_FLOWER_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        ConfiguredFeatures.register(featureRegisterable, YELLOW_FLOWER_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock)ModNatureBlocks.YELLOW_FLOWER_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        ConfiguredFeatures.register(featureRegisterable, PINK_FLOWER_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock)ModNatureBlocks.PINK_FLOWER_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        ConfiguredFeatures.register(featureRegisterable, WHITE_FLOWER_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock)ModNatureBlocks.WHITE_FLOWER_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));


        ConfiguredFeatures.register(featureRegisterable, PATCH_BASALT, Feature.BLOCK_PILE,
                new BlockPileFeatureConfig(BlockStateProvider.of(Blocks.BASALT)));
        ConfiguredFeatures.register(featureRegisterable, PATCH_BLACKSTONE, Feature.BLOCK_PILE,
                new BlockPileFeatureConfig(BlockStateProvider.of(Blocks.BLACKSTONE)));
        ConfiguredFeatures.register(featureRegisterable, PATCH_PUMICE, Feature.BLOCK_PILE,
                new BlockPileFeatureConfig(BlockStateProvider.of(StoneBlockSets.PUMICE.base())));

        ConfiguredFeatures.register(featureRegisterable, PATCH_COBWEB, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.COBWEB))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_SPIDER_EGGS, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.MIRKWOOD_SPIDER_EGG))));

        ConfiguredFeatures.register(featureRegisterable, PUMICE_COLUMN, ModFeatures.CAVE_COLUMN,
                new CaveColumnFeatureConfig(ConstantIntProvider.create(1), UniformIntProvider.create(1, 4), StoneBlockSets.PUMICE.base().getDefaultState()));
        ConfiguredFeatures.register(featureRegisterable, PUMICE_COLUMN_LARGE, ModFeatures.CAVE_COLUMN,
                new CaveColumnFeatureConfig(UniformIntProvider.create(2, 3), UniformIntProvider.create(5, 10), StoneBlockSets.PUMICE.base().getDefaultState()));

        ConfiguredFeatures.register(featureRegisterable, PATCH_BRACKEN, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.BRACKEN))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_BROWN_GRASS, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.BROWN_GRASS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_SHORT_BULRUSH, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.SHORT_BULRUSH))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_SHORT_CATTAIL, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.SHORT_CATTAILS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_TALL_BULRUSH, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.TALL_BULRUSH))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_TALL_CATTAIL, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.TALL_CATTAILS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_COASTAL_PANIC_GRASS, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.COASTAL_PANIC_GRASS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_CORRUPTED_MOSS, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock)ModNatureBlocks.CORRUPTED_MOSS,
                        20, true, true, true, 0.5f,
                        RegistryEntryList.of(Block::getRegistryEntry,
                                Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.MOSSY_COBBLESTONE, Blocks.COBBLESTONE,
                                Blocks.GRASS_BLOCK, Blocks.DIRT,  Blocks.SPRUCE_LOG, Blocks.OAK_LOG, Blocks.BIRCH_LOG, Blocks.DARK_OAK_LOG,
                                WoodBlockSets.MIRKWOOD.log(), ModNatureBlocks.OLD_PODZOL)));

        ConfiguredFeatures.register(featureRegisterable, PATCH_CORRUPTED_MOSS_CARPET, Feature.BLOCK_PILE,
                new BlockPileFeatureConfig(BlockStateProvider.of(ModNatureBlocks.CORRUPTED_MOSS_CARPET)));

        ConfiguredFeatures.register(featureRegisterable, PATCH_DEAD_RUSHES, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.DEAD_RUSHES))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_BEACH_GRASS, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.BEACH_GRASS))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_DRY_GRASS, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.DRY_GRASS))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_SMALL_DRY_SHRUB, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.SMALL_DRY_SHRUB))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_DUCKWEED, Feature.RANDOM_PATCH,
                new RandomPatchFeatureConfig(16, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.DUCKWEED)))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_DYING_GRASS, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.DYING_GRASS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_FALLEN_LEAVES, Feature.BLOCK_PILE,
                new BlockPileFeatureConfig(BlockStateProvider.of(ModNatureBlocks.FALLEN_LEAVES)));
        ConfiguredFeatures.register(featureRegisterable, PATCH_FALLEN_MALLORN_LEAVES, Feature.BLOCK_PILE,
                new BlockPileFeatureConfig(BlockStateProvider.of(ModNatureBlocks.FALLEN_MALLORN_LEAVES)));
        ConfiguredFeatures.register(featureRegisterable, PATCH_FALLEN_MIRKWOOD_LEAVES, Feature.BLOCK_PILE,
                new BlockPileFeatureConfig(BlockStateProvider.of(ModNatureBlocks.FALLEN_MIRKWOOD_LEAVES)));

        ConfiguredFeatures.register(featureRegisterable, PATCH_FALSE_OATGRASS, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.FALSE_OATGRASS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_FOREST_MOSS, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock)ModNatureBlocks.FOREST_MOSS,
                    20, true, true, true, 0.5f, BLOCKS_MOSS));

        ConfiguredFeatures.register(featureRegisterable, PATCH_FLOATING_ICE, Feature.RANDOM_PATCH,
                new RandomPatchFeatureConfig(12, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.FLOATING_ICE)))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_FOREST_MOSS_CARPET, Feature.BLOCK_PILE,
                new BlockPileFeatureConfig(BlockStateProvider.of(ModNatureBlocks.FOREST_MOSS_CARPET)));

        ConfiguredFeatures.register(featureRegisterable, PATCH_FROZEN_GRASS, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.FROZEN_GRASS))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_FROZEN_SHRUB, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.FROZEN_SHRUB))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_FROZEN_TUFT, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.FROZEN_TUFT))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_GREEN_SHRUB, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.GREEN_SHRUB))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_GRIM_GRASS, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.GRIM_GRASS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_HEATH, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.HEATH))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_HEATHER, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.HEATHER))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_DEAD_HEATHER, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.DEAD_HEATHER))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_DRY_HEATHER, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.DRY_HEATHER))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_HOROKAKA, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.HOROKAKA))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_GIANT_HOROKAKA, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.GIANT_HOROKAKA))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_RED_HEATHER, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.RED_HEATHER))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_LAVENDER, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.LAVENDER))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_REEDS, Feature.RANDOM_PATCH,
                new RandomPatchFeatureConfig(256, 12, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModResourceItems.REEDS)),
                        BlockFilterPlacementModifier.of(BlockPredicate.allOf(BlockPredicate.IS_AIR, BlockPredicate.wouldSurvive(ModResourceItems.REEDS.getDefaultState(), BlockPos.ORIGIN),
                                BlockPredicate.anyOf(
                                    BlockPredicate.matchingFluids(new BlockPos(1, -1, 0), Fluids.WATER, Fluids.FLOWING_WATER),
                                    BlockPredicate.matchingFluids(new BlockPos(-1, -1, 0), Fluids.WATER, Fluids.FLOWING_WATER),
                                    BlockPredicate.matchingFluids(new BlockPos(0, -1, 1), Fluids.WATER, Fluids.FLOWING_WATER),
                                    BlockPredicate.matchingFluids(new BlockPos(0, -1, -1), Fluids.WATER, Fluids.FLOWING_WATER),
                                    BlockPredicate.matchingFluids(new BlockPos(1, -1, 1), Fluids.WATER, Fluids.FLOWING_WATER),
                                    BlockPredicate.matchingFluids(new BlockPos(-1, -1, -1), Fluids.WATER, Fluids.FLOWING_WATER),
                                    BlockPredicate.matchingFluids(new BlockPos(-1, -1, 1), Fluids.WATER, Fluids.FLOWING_WATER),
                                    BlockPredicate.matchingFluids(new BlockPos(1, -1, -1), Fluids.WATER, Fluids.FLOWING_WATER)))))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_SCORCHED_GRASS, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.SCORCHED_GRASS))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_SCORCHED_SHRUB, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.SCORCHED_SHRUB))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_SCORCHED_TUFT, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.SCORCHED_TUFT))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_SEDUM, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.SEDUM))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_SEDUM_YELLOW, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.YELLOW_SEDUM))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_SHRIVELED_SHRUB, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.SHRIVELED_SHRUB))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_STRAWBERRY_BUSH, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.STRAWBERRY_BUSH.getDefaultState()
                                .with(SweetBerryBushBlock.AGE, 0))), List.of(Blocks.GRASS_BLOCK)));

        ConfiguredFeatures.register(featureRegisterable, PATCH_YELLOW_TROLLIUS, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.YELLOW_TROLLIUS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_TAN_SHRUB, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.TAN_SHRUB))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_TEMPERATE_GRASS, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.TEMPERATE_GRASS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_TOUGH_BERRY_BUSH, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.TOUGH_BERRY_BUSH.getDefaultState()
                                .with(SweetBerryBushBlock.AGE, 0))), List.of(Blocks.GRASS_BLOCK, Blocks.DIRT, ModBlocks.ASHEN_DIRT)));

        ConfiguredFeatures.register(featureRegisterable, PATCH_TUFT_GRASS, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.GRASS_TUFT))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_LILY_PADS, Feature.RANDOM_PATCH,
                new RandomPatchFeatureConfig(12, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.LILY_PADS)))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_SMALL_LILY_PADS, Feature.RANDOM_PATCH,
                new RandomPatchFeatureConfig(10, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.SMALL_LILY_PADS)))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_SMALL_FLOWERING_LILY_PADS, Feature.RANDOM_PATCH,
                new RandomPatchFeatureConfig(10, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.SMALL_FLOWERING_LILY_PADS)))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_MIRKWOOD, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                                .add(Blocks.SHORT_GRASS.getDefaultState(), 8)
                                .add(Blocks.FERN.getDefaultState(), 8)
                                .add(Blocks.TALL_GRASS.getDefaultState(), 15)
                                .add(Blocks.LARGE_FERN.getDefaultState(), 10)
                                .add(Blocks.BROWN_MUSHROOM.getDefaultState(), 1))), List.of(), 15));

        ConfiguredFeatures.register(featureRegisterable, PATCH_MIRKWOOD_ROOTS, Feature.BLOCK_PILE,
                new BlockPileFeatureConfig(new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                        .add(ModNatureBlocks.MIRKWOOD_ROOTS.getDefaultState(), 3))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_MOSS, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock)ModNatureBlocks.MOSS,
                        20, true, true, true, 0.5f, BLOCKS_MOSS));
        ConfiguredFeatures.register(featureRegisterable, PATCH_MOSS_CARPET, Feature.BLOCK_PILE,
                new BlockPileFeatureConfig(BlockStateProvider.of(Blocks.MOSS_CARPET)));

        ConfiguredFeatures.register(featureRegisterable, PATCH_STICKY_SNOW, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock)ModNatureBlocks.STICKY_SNOW,
                        32, true, true, true, 0.75f,
                        RegistryEntryList.of(Block::getRegistryEntry,
                                Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.MOSSY_COBBLESTONE, Blocks.COBBLESTONE,
                                Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.BASALT)));

        ConfiguredFeatures.register(featureRegisterable, PATCH_MIXED_WILD_WHEAT, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(new WeightedBlockStateProvider((DataPool.<BlockState>builder()
                                .add(ModNatureBlocks.TALL_WILD_WHEAT.getDefaultState(), 1)
                                .add(ModNatureBlocks.WILD_WHEAT.getDefaultState(), 3))
                        ))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_WHEAT_GRASS, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WHEATGRASS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_WILD_GRASS, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WILD_GRASS))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_WILDER_GRASS, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WILDERGRASS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_YELLOW_FLOWER, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.YELLOW_FLOWER))));

        // region MUSHROOMS
        ConfiguredFeatures.register(featureRegisterable, PATCH_BROWN_BOLETE, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.BROWN_BOLETE))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_BROWN_BOLETE_TILLER, Feature.FLOWER, new RandomPatchFeatureConfig(48, 6, 2,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(CavesConfiguredFeatures.getMushroomBuilder(ModNatureBlocks.BROWN_BOLETE_TILLER))))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_MORSEL, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.MORSEL))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_MORSEL_TILLER, Feature.FLOWER, new RandomPatchFeatureConfig(48, 6, 2,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(CavesConfiguredFeatures.getMushroomBuilder(ModNatureBlocks.MORSEL_TILLER))))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_WHITE_MUSHROOM, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WHITE_MUSHROOM))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_WHITE_MUSHROOM_TILLER, Feature.FLOWER, new RandomPatchFeatureConfig(48, 6, 2,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(CavesConfiguredFeatures.getMushroomBuilder(ModNatureBlocks.WHITE_MUSHROOM_TILLER))))));
        // endregion

        // region WILD CROPS
        ConfiguredFeatures.register(featureRegisterable, PATCH_WILD_BEETROOT, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WILD_BEETROOT))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_WILD_BELL_PEPPER, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WILD_BELL_PEPPER))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_WILD_CARROT, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WILD_CARROT))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_WILD_CUCUMBER, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WILD_CUCUMBER))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_WILD_FLAX, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WILD_FLAX))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_WILD_GARLIC, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WILD_GARLIC))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_WILD_LEEK, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WILD_LEEK))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_WILD_LETTUCE, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WILD_LETTUCE))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_WILD_ONION, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WILD_ONION))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_WILD_PIPEWEED, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WILD_PIPEWEED))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_WILD_POTATO, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WILD_POTATO))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_WILD_TOMATO, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WILD_TOMATO))));
        // endregion
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(MiddleEarth.MOD_ID, name));
    }
}
