package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.jukoz.me.resources.MiddleEarthNpcs;
import net.jukoz.me.resources.MiddleEarthRaces;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class NpcProvider extends FabricDynamicRegistryProvider {
    public NpcProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        entries.addAll(registries.getWrapperOrThrow(MiddleEarthNpcs.NPC_KEY));
    }

    @Override
    public String getName() {
        return "Npcs";
    }
}
