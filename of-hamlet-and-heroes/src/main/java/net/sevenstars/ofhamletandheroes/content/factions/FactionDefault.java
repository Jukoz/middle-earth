package net.sevenstars.ofhamletandheroes.content.factions;

import net.minecraft.registry.RegistryKey;
import net.sevenstars.ofhamletandheroes.dtos.faction.Faction;
import net.sevenstars.ofhamletandheroes.registries.custom.BannerRegistryHH;
import net.sevenstars.ofhamletandheroes.registries.custom.DispositionRegistryHH;
import net.sevenstars.ofhamletandheroes.registries.custom.FactionRegistryHH;
import net.sevenstars.ofhamletandheroes.registries.custom.SpawnRegistryHH;

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
