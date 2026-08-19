package net.sevenstars.middleearth.world.features.platedfood;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.world.features.chain.ChainFeatureConfig;
import net.sevenstars.middleearth.world.gen.ModFeatures;

public class PlatedFoodConfiguredFeatures {
    // NOTE keep it here just in case
    //public static final RegistryKey<ConfiguredFeature<?, ?>> CERAMIC_PLATE = registerKey("ceramic_plate");

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, name));
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> featureRegisterable) {
        //ConfiguredFeatures.register(featureRegisterable, CERAMIC_PLATE, ModFeatures.PLATED_FOOD,
        //        new PlatedFoodFeatureConfig(ModDecorativeBlocks.CERAMIC_PLATE.getDefaultState(), MiddleEarth.of("structures/shire/food")));

    }
}