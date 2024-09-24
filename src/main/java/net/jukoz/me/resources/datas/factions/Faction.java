package net.jukoz.me.resources.datas.factions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.util.NbtType;
import net.jukoz.me.resources.MiddleEarthFactions;
import net.jukoz.me.resources.datas.Alignment;
import net.jukoz.me.resources.datas.FactionType;
import net.jukoz.me.resources.datas.factions.data.BannerData;
import net.jukoz.me.resources.datas.factions.data.NpcPreview;
import net.jukoz.me.resources.datas.factions.data.SpawnDataHandler;
import net.jukoz.me.resources.datas.races.Race;
import net.jukoz.me.resources.datas.races.RaceLookup;
import net.jukoz.me.utils.IdentifierUtil;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


public class Faction {

    public static final Codec<Faction> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(Faction::getIdValue),
            Codec.STRING.fieldOf("alignment").forGetter(Faction::getAlignmentString),
            Codec.STRING.fieldOf("faction_type").forGetter(Faction::getFactionTypeString),
            Identifier.CODEC.optionalFieldOf("parent_faction").forGetter(Faction::getParentFactionIdentifier),
            Codec.list(Identifier.CODEC).optionalFieldOf("subfactions").forGetter(Faction::getSubfactionIds),
            NbtCompound.CODEC.fieldOf("races").forGetter(Faction::getPreviewGearNbt),
            NbtCompound.CODEC.optionalFieldOf("banner").forGetter(Faction::getBannerNbt),
            NbtCompound.CODEC.optionalFieldOf("spawns").forGetter(Faction::getSpawnDataNbt),
            Codec.list(Codec.STRING, 0, 5).optionalFieldOf("command_join").forGetter(Faction::getJoinCommands),
            Codec.list(Codec.STRING, 0, 5).optionalFieldOf("command_leave").forGetter(Faction::getLeaveCommands)
        ).apply(instance, Faction::new));


    private final Identifier id;
    private final String translatableKey;
    private final Alignment alignment;
    private final FactionType factionType;
    private final Identifier parentFactionId;
    private final HashMap<Race, NpcPreview> racePreviews;
    private final BannerData bannerData;
    private final SpawnDataHandler spawnDataHandler;
    private List<Identifier> subFactions = null;
    private List<String> joinCommands;
    private List<String> leaveCommands;

    public Faction(String id, String alignment, String factionType, Optional<Identifier> parentFaction, Optional<List<Identifier>> newSubFactions, NbtCompound races, Optional<NbtCompound> bannerDataNbt, Optional<NbtCompound> spawnsNbt, Optional<List<String>> joinCommands, Optional<List<String>> leaveCommands) {
        this.id = IdentifierUtil.getIdentifierFromString(id);
        this.translatableKey = "faction.".concat(this.id.toTranslationKey());

        this.alignment = Alignment.valueOf(alignment.toUpperCase());
        this.factionType = FactionType.valueOf(factionType.toUpperCase());
        this.parentFactionId = parentFaction.orElse(null);

        if(newSubFactions.isPresent()){
            this.subFactions = new ArrayList<>();
            this.subFactions.addAll(newSubFactions.get());
        }

        this.racePreviews = new HashMap<>();
        NbtList raceList = races.getList("races", NbtType.COMPOUND);
        for(int i = 0; i < raceList.size(); i++){
            NbtCompound nbt = raceList.getCompound(i);
            this.racePreviews.put(RaceLookup.getRaceFromString(nbt.getString("race")), new NpcPreview(Optional.ofNullable(nbt.getCompound("preview"))));
        }

        this.bannerData = new BannerData(bannerDataNbt);
        this.spawnDataHandler = new SpawnDataHandler(spawnsNbt);

        this.joinCommands = new ArrayList<>();
        joinCommands.ifPresent(nbtCompound -> this.joinCommands.addAll(nbtCompound));
        this.leaveCommands = new ArrayList<>();
        leaveCommands.ifPresent(nbtCompound -> this.leaveCommands.addAll(nbtCompound));
    }

    public Faction(String name, Alignment alignment, FactionType factionType, Identifier parentFactionId, List<Identifier> subFactions, HashMap<Race, NpcPreview> races, BannerData bannerData, SpawnDataHandler spawnDataHandler, List<String> joinCommand, List<String> leaveCommand){
        this.id = IdentifierUtil.getIdentifierFromString(name);
        this.translatableKey = "faction.".concat(this.id.toTranslationKey());
        this.alignment = alignment;
        this.factionType = factionType;
        this.parentFactionId = parentFactionId;
        this.subFactions = subFactions;
        this.racePreviews = new HashMap<>();
        if(races != null) {
            for (Race race : races.keySet()){
                NpcPreview previewData = races.get(race);
                this.racePreviews.put(race, previewData);
            }
        }
        this.bannerData = bannerData;;
        this.spawnDataHandler = spawnDataHandler;
        this.joinCommands = joinCommand;
        this.leaveCommands = leaveCommand;
    }

    private String getIdValue() {
        return this.id.toString();
    }

    private Optional<Identifier> getParentFactionIdentifier() {
        if(this.parentFactionId == null)
            return Optional.empty();
        return Optional.of(this.parentFactionId);
    }
    public Identifier getParentFactionId() {
        return parentFactionId;
    }
    public NbtCompound getPreviewGearNbt() {
        NbtList list = new NbtList();
        for(Race race : this.racePreviews.keySet()){
            NbtCompound nbt = new NbtCompound();
            NpcPreview npcPreviewData = this.racePreviews.get(race);
            nbt.putString("race", race.getId().toString());
            NbtCompound nbtPreview = new NbtCompound();
            for(EquipmentSlot slot : npcPreviewData.data.keySet()){
                nbtPreview.putString(slot.name().toLowerCase(), npcPreviewData.get(slot).getItem().toString());
            }
            nbt.put("preview", nbtPreview);
            list.add(nbt);
        }
        NbtCompound nbt = new NbtCompound();
        nbt.put("races", list);
        return nbt;
    }

    private Optional<List<Identifier>> getSubfactionIds() {
        if(this.subFactions == null)
            return Optional.empty();
        return Optional.of(subFactions);
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

    @Override
    public String toString() {
    return id.toString();
    }

    public NpcPreview getPreviewGear(Race race){
        return racePreviews.get(race);
    }

    public DyeColor getBaseBannerColor(){
        if(bannerData == null) return BannerData.DEFAULT_DYE;
        return bannerData.getBaseDye();
    }

    public List<BannerData.BannerPatternWithColor> getBannerPatternsWithColors(ClientWorld world) {
        if(bannerData == null) return null;
        return bannerData.getBannerPatternsWithColors(world);

    }

    public List<Identifier> getSubFactions(){
        return subFactions;
    }

    public Faction getSubfaction(World world, int index){
        if(world == null || this.subFactions == null || index >= this.subFactions.size())
            return null;
        return getSubfactionById(world, subFactions.get(index));
    }

    public Alignment getAlignment(){
        return alignment;
    }
    public String getAlignmentString(){
        return alignment.name();
    }
    public String getFactionTypeString(){
        return factionType.name();
    }

    public FactionType getFactionType(){
        return factionType;
    }

    public SpawnDataHandler getSpawnData() {
        return spawnDataHandler; }

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

    public Faction getSubfactionById(World world, Identifier id) {
        if(subFactions == null)
            return null;
        return world.getRegistryManager().get(MiddleEarthFactions.FACTION_KEY).get(id);
    }

    public List<Race> getRaces() {
        return racePreviews.keySet().stream().toList();
    }
}
