package net.sevenstars.middleearth.resources.datas.npcs;

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
import net.sevenstars.middleearth.resources.datas.combatarchetypes.data.CombatArchetype;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.runtime.CombatArchetypeRuntimeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.runtime.MeleeCombatArchetypeRuntimeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.runtime.RangedCombatArchetypeRuntimeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.utils.CombatArchetypeDataUtil;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.data.WeightedGearData;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDataPool;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.RaceLookup;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NpcData {
    public static final Codec<NpcData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("id").forGetter(NpcData::getId),
            Identifier.CODEC.fieldOf("race").forGetter(NpcData::getRace),
            Identifier.CODEC.fieldOf("faction").forGetter(NpcData::getFactionIdentifier),
            Identifier.CODEC.fieldOf("base_npc_texture").forGetter(NpcData::getNpcTextureDataValue),
            NbtCompound.CODEC.fieldOf("gear").forGetter(NpcData::getGearDataValues),
            NbtCompound.CODEC.fieldOf("npc_attributes").forGetter(NpcData::getNpcAttributePool),
            NbtCompound.CODEC.fieldOf("combat_archetype").forGetter(NpcData::getCombatArchetypeData)
    ).apply(instance, NpcData::new));

    private final Identifier id;
    private final Identifier raceId;
    private final Identifier factionId;
    private final Identifier npcTextureKey;
    private final CombatArchetypeData combatArchetypeData;
    private final WeightedPool<WeightedGearData> gearDatas;
    private final HashMap<EntityCategories, AttributePool> npcAttributePools;

    public NpcData(Identifier id, Identifier raceId, Identifier factionId, Identifier npcTextureKey, NbtCompound gearDatas, NbtCompound npcAttributes, NbtCompound combatArchetypeData) {
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
    }

    public NpcData(Identifier id, RegistryKey<Race> race, RegistryKey<Faction> faction, RegistryKey<TexturePresetDataPool> npcTextureKey, List<WeightedGearData> weightedGearData, HashMap<EntityCategories, AttributePool> npcAttributePools, CombatArchetypeData combatArchetypeData) {
        this.id = id;
        this.raceId = race.getValue();
        this.factionId = faction.getValue();
        this.npcTextureKey = npcTextureKey.getValue();
        this.gearDatas = new WeightedPool<>(weightedGearData);
        this.npcAttributePools = npcAttributePools;
        this.combatArchetypeData = combatArchetypeData;
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
        //EntityCategories category = npcEntity.getNpcCategory();
        //if(npcAttributePools.containsKey(EntityCategories.SHARED))
        //    npcAttributePools.get(EntityCategories.SHARED).apply(npcEntity);
        //if(npcAttributePools.containsKey(category))
        //    npcAttributePools.get(category).apply(npcEntity);

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
}
