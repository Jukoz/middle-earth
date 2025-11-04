package net.sevenstars.middleearth.item.items.weapons;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.WeaponTypeDataComponent;
import net.sevenstars.middleearth.item.utils.WeaponTypesME;

public class TrollWeaponItem extends Item {

    public TrollWeaponItem(ToolMaterial toolMaterial, Settings settings) {
        super(settings.sword(toolMaterial, WeaponTypesME.TROLL_WEAPON.attack, WeaponTypesME.TROLL_WEAPON.attackSpeed)
                .component(DataComponentTypesME.WEAPON_TYPE_DATA, new WeaponTypeDataComponent(WeaponTypesME.TROLL_WEAPON.name)));
    }
}
