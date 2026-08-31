package net.sevenstars.ofhamletandheroes.content.factions;

import net.minecraft.registry.RegistryKey;
import net.sevenstars.ofhamletandheroes.dtos.disposition.Disposition;
import net.sevenstars.ofhamletandheroes.dtos.faction.Faction;
import net.sevenstars.ofhamletandheroes.dtos.banner.Banner;
import net.sevenstars.ofhamletandheroes.dtos.spawn.Spawn;

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
