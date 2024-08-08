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

    public static final RegistryKey<ConfiguredFeature<?, ?>> MEDIUM_BOULDER_ANDESITE = registerKey("medium_boulder_andesite");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BIG_BOULDER_ANDESITE = registerKey("big_boulder_andesite");

    private static final List<BlockState> andesite = Arrays.asList(
            Blocks.ANDESITE.getDefaultState(),
            Blocks.ANDESITE.getDefaultState(),
            StoneBlockSets.COBBLED_ANDESITE.base().getDefaultState(),
            StoneBlockSets.MOSSY_COBBLED_ANDESITE.base().getDefaultState());

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

        ConfiguredFeatures.register(featureRegisterable, MEDIUM_BOULDER_ANDESITE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(3.9f, 5.2f,  3.7f, 0.9f, 0.21f, andesite));
        ConfiguredFeatures.register(featureRegisterable, BIG_BOULDER_ANDESITE, ModFeatures.BIG_BOULDER,
                new BigBoulderFeatureConfig(5.5f, 6.7f,  5.3f, 1.7f, 0.41f, andesite));
    }


    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(MiddleEarth.MOD_ID, name));
    }
}