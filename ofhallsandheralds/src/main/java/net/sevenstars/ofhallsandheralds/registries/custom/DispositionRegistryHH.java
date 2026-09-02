package net.sevenstars.ofhallsandheralds.registries.custom;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.api.registries.DynamicRegistriesAPI;
import net.sevenstars.ofhallsandheralds.OfHallsAndHeralds;
import net.sevenstars.ofhallsandheralds.content.dispositions.DispositionDefault;
import net.sevenstars.ofhallsandheralds.dtos.disposition.Disposition;
import net.sevenstars.ofhallsandheralds.registries.DynamicRegistriesHH;

import java.util.HashMap;
import java.util.Map;

public class DispositionRegistryHH extends DynamicRegistriesAPI<Disposition> {
    private static final RegistryKey<Registry<Disposition>> DISPOSITION_KEY = DynamicRegistriesHH.DISPOSITION;

    public static final RegistryKey<Disposition> NONE = of(DISPOSITION_KEY, OfHallsAndHeralds.id("none"));

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
