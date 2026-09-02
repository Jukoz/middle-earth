package net.sevenstars.ofhallsandheralds.content.factions;

import net.minecraft.registry.RegistryKey;
import net.sevenstars.ofhallsandheralds.dtos.banner.Banner;
import net.sevenstars.ofhallsandheralds.dtos.disposition.Disposition;
import net.sevenstars.ofhallsandheralds.dtos.faction.Faction;
import net.sevenstars.ofhallsandheralds.dtos.spawn.Spawn;

import java.util.ArrayList;
import java.util.List;

public class AbstractFactionPool {
    public static Faction createNonJoinable(RegistryKey<Disposition> disposition, RegistryKey<Banner> banner, List<RegistryKey<Spawn>> spawnData) {
        return new Faction(false, disposition, banner, spawnData, new ArrayList<>(), new ArrayList<>());
    }
    public static Faction create(RegistryKey<Disposition> disposition, RegistryKey<Banner> banner, List<RegistryKey<Spawn>> spawnData) {
        return new Faction(true, disposition, banner, spawnData, new ArrayList<>(), new ArrayList<>());
    }
}
