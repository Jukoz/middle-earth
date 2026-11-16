package net.sevenstars.middleearth.resources.datas.npc_classes;

import net.sevenstars.middleearth.item.items.weapons.ReachWeaponItem;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.WeightedGearData;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class NpcUtil {
    public static void equipAll(LivingEntity entity, WeightedGearData data) {
        entity.equipStack(EquipmentSlot.HEAD, data.get(EquipmentSlot.HEAD));
        entity.equipStack(EquipmentSlot.CHEST, data.get(EquipmentSlot.CHEST));
        entity.equipStack(EquipmentSlot.LEGS, data.get(EquipmentSlot.LEGS));
        entity.equipStack(EquipmentSlot.FEET, data.get(EquipmentSlot.FEET));

        ItemStack mainHandItem = data.get(EquipmentSlot.MAINHAND);
        ItemStack offhandItem = data.get(EquipmentSlot.OFFHAND);

        // Nothing goes in the offhand next to a two-handed weapon
        entity.equipStack(EquipmentSlot.MAINHAND, mainHandItem);
        entity.equipStack(EquipmentSlot.OFFHAND, isTwoHanded(mainHandItem) || isTwoHanded(offhandItem) ? ItemStack.EMPTY : offhandItem);
    }

    private static boolean isTwoHanded(ItemStack stack) {
        return stack.getItem() instanceof ReachWeaponItem reachWeapon && reachWeapon.type.twoHanded;
    }
}
