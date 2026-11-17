package net.sevenstars.middleearth.features.cooking;

import java.util.function.Function;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.sevenstars.middleearth.common.registration.ItemRegistrar;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;

public class CookingItems {
    public static final Item LAYERED_CAKE = registerFoodItem("layered_cake",
            (settings) -> new BlockItem(CookingBlocks.LAYERED_CAKE, settings),new Item.Settings().maxCount(1));

    private static Item registerFoodItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return ItemRegistrar.register(name, factory, settings, ItemGroupsME.FOOD_CONTENTS);
    }

    public static void register() {
    }
}
