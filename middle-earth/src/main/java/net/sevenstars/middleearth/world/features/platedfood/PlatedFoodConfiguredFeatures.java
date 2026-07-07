package net.sevenstars.middleearth.world.features.platedfood;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.world.features.chain.ChainFeatureConfig;
import net.sevenstars.middleearth.world.gen.ModFeatures;

public class PlatedFoodConfiguredFeatures {
    // NOTE keep it here just in case
    //public static final RegistryKey<ConfiguredFeature<?, ?>> CERAMIC_PLATE = registerKey("ceramic_plate");

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(MiddleEarth.MOD_ID, name));
    }

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        //ConfiguredFeatures.register(featureRegisterable, CERAMIC_PLATE, ModFeatures.PLATED_FOOD,
        //        new PlatedFoodFeatureConfig(ModDecorativeBlocks.CERAMIC_PLATE.getDefaultState(), MiddleEarth.of("structures/shire/food")));

    }
}