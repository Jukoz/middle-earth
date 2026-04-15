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
import net.sevenstars.middleearth.datageneration.providers.recipes.ArtisanTableArmorRecipeProvider;
import net.sevenstars.middleearth.datageneration.providers.recipes.ArtisanTableHandheldRecipeProvider;
import net.sevenstars.middleearth.datageneration.providers.recipes.InscriptionRecipeProvider;
import net.sevenstars.middleearth.datageneration.providers.recipes.RecipeProvider;
import net.sevenstars.middleearth.datageneration.providers.tags.BlockTagProvider;
import net.sevenstars.middleearth.datageneration.providers.tags.ItemTagProvider;
import net.sevenstars.middleearth.enchantments.EnchantmentsME;
import net.sevenstars.middleearth.item.utils.SmithingTrimMaterialsME;
import net.sevenstars.middleearth.item.utils.SmithingTrimPatternsME;
import net.sevenstars.middleearth.registries.AtlasesME;
import net.sevenstars.middleearth.registries.CharacterMaterialsRegistryME;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.world.biomes.caves.ModCaveBiomes;
import net.sevenstars.middleearth.world.biomes.surface.ModBiomes;
import net.sevenstars.middleearth.world.features.boulder.BoulderConfiguredFeatures;
import net.sevenstars.middleearth.world.features.boulder.BoulderPlacedFeatures;
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
        pack.addProvider(DataWorldGenerator::new);
        pack.addProvider(LanguageProvider::new);
        pack.addProvider(EnchantmentProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        DataGeneratorEntrypoint.super.buildRegistry(registryBuilder);
        registryBuilder.addRegistry(RegistryKeys.BIOME, ModBiomes::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.BIOME, ModCaveBiomes::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModTreeConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModVegetationConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, BoulderConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, OreConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, CavesConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModMiscConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, MushroomTreeConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModTreePlacedFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModVegetationPlacedFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, BoulderPlacedFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, OrePlacedFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, CavesPlacedFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModMiscPlacedFeatures::bootstrap);

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
