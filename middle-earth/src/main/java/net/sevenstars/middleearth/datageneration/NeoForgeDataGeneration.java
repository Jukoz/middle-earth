package net.sevenstars.middleearth.datageneration;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.providers.BlockLootTableProvider;
import net.sevenstars.middleearth.datageneration.providers.LanguageProvider;
import net.sevenstars.middleearth.datageneration.providers.NeoForgeDataMapProvider;
import net.sevenstars.middleearth.datageneration.providers.models.BlockModelProvider;
import net.sevenstars.middleearth.datageneration.providers.models.ItemModelProvider;
import net.sevenstars.middleearth.datageneration.providers.recipes.ArtisanTableArmorRecipeProvider;
import net.sevenstars.middleearth.datageneration.providers.recipes.ArtisanTableGenericArmorRecipeProvider;
import net.sevenstars.middleearth.datageneration.providers.recipes.ArtisanTableHandheldRecipeProvider;
import net.sevenstars.middleearth.datageneration.providers.recipes.InscriptionRecipeProvider;
import net.sevenstars.middleearth.datageneration.providers.recipes.RecipeProvider;
import net.sevenstars.middleearth.datageneration.providers.tags.BlockTagProvider;
import net.sevenstars.middleearth.datageneration.providers.tags.ItemTagProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = MiddleEarth.NEOFORGE_MOD_ID)
public final class NeoForgeDataGeneration {
    private NeoForgeDataGeneration() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGeneration.isDataGen = true;
        CompletableFuture<HolderLookup.Provider> baseLookup = event.getLookupProvider();
        event.createDatapackRegistryObjects(
                DataGeneration.createCompleteLookupRegistrySetBuilder(),
                Set.of("middle-earth-datagen-lookup-only")
        );

        PackOutput output = event.getGenerator().getPackOutput();
        if (event.includeServer()) {
            event.addProvider(new OutputRegistryProvider(
                    output,
                    baseLookup,
                    DataGeneration.createRegistrySetBuilder()
            ));
            event.createProvider(NeoForgeDataMapProvider::new);
            event.createBlockAndItemTags(
                    (packOutput, lookup) -> new BlockTagProvider(
                            packOutput, lookup, event.getExistingFileHelper()
                    ),
                    (packOutput, lookup, blockTags) -> new ItemTagProvider(
                            packOutput, lookup, blockTags, event.getExistingFileHelper()
                    )
            );
            event.addProvider(new LootTableProvider(
                    output,
                    Set.of(),
                    List.of(new LootTableProvider.SubProviderEntry(
                            BlockLootTableProvider::new,
                            LootContextParamSets.BLOCK
                    )),
                    event.getLookupProvider()
            ));
            event.addProvider(namedProvider(
                    "Middle-earth recipes",
                    new RecipeProvider(output, event.getLookupProvider())
            ));
            event.addProvider(namedProvider(
                    "Middle-earth inscription recipes",
                    new InscriptionRecipeProvider(output, event.getLookupProvider())
            ));
            event.addProvider(namedProvider(
                    "Middle-earth artisan handheld recipes",
                    new ArtisanTableHandheldRecipeProvider(output, event.getLookupProvider())
            ));
            event.addProvider(namedProvider(
                    "Middle-earth artisan armor recipes",
                    new ArtisanTableArmorRecipeProvider(output, event.getLookupProvider())
            ));
            event.addProvider(namedProvider(
                    "Middle-earth artisan generic armor recipes",
                    new ArtisanTableGenericArmorRecipeProvider(output, event.getLookupProvider())
            ));
        }

        if (event.includeClient()) {
            HelpingGenerator.generateFiles();
            event.createProvider(LanguageProvider::new);
            event.createProvider(BlockModelProvider::new);
            event.createProvider(ItemModelProvider::new);
        }
    }

    private static DataProvider namedProvider(String name, DataProvider delegate) {
        return new DataProvider() {
            @Override
            public CompletableFuture<?> run(CachedOutput output) {
                return delegate.run(output);
            }

            @Override
            public String getName() {
                return name;
            }
        };
    }

    private static final class OutputRegistryProvider extends DatapackBuiltinEntriesProvider {
        private OutputRegistryProvider(
                PackOutput output,
                CompletableFuture<HolderLookup.Provider> registries,
                RegistrySetBuilder registryBuilder
        ) {
            super(output, registries, registryBuilder, Set.of(MiddleEarth.MOD_ID));
        }

        @Override
        public String getName() {
            return "Middle-earth registry objects";
        }
    }
}
