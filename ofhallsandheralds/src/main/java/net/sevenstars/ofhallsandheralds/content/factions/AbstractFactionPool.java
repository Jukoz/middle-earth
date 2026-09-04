package net.sevenstars.ofhallsandheralds.content.factions;

import net.minecraft.registry.RegistryKey;
import net.sevenstars.ofhallsandheralds.OfHallsAndHeralds;
import net.sevenstars.ofhallsandheralds.dtos.banner.Banner;
import net.sevenstars.ofhallsandheralds.dtos.disposition.Disposition;
import net.sevenstars.ofhallsandheralds.dtos.faction.Faction;
import net.sevenstars.ofhallsandheralds.dtos.spawn.Spawn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbstractFactionPool {
    public static Faction createNonJoinable(RegistryKey<Disposition> disposition, RegistryKey<Banner> banner, List<RegistryKey<Spawn>> spawnData) {
        return create(disposition, banner, spawnData, false);
    }
    public static Faction createJoinable(RegistryKey<Disposition> disposition, RegistryKey<Banner> banner, List<RegistryKey<Spawn>> spawnData) {
        return create(disposition, banner, spawnData, true);
    }
    private static Faction create(RegistryKey<Disposition> disposition, RegistryKey<Banner> banner, List<RegistryKey<Spawn>> spawnData, boolean isJoinable) {
        return new Faction(isJoinable, disposition, banner, spawnData, new ArrayList<>(), new ArrayList<>());
    }

}
