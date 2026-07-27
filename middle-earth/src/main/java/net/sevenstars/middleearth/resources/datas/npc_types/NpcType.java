package net.sevenstars.middleearth.resources.datas.npc_types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.sevenstars.api.dtos.WeightedPool;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.CombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.MeleeCombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.RangedCombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.runtime.CombatArchetypeRuntimeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.runtime.MeleeCombatArchetypeRuntimeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.runtime.RangedCombatArchetypeRuntimeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.utils.CombatArchetypeDataUtil;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npc_types.data.LootData;
import net.sevenstars.middleearth.resources.datas.npc_types.data.MountData;
import net.sevenstars.middleearth.resources.datas.npc_types.data.WeightedGearData;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDataPool;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.RaceLookup;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class NpcType {
    public static final Codec<NpcType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(NpcType::getId),
            ResourceLocation.CODEC.fieldOf("race").forGetter(NpcType::getRace),
            ResourceLocation.CODEC.fieldOf("faction").forGetter(NpcType::getFactionIdentifier),
            ResourceLocation.CODEC.fieldOf("base_npc_texture").forGetter(NpcType::getNpcTextureDataValue),
            CompoundTag.CODEC.fieldOf("gear").forGetter(NpcType::getGearDataValues),
            CompoundTag.CODEC.fieldOf("npc_attributes").forGetter(NpcType::getNpcAttributePool),
            CompoundTag.CODEC.fieldOf("combat_archetype").forGetter(NpcType::getCombatArchetypeData),
            MountData.CODEC.optionalFieldOf("mount").forGetter(NpcType::getOptionalMountData),
            LootData.CODEC.optionalFieldOf("loot").forGetter(NpcType::getOptionalLootData)
    ).apply(instance, NpcType::new));

    private final ResourceLocation id;
    private final ResourceLocation raceId;
    private final ResourceLocation factionId;
    private final ResourceLocation npcTextureKey;
    private final CombatArchetypeData combatArchetypeData;
    private final WeightedPool<WeightedGearData> gearDatas;
    private final HashMap<EntityCategories, AttributePool> npcAttributePools;
    private final MountData mountData;
    private final LootData lootData;

    public NpcType(ResourceLocation id, ResourceLocation raceId, ResourceLocation factionId, ResourceLocation npcTextureKey, CompoundTag gearDatas, CompoundTag npcAttributes, CompoundTag combatArchetypeData, Optional<MountData> mount, Optional<LootData> lootData) {
        this.id = id;
        this.raceId = raceId;
        this.factionId = factionId;
        this.npcTextureKey = npcTextureKey;

        ListTag npcGears = gearDatas.getList("pool", Tag.TAG_COMPOUND);
        List<WeightedGearData> weightedGearData = new ArrayList<>();
        for(int j = 0; j < npcGears.size(); j++) {
            CompoundTag compound = npcGears.getCompound(j);
            weightedGearData.add(WeightedGearData.readNbt(compound));
        }
        this.gearDatas = new WeightedPool<>(weightedGearData);

        this.npcAttributePools = new HashMap<>();
        for(var category : EntityCategories.values()){
            if(npcAttributes.contains(category.name())){
                this.npcAttributePools.put(category, new AttributePool(npcAttributes.getCompound(category.name())));
            }
        }

        this.combatArchetypeData = CombatArchetypeDataUtil.create(combatArchetypeData);
        this.mountData = mount.orElse(null);
        this.lootData = lootData.orElse(null);
    }
    public NpcType(ResourceLocation id, ResourceKey<Race> race, ResourceKey<Faction> faction, ResourceKey<TexturePresetDataPool> npcTextureKey, List<WeightedGearData> weightedGearData, HashMap<EntityCategories, AttributePool> npcAttributePools, CombatArchetypeData combatArchetypeData, LootData lootData) {
        this(id, race, faction, npcTextureKey, weightedGearData, npcAttributePools, combatArchetypeData, null, lootData);
    }

    public NpcType(ResourceLocation id, ResourceKey<Race> race, ResourceKey<Faction> faction, ResourceKey<TexturePresetDataPool> npcTextureKey, List<WeightedGearData> weightedGearData, HashMap<EntityCategories, AttributePool> npcAttributePools, CombatArchetypeData combatArchetypeData, MountData mount, LootData lootData) {
        this.id = id;
        this.raceId = race.location();
        this.factionId = faction.location();
        this.npcTextureKey = npcTextureKey.location();
        this.gearDatas = new WeightedPool<>(weightedGearData);
        this.npcAttributePools = npcAttributePools;
        this.combatArchetypeData = combatArchetypeData;
        this.mountData = mount;
        this.lootData = lootData;
    }

    public ResourceLocation getId() {
        return id;
    }

    public ResourceLocation getRace() {
        return raceId;
    }
    public ResourceLocation getFactionIdentifier() {
        return factionId;
    }

    private CompoundTag getCombatArchetypeData() {
        return combatArchetypeData.getNbt();
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


    private CompoundTag getGearDataValues() {
        CompoundTag nbt = new CompoundTag();
        ListTag gears = new ListTag();
        for(WeightedGearData weightedGearData : this.gearDatas.elements){
            gears.add(weightedGearData.getNbt());
        }
        nbt.put("pool", gears);
        return nbt;
    }

    public String getName(){
        return id.toLanguageKey("npc_data");
    }

    public WeightedGearData getGear() {
        if(gearDatas == null)
            return null;
        return gearDatas.getRandom();
    }

    private CompoundTag getNpcAttributePool() {
        if(npcAttributePools == null)
            return null;
        var nbt = new CompoundTag();
        for(var category : npcAttributePools.keySet()){
            nbt.put(category.name(), npcAttributePools.get(category).getNbt());
        }
        return nbt;
    }

    private ResourceLocation getNpcTextureDataValue() {
        return npcTextureKey;
    }
    public TexturePresetDataPool getNpcTextureData(Level world) {
        return world.registryAccess().registryOrThrow(DynamicRegistriesME.TEXTURE_PRESETS).get(npcTextureKey);
    }


    public void applyAttributes(NpcEntity npcEntity) {
        AttributePool.reverse(npcEntity);
        Race race = RaceLookup.getRace(npcEntity.level(), raceId);
        if(race != null)
            race.applyNpcAttributes(npcEntity);
        EntityCategories category = npcEntity.getNpcCategory();
        if(npcAttributePools.containsKey(EntityCategories.SHARED))
            npcAttributePools.get(EntityCategories.SHARED).apply(npcEntity);
        if(npcAttributePools.containsKey(category))
            npcAttributePools.get(category).apply(npcEntity);

        npcEntity.heal(npcEntity.getMaxHealth() - npcEntity.getHealth());
    }

    public CombatArchetypeRuntimeData getCombatArchetypeRuntime() {
        CombatArchetypeData archetypeData = combatArchetypeData;
        return switch (archetypeData.getArchetype()){
            case MELEE -> new MeleeCombatArchetypeRuntimeData((MeleeCombatArchetypeData) archetypeData);
            case RANGED -> new RangedCombatArchetypeRuntimeData((RangedCombatArchetypeData) archetypeData);
            case HYBRID -> null;
            case SHIELDED -> null;
            case LANCERS -> null;
            case SENTINEL -> null;
            case BRAWLER -> null;
            case BEAST_RIDER -> null;
        };
    }

    public boolean hasCategory(Level world, EntityCategories category) {
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
