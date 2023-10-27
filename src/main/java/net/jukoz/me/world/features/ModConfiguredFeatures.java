package net.jukoz.me.world.features;

import com.google.common.collect.ImmutableList;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.block.WoodBlockSets;
import net.jukoz.me.world.features.foliages.OvalFoliagePlacer;
import net.jukoz.me.world.features.roots.MirkwoodRootPlacement;
import net.jukoz.me.world.features.roots.MirkwoodRootPlacer;
import net.jukoz.me.world.features.trunks.LargeTrunkPlacer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PropaguleBlock;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;
import net.minecraft.world.gen.root.AboveRootPlacement;
import net.minecraft.world.gen.root.MangroveRootPlacement;
import net.minecraft.world.gen.root.MangroveRootPlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.RandomizedIntBlockStateProvider;
import net.minecraft.world.gen.treedecorator.AttachedToLeavesTreeDecorator;
import net.minecraft.world.gen.treedecorator.LeavesVineTreeDecorator;
import net.minecraft.world.gen.trunk.UpwardsBranchingTrunkPlacer;

import java.util.List;
import java.util.Optional;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> MIRKWOOD_TREE_KEY = registerKey("mirkwood_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEGA_MIRKWOOD_TREE_KEY = registerKey("mega_mirkwood_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MALLORN_TREE_KEY = registerKey("mallorn_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEGA_MALLORN_TREE_KEY = registerKey("mega_mallorn_tree");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RegistryEntryLookup<Block> registryEntryLookup = context.getRegistryLookup(RegistryKeys.BLOCK);

        register(context, MIRKWOOD_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(WoodBlockSets.MIRKWOOD.wood()),
                new LargeTrunkPlacer(14, 2, 1.1f, 0.4f, 3.2f, 2, 0.28f),
                BlockStateProvider.of(WoodBlockSets.MIRKWOOD.leaves()),
                new OvalFoliagePlacer(2, ConstantIntProvider.create(-1), ConstantIntProvider.create(3), 0.4f),
                Optional.of(new MirkwoodRootPlacer(UniformIntProvider.create(2, 5), BlockStateProvider.of(ModNatureBlocks.MIRKWOOD_ROOTS),
                        Optional.of(new AboveRootPlacement(BlockStateProvider.of(ModNatureBlocks.FOREST_MOSS_CARPET), 0.5F)),
                        new MirkwoodRootPlacement(
                                RegistryEntryList.of(Block::getRegistryEntry, WoodBlockSets.MIRKWOOD.wood(), WoodBlockSets.MIRKWOOD.log()),
                                RegistryEntryList.of(Block::getRegistryEntry),
                                BlockStateProvider.of(Blocks.MUDDY_MANGROVE_ROOTS),
                                13, 15, 0.3F))),
                new TwoLayersFeatureSize(1, 0, 2))
                .decorators(ImmutableList.of(new LeavesVineTreeDecorator(0.25F)))
                .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, MEGA_MIRKWOOD_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(WoodBlockSets.MIRKWOOD.wood()),
                new LargeTrunkPlacer(27, 3, 2.5f, 0.5f, 6.2f, 5, 0.25f),
                BlockStateProvider.of(WoodBlockSets.MIRKWOOD.leaves()),
                new OvalFoliagePlacer(3, ConstantIntProvider.create(-1), ConstantIntProvider.create(4), 0.5f),
                Optional.of(new MirkwoodRootPlacer(UniformIntProvider.create(2, 5), BlockStateProvider.of(ModNatureBlocks.MIRKWOOD_ROOTS),
                        Optional.of(new AboveRootPlacement(BlockStateProvider.of(ModNatureBlocks.FOREST_MOSS_CARPET), 0.5F)),
                        new MirkwoodRootPlacement(
                                RegistryEntryList.of(Block::getRegistryEntry, WoodBlockSets.MIRKWOOD.wood(), WoodBlockSets.MIRKWOOD.log()),
                                RegistryEntryList.of(Block::getRegistryEntry),
                                BlockStateProvider.of(Blocks.MUDDY_MANGROVE_ROOTS),
                                13, 15, 0.3F))),
                new TwoLayersFeatureSize(1, 0, 2))
                .decorators(ImmutableList.of(new LeavesVineTreeDecorator(0.25F)))
                .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, MALLORN_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(WoodBlockSets.MALLORN.wood()),
                new BigTrunkPlacer(16, 2, 1.0f, 0.4f, 1.6f, 2, 0.26f),
                BlockStateProvider.of(WoodBlockSets.MALLORN.leaves()),
                new OvalFoliagePlacer(2, ConstantIntProvider.create(-1), ConstantIntProvider.create(3), 0.4f),
                new TwoLayersFeatureSize(1, 0, 2))
                .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, MEGA_MALLORN_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(WoodBlockSets.MALLORN.wood()),
                new BigTrunkPlacer(29, 3, 2.5f, 0.5f, 3.2f, 5, 0.23f),
                BlockStateProvider.of(WoodBlockSets.MALLORN.leaves()),
                new OvalFoliagePlacer(3, ConstantIntProvider.create(-1), ConstantIntProvider.create(4), 0.5f),
                new TwoLayersFeatureSize(1, 0, 2))
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
