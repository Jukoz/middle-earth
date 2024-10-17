package net.jukoz.me.resources.datas.npcs;

import net.jukoz.me.item.items.weapons.ReachWeaponItem;
import net.jukoz.me.resources.datas.npcs.data.NpcGearData;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class NpcUtil {
    public static void equipAll(LivingEntity entity, NpcGearData data) {
        entity.equipStack(EquipmentSlot.HEAD, data.get(EquipmentSlot.HEAD));
        entity.equipStack(EquipmentSlot.CHEST, data.get(EquipmentSlot.CHEST));
        entity.equipStack(EquipmentSlot.LEGS, data.get(EquipmentSlot.LEGS));
        entity.equipStack(EquipmentSlot.FEET, data.get(EquipmentSlot.FEET));

        ItemStack mainHandItem = data.get(EquipmentSlot.MAINHAND);
        ItemStack offhandItem = data.get(EquipmentSlot.OFFHAND);

        if(mainHandItem != null && mainHandItem.getItem() instanceof ReachWeaponItem reachWeaponItem && reachWeaponItem.type.twoHanded){
            entity.equipStack(EquipmentSlot.MAINHAND, mainHandItem);
            entity.equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.AIR));
        } else if(offhandItem != null && offhandItem.getItem() instanceof ReachWeaponItem reachWeaponItem && reachWeaponItem.type.twoHanded){
            entity.equipStack(EquipmentSlot.MAINHAND, offhandItem);
            entity.equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.AIR));
        } else {
            entity.equipStack(EquipmentSlot.MAINHAND, mainHandItem);
            entity.equipStack(EquipmentSlot.OFFHAND, offhandItem);
        }
    }
}
