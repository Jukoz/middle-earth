package net.sevenstars.ofheraldsandhamlets.content.factions;

import net.minecraft.registry.RegistryKey;
import net.sevenstars.ofheraldsandhamlets.dtos.faction.Faction;
import net.sevenstars.ofheraldsandhamlets.registries.custom.BannerRegistryHH;
import net.sevenstars.ofheraldsandhamlets.registries.custom.DispositionRegistryHH;
import net.sevenstars.ofheraldsandhamlets.registries.custom.FactionRegistryHH;
import net.sevenstars.ofheraldsandhamlets.registries.custom.SpawnRegistryHH;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FactionDefault extends AbstractFactionPool {
    public static final Faction TEST = create(DispositionRegistryHH.NONE, BannerRegistryHH.NONE, List.of(SpawnRegistryHH.TEST));

    public static Map<RegistryKey<Faction>, Faction> fetch() {
        Map<RegistryKey<Faction>, Faction> map = new HashMap<>();
        map.put(FactionRegistryHH.TEST, TEST);
        return map;
    }
}
