package net.jukoz.me.world.features.vegetation;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.block.WoodBlockSets;
import net.minecraft.block.*;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.dynamic.Range;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.DualNoiseBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.List;

public class ModVegetationConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_MEADOW = registerKey("flower_meadow");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_MALLOS = registerKey("flower_mallos");

    // region FOLIAGE
    public static final RegistryKey<ConfiguredFeature<?, ?>> MOCK = registerKey("mock"); // Mock feature. Use it for duplicate data gen strategy
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_BASALT = registerKey("patch_basalt");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_BLACKSTONE = registerKey("patch_blackstone");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_BROWN_GRASS = registerKey("patch_brown_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_CORRUPTED_MOSS = registerKey("patch_corrupted_moss");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_CORRUPTED_MOSS_CARPET = registerKey("patch_corrupted_moss_carpet");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_DRY_GRASS = registerKey("patch_dry_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_DYING_GRASS = registerKey("patch_dying_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_FOREST_MOSS = registerKey("patch_forest_moss");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_FOREST_MOSS_CARPET = registerKey("patch_forest_moss_carpet");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_GREEN_SHRUB = registerKey("patch_green_shrub");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_HEATHER = registerKey("patch_heather");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_HEATHER_BUSH = registerKey("patch_heather_bush");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_RED_HEATHER = registerKey("patch_red_heather");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_MIRKWOOD = registerKey("patch_mirkwood");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_MIRKWOOD_ROOTS = registerKey("patch_mirkwood_roots");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_MORDOR_LICHEN = registerKey("patch_mordor_lichen");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_STRAWBERRY_BUSH = registerKey("patch_strawberry_bush");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_TAN_SHRUB = registerKey("patch_tan_shrub");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_TOUGH_BERRY_BUSH = registerKey("patch_tough_berry_bush");
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
        ConfiguredFeatures.register(featureRegisterable, MOCK, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.GRASS))));

        ConfiguredFeatures.register(featureRegisterable, FLOWER_MALLOS, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.MALLOS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_BASALT, Feature.BLOCK_PILE,
                new BlockPileFeatureConfig(BlockStateProvider.of(Blocks.BASALT)));
        ConfiguredFeatures.register(featureRegisterable, PATCH_BLACKSTONE, Feature.BLOCK_PILE,
                new BlockPileFeatureConfig(BlockStateProvider.of(Blocks.BLACKSTONE)));

        ConfiguredFeatures.register(featureRegisterable, PATCH_BROWN_GRASS, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.BROWN_GRASS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_CORRUPTED_MOSS, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock)ModNatureBlocks.CORRUPTED_MOSS,
                        20, true, true, true, 0.5f,
                        RegistryEntryList.of(Block::getRegistryEntry,
                                Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.MOSSY_COBBLESTONE, Blocks.COBBLESTONE,
                                Blocks.GRASS_BLOCK, Blocks.DIRT,  Blocks.SPRUCE_LOG, Blocks.OAK_LOG, Blocks.BIRCH_LOG, Blocks.DARK_OAK_LOG,
                                WoodBlockSets.MIRKWOOD.log(), ModNatureBlocks.OLD_PODZOL)));

        ConfiguredFeatures.register(featureRegisterable, PATCH_CORRUPTED_MOSS_CARPET, Feature.BLOCK_PILE,
                new BlockPileFeatureConfig(BlockStateProvider.of(ModNatureBlocks.CORRUPTED_MOSS_CARPET)));

        ConfiguredFeatures.register(featureRegisterable, PATCH_DRY_GRASS, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.DRY_GRASS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_DYING_GRASS, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.DYING_GRASS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_FOREST_MOSS, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthFeatureConfig((MultifaceGrowthBlock)ModNatureBlocks.FOREST_MOSS,
                    20, true, true, true, 0.5f,
                    RegistryEntryList.of(Block::getRegistryEntry,
                        Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.MOSSY_COBBLESTONE, Blocks.COBBLESTONE,
                        Blocks.GRASS_BLOCK, Blocks.DIRT,  Blocks.SPRUCE_LOG, Blocks.OAK_LOG, Blocks.BIRCH_LOG, Blocks.DARK_OAK_LOG,
                        WoodBlockSets.PINE.log(), WoodBlockSets.LARCH.log(), WoodBlockSets.BEECH.log(), WoodBlockSets.MAPLE.log(),
                        WoodBlockSets.BLACK_LEBETHRON.log(), WoodBlockSets.WHITE_LEBETHRON.log())));

        ConfiguredFeatures.register(featureRegisterable, PATCH_FOREST_MOSS_CARPET, Feature.BLOCK_PILE,
                new BlockPileFeatureConfig(BlockStateProvider.of(ModNatureBlocks.FOREST_MOSS_CARPET)));

        ConfiguredFeatures.register(featureRegisterable, PATCH_GREEN_SHRUB, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.GREEN_SHRUB))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_HEATHER, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.HEATHER))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_HEATHER_BUSH, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.HEATHER_BUSH))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_RED_HEATHER, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.RED_HEATHER))));

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

        ConfiguredFeatures.register(featureRegisterable, PATCH_MIRKWOOD, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                                .add(Blocks.GRASS.getDefaultState(), 8)
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
        ConfiguredFeatures.register(featureRegisterable, PATCH_BROWN_BOLETE_TILLER, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.BROWN_BOLETE_TILLER))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_MORSEL, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.MORSEL))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_MORSEL_TILLER, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.MORSEL_TILLER))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_WHITE_MUSHROOM, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WHITE_MUSHROOM))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_WHITE_MUSHROOM_TILLER, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WHITE_MUSHROOM_TILLER))));
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
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(MiddleEarth.MOD_ID, name));
    }
}
