package net.jukoz.me.resource.data.faction;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.resource.data.Alignment;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Faction {
    private final Identifier id;
    private final Alignment alignment;
    private HashMap<Identifier, Faction> subFactions = null;

    public Faction(Alignment alignment, JsonObject json, Identifier id, HashMap<Identifier, Faction> subFactions){
        this(alignment, json, id);
        this.subFactions = subFactions;
        LoggerUtil.logDebugMsg("Faction::" + id + " has " + subFactions.size() + " subfactions");
    }

    public Faction(Alignment alignment, JsonObject json, Identifier id) {
        this.alignment = alignment;
        this.id = id;

        LoggerUtil.logDebugMsg("Faction::Created a new faction -> " + id);
    }

    @Override
    public String toString() {
    return id.toString();
    }

    public HashMap<Identifier,Faction> getSubFactions(){
        return subFactions;
    }

    public Faction getSubfaction(int index){
        if(subFactions == null) return null;
        return subFactions.values().stream().toList().get(index);
    }

    public Alignment getAlignment(){
        return alignment;
    }

    public String getId() {
        return id.toString();
    }

    public String getLangKey() {
        return id.toTranslationKey();
    }
}
