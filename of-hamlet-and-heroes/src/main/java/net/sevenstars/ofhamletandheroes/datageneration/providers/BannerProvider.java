package net.sevenstars.ofhamletandheroes.datageneration.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;
import net.sevenstars.ofhamletandheroes.registries.DynamicRegistriesHH;

import java.util.concurrent.CompletableFuture;

public class BannerProvider extends FabricDynamicRegistryProvider {
    public BannerProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        entries.addAll(registries.getOrThrow(DynamicRegistriesHH.BANNER));
    }

    public String getName() {
        return DynamicRegistriesHH.BANNER.getValue().getPath();
    }
}
