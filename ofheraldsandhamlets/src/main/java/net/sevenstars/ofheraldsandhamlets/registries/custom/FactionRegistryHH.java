package net.sevenstars.ofheraldsandhamlets.registries.custom;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.api.registries.DynamicRegistriesAPI;
import net.sevenstars.ofheraldsandhamlets.OfHeraldsAndHamlets;
import net.sevenstars.ofheraldsandhamlets.content.factions.FactionDefault;
import net.sevenstars.ofheraldsandhamlets.dtos.faction.Faction;
import net.sevenstars.ofheraldsandhamlets.registries.DynamicRegistriesHH;

import java.util.HashMap;
import java.util.Map;

public class FactionRegistryHH extends DynamicRegistriesAPI<Faction> {
    private static final RegistryKey<Registry<Faction>> FACTION_KEY = DynamicRegistriesHH.FACTION;

    public static final RegistryKey<Faction> TEST = of(FACTION_KEY, OfHeraldsAndHamlets.id("test"));

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
