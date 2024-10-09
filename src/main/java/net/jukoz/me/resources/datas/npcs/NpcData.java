package net.jukoz.me.resources.datas.npcs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.util.NbtType;
import net.jukoz.me.resources.datas.npcs.data.NpcGearData;
import net.jukoz.me.utils.IdentifierUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NpcData {
    public static final Codec<NpcData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(NpcData::getIdValue),
            Codec.STRING.fieldOf("race").forGetter(NpcData::getRaceIdValue),
            NbtCompound.CODEC.fieldOf("gears").forGetter(NpcData::getGearDataValues)
    ).apply(instance, NpcData::new));

    private final Identifier id;
    private final Identifier raceId;
    private final List<NpcGearData> gearDatas;
    public NpcData(String id, String raceId, NbtCompound gearDatas){
        this.id = IdentifierUtil.getIdentifierFromString(id);
        this.raceId = IdentifierUtil.getIdentifierFromString(raceId);

        NbtList npcGears = gearDatas.getList("pool", NbtType.COMPOUND);
        List<NpcGearData> npcGearDatas = new ArrayList<>();
        for(int j = 0; j < npcGears.size(); j++) {
            NbtCompound compound = npcGears.getCompound(j);
            npcGearDatas.add(NpcGearData.readNbt(compound));
        }
        this.gearDatas = npcGearDatas;
    }

    public NpcData(Identifier id, Identifier raceId, List<NpcGearData> gearDatas){
        this.id = id;
        this.raceId = raceId;
        this.gearDatas = gearDatas;
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
}
