package net.sevenstars.middleearth.item;

import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.item.BlockItem;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.ModBlocks;
import net.sevenstars.middleearth.block.ModNatureBlocks;
import net.sevenstars.middleearth.item.items.OrcishFoodItem;
import net.sevenstars.middleearth.item.utils.ModItemGroups;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModFoodItems {

    public static final Item LEMBAS = registerItem("lembas",
            Item::new,new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(20).saturationModifier(1).build()));

    public static final Item RAW_SWAN = registerItem("raw_swan",
            Item::new,new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(3).saturationModifier(0.4F).build(), ConsumableComponents.RAW_CHICKEN));
    public static final Item COOKED_SWAN = registerItem("cooked_swan",
            Item::new,new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(8).saturationModifier(0.8F).build()));

    public static final Item RAW_VENISON = registerItem("raw_venison",
            Item::new,new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.4F).build()));
    public static final Item COOKED_VENISON = registerItem("cooked_venison",
            Item::new,new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.8F).build()));

    public static final Item RAW_HORSE = registerItem("raw_horse",
            Item::new,new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(3).saturationModifier(0.4F).build()));
    public static final Item COOKED_HORSE = registerItem("cooked_horse",
            Item::new,new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(8).saturationModifier(0.8F).build()));

    public static final Item MAGGOTY_BREAD = registerItem("maggoty_bread",
            OrcishFoodItem::new,new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(5).saturationModifier(1.2f).build()));
    public static final Item TOUGH_BERRIES = registerItem("tough_berries",
            (settings) -> new BlockItem(ModNatureBlocks.TOUGH_BERRY_BUSH, settings), new Item.Settings());
    public static final Item STRAWBERRY = registerItem("strawberry",
            (settings) -> new BlockItem(ModNatureBlocks.STRAWBERRY_BUSH, settings), new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.1f).build()));

    public static final Item TOMATO = registerItem("tomato",
            Item::new,new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(4).saturationModifier(0.3f).build()));
    public static final Item BELL_PEPPER = registerItem("bell_pepper",
            Item::new,new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(4).saturationModifier(0.3f).build()));
    public static final Item CUCUMBER = registerItem("cucumber",
            Item::new,new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(4).saturationModifier(0.3f).build()));
    public static final Item GARLIC = registerItem("garlic",
            (settings) -> new BlockItem(ModNatureBlocks.GARLIC_CROP, settings),new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.2f).build()));
    public static final Item LEEK = registerItem("leek",
            (settings) -> new BlockItem(ModNatureBlocks.LEEK_CROP, settings),new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.2f).build()));
    public static final Item LETTUCE = registerItem("lettuce",
            Item::new,new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(3).saturationModifier(0.3f).build()));
    public static final Item ONION = registerItem("onion",
            (settings) -> new BlockItem(ModNatureBlocks.ONION_CROP, settings),new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.2f).build()));

    public static final Item BERRY_PIE = registerItem("berry_pie",
            Item::new,new Item.Settings().food(
                    new FoodComponent.Builder().nutrition(8).saturationModifier(0.5f).build()));
    public static final Item BOILED_EGG = registerItem("boiled_egg",
            Item::new,new Item.Settings().food(
                    new FoodComponent.Builder().nutrition(3).saturationModifier(0.6f).build()));
    public static final Item FISH_STEW = registerItem("fish_stew",
            Item::new,new Item.Settings().food(
                    new FoodComponent.Builder().nutrition(8).saturationModifier(1.0f).build()).maxCount(1).useRemainder(Items.BOWL));
    public static final Item MEAT_BOWL = registerItem("meat_bowl",
            Item::new,new Item.Settings().food(
                    new FoodComponent.Builder().nutrition(12).saturationModifier(1.0f).build()).maxCount(1).useRemainder(Items.BOWL));
    public static final Item MEAT_EGG_MEAL = registerItem("meat_egg_meal",
            Item::new,new Item.Settings().food(
                    new FoodComponent.Builder().nutrition(9).saturationModifier(0.9f).build()).maxCount(1).useRemainder(Items.BOWL));
    public static final Item MEAT_SKEWER = registerItem("meat_skewer",
            Item::new,new Item.Settings().food(
                    new FoodComponent.Builder().nutrition(5).saturationModifier(0.6f).build()).maxCount(8).useRemainder(Items.BOWL));
    public static final Item COOKED_MEAT_SKEWER = registerItem("cooked_meat_skewer",
            Item::new,new Item.Settings().food(
                    new FoodComponent.Builder().nutrition(10).saturationModifier(0.8f).build()).maxCount(8).useRemainder(Items.BOWL));
    public static final Item POULTRY_MEAL = registerItem("poultry_meal",
            Item::new,new Item.Settings().food(
                    new FoodComponent.Builder().nutrition(8).saturationModifier(0.8f).build()).maxCount(8).useRemainder(Items.BOWL));
    public static final Item VEGETABLE_SKEWER = registerItem("vegetable_skewer",
            Item::new,new Item.Settings().food(
                    new FoodComponent.Builder().nutrition(3).saturationModifier(0.3f).build()).maxCount(8).useRemainder(Items.BOWL));
    public static final Item COOKED_VEGETABLE_SKEWER = registerItem("cooked_vegetable_skewer",
            Item::new,new Item.Settings().food(
                    new FoodComponent.Builder().nutrition(6).saturationModifier(0.6f).build()).maxCount(8).useRemainder(Items.BOWL));
    public static final Item VEGETABLE_SOUP = registerItem("vegetable_soup",
            Item::new,new Item.Settings().food(
                    new FoodComponent.Builder().nutrition(7).saturationModifier(0.6f).build()).maxCount(1).useRemainder(Items.BOWL));

    public static final Item SACK_OF_HORSEFEED = registerItem("sack_of_horsefeed",
            Item::new,new Item.Settings().food(
                    new FoodComponent.Builder().nutrition(7).saturationModifier(0.4f).build()));


    private static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ModItemGroups.FOOD_CONTENTS.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Food Items for " + MiddleEarth.MOD_ID);
    }
}
