package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.item.equipment.EquipmentType;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;

public class CustomChestplateItem extends ArmorItem {

    public CustomChestplateItem(ExtendedArmorMaterial material, Settings settings) {
        super(material, settings.armor(material.material(), EquipmentType.CHESTPLATE).maxCount(1));
    }
}
