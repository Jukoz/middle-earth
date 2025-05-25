package net.sevenstars.middleearth.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;
import net.sevenstars.middleearth.resources.NpcTextureMaterialsME;
import net.sevenstars.middleearth.resources.NpcTexturePatternsME;

import java.util.concurrent.CompletableFuture;

public class NpcTextureProvider extends FabricDynamicRegistryProvider {
    public NpcTextureProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, FabricDynamicRegistryProvider.Entries entries) {
        entries.addAll(registries.getOrThrow(NpcTextureMaterialsME.Keys.SKIN_KEY));
        entries.addAll(registries.getOrThrow(NpcTexturePatternsME.Keys.SKIN_KEY));
        entries.addAll(registries.getOrThrow(NpcTextureMaterialsME.Keys.EYE_KEY));
        entries.addAll(registries.getOrThrow(NpcTexturePatternsME.Keys.EYE_KEY));
        entries.addAll(registries.getOrThrow(NpcTextureMaterialsME.Keys.HAIR_KEY));
        entries.addAll(registries.getOrThrow(NpcTexturePatternsME.Keys.HAIR_KEY));
        entries.addAll(registries.getOrThrow(NpcTextureMaterialsME.Keys.CLOTHING_KEY));
        entries.addAll(registries.getOrThrow(NpcTexturePatternsME.Keys.CLOTHING_KEY));
    }

    @Override
    public String getName() {
        return "NpcTextures";
    }
}
