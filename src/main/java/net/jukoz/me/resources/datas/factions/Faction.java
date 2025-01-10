package net.jukoz.me.resources.datas.factions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.util.NbtType;
import net.jukoz.me.resources.MiddleEarthFactions;
import net.jukoz.me.resources.datas.Disposition;
import net.jukoz.me.resources.datas.FactionType;
import net.jukoz.me.resources.datas.factions.data.BannerData;
import net.jukoz.me.resources.datas.factions.data.SpawnDataHandler;
import net.jukoz.me.resources.datas.npcs.NpcData;
import net.jukoz.me.resources.datas.npcs.NpcDataLookup;
import net.jukoz.me.resources.datas.npcs.data.NpcGearData;
import net.jukoz.me.resources.datas.npcs.data.NpcRank;
import net.jukoz.me.resources.datas.races.Race;
import net.jukoz.me.resources.datas.races.RaceLookup;
import net.jukoz.me.utils.IdentifierUtil;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.*;


public class Faction {
    private static HashMap<Disposition, List<Integer>> factionSelectionOrderIndexPerDisposition;

    public static final Codec<Faction> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(Faction::getIdValue),
            Codec.INT.fieldOf("faction_selection_order_index").forGetter(Faction::getFactionSelectionOrderIndex),
            Codec.BOOL.fieldOf("joinable").forGetter(Faction::getJoinable),
            Codec.STRING.fieldOf("disposition").forGetter(Faction::getDispositionString),
            Codec.STRING.fieldOf("faction_type").forGetter(Faction::getFactionTypeString),
            Identifier.CODEC.optionalFieldOf("parent_faction").forGetter(Faction::getParentFactionIdentifier),
            Codec.list(Identifier.CODEC).optionalFieldOf("subfaction").forGetter(Faction::getSubfactionIds),
            NbtCompound.CODEC.optionalFieldOf("npcs").forGetter(Faction::getNpcValues),
            NbtCompound.CODEC.optionalFieldOf("banner").forGetter(Faction::getBannerNbt),
            NbtCompound.CODEC.optionalFieldOf("spawns").forGetter(Faction::getSpawnDataNbt),
            Codec.list(Codec.STRING, 0, 5).optionalFieldOf("command_join").forGetter(Faction::getJoinCommands),
            Codec.list(Codec.STRING, 0, 5).optionalFieldOf("command_leave").forGetter(Faction::getLeaveCommands)
        ).apply(instance, Faction::new));

    private final Identifier id;
    private final Integer factionSelectionOrderIndex;
    private final String translatableKey;
    private final boolean joinable;
    private final Disposition disposition;
    private final FactionType factionType;
    private final Identifier parentFactionId;
    private final HashMap<NpcRank, List<Identifier>> npcDatasByRank;
    private final BannerData bannerData;
    private final SpawnDataHandler spawnDataHandler;
    private List<Identifier> subFactions = null;
    private List<String> joinCommands;
    private List<String> leaveCommands;
    public List<Race> races = null;
    private List<Text> descriptions = null;
    private Text raceList = null;

    public Faction(String id, Integer factionSelectionOrderIndex, Boolean joinable, String disposition, String factionType, Optional<Identifier> parentFaction, Optional<List<Identifier>> newSubFactions, Optional<NbtCompound> npcs, Optional<NbtCompound> bannerDataNbt, Optional<NbtCompound> spawnsNbt, Optional<List<String>> joinCommands, Optional<List<String>> leaveCommands) {
        this.id = IdentifierUtil.getIdentifierFromString(id);

        this.factionSelectionOrderIndex = factionSelectionOrderIndex; // TODO : Validation, rework this part in the future
        this.translatableKey = "faction.".concat(this.id.toTranslationKey());
        this.joinable = joinable;
        this.disposition = Disposition.valueOf(disposition.toUpperCase());

        this.factionType = FactionType.valueOf(factionType.toUpperCase());
        this.parentFactionId = parentFaction.orElse(null);

        if(newSubFactions.isPresent()){
            this.subFactions = new ArrayList<>();
            this.subFactions.addAll(newSubFactions.get());
        }

        this.npcDatasByRank = new HashMap<>();
        npcs.ifPresent(nbtCompound -> {
            NbtList list = nbtCompound.getList("ranks", NbtType.COMPOUND);
            for(int i = 0; i < list.size(); i++){
                NbtCompound rankCompound = list.getCompound(i);
                NpcRank rank = NpcRank.valueOf(rankCompound.getString("rank").toUpperCase());
                NbtList npcDataList = rankCompound.getList("pool", NbtType.STRING);
                List<Identifier> dataList = new ArrayList<>();
                for(int j = 0; j < npcDataList.size(); j++){
                    dataList.add(IdentifierUtil.getIdentifierFromString(npcDataList.getString(j)));
                }
                this.npcDatasByRank.put(rank, dataList);
            }
        });

        this.bannerData = (bannerDataNbt.isEmpty()) ? null : new BannerData(bannerDataNbt);;
        this.spawnDataHandler = new SpawnDataHandler(spawnsNbt);

        this.joinCommands = new ArrayList<>();
        joinCommands.ifPresent(nbtCompound -> this.joinCommands.addAll(nbtCompound));
        this.leaveCommands = new ArrayList<>();
        leaveCommands.ifPresent(nbtCompound -> this.leaveCommands.addAll(nbtCompound));

        this.raceList = null;
        this.descriptions = null;

        verifyData();
    }

    public Faction(String name, Boolean joinable, Disposition disposition, FactionType factionType, Identifier parentFactionId, List<Identifier> subFactions, HashMap<NpcRank, List<NpcData>> npcDatas, BannerData bannerData, SpawnDataHandler spawnDataHandler, List<String> joinCommand, List<String> leaveCommand){
        this.id = IdentifierUtil.getIdentifierFromString(name);

        if(factionSelectionOrderIndexPerDisposition == null)
            factionSelectionOrderIndexPerDisposition = new HashMap<>();
        if(factionSelectionOrderIndexPerDisposition.containsKey(disposition)){
            this.factionSelectionOrderIndex = factionSelectionOrderIndexPerDisposition.get(disposition).size();
            List<Integer> orderList = new ArrayList<>(factionSelectionOrderIndexPerDisposition.get(disposition));
            orderList.add(this.factionSelectionOrderIndex);
            factionSelectionOrderIndexPerDisposition.put(disposition, orderList);
        }
        else {
            int initialIndex = 0;
            this.factionSelectionOrderIndex = initialIndex;
            factionSelectionOrderIndexPerDisposition.put(disposition, List.of(initialIndex));
        }

        this.translatableKey = "faction.".concat(this.id.toTranslationKey());
        this.joinable = joinable;
        this.disposition = disposition;
        this.factionType = factionType;
        this.parentFactionId = parentFactionId;
        this.subFactions = subFactions;
        if(npcDatas == null || npcDatas.isEmpty()){
            this.npcDatasByRank = null;
        } else{
            this.npcDatasByRank = new HashMap<>();
            for(NpcRank rank : npcDatas.keySet()){
                List<Identifier> listOfIdentifiers = new ArrayList<>();
                for(NpcData data : npcDatas.get(rank)){
                    listOfIdentifiers.add(data.getId());
                }
                this.npcDatasByRank.put(rank, listOfIdentifiers);
            }
        }
        this.bannerData = bannerData;;
        this.spawnDataHandler = spawnDataHandler;
        this.joinCommands = joinCommand;
        this.leaveCommands = leaveCommand;
        this.raceList = null;
        this.descriptions = null;

        verifyData();
    }

    private void verifyData(){
        if(this.id.toString().contains("dorwinion")){
            throw new RuntimeException("There is no dorwinion in Middle-earth");
        }
        if(this.id.toString().toLowerCase().contains("dorw")){
            throw new RuntimeException("Do not even try... We are watching you");
        }

        // Need these data for a functional faction
        if((this.factionType == FactionType.SUBFACTION) || (this.factionType == FactionType.FACTION) && (subFactions == null || subFactions.isEmpty())){
            if(this.npcDatasByRank == null || this.npcDatasByRank.isEmpty()){
                throw new RuntimeException("Faction [%s] is missing their npc data, make sure they have at least 1 available npc data per rank.".formatted(id));
            } else {
                if(!npcDatasByRank.containsKey(NpcRank.MILITIA)
                        || !npcDatasByRank.containsKey(NpcRank.SOLDIER)
                        || !npcDatasByRank.containsKey(NpcRank.KNIGHT)
                        || !npcDatasByRank.containsKey(NpcRank.VETERAN)
                        || !npcDatasByRank.containsKey(NpcRank.LEADER)) {
                    throw new RuntimeException("Faction [%s] is missing their npc data, make sure they have at least 1 npc data per rank.".formatted(id));
                }
            }
            if(this.bannerData == null){
                throw new RuntimeException("Faction [%s] is missing their banner data, make sure they have one.".formatted(id));
            }
        }
    }

    private String getIdValue() {
        return this.id.toString();
    }

    public Integer getFactionSelectionOrderIndex() {
        return this.factionSelectionOrderIndex;
    }

    private Boolean getJoinable() {
        return joinable;
    }

    private Optional<Identifier> getParentFactionIdentifier() {
        if(this.parentFactionId == null)
            return Optional.empty();
        return Optional.of(this.parentFactionId);
    }
    public Identifier getParentFactionId() {
        return parentFactionId;
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

    public Optional<NbtCompound> getNpcValues() {
        if(this.npcDatasByRank == null || this.npcDatasByRank.isEmpty())
            return Optional.empty();
        NbtCompound nbtCompound = new NbtCompound();
        NbtList ranks = new NbtList();
        for(NpcRank rank : this.npcDatasByRank.keySet()){
            NbtCompound rankNbt = new NbtCompound();
            rankNbt.putString("rank", rank.toString().toUpperCase());
            NbtList identifiers = new NbtList();
            for(Identifier npcDataIdentifier : this.npcDatasByRank.get(rank).stream().toList()) {
                identifiers.add(NbtString.of(npcDataIdentifier.toString()));
            }
            rankNbt.put("pool", identifiers);
            ranks.add(rankNbt);
        }
        nbtCompound.put("ranks", ranks);
        return Optional.of(nbtCompound);
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

    public NpcData getRandomGear(World world, NpcRank npcRank, Race race) {
        if(!this.npcDatasByRank.containsKey(npcRank))
            return null;
        List<NpcData> npcDataList = NpcDataLookup.getAllNpcDatasFromRace(world, getNpcPoolFromRank(npcRank), race.getId());
        if(npcDataList.isEmpty())
            return null;
        Random random = new Random();
        return npcDataList.get(random.nextInt(0, npcDataList.size()));
    }

    public NpcGearData getPreviewGear(World world, Race selectedRace){
        if(selectedRace == null)
            return NpcGearData.Create();

        List<Identifier> identifiersToUse = new ArrayList<>();
        identifiersToUse.addAll(getNpcPoolFromRank(NpcRank.MILITIA));
        identifiersToUse.addAll(getNpcPoolFromRank(NpcRank.SOLDIER));
        identifiersToUse.addAll(getNpcPoolFromRank(NpcRank.KNIGHT));
        identifiersToUse.addAll(getNpcPoolFromRank(NpcRank.VETERAN));
        identifiersToUse.addAll(getNpcPoolFromRank(NpcRank.LEADER));

        List<NpcData> npcDataList = NpcDataLookup.getAllNpcDatasFromRace(world, identifiersToUse, selectedRace.getId());
        if(npcDataList.isEmpty())
            return NpcGearData.Create();
        Random random = new Random();
        NpcData foundNpcData = npcDataList.get(random.nextInt(0, npcDataList.size()));
        return foundNpcData.getGear();
    }

    private List<Identifier> getNpcPoolFromRank(NpcRank npcRank) {
        return this.npcDatasByRank.get(npcRank);
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

    public Disposition getDisposition(){
        return disposition;
    }
    public String getDispositionString(){
        return disposition.name();
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

    public List<Race> getRaces(World world) {
        if(races != null) return races;

        List<Identifier> allRaceIds = new ArrayList<>();
        for(NpcRank rank : this.npcDatasByRank.keySet()){
            List<NpcData> datas = NpcDataLookup.getAllNpcDatas(world, this.npcDatasByRank.get(rank));
            for(NpcData data : datas){
                if(data != null)
                    allRaceIds.add(data.getRaceId());
            }
        }
        races = RaceLookup.getAllRaces(world, allRaceIds);
        return races;
    }

    public boolean isJoinable() {
        return joinable;
    }

    public List<Text> getDescription() {
        if(descriptions == null){
            descriptions = new ArrayList<>();
            boolean hasDescription = true;
            String base = "description.me.%s.description_%s".formatted(id.getPath(), "%s");
            while(hasDescription){
                String langPath = base.formatted(descriptions.size());
                Text text = Text.translatable(langPath);
                if(!Objects.equals(text.getString(), langPath)){
                    descriptions.add(text);
                } else {
                    hasDescription = false;
                }
            }
        }
        return descriptions;
    }

    public Text getRaceListText(World world) {
        if(raceList == null){
            StringBuilder raceListStringBuilder = new StringBuilder();
            if(races == null)
                races = getRaces(world);
            for(Race race : races){
                raceListStringBuilder.append(race.getFullName().getString());
                if(race != races.getLast())
                    raceListStringBuilder.append(", ");
            }
            raceList = Text.literal(raceListStringBuilder.toString());
        }
        return raceList;
    }

    public BannerPatternsComponent getBannerPatternComponents(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        if(bannerData == null)
            return null;
        return bannerData.getBannerPatternComponents(bannerPatternLookup);
    }
}
