package net.sevenstars.middleearth.resources.datas.factions;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.common.FactionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class FactionLookup {
    public static List<Faction> getAllFactions(Level world) {
        return world.registryAccess().registryOrThrow(DynamicRegistriesME.FACTION).stream().toList();
    }
    public static Faction getFactionById(Level world, ResourceLocation id) throws FactionIdentifierException {
        if(id == null)
            return null;
        Faction faction = world.registryAccess().registryOrThrow(DynamicRegistriesME.FACTION).get(id);
        if(faction == null)
            throw new FactionIdentifierException();
        return faction;
    }

    public static HashMap<ResourceLocation, Faction> getFactionsByDisposition(Level world, DispositionType dispositionType){
        Stream<Faction> factions = getAllJoinableFaction(world).stream();
        HashMap<ResourceLocation, Faction> foundFactions = new HashMap<>();

        for(Faction faction : factions.filter(x -> x.getDisposition() == dispositionType).toList()){
            if(faction.getFactionType() == FactionType.FACTION)
                foundFactions.put(faction.getId(), faction);
        }
        return foundFactions;
    }

    public static List<Faction> getAllJoinableFaction(Level world) {
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
