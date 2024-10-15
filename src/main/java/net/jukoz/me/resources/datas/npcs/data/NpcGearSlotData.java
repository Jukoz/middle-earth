package net.jukoz.me.resources.datas.npcs.data;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NpcGearSlotData {
    private NpcGearItemData npcGearItemData;
    private List<NpcGearItemData> npcGearItemPool;

    public NpcGearSlotData() {
        this.npcGearItemPool = new ArrayList<>();
    }
    public static NpcGearSlotData create() {
        return new NpcGearSlotData();
    }


    public NpcGearSlotData(NpcGearItemData npcGearItemData) {
        this.npcGearItemData = npcGearItemData;
    }

    public NpcGearSlotData add(NpcGearItemData newGearData){
        if(this.npcGearItemPool == null)
            this.npcGearItemPool = new ArrayList<>();
        if(this.npcGearItemData != null)
            this.npcGearItemData = null;
        this.npcGearItemPool.add(newGearData);
        return this;
    }

    public boolean isPool(){
        return npcGearItemPool != null && !npcGearItemPool.isEmpty();
    }

    public static NbtCompound createNbt(NpcGearSlotData slotData){
        if(slotData.isPool()){
            NbtCompound nbt = new NbtCompound();
            NbtList nbtList = new NbtList();
            for(NpcGearItemData gearItemData : slotData.npcGearItemPool){
                nbtList.add(NpcGearItemData.createNbt(gearItemData));
            }
            nbt.put("pool",nbtList);
            return nbt;
        }
        else if(slotData.npcGearItemData == null){
            return null;
        }
        return NpcGearItemData.createNbt(slotData.npcGearItemData);
    }
    public static NpcGearSlotData readNbt(NbtCompound nbt){
        if(nbt.get("pool") == null){
            return new NpcGearSlotData(NpcGearItemData.readNbt(nbt));
        }
        NbtList list = nbt.getList("pool", NbtType.COMPOUND);
        NpcGearSlotData npcGearSlotData = new NpcGearSlotData();
        for(int i = 0; i < list.size(); i++){
            npcGearSlotData.add(NpcGearItemData.readNbt(list.getCompound(i)));
        }
        return npcGearSlotData;
    }

    public ItemStack getItemStack() {
        if(isPool()){
            if(npcGearItemPool == null)
                return null;
            if(npcGearItemPool.size() == 1)
                return npcGearItemPool.getFirst().getItem();
            return npcGearItemPool.get(findIndex()).getItem();
        }
        return npcGearItemData.getItem();
    }

    private int findIndex() {
        List<Integer> indexes = new ArrayList<>();
        for(int i = 0; i < npcGearItemPool.size(); i ++){
            int weight = npcGearItemPool.get(i).getWeight();
            for(int j = 0; j < weight; j ++){
                indexes.add(i);
            }
        }
        Random random = new Random();
        int current = random.nextInt(0, indexes.size());
        return indexes.get(current);
        /*
        // 2 different methods of weight randomness
        int foundIndex = 0;
        do{
            int weight = npcGearItemPool.get(indexes.get(current)).getWeight();
            if(current - weight < 0){
                foundIndex = indexes.get(current);
            }
            current -= weight;
        } while(current >= 0);
        return foundIndex;
         */
    }
}
