package net.sevenstars.middleearth.item.items.armor;

import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;

public class MountArmorItem extends ArmorItem {
    public MountArmorItem(ExtendedArmorMaterial material, Properties properties) {
        super(material, Type.BODY, properties.stacksTo(1));
    }
}
