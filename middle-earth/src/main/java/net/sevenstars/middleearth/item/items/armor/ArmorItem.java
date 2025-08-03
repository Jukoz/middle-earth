package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.item.Item;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.ArmorTierDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;

public class ArmorItem extends Item {

    private final ExtendedArmorMaterial material;

    public ArmorItem(ExtendedArmorMaterial material, Settings settings) {
        super(settings.component(DataComponentTypesME.ARMOR_TIER_DATA, new ArmorTierDataComponent(material.tier())));
        this.material = material;
    }

    public ExtendedArmorMaterial getMaterial() {
        return material;
    }
}
