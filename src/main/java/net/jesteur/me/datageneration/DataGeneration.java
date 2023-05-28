package net.jesteur.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DataGeneration implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();
        pack.addProvider(BlockTagProvider::new);
        pack.addProvider(BlockLootTableProvider::new);
        pack.addProvider(ModelProvider::new);
    }

}
