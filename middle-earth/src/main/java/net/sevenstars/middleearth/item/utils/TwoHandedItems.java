package net.sevenstars.middleearth.item.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.item.items.shields.CustomSiegeShieldItem;
import net.sevenstars.middleearth.item.items.weapons.ReachWeaponItem;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomLongbowWeaponItem;

public class TwoHandedItems {
    public static boolean isTwoHanded(ItemStack stack) {
        if(stack == null) return false;
        Item item = stack.getItem();
        return (item instanceof ReachWeaponItem reachWeapon && reachWeapon.type.twoHanded)
                || item instanceof CustomSiegeShieldItem
                || item instanceof CustomLongbowWeaponItem;
    }
}
