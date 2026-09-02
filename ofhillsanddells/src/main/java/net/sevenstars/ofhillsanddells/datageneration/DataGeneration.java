package net.sevenstars.ofhillsanddells.datageneration;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;

public class DataGeneration implements DataGeneratorEntrypoint {
	public static boolean isDataGen = false;

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		isDataGen = true;

		var pack = fabricDataGenerator.createPack();
		//DynamicRegistriesHD.addProviders(pack);
	}


	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		DataGeneratorEntrypoint.super.buildRegistry(registryBuilder);
		// Mod Dynamic
		//DynamicRegistriesHD.prepareBoostrap(registryBuilder);
	}
}