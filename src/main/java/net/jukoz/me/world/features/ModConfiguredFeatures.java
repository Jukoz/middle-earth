package net.jukoz.me.world.features;

import com.google.common.collect.ImmutableList;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.block.WoodBlockSets;
import net.jukoz.me.world.features.foliages.OvalFoliagePlacer;
import net.jukoz.me.world.features.foliages.PalmFoliagePlacer;
import net.jukoz.me.world.features.roots.MirkwoodRootPlacement;
import net.jukoz.me.world.features.roots.MirkwoodRootPlacer;
import net.jukoz.me.world.features.trunks.ArcTrunkPlacer;
import net.jukoz.me.world.features.trunks.CanopyTrunkPlacer;
import net.jukoz.me.world.features.trunks.LargeTrunkPlacer;
import net.jukoz.me.world.features.trunks.SpruceTrunkPlacer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.PineFoliagePlacer;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.root.AboveRootPlacement;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.LeavesVineTreeDecorator;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.Optional;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> BEECH_TREE_KEY = registerKey("beech_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BIRCH_TREE_KEY = registerKey("birch_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEGA_DARK_OAK_TREE_KEY = registerKey("mega_dark_oak_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEGA_BIRCH_TREE_KEY = registerKey("mega_birch_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> LARCH_TREE_KEY = registerKey("larch_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BLACK_LEBETHRON_TREE_KEY = registerKey("black_lebethron_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> WHITE_LEBETHRON_TREE_KEY = registerKey("white_lebethron_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MIRKWOOD_TREE_KEY = registerKey("mirkwood_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEGA_MIRKWOOD_TREE_KEY = registerKey("mega_mirkwood_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MALLORN_TREE_KEY = registerKey("mallorn_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEGA_MALLORN_TREE_KEY = registerKey("mega_mallorn_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MAPLE_TREE_KEY = registerKey("maple_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> OAK_TREE_KEY = registerKey("oak_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEGA_OAK_TREE_KEY = registerKey("mega_oak_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PALM_TREE_KEY = registerKey("palm_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PINE_TREE_KEY = registerKey("pine_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SPRUCE_TREE_KEY = registerKey("spruce_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> WILLOW_TREE_KEY = registerKey("willow_tree");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RegistryEntryLookup<Block> registryEntryLookup = context.getRegistryLookup(RegistryKeys.BLOCK);

        register(context, BEECH_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.LARCH.log()),
            new CanopyTrunkPlacer(12, 2, 0.91f, 0.87f, 5.0f, 3, 0.42f, -0.1f, 1,1),
            BlockStateProvider.of(WoodBlockSets.LARCH.leaves()),
            new OvalFoliagePlacer(3, ConstantIntProvider.create(0), ConstantIntProvider.create(2), 0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, BIRCH_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.BIRCH_LOG),
            new CanopyTrunkPlacer(15, 2, 0.95f, 0.9f, 4.3f, 3, 0.37f,  0.025f,1,1),
            BlockStateProvider.of(Blocks.BIRCH_LEAVES),
            new OvalFoliagePlacer(3, ConstantIntProvider.create(0), ConstantIntProvider.create(2), 0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, MEGA_BIRCH_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.BIRCH_LOG),
            new CanopyTrunkPlacer(18, 3, 1.0f, 0.67f, 5.2f, 3, 0.44f, -0.05f, 2, 1),
            BlockStateProvider.of(Blocks.BIRCH_LEAVES),
            new OvalFoliagePlacer(2, ConstantIntProvider.create(0), ConstantIntProvider.create(3), 0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, MEGA_DARK_OAK_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.DARK_OAK_LOG),
            new CanopyTrunkPlacer(21, 3, 1.8f, 0.55f, 6.1f, 3, 0.44f, -0.15f, 2, 0),
            BlockStateProvider.of(Blocks.DARK_OAK_LEAVES),
            new OvalFoliagePlacer(2, ConstantIntProvider.create(0), ConstantIntProvider.create(3), 0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, LARCH_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.LARCH.log()),
            new StraightTrunkPlacer(11, 2 , 1),
            BlockStateProvider.of(WoodBlockSets.LARCH.leaves()),
            new SpruceFoliagePlacer(ConstantIntProvider.create(3), UniformIntProvider.create(0, 2), UniformIntProvider.create(2, 3)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, BLACK_LEBETHRON_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.BLACK_LEBETHRON.log()),
            new CanopyTrunkPlacer(10, 2, 0.9f, 0.87f, 8.6f, 3, 0.42f, -0.15f, 0,1),
            BlockStateProvider.of(ModNatureBlocks.LEBETHRON_LEAVES),
            new OvalFoliagePlacer(2, ConstantIntProvider.create(0), ConstantIntProvider.create(2), 0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, WHITE_LEBETHRON_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.WHITE_LEBETHRON.log()),
            new CanopyTrunkPlacer(10, 2, 0.9f, 0.87f, 8.6f, 3, 0.42f, -0.15f, 0,1),
            BlockStateProvider.of(ModNatureBlocks.LEBETHRON_LEAVES),
            new OvalFoliagePlacer(2, ConstantIntProvider.create(0), ConstantIntProvider.create(2), 0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, MALLORN_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.MALLORN.log()),
            new CanopyTrunkPlacer(16, 2, 0.9f, 0.87f, 5.2f, 3, 0.42f, -0.15f, 0,1),
            BlockStateProvider.of(WoodBlockSets.MALLORN.leaves()),
            new OvalFoliagePlacer(2, ConstantIntProvider.create(0), ConstantIntProvider.create(3), 0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, MEGA_MALLORN_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.MALLORN.log()),
            new CanopyTrunkPlacer(29, 3, 2.8f, 0.67f, 8.1f, 3, 0.44f, -0.15f, 2,1),
            BlockStateProvider.of(WoodBlockSets.MALLORN.leaves()),
            new OvalFoliagePlacer(3, ConstantIntProvider.create(-1), ConstantIntProvider.create(4), 0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, MAPLE_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.MAPLE.log()),
            new CanopyTrunkPlacer(11, 2, 1.2f, 0.87f, 5.1f, 2, 0.35f, -0.15f, 1,0),
            BlockStateProvider.of(WoodBlockSets.MAPLE.leaves()),
            new OvalFoliagePlacer(2, ConstantIntProvider.create(0), ConstantIntProvider.create(3), 0.3f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, MIRKWOOD_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.MIRKWOOD.wood()),
            new LargeTrunkPlacer(14, 2, 1.1f, 0.4f, 3.2f, 2, 0.28f),
            BlockStateProvider.of(WoodBlockSets.MIRKWOOD.leaves()),
            new OvalFoliagePlacer(2, ConstantIntProvider.create(-1), ConstantIntProvider.create(3), 0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .decorators(ImmutableList.of(new LeavesVineTreeDecorator(0.25F)))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, MEGA_MIRKWOOD_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.MIRKWOOD.log()),
            new LargeTrunkPlacer(27, 3, 2.5f, 0.5f, 6.2f, 5, 0.25f),
            BlockStateProvider.of(WoodBlockSets.MIRKWOOD.leaves()),
            new OvalFoliagePlacer(3, ConstantIntProvider.create(-1), ConstantIntProvider.create(4), 0.5f),
            new TwoLayersFeatureSize(1, 0, 2))
            .decorators(ImmutableList.of(new LeavesVineTreeDecorator(0.25F)))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, OAK_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.OAK_LOG),
            new CanopyTrunkPlacer(12, 2, 0.91f, 0.87f, 5.0f, 3, 0.42f, -0.1f, 1,1),
            BlockStateProvider.of(Blocks.OAK_LEAVES),
            new OvalFoliagePlacer(2, ConstantIntProvider.create(0), ConstantIntProvider.create(3), 0.3f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, MEGA_OAK_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.OAK_LOG),
            new CanopyTrunkPlacer(20, 3, 1.8f, 0.55f, 5.7f, 3, 0.38f, -0.15f, 2, 0),
            BlockStateProvider.of(Blocks.OAK_LEAVES),
            new OvalFoliagePlacer(2, ConstantIntProvider.create(0), ConstantIntProvider.create(3), 0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, PALM_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.PALM.log()),
            new ArcTrunkPlacer(10, 2, 0.07f, 0),
            BlockStateProvider.of(WoodBlockSets.PALM.leaves()),
            new PalmFoliagePlacer(6, ConstantIntProvider.create(0), ConstantIntProvider.create(2), -0.3f, 0.3f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, PINE_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.PINE.log()),
            new SpruceTrunkPlacer(14, 3),
            BlockStateProvider.of(WoodBlockSets.PINE.leaves()),
            new PineFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(1), UniformIntProvider.create(3, 4)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, SPRUCE_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.SPRUCE_LOG),
            new SpruceTrunkPlacer(13, 2),
            BlockStateProvider.of(Blocks.SPRUCE_LEAVES),
            new SpruceFoliagePlacer(ConstantIntProvider.create(3), UniformIntProvider.create(0, 2), UniformIntProvider.create(2, 3)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, WILLOW_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.WILLOW.log()),
            new LargeTrunkPlacer(13, 2, 1.2f, 0.67f, 6.0f, 3, 0.32f),
            BlockStateProvider.of(WoodBlockSets.WILLOW.leaves()),
            new OvalFoliagePlacer(2, ConstantIntProvider.create(-1), ConstantIntProvider.create(3), 0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .decorators(ImmutableList.of(new LeavesVineTreeDecorator(0.35F)))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(MiddleEarth.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(
            Registerable<ConfiguredFeature<?, ?>> context,
            RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
