package net.jukoz.me.resources.datas.npcs.data;

import net.fabricmc.fabric.api.util.NbtType;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;

public class NpcGearData {
    public final HashMap<EquipmentSlot, NpcGearSlotData> gears;

    public NpcGearData(){
        gears = new HashMap<>();
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
            LoggerUtil.logError("NpcGearData::Overwriting slotData - %s".formatted(slot.getName()));
        }
        gears.put(slot, slotData);
        return this;
    }

    public static NpcGearData Create() {
        return new NpcGearData();
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
