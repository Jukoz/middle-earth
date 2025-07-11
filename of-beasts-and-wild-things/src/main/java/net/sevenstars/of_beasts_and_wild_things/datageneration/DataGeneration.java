package net.sevenstars.of_beasts_and_wild_things.datageneration;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.sevenstars.of_beasts_and_wild_things.datageneration.providers.EntityTagProvider;
import net.sevenstars.of_beasts_and_wild_things.datageneration.providers.LanguageProvider;
import net.sevenstars.of_beasts_and_wild_things.datageneration.providers.ModelProvider;

public class DataGeneration implements DataGeneratorEntrypoint {
    public static boolean isDataGen = false;

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        isDataGen = true;

        var pack = fabricDataGenerator.createPack();

        pack.addProvider(ModelProvider::new);
        pack.addProvider(LanguageProvider::new);
        pack.addProvider(EntityTagProvider::new);
    }
}
