package net.sevenstars.middleearth.datageneration;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.sevenstars.middleearth.datageneration.providers.*;
import net.sevenstars.middleearth.datageneration.providers.models.BlockModelProvider;
import net.sevenstars.middleearth.datageneration.providers.models.ItemModelProvider;
import net.sevenstars.middleearth.datageneration.providers.recipes.ArtisanTableArmorRecipeProvider;
import net.sevenstars.middleearth.datageneration.providers.recipes.ArtisanTableHandheldRecipeProvider;
import net.sevenstars.middleearth.datageneration.providers.recipes.RecipeProvider;
import net.sevenstars.middleearth.datageneration.providers.tags.BlockTagProvider;
import net.sevenstars.middleearth.datageneration.providers.tags.ItemTagProvider;
import net.sevenstars.middleearth.entity.spider.SpiderVariants;
import net.sevenstars.middleearth.item.utils.SmithingTrimMaterialsME;
import net.sevenstars.middleearth.item.utils.SmithingTrimPatternsME;
import net.sevenstars.middleearth.resources.*;
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
        pack.addProvider(BlockTagProvider::new);
        pack.addProvider(BlockLootTableProvider::new);
        pack.addProvider(ItemTagProvider::new);
        pack.addProvider(NpcTextureProvider::new);
        pack.addProvider(BlockModelProvider::new);
        pack.addProvider(ItemModelProvider::new);
        pack.addProvider(RecipeProvider::new);
        pack.addProvider(ArtisanTableHandheldRecipeProvider::new);
        pack.addProvider(ArtisanTableArmorRecipeProvider::new);
        pack.addProvider(SpiderVariantsProvider::new);
        pack.addProvider(RaceProvider::new);
        pack.addProvider(NpcProvider::new);
        pack.addProvider(FactionProvider::new);
        pack.addProvider(StructureDataProvider::new);
        pack.addProvider(BiomeEventProvider::new);
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

        registryBuilder.addRegistry(NpcTextureMaterialsME.Keys.SKIN_KEY, NpcTextureMaterialsME::bootstrapSkins);
        registryBuilder.addRegistry(NpcTexturePatternsME.Keys.SKIN_KEY, NpcTexturePatternsME::bootstrapSkins);

        registryBuilder.addRegistry(NpcTextureMaterialsME.Keys.HAIR_KEY, NpcTextureMaterialsME::bootstrapHairs);
        registryBuilder.addRegistry(NpcTexturePatternsME.Keys.HAIR_KEY, NpcTexturePatternsME::bootstrapHairs);

        registryBuilder.addRegistry(NpcTextureMaterialsME.Keys.CLOTHING_KEY, NpcTextureMaterialsME::bootstrapClothings);
        registryBuilder.addRegistry(NpcTexturePatternsME.Keys.CLOTHING_KEY, NpcTexturePatternsME::bootstrapClothings);

        registryBuilder.addRegistry(NpcTextureMaterialsME.Keys.EYE_KEY, NpcTextureMaterialsME::bootstrapEyes);
        registryBuilder.addRegistry(NpcTexturePatternsME.Keys.EYE_KEY, NpcTexturePatternsME::bootstrapEyes);

        // Mod Dynamic
        registryBuilder.addRegistry(SpiderVariants.KEY, SpiderVariants::bootstrap);

        registryBuilder.addRegistry(RacesME.KEY, RacesME::bootstrap);
        registryBuilder.addRegistry(NpcME.KEY, NpcME::bootstrap);
        registryBuilder.addRegistry(FactionsME.KEY, FactionsME::bootstrap);
        registryBuilder.addRegistry(StructureManagerDatasME.KEY, StructureManagerDatasME::bootstrap);
        registryBuilder.addRegistry(BiomeEventsME.KEY, BiomeEventsME::bootstrap);

        // Vanilla registries
        registryBuilder.addRegistry(RegistryKeys.TRIM_MATERIAL, SmithingTrimMaterialsME::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.TRIM_PATTERN, SmithingTrimPatternsME::bootstrap);
    }
}
