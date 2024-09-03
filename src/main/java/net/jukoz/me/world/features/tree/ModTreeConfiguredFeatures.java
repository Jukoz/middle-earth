package net.jukoz.me.world.features.tree;

import com.google.common.collect.ImmutableList;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.block.WoodBlockSets;
import net.jukoz.me.world.features.tree.foliages.OvalFoliagePlacer;
import net.jukoz.me.world.features.tree.foliages.PalmFoliagePlacer;
import net.jukoz.me.world.features.tree.trunks.ArcTrunkPlacer;
import net.jukoz.me.world.features.tree.trunks.CanopyTrunkPlacer;
import net.jukoz.me.world.features.tree.trunks.LargeTrunkPlacer;
import net.jukoz.me.world.features.tree.trunks.SpruceTrunkPlacer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.BushFoliagePlacer;
import net.minecraft.world.gen.foliage.PineFoliagePlacer;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.LeavesVineTreeDecorator;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.*;

public class ModTreeConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> BEECH_TREE_KEY = registerKey("beech_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BIRCH_TREE_KEY = registerKey("birch_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CHESTNUT_TREE_KEY = registerKey("chestnut_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DARK_OAK_TREE_KEY = registerKey("dark_oak_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEGA_DARK_OAK_TREE_KEY = registerKey("mega_dark_oak_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEGA_BIRCH_TREE_KEY = registerKey("mega_birch_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> HOLLY_TREE_KEY = registerKey("holy_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> LARCH_TREE_KEY = registerKey("larch_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BLACK_LEBETHRON_TREE_KEY = registerKey("black_lebethron_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> WHITE_LEBETHRON_TREE_KEY = registerKey("white_lebethron_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_MIRKWOOD_TREE_KEY = registerKey("small_mirkwood_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MIRKWOOD_TREE_KEY = registerKey("mirkwood_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEGA_MIRKWOOD_TREE_KEY = registerKey("mega_mirkwood_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MALLORN_TREE_KEY = registerKey("mallorn_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_MALLORN_TREE_KEY = registerKey("small_mallorn_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MALLORN_BUSH_KEY = registerKey("mallorn_bush");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEGA_MALLORN_TREE_KEY = registerKey("mega_mallorn_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MAPLE_TREE_KEY = registerKey("maple_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> YELLOW_MAPLE_TREE_KEY = registerKey("yellow_maple_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORANGE_MAPLE_TREE_KEY = registerKey("orange_maple_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> RED_MAPLE_TREE_KEY = registerKey("red_maple_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SILVER_MAPLE_TREE_KEY = registerKey("silver_maple_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SILVER_YELLOW_MAPLE_TREE_KEY = registerKey("silver_yellow_maple_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SILVER_ORANGE_MAPLE_TREE_KEY = registerKey("silver_orange_maple_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SILVER_RED_MAPLE_TREE_KEY = registerKey("silver_red_maple_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> OAK_BUSH_TREE_KEY = registerKey("silver_oak_bush_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> OAK_TREE_KEY = registerKey("oak_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> OAK_TREE_VINES_KEY = registerKey("oak_vines_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> OAK_SMALL_TREE_VINES_KEY = registerKey("oak_small_vines_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEGA_OAK_TREE_KEY = registerKey("mega_oak_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PALM_TREE_KEY = registerKey("palm_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> WHITE_PALM_TREE_KEY = registerKey("white_palm_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PINE_TREE_KEY = registerKey("pine_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DEAD_PINE_TREE_KEY = registerKey("dead_pine_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DRY_PINE_TREE_KEY = registerKey("dry_pine_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DRY_PINE_BUSH_TREE_KEY = registerKey("dry_pine_bush_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BLACK_PINE_TREE_KEY = registerKey("black_pine_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DEAD_BLACK_PINE_TREE_KEY = registerKey("dead_black_pine_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SPRUCE_TREE_KEY = registerKey("spruce_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SPRUCE_BUSH_TREE_KEY = registerKey("spruce_bush_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> WILLOW_TREE_KEY = registerKey("willow_tree");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RegistryEntryLookup<Block> registryEntryLookup = context.getRegistryLookup(RegistryKeys.BLOCK);
        final ArrayList<BlockState> emptyList = new ArrayList<>();
        final ArrayList<BlockState> mallornLeaves = new ArrayList<>(Arrays.asList(WoodBlockSets.MALLORN.leaves().getDefaultState(), WoodBlockSets.MALLORN.leaves().getDefaultState(), ModNatureBlocks.FLOWERING_MALLORN_LEAVES.getDefaultState()));
        final ArrayList<BlockState> hollyLeaves = new ArrayList<>(Arrays.asList(WoodBlockSets.HOLLY.leaves().getDefaultState(), WoodBlockSets.HOLLY.leaves().getDefaultState(), ModNatureBlocks.BERRY_HOLLY_LEAVES.getDefaultState()));

        register(context, BEECH_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.BEECH.log()),
            new CanopyTrunkPlacer(12, 2, 0.91f, 0.87f, 5.0f, 3, 0.42f, -0.1f, 1,1),
            BlockStateProvider.of(WoodBlockSets.BEECH.leaves()),
            new OvalFoliagePlacer(3, ConstantIntProvider.create(0), ConstantIntProvider.create(2), emptyList,0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, BIRCH_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.BIRCH_LOG),
            new CanopyTrunkPlacer(15, 2, 0.95f, 0.9f, 4.3f, 3, 0.37f,  0.025f,1,1),
            BlockStateProvider.of(Blocks.BIRCH_LEAVES),
            new OvalFoliagePlacer(3, ConstantIntProvider.create(0), ConstantIntProvider.create(2), emptyList,0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, MEGA_BIRCH_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.BIRCH_LOG),
            new CanopyTrunkPlacer(18, 3, 1.0f, 0.67f, 5.2f, 3, 0.44f, -0.05f, 2, 1),
            BlockStateProvider.of(Blocks.BIRCH_LEAVES),
            new OvalFoliagePlacer(2, ConstantIntProvider.create(0), ConstantIntProvider.create(3), emptyList,0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, CHESTNUT_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.CHESTNUT.log()),
            new CanopyTrunkPlacer(7, 2, 0.9f, 0.87f, 8.6f, 2, 0.4f, -0.15f, 0,1),
            BlockStateProvider.of(WoodBlockSets.CHESTNUT.leaves()),
            new OvalFoliagePlacer(2, ConstantIntProvider.create(0), ConstantIntProvider.create(2), emptyList,0.14f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, DARK_OAK_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.DARK_OAK_LOG),
            new CanopyTrunkPlacer(11, 2, 0.97f, 0.87f, 5.0f, 3, 0.42f, -0.1f, 1,1),
            BlockStateProvider.of(Blocks.DARK_OAK_LEAVES),
            new OvalFoliagePlacer(2, ConstantIntProvider.create(0), ConstantIntProvider.create(3), emptyList, 0.3f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, MEGA_DARK_OAK_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.DARK_OAK_LOG),
            new CanopyTrunkPlacer(21, 3, 1.8f, 0.55f, 6.1f, 3, 0.44f, -0.15f, 2, 0),
            BlockStateProvider.of(Blocks.DARK_OAK_LEAVES),
            new OvalFoliagePlacer(2, ConstantIntProvider.create(0), ConstantIntProvider.create(3), emptyList,0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, HOLLY_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.HOLLY.log()),
            new CanopyTrunkPlacer(9, 2, 0.9f, 0.87f, 8.6f, 1, 0.4f, -0.15f, 0,1),
            BlockStateProvider.of(WoodBlockSets.HOLLY.leaves()),
            new OvalFoliagePlacer(3, ConstantIntProvider.create(-1), ConstantIntProvider.create(2), hollyLeaves, 0.17f),
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
            new OvalFoliagePlacer(2, ConstantIntProvider.create(0), ConstantIntProvider.create(2), emptyList, 0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, WHITE_LEBETHRON_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.WHITE_LEBETHRON.log()),
            new CanopyTrunkPlacer(10, 2, 0.9f, 0.87f, 8.6f, 3, 0.42f, -0.15f, 0,1),
            BlockStateProvider.of(ModNatureBlocks.LEBETHRON_LEAVES),
            new OvalFoliagePlacer(2, ConstantIntProvider.create(0), ConstantIntProvider.create(2), emptyList, 0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, MALLORN_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.MALLORN.log()),
            new CanopyTrunkPlacer(16, 2, 0.9f, 0.87f, 5.2f, 3, 0.45f, -0.15f, 0,1),
            BlockStateProvider.of(WoodBlockSets.MALLORN.leaves()),
            new OvalFoliagePlacer(2, ConstantIntProvider.create(0), ConstantIntProvider.create(3), mallornLeaves,0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, SMALL_MALLORN_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.MALLORN.log()),
            new CanopyTrunkPlacer(9, 2, 0.9f, 0.87f, 5.2f, 2, 0.45f, -0.15f, 0,1),
            BlockStateProvider.of(WoodBlockSets.MALLORN.leaves()),
            new OvalFoliagePlacer(2, ConstantIntProvider.create(0), ConstantIntProvider.create(3), mallornLeaves,  0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, MEGA_MALLORN_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.MALLORN.log()),
            new CanopyTrunkPlacer(34, 3, 1.6f, 0.56f, 8.3f, 4, 0.48f, 0f, 2,1),
            BlockStateProvider.of(WoodBlockSets.MALLORN.leaves()),
            new OvalFoliagePlacer(3, ConstantIntProvider.create(-1), ConstantIntProvider.create(4), mallornLeaves,  0.7f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, MALLORN_BUSH_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.MALLORN.log()),
            new StraightTrunkPlacer(1, 0, 0), BlockStateProvider.of(WoodBlockSets.MALLORN.leaves()),
            new BushFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2),
            new TwoLayersFeatureSize(0, 0, 0)).build());

        CanopyTrunkPlacer mapleTrunk = new CanopyTrunkPlacer(11, 2, 0.91f, 0.87f,
                5.1f, 2, 0.37f, -0.1f, 1,1);
        OvalFoliagePlacer mapleFoliage = new OvalFoliagePlacer(2, ConstantIntProvider.create(0), ConstantIntProvider.create(3), emptyList, 0.3f);

        register(context, MAPLE_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.MAPLE.log()),
            mapleTrunk,
            BlockStateProvider.of(ModNatureBlocks.MAPLE_LEAVES),
            mapleFoliage,
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, YELLOW_MAPLE_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.MAPLE.log()),
            mapleTrunk,
            BlockStateProvider.of(ModNatureBlocks.YELLOW_MAPLE_LEAVES),
            mapleFoliage,
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, ORANGE_MAPLE_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.MAPLE.log()),
            mapleTrunk,
            BlockStateProvider.of(ModNatureBlocks.ORANGE_MAPLE_LEAVES),
            mapleFoliage,
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, RED_MAPLE_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.MAPLE.log()),
            mapleTrunk,
            BlockStateProvider.of(ModNatureBlocks.RED_MAPLE_LEAVES),
            mapleFoliage,
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, SILVER_MAPLE_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.SILVER_MAPLE.log()),
            mapleTrunk,
            BlockStateProvider.of(ModNatureBlocks.MAPLE_LEAVES),
            mapleFoliage,
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, SILVER_YELLOW_MAPLE_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.SILVER_MAPLE.log()),
            mapleTrunk,
            BlockStateProvider.of(ModNatureBlocks.YELLOW_MAPLE_LEAVES),
            mapleFoliage,
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, SILVER_ORANGE_MAPLE_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.SILVER_MAPLE.log()),
            mapleTrunk,
            BlockStateProvider.of(ModNatureBlocks.ORANGE_MAPLE_LEAVES),
            mapleFoliage,
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, SILVER_RED_MAPLE_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.SILVER_MAPLE.log()),
            mapleTrunk,
            BlockStateProvider.of(ModNatureBlocks.RED_MAPLE_LEAVES),
            mapleFoliage,
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, SMALL_MIRKWOOD_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.MIRKWOOD.wood()),
            new CanopyTrunkPlacer(7, 2, 0.9f, 0.87f, 3.2f, 1, 0.28f, -0.15f, 0, 0),
            BlockStateProvider.of(WoodBlockSets.MIRKWOOD.leaves()),
            new OvalFoliagePlacer(2, ConstantIntProvider.create(-1), ConstantIntProvider.create(2), emptyList, 0.3f),
            new TwoLayersFeatureSize(1, 0, 2))
            .decorators(ImmutableList.of(new LeavesVineTreeDecorator(0.1F)))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, MIRKWOOD_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.MIRKWOOD.wood()),
            new LargeTrunkPlacer(14, 2, 1.1f, 0.55f, 3.2f, 2, 0.28f),
            BlockStateProvider.of(WoodBlockSets.MIRKWOOD.leaves()),
            new OvalFoliagePlacer(2, ConstantIntProvider.create(-1), ConstantIntProvider.create(3), emptyList, 0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .decorators(ImmutableList.of(new LeavesVineTreeDecorator(0.25F)))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, MEGA_MIRKWOOD_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.MIRKWOOD.log()),
            new LargeTrunkPlacer(27, 3, 2.3f, 0.6f, 6.2f, 5, 0.25f),
            BlockStateProvider.of(WoodBlockSets.MIRKWOOD.leaves()),
            new OvalFoliagePlacer(3, ConstantIntProvider.create(-1), ConstantIntProvider.create(4), emptyList, 0.5f),
            new TwoLayersFeatureSize(1, 0, 2))
            .decorators(ImmutableList.of(new LeavesVineTreeDecorator(0.25F)))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, OAK_BUSH_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.OAK_LOG),
            new StraightTrunkPlacer(1, 0, 0), BlockStateProvider.of(Blocks.OAK_LEAVES),
            new BushFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2),
            new TwoLayersFeatureSize(0, 0, 0)).build());


        register(context, OAK_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.OAK_LOG),
            new CanopyTrunkPlacer(12, 2, 0.91f, 0.87f, 5.0f, 3, 0.42f, -0.1f, 1,1),
            BlockStateProvider.of(Blocks.OAK_LEAVES),
            new OvalFoliagePlacer(2, ConstantIntProvider.create(0), ConstantIntProvider.create(3), emptyList, 0.3f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, OAK_TREE_VINES_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.OAK_LOG),
            new CanopyTrunkPlacer(10, 2, 0.91f, 0.87f, 5.0f, 3, 0.42f, -0.1f, 1,1),
            BlockStateProvider.of(Blocks.OAK_LEAVES),
            new OvalFoliagePlacer(2, ConstantIntProvider.create(0), ConstantIntProvider.create(3), emptyList, 0.3f),
            new TwoLayersFeatureSize(1, 0, 2))
            .decorators(ImmutableList.of(new LeavesVineTreeDecorator(0.25F)))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, OAK_SMALL_TREE_VINES_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.OAK_LOG),
            new StraightTrunkPlacer(4, 2, 0),
            BlockStateProvider.of(Blocks.OAK_LEAVES),
            new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
            new TwoLayersFeatureSize(1, 0, 2))
            .decorators(ImmutableList.of(new LeavesVineTreeDecorator(0.25F)))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, MEGA_OAK_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.OAK_LOG),
            new CanopyTrunkPlacer(20, 3, 1.8f, 0.55f, 5.7f, 3, 0.38f, -0.15f, 2, 0),
            BlockStateProvider.of(Blocks.OAK_LEAVES),
            new OvalFoliagePlacer(2, ConstantIntProvider.create(0), ConstantIntProvider.create(3), emptyList, 0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, PALM_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.PALM.log()),
            new ArcTrunkPlacer(10, 2, 0.02f, 0.07f, 0),
            BlockStateProvider.of(WoodBlockSets.PALM.leaves()),
            new PalmFoliagePlacer(4, ConstantIntProvider.create(0), ConstantIntProvider.create(1), -0.3f, 0.3f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, WHITE_PALM_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.WHITE_PALM.log()),
            new ArcTrunkPlacer(10, 2, 0.02f, 0.07f, 0),
            BlockStateProvider.of(WoodBlockSets.PALM.leaves()),
            new PalmFoliagePlacer(4, ConstantIntProvider.create(0), ConstantIntProvider.create(1), -0.3f, 0.3f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, PINE_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.PINE.log()),
            new SpruceTrunkPlacer(14, 3),
            BlockStateProvider.of(WoodBlockSets.PINE.leaves()),
            new PineFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(1), UniformIntProvider.create(3, 4)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, DEAD_PINE_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.PINE.log()),
            new SpruceTrunkPlacer(14, 3),
            BlockStateProvider.of(Blocks.AIR),
            new PineFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(1), ConstantIntProvider.create(1)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, DRY_PINE_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.PINE.log()),
            new SpruceTrunkPlacer(14, 3),
            BlockStateProvider.of(ModNatureBlocks.DRY_PINE_LEAVES),
            new PineFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(1), UniformIntProvider.create(2, 3)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, DRY_PINE_BUSH_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(ModNatureBlocks.PINE_BRANCHES),
            new StraightTrunkPlacer(1, 0, 0), BlockStateProvider.of(ModNatureBlocks.DRY_PINE_LEAVES),
            new BushFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2),
            new TwoLayersFeatureSize(0, 0, 0)).build());

        register(context, BLACK_PINE_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.BLACK_PINE.log()),
            new SpruceTrunkPlacer(13, 3),
            BlockStateProvider.of(WoodBlockSets.BLACK_PINE.leaves()),
            new PineFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(1), UniformIntProvider.create(3, 4)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, DEAD_BLACK_PINE_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.BLACK_PINE.log()),
            new SpruceTrunkPlacer(13, 3),
            BlockStateProvider.of(Blocks.AIR),
            new PineFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(1), ConstantIntProvider.create(1)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, SPRUCE_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.SPRUCE_LOG),
            new StraightTrunkPlacer(14, 2, 0),
            BlockStateProvider.of(Blocks.SPRUCE_LEAVES),
            new SpruceFoliagePlacer(ConstantIntProvider.create(3), UniformIntProvider.create(0, 1), ConstantIntProvider.create(2)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, SPRUCE_BUSH_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.SPRUCE_LOG),
            new StraightTrunkPlacer(1, 0, 0), BlockStateProvider.of(Blocks.SPRUCE_LEAVES),
            new BushFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2),
            new TwoLayersFeatureSize(0, 0, 0)).build());

        register(context, WILLOW_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WoodBlockSets.WILLOW.log()),
            new LargeTrunkPlacer(13, 2, 1.2f, 0.67f, 6.0f, 3, 0.32f),
            BlockStateProvider.of(WoodBlockSets.WILLOW.leaves()),
            new OvalFoliagePlacer(2, ConstantIntProvider.create(-1), ConstantIntProvider.create(3), emptyList, 0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .decorators(ImmutableList.of(new LeavesVineTreeDecorator(0.35F)))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
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
