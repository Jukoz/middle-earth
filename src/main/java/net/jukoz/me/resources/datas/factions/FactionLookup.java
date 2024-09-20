package net.jukoz.me.resources.datas.factions;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.resources.datas.Alignment;
import net.minecraft.util.Identifier;

import java.util.*;

public class FactionLookup {
    private static HashMap<Identifier, Faction> factions;
    private static HashMap<Identifier, List<Faction>> temporaryFactions;

    public static Faction getFactionById(Identifier id) {
        try{
            return findFactionById(id);
        } catch (FactionIdentifierException e){
            return null;
        }
    }

    public static void addFaction(Faction faction){
        if(factions == null)
            factions = new HashMap<>();
        if(temporaryFactions == null)
            temporaryFactions = new HashMap<>();

        String parentName = null;
        List<String> splittedId = Arrays.stream(faction.getId().getPath().split("\\.")).toList();
        if(splittedId.size() == 2){
            parentName = splittedId.get(0);
        }
        if(parentName != null){
            Identifier parentFactionId = Identifier.of(MiddleEarth.MOD_ID, parentName);
            Faction parentFaction = factions.get(parentFactionId);
            if(parentFaction != null){
                parentFaction.addSubfaction(faction);
            } else {
                List<Faction> subFactions = temporaryFactions.get(parentFactionId);
                if(subFactions == null){
                    subFactions = new ArrayList<>();
                } else {
                    temporaryFactions.remove(parentFactionId);
                }
                subFactions.add(faction);
                temporaryFactions.put(parentFactionId, subFactions);
            }
        } else if(factions.get(faction.getId()) == null){ // Prevent overwrites

            List<Faction> subFactions = temporaryFactions.get(faction.getId());
            if(subFactions != null && !subFactions.isEmpty()){
                for(Faction subFaction : subFactions){
                    faction.addSubfaction(subFaction);
                }
                temporaryFactions.remove(faction.getId());
            }
            factions.put(faction.getId(), faction);
        }
    }

    public static HashMap<Identifier, Faction> getFactionsByAlignment(Alignment alignment){
        HashMap<Identifier, Faction> foundFactions = new HashMap<>();

        for(Faction faction : factions.values().stream().filter(x -> x.getAlignment() == alignment).toList()){
            foundFactions.put(faction.getId(), faction);
        }
        return foundFactions;
    }

    public static Faction findFactionById(Identifier factionId) throws FactionIdentifierException {
        List<String> splittedId = Arrays.stream(factionId.getPath().split("\\.")).toList();
        Identifier id = Identifier.of(MiddleEarth.MOD_ID, splittedId.get(0));
        Faction foundFaction = factions.get(id);

        if(foundFaction != null && splittedId.size() == 2){
            foundFaction = foundFaction.findSubfaction(factionId);
        }

        if(foundFaction == null)
            throw new FactionIdentifierException();
        return foundFaction;
    }

    public static List<Identifier> getAllJoinableFactionId() {
        List<Identifier> factionIds = new ArrayList<>();
        for(Faction faction : factions.values()) {
            HashMap<Identifier, Faction> subFactions = faction.getSubFactions();
            if(subFactions == null)
                factionIds.add(faction.getId());
            else{
                List<Faction> subFacs = subFactions.values().stream().toList();
                for(Faction subFac : subFacs) {
                    factionIds.add(subFac.getId());
                }
            }
        }
        return factionIds;
    }

    public static List<Faction> getAllFactions() {
        return factions.values().stream().toList();
    }
}
