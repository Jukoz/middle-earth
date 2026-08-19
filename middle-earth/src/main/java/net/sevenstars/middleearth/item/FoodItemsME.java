package net.sevenstars.middleearth.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.api.registries.RegistrationBridge;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.item.items.BottleDrinkItem;
import net.sevenstars.middleearth.item.items.OrcishFoodItem;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;
import net.sevenstars.middleearth.registries.RegistryAliasesME;

import java.util.function.Function;

/**
 * Middle-earth mod Food Items registry
 */
public class FoodItemsME {
    public static final Item LEMBAS = registerItem("lembas",
            Item::new,new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(20).saturationModifier(1).build()));
    public static final Item CRAM = registerItem("cram",
            Item::new,new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.2F).build()));

    public static final Item RAW_HORSE = registerItem("raw_horse",
            Item::new,new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.4F).build()));
    public static final Item COOKED_HORSE = registerItem("cooked_horse",
            Item::new,new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.8F).build()));

    public static final Item MAGGOTY_BREAD = registerItem("maggoty_bread",
            OrcishFoodItem::new,new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(5).saturationModifier(1.2f).build()));
    public static final Item TOUGH_BERRIES = registerItem("tough_berries",
            (settings) -> new BlockItem(ModNatureBlocks.TOUGH_BERRY_BUSH, settings), new Item.Properties());
    public static final Item STRAWBERRIES = registerItem("strawberries",
            (settings) -> new BlockItem(ModNatureBlocks.STRAWBERRY_BUSH, settings), new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build()));

    public static final Item MAPLE_SYRUP = registerItem("maple_syrup",
            (settings) -> new BottleDrinkItem(settings, false), new Item.Properties()
                    .craftRemainder(Items.GLASS_BOTTLE)
                    .food(Foods.HONEY_BOTTLE).stacksTo(16));
    public static final Item BIRCH_WATER = registerItem("birch_water",
            (settings) -> new BottleDrinkItem(settings, true), new Item.Properties()
                    .craftRemainder(Items.GLASS_BOTTLE)
                    .food(Foods.HONEY_BOTTLE).stacksTo(16));

    public static final Item TOMATO = registerItem("tomato",
            Item::new,new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.3f).build()));
    public static final Item BELL_PEPPER = registerItem("bell_pepper",
            Item::new,new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.3f).build()));
    public static final Item CUCUMBER = registerItem("cucumber",
            Item::new,new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.3f).build()));
    public static final Item GARLIC = registerItem("garlic",
            (settings) -> new BlockItem(ModNatureBlocks.GARLIC_CROP, settings),new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build()));
    public static final Item LEEK = registerItem("leek",
            (settings) -> new BlockItem(ModNatureBlocks.LEEK_CROP, settings),new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build()));
    public static final Item LETTUCE = registerItem("lettuce",
            Item::new,new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build()));
    public static final Item ONION = registerItem("onion",
            (settings) -> new BlockItem(ModNatureBlocks.ONION_CROP, settings),new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build()));

    public static final Item LAYERED_CAKE = registerItem("layered_cake",
            (settings) -> new BlockItem(ModBlocks.LAYERED_CAKE, settings),new Item.Properties().stacksTo(1));
    public static final Item BERRY_PIE = registerItem("berry_pie",
            Item::new,new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(8).saturationModifier(0.5f).build()));
    public static final Item BOILED_EGG = registerItem("boiled_egg",
            Item::new,new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(3).saturationModifier(0.6f).build()));
    public static final Item FISH_STEW = registerItem("fish_stew",
            Item::new,new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(8).saturationModifier(1.0f).usingConvertsTo(Items.BOWL).build()).stacksTo(1));
    public static final Item MEAT_BOWL = registerItem("meat_bowl",
            Item::new,new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(12).saturationModifier(1.0f).usingConvertsTo(Items.BOWL).build()).stacksTo(1));
    public static final Item MEAT_EGG_MEAL = registerItem("meat_egg_meal",
            Item::new,new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(9).saturationModifier(0.9f).usingConvertsTo(Items.BOWL).build()).stacksTo(1));
    public static final Item MEAT_SKEWER = registerItem("meat_skewer",
            Item::new,new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(5).saturationModifier(0.6f).usingConvertsTo(Items.STICK).build()).stacksTo(8));
    public static final Item COOKED_MEAT_SKEWER = registerItem("cooked_meat_skewer",
            Item::new,new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(10).saturationModifier(0.8f).usingConvertsTo(Items.STICK).build()).stacksTo(8));
    public static final Item POULTRY_MEAL = registerItem("poultry_meal",
            Item::new,new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(8).saturationModifier(0.8f).usingConvertsTo(Items.BOWL).build()).stacksTo(8));
    public static final Item VEGETABLE_SKEWER = registerItem("vegetable_skewer",
            Item::new,new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).usingConvertsTo(Items.STICK).build()).stacksTo(8));
    public static final Item COOKED_VEGETABLE_SKEWER = registerItem("cooked_vegetable_skewer",
            Item::new,new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(6).saturationModifier(0.6f).usingConvertsTo(Items.STICK).build()).stacksTo(8));
    public static final Item VEGETABLE_SOUP = registerItem("vegetable_soup",
            Item::new,new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(7).saturationModifier(0.6f).usingConvertsTo(Items.BOWL).build()).stacksTo(1));

    public static final Item SACK_OF_HORSEFEED = registerItem("sack_of_horsefeed",
            Item::new,new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(7).saturationModifier(0.4f).build()));


    private static Item registerItem(String name, Function<Item.Properties, Item> factory, Item.Properties settings) {
        Item item = factory.apply(settings);
        ItemGroupsME.FOOD_CONTENTS.add(item.getDefaultInstance());
        TranslationEntries.itemEntries.add(item);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(BuiltInRegistries.ITEM, name));
        return RegistrationBridge.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Food Items for " + MiddleEarth.MOD_ID);
    }
}
