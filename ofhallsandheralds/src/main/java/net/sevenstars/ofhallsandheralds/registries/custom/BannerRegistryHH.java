package net.sevenstars.ofhallsandheralds.registries.custom;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.api.registries.DynamicRegistriesAPI;
import net.sevenstars.ofhallsandheralds.OfHallsAndHeralds;
import net.sevenstars.ofhallsandheralds.content.banners.BannerDefault;
import net.sevenstars.ofhallsandheralds.dtos.Banner;
import net.sevenstars.ofhallsandheralds.registries.DynamicRegistriesHH;
import java.util.HashMap;
import java.util.Map;

public class BannerRegistryHH extends DynamicRegistriesAPI<Banner> {
    private static final RegistryKey<Registry<Banner>> BANNER_KEY = DynamicRegistriesHH.BANNER;

    public static final RegistryKey<Banner> NONE = of(BANNER_KEY, OfHallsAndHeralds.id("none"));

    public static void bootstrap(Registerable<Banner> context) {
        RegistryEntryLookup<Banner> registryEntryLookup = context.getRegistryLookup(BANNER_KEY);

        Map<RegistryKey<Banner>, Banner> registryMap = fetchAll();

        for(Map.Entry<RegistryKey<Banner>, Banner> entry : registryMap.entrySet()) {
            register(context, registryEntryLookup, entry.getKey(), entry.getValue());
        }
    }

    public static Map<RegistryKey<Banner>, Banner> fetchAll() {
        Map<RegistryKey<Banner>, Banner> registryMap = new HashMap<>();

        registryMap.putAll(BannerDefault.fetch());

        return registryMap;
    }
}