package net.sevenstars.middleearth.resources.datas.npcs.data;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.sevenstars.api.dtos.WeightedItem;
import net.sevenstars.middleearth.MiddleEarth;

import java.util.HashMap;

public class GearData extends WeightedItem<HashMap<EquipmentSlot, GearSlotPool>> {
    public GearData(){
        item = new HashMap<>();
    }
    public static GearData create() {
        return new GearData();
    }

    public GearData(NbtCompound gearNbt) {
        this.item = new HashMap<>();
        addSlot(gearNbt, EquipmentSlot.HEAD);
        addSlot(gearNbt, EquipmentSlot.CHEST);
        addSlot(gearNbt, EquipmentSlot.LEGS);
        addSlot(gearNbt, EquipmentSlot.FEET);
        addSlot(gearNbt, EquipmentSlot.MAINHAND);
        addSlot(gearNbt, EquipmentSlot.OFFHAND);
    }

    private void addSlot(NbtCompound gearNbt, EquipmentSlot equipmentSlot) {
        if(gearNbt.get(equipmentSlot.asString()) != null){
            if(gearNbt.getCompound(equipmentSlot.asString()).isPresent()){
                NbtCompound slotNbt = gearNbt.getCompound(equipmentSlot.asString()).get();
                this.item.put(equipmentSlot, GearSlotPool.readNbt(slotNbt));
            }
        }
    }

    public GearData add(EquipmentSlot slot, GearSlotPool slotData){
        if(item.containsKey(slot)) {
            MiddleEarth.LOGGER.logError("NpcGearData::Overwriting slotData - %s".formatted(slot.getName()));
        }
        item.put(slot, slotData);
        return this;
    }

    public static GearData Create() {
        return GearData.create();
    }

    public ItemStack get(EquipmentSlot slot) {
        if(!item.containsKey(slot))
            return new ItemStack(Items.AIR);
        var foundItem = item.get(slot);
        if(foundItem == null )
            return new ItemStack(Items.AIR);
        return foundItem.getItemStack();
    }

    public NbtElement getNbt(){
        NbtElement nbt = super.getNbt();
        if(nbt == null)
            nbt = new NbtCompound();

        for(EquipmentSlot slot : item.keySet()){
            nbt.asCompound().get().put(slot.getName().toLowerCase(), GearSlotPool.createNbt(item.get(slot)));
        }
        return nbt;
    }
    public static GearData readNbt(NbtCompound nbt){
        return new GearData(nbt);
    }
}
