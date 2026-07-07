package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.item.equipment.EquipmentType;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;

public class CustomLeggingsItem extends ArmorItem {

    public CustomLeggingsItem(ExtendedArmorMaterial material, Settings settings) {
        super(material, settings.armor(material.material(), EquipmentType.LEGGINGS).maxCount(1));
    }
}
