package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.item.Item;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;

public class ArmorItem extends Item {

    private final ExtendedArmorMaterial material;

    public ArmorItem(ExtendedArmorMaterial material, Settings settings) {
        super(settings);
        this.material = material;
    }

    public ExtendedArmorMaterial getMaterial() {
        return material;
    }
}
