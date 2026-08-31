package net.sevenstars.ofhamletandheroes.datageneration;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.sevenstars.ofhamletandheroes.registries.DynamicRegistriesHH;

public class DataGeneration implements DataGeneratorEntrypoint {
	public static boolean isDataGen = false;

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		isDataGen = true;

		var pack = fabricDataGenerator.createPack();
		DynamicRegistriesHH.addProviders(pack);
	}


	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		DataGeneratorEntrypoint.super.buildRegistry(registryBuilder);
		// Mod Dynamic
		DynamicRegistriesHH.prepareBoostrap(registryBuilder);
	}
}