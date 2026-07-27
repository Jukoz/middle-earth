package net.sevenstars.middleearth.datageneration.providers.dynamic;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.greathornvariants.GreatHornVariantRegistry;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class GreatHornVariantsProvider extends DatapackBuiltinEntriesProvider {
    public GreatHornVariantsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture,
                new RegistrySetBuilder().add(
                        DynamicRegistriesME.GREAT_HORN_VARIANTS,
                        GreatHornVariantRegistry::bootstrap
                ),
                Set.of(MiddleEarth.MOD_ID));
    }

    @Override
    public String getName() {
        return "GreatHornVariants";
    }
}
