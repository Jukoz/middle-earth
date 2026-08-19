package net.sevenstars.of_beasts_and_wild_things.datageneration;

import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.sevenstars.of_beasts_and_wild_things.datageneration.providers.EntityTagProvider;
import net.sevenstars.of_beasts_and_wild_things.datageneration.providers.ItemTagProvider;
import net.sevenstars.of_beasts_and_wild_things.datageneration.providers.LanguageProvider;
import net.sevenstars.of_beasts_and_wild_things.datageneration.providers.ModelProvider;

import java.util.concurrent.CompletableFuture;

public final class DataGeneration {
    public static boolean isDataGen = false;

    private DataGeneration() {
    }

    public static void gatherData(GatherDataEvent event) {
        isDataGen = true;
        PackOutput output = event.getGenerator().getPackOutput();
        if (event.includeClient()) {
            event.addProvider(new ModelProvider(output, event.getExistingFileHelper()));
            event.addProvider(new LanguageProvider(output));
        }
        if (event.includeServer()) {
            event.addProvider(new EntityTagProvider(output, event.getLookupProvider(), event.getExistingFileHelper()));
            event.addProvider(new ItemTagProvider(output, event.getLookupProvider(),
                    CompletableFuture.completedFuture(TagsProvider.TagLookup.empty()),
                    event.getExistingFileHelper()));
        }
    }
}
