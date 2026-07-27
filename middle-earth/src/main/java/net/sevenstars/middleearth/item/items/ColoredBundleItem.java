package net.sevenstars.middleearth.item.items;

import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.DyeColor;

public final class ColoredBundleItem extends BundleItem {
    private final DyeColor color;

    public ColoredBundleItem(DyeColor color, Properties properties) {
        super(properties);
        this.color = color;
    }

    public DyeColor color() {
        return color;
    }
}
