package net.sevenstars.middleearth.features.npcs.classes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.api.dtos.WeightedPool;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.features.npcs.classes.data.LootData;
import net.sevenstars.middleearth.features.npcs.classes.data.MountData;
import net.sevenstars.middleearth.features.npcs.classes.data.RoutineData;
import net.sevenstars.middleearth.features.npcs.classes.data.WeightedGearData;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDataPool;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.RaceLookup;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class NpcClass {
    private static final Codec<NbtCompound> GEAR_CODEC = NbtCompound.CODEC.flatXmap(NpcClass::checkGear, DataResult::success);
    private static final Codec<NbtCompound> ATTRIBUTES_CODEC = NbtCompound.CODEC.flatXmap(NpcClass::checkAttributes, DataResult::success);

    public static final Codec<NpcClass> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("id").forGetter(NpcClass::getId),
            Identifier.CODEC.fieldOf("race").forGetter(NpcClass::getRace),
            Identifier.CODEC.fieldOf("faction").forGetter(NpcClass::getFactionIdentifier),
            Identifier.CODEC.fieldOf("base_npc_texture").forGetter(NpcClass::getNpcTextureDataValue),
            GEAR_CODEC.fieldOf("gear").forGetter(NpcClass::getGearDataValues),
            ATTRIBUTES_CODEC.fieldOf("npc_attributes").forGetter(NpcClass::getNpcAttributePool),
            MountData.CODEC.optionalFieldOf("mount").forGetter(NpcClass::getOptionalMountData),
            LootData.CODEC.optionalFieldOf("loot").forGetter(NpcClass::getOptionalLootData),
            RoutineData.CODEC.optionalFieldOf("routine").forGetter(NpcClass::getOptionalRoutine),
            Codec.BOOL.optionalFieldOf("civilian", false).forGetter(NpcClass::isCivilian)
    ).apply(instance, NpcClass::new));

    private final Identifier id;
    private final Identifier raceId;
    private final Identifier factionId;
    private final Identifier npcTextureKey;
    private final WeightedPool<WeightedGearData> gearDatas;
    private final Map<EntityCategories, AttributePool> npcAttributePools;
    private final MountData mountData;
    private final LootData lootData;
    private final @Nullable RoutineData routine;
    private final boolean civilian;

    public NpcClass(Identifier id, Identifier raceId, Identifier factionId, Identifier npcTextureKey, NbtCompound gearDatas, NbtCompound npcAttributes, Optional<MountData> mount, Optional<LootData> lootData, Optional<RoutineData> routine, boolean civilian) {
        this.id = id;
        this.raceId = raceId;
        this.factionId = factionId;
        this.npcTextureKey = npcTextureKey;

        NbtList npcGears = gearDatas.getList("pool").get();
        List<WeightedGearData> weightedGearData = new ArrayList<>();
        for(int j = 0; j < npcGears.size(); j++) {
            NbtCompound compound = npcGears.getCompound(j).get();
            weightedGearData.add(WeightedGearData.readNbt(compound));
        }
        this.gearDatas = new WeightedPool<>(weightedGearData);

        this.npcAttributePools = new HashMap<>();
        for(var category : EntityCategories.values()){
            if(npcAttributes.contains(category.name())){
                this.npcAttributePools.put(category, new AttributePool(npcAttributes.getCompound(category.name()).get()));
            }
        }

        this.mountData = mount.orElse(null);
        this.lootData = lootData.orElse(null);
        this.routine = routine.orElse(null);
        this.civilian = civilian;
    }

    private NpcClass(NpcClass base, @Nullable RoutineData routine, boolean civilian) {
        this.id = base.id;
        this.raceId = base.raceId;
        this.factionId = base.factionId;
        this.npcTextureKey = base.npcTextureKey;
        this.gearDatas = base.gearDatas;
        this.npcAttributePools = base.npcAttributePools;
        this.mountData = base.mountData;
        this.lootData = base.lootData;
        this.routine = routine;
        this.civilian = civilian;
    }

    public NpcClass(Identifier id, RegistryKey<Race> race, RegistryKey<Faction> faction, RegistryKey<TexturePresetDataPool> npcTextureKey, List<WeightedGearData> weightedGearData, Map<EntityCategories, AttributePool> npcAttributePools, LootData lootData) {
        this(id, race, faction, npcTextureKey, weightedGearData, npcAttributePools, null, lootData);
    }

    public NpcClass(Identifier id, RegistryKey<Race> race, RegistryKey<Faction> faction, RegistryKey<TexturePresetDataPool> npcTextureKey, List<WeightedGearData> weightedGearData, Map<EntityCategories, AttributePool> npcAttributePools, MountData mount, LootData lootData) {
        this.id = id;
        this.raceId = race.getValue();
        this.factionId = faction.getValue();
        this.npcTextureKey = npcTextureKey.getValue();
        this.gearDatas = new WeightedPool<>(weightedGearData);
        this.npcAttributePools = new HashMap<>(npcAttributePools);
        this.mountData = mount;
        this.lootData = lootData;
        this.routine = null;
        this.civilian = false;
    }

    private static DataResult<NbtCompound> checkGear(NbtCompound gear) {
        if(gear.getList("pool").isEmpty())
            return DataResult.error(() -> "gear has no 'pool' list");
        NbtList pool = gear.getList("pool").get();
        for(int i = 0; i < pool.size(); i++) {
            if(pool.getCompound(i).isEmpty()) {
                int index = i;
                return DataResult.error(() -> "gear pool entry " + index + " is not a compound");
            }
        }
        return DataResult.success(gear);
    }

    private static DataResult<NbtCompound> checkAttributes(NbtCompound attributes) {
        for(var category : EntityCategories.values()) {
            if(attributes.contains(category.name()) && attributes.getCompound(category.name()).isEmpty())
                return DataResult.error(() -> "npc_attributes '" + category.name() + "' is not a compound");
        }
        return DataResult.success(attributes);
    }

    public Identifier getId() {
        return id;
    }

    public Identifier getRace() {
        return raceId;
    }
    public Identifier getFactionIdentifier() {
        return factionId;
    }

    private Optional<MountData> getOptionalMountData() {
        return Optional.ofNullable(mountData);
    }
    private Optional<LootData> getOptionalLootData() {
        return Optional.ofNullable(lootData);
    }
    public LootData getLootData() {
        return lootData;
    }

    private Optional<RoutineData> getOptionalRoutine() {
        return Optional.ofNullable(routine);
    }
    public @Nullable RoutineData getRoutine() {
        return routine;
    }
    public NpcClass withRoutine(RoutineData routine) {
        return new NpcClass(this, routine, civilian);
    }

    // No tool until its household assigns a trade
    public boolean isCivilian() {
        return civilian;
    }
    public NpcClass asCivilian() {
        return new NpcClass(this, routine, true);
    }

    private NbtCompound getGearDataValues() {
        NbtCompound nbt = new NbtCompound();
        NbtList gears = new NbtList();
        for(WeightedGearData weightedGearData : this.gearDatas.elements){
            gears.add(weightedGearData.getNbt());
        }
        nbt.put("pool", gears);
        return nbt;
    }

    public String getName(){
        return id.toTranslationKey("npc_data");
    }

    public WeightedGearData getGear() {
        if(gearDatas == null)
            return null;
        return gearDatas.getRandom();
    }

    private NbtCompound getNpcAttributePool() {
        if(npcAttributePools == null)
            return null;
        var nbt = new NbtCompound();
        for(var category : npcAttributePools.keySet()){
            nbt.put(category.name(), npcAttributePools.get(category).getNbt());
        }
        return nbt;
    }

    private Identifier getNpcTextureDataValue() {
        return npcTextureKey;
    }
    public TexturePresetDataPool getNpcTextureData(World world) {
        return world.getRegistryManager().getOrThrow(DynamicRegistriesME.TEXTURE_PRESETS).get(npcTextureKey);
    }


    public void applyAttributes(NpcEntity npcEntity) {
        AttributePool.reverse(npcEntity);
        Race race = RaceLookup.getRace(npcEntity.getEntityWorld(), raceId);
        if(race != null)
            race.applyNpcAttributes(npcEntity);
        EntityCategories category = npcEntity.getNpcCategory();
        if(npcAttributePools.containsKey(EntityCategories.SHARED))
            npcAttributePools.get(EntityCategories.SHARED).apply(npcEntity);
        if(npcAttributePools.containsKey(category))
            npcAttributePools.get(category).apply(npcEntity);

        npcEntity.heal(npcEntity.getMaxHealth() - npcEntity.getHealth());
    }

    public boolean hasCategory(World world, EntityCategories category) {
       TexturePresetDataPool pool =  getNpcTextureData(world);
       return pool.hasCategory(category);
    }

    public boolean hasMount() {
        return this.mountData != null;
    }

    public MountData getMountData() {
        return this.mountData;
    }
}
