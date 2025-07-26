package net.sevenstars.middleearth.item.items.weapons;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.sevenstars.middleearth.item.utils.ModWeaponTypes;

public class TrollWeaponItem extends Item {

    public TrollWeaponItem(ToolMaterial toolMaterial, Settings settings) {
        super(settings.sword(toolMaterial, ModWeaponTypes.TROLL_WEAPON.attack, ModWeaponTypes.TROLL_WEAPON.attackSpeed));
    }
}
