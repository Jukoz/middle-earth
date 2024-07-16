package net.jukoz.me.resource.data.faction;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.resource.data.Alignment;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Faction {
    private final Identifier id;
    private final Alignment alignment;
    private HashMap<Identifier, Faction> subFactions = null;

    private HashMap<EquipmentSlot, Item> previewGear = null;

    public Faction(Alignment alignment, JsonObject json, Identifier id, HashMap<Identifier, Faction> subFactions){
        this(alignment, json, id);
        this.subFactions = subFactions;
        LoggerUtil.logDebugMsg("Faction::" + id + " has " + subFactions.size() + " subfactions");
    }

    public Faction(Alignment alignment, JsonObject json, Identifier id) {
        this.alignment = alignment;
        this.id = id;
        buildPreviewGear(json);
        LoggerUtil.logDebugMsg("Faction::Created a new faction -> " + id);
    }

    private void buildPreviewGear(JsonObject json) {
        JsonObject jsonObject = json.getAsJsonObject("preview_gear");
        if(jsonObject == null) return;

        previewGear = new HashMap<>();


        previewGear.put(EquipmentSlot.HEAD,  getItem(getValue(jsonObject, "head")));
        previewGear.put(EquipmentSlot.CHEST,  getItem(getValue(jsonObject, "chest")));
        previewGear.put(EquipmentSlot.LEGS,  getItem(getValue(jsonObject, "legs")));
        previewGear.put(EquipmentSlot.FEET,  getItem(getValue(jsonObject, "feet")));
        previewGear.put(EquipmentSlot.MAINHAND,  getItem(getValue(jsonObject, "main_hand")));
        previewGear.put(EquipmentSlot.OFFHAND,  getItem(getValue(jsonObject, "off_hand")));
    }

    private String getValue(JsonObject jsonObject, String key){
        return String.valueOf(jsonObject.get(key)).replace("\"", "");
    }

    private Item getItem(String itemId){
        return Registries.ITEM.get(Identifier.of(itemId));
    }

    @Override
    public String toString() {
    return id.toString();
    }

    public Item getPreviewGearAt(EquipmentSlot slot){
        if(previewGear == null) return null;
        return previewGear.get(slot);
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
