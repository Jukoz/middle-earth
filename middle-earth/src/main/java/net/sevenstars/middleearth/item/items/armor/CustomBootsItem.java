package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.item.equipment.EquipmentType;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;

public class CustomBootsItem extends ArmorItem {

    public CustomBootsItem(ExtendedArmorMaterial material, Settings settings) {
        super(material, settings.armor(material.material(), EquipmentType.BOOTS).maxCount(1));
    }
}
