package net.sevenstars.middleearth.resources.datas.races;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.resources.RacesME;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RaceLookup {
    public static List<Race> getAllRaces(World world) {
        return world.getRegistryManager().getOrThrow(RacesME.KEY).stream().toList();
    }

    public static List<Race> getAllRaces(World world, List<Identifier> ids) {
        Registry<Race> registry = world.getRegistryManager().getOrThrow(RacesME.KEY);
        List<Race> list = new ArrayList<>();
        for(Identifier id : ids){
            Race race = registry.get(id);
            if(!list.contains(race))
                list.add(race);
        }
        return list;
    }

    public static Race getRace(World world, Identifier identifier) {
        Optional<Race> race = world.getRegistryManager().getOrThrow(RacesME.KEY).getOptionalValue(identifier);
        return race.orElse(null);
    }
}
