package net.jukoz.me.resources.datas.faction;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.jukoz.me.resources.datas.Alignment;
import net.jukoz.me.resources.datas.Race;
import net.jukoz.me.resources.datas.faction.utils.FactionNpcPreviewData;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.joml.Vector2i;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Faction {
    public class BannerPatternWithColor {
        public BannerPattern pattern;
        public DyeColor color;
        BannerPatternWithColor(BannerPattern pattern, DyeColor color){
            this.pattern = pattern;
            this.color = color;
        }
    }
    private final Identifier id;
    private final Alignment alignment;
    private Race preview_race;
    private HashMap<Identifier, Faction> subFactions = null;
    private FactionNpcPreviewData previewGear = null;

    private DyeColor baseBannerColor;
    private List<Identifier> bannerPatternIds;
    private List<DyeColor> bannerDyeColors;
    DyeColor defaultDye = DyeColor.PURPLE;
    private List<Vector3i> spawnCoordinates;
    private Vector2i mapPreviewCoordinate;


    public Faction(Alignment alignment, JsonObject json, Identifier id, HashMap<Identifier, Faction> subFactions){
        this(alignment, json, id);
        this.subFactions = subFactions;
        LoggerUtil.logDebugMsg("Faction::" + id + " has " + subFactions.size() + " subfactions");
    }

    public Faction(Alignment alignment, JsonObject json, Identifier id) {
        this.alignment = alignment;
        this.id = id;
        buildPreview(json);
        buildSpawnInformation(json);
        buildBanner(json);
        LoggerUtil.logDebugMsg("Faction::Created a new faction -> " + id);
    }

    private Race parseRace(JsonObject json) {
        String jsonValue = getValue(json, "race");
        LoggerUtil.logDebugMsg("Faction::"+id+":found race -> " + jsonValue);
        return Race.fromString(jsonValue);
    }

    private void buildPreview(JsonObject json) {
        JsonObject jsonObject = json.getAsJsonObject("preview");
        if(jsonObject == null) return;

        this.preview_race = parseRace(jsonObject);
        previewGear = new FactionNpcPreviewData(
                getItem(getValue(jsonObject, "head")),
                getItem(getValue(jsonObject, "chest")),
                getItem(getValue(jsonObject, "legs")),
                getItem(getValue(jsonObject, "boots")),
                getItem(getValue(jsonObject, "main_hand")),
                getItem(getValue(jsonObject, "off_hand"))
        );
    }

    private void buildBanner(JsonObject json){
        JsonObject jsonObject = json.getAsJsonObject("banner");
        if(jsonObject == null) return;
        baseBannerColor = DyeColor.byName(getValue(jsonObject, "base_color"), defaultDye);

        JsonArray patternArray = jsonObject.getAsJsonArray("patterns");
        if(patternArray == null) return;

        bannerPatternIds = new ArrayList<>();
        bannerDyeColors = new ArrayList<>();

        for (JsonElement element : patternArray){
            JsonObject elementObj = element.getAsJsonObject();

            bannerPatternIds.add(Identifier.tryParse(getValue(elementObj, "id")));
            bannerDyeColors.add(DyeColor.byName(getValue(elementObj, "dye_color"), defaultDye));
        }

        baseBannerColor = DyeColor.byName(getValue(jsonObject, "base_color"), DyeColor.PINK);
    }

    private void buildSpawnInformation(JsonObject json){
        JsonObject jsonObject = json.getAsJsonObject("spawn");
        if(jsonObject == null) return;

        JsonObject mapCoordinateJsonObject = jsonObject.getAsJsonObject("map_preview_center");
        if(mapCoordinateJsonObject == null) return;
        mapPreviewCoordinate = new Vector2i(
                mapCoordinateJsonObject.get("x").getAsInt(),
                mapCoordinateJsonObject.get("y").getAsInt()
        );

        JsonArray spawnSets = jsonObject.getAsJsonArray("sets");
        if(spawnSets == null) return;
        spawnCoordinates = new ArrayList<>();

        for (JsonElement element : spawnSets){
            JsonObject elementObj = element.getAsJsonObject();
            Vector3i newCoordinate = new Vector3i(
                    elementObj.get("x").getAsInt(),
                    elementObj.get("y").getAsInt(),
                    elementObj.get("z").getAsInt()
            );
            spawnCoordinates.add(newCoordinate);
        }
        LoggerUtil.logDebugMsg("Faction::"+id+":found spawn -> " + spawnCoordinates.size());
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

    public FactionNpcPreviewData getPreviewGear(){
        return previewGear;
    }

    public ItemStack getPreviewGearAt(EquipmentSlot slot){
        if(previewGear == null) return null;
        return previewGear.get(slot);
    }

    public DyeColor getBaseBannerColor(){
        if(baseBannerColor == null) return defaultDye;
        return baseBannerColor;
    }

    public List<BannerPatternWithColor> getBannerPatternsWithColors(ClientWorld world){
        if(bannerDyeColors == null || bannerPatternIds == null) return null;
        if(bannerDyeColors.isEmpty() || bannerPatternIds.isEmpty()) return new ArrayList<>();
        if(bannerDyeColors.size() != bannerPatternIds.size()) return new ArrayList<>();

        List<BannerPatternWithColor> patterns = new ArrayList<>();
        for(int i = 0; i < bannerDyeColors.size(); i++){
            BannerPattern pattern = world.getRegistryManager().getOptional(RegistryKeys.BANNER_PATTERN).get().get(bannerPatternIds.get(i));
            DyeColor color = bannerDyeColors.get(i);
            if(pattern == null){
                LoggerUtil.logError("Faction::"+id+":Couldn't find pattern for " + bannerPatternIds.get(i));
                continue;
            }
            patterns.add(new BannerPatternWithColor(pattern, color));
        }
        return patterns;
    }

    public Race getPreviewRace() {
        if(this.preview_race == null){
            LoggerUtil.logDebugMsg("Faction::"+id+":Couldn't find race -> returning "+ Race.Human.toString());
            return Race.Human;
        }

        return this.preview_race;
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

    public Vector2i getMapPreviewCoordinate() { return mapPreviewCoordinate; }
    public List<Vector3i> getSpawnCoordinates() { return spawnCoordinates; }

    public String getId() {
        return id.toString();
    }

    public MutableText getName() {
        return Text.translatable(id.toTranslationKey());
    }

    public MutableText tryGetShortName() {
        String target = id.toTranslationKey().concat(".fallback");
        String fallback = Text.translatable(id.toTranslationKey()).getString();
        return MutableText.of(new TranslatableTextContent(target, fallback, TranslatableTextContent.EMPTY_ARGUMENTS));
    }
}
