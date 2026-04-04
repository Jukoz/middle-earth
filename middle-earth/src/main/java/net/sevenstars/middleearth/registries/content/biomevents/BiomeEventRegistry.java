package net.sevenstars.middleearth.registries.content.biomevents;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.biomevents.pools.GenericHostilesBiomeEventPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.world.biomes.MEBiomeKeys;

public class BiomeEventRegistry {
    private static final RegistryKey<Registry<BiomeEventData>> BIOME_EVENT_KEY = DynamicRegistriesME.BIOME_EVENT;

    public final static RegistryKey<BiomeEventData> DEFAULT = DynamicRegistriesME.of(BIOME_EVENT_KEY, MiddleEarth.of("default"));

    public final static RegistryKey<BiomeEventData> CAVE = DynamicRegistriesME.of(BIOME_EVENT_KEY, MEBiomeKeys.BASIC_CAVE.getValue());


    public static void bootstrap(Registerable<BiomeEventData> context) {
        RegistryEntryLookup<BiomeEventData> registryEntryLookup = context.getRegistryLookup(BIOME_EVENT_KEY);

        register(context, registryEntryLookup, DEFAULT, GenericHostilesBiomeEventPool.DEFAULT);
        register(context, registryEntryLookup, CAVE, GenericHostilesBiomeEventPool.CAVE);
    }

    private static void register(Registerable<BiomeEventData> context, RegistryEntryLookup<BiomeEventData> registryEntryLookup, RegistryKey<BiomeEventData> registryKey, BiomeEventData element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        // [LANG datagen]
        // None
    }
}
