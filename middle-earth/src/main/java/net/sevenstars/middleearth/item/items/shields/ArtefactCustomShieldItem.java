package net.sevenstars.middleearth.item.items.shields;

import net.minecraft.item.Item;
import net.sevenstars.middleearth.item.utils.ModShieldTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ArtefactCustomShieldItem extends CustomShieldItem {

    public ArtefactCustomShieldItem(ModShieldTypes type, Item.Settings settings) {
        super(type, settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey()).formatted(Formatting.AQUA).formatted(Formatting.ITALIC);
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
