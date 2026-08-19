package net.sevenstars.middleearth.item.items.armor;

import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;

public class CustomLeggingsItem extends ArmorItem {

    public CustomLeggingsItem(ExtendedArmorMaterial material, Properties settings) {
        super(material, Type.LEGGINGS, settings.stacksTo(1));
    }
}
