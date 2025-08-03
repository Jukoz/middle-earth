package net.sevenstars.middleearth.item.items.weapons;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.WeaponTypeDataComponent;
import net.sevenstars.middleearth.item.utils.ModWeaponTypes;

public class TrollWeaponItem extends Item {

    public TrollWeaponItem(ToolMaterial toolMaterial, Settings settings) {
        super(settings.sword(toolMaterial, ModWeaponTypes.TROLL_WEAPON.attack, ModWeaponTypes.TROLL_WEAPON.attackSpeed)
                .component(DataComponentTypesME.WEAPON_TYPE_DATA, new WeaponTypeDataComponent(ModWeaponTypes.TROLL_WEAPON.name)));
    }
}
