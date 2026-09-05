package net.sevenstars.ofhallsandheralds.content.factions;

import net.minecraft.registry.RegistryKey;
import net.sevenstars.ofhallsandheralds.dtos.banner.Banner;
import net.sevenstars.ofhallsandheralds.dtos.faction.Faction;
import net.sevenstars.ofhallsandheralds.dtos.spawn.Spawn;

import java.util.ArrayList;
import java.util.List;

public class AbstractFactionPool {
    public static Faction createNonJoinable(RegistryKey<Banner> banner, List<RegistryKey<Spawn>> spawnData) {
        return create(banner, spawnData, false);
    }
    public static Faction createJoinable(RegistryKey<Banner> banner, List<RegistryKey<Spawn>> spawnData) {
        return create(banner, spawnData, true);
    }
    private static Faction create(RegistryKey<Banner> banner, List<RegistryKey<Spawn>> spawnData, boolean isJoinable) {
        return new Faction(isJoinable, banner, spawnData, new ArrayList<>(), new ArrayList<>());
    }

}
