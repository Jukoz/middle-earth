package net.sevenstars.ofhallsandheralds.registries.custom;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.api.registries.DynamicRegistriesAPI;
import net.sevenstars.ofhallsandheralds.OfHallsAndHeralds;
import net.sevenstars.ofhallsandheralds.content.factions.FactionDefault;
import net.sevenstars.ofhallsandheralds.dtos.faction.Faction;
import net.sevenstars.ofhallsandheralds.registries.DynamicRegistriesHH;

import java.util.HashMap;
import java.util.Map;

public class FactionRegistryHH extends DynamicRegistriesAPI<Faction> {
    private static final RegistryKey<Registry<Faction>> FACTION_KEY = DynamicRegistriesHH.FACTION;

    public static final RegistryKey<Faction> TEST = of(FACTION_KEY, OfHallsAndHeralds.id("test"));

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
