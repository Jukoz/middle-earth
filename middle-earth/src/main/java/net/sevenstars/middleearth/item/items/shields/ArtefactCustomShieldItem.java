package net.sevenstars.middleearth.item.items.shields;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.item.utils.ShieldTypesME;

public class ArtefactCustomShieldItem extends CustomShieldItem {

    public ArtefactCustomShieldItem(ShieldTypesME type, Item.Properties settings) {
        super(type, settings);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        if(stack.getDamageValue() == stack.getMaxDamage() - 1) {
            return false;
        } else if( stack.getDamageValue() >= 1) {
            return true;
        } else {
            return false;
        }
    }
}
