package net.sevenstars.ofhallsandheralds.content.factions;

import net.minecraft.registry.RegistryKey;
import net.sevenstars.ofhallsandheralds.dtos.Faction;
import net.sevenstars.ofhallsandheralds.registries.custom.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FactionDefault extends AbstractFactionPool {
    protected static Map<RegistryKey<Faction>, Faction> map = new HashMap<>();

    public static final Faction TEST = createNonJoinable(BannerRegistryHH.NONE, List.of(SpawnRegistryHH.TEST));

    public static Map<RegistryKey<Faction>, Faction> fetch() {
        map.put(FactionRegistryHH.TEST, TEST);
        return map;
    }
}
