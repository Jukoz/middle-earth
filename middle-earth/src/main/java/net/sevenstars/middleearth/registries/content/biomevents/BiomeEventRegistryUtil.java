package net.sevenstars.middleearth.registries.content.biomevents;

import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.biomevents.pools.GenericHostilesBiomeEventPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;

import java.util.ArrayList;
import java.util.List;

public class BiomeEventRegistryUtil {
    private static final RegistryKey<Registry<BiomeEventData>> BIOME_EVENT_KEY = DynamicRegistriesME.BIOME_EVENT;

    public static List<RegistryKey<Biome>> biomeEntries = new ArrayList<>();


    public static void addBiomeEntry(RegistryKey<Biome> biome) {
        if(biomeEntries == null) {
            biomeEntries = new ArrayList<>();
        }
        if(biomeEntries.contains(biome)) {
            return;
        }
        biomeEntries.add(biome);
    }


    public static void removeBiomeEntry(Identifier biomeId) {
        if (biomeEntries != null) {
            biomeEntries.removeIf(entry -> entry.getValue().equals(biomeId));
        }
    }

    public static void registerDefaults(Registerable<BiomeEventData> context, RegistryEntryLookup<BiomeEventData> registryEntryLookup) {
        if(biomeEntries == null)
            return;

        for(RegistryKey<Biome> key : biomeEntries){
            DynamicRegistriesME.register(context, registryEntryLookup, of(key), GenericHostilesBiomeEventPool.EMPTY);
        }
    }

    public static RegistryKey<BiomeEventData> of(RegistryKey<Biome> key){
        return DynamicRegistriesME.of(BIOME_EVENT_KEY, key.getValue());
    }


    public static void register(Registerable<BiomeEventData> context, RegistryEntryLookup<BiomeEventData> registryEntryLookup, RegistryKey<BiomeEventData> registryKey, BiomeEventData element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        BiomeEventRegistryUtil.removeBiomeEntry(registryKey.getValue());
        // [LANG datagen]
        // None
    }

    public static RegistryKey<Structure> register(String name) {
        return RegistryKey.of(RegistryKeys.STRUCTURE, Identifier.of(MiddleEarth.MOD_ID, name));
    }
}
