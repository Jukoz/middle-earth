package net.jukoz.me.resources.datas.factions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jukoz.me.resources.datas.Alignment;
import net.jukoz.me.resources.datas.factions.data.BannerData;
import net.jukoz.me.resources.datas.factions.data.FactionNpcPreviewData;
import net.jukoz.me.resources.datas.factions.data.SpawnDataHandler;
import net.jukoz.me.resources.datas.races.Race;
import net.jukoz.me.resources.datas.races.RaceLookup;
import net.jukoz.me.utils.IdentifierUtil;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.*;


public class Faction {

    public static final Codec<Faction> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(Faction::getIdValue),
            Codec.STRING.fieldOf("alignment").forGetter(Faction::getAlignmentString),
            Codec.list(Codec.STRING, 1, 5).fieldOf("races").forGetter(Faction::getRaceNames),
            NbtCompound.CODEC.optionalFieldOf("preview_gears").forGetter(Faction::getPreviewGearNbt),
            NbtCompound.CODEC.optionalFieldOf("banner").forGetter(Faction::getBannerNbt),
            NbtCompound.CODEC.optionalFieldOf("spawns").forGetter(Faction::getSpawnDataNbt),
            Codec.list(Codec.STRING, 0, 5).optionalFieldOf("command_join").forGetter(Faction::getJoinCommands),
            Codec.list(Codec.STRING, 0, 5).optionalFieldOf("command_leave").forGetter(Faction::getLeaveCommands)
        ).apply(instance, Faction::new));

    private final Identifier id;
    private final String translatableKey;
    private final Alignment alignment;
    private List<Race> official_races;
    private final FactionNpcPreviewData previewGears;
    private final BannerData bannerData;
    private final SpawnDataHandler spawnDataHandler;
    private HashMap<Identifier, Faction> subFactions = null;
    private List<String> joinCommands;
    private List<String> leaveCommands;

    public Faction(String id, String alignment, List<String> races, Optional<NbtCompound> previewGearNbt, Optional<NbtCompound> bannerDataNbt, Optional<NbtCompound> spawnsNbt, Optional<List<String>> joinCommands, Optional<List<String>> leaveCommands) {
        this.id = IdentifierUtil.getIdentifierFromString(id);
        this.translatableKey = "faction.".concat(this.id.toTranslationKey());

        this.alignment = Alignment.valueOf(alignment.toUpperCase());

        this.official_races = RaceLookup.getRacesFromString(races);

        this.previewGears = new FactionNpcPreviewData(previewGearNbt);
        this.bannerData = new BannerData(bannerDataNbt);
        this.spawnDataHandler = new SpawnDataHandler(spawnsNbt);

        this.joinCommands = new ArrayList<>();
        joinCommands.ifPresent(nbtCompound -> this.joinCommands.addAll(nbtCompound));
        this.leaveCommands = new ArrayList<>();
        leaveCommands.ifPresent(nbtCompound -> this.leaveCommands.addAll(nbtCompound));
        FactionLookup.addFaction(this);

        LoggerUtil.logDebugMsg("Adding faction : \n[Id] : " + this.id + "\n" + "[TranslatableKey] : " + this.translatableKey);
    }

    public Faction(String name, Alignment alignment, FactionNpcPreviewData factionNpcPreviewData, BannerData bannerData, SpawnDataHandler spawnDataHandler, List<Race> races, List<String> joinCommand, List<String> leaveCommand){
        this.id = IdentifierUtil.getIdentifierFromString(name);
        this.translatableKey = "faction.".concat(this.id.toTranslationKey());
        this.alignment = alignment;
        this.previewGears = factionNpcPreviewData;
        this.bannerData = bannerData;;
        this.spawnDataHandler = spawnDataHandler;
        this.official_races = races;
        this.joinCommands = joinCommand;
        this.leaveCommands = leaveCommand;
    }

    private List<String> getRaceNames() {
        List<String> races = new ArrayList<>();
        for(Race race : this.official_races){
            races.add(race.getId().toString());
        }
        return races;
    }

    private String getIdValue() {
        return this.id.toString();
    }
    private Optional<NbtCompound> getPreviewGearNbt() {
        if(this.previewGears == null)
            return Optional.empty();
        return this.previewGears.getNbt();
    }

    private Optional<NbtCompound> getBannerNbt() {
        if(this.bannerData == null)
            return Optional.empty();
        return this.bannerData.getNbt();
    }
    private Optional<NbtCompound> getSpawnDataNbt() {
        if(this.spawnDataHandler == null)
            return Optional.empty();
        return this.spawnDataHandler.serializeNbt();
    }

    public Optional<List<String>> getJoinCommands() {
        if(this.joinCommands == null)
            return Optional.empty();
        return Optional.of(this.joinCommands);
    }

    public Optional<List<String>> getLeaveCommands() {
        if(this.leaveCommands == null)
            return Optional.empty();
        return Optional.of(this.leaveCommands);
    }

    public void debugPrint(String messsage) {
        String races = "";
        for(Race race : this.official_races){
            races += race.getId() + ",";
        }

        String subfactionsText = "";
        if(this.subFactions != null){
            for(Faction faction : this.subFactions.values()){
                subfactionsText += faction.getId() + ",";
            }
        }

        String previewGearString = "None";
        if(this.previewGears != null){
            previewGearString = previewGears.getNbt().toString();
        }

        String bannerDataString = "None";
        if(this.bannerData != null){
            bannerDataString = bannerData.getNbt().toString();
        }

        String spawnDataString = "None";
        if(this.spawnDataHandler != null){
            spawnDataString = spawnDataHandler.serializeNbt().toString();
        }

        String print = messsage + "\n" +
                "Id: " + id + "\n" +
                "Alignment: " + alignment + "\n" +
                "Races: " + races + "\n" +
                "PreviewGear: " + previewGearString + "\n" +
                "BannerData: " + bannerDataString + "\n" +
                "SpawnData: " + spawnDataString + "\n" +
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

    public SpawnDataHandler getSpawnData() { return spawnDataHandler; }

    public Identifier getId() {
        return id;
    }

    public String getName() {
        return id.getPath();
    }

    public MutableText getFullName() {
        return MutableText.of(new TranslatableTextContent(translatableKey, "", TranslatableTextContent.EMPTY_ARGUMENTS));
    }

    public MutableText tryGetShortName() {
        String target = translatableKey.concat(".fallback");
        String fallback = Text.translatable(translatableKey).getString();
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

    public Faction findSubfaction(Identifier id) {
        return subFactions.get(id);
    }

    public List<Race> getRaces() {
        return official_races;
    }
}
