package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.jukoz.me.resources.Factions;
import net.jukoz.me.resources.Races;
import net.jukoz.me.world.biomes.surface.ModBiomes;
import net.jukoz.me.world.biomes.caves.ModCaveBiomes;
import net.jukoz.me.world.features.boulder.BoulderConfiguredFeatures;
import net.jukoz.me.world.features.boulder.BoulderPlacedFeatures;
import net.jukoz.me.world.features.misc.ModMiscConfiguredFeatures;
import net.jukoz.me.world.features.misc.ModMiscPlacedFeatures;
import net.jukoz.me.world.features.ores.OreConfiguredFeatures;
import net.jukoz.me.world.features.ores.OrePlacedFeatures;
import net.jukoz.me.world.features.tree.ModTreeConfiguredFeatures;
import net.jukoz.me.world.features.tree.ModTreePlacedFeatures;
import net.jukoz.me.world.features.tree.MushroomTreeConfiguredFeatures;
import net.jukoz.me.world.features.underground.CavesConfiguredFeatures;
import net.jukoz.me.world.features.underground.CavesPlacedFeatures;
import net.jukoz.me.world.features.vegetation.ModVegetationConfiguredFeatures;
import net.jukoz.me.world.features.vegetation.ModVegetationPlacedFeatures;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

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
        pack.addProvider(ModelProvider::new);
        pack.addProvider(RecipeProvider::new);
        pack.addProvider(RaceProvider::new);
        pack.addProvider(FactionProvider::new);
        pack.addProvider(DataWorldGenerator::new);
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
        // Dynamic
        registryBuilder.addRegistry(Races.RACE_KEY, Races::bootstrap);
        registryBuilder.addRegistry(Factions.FACTION_KEY, Factions::bootstrap);
    }
}
