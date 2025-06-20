package net.sevenstars.middleearth.datageneration.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;
import net.sevenstars.middleearth.resources.StructureDatasME;

import java.util.concurrent.CompletableFuture;

public class StructureDataProvider extends FabricDynamicRegistryProvider {
    public StructureDataProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        entries.addAll(registries.getOrThrow(StructureDatasME.KEY));
    }

    @Override
    public String getName() {
        return "StructureDatas";
    }
}
