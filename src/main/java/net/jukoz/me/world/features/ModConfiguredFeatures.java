package net.jukoz.me.world.features;

import com.google.common.collect.ImmutableList;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlockTags;
import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.block.WoodBlockSets;
import net.jukoz.me.world.features.foliages.OvalFoliagePlacer;
import net.jukoz.me.world.features.roots.MirkwoodRootPlacement;
import net.jukoz.me.world.features.roots.MirkwoodRootPlacer;
import net.jukoz.me.world.features.trunks.LargeTrunkPlacer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.root.AboveRootPlacement;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.LeavesVineTreeDecorator;

import java.util.Optional;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> MIRKWOOD_TREE_KEY = registerKey("mirkwood_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MEGA_MIRKWOOD_TREE_KEY = registerKey("mega_mirkwood_tree");


    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RegistryEntryLookup<Block> registryEntryLookup = context.getRegistryLookup(RegistryKeys.BLOCK);

        register(context, MIRKWOOD_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(WoodBlockSets.MIRKWOOD.wood()),
                new LargeTrunkPlacer(14, 2, 1.1f, 0.4f, 3.2f, 2, 0.28f),
                BlockStateProvider.of(WoodBlockSets.MIRKWOOD.leaves()),
                new OvalFoliagePlacer(2, ConstantIntProvider.create(-1), ConstantIntProvider.create(3), 0.4f),
                Optional.of(new MirkwoodRootPlacer(UniformIntProvider.create(1, 3), BlockStateProvider.of(ModNatureBlocks.MIRKWOOD_ROOTS),
                        Optional.of(new AboveRootPlacement(BlockStateProvider.of(ModNatureBlocks.FOREST_MOSS_CARPET), 0.5F)),
                        new MirkwoodRootPlacement(registryEntryLookup.getOrThrow(ModBlockTags.MIRKWOOD_ROOTS_CAN_GROW_THROUGH),
                                RegistryEntryList.of(Block::getRegistryEntry, new Block[]{Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS}),
                                BlockStateProvider.of(Blocks.MUDDY_MANGROVE_ROOTS),
                                8, 12, 0.3F))),
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
                        new MirkwoodRootPlacement(registryEntryLookup.getOrThrow(ModBlockTags.MIRKWOOD_ROOTS_CAN_GROW_THROUGH),
                                RegistryEntryList.of(Block::getRegistryEntry, new Block[]{Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS}),
                                BlockStateProvider.of(Blocks.MUDDY_MANGROVE_ROOTS),
                                13, 15, 0.3F))),
                new TwoLayersFeatureSize(1, 0, 2))
                .decorators(ImmutableList.of(new LeavesVineTreeDecorator(0.25F)))
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
