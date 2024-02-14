package net.jukoz.me.world.features.vegetation;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModNatureBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.List;

public class ModVegetationConfiguredFeatures {
    // region FOLIAGE
    public static final RegistryKey<ConfiguredFeature<?, ?>> MOCK = registerKey("mock"); // Mock feature. Use it for duplicate data gen strategy
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_FOREST_MOSS = registerKey("patch_forest_moss");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_HEATHER = registerKey("patch_heather");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_HEATHER_BUSH = registerKey("patch_heather_bush");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_RED_HEATHER = registerKey("patch_red_heather");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_STRAWBERRY_BUSH = registerKey("patch_strawberry_bush");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_TOUGH_BERRY_BUSH = registerKey("patch_tough_berry_bush");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_MALLOS = registerKey("patch_mallos");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_MIRKWOOD = registerKey("patch_mirkwood");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_MORDOR_LICHEN = registerKey("patch_mordor_lichen");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WHEAT_GRASS = registerKey("patch_wheat_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_WILD_GRASS = registerKey("patch_wild_grass");
    // endregion

    // region Mushrooms
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

        ConfiguredFeatures.register(featureRegisterable, PATCH_FOREST_MOSS, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.FOREST_MOSS))));

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

        ConfiguredFeatures.register(featureRegisterable, PATCH_TOUGH_BERRY_BUSH, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.TOUGH_BERRY_BUSH.getDefaultState()
                                .with(SweetBerryBushBlock.AGE, 0))), List.of(Blocks.GRASS_BLOCK)));

        ConfiguredFeatures.register(featureRegisterable, PATCH_MALLOS, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.MALLOS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_MIRKWOOD, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                                .add(Blocks.GRASS.getDefaultState(), 8)
                                .add(Blocks.FERN.getDefaultState(), 8)
                                .add(Blocks.TALL_GRASS.getDefaultState(), 15)
                                .add(Blocks.LARGE_FERN.getDefaultState(), 10)
                                .add(Blocks.BROWN_MUSHROOM.getDefaultState(), 1))), List.of(), 15));

        ConfiguredFeatures.register(featureRegisterable, PATCH_MORDOR_LICHEN, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.MORDOR_LICHEN)), List.of(), 30));

        ConfiguredFeatures.register(featureRegisterable, PATCH_WHEAT_GRASS, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WHEATGRASS))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_WILD_GRASS, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.WILD_GRASS))));

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
