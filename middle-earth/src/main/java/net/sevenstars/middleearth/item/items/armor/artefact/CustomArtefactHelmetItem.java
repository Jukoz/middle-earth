package net.sevenstars.middleearth.item.items.armor.artefact;

import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.item.items.armor.CustomHelmetItem;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;

public class CustomArtefactHelmetItem extends CustomHelmetItem {
    public CustomArtefactHelmetItem(ExtendedArmorMaterial material, Properties settings) {
        super(material, settings);
    }

    //TODO canRepair gone need to find new thing -> component REPAIRABLE

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
