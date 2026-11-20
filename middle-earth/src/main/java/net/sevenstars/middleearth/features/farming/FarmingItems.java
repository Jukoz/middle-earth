package net.sevenstars.middleearth.features.farming;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.sevenstars.middleearth.common.registration.ItemRegistrar;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;

public class FarmingItems {
    private static final ItemRegistrar FOOD = ItemRegistrar.forGroup(ItemGroupsME.FOOD_CONTENTS);
    private static final ItemRegistrar RESOURCES = ItemRegistrar.forGroup(ItemGroupsME.RESOURCES_CONTENTS);

    public static final Item GARLIC = FOOD.register("garlic",
            (settings) -> new BlockItem(FarmingBlocks.GARLIC_CROP, settings),new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.2f).build()));

    public static final Item LEEK = FOOD.register("leek",
            (settings) -> new BlockItem(FarmingBlocks.LEEK_CROP, settings),new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.2f).build()));

    public static final Item ONION = FOOD.register("onion",
            (settings) -> new BlockItem(FarmingBlocks.ONION_CROP, settings),new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.2f).build()));

    public static final Item FLAX_SEEDS = RESOURCES.register("flax_seeds",
            (settings) -> new BlockItem(FarmingBlocks.FLAX_CROP, settings), new Item.Settings());

    public static final Item PIPEWEED_SEEDS = RESOURCES.register("pipeweed_seeds",
            (settings) -> new BlockItem(FarmingBlocks.PIPEWEED_CROP, settings), new Item.Settings());

    public static final Item TOMATO_SEEDS = RESOURCES.register("tomato_seeds",
            (settings) -> new BlockItem(FarmingBlocks.TOMATO_CROP, settings), new Item.Settings());

    public static final Item BELL_PEPPER_SEEDS = RESOURCES.register("bell_pepper_seeds",
            (settings) -> new BlockItem(FarmingBlocks.BELL_PEPPER_CROP, settings), new Item.Settings());

    public static final Item CUCUMBER_SEEDS = RESOURCES.register("cucumber_seeds",
            (settings) -> new BlockItem(FarmingBlocks.CUCUMBER_CROP, settings), new Item.Settings());

    public static final Item LETTUCE_SEEDS = RESOURCES.register("lettuce_seeds",
            (settings) -> new BlockItem(FarmingBlocks.LETTUCE_CROP, settings), new Item.Settings());
}
