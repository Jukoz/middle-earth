package net.sevenstars.middleearth.world.features.tree;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.ModBlocks;
import net.sevenstars.middleearth.block.ModNatureBlocks;
import net.sevenstars.middleearth.block.MushroomBlockSets;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.PineFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public class MushroomTreeConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> BROWN_BOLETTE_TREE_KEY = registerKey("brown_bolette_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CAVE_AMANITA_TREE_KEY = registerKey("cave_amanita_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DEEP_FIRECAP_TREE_KEY = registerKey("deep_firecap_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SKY_FIRECAP_TREE_KEY = registerKey("sky_firecap_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> YELLOW_AMANITA_TREE_KEY = registerKey("yellow_amanita_tree");


    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RegistryEntryLookup<Block> registryEntryLookup = context.getRegistryLookup(RegistryKeys.BLOCK);

        register(context, BROWN_BOLETTE_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.MUSHROOM_STEM),
            new StraightTrunkPlacer(5, 2 , 0),
            BlockStateProvider.of(ModNatureBlocks.BROWN_BOLETE_BLOCK),
            new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 2),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(ModBlocks.STONE_MYCELIUM)).build());

        register(context, CAVE_AMANITA_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(MushroomBlockSets.GRAY_MUSHROOM.stem()),
            new StraightTrunkPlacer(3, 2 , 0),
            BlockStateProvider.of(ModNatureBlocks.CAVE_AMANITA_BLOCK),
            new PineFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), ConstantIntProvider.create(2)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(ModBlocks.STONE_MYCELIUM)).build());

        register(context, DEEP_FIRECAP_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(MushroomBlockSets.GRAY_MUSHROOM.stem()),
            new StraightTrunkPlacer(5, 2 , 0),
            BlockStateProvider.of(ModNatureBlocks.DEEP_FIRECAP_BLOCK),
            new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 2),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(ModBlocks.STONE_MYCELIUM)).build());

        register(context, SKY_FIRECAP_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(MushroomBlockSets.DARK_MUSHROOM.stem()),
            new StraightTrunkPlacer(5, 2 , 0),
            BlockStateProvider.of(ModNatureBlocks.SKY_FIRECAP_BLOCK),
            new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 2),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(ModBlocks.STONE_MYCELIUM)).build());

        register(context, YELLOW_AMANITA_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.MUSHROOM_STEM),
            new StraightTrunkPlacer(3, 2 , 0),
            BlockStateProvider.of(ModNatureBlocks.YELLOW_AMANITA_BLOCK),
            new PineFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), ConstantIntProvider.create(2)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(ModBlocks.STONE_MYCELIUM)).build());
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(MiddleEarth.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(
            Registerable<ConfiguredFeature<?, ?>> context,
            RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
