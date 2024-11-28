package net.jukoz.me.world.features.vegetation;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.block.StoneBlockSets;
import net.jukoz.me.block.WoodBlockSets;
import net.jukoz.me.item.ModResourceItems;
import net.jukoz.me.world.features.columns.ColumnFeatureConfig;
import net.jukoz.me.world.features.underground.CavesConfiguredFeatures;
import net.jukoz.me.world.gen.ModFeatures;
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
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_ELANOR = registerKey("flower_elanor");

    // region FIELDS
    public static final RegistryKey<ConfiguredFeature<?, ?>> FIELD_HEATHER = registerKey("field_heather");
    // endregion

    // region GROWTH
    public static final RegistryKey<ConfiguredFeature<?, ?>> AZALEA_GROWTH = registerKey("azalea_growth");
    public static final RegistryKey<ConfiguredFeature<?, ?>> IVY_GROWTH = registerKey("ivy_growth");
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
    public static final RegistryKey<ConfiguredFeature<?, ?>> PUMICE_COLUMN = registerKey("pumice_column");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PUMICE_COLUMN_LARGE = registerKey("pumice_column_large");
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
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_DUCKWEED = registerKey("patch_duckweed");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_DYING_GRASS = registerKey("patch_dying_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_FALSE_OATGRASS = registerKey("patch_false_oatgrass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_FOREST_MOSS = registerKey("patch_forest_moss");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_FOREST_MOSS_CARPET = registerKey("patch_forest_moss_carpet");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_GREEN_SHRUB = registerKey("patch_green_shrub");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_HEATH = registerKey("patch_heath");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_HEATHER = registerKey("patch_heather");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_HEATHER_BUSH = registerKey("patch_heather_bush");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_HOROKAKA = registerKey("patch_horokaka");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_GIANT_HOROKAKA = registerKey("patch_giant_horokaka");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_RED_HEATHER = registerKey("patch_red_heather");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_REEDS = registerKey("patch_reeds");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_STICKY_SNOW = registerKey("patch_sticky_snow");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_LILY_PADS = registerKey("patch_lily_pads");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_SMALL_LILY_PADS = registerKey("patch_small_lily_pads");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_SMALL_FLOWERING_LILY_PADS = registerKey("patch_small_flowering_lily_pads");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_MIRKWOOD = registerKey("patch_mirkwood");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_MIRKWOOD_ROOTS = registerKey("patch_mirkwood_roots");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_MORDOR_LICHEN = registerKey("patch_mordor_lichen");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_MOSS = registerKey("patch_moss");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_SHRIVELED_SHRUB = registerKey("patch_shriveled_shrub");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_STRAWBERRY_BUSH = registerKey("patch_strawberry_bush");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_TAN_SHRUB = registerKey("patch_tan_shrub");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_TOUGH_BERRY_BUSH = registerKey("patch_tough_berry_bush");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_TUFT_GRASS = registerKey("patch_tuft_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WHEAT_GRASS = registerKey("patch_wheat_grass");
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
        ConfiguredFeatures.register(featureRegisterable, WATER_DELTA, Feature.DELTA_FEATURE,
                new DeltaFeatureConfig(Blocks.WATER.getDefaultState(), Blocks.GRASS_BLOCK.getDefaultState(), UniformIntProvider.create(3, 7), UniformIntProvider.create(0, 2)));

        ConfiguredFeatures.register(featureRegisterable, FIELD_HEATHER, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.HEATHER))));

        ConfiguredFeatures.register(featureRegisterable, FLOWER_ALLIUM, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.ALLIUM))));
        ConfiguredFeatures.register(featureRegisterable, FLOWER_AZURE_BLUET, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.AZURE_BLUET))));
        ConfiguredFeatures.register(featureRegisterable, FLOWER_CORNFLOWER, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.CORNFLOWER))));
        ConfiguredFeatures.register(featureRegisterable, FLOWER_GREEN_JEWEL, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.GREEN_JEWEL_CORNFLOWER))));
        ConfiguredFeatures.register(featureRegisterable, FLOWER_LILAC, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.LILAC))));
        ConfiguredFeatures.register(featureRegisterable, FLOWER_LEBENNIN, Feature.FLOWER,
                new RandomPatchFeatureConfig(96, 6, 2, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(new DualNoiseBlockStateProvider(new Range<>(1, 3), new DoublePerlinNoiseSampler.NoiseParameters(-10, 1.0), 1.0f, 2143L,
                                new DoublePerlinNoiseSampler.NoiseParameters(-3, 1.0), 1.0f,
                                List.of(Blocks.TALL_GRASS.getDefaultState(), Blocks.RED_TULIP.getDefaultState(), Blocks.POPPY.getDefaultState(), Blocks.AZURE_BLUET.getDefaultState(),
                                        Blocks.DANDELION.getDefaultState(), Blocks.ORANGE_TULIP.getDefaultState(), Blocks.OXEYE_DAISY.getDefaultState(), Blocks.SHORT_GRASS.getDefaultState()))))));
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
        ConfiguredFeatures.register(featureRegisterable, FLOWER_MALLOS, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.MALLOS))));
        ConfiguredFeatures.register(featureRegisterable, FLOWER_ELANOR, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.ELANOR))));


        ConfiguredFeatures.register(featureRegisterable, AZALEA_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock)ModNatureBlocks.AZALEA_FLOWER_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        ConfiguredFeatures.register(featureRegisterable, IVY_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock)ModNatureBlocks.IVY_GROWTH,
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

        ConfiguredFeatures.register(featureRegisterable, PUMICE_COLUMN, ModFeatures.COLUMN,
                new ColumnFeatureConfig(ConstantIntProvider.create(1), UniformIntProvider.create(1, 4), StoneBlockSets.PUMICE.base().getDefaultState()));
        ConfiguredFeatures.register(featureRegisterable, PUMICE_COLUMN_LARGE, ModFeatures.COLUMN,
                new ColumnFeatureConfig(UniformIntProvider.create(2, 3), UniformIntProvider.create(5, 10), StoneBlockSets.PUMICE.base().getDefaultState()));

        ConfiguredFeatures.register(featureRegisterable, PATCH_BRACKEN, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.BRACKEN))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_BROWN_GRASS, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.BROWN_GRASS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_SHORT_BULRUSH, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.SHORT_BULRUSH))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_SHORT_CATTAIL, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.SHORT_CATTAILS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_TALL_BULRUSH, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.TALL_BULRUSH))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_TALL_CATTAIL, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.TALL_CATTAILS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_COASTAL_PANIC_GRASS, Feature.RANDOM_PATCH,
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

        ConfiguredFeatures.register(featureRegisterable, PATCH_DEAD_RUSHES, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.DEAD_RUSHES))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_DRY_GRASS, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.DRY_GRASS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_DUCKWEED, Feature.RANDOM_PATCH,
                new RandomPatchFeatureConfig(16, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.DUCKWEED)))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_DYING_GRASS, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.DYING_GRASS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_FALSE_OATGRASS, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.FALSE_OATGRASS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_FOREST_MOSS, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock)ModNatureBlocks.FOREST_MOSS,
                    20, true, true, true, 0.5f, BLOCKS_MOSS));

        ConfiguredFeatures.register(featureRegisterable, PATCH_FOREST_MOSS_CARPET, Feature.BLOCK_PILE,
                new BlockPileFeatureConfig(BlockStateProvider.of(ModNatureBlocks.FOREST_MOSS_CARPET)));

        ConfiguredFeatures.register(featureRegisterable, PATCH_GREEN_SHRUB, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.GREEN_SHRUB))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_HEATH, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.HEATH))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_HEATHER, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.HEATHER))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_HEATHER_BUSH, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.HEATHER_BUSH))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_HOROKAKA, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.HOROKAKA))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_GIANT_HOROKAKA, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.GIANT_HOROKAKA))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_RED_HEATHER, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.RED_HEATHER))));
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

        ConfiguredFeatures.register(featureRegisterable, PATCH_SHRIVELED_SHRUB, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.SHRIVELED_SHRUB))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_STRAWBERRY_BUSH, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.STRAWBERRY_BUSH.getDefaultState()
                                .with(SweetBerryBushBlock.AGE, 0))), List.of(Blocks.GRASS_BLOCK)));

        ConfiguredFeatures.register(featureRegisterable, PATCH_TAN_SHRUB, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.TAN_SHRUB))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_TOUGH_BERRY_BUSH, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.TOUGH_BERRY_BUSH.getDefaultState()
                                .with(SweetBerryBushBlock.AGE, 0))), List.of(Blocks.GRASS_BLOCK, Blocks.DIRT, ModBlocks.ASHEN_DIRT)));

        ConfiguredFeatures.register(featureRegisterable, PATCH_TUFT_GRASS, Feature.RANDOM_PATCH,
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

        ConfiguredFeatures.register(featureRegisterable, PATCH_MORDOR_LICHEN, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.MORDOR_LICHEN)), List.of(), 30));

        ConfiguredFeatures.register(featureRegisterable, PATCH_MOSS, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock)ModNatureBlocks.MOSS,
                        20, true, true, true, 0.5f, BLOCKS_MOSS));

        ConfiguredFeatures.register(featureRegisterable, PATCH_STICKY_SNOW, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock)ModNatureBlocks.STICKY_SNOW,
                        32, true, true, true, 0.75f,
                        RegistryEntryList.of(Block::getRegistryEntry,
                                Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.MOSSY_COBBLESTONE, Blocks.COBBLESTONE,
                                Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.BASALT)));
        //ConfiguredFeatures.register(featureRegisterable, PATCH_STICKY_SNOW, Feature.BLOCK_PILE,
        //        new BlockPileFeatureConfig(BlockStateProvider.of(ModNatureBlocks.STICKY_SNOW)));

        ConfiguredFeatures.register(featureRegisterable, PATCH_WHEAT_GRASS, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WHEATGRASS))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_WILD_GRASS, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WILD_GRASS))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_WILDER_GRASS, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WILDERGRASS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_YELLOW_FLOWER, Feature.RANDOM_PATCH,
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
