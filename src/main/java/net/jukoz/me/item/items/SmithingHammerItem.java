package net.jukoz.me.item.items;

import net.minecraft.item.Item;

public class SmithingHammerItem extends Item {
    public SmithingHammerItem(Settings settings) {
        super(settings.maxCount(1).maxDamage(64));
    }
}
