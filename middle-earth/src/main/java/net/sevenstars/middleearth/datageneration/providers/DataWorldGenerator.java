package net.sevenstars.middleearth.datageneration.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.DataGeneration;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DataWorldGenerator extends DatapackBuiltinEntriesProvider {
    public DataWorldGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture, DataGeneration.createRegistrySetBuilder(), Set.of(MiddleEarth.MOD_ID));
    }

    @Override
    public String getName() {
        return MiddleEarth.MOD_ID;
    }
}
