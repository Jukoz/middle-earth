package net.sevenstars.middleearth.resources.datas.npcs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcTextureData;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.RaceLookup;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;
import net.sevenstars.middleearth.utils.IdentifierUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class NpcData {
    public static final Codec<NpcData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(NpcData::getIdValue),
            Codec.STRING.fieldOf("race").forGetter(NpcData::getRaceIdValue),
            NbtCompound.CODEC.fieldOf("gears").forGetter(NpcData::getGearDataValues),
            NbtCompound.CODEC.fieldOf("npc_attributes").forGetter(NpcData::getNpcAttributePool),
            NbtCompound.CODEC.fieldOf("npc_textures").forGetter(NpcData::getNpcTextureDataNbt)
    ).apply(instance, NpcData::new));

    private final Identifier id;
    private final Identifier raceId;
    private final List<NpcGearData> gearDatas;
    private final HashMap<EntityCategory, AttributePool> npcAttributePools;
    private final NpcTextureData npcTextureData;

    public NpcData(String id, String raceId, NbtCompound gearDatas, NbtCompound npcAttributes, NbtCompound npcTextureData){
        this.id = IdentifierUtil.getIdentifierFromString(id);
        this.raceId = IdentifierUtil.getIdentifierFromString(raceId);

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
        // Skin Textures
        this.npcTextureData = new NpcTextureData(npcTextureData);
    }

    public NpcData(Identifier id, Race race, List<NpcGearData> gearDatas, HashMap<EntityCategory, AttributePool> npcAttributePools, NpcTextureData npcTextureData){
        this.id = id;
        this.raceId = race.getId();
        this.gearDatas = gearDatas;
        this.npcAttributePools = npcAttributePools;
        this.npcTextureData = npcTextureData;
    }

    private String getIdValue() {
        return id.toString();
    }

    private String getRaceIdValue() {
        return raceId.toString();
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
        return id.getPath();
    }

    public Identifier getId() {
        return id;
    }

    public Identifier getRaceId() {
        return raceId;
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

    private NbtCompound getNpcTextureDataNbt() {
        return npcTextureData.getNbt();
    }
    public NpcTextureData getNpcTextureData() {
        return npcTextureData;
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
    }

    public EntityCategory getRandomCategory() {
        return this.npcTextureData.getRandomCategory();
    }
}
