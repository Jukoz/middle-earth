package net.jukoz.me.resources.datas.races;

import net.jukoz.me.resources.MiddleEarthFactions;
import net.jukoz.me.resources.MiddleEarthRaces;
import net.jukoz.me.resources.datas.factions.Faction;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class RaceLookup {
    public static List<Race> getAllRaces(World world) {
        return world.getRegistryManager().get(MiddleEarthRaces.RACE_KEY).stream().toList();
    }

    public static List<Race> getAllRaces(World world, List<Identifier> ids) {
        Registry<Race> registry = world.getRegistryManager().get(MiddleEarthRaces.RACE_KEY);
        List<Race> list = new ArrayList<>();
        for(Identifier id : ids){
            Race race = registry.get(id);
            if(!list.contains(race))
                list.add(race);
        }
        return list;
    }

    public static Race getRace(World world, Identifier identifier) {
        return world.getRegistryManager().get(MiddleEarthRaces.RACE_KEY).get(identifier);
    }
}
