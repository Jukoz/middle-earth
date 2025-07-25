package net.sevenstars.middleearth.item.items.weapons;

import net.minecraft.item.Item;
import net.sevenstars.middleearth.item.utils.ModWeaponTypes;
import net.minecraft.item.ToolMaterial;

public class CustomLongswordWeaponItem extends ReachWeaponItem {
    public CustomLongswordWeaponItem(ToolMaterial toolMaterial, Item.Settings settings) {
        super(toolMaterial, ModWeaponTypes.LONGSWORD, settings);
        this.type = ModWeaponTypes.LONGSWORD;
    }
}
