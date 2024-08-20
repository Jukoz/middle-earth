package net.jukoz.me.resources.datas.faction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.resources.ModFactionRegistry;
import net.jukoz.me.resources.datas.Alignment;
import net.jukoz.me.resources.datas.Race;
import net.jukoz.me.resources.datas.faction.utils.BannerData;
import net.jukoz.me.resources.datas.faction.utils.FactionNpcPreviewData;
import net.jukoz.me.resources.datas.faction.utils.SpawnsData;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Faction {

    public static final Codec<Faction> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(Faction::getIdValue),
            Codec.STRING.fieldOf("alignment").forGetter(Faction::getAlignmentString),
            Codec.list(Codec.STRING, 1, 5).fieldOf("races").forGetter(Faction::getRaceNames),
            NbtCompound.CODEC.fieldOf("preview_gears").forGetter(Faction::getPreviewGearNbt),
            NbtCompound.CODEC.fieldOf("banner").forGetter(Faction::getBannerNbt),
            NbtCompound.CODEC.fieldOf("spawns").forGetter(Faction::getSpawnDataNbt)
    ).apply(instance, Faction::new));

    private final Identifier id;
    private final Alignment alignment;
    private List<Race> official_races;
    private final FactionNpcPreviewData previewGears;
    private final BannerData bannerData;
    private final SpawnsData spawnsData;
    private HashMap<Identifier, Faction> subFactions = null;

    /**
     * Codec constructor
     * @param id Id of the faction, meant to be the path of the identifier
     * @param alignment Alignment name, based on the alignment enum
     * @param races all races, first value is used as preview race
     */
    public Faction(String id, String alignment, List<String> races, NbtCompound previewGearNbt, NbtCompound bannerDataNbt, NbtCompound spawnsNbt) {
        this.id = Identifier.of(MiddleEarth.MOD_ID, id);
        this.alignment = Alignment.valueOf(alignment.toUpperCase());

        this.official_races = new ArrayList<>();

        for(String raceName : races){
            official_races.add(Race.fromString(raceName.toUpperCase()));
        }
        this.previewGears = new FactionNpcPreviewData(previewGearNbt);
        this.bannerData = new BannerData(bannerDataNbt);
        this.spawnsData = new SpawnsData(spawnsNbt);
        LoggerUtil.logDebugMsg("Adding faction : " + id);
        ModFactionRegistry.addFaction(this, this.id);
    }

    private List<String> getRaceNames() {
        List<String> races = new ArrayList<>();
        for(Race race : official_races){
            races.add(race.name());
        }
        return races;
    }

    private String getIdValue() {
        return this.id.toString();
    }
    private NbtCompound getPreviewGearNbt() {
        return this.previewGears.getNbt();
    }

    private NbtCompound getBannerNbt() {
        return this.bannerData.getNbt();
    }
    private NbtCompound getSpawnDataNbt() {
        return this.spawnsData.getNbt();
    }

    public void debugPrint(String messsage) {
        String races = "";
        for(Race race : this.official_races){
            races += race.name() + ",";
        }

        String subfactionsText = "";
        if(this.subFactions != null){
            for(Faction faction : this.subFactions.values()){
                subfactionsText += faction.getId() + ",";
            }
        }

        String print = messsage + "\n" +
                "Id: " + id + "\n" +
                "Alignment: " + alignment + "\n" +
                "Races: " + races + "\n" +
                "PreviewGear: " + previewGears.getNbt().toString() + "\n" +
                "BannerData: " + bannerData.getNbt().toString() + "\n" +
                "SpawnData: " + spawnsData.getNbt().toString() + "\n" +
                "Subfactions: " + subfactionsText;

        LoggerUtil.logDebugMsg(print);
    }


    @Override
    public String toString() {
    return id.toString();
    }

    public FactionNpcPreviewData getPreviewGear(){
        return previewGears;
    }

    public DyeColor getBaseBannerColor(){
        if(bannerData == null) return BannerData.DEFAULT_DYE;
        return bannerData.getBaseDye();
    }

    public List<BannerData.BannerPatternWithColor> getBannerPatternsWithColors(ClientWorld world) {
        if(bannerData == null) return null;
        return bannerData.getBannerPatternsWithColors(world);

    }

    public Race getPreviewRace() {
        if(this.official_races == null || this.official_races.isEmpty()){
            LoggerUtil.logDebugMsg("Faction::"+id+":Couldn't find race -> returning "+ Race.Human.toString());
            return Race.Human;
        }

        return this.official_races.get(0);
    }
    public HashMap<Identifier,Faction> getSubFactions(){
        return subFactions;
    }

    public Faction getSubfaction(int index){
        if(subFactions == null || index >= subFactions.size())
            return null;
        return subFactions.values().stream().toList().get(index);
    }

    public Alignment getAlignment(){
        return alignment;
    }
    public String getAlignmentString(){
        return alignment.name();
    }

    public List<Vector3i> getSpawnCoordinates() { return spawnsData.getTemporaryCoordinate(); }

    public Identifier getId() {
        return id;
    }

    public String getName() {
        return id.getPath();
    }

    public MutableText tryGetShortName() {
        String target = id.toTranslationKey().concat(".fallback");
        String fallback = Text.translatable(id.toTranslationKey()).getString();
        return MutableText.of(new TranslatableTextContent(target, fallback, TranslatableTextContent.EMPTY_ARGUMENTS));
    }

    public void addSubfaction(Faction faction) {
        if(subFactions == null)
            subFactions = new HashMap<>();

        if(faction != null){
            LoggerUtil.logDebugMsg("[" + id + "] Adding subfaction : " + faction.id);
            subFactions.put(faction.id, faction);
        }
    }
}
