package net.sevenstars.middleearth.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.sevenstars.middleearth.resources.MiddleEarthRaces;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class RaceProvider extends FabricDynamicRegistryProvider {
    public RaceProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        entries.addAll(registries.getWrapperOrThrow(MiddleEarthRaces.RACE_KEY));
    }

    @Override
    public String getName() {
        return "Races";
    }
}
