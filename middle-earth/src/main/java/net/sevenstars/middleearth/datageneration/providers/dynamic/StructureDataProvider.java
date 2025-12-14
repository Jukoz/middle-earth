package net.sevenstars.middleearth.datageneration.providers.dynamic;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.StructureManagerDatasME;

import java.util.concurrent.CompletableFuture;

public class StructureDataProvider extends FabricDynamicRegistryProvider {
    public StructureDataProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        entries.addAll(registries.getOrThrow(StructureManagerDatasME.KEY));
    }

    @Override
    public String getName() {
        return DynamicRegistriesME.STRUCTURE_MANAGER_DATA.getValue().getPath();
    }
}
