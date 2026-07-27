package net.sevenstars.middleearth.item.items.armor;

import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;

public class CustomBootsItem extends ArmorItem {

    public CustomBootsItem(ExtendedArmorMaterial material, Properties settings) {
        super(material, Type.BOOTS, settings.stacksTo(1));
    }
}
