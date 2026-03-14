package net.sevenstars.middleearth.datageneration.providers.dynamic;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.CharacterMaterialsME;
import net.sevenstars.middleearth.resources.CharacterPatternsME;

import java.util.concurrent.CompletableFuture;

public class CharacterAtlasTexturesProvider extends FabricDynamicRegistryProvider {
    public CharacterAtlasTexturesProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, FabricDynamicRegistryProvider.Entries entries) {
        entries.addAll(registries.getOrThrow(DynamicRegistriesME.SKIN_PATTERN));
        entries.addAll(registries.getOrThrow(DynamicRegistriesME.SKIN_MATERIAL));
        entries.addAll(registries.getOrThrow(DynamicRegistriesME.EYE_PATTERN));
        entries.addAll(registries.getOrThrow(DynamicRegistriesME.EYE_MATERIAL));
        entries.addAll(registries.getOrThrow(DynamicRegistriesME.HAIR_PATTERN));
        entries.addAll(registries.getOrThrow(DynamicRegistriesME.HAIR_MATERIAL));
    }

    @Override
    public String getName() {
        return "CharacterAtlasTextures";
    }
}
