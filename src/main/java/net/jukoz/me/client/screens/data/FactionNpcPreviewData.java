package net.jukoz.me.client.screens.data;

import net.jukoz.me.utils.Factions;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FactionNpcPreviewData{
    public final Factions FACTION;
    public final ItemStack HEAD;
    public final ItemStack CHEST;
    public final ItemStack LEGS;
    public final ItemStack FEET;
    public final ItemStack MAIN_HAND;
    public final ItemStack OFF_HAND;

    /**
     * Faction Npc Preview Data constructor, for showcasing specific entities with armors and weapons
     * @param factions Linked faction
     * @param head Head equipment [Helmet]
     * @param chest Chest equipment [Chestplate]
     * @param legs Leg equipment [Leggings]
     * @param feet Feet equipment [Boots]
     * @param mainHand Item on the left of the entity (back view)
     * @param offHand Item on the right of the entity (front view)
     */
    public FactionNpcPreviewData(Factions factions, Item head, Item chest, Item legs, Item feet, Item mainHand, Item offHand){
        this.FACTION = factions;
        this.HEAD = new ItemStack(head);
        this.CHEST = new ItemStack(chest);
        this.LEGS = new ItemStack(legs);
        this.FEET = new ItemStack(feet);
        this.MAIN_HAND = new ItemStack(mainHand);
        this.OFF_HAND = new ItemStack(offHand);
    }
}
