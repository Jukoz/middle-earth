package net.sevenstars.middleearth.world.features.chain;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.world.gen.ModFeatures;

public class ChainConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ROPE_LADDER_DOWN = registerKey("rope_ladder_down");

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, MiddleEarth.of(name));
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> featureRegisterable) {
        FeatureUtils.register(featureRegisterable, ROPE_LADDER_DOWN, ModFeatures.CHAIN,
                new ChainFeatureConfig(24, false, Direction.DOWN, Direction.EAST, ModDecorativeBlocks.ROPE_LADDER.defaultBlockState()));

    }
}
