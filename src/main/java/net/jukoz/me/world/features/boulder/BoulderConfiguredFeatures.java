package net.jukoz.me.world.features.boulder;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModNatureBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;

public class BoulderConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> ANDESITE_BOULDER = registerKey("andesite_boulder");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DIORITE_BOULDER = registerKey("diorite_boulder");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GRANITE_BOULDER = registerKey("granite_boulder");
    public static final RegistryKey<ConfiguredFeature<?, ?>> STONE_BOULDER = registerKey("stone_boulder");
    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        ConfiguredFeatures.register(featureRegisterable, ANDESITE_BOULDER, Feature.FOREST_ROCK,
                new SingleStateFeatureConfig(Blocks.ANDESITE.getDefaultState()));
        ConfiguredFeatures.register(featureRegisterable, DIORITE_BOULDER, Feature.FOREST_ROCK,
                new SingleStateFeatureConfig(Blocks.DIORITE.getDefaultState()));
        ConfiguredFeatures.register(featureRegisterable, GRANITE_BOULDER, Feature.FOREST_ROCK,
                new SingleStateFeatureConfig(Blocks.GRANITE.getDefaultState()));
        ConfiguredFeatures.register(featureRegisterable, STONE_BOULDER, Feature.FOREST_ROCK,
                new SingleStateFeatureConfig(Blocks.STONE.getDefaultState()));
    }


    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(MiddleEarth.MOD_ID, name));
    }
}