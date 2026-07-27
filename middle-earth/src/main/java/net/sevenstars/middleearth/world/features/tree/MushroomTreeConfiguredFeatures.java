package net.sevenstars.middleearth.world.features.tree;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.PineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.block.registration.WoodBlockSets;

public class MushroomTreeConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> BROWN_BOLETTE_TREE_KEY = registerKey("brown_bolette_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CAVE_AMANITA_TREE_KEY = registerKey("cave_amanita_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEP_FIRECAP_TREE_KEY = registerKey("deep_firecap_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKY_FIRECAP_TREE_KEY = registerKey("sky_firecap_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_AMANITA_TREE_KEY = registerKey("yellow_amanita_tree");


    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<Block> registryEntryLookup = context.lookup(Registries.BLOCK);

        register(context, BROWN_BOLETTE_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(Blocks.MUSHROOM_STEM),
            new StraightTrunkPlacer(5, 2 , 0),
            BlockStateProvider.simple(ModNatureBlocks.BROWN_BOLETE_BLOCK),
            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirt(BlockStateProvider.simple(ModBlocks.STONE_MYCELIUM)).build());

        register(context, CAVE_AMANITA_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(WoodBlockSets.GRAY_MUSHROOM_SET.mushroomStemBlocks.stem()),
            new StraightTrunkPlacer(3, 2 , 0),
            BlockStateProvider.simple(ModNatureBlocks.CAVE_AMANITA_BLOCK),
            new PineFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), ConstantInt.of(2)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirt(BlockStateProvider.simple(ModBlocks.STONE_MYCELIUM)).build());

        register(context, DEEP_FIRECAP_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(WoodBlockSets.GRAY_MUSHROOM_SET.mushroomStemBlocks.stem()),
            new StraightTrunkPlacer(5, 2 , 0),
            BlockStateProvider.simple(ModNatureBlocks.DEEP_FIRECAP_BLOCK),
            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirt(BlockStateProvider.simple(ModBlocks.STONE_MYCELIUM)).build());

        register(context, SKY_FIRECAP_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(WoodBlockSets.DARK_MUSHROOM_SET.mushroomStemBlocks.stem()),
            new StraightTrunkPlacer(5, 2 , 0),
            BlockStateProvider.simple(ModNatureBlocks.SKY_FIRECAP_BLOCK),
            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirt(BlockStateProvider.simple(ModBlocks.STONE_MYCELIUM)).build());

        register(context, YELLOW_AMANITA_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(Blocks.MUSHROOM_STEM),
            new StraightTrunkPlacer(3, 2 , 0),
            BlockStateProvider.simple(ModNatureBlocks.YELLOW_AMANITA_BLOCK),
            new PineFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), ConstantInt.of(2)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirt(BlockStateProvider.simple(ModBlocks.STONE_MYCELIUM)).build());
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
            BootstrapContext<ConfiguredFeature<?, ?>> context,
            ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
