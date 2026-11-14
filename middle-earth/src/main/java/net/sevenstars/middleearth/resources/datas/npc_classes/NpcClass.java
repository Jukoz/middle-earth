package net.sevenstars.middleearth.resources.datas.npc_classes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.api.dtos.WeightedPool;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.LootData;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.MountData;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.WeightedGearData;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDataPool;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.RaceLookup;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class NpcClass {
    public static final Codec<NpcClass> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("id").forGetter(NpcClass::getId),
            Identifier.CODEC.fieldOf("race").forGetter(NpcClass::getRace),
            Identifier.CODEC.fieldOf("faction").forGetter(NpcClass::getFactionIdentifier),
            Identifier.CODEC.fieldOf("base_npc_texture").forGetter(NpcClass::getNpcTextureDataValue),
            NbtCompound.CODEC.fieldOf("gear").forGetter(NpcClass::getGearDataValues),
            NbtCompound.CODEC.fieldOf("npc_attributes").forGetter(NpcClass::getNpcAttributePool),
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
    private final HashMap<EntityCategories, AttributePool> npcAttributePools;
    private final MountData mountData;
    private final LootData lootData;
    private @Nullable RoutineData routine = null;
    private boolean civilian = false;

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
    public NpcClass(Identifier id, RegistryKey<Race> race, RegistryKey<Faction> faction, RegistryKey<TexturePresetDataPool> npcTextureKey, List<WeightedGearData> weightedGearData, HashMap<EntityCategories, AttributePool> npcAttributePools, LootData lootData) {
        this(id, race, faction, npcTextureKey, weightedGearData, npcAttributePools, null, lootData);
    }

    public NpcClass(Identifier id, RegistryKey<Race> race, RegistryKey<Faction> faction, RegistryKey<TexturePresetDataPool> npcTextureKey, List<WeightedGearData> weightedGearData, HashMap<EntityCategories, AttributePool> npcAttributePools, MountData mount, LootData lootData) {
        this.id = id;
        this.raceId = race.getValue();
        this.factionId = faction.getValue();
        this.npcTextureKey = npcTextureKey.getValue();
        this.gearDatas = new WeightedPool<>(weightedGearData);
        this.npcAttributePools = npcAttributePools;
        this.mountData = mount;
        this.lootData = lootData;
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
        this.routine = routine;
        return this;
    }

    // Does a trade of its own once its household gives it one, and carries no tool until then
    public boolean isCivilian() {
        return civilian;
    }
    public NpcClass asCivilian() {
        this.civilian = true;
        return this;
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
