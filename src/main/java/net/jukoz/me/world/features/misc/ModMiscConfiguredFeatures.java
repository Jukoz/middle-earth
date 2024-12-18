package net.jukoz.me.world.features.misc;

import net.jukoz.me.MiddleEarth;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class ModMiscConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> LAVA_MAGMA_POOL = registerKey("lava_magma_pool");
    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        ConfiguredFeatures.register(featureRegisterable, LAVA_MAGMA_POOL, Feature.LAKE,
                new LakeFeature.Config(BlockStateProvider.of(Blocks.LAVA.getDefaultState()), BlockStateProvider.of(Blocks.MAGMA_BLOCK.getDefaultState())));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(MiddleEarth.MOD_ID, name));
    }
}
