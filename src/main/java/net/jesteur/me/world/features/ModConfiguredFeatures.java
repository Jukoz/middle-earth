package net.jesteur.me.world.features;

import net.jesteur.me.MiddleEarth;
import net.jesteur.me.block.WoodBlockSets;
import net.minecraft.block.Blocks;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.DarkOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> MIRKWOOD_TREE_KEY = registerKey("mirkwood_tree");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        register(context, MIRKWOOD_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(WoodBlockSets.MIRKWOOD.log()),
                new BigTrunkPlacer(17, 2, 0),
                BlockStateProvider.of(WoodBlockSets.MIRKWOOD.leaves()),
                new DarkOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0)),
                new TwoLayersFeatureSize(1, 0, 2)).dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
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
