package net.sevenstars.middleearth.registries.custom;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.api.registries.DynamicRegistriesAPI;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.content.dispositions.DispositionMiddleEarth;
import net.sevenstars.ofhallsandheralds.dtos.disposition.Disposition;
import net.sevenstars.ofhallsandheralds.registries.DynamicRegistriesHH;

import java.util.HashMap;
import java.util.Map;

public class DispositionRegistryME extends DynamicRegistriesAPI<Disposition> {
    private static final RegistryKey<Registry<Disposition>> DISPOSITION_KEY = DynamicRegistriesHH.DISPOSITION;

    public final static RegistryKey<Disposition> FREE_PEOPLES  = of(DISPOSITION_KEY, MiddleEarth.id("free_peoples"));

    public static void bootstrap(Registerable<Disposition> context) {
        RegistryEntryLookup<Disposition> registryEntryLookup = context.getRegistryLookup(DISPOSITION_KEY);

        Map<RegistryKey<Disposition>, Disposition> registryMap = new HashMap<>();

        registryMap.putAll(DispositionMiddleEarth.fetch());

        for(Map.Entry<RegistryKey<Disposition>, Disposition> entry : registryMap.entrySet()) {
            register(context, registryEntryLookup, entry.getKey(), entry.getValue());
        }
    }
}
