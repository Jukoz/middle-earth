package net.sevenstars.middleearth.datageneration.providers.dynamic;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

import java.util.concurrent.CompletableFuture;

public class RingMetalsProvider extends FabricDynamicRegistryProvider {
    public RingMetalsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        entries.addAll(registries.getOrThrow(DynamicRegistriesME.RING_METAL));
    }

    @Override
    public String getName() {
        return DynamicRegistriesME.RING_METAL.getValue().getPath();
    }
}
