package net.sevenstars.middleearth.resources.datas.npc_types.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

    public WeightedGearData(CompoundTag gearNbt) {
        this.item = new HashMap<>();

        addSlot(gearNbt, EquipmentSlot.HEAD);
        addSlot(gearNbt, EquipmentSlot.CHEST);
        addSlot(gearNbt, EquipmentSlot.LEGS);
        addSlot(gearNbt, EquipmentSlot.FEET);
        addSlot(gearNbt, EquipmentSlot.MAINHAND);
        addSlot(gearNbt, EquipmentSlot.OFFHAND);
    }

    private void addSlot(CompoundTag gearNbt, EquipmentSlot equipmentSlot) {
        if(gearNbt.get(equipmentSlot.getSerializedName().toLowerCase()) != null){
            if(gearNbt.get(equipmentSlot.getSerializedName().toLowerCase()) != null){
                Tag element = gearNbt.get(equipmentSlot.getSerializedName());
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
    public Tag getNbt(){
        Tag nbt = super.getNbt();
        if(nbt == null)
            nbt = new CompoundTag();

        for(EquipmentSlot slot : item.keySet()){
            ((CompoundTag) nbt).put(slot.getName().toLowerCase(), GearSlotPool.createNbt(item.get(slot)));
        }
        return nbt;
    }

    @Override
    public WeightedGearData withWeight(int newWeight) {
        this.weight = newWeight;
        return this;
    }

    public static WeightedGearData readNbt(CompoundTag nbt){
        return new WeightedGearData(nbt);
    }
}
