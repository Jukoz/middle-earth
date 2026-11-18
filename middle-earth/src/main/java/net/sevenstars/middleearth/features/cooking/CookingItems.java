package net.sevenstars.middleearth.features.cooking;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.sevenstars.middleearth.common.registration.ItemRegistrar;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;

public class CookingItems {
    private static final ItemRegistrar FOOD = ItemRegistrar.forGroup(ItemGroupsME.FOOD_CONTENTS);

    public static final Item LAYERED_CAKE = FOOD.register("layered_cake",
            (settings) -> new BlockItem(CookingBlocks.LAYERED_CAKE, settings),new Item.Settings().maxCount(1));
}
