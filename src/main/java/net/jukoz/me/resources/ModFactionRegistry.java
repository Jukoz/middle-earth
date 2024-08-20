package net.jukoz.me.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.resources.datas.Alignment;
import net.jukoz.me.resources.datas.faction.Faction;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ModFactionRegistry {
    public final static String PATH = "factions";
    public static final RegistryKey<Registry<Faction>> FACTION_KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, PATH));
    private static HashMap<Identifier, Faction> factions;
    private static HashMap<Identifier, List<Faction>> temporaryFactions;

    public static void register(){
        DynamicRegistries.registerSynced(FACTION_KEY, Faction.CODEC);
    }

    public static void addFaction(Faction faction, Identifier id){
        if(factions == null)
            factions = new HashMap<>();
        if(temporaryFactions == null)
            temporaryFactions = new HashMap<>();

        String parentName = null;
        List<String> splittedId = Arrays.stream(id.getPath().split("\\.")).toList();
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
        } else {
            List<Faction> subFactions = temporaryFactions.get(id);
            if(subFactions != null && !subFactions.isEmpty()){

                for(Faction subFaction : subFactions){
                    faction.addSubfaction(subFaction);
                }
                temporaryFactions.remove(id);
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
}

