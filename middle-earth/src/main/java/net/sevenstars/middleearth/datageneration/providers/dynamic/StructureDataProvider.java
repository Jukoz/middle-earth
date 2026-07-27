package net.sevenstars.middleearth.datageneration.providers.dynamic;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.structuremanagerdatas.StructureManagerDataRegistry;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class StructureDataProvider extends DatapackBuiltinEntriesProvider {
    public StructureDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture,
                new RegistrySetBuilder().add(
                        DynamicRegistriesME.STRUCTURE_MANAGER_DATA,
                        StructureManagerDataRegistry::bootstrap
                ),
                Set.of(MiddleEarth.MOD_ID));
    }

    @Override
    public String getName() {
        return DynamicRegistriesME.STRUCTURE_MANAGER_DATA.location().getPath();
    }
}
