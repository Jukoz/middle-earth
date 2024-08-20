package net.jukoz.me.resources.datas.faction;


import net.jukoz.me.resources.datas.Alignment;
import net.minecraft.util.Identifier;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ModFactions {
    public static HashMap<Identifier, Faction> factions;

    public static List<Faction> getFactions(Alignment alignment){
        return factions.values().stream().filter(x -> x.getAlignment() == alignment).collect(Collectors.toList());
    }

    /*
     public static void registerFactions(Map<Identifier, Resource> resources) {
        LoggerUtil.logDebugMsg("ModFactions:registerFactions -> begin");
        factions = new HashMap<>();

        JsonParser jsonParser = new JsonParser();

        try{
            Resource resource = resources.get(Identifier.of(MiddleEarth.MOD_ID, CustomServerDataResourceReloadListener.PATH + "/factions.json"));

            JsonObject jsonObject = (JsonObject) jsonParser.parse(
                    new InputStreamReader(resource.getInputStream(), "UTF-8"));

            registerFactionByAlignment(jsonParser, Alignment.GOOD, jsonObject, resources);
            registerFactionByAlignment(jsonParser,  Alignment.NEUTRAL, jsonObject, resources);
            registerFactionByAlignment(jsonParser,  Alignment.EVIL, jsonObject, resources);
        } catch (Exception e){
            LoggerUtil.logError("ModFactions:registerFactions -> " + e);
        }
    }

    private static void registerFactionByAlignment(JsonParser jsonParser, Alignment alignment, JsonObject jsonObject, Map<Identifier, Resource> resources) throws IOException {
        for(JsonElement element : jsonObject.getAsJsonArray(alignment.toString())){
            Identifier id = Identifier.of(MiddleEarth.MOD_ID, ID_PREFIX + element.getAsString());
            Resource factionResource = resources.get(Identifier.of(MiddleEarth.MOD_ID, CustomServerDataResourceReloadListener.PATH + "/factions/" + element.getAsString() + ".json"));
            JsonObject factionJsonObject = (JsonObject) jsonParser.parse(
                    new InputStreamReader(factionResource.getInputStream(), "UTF-8"));

            Faction faction = null;
            if(factionJsonObject.get("sub") != null && !factionJsonObject.getAsJsonArray("sub").isEmpty()){
                HashMap<Identifier, Faction> subFactions = new HashMap<>();
                for(JsonElement subFacElement : factionJsonObject.getAsJsonArray("sub")){
                    Resource subFactionResource = resources.get(Identifier.of(MiddleEarth.MOD_ID, CustomServerDataResourceReloadListener.PATH + "/factions/" + element.getAsString() + "/" + subFacElement.getAsString() +".json"));
                    JsonObject subFactionJsonObject = (JsonObject) jsonParser.parse(
                            new InputStreamReader(subFactionResource.getInputStream(), "UTF-8"));
                    Identifier subFacId = Identifier.of(MiddleEarth.MOD_ID, ID_PREFIX + element.getAsString() + "." + subFacElement.getAsString());
                    Faction subFaction = new Faction(alignment, subFactionJsonObject, subFacId);
                    subFactions.put(subFacId, subFaction);
                }
                faction = new Faction(alignment, factionJsonObject, id, subFactions);
            }
            else {
                faction = new Faction(alignment, factionJsonObject, id);
            }
            factions.put(id, faction);
            ModFactionRegistry.FACTIONS.add(RegistryKey.of(ModFactionRegistry.FACTION_KEY, faction.getId()), faction, new RegistryEntryInfo(Optional.of(new VersionedIdentifier(MiddleEarth.MOD_ID, faction.getId().toString(), MiddleEarth.MOD_VERSION)), Lifecycle.stable()));
        }
    }

     */
}
