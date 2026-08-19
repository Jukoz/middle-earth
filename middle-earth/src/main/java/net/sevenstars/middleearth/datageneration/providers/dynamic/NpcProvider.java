package net.sevenstars.middleearth.datageneration.providers.dynamic;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class NpcProvider extends DatapackBuiltinEntriesProvider {
    public NpcProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture,
                new RegistrySetBuilder().add(DynamicRegistriesME.NPC_TYPE, NpcRegistry::bootstrap),
                Set.of(MiddleEarth.MOD_ID));
    }

    @Override
    public String getName() {
        return DynamicRegistriesME.NPC_TYPE.location().getPath();
    }
}
