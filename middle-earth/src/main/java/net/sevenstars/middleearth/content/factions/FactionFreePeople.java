package net.sevenstars.middleearth.content.factions;

import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.registries.custom.DispositionRegistryME;
import net.sevenstars.middleearth.registries.custom.FactionRegistryME;
import net.sevenstars.ofhallsandheralds.content.factions.AbstractFactionPool;
import net.sevenstars.ofhallsandheralds.dtos.faction.Faction;
import net.sevenstars.ofhallsandheralds.registries.custom.BannerRegistryHH;
import net.sevenstars.ofhallsandheralds.registries.custom.SpawnRegistryHH;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FactionFreePeople extends AbstractFactionPool {
    protected static Map<RegistryKey<Faction>, Faction> map = new HashMap<>();

    public static final Faction GONDOR = createJoinable(DispositionRegistryME.FREE_PEOPLES, BannerRegistryHH.NONE, List.of(SpawnRegistryHH.TEST));

    public static Map<RegistryKey<Faction>, Faction> fetch() {
        map.put(FactionRegistryME.GONDOR, GONDOR);
        return map;
    }
}
