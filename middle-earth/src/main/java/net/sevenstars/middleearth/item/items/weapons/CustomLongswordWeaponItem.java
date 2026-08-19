package net.sevenstars.middleearth.item.items.weapons;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.sevenstars.middleearth.item.utils.WeaponTypesME;

public class CustomLongswordWeaponItem extends ReachWeaponItem {
    public CustomLongswordWeaponItem(Tier toolMaterial, Item.Properties settings) {
        super(toolMaterial, WeaponTypesME.LONGSWORD, settings);
        this.type = WeaponTypesME.LONGSWORD;
    }
}
