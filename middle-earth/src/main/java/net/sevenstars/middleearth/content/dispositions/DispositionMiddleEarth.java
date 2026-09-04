package net.sevenstars.middleearth.content.dispositions;

import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.custom.DispositionRegistryME;
import net.sevenstars.ofhallsandheralds.content.dispositions.AbstractDispositionPool;
import net.sevenstars.ofhallsandheralds.dtos.disposition.Disposition;

import java.util.HashMap;
import java.util.Map;


public class DispositionMiddleEarth extends AbstractDispositionPool {
    protected static Map<RegistryKey<Disposition>, Disposition> map = new HashMap<>();

    public static final Disposition FREE_PEOPLES = create(MiddleEarth.id("free_peoples"));

    public static Map<RegistryKey<Disposition>, Disposition> fetch() {
        map.put(DispositionRegistryME.FREE_PEOPLES, FREE_PEOPLES);
        return map;
    }
}
