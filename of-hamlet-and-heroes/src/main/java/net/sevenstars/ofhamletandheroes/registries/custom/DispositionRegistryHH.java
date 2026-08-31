package net.sevenstars.ofhamletandheroes.registries.custom;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.api.registries.DynamicRegistriesAPI;
import net.sevenstars.ofhamletandheroes.OfHamletAndHeroes;
import net.sevenstars.ofhamletandheroes.content.dispositions.DispositionDefault;
import net.sevenstars.ofhamletandheroes.dtos.disposition.Disposition;
import net.sevenstars.ofhamletandheroes.registries.DynamicRegistriesHH;

import java.util.HashMap;
import java.util.Map;

public class DispositionRegistryHH extends DynamicRegistriesAPI<Disposition> {
    private static final RegistryKey<Registry<Disposition>> DISPOSITION_KEY = DynamicRegistriesHH.DISPOSITION;

    public static final RegistryKey<Disposition> NONE = DynamicRegistriesHH.of(DISPOSITION_KEY, OfHamletAndHeroes.id("none"));

    public static void bootstrap(Registerable<Disposition> context) {
        RegistryEntryLookup<Disposition> registryEntryLookup = context.getRegistryLookup(DISPOSITION_KEY);

        Map<RegistryKey<Disposition>, Disposition> registryMap = fetchAll();

        for(Map.Entry<RegistryKey<Disposition>, Disposition> entry : registryMap.entrySet()) {
            register(context, registryEntryLookup, entry.getKey(), entry.getValue());
        }
    }

    public static Map<RegistryKey<Disposition>, Disposition> fetchAll() {
        Map<RegistryKey<Disposition>, Disposition> registryMap = new HashMap<>();

        registryMap.putAll(DispositionDefault.fetch());

        return registryMap;
    }
}
