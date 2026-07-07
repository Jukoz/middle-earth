package net.sevenstars.middleearth.item.items.shields;

import net.minecraft.item.Item;
import net.sevenstars.middleearth.item.utils.ShieldTypesME;
import net.minecraft.item.ItemStack;

public class ArtefactCustomShieldItem extends CustomShieldItem {

    public ArtefactCustomShieldItem(ShieldTypesME type, Item.Settings settings) {
        super(type, settings);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        if(stack.getDamage() == stack.getMaxDamage() - 1) {
            return false;
        } else if( stack.getDamage() >= 1) {
            return true;
        } else {
            return false;
        }
    }
}
