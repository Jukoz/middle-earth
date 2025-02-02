package net.sevenstars.middleearth.resources.datas.factions;

import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.resources.MiddleEarthFactions;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.FactionType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class FactionLookup {
    public static List<Faction> getAllFactions(World world) {
        return world.getRegistryManager().getOrThrow(MiddleEarthFactions.FACTION_KEY).stream().toList();
    }
    public static Faction getFactionById(World world, Identifier id) throws FactionIdentifierException {
        Faction faction = world.getRegistryManager().getOrThrow(MiddleEarthFactions.FACTION_KEY).get(id);
        if(faction == null)
            throw new FactionIdentifierException();
        return faction;
    }

    public static HashMap<Identifier, Faction> getFactionsByDisposition(World world, Disposition disposition){
        Stream<Faction> factions = getAllJoinableFaction(world).stream();
        HashMap<Identifier, Faction> foundFactions = new HashMap<>();

        for(Faction faction : factions.filter(x -> x.getDisposition() == disposition).toList()){
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
