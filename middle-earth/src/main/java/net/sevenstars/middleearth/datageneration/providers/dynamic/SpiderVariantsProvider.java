package net.sevenstars.middleearth.datageneration.providers.dynamic;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.spidervariants.SpiderVariantRegistry;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SpiderVariantsProvider extends DatapackBuiltinEntriesProvider {
    public SpiderVariantsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture,
                new RegistrySetBuilder().add(
                        DynamicRegistriesME.SPIDER_VARIANTS,
                        SpiderVariantRegistry::bootstrap
                ),
                Set.of(MiddleEarth.MOD_ID));
    }

    @Override
    public String getName() {
        return DynamicRegistriesME.SPIDER_VARIANTS.location().getPath();
    }
}
