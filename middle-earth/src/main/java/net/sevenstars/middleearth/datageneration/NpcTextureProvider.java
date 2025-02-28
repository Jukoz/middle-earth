package net.sevenstars.middleearth.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;
import net.sevenstars.middleearth.resources.MiddleEarthNpcTextureMaterials;
import net.sevenstars.middleearth.resources.MiddleEarthNpcTexturePatterns;

import java.util.concurrent.CompletableFuture;

public class NpcTextureProvider extends FabricDynamicRegistryProvider {
    public NpcTextureProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, FabricDynamicRegistryProvider.Entries entries) {
        entries.addAll(registries.getOrThrow(MiddleEarthNpcTextureMaterials.SKIN_KEY));
        entries.addAll(registries.getOrThrow(MiddleEarthNpcTexturePatterns.Keys.SKIN_KEY));
        entries.addAll(registries.getOrThrow(MiddleEarthNpcTextureMaterials.EYE_KEY));
        entries.addAll(registries.getOrThrow(MiddleEarthNpcTexturePatterns.Keys.EYE_KEY));
        entries.addAll(registries.getOrThrow(MiddleEarthNpcTextureMaterials.HAIR_KEY));
        entries.addAll(registries.getOrThrow(MiddleEarthNpcTexturePatterns.Keys.HAIR_KEY));
        entries.addAll(registries.getOrThrow(MiddleEarthNpcTextureMaterials.CLOTHING_KEY));
        entries.addAll(registries.getOrThrow(MiddleEarthNpcTexturePatterns.Keys.CLOTHING_KEY));
    }

    @Override
    public String getName() {
        return "NpcTextures";
    }
}
