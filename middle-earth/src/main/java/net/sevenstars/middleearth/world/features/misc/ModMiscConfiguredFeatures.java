package net.sevenstars.middleearth.world.features.misc;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.StoneBlockSets;
import net.sevenstars.middleearth.world.features.columns.ColumnsFeatureConfig;
import net.sevenstars.middleearth.world.gen.ModFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.LakeFeature;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class ModMiscConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> LAVA_MAGMA_POOL = registerKey("lava_magma_pool");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_PUMICE_COLUMNS = registerKey("small_pumice_columns");
    public static final RegistryKey<ConfiguredFeature<?, ?>> LARGE_PUMICE_COLUMNS = registerKey("large_pumice_columns");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        ConfiguredFeatures.register(featureRegisterable, LAVA_MAGMA_POOL, Feature.LAKE,
                new LakeFeature.Config(BlockStateProvider.of(Blocks.LAVA.getDefaultState()), BlockStateProvider.of(Blocks.MAGMA_BLOCK.getDefaultState())));

        ConfiguredFeatures.register(featureRegisterable, SMALL_PUMICE_COLUMNS, ModFeatures.COLUMNS,
                new ColumnsFeatureConfig(ConstantIntProvider.create(1), UniformIntProvider.create(1, 4), StoneBlockSets.PUMICE.base().getDefaultState()));
        ConfiguredFeatures.register(featureRegisterable, LARGE_PUMICE_COLUMNS, ModFeatures.COLUMNS,
                new ColumnsFeatureConfig(UniformIntProvider.create(2, 3), UniformIntProvider.create(4, 7), StoneBlockSets.PUMICE.base().getDefaultState()));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(MiddleEarth.MOD_ID, name));
    }
}
