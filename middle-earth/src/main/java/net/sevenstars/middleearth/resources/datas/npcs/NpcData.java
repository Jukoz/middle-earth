package net.sevenstars.middleearth.resources.datas.npcs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.TexturePresets;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.RaceLookup;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class NpcData {
    public static final Codec<NpcData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("id").forGetter(NpcData::getId),
            Identifier.CODEC.fieldOf("race").forGetter(NpcData::getRace),
            Identifier.CODEC.fieldOf("faction").forGetter(NpcData::getFaction),
            Identifier.CODEC.fieldOf("base_npc_texture").forGetter(NpcData::getNpcTextureDataValue),
            NbtCompound.CODEC.fieldOf("gears").forGetter(NpcData::getGearDataValues),
            NbtCompound.CODEC.fieldOf("npc_attributes").forGetter(NpcData::getNpcAttributePool)
    ).apply(instance, NpcData::new));

    private final Identifier id;
    private final Identifier raceId;
    private final Identifier factionId;
    private final Identifier npcTextureKey;
    private final List<NpcGearData> gearDatas;
    private final HashMap<EntityCategory, AttributePool> npcAttributePools;

    public NpcData(Identifier id, Identifier raceId, Identifier factionId, Identifier npcTextureKey, NbtCompound gearDatas, NbtCompound npcAttributes){
        this.id = id;
        this.raceId = raceId;
        this.factionId = factionId;
        this.npcTextureKey = npcTextureKey;

        NbtList npcGears = gearDatas.getList("pool").get();
        List<NpcGearData> npcGearDatas = new ArrayList<>();
        for(int j = 0; j < npcGears.size(); j++) {
            NbtCompound compound = npcGears.getCompound(j).get();
            npcGearDatas.add(NpcGearData.readNbt(compound));
        }
        this.gearDatas = npcGearDatas;

        this.npcAttributePools = new HashMap<>();
        for(var category : EntityCategory.values()){
            if(npcAttributes.contains(category.name())){
                this.npcAttributePools.put(category, new AttributePool(npcAttributes.getCompound(category.name()).get()));
            }
        }
    }

    public NpcData(Identifier id, RegistryKey<Race> race, RegistryKey<Faction> faction, RegistryKey<TexturePresets> npcTextureKey, List<NpcGearData> gearDatas, HashMap<EntityCategory, AttributePool> npcAttributePools){
        this.id = id;
        this.raceId = race.getValue();
        this.factionId = faction.getValue();
        this.npcTextureKey = npcTextureKey.getValue();
        this.gearDatas = gearDatas;
        this.npcAttributePools = npcAttributePools;
    }

    public Identifier getId() {
        return id;
    }

    public Identifier getRace() {
        return raceId;
    }
    public Identifier getFaction() {
        return factionId;
    }

    private NbtCompound getGearDataValues() {
        NbtCompound nbt = new NbtCompound();
        NbtList gears = new NbtList();
        for(NpcGearData npcGearData : this.gearDatas){
            gears.add(NpcGearData.createNbt(npcGearData));
        }
        nbt.put("pool", gears);
        return nbt;
    }

    public String getName(){
        return id.toTranslationKey("npc_data");
    }

    public NpcGearData getGear() {
        if(gearDatas == null)
            return null;

        if(gearDatas.size() == 1)
            return gearDatas.getFirst();

        Random random = new Random();
        return gearDatas.get(random.nextInt(0, gearDatas.size()));
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
    public TexturePresets getNpcTextureData(World world) {
        return world.getRegistryManager().getOrThrow(DynamicRegistriesME.TEXTURE_PRESETS).get(npcTextureKey);
    }


    public void applyAttributes(NpcEntity npcEntity) {
        AttributePool.reverse(npcEntity);
        Race race = RaceLookup.getRace(npcEntity.getWorld(), raceId);
        if(race != null)
            race.applyNpcAttributes(npcEntity);
        EntityCategory category = npcEntity.getNpcCategory();
        if(npcAttributePools.containsKey(EntityCategory.SHARED))
            npcAttributePools.get(EntityCategory.SHARED).apply(npcEntity);
        if(npcAttributePools.containsKey(category))
            npcAttributePools.get(category).apply(npcEntity);

        npcEntity.heal(npcEntity.getMaxHealth() - npcEntity.getHealth());
    }
}
