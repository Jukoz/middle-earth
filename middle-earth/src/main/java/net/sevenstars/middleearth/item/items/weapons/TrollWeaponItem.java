package net.sevenstars.middleearth.item.items.weapons;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.WeaponTypeDataComponent;
import net.sevenstars.middleearth.item.utils.WeaponTypesME;

public class TrollWeaponItem extends SwordItem {

    public TrollWeaponItem(Tier toolMaterial, Properties settings) {
        super(toolMaterial, settings.attributes(SwordItem.createAttributes(
                        toolMaterial, WeaponTypesME.TROLL_WEAPON.attack, WeaponTypesME.TROLL_WEAPON.attackSpeed))
                .component(DataComponentTypesME.WEAPON_TYPE_DATA, new WeaponTypeDataComponent(WeaponTypesME.TROLL_WEAPON.name)));
    }
}
