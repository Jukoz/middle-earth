package net.sevenstars.ofhamletandheroes.content.dispositions;

import net.minecraft.registry.RegistryKey;
import net.sevenstars.ofhamletandheroes.dtos.disposition.Disposition;
import net.sevenstars.ofhamletandheroes.registries.custom.DispositionRegistryHH;

import java.util.HashMap;
import java.util.Map;

public class DispositionDefault extends AbstractDispositionPool {
    public static final Disposition NONE = create("none");

    public static Map<RegistryKey<Disposition>, Disposition> fetch() {
        Map<RegistryKey<Disposition>, Disposition> map = new HashMap<>();
        map.put(DispositionRegistryHH.NONE, NONE);
        return map;
    }
}
