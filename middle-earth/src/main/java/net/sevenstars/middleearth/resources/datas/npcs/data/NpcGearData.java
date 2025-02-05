package net.sevenstars.middleearth.resources.datas.npcs.data;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.sevenstars.middleearth.MiddleEarth;

import java.util.HashMap;

public class NpcGearData {
    public final HashMap<EquipmentSlot, NpcGearSlotData> gears;

    public NpcGearData(){
        gears = new HashMap<>();
    }
    public static NpcGearData create() {
        return new NpcGearData();
    }
    public NpcGearData(NbtCompound gearNbt) {
        this.gears = new HashMap<>();
        addSlot(gearNbt, EquipmentSlot.HEAD);
        addSlot(gearNbt, EquipmentSlot.CHEST);
        addSlot(gearNbt, EquipmentSlot.LEGS);
        addSlot(gearNbt, EquipmentSlot.FEET);
        addSlot(gearNbt, EquipmentSlot.MAINHAND);
        addSlot(gearNbt, EquipmentSlot.OFFHAND);
    }

    private void addSlot(NbtCompound gearNbt, EquipmentSlot equipmentSlot) {
        if(gearNbt.get(equipmentSlot.asString()) != null){
            NbtCompound slotNbt = gearNbt.getCompound(equipmentSlot.asString());
            this.gears.put(equipmentSlot, NpcGearSlotData.readNbt(slotNbt));
        }
    }

    public NpcGearData add(EquipmentSlot slot, NpcGearSlotData slotData){
        if(gears.containsKey(slot)) {
            MiddleEarth.LOGGER.logError("NpcGearData::Overwriting slotData - %s".formatted(slot.getName()));
        }
        gears.put(slot, slotData);
        return this;
    }

    public static NpcGearData Create() {
        return NpcGearData.create();
    }

    public ItemStack get(EquipmentSlot slot) {
        if(!gears.containsKey(slot))
            return new ItemStack(Items.AIR);
        return gears.get(slot).getItemStack();
    }

    public static NbtCompound createNbt(NpcGearData gearData){
        NbtCompound nbt = new NbtCompound();
        for(EquipmentSlot slot : gearData.gears.keySet()){
            nbt.put(slot.getName().toLowerCase(), NpcGearSlotData.createNbt(gearData.gears.get(slot)));
        }
        return nbt;
    }
    public static NpcGearData readNbt(NbtCompound nbt){
        return new NpcGearData(nbt);
    }
}
