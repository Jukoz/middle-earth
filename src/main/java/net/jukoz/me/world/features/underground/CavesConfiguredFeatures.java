package net.jukoz.me.world.features.underground;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class CavesConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> RED_AGATE_GEODE = registerKey("red_agate_geode");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {

        ConfiguredFeatures.register(featureRegisterable, RED_AGATE_GEODE, Feature.GEODE, new GeodeFeatureConfig(
                new GeodeLayerConfig(BlockStateProvider.of(Blocks.AIR), BlockStateProvider.of(ModBlocks.RED_AGATE_BLOCK), BlockStateProvider.of(ModBlocks.BUDDING_RED_AGATE),
                        BlockStateProvider.of(Blocks.CALCITE), BlockStateProvider.of(Blocks.SMOOTH_BASALT), List.of(ModBlocks.SMALL_RED_AGATE_BUD.getDefaultState(),
                        ModBlocks.MEDIUM_RED_AGATE_BUD.getDefaultState(), ModBlocks.LARGE_RED_AGATE_BUD.getDefaultState(), ModBlocks.RED_AGATE_CLUSTER.getDefaultState()),
                        BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS),
                new GeodeLayerThicknessConfig(1.7, 2.2, 3.2, 4.2),
                new GeodeCrackConfig(0.95, 2.0, 2), 0.35, 0.083,
                true, UniformIntProvider.create(4, 6), UniformIntProvider.create(3, 4), UniformIntProvider.create(1, 2),
                -16, 16, 0.05, 1));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(MiddleEarth.MOD_ID, name));
    }
}
