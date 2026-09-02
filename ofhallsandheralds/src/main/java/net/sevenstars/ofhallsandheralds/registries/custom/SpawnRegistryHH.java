package net.sevenstars.ofhallsandheralds.registries.custom;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.api.registries.DynamicRegistriesAPI;
import net.sevenstars.ofhallsandheralds.OfHallsAndHeralds;
import net.sevenstars.ofhallsandheralds.content.spawns.SpawnDefault;
import net.sevenstars.ofhallsandheralds.dtos.spawn.Spawn;
import net.sevenstars.ofhallsandheralds.registries.DynamicRegistriesHH;

import java.util.HashMap;
import java.util.Map;

public class SpawnRegistryHH extends DynamicRegistriesAPI<Spawn> {
    private static final RegistryKey<Registry<Spawn>> SPAWN_KEY = DynamicRegistriesHH.SPAWN;

    public static final RegistryKey<Spawn> TEST = of(SPAWN_KEY, OfHallsAndHeralds.id("test"));

    public static void bootstrap(Registerable<Spawn> context) {
        RegistryEntryLookup<Spawn> registryEntryLookup = context.getRegistryLookup(SPAWN_KEY);

        Map<RegistryKey<Spawn>, Spawn> registryMap = fetchAll();

        for(Map.Entry<RegistryKey<Spawn>, Spawn> entry : registryMap.entrySet()) {
            register(context, registryEntryLookup, entry.getKey(), entry.getValue());
        }
    }

    public static Map<RegistryKey<Spawn>, Spawn> fetchAll() {
        Map<RegistryKey<Spawn>, Spawn> registryMap = new HashMap<>();

        registryMap.putAll(SpawnDefault.fetch());

        return registryMap;
    }
}