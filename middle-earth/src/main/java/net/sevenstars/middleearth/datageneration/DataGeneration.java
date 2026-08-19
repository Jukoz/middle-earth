package net.sevenstars.middleearth.datageneration;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.sevenstars.middleearth.enchantments.EnchantmentsME;
import net.sevenstars.middleearth.item.utils.SmithingTrimMaterialsME;
import net.sevenstars.middleearth.item.utils.SmithingTrimPatternsME;
import net.sevenstars.middleearth.registries.CharacterMaterialsRegistryME;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.world.biomes.caves.ModCaveBiomes;
import net.sevenstars.middleearth.world.biomes.surface.ModBiomes;
import net.sevenstars.middleearth.world.features.boulder.BoulderConfiguredFeatures;
import net.sevenstars.middleearth.world.features.boulder.BoulderPlacedFeatures;
import net.sevenstars.middleearth.world.features.chain.ChainConfiguredFeatures;
import net.sevenstars.middleearth.world.features.misc.ModMiscConfiguredFeatures;
import net.sevenstars.middleearth.world.features.misc.ModMiscPlacedFeatures;
import net.sevenstars.middleearth.world.features.ores.OreConfiguredFeatures;
import net.sevenstars.middleearth.world.features.ores.OrePlacedFeatures;
import net.sevenstars.middleearth.world.features.tree.ModTreeConfiguredFeatures;
import net.sevenstars.middleearth.world.features.tree.ModTreePlacedFeatures;
import net.sevenstars.middleearth.world.features.tree.MushroomTreeConfiguredFeatures;
import net.sevenstars.middleearth.world.features.underground.CavesConfiguredFeatures;
import net.sevenstars.middleearth.world.features.underground.CavesPlacedFeatures;
import net.sevenstars.middleearth.world.features.vegetation.ModVegetationConfiguredFeatures;
import net.sevenstars.middleearth.world.features.vegetation.ModVegetationPlacedFeatures;

public final class DataGeneration {
    public static boolean isDataGen = false;

    private DataGeneration() {
    }

    public static RegistrySetBuilder createRegistrySetBuilder() {
        return buildRegistryEntries(new RegistrySetBuilder());
    }

    public static RegistrySetBuilder createCompleteLookupRegistrySetBuilder() {
        RegistrySetBuilder registryBuilder = createRegistrySetBuilder();
        registryBuilder.add(Registries.ENCHANTMENT, EnchantmentsME::bootstrap);
        registryBuilder.add(Registries.TRIM_MATERIAL, SmithingTrimMaterialsME::bootstrap);
        registryBuilder.add(Registries.TRIM_PATTERN, SmithingTrimPatternsME::bootstrap);
        return registryBuilder;
    }

    public static RegistrySetBuilder buildRegistryEntries(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.BIOME, DataGeneration::bootstrapBiomes);
        registryBuilder.add(Registries.CONFIGURED_FEATURE, DataGeneration::bootstrapConfiguredFeatures);
        registryBuilder.add(Registries.PLACED_FEATURE, DataGeneration::bootstrapPlacedFeatures);

        registryBuilder.add(DynamicRegistriesME.SKIN_MATERIAL, CharacterMaterialsRegistryME::bootstrapSkins);
        registryBuilder.add(DynamicRegistriesME.SKIN_PATTERN, CharacterPatternsRegistryME::bootstrapSkins);

        registryBuilder.add(DynamicRegistriesME.HAIR_MATERIAL, CharacterMaterialsRegistryME::bootstrapHairs);
        registryBuilder.add(DynamicRegistriesME.HAIR_PATTERN, CharacterPatternsRegistryME::bootstrapHairs);

        registryBuilder.add(DynamicRegistriesME.EYE_MATERIAL, CharacterMaterialsRegistryME::bootstrapEyes);
        registryBuilder.add(DynamicRegistriesME.EYE_PATTERN, CharacterPatternsRegistryME::bootstrapEyes);

        // Mod Dynamic
        DynamicRegistriesME.prepareBoostrap(registryBuilder);

        return registryBuilder;
    }

    private static void bootstrapBiomes(BootstrapContext<Biome> context) {
        ModBiomes.bootstrap(context);
        ModCaveBiomes.bootstrap(context);
    }

    private static void bootstrapConfiguredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        ModTreeConfiguredFeatures.bootstrap(context);
        ModVegetationConfiguredFeatures.bootstrap(context);
        BoulderConfiguredFeatures.bootstrap(context);
        OreConfiguredFeatures.bootstrap(context);
        CavesConfiguredFeatures.bootstrap(context);
        ModMiscConfiguredFeatures.bootstrap(context);
        MushroomTreeConfiguredFeatures.bootstrap(context);
        ChainConfiguredFeatures.bootstrap(context);
    }

    private static void bootstrapPlacedFeatures(BootstrapContext<PlacedFeature> context) {
        ModTreePlacedFeatures.bootstrap(context);
        ModVegetationPlacedFeatures.bootstrap(context);
        BoulderPlacedFeatures.bootstrap(context);
        OrePlacedFeatures.bootstrap(context);
        CavesPlacedFeatures.bootstrap(context);
        ModMiscPlacedFeatures.bootstrap(context);
    }
}
