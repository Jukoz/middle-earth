package net.sevenstars.middleearth.datageneration;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.sevenstars.middleearth.datageneration.providers.BlockLootTableProvider;
import net.sevenstars.middleearth.datageneration.providers.DataWorldGenerator;
import net.sevenstars.middleearth.datageneration.providers.EnchantmentProvider;
import net.sevenstars.middleearth.datageneration.providers.LanguageProvider;
import net.sevenstars.middleearth.datageneration.providers.models.BlockModelProvider;
import net.sevenstars.middleearth.datageneration.providers.models.ItemModelProvider;
import net.sevenstars.middleearth.datageneration.providers.recipes.*;
import net.sevenstars.middleearth.datageneration.providers.tags.BlockTagProvider;
import net.sevenstars.middleearth.datageneration.providers.tags.ItemTagProvider;
import net.sevenstars.middleearth.enchantments.EnchantmentsME;
import net.sevenstars.middleearth.item.utils.SmithingTrimMaterialsME;
import net.sevenstars.middleearth.item.utils.SmithingTrimPatternsME;
import net.sevenstars.middleearth.registries.AtlasesME;
import net.sevenstars.middleearth.registries.CharacterMaterialsRegistryME;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.world.biomes.caves.CaveBiomesME;
import net.sevenstars.middleearth.world.biomes.surface.BiomesME;
import net.sevenstars.middleearth.world.features.boulder.BoulderConfiguredFeatureRegistryME;
import net.sevenstars.middleearth.world.features.boulder.BoulderPlacedFeatureRegistryME;
import net.sevenstars.middleearth.world.features.chain.ChainConfiguredFeatureRegistryME;
import net.sevenstars.middleearth.world.features.misc.MiscConfiguredFeatureRegistryME;
import net.sevenstars.middleearth.world.features.misc.MiscPlacedFeatureRegistryME;
import net.sevenstars.middleearth.world.features.ores.OreConfiguredFeatureRegistryME;
import net.sevenstars.middleearth.world.features.ores.OrePlacedFeatureRegistryME;
import net.sevenstars.middleearth.world.features.tree.TreeConfiguredFeatureRegistryME;
import net.sevenstars.middleearth.world.features.tree.TreePlacedFeatureRegistryME;
import net.sevenstars.middleearth.world.features.tree.MushroomTreeConfiguredFeatureRegistryME;
import net.sevenstars.middleearth.world.features.underground.CavesConfiguredFeatureRegistryME;
import net.sevenstars.middleearth.world.features.underground.CavesPlacedFeatureRegistryME;
import net.sevenstars.middleearth.world.features.vegetation.VegetationConfiguredFeatureRegistryME;
import net.sevenstars.middleearth.world.features.vegetation.VegetationPlacedFeatureRegistryME;

public class DataGeneration implements DataGeneratorEntrypoint {
    public static boolean isDataGen = false;

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        isDataGen = true;

        HelpingGenerator.generateFiles();

        var pack = fabricDataGenerator.createPack();
        // Atlases
        AtlasesME.addProviders(pack);
        // Custom Dynamic Registries
        DynamicRegistriesME.addProviders(pack);
        // Others
        pack.addProvider(InscriptionRecipeProvider::new);
        pack.addProvider(BlockTagProvider::new);
        pack.addProvider(BlockLootTableProvider::new);
        pack.addProvider(ItemTagProvider::new);
        pack.addProvider(BlockModelProvider::new);
        pack.addProvider(ItemModelProvider::new);
        pack.addProvider(RecipeProvider::new);
        pack.addProvider(ArtisanTableHandheldRecipeProvider::new);
        pack.addProvider(ArtisanTableArmorRecipeProvider::new);
        pack.addProvider(ArtisanTableGenericArmorRecipeProvider::new);
        pack.addProvider(DataWorldGenerator::new);
        pack.addProvider(LanguageProvider::new);
        pack.addProvider(EnchantmentProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        DataGeneratorEntrypoint.super.buildRegistry(registryBuilder);
        registryBuilder.addRegistry(RegistryKeys.BIOME, BiomesME::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.BIOME, CaveBiomesME::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, TreeConfiguredFeatureRegistryME::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, VegetationConfiguredFeatureRegistryME::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, BoulderConfiguredFeatureRegistryME::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, OreConfiguredFeatureRegistryME::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, CavesConfiguredFeatureRegistryME::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, MiscConfiguredFeatureRegistryME::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, MushroomTreeConfiguredFeatureRegistryME::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ChainConfiguredFeatureRegistryME::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, TreePlacedFeatureRegistryME::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, VegetationPlacedFeatureRegistryME::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, BoulderPlacedFeatureRegistryME::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, OrePlacedFeatureRegistryME::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, CavesPlacedFeatureRegistryME::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, MiscPlacedFeatureRegistryME::bootstrap);

        registryBuilder.addRegistry(DynamicRegistriesME.SKIN_MATERIAL, CharacterMaterialsRegistryME::bootstrapSkins);
        registryBuilder.addRegistry(DynamicRegistriesME.SKIN_PATTERN, CharacterPatternsRegistryME::bootstrapSkins);

        registryBuilder.addRegistry(DynamicRegistriesME.HAIR_MATERIAL, CharacterMaterialsRegistryME::bootstrapHairs);
        registryBuilder.addRegistry(DynamicRegistriesME.HAIR_PATTERN, CharacterPatternsRegistryME::bootstrapHairs);

        registryBuilder.addRegistry(DynamicRegistriesME.EYE_MATERIAL, CharacterMaterialsRegistryME::bootstrapEyes);
        registryBuilder.addRegistry(DynamicRegistriesME.EYE_PATTERN, CharacterPatternsRegistryME::bootstrapEyes);

        // Mod Dynamic
        DynamicRegistriesME.prepareBoostrap(registryBuilder);

        // Vanilla registries
        registryBuilder.addRegistry(RegistryKeys.TRIM_MATERIAL, SmithingTrimMaterialsME::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.TRIM_PATTERN, SmithingTrimPatternsME::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.ENCHANTMENT, EnchantmentsME::bootstrap);
    }
}
