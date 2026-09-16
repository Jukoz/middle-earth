package net.sevenstars.ofhallsandheralds.content.factions;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.ofhallsandheralds.dtos.Banner;
import net.sevenstars.ofhallsandheralds.dtos.Faction;
import net.sevenstars.ofhallsandheralds.dtos.FactionPalette;
import net.sevenstars.ofhallsandheralds.dtos.Spawn;

import java.util.ArrayList;
import java.util.List;

public class AbstractFactionPool {
    public static Faction createNonJoinable(RegistryKey<FactionPalette> palette, RegistryKey<Banner> banner, List<RegistryKey<Spawn>> spawnData) {
        return create(palette, banner, spawnData, false);
    }
    public static Faction createJoinable(RegistryKey<FactionPalette>  palette, RegistryKey<Banner> banner, List<RegistryKey<Spawn>> spawnData) {
        return create(palette, banner, spawnData, true);
    }
    private static Faction create(RegistryKey<FactionPalette>  palette, RegistryKey<Banner> banner, List<RegistryKey<Spawn>> spawnData, boolean isJoinable) {
        return new Faction(isJoinable, palette, banner, spawnData, new ArrayList<>(), new ArrayList<>());
    }

}
