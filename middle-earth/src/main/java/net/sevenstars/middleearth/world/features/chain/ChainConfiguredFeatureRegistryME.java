package net.sevenstars.middleearth.world.features.chain;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.feature.*;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.world.gen.FeatureRegistryME;

public class ChainConfiguredFeatureRegistryME {
    public static final RegistryKey<ConfiguredFeature<?, ?>> ROPE_LADDER_DOWN = registerKey("rope_ladder_down");

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, MiddleEarth.id(name));
    }

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        ConfiguredFeatures.register(featureRegisterable, ROPE_LADDER_DOWN, FeatureRegistryME.CHAIN,
                new ChainFeatureConfig(24, false, Direction.DOWN, Direction.EAST, DecorativeBlockRegistryME.ROPE_LADDER.getDefaultState()));

    }
}