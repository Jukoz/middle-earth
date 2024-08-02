package net.jukoz.me.resources.datas.faction.utils;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class FactionNpcPreviewData{
    private HashMap<EquipmentSlot, ItemStack> gears;

    /**
     * Faction Npc Preview Data constructor, for showcasing specific entities with armors and weapons
     * @param head Head equipment [Helmet]
     * @param chest Chest equipment [Chestplate]
     * @param legs Leg equipment [Leggings]
     * @param feet Feet equipment [Boots]
     * @param mainHand Item on the left of the entity (back view)
     * @param offHand Item on the right of the entity (front view)
     */
    public FactionNpcPreviewData(Item head, Item chest, Item legs, Item feet, Item mainHand, Item offHand){
        this.gears = new HashMap<>();
        this.gears.put(EquipmentSlot.HEAD, new ItemStack(head));
        this.gears.put(EquipmentSlot.CHEST, new ItemStack(chest));
        this.gears.put(EquipmentSlot.LEGS, new ItemStack(legs));
        this.gears.put(EquipmentSlot.FEET, new ItemStack(feet));
        this.gears.put(EquipmentSlot.MAINHAND, new ItemStack(mainHand));
        this.gears.put(EquipmentSlot.OFFHAND, new ItemStack(offHand));
    }

    public ItemStack get(EquipmentSlot slot){
        if(slot == null)
            return null;
        return gears.get(slot);
    }
}
