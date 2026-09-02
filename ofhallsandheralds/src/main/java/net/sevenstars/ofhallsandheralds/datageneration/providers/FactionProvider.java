package net.sevenstars.ofhallsandheralds.datageneration.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;
import net.sevenstars.ofhallsandheralds.registries.DynamicRegistriesHH;

import java.util.concurrent.CompletableFuture;

public class FactionProvider extends FabricDynamicRegistryProvider {
    public FactionProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        entries.addAll(registries.getOrThrow(DynamicRegistriesHH.FACTION));
    }

    public String getName() {
        return DynamicRegistriesHH.FACTION.getValue().getPath();
    }
}
