package net.sevenstars.middleearth.features.farming;

import java.util.function.Function;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.sevenstars.middleearth.common.registration.ItemRegistrar;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;

public class FarmingItems {
    public static final Item GARLIC = registerFoodItem("garlic",
            (settings) -> new BlockItem(FarmingBlocks.GARLIC_CROP, settings),new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.2f).build()));

    public static final Item LEEK = registerFoodItem("leek",
            (settings) -> new BlockItem(FarmingBlocks.LEEK_CROP, settings),new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.2f).build()));

    public static final Item ONION = registerFoodItem("onion",
            (settings) -> new BlockItem(FarmingBlocks.ONION_CROP, settings),new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.2f).build()));

    public static final Item FLAX_SEEDS = registerResourceItem("flax_seeds",
            (settings) -> new BlockItem(FarmingBlocks.FLAX_CROP, settings), new Item.Settings());

    public static final Item PIPEWEED_SEEDS = registerResourceItem("pipeweed_seeds",
            (settings) -> new BlockItem(FarmingBlocks.PIPEWEED_CROP, settings), new Item.Settings());

    public static final Item TOMATO_SEEDS = registerResourceItem("tomato_seeds",
            (settings) -> new BlockItem(FarmingBlocks.TOMATO_CROP, settings), new Item.Settings());

    public static final Item BELL_PEPPER_SEEDS = registerResourceItem("bell_pepper_seeds",
            (settings) -> new BlockItem(FarmingBlocks.BELL_PEPPER_CROP, settings), new Item.Settings());

    public static final Item CUCUMBER_SEEDS = registerResourceItem("cucumber_seeds",
            (settings) -> new BlockItem(FarmingBlocks.CUCUMBER_CROP, settings), new Item.Settings());

    public static final Item LETTUCE_SEEDS = registerResourceItem("lettuce_seeds",
            (settings) -> new BlockItem(FarmingBlocks.LETTUCE_CROP, settings), new Item.Settings());

    private static Item registerFoodItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return ItemRegistrar.register(name, factory, settings, ItemGroupsME.FOOD_CONTENTS);
    }

    private static Item registerResourceItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return ItemRegistrar.register(name, factory, settings, ItemGroupsME.RESOURCES_CONTENTS);
    }

    public static void register() {
    }
}
