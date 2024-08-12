package net.jukoz.me.world.features.boulder;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.block.StoneBlockSets;
import net.jukoz.me.world.gen.ModFeatures;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.Arrays;
import java.util.List;

public class BoulderConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> ANDESITE_BOULDER = registerKey("andesite_boulder");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CALCITE_BOULDER = registerKey("calcite_boulder");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DIORITE_BOULDER = registerKey("diorite_boulder");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GRANITE_BOULDER = registerKey("granite_boulder");
    public static final RegistryKey<ConfiguredFeature<?, ?>> LIMESTONE_BOULDER = registerKey("limestone_boulder");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SANDSTONE_BOULDER = registerKey("sandstone_boulder");
    public static final RegistryKey<ConfiguredFeature<?, ?>> STONE_BOULDER = registerKey("stone_boulder");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MIRKWOOD_ROOTS_BOULDER = registerKey("mirkwood_roots_boulder");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MOSSY_BOULDER = registerKey("mossy_boulder");


    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_BOULDER_ANDESITE = registerKey("small_boulder_andesite");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEDIUM_BOULDER_ANDESITE = registerKey("medium_boulder_andesite");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BIG_BOULDER_ANDESITE = registerKey("big_boulder_andesite");

    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_BOULDER_ASHEN_STONE = registerKey("small_boulder_ashen_stone");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEDIUM_BOULDER_ASHEN_STONE = registerKey("medium_boulder_ashen_stone");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BIG_BOULDER_ASHEN_STONE = registerKey("big_boulder_ashen_stone");

    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_BOULDER_BASALT = registerKey("small_boulder_basalt");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEDIUM_BOULDER_BASALT = registerKey("medium_boulder_basalt");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BIG_BOULDER_BASALT = registerKey("big_boulder_basalt");

    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_BOULDER_BLUE_TUFF = registerKey("small_boulder_blue_tuff");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEDIUM_BOULDER_BLUE_TUFF = registerKey("medium_boulder_blue_tuff");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BIG_BOULDER_BLUE_TUFF = registerKey("big_boulder_blue_tuff");

    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_BOULDER_CALCITE = registerKey("small_boulder_calcite");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEDIUM_BOULDER_CALCITE = registerKey("medium_boulder_calcite");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BIG_BOULDER_CALCITE = registerKey("big_boulder_calcite");

    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_BOULDER_DIORITE = registerKey("small_boulder_diorite");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEDIUM_BOULDER_DIORITE = registerKey("medium_boulder_diorite");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BIG_BOULDER_DIORITE = registerKey("big_boulder_diorite");

    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_BOULDER_DOLOMITE = registerKey("small_boulder_dolomite");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEDIUM_BOULDER_DOLOMITE = registerKey("medium_boulder_dolomite");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BIG_BOULDER_DOLOMITE = registerKey("big_boulder_dolomite");

    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_BOULDER_GNEISS = registerKey("small_boulder_gneiss");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEDIUM_BOULDER_GNEISS = registerKey("medium_boulder_gneiss");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BIG_BOULDER_GNEISS = registerKey("big_boulder_gneiss");

    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_BOULDER_GRANITE = registerKey("small_boulder_granite");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEDIUM_BOULDER_GRANITE = registerKey("medium_boulder_granite");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BIG_BOULDER_GRANITE = registerKey("big_boulder_granite");

    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_BOULDER_HEMATITE = registerKey("small_boulder_hematite");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEDIUM_BOULDER_HEMATITE = registerKey("medium_boulder_hematite");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BIG_BOULDER_HEMATITE = registerKey("big_boulder_hematite");

    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_BOULDER_IRONSTONE = registerKey("small_boulder_ironstone");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEDIUM_BOULDER_IRONSTONE = registerKey("medium_boulder_ironstone");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BIG_BOULDER_IRONSTONE = registerKey("big_boulder_ironstone");

    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_BOULDER_LIMESTONE = registerKey("small_boulder_limestone");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEDIUM_BOULDER_LIMESTONE = registerKey("medium_boulder_limestone");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BIG_BOULDER_LIMESTONE = registerKey("big_boulder_limestone");

    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_BOULDER_MOSSY_STONE = registerKey("small_boulder_mossy_stone");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEDIUM_BOULDER_MOSSY_STONE = registerKey("medium_boulder_mossy_stone");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BIG_BOULDER_MOSSY_STONE = registerKey("big_boulder_mossy_stone");

    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_BOULDER_SANDSTONE = registerKey("small_boulder_sandstone");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEDIUM_BOULDER_SANDSTONE = registerKey("medium_boulder_sandstone");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BIG_BOULDER_SANDSTONE = registerKey("big_boulder_mossy_sandstone");

    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_BOULDER_STONE = registerKey("small_boulder_stone");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEDIUM_BOULDER_STONE = registerKey("medium_boulder_stone");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BIG_BOULDER_STONE = registerKey("big_boulder_vstone");

    // region Configs
    private static final float smallWidth = 1.5f;
    private static final float smallLength = 2.1f;
    private static final float smallHeight = 1.55f;
    private static final float smallRandomSize = 0.6f;
    private static final float smallRandomness = 0.19f;

    private static final float mediumWidth = 2.6f;
    private static final float mediumLength = 4.0f;
    private static final float mediumHeight = 2.8f;
    private static final float mediumRandomSize = 0.95f;
    private static final float mediumRandomness = 0.26f;

    private static final float bigWidth = 4.5f;
    private static final float bigLength = 5.85f;
    private static final float bigHeight = 4.3f;
    private static final float bigRandomSize = 1.7f;
    private static final float bigRandomness = 0.27f;
    // endregion

    private static final List<BlockState> andesite = Arrays.asList(
            Blocks.ANDESITE.getDefaultState(),
            Blocks.ANDESITE.getDefaultState(),
            StoneBlockSets.COBBLED_ANDESITE.base().getDefaultState(),
            StoneBlockSets.MOSSY_COBBLED_ANDESITE.base().getDefaultState());

    private static final List<BlockState> ashenStone = Arrays.asList(
            StoneBlockSets.ASHEN_STONE.base().getDefaultState(),
            StoneBlockSets.ASHEN_STONE.base().getDefaultState(),
            StoneBlockSets.ASHEN_COBBLESTONE.base().getDefaultState(),
            ModBlocks.ASHEN_GRAVEL.getDefaultState(),
            ModBlocks.ASH_BLOCK.getDefaultState());

    private static final List<BlockState> basalt = Arrays.asList(
            Blocks.BASALT.getDefaultState(),
            Blocks.SMOOTH_BASALT.getDefaultState());

    private static final List<BlockState> blueTuff = Arrays.asList(
            StoneBlockSets.BLUE_TUFF.base().getDefaultState(),
            StoneBlockSets.BLUE_TUFF.base().getDefaultState(),
            StoneBlockSets.COBBLED_BLUE_TUFF.base().getDefaultState(),
            StoneBlockSets.MOSSY_COBBLED_BLUE_TUFF.base().getDefaultState());

    private static final List<BlockState> calcite = Arrays.asList(
            Blocks.CALCITE.getDefaultState(),
            Blocks.CALCITE.getDefaultState(),
            StoneBlockSets.COBBLED_CALCITE.base().getDefaultState(),
            StoneBlockSets.MOSSY_COBBLED_CALCITE.base().getDefaultState());

    private static final List<BlockState> diorite = Arrays.asList(
            Blocks.DIORITE.getDefaultState(),
            Blocks.DIORITE.getDefaultState(),
            StoneBlockSets.COBBLED_DIORITE.base().getDefaultState(),
            StoneBlockSets.MOSSY_COBBLED_DIORITE.base().getDefaultState());

    private static final List<BlockState> dolomite = Arrays.asList(
            StoneBlockSets.DOLOMITE.base().getDefaultState(),
            StoneBlockSets.DOLOMITE.base().getDefaultState(),
            StoneBlockSets.COBBLED_DOLOMITE.base().getDefaultState(),
            StoneBlockSets.MOSSY_COBBLED_DOLOMITE.base().getDefaultState());

    private static final List<BlockState> gneiss = Arrays.asList(
            StoneBlockSets.GNEISS.base().getDefaultState(),
            StoneBlockSets.GNEISS.base().getDefaultState(),
            StoneBlockSets.SMOOTH_GNEISS.base().getDefaultState());

    private static final List<BlockState> granite = Arrays.asList(
            Blocks.GRANITE.getDefaultState(),
            Blocks.GRANITE.getDefaultState(),
            StoneBlockSets.COBBLED_GRANITE.base().getDefaultState(),
            StoneBlockSets.MOSSY_COBBLED_GRANITE.base().getDefaultState());

    private static final List<BlockState> hematite = Arrays.asList(
            StoneBlockSets.HEMATITE.base().getDefaultState(),
            StoneBlockSets.HEMATITE.base().getDefaultState(),
            StoneBlockSets.COBBLED_HEMATITE.base().getDefaultState());

    private static final List<BlockState> ironStone = Arrays.asList(
            StoneBlockSets.IRONSTONE.base().getDefaultState(),
            StoneBlockSets.IRONSTONE.base().getDefaultState(),
            StoneBlockSets.COBBLED_IRONSTONE.base().getDefaultState());

    private static final List<BlockState> limeStone = Arrays.asList(
            StoneBlockSets.LIMESTONE.base().getDefaultState(),
            StoneBlockSets.LIMESTONE.base().getDefaultState(),
            StoneBlockSets.COBBLED_LIMESTONE.base().getDefaultState(),
            StoneBlockSets.MOSSY_COBBLED_LIMESTONE.base().getDefaultState());

    private static final List<BlockState> mossyStone = Arrays.asList(
            Blocks.STONE.getDefaultState(),
            Blocks.COBBLESTONE.getDefaultState(),
            Blocks.MOSS_BLOCK.getDefaultState(),
            Blocks.MOSSY_COBBLESTONE.getDefaultState());

    private static final List<BlockState> sandStone = Arrays.asList(
            Blocks.SANDSTONE.getDefaultState(),
            Blocks.SMOOTH_SANDSTONE.getDefaultState(),
            Blocks.SAND.getDefaultState());

    private static final List<BlockState> stone = Arrays.asList(
            Blocks.STONE.getDefaultState(),
            Blocks.COBBLESTONE.getDefaultState(),
            Blocks.MOSSY_COBBLESTONE.getDefaultState());



    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        ConfiguredFeatures.register(featureRegisterable, ANDESITE_BOULDER, Feature.FOREST_ROCK,
                new SingleStateFeatureConfig(Blocks.ANDESITE.getDefaultState()));
        ConfiguredFeatures.register(featureRegisterable, CALCITE_BOULDER, Feature.FOREST_ROCK,
                new SingleStateFeatureConfig(Blocks.CALCITE.getDefaultState()));
        ConfiguredFeatures.register(featureRegisterable, DIORITE_BOULDER, Feature.FOREST_ROCK,
                new SingleStateFeatureConfig(Blocks.DIORITE.getDefaultState()));
        ConfiguredFeatures.register(featureRegisterable, GRANITE_BOULDER, Feature.FOREST_ROCK,
                new SingleStateFeatureConfig(Blocks.GRANITE.getDefaultState()));
        ConfiguredFeatures.register(featureRegisterable, LIMESTONE_BOULDER, Feature.FOREST_ROCK,
                new SingleStateFeatureConfig(StoneBlockSets.LIMESTONE.base().getDefaultState()));
        ConfiguredFeatures.register(featureRegisterable, SANDSTONE_BOULDER, Feature.FOREST_ROCK,
                new SingleStateFeatureConfig(Blocks.SANDSTONE.getDefaultState()));
        ConfiguredFeatures.register(featureRegisterable, STONE_BOULDER, Feature.FOREST_ROCK,
                new SingleStateFeatureConfig(Blocks.STONE.getDefaultState()));
        ConfiguredFeatures.register(featureRegisterable, MIRKWOOD_ROOTS_BOULDER, Feature.FOREST_ROCK,
                new SingleStateFeatureConfig(ModNatureBlocks.MIRKWOOD_ROOTS.getDefaultState()));

        ConfiguredFeatures.register(featureRegisterable, MOSSY_BOULDER, Feature.BLOCK_PILE,
                new BlockPileFeatureConfig(new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                        .add(Blocks.STONE.getDefaultState(), 3)
                        .add(Blocks.ANDESITE.getDefaultState(), 2)
                        .add(Blocks.STONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM).with(SlabBlock.WATERLOGGED, false), 1)
                        .add(Blocks.MOSSY_COBBLESTONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM).with(SlabBlock.WATERLOGGED, false), 2)
                        .add(Blocks.MOSSY_COBBLESTONE.getDefaultState(), 6))));

        ConfiguredFeatures.register(featureRegisterable, SMALL_BOULDER_ANDESITE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(smallWidth, smallLength, smallHeight, smallRandomSize, smallRandomness, andesite));
        ConfiguredFeatures.register(featureRegisterable, MEDIUM_BOULDER_ANDESITE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(mediumWidth, mediumLength, mediumHeight, mediumRandomSize, mediumRandomness, andesite));
        ConfiguredFeatures.register(featureRegisterable, BIG_BOULDER_ANDESITE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(bigWidth, bigLength, bigHeight, bigRandomSize, bigRandomness, andesite));

        ConfiguredFeatures.register(featureRegisterable, SMALL_BOULDER_ASHEN_STONE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(smallWidth, smallLength, smallHeight, smallRandomSize, smallRandomness, ashenStone));
        ConfiguredFeatures.register(featureRegisterable, MEDIUM_BOULDER_ASHEN_STONE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(mediumWidth, mediumLength, mediumHeight, mediumRandomSize, mediumRandomness, ashenStone));
        ConfiguredFeatures.register(featureRegisterable, BIG_BOULDER_ASHEN_STONE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(bigWidth, bigLength, bigHeight, bigRandomSize, bigRandomness, ashenStone));

        ConfiguredFeatures.register(featureRegisterable, SMALL_BOULDER_BASALT, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(smallWidth, smallLength, smallHeight, smallRandomSize, smallRandomness, basalt));
        ConfiguredFeatures.register(featureRegisterable, MEDIUM_BOULDER_BASALT, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(mediumWidth, mediumLength, mediumHeight, mediumRandomSize, mediumRandomness, basalt));
        ConfiguredFeatures.register(featureRegisterable, BIG_BOULDER_BASALT, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(bigWidth, bigLength, bigHeight, bigRandomSize, bigRandomness, basalt));

        ConfiguredFeatures.register(featureRegisterable, SMALL_BOULDER_BLUE_TUFF, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(smallWidth, smallLength, smallHeight, smallRandomSize, smallRandomness, blueTuff));
        ConfiguredFeatures.register(featureRegisterable, MEDIUM_BOULDER_BLUE_TUFF, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(mediumWidth, mediumLength, mediumHeight, mediumRandomSize, mediumRandomness, blueTuff));
        ConfiguredFeatures.register(featureRegisterable, BIG_BOULDER_BLUE_TUFF, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(bigWidth, bigLength, bigHeight, bigRandomSize, bigRandomness, blueTuff));

        ConfiguredFeatures.register(featureRegisterable, SMALL_BOULDER_CALCITE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(smallWidth, smallLength, smallHeight, smallRandomSize, smallRandomness, calcite));
        ConfiguredFeatures.register(featureRegisterable, MEDIUM_BOULDER_CALCITE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(mediumWidth, mediumLength, mediumHeight, mediumRandomSize, mediumRandomness, calcite));
        ConfiguredFeatures.register(featureRegisterable, BIG_BOULDER_CALCITE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(bigWidth, bigLength, bigHeight, bigRandomSize, bigRandomness, calcite));

        ConfiguredFeatures.register(featureRegisterable, SMALL_BOULDER_DIORITE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(smallWidth, smallLength, smallHeight, smallRandomSize, smallRandomness, diorite));
        ConfiguredFeatures.register(featureRegisterable, MEDIUM_BOULDER_DIORITE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(mediumWidth, mediumLength, mediumHeight, mediumRandomSize, mediumRandomness, diorite));
        ConfiguredFeatures.register(featureRegisterable, BIG_BOULDER_DIORITE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(bigWidth, bigLength, bigHeight, bigRandomSize, bigRandomness, diorite));

        ConfiguredFeatures.register(featureRegisterable, SMALL_BOULDER_DOLOMITE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(smallWidth, smallLength, smallHeight, smallRandomSize, smallRandomness, dolomite));
        ConfiguredFeatures.register(featureRegisterable, MEDIUM_BOULDER_DOLOMITE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(mediumWidth, mediumLength, mediumHeight, mediumRandomSize, mediumRandomness, dolomite));
        ConfiguredFeatures.register(featureRegisterable, BIG_BOULDER_DOLOMITE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(bigWidth, bigLength, bigHeight, bigRandomSize, bigRandomness, dolomite));

        ConfiguredFeatures.register(featureRegisterable, SMALL_BOULDER_GNEISS, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(smallWidth, smallLength, smallHeight, smallRandomSize, smallRandomness, gneiss));
        ConfiguredFeatures.register(featureRegisterable, MEDIUM_BOULDER_GNEISS, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(mediumWidth, mediumLength, mediumHeight, mediumRandomSize, mediumRandomness, gneiss));
        ConfiguredFeatures.register(featureRegisterable, BIG_BOULDER_GNEISS, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(bigWidth, bigLength, bigHeight, bigRandomSize, bigRandomness, gneiss));

        ConfiguredFeatures.register(featureRegisterable, SMALL_BOULDER_GRANITE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(smallWidth, smallLength, smallHeight, smallRandomSize, smallRandomness, granite));
        ConfiguredFeatures.register(featureRegisterable, MEDIUM_BOULDER_GRANITE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(mediumWidth, mediumLength, mediumHeight, mediumRandomSize, mediumRandomness, granite));
        ConfiguredFeatures.register(featureRegisterable, BIG_BOULDER_GRANITE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(bigWidth, bigLength, bigHeight, bigRandomSize, bigRandomness, granite));

        ConfiguredFeatures.register(featureRegisterable, SMALL_BOULDER_HEMATITE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(smallWidth, smallLength, smallHeight, smallRandomSize, smallRandomness, hematite));
        ConfiguredFeatures.register(featureRegisterable, MEDIUM_BOULDER_HEMATITE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(mediumWidth, mediumLength, mediumHeight, mediumRandomSize, mediumRandomness, hematite));
        ConfiguredFeatures.register(featureRegisterable, BIG_BOULDER_HEMATITE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(bigWidth, bigLength, bigHeight, bigRandomSize, bigRandomness, hematite));

        ConfiguredFeatures.register(featureRegisterable, SMALL_BOULDER_IRONSTONE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(smallWidth, smallLength, smallHeight, smallRandomSize, smallRandomness, ironStone));
        ConfiguredFeatures.register(featureRegisterable, MEDIUM_BOULDER_IRONSTONE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(mediumWidth, mediumLength, mediumHeight, mediumRandomSize, mediumRandomness, ironStone));
        ConfiguredFeatures.register(featureRegisterable, BIG_BOULDER_IRONSTONE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(bigWidth, bigLength, bigHeight, bigRandomSize, bigRandomness, ironStone));

        ConfiguredFeatures.register(featureRegisterable, SMALL_BOULDER_LIMESTONE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(smallWidth, smallLength, smallHeight, smallRandomSize, smallRandomness, limeStone));
        ConfiguredFeatures.register(featureRegisterable, MEDIUM_BOULDER_LIMESTONE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(mediumWidth, mediumLength, mediumHeight, mediumRandomSize, mediumRandomness, limeStone));
        ConfiguredFeatures.register(featureRegisterable, BIG_BOULDER_LIMESTONE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(bigWidth, bigLength, bigHeight, bigRandomSize, bigRandomness, limeStone));

        ConfiguredFeatures.register(featureRegisterable, SMALL_BOULDER_MOSSY_STONE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(smallWidth, smallLength, smallHeight, smallRandomSize, smallRandomness, mossyStone));
        ConfiguredFeatures.register(featureRegisterable, MEDIUM_BOULDER_MOSSY_STONE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(mediumWidth, mediumLength, mediumHeight, mediumRandomSize, mediumRandomness, mossyStone));
        ConfiguredFeatures.register(featureRegisterable, BIG_BOULDER_MOSSY_STONE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(bigWidth, bigLength, bigHeight, bigRandomSize, bigRandomness, mossyStone));

        ConfiguredFeatures.register(featureRegisterable, SMALL_BOULDER_SANDSTONE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(smallWidth, smallLength, smallHeight, smallRandomSize, smallRandomness, sandStone));
        ConfiguredFeatures.register(featureRegisterable, MEDIUM_BOULDER_SANDSTONE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(mediumWidth, mediumLength, mediumHeight, mediumRandomSize, mediumRandomness, sandStone));
        ConfiguredFeatures.register(featureRegisterable, BIG_BOULDER_SANDSTONE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(bigWidth, bigLength, bigHeight, bigRandomSize, bigRandomness, sandStone));

        ConfiguredFeatures.register(featureRegisterable, SMALL_BOULDER_STONE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(smallWidth, smallLength, smallHeight, smallRandomSize, smallRandomness, stone));
        ConfiguredFeatures.register(featureRegisterable, MEDIUM_BOULDER_STONE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(mediumWidth, mediumLength, mediumHeight, mediumRandomSize, mediumRandomness, stone));
        ConfiguredFeatures.register(featureRegisterable, BIG_BOULDER_STONE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(bigWidth, bigLength, bigHeight, bigRandomSize, bigRandomness, stone));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(MiddleEarth.MOD_ID, name));
    }
}