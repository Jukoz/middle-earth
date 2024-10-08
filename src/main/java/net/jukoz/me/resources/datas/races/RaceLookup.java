package net.jukoz.me.resources.datas.races;

import net.jukoz.me.resources.MiddleEarthNpcs;
import net.jukoz.me.resources.MiddleEarthRaces;
import net.jukoz.me.resources.datas.npcs.NpcData;
import net.jukoz.me.utils.IdentifierUtil;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RaceLookup {
    public static List<Race> getAllRaces(World world, List<Identifier> ids) {
        Registry<Race> registry = world.getRegistryManager().get(MiddleEarthRaces.RACE_KEY);
        List<Race> list = new ArrayList<>();
        for(Identifier id : ids){
            list.add(registry.get(id));
        }
        return list;
    }

    public static Race getRace(World world, Identifier identifier) {
        return world.getRegistryManager().get(MiddleEarthRaces.RACE_KEY).get(identifier);
    }
}
