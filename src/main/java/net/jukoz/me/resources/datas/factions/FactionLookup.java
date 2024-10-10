package net.jukoz.me.resources.datas.factions;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.resources.MiddleEarthFactions;
import net.jukoz.me.resources.datas.Alignment;
import net.jukoz.me.resources.datas.FactionType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.*;
import java.util.stream.Stream;

public class FactionLookup {
    public static List<Faction> getAllFactions(World world) {
        return world.getRegistryManager().get(MiddleEarthFactions.FACTION_KEY).stream().toList();
    }
    public static Faction getFactionById(World world, Identifier id) throws FactionIdentifierException {
        Faction faction = world.getRegistryManager().get(MiddleEarthFactions.FACTION_KEY).get(id);
        if(faction == null)
            throw new FactionIdentifierException();
        return faction;
    }

    public static HashMap<Identifier, Faction> getFactionsByAlignment(World world, Alignment alignment){
        Stream<Faction> factions = getAllJoinableFaction(world).stream();
        HashMap<Identifier, Faction> foundFactions = new HashMap<>();

        for(Faction faction : factions.filter(x -> x.getAlignment() == alignment).toList()){
            if(faction.getFactionType() == FactionType.FACTION)
                foundFactions.put(faction.getId(), faction);
        }
        return foundFactions;
    }

    public static List<Faction> getAllJoinableFaction(World world) {
        List<Faction> factions = getAllFactions(world);
        List<Faction> factionList = new ArrayList<>();
        for(Faction faction : factions) {
            if(!faction.isJoinable())
                continue;
            factionList.add(faction);
        }
        return factionList;
    }


}
