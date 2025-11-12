package net.sevenstars.middleearth.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;
import net.sevenstars.middleearth.resources.CharacterMaterialsME;
import net.sevenstars.middleearth.resources.CharacterPatternsME;

import java.util.concurrent.CompletableFuture;

public class NpcTextureProvider extends FabricDynamicRegistryProvider {
    public NpcTextureProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, FabricDynamicRegistryProvider.Entries entries) {
        entries.addAll(registries.getOrThrow(CharacterMaterialsME.Keys.SKIN_KEY));
        entries.addAll(registries.getOrThrow(CharacterPatternsME.Keys.SKIN_KEY));
        entries.addAll(registries.getOrThrow(CharacterMaterialsME.Keys.EYE_KEY));
        entries.addAll(registries.getOrThrow(CharacterPatternsME.Keys.EYE_KEY));
        entries.addAll(registries.getOrThrow(CharacterMaterialsME.Keys.HAIR_KEY));
        entries.addAll(registries.getOrThrow(CharacterPatternsME.Keys.HAIR_KEY));
        entries.addAll(registries.getOrThrow(CharacterMaterialsME.Keys.CLOTHING_KEY));
        entries.addAll(registries.getOrThrow(CharacterPatternsME.Keys.CLOTHING_KEY));
    }

    @Override
    public String getName() {
        return "NpcTextures";
    }
}
