package net.sevenstars.middleearth.resources.datas.factions;

import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.common.FactionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class FactionLookup {
    public static List<Faction> getAllFactions(World world) {
        return world.getRegistryManager().getOrThrow(DynamicRegistriesME.FACTION).stream().toList();
    }
    public static Faction getFactionById(World world, Identifier id) throws FactionIdentifierException {
        Faction faction = world.getRegistryManager().getOrThrow(DynamicRegistriesME.FACTION).get(id);
        if(faction == null)
            throw new FactionIdentifierException();
        return faction;
    }

    public static HashMap<Identifier, Faction> getFactionsByDisposition(World world, DispositionType dispositionType){
        Stream<Faction> factions = getAllJoinableFaction(world).stream();
        HashMap<Identifier, Faction> foundFactions = new HashMap<>();

        for(Faction faction : factions.filter(x -> x.getDisposition() == dispositionType).toList()){
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
