package net.jukoz.me.resource.data.faction;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.resource.CustomServerDataResourceReloadListener;
import net.jukoz.me.resource.data.Alignment;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModFactions {
    public static final String ID_PREFIX = "faction.";
    public static HashMap<Identifier, Faction> factions;
    public static void registerFactions(Map<Identifier, Resource> resources) {
        LoggerUtil.logDebugMsg("ModFactions:registerFactions -> begin");
        factions = new HashMap<>();

        JsonParser jsonParser = new JsonParser();

        try{
            Resource resource = resources.get(Identifier.of(MiddleEarth.MOD_ID, CustomServerDataResourceReloadListener.PATH + "/factions.json"));

            JsonObject jsonObject = (JsonObject) jsonParser.parse(
                    new InputStreamReader(resource.getInputStream(), "UTF-8"));

            registerFactionByAlignment(jsonParser, Alignment.Good, jsonObject, resources);
            registerFactionByAlignment(jsonParser,  Alignment.Neutral, jsonObject, resources);
            registerFactionByAlignment(jsonParser,  Alignment.Evil, jsonObject, resources);
        } catch (Exception e){
            LoggerUtil.logError("ModFactions:registerFactions -> " + e);
        }
    }

    private static void registerFactionByAlignment(JsonParser jsonParser, Alignment alignment, JsonObject jsonObject, Map<Identifier, Resource> resources) throws IOException {
        LoggerUtil.logDebugMsg("ModFactions:registerFactions -> "+ alignment.toString() +" faction amount = " + jsonObject.getAsJsonArray(alignment.toString()).size());
        for(JsonElement element : jsonObject.getAsJsonArray(alignment.toString())){
            Identifier id = Identifier.of(MiddleEarth.MOD_ID, ID_PREFIX + element.getAsString());
            Resource factionResource = resources.get(Identifier.of(MiddleEarth.MOD_ID, CustomServerDataResourceReloadListener.PATH + "/factions/" + element.getAsString() + ".json"));
            JsonObject factionJsonObject = (JsonObject) jsonParser.parse(
                    new InputStreamReader(factionResource.getInputStream(), "UTF-8"));

            if(factionJsonObject.get("sub") != null && !factionJsonObject.getAsJsonArray("sub").isEmpty()){
                HashMap<Identifier, Faction> subFactions = new HashMap<>();
                for(JsonElement subFacElement : factionJsonObject.getAsJsonArray("sub")){
                    Resource subFactionResource = resources.get(Identifier.of(MiddleEarth.MOD_ID, CustomServerDataResourceReloadListener.PATH + "/factions/" + element.getAsString() + "/" + subFacElement.getAsString() +".json"));
                    JsonObject subFactionJsonObject = (JsonObject) jsonParser.parse(
                            new InputStreamReader(subFactionResource.getInputStream(), "UTF-8"));
                    Identifier subFacId = Identifier.of(MiddleEarth.MOD_ID, ID_PREFIX + element.getAsString() + "." + subFacElement.getAsString());
                    subFactions.put(subFacId, new Faction(alignment, subFactionJsonObject, subFacId));
                    LoggerUtil.logDebugMsg("ModFactions:registerFactions -> Adding subfaction : " + subFacId);
                }
                factions.put(id, new Faction(alignment, factionJsonObject, id, subFactions));
            }
            else {
                LoggerUtil.logDebugMsg("ModFactions:New Faction -> No subfaction");
                factions.put(id, new Faction(alignment, factionJsonObject, id));
            }
            LoggerUtil.logDebugMsg("ModFactions:registerFactions -> Adding faction : " + id);
        }
    }

    public static List<Faction> getFactions(Alignment alignment){
        return factions.values().stream().filter(x -> x.getAlignment() == alignment).collect(Collectors.toList());
    }
}
