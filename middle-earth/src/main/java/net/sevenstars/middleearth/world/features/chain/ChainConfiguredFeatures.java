package net.sevenstars.middleearth.world.features.chain;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.feature.*;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.world.gen.ModFeatures;

public class ChainConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> ROPE_LADDER_DOWN = registerKey("rope_ladder_down");

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(MiddleEarth.MOD_ID, name));
    }

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        ConfiguredFeatures.register(featureRegisterable, ROPE_LADDER_DOWN, ModFeatures.CHAIN,
                new ChainFeatureConfig(24, false, Direction.DOWN, Direction.EAST, ModDecorativeBlocks.ROPE_LADDER.getDefaultState()));

    }
}