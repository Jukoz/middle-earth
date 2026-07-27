package net.sevenstars.middleearth.item.items.shields;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShieldItem;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.WeaponTypeDataComponent;
import net.sevenstars.middleearth.item.utils.ShieldTypesME;
import java.util.HashSet;

public class CustomShieldItem extends ShieldItem {
    public final ShieldTypesME type;
    public final static HashSet<CustomShieldItem> instances = new HashSet<>();

    public CustomShieldItem(ShieldTypesME type, Item.Properties settings) {
        super(settings.stacksTo(1).durability(type.durability)
                .component(DataComponentTypesME.WEAPON_TYPE_DATA, new WeaponTypeDataComponent(type.name)));
        this.type = type;
        instances.add(this);
    }
}
