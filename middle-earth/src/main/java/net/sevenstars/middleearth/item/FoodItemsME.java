package net.sevenstars.middleearth.item;

import net.minecraft.block.Block;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.consume.RemoveEffectsConsumeEffect;
import net.minecraft.sound.SoundEvents;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.item.items.OrcishFoodItem;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.registries.RegistryAliasesME;

import java.util.function.Function;

/**
 * Middle-earth mod Food Items registry
 */
public class FoodItemsME {
    public static final ConsumableComponent BIRCH_BOTTLE = ConsumableComponents.drink().consumeSeconds(2.0F)
            .consumeEffect(new RemoveEffectsConsumeEffect(StatusEffects.POISON)).build();;
    public static final ConsumableComponent MAPLE_BOTTLE = ConsumableComponents.drink().consumeSeconds(2.0F)
            .sound(SoundEvents.ITEM_HONEY_BOTTLE_DRINK).build();;

    public static final Item LEMBAS = registerItem("lembas",
            Item::new,new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(20).saturationModifier(1).build()));
    public static final Item CRAM = registerItem("cram",
            Item::new,new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.2F).build()));

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
    public static final Item STRAWBERRIES = registerItem("strawberries",
            (settings) -> new BlockItem(ModNatureBlocks.STRAWBERRY_BUSH, settings), new Item.Settings()
                    .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.1f).build()));

    public static final Item MAPLE_SYRUP = registerItem("maple_syrup",
            Item::new,new Item.Settings()
                    .recipeRemainder(Items.GLASS_BOTTLE).useRemainder(Items.GLASS_BOTTLE)
                    .food(FoodComponents.HONEY_BOTTLE, MAPLE_BOTTLE).maxCount(16));
    public static final Item BIRCH_WATER = registerItem("birch_water",
            Item::new,new Item.Settings()
                    .recipeRemainder(Items.GLASS_BOTTLE).useRemainder(Items.GLASS_BOTTLE)
                    .food(FoodComponents.HONEY_BOTTLE, BIRCH_BOTTLE).maxCount(16));

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

    public static final Item LAYERED_CAKE = registerItem("layered_cake",
            (settings) -> new BlockItem(ModBlocks.LAYERED_CAKE, settings),new Item.Settings().maxCount(1));
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
                    new FoodComponent.Builder().nutrition(5).saturationModifier(0.6f).build()).maxCount(8).useRemainder(Items.STICK));
    public static final Item COOKED_MEAT_SKEWER = registerItem("cooked_meat_skewer",
            Item::new,new Item.Settings().food(
                    new FoodComponent.Builder().nutrition(10).saturationModifier(0.8f).build()).maxCount(8).useRemainder(Items.STICK));
    public static final Item POULTRY_MEAL = registerItem("poultry_meal",
            Item::new,new Item.Settings().food(
                    new FoodComponent.Builder().nutrition(8).saturationModifier(0.8f).build()).maxCount(8).useRemainder(Items.BOWL));
    public static final Item VEGETABLE_SKEWER = registerItem("vegetable_skewer",
            Item::new,new Item.Settings().food(
                    new FoodComponent.Builder().nutrition(3).saturationModifier(0.3f).build()).maxCount(8).useRemainder(Items.STICK));
    public static final Item COOKED_VEGETABLE_SKEWER = registerItem("cooked_vegetable_skewer",
            Item::new,new Item.Settings().food(
                    new FoodComponent.Builder().nutrition(6).saturationModifier(0.6f).build()).maxCount(8).useRemainder(Items.STICK));
    public static final Item VEGETABLE_SOUP = registerItem("vegetable_soup",
            Item::new,new Item.Settings().food(
                    new FoodComponent.Builder().nutrition(7).saturationModifier(0.6f).build()).maxCount(1).useRemainder(Items.BOWL));

    public static final Item SACK_OF_HORSEFEED = registerItem("sack_of_horsefeed",
            Item::new,new Item.Settings().food(
                    new FoodComponent.Builder().nutrition(7).saturationModifier(0.4f).build()));


    private static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ItemGroupsME.FOOD_CONTENTS.add(item.getDefaultStack());
        TranslationEntries.itemEntries.add(item);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(Registries.ITEM, name));
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Food Items for " + MiddleEarth.MOD_ID);
    }
}
