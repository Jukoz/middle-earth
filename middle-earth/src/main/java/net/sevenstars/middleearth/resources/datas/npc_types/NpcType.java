package net.sevenstars.middleearth.resources.datas.npc_types;

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
import net.sevenstars.middleearth.resources.datas.combatarchetypes.CombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.MeleeCombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.RangedCombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.runtime.CombatArchetypeRuntimeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.runtime.MeleeCombatArchetypeRuntimeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.runtime.RangedCombatArchetypeRuntimeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.utils.CombatArchetypeDataUtil;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
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
            Identifier.CODEC.fieldOf("id").forGetter(NpcType::getId),
            Identifier.CODEC.fieldOf("race").forGetter(NpcType::getRace),
            Identifier.CODEC.fieldOf("faction").forGetter(NpcType::getFactionIdentifier),
            Identifier.CODEC.fieldOf("base_npc_texture").forGetter(NpcType::getNpcTextureDataValue),
            NbtCompound.CODEC.fieldOf("gear").forGetter(NpcType::getGearDataValues),
            NbtCompound.CODEC.fieldOf("npc_attributes").forGetter(NpcType::getNpcAttributePool),
            NbtCompound.CODEC.fieldOf("combat_archetype").forGetter(NpcType::getCombatArchetypeData),
            MountData.CODEC.optionalFieldOf("mount").forGetter(NpcType::getOptionalMountData)
    ).apply(instance, NpcType::new));

    private final Identifier id;
    private final Identifier raceId;
    private final Identifier factionId;
    private final Identifier npcTextureKey;
    private final CombatArchetypeData combatArchetypeData;
    private final WeightedPool<WeightedGearData> gearDatas;
    private final HashMap<EntityCategories, AttributePool> npcAttributePools;
    private final MountData mountData;
    public NpcType(Identifier id, Identifier raceId, Identifier factionId, Identifier npcTextureKey, NbtCompound gearDatas, NbtCompound npcAttributes, NbtCompound combatArchetypeData, Optional<MountData> mount) {
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

        this.combatArchetypeData = CombatArchetypeDataUtil.create(combatArchetypeData);
        this.mountData = mount.orElse(null);
    }
    public NpcType(Identifier id, RegistryKey<Race> race, RegistryKey<Faction> faction, RegistryKey<TexturePresetDataPool> npcTextureKey, List<WeightedGearData> weightedGearData, HashMap<EntityCategories, AttributePool> npcAttributePools, CombatArchetypeData combatArchetypeData) {
        this(id, race, faction, npcTextureKey, weightedGearData, npcAttributePools, combatArchetypeData, null);
    }

    public NpcType(Identifier id, RegistryKey<Race> race, RegistryKey<Faction> faction, RegistryKey<TexturePresetDataPool> npcTextureKey, List<WeightedGearData> weightedGearData, HashMap<EntityCategories, AttributePool> npcAttributePools, CombatArchetypeData combatArchetypeData, MountData mount) {
        this.id = id;
        this.raceId = race.getValue();
        this.factionId = faction.getValue();
        this.npcTextureKey = npcTextureKey.getValue();
        this.gearDatas = new WeightedPool<>(weightedGearData);
        this.npcAttributePools = npcAttributePools;
        this.combatArchetypeData = combatArchetypeData;
        this.mountData = mount;
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

    private NbtCompound getCombatArchetypeData() {
        return combatArchetypeData.getNbt();
    }

    private Optional<MountData> getOptionalMountData() {
        return Optional.ofNullable(mountData);
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
        Race race = RaceLookup.getRace(npcEntity.getWorld(), raceId);
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
