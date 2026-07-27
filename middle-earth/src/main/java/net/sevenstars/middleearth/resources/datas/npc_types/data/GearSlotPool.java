package net.sevenstars.middleearth.resources.datas.npc_types.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.api.dtos.WeightedPool;
import net.sevenstars.middleearth.MiddleEarth;

public class GearSlotPool {
    private WeightedItemData weightedItemData;
    private WeightedPool<WeightedItemData> npcGearItemPool;

    public GearSlotPool() {
        this.npcGearItemPool = new WeightedPool<>();
    }

    public static GearSlotPool create() {
        return new GearSlotPool();
    }

    public static GearSlotPool create(WeightedItemData weightedItemData) {
        return new GearSlotPool(weightedItemData);
    }

    public GearSlotPool(WeightedItemData weightedItemData) {
        this.weightedItemData = weightedItemData;
    }

    public GearSlotPool add(WeightedItemData newGearData){
        if(this.npcGearItemPool == null)
            this.npcGearItemPool = new WeightedPool<>();
        if(this.weightedItemData != null)
            this.weightedItemData = null;
        this.npcGearItemPool.add(newGearData);
        return this;
    }

    public boolean isPool(){
        return npcGearItemPool != null && !npcGearItemPool.isEmpty();
    }

    public static Tag createNbt(GearSlotPool slotData){
        if(slotData.isPool()){
            CompoundTag nbt = new CompoundTag();
            ListTag nbtList = new ListTag();
            for(WeightedItemData gearItemData : slotData.npcGearItemPool.elements){
                nbtList.add(WeightedPool.toCompoundListElement(gearItemData.getNbt()));
            }
            nbt.put("pool",nbtList);
            return nbt;
        }
        else if(slotData.weightedItemData == null){
            return null;
        }
        var nbt = slotData.weightedItemData.getNbt();
        if(nbt instanceof net.minecraft.nbt.StringTag){
            return nbt;
        } else if(nbt instanceof CompoundTag compound)
            return compound;
        return null;
    }

    public static GearSlotPool readNbt(Tag nbt){
        if(nbt instanceof CompoundTag nbtCompound){
            if(!(nbtCompound.get("pool") instanceof ListTag list)){
                return GearSlotPool.create(new WeightedItemData(nbtCompound));
            }
            GearSlotPool gearSlotPool = GearSlotPool.create();
            for(int i = 0; i < list.size(); i++){
                Tag element = list.get(i);
                if(element instanceof net.minecraft.nbt.StringTag){
                    gearSlotPool.add(new WeightedItemData(MiddleEarth.fetchId(element.getAsString())));
                } else if(element instanceof CompoundTag compound){
                    gearSlotPool.add(new WeightedItemData(compound));
                }
            }
            return gearSlotPool;
        } else if(nbt instanceof net.minecraft.nbt.StringTag){
            return new GearSlotPool(new WeightedItemData(MiddleEarth.fetchId(nbt.getAsString())));
        }

        return null;
    }

    public ItemStack getItemStack() {
        if(isPool()){
            return npcGearItemPool.getRandom().getItem().getItemStack();
        }
        if(weightedItemData != null)
            return weightedItemData.getItem().getItemStack();
        return ItemStack.EMPTY;
    }
}
