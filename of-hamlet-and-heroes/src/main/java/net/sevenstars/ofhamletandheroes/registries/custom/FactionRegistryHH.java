package net.sevenstars.ofhamletandheroes.registries.custom;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.api.registries.DynamicRegistriesAPI;
import net.sevenstars.ofhamletandheroes.OfHamletAndHeroes;
import net.sevenstars.ofhamletandheroes.content.factions.FactionDefault;
import net.sevenstars.ofhamletandheroes.dtos.faction.Faction;
import net.sevenstars.ofhamletandheroes.registries.DynamicRegistriesHH;

import java.util.HashMap;
import java.util.Map;

public class FactionRegistryHH extends DynamicRegistriesAPI<Faction> {
    private static final RegistryKey<Registry<Faction>> FACTION_KEY = DynamicRegistriesHH.FACTION;

    public static final RegistryKey<Faction> TEST = of(FACTION_KEY, OfHamletAndHeroes.id("test"));

    public static void bootstrap(Registerable<Faction> context) {
        RegistryEntryLookup<Faction> registryEntryLookup = context.getRegistryLookup(FACTION_KEY);

        Map<RegistryKey<Faction>, Faction> registryMap = fetchAll();

        for(Map.Entry<RegistryKey<Faction>, Faction> entry : registryMap.entrySet()) {
            register(context, registryEntryLookup, entry.getKey(), entry.getValue());
        }
    }

    public static Map<RegistryKey<Faction>, Faction> fetchAll() {
        Map<RegistryKey<Faction>, Faction> registryMap = new HashMap<>();

        registryMap.putAll(FactionDefault.fetch());

        return registryMap;
    }
}
