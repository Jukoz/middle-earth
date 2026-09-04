package net.sevenstars.ofhallsandheralds.content.dispositions;

import net.minecraft.registry.RegistryKey;
import net.sevenstars.ofhallsandheralds.OfHallsAndHeralds;
import net.sevenstars.ofhallsandheralds.dtos.disposition.Disposition;
import net.sevenstars.ofhallsandheralds.registries.custom.DispositionRegistryHH;

import java.util.HashMap;
import java.util.Map;

public class DispositionDefault extends AbstractDispositionPool {
    protected static Map<RegistryKey<Disposition>, Disposition> map = new HashMap<>();

    public static final Disposition NONE = create(OfHallsAndHeralds.id("none"));

    public static Map<RegistryKey<Disposition>, Disposition> fetch() {
        map.put(DispositionRegistryHH.NONE, NONE);
        return map;
    }
}
