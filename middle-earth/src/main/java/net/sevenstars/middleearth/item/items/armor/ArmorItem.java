package net.sevenstars.middleearth.item.items.armor;

import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.ArmorTierDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;

public class ArmorItem extends net.minecraft.world.item.ArmorItem {

    private final ExtendedArmorMaterial extendedMaterial;

    public ArmorItem(ExtendedArmorMaterial material, Type type, Properties settings) {
        super(
                material.material(),
                type,
                settings.durability(type.getDurability(material.durabilityModifier()))
                        .component(DataComponentTypesME.ARMOR_TIER_DATA, new ArmorTierDataComponent(material.tier()))
        );
        this.extendedMaterial = material;
    }

    public ExtendedArmorMaterial getExtendedMaterial() {
        return extendedMaterial;
    }
}
