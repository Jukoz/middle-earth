package net.sevenstars.middleearth.datageneration.providers.dynamic;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.CharacterMaterialsRegistryME;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class CharacterAtlasTexturesProvider extends DatapackBuiltinEntriesProvider {
    public CharacterAtlasTexturesProvider(PackOutput output,
                                           CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture, createRegistrySetBuilder(), Set.of(MiddleEarth.MOD_ID));
    }

    private static RegistrySetBuilder createRegistrySetBuilder() {
        return new RegistrySetBuilder()
                .add(DynamicRegistriesME.SKIN_PATTERN, CharacterPatternsRegistryME::bootstrapSkins)
                .add(DynamicRegistriesME.SKIN_MATERIAL, CharacterMaterialsRegistryME::bootstrapSkins)
                .add(DynamicRegistriesME.EYE_PATTERN, CharacterPatternsRegistryME::bootstrapEyes)
                .add(DynamicRegistriesME.EYE_MATERIAL, CharacterMaterialsRegistryME::bootstrapEyes)
                .add(DynamicRegistriesME.HAIR_PATTERN, CharacterPatternsRegistryME::bootstrapHairs)
                .add(DynamicRegistriesME.HAIR_MATERIAL, CharacterMaterialsRegistryME::bootstrapHairs);
    }

    @Override
    public String getName() {
        return "CharacterAtlasTextures";
    }
}
