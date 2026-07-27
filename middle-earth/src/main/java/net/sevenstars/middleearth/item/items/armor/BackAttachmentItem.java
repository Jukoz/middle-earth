package net.sevenstars.middleearth.item.items.armor;

import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;

public class BackAttachmentItem extends ArmorItem {

    public BackAttachmentItem(Properties settings, ExtendedArmorMaterial material) {
        super(material, Type.CHESTPLATE, settings.stacksTo(1));
    }
}
