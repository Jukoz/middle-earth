package net.sevenstars.middleearth.item.items.armor.artefact;

import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.item.items.armor.CustomHelmetItem;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;

public class CustomArtefactHelmetItem extends CustomHelmetItem {
    public CustomArtefactHelmetItem(ExtendedArmorMaterial material, Settings settings) {
        super(material, settings);
    }

    //TODO canRepair gone need to find new thing -> component REPAIRABLE

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
