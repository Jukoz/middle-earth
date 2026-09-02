package net.sevenstars.ofhallsandheralds.datageneration.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;
import net.sevenstars.ofhallsandheralds.registries.DynamicRegistriesHH;

import java.util.concurrent.CompletableFuture;

public class DispositionProvider extends FabricDynamicRegistryProvider {
    public DispositionProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        entries.addAll(registries.getOrThrow(DynamicRegistriesHH.DISPOSITION));
    }

    public String getName() {
        return DynamicRegistriesHH.DISPOSITION.getValue().getPath();
    }
}
