package net.jukoz.me.resource;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.resource.data.Alignment;
import net.jukoz.me.resource.data.faction.ModFactions;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class CustomServerDataResourceReloadListener {

    public static final String PATH = "custom";

    public static void register(){
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(
            new SimpleSynchronousResourceReloadListener() {
                @Override
                public Identifier getFabricId() {
                    return Identifier.of(MiddleEarth.MOD_ID, "custom_resources");
                }

                @Override
                public void reload(ResourceManager manager) {
                    LoggerUtil.logDebugMsg("CustomServerDataResourceReloadListener:reload -> Begin");


                    LoggerUtil.logDebugMsg("CustomServerDataResourceReloadListener:reload -> Find resources");
                    Map<Identifier, Resource> resources = manager.findResources(PATH, path -> path.getPath().endsWith(".json"));
                    for(Identifier id: resources.keySet()){
                        LoggerUtil.logDebugMsg("CustomServerDataResourceReloadListener:reload -> " + id);
                    }

                    LoggerUtil.logDebugMsg("CustomServerDataResourceReloadListener:reload -> Register datas");
                    ModFactions.registerFactions(resources);

                    LoggerUtil.logDebugMsg("CustomServerDataResourceReloadListener:reload -> Completed");
                }
            }
        );
    }

    /*
    private static void readFile(InputStream stream, Identifier id) throws UnsupportedEncodingException {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject)jsonParser.parse(new InputStreamReader(stream, "UTF-8"));

        ModFactions.registerFactions(jsonObject);

        if(Objects.equals(id, Identifier.of(MiddleEarth.MOD_ID, "custom/factions.json"))){
            createFactionPool(jsonObject);
            return;
        }

        ModFactions.addFromJson(jsonObject, id);
        LoggerUtil.logDebugMsg("ResourceManager::it works! " + jsonObject.get("name") + " " + id);
    }

    private static void createFactionPool(JsonObject jsonObject) {
        LoggerUtil.logInfoMsg(jsonObject.get("good").toString());
        JsonArray array = jsonObject.getAsJsonArray("good");
        LoggerUtil.logInfoMsg(array.size() + "");
        List<String> goodAlignmentFactions = new ArrayList<>();

        ModFactions.registerFactions(array);

        for(int i = 0; i < array.size(); i++){
            goodAlignmentFactions.add(array.get(i).toString());
            LoggerUtil.logInfoMsg(array.get(i).toString());
        }
        factionsByAlignment.put(Alignment.Good, goodAlignmentFactions);
    }

    private void addAlignedFactions(){

    }
     */
}
