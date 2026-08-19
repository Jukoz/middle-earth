package net.sevenstars.middleearth.resources.datas.npc_types;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.sevenstars.middleearth.item.items.weapons.ReachWeaponItem;
import net.sevenstars.middleearth.resources.datas.npc_types.data.WeightedGearData;

public class NpcUtil {
    public static void equipAll(LivingEntity entity, WeightedGearData data) {
        entity.setItemSlot(EquipmentSlot.HEAD, data.get(EquipmentSlot.HEAD));
        entity.setItemSlot(EquipmentSlot.CHEST, data.get(EquipmentSlot.CHEST));
        entity.setItemSlot(EquipmentSlot.LEGS, data.get(EquipmentSlot.LEGS));
        entity.setItemSlot(EquipmentSlot.FEET, data.get(EquipmentSlot.FEET));

        ItemStack mainHandItem = data.get(EquipmentSlot.MAINHAND);
        ItemStack offhandItem = data.get(EquipmentSlot.OFFHAND);

        if(mainHandItem != null && mainHandItem.getItem() instanceof ReachWeaponItem reachWeaponItem && reachWeaponItem.type.twoHanded){
            entity.setItemSlot(EquipmentSlot.MAINHAND, mainHandItem);
            entity.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.AIR));
        } else if(offhandItem != null && offhandItem.getItem() instanceof ReachWeaponItem reachWeaponItem && reachWeaponItem.type.twoHanded){
            entity.setItemSlot(EquipmentSlot.MAINHAND, mainHandItem);
            entity.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
        } else {
            entity.setItemSlot(EquipmentSlot.MAINHAND, mainHandItem);
            entity.setItemSlot(EquipmentSlot.OFFHAND, offhandItem);
        }
    }
}
