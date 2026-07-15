package net.sevenstars.middleearth.resources.datas.npc_types.data;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.sevenstars.api.dtos.WeightedItem;
import net.sevenstars.middleearth.MiddleEarth;

import java.util.HashMap;

public class WeightedGearData extends WeightedItem<HashMap<EquipmentSlot, GearSlotPool>> {
    public WeightedGearData(){
        item = new HashMap<>();
    }

    public static WeightedGearData create() {
        return new WeightedGearData();
    }

    public WeightedGearData(NbtCompound gearNbt) {
        this.item = new HashMap<>();

        addSlot(gearNbt, EquipmentSlot.HEAD);
        addSlot(gearNbt, EquipmentSlot.CHEST);
        addSlot(gearNbt, EquipmentSlot.LEGS);
        addSlot(gearNbt, EquipmentSlot.FEET);
        addSlot(gearNbt, EquipmentSlot.MAINHAND);
        addSlot(gearNbt, EquipmentSlot.OFFHAND);
    }

    private void addSlot(NbtCompound gearNbt, EquipmentSlot equipmentSlot) {
        if(gearNbt.get(equipmentSlot.asString().toLowerCase()) != null){
            if(gearNbt.get(equipmentSlot.asString().toLowerCase()) != null){
                NbtElement element = gearNbt.get(equipmentSlot.asString());
                this.item.put(equipmentSlot, GearSlotPool.readNbt(element));
            }
        }
    }

    public WeightedGearData add(EquipmentSlot slot, GearSlotPool slotData){
        if(item.containsKey(slot)) {
            MiddleEarth.LOGGER.logError("NpcGearData::Overwriting slotData - %s".formatted(slot.getName()));
        }
        item.put(slot, slotData);
        return this;
    }

    public static WeightedGearData Create() {
        return WeightedGearData.create();
    }

    public ItemStack get(EquipmentSlot slot) {
        if(!item.containsKey(slot))
            return new ItemStack(Items.AIR);
        var foundItem = item.get(slot);
        if(foundItem == null )
            return new ItemStack(Items.AIR);
        return foundItem.getItemStack();
    }

    @Override
    public NbtElement getNbt(){
        NbtElement nbt = super.getNbt();
        if(nbt == null)
            nbt = new NbtCompound();

        for(EquipmentSlot slot : item.keySet()){
            nbt.asCompound().get().put(slot.getName().toLowerCase(), GearSlotPool.createNbt(item.get(slot)));
        }
        return nbt;
    }

    @Override
    public WeightedGearData withWeight(int newWeight) {
        this.weight = newWeight;
        return this;
    }

    public static WeightedGearData readNbt(NbtCompound nbt){
        return new WeightedGearData(nbt);
    }
}
