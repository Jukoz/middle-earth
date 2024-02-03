package net.jukoz.me.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.datageneration.content.models.SimpleItemModel;
import net.jukoz.me.item.utils.ModItemGroups;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModFoodItems {

    public static final Item LEMBAS = registerItem("lembas",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(20).saturationModifier(20).build())));

    public static final Item RAW_CRAB_CLAW = registerItem("raw_crab_claw",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(2).saturationModifier(0.2F).meat().build())));
    public static final Item COOKED_CRAB_CLAW = registerItem("cooked_crab_claw",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(5).saturationModifier(0.6F).meat().build())));

    public static final Item RAW_GOOSE = registerItem("raw_goose",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).meat().build())));
    public static final Item COOKED_GOOSE = registerItem("cooked_goose",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(7).saturationModifier(0.8F).meat().build())));

    public static final Item RAW_DUCK = registerItem("raw_duck",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(2).saturationModifier(0.3F).meat().build())));
    public static final Item COOKED_DUCK = registerItem("cooked_duck",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(6).saturationModifier(0.7F).meat().build())));

    public static final Item RAW_SWAN = registerItem("raw_swan",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(3).saturationModifier(0.4F).meat().build())));
    public static final Item COOKED_SWAN = registerItem("cooked_swan",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(8).saturationModifier(0.8F).meat().build())));

    public static final Item RAW_HORSE = registerItem("raw_horse",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(4).saturationModifier(0.4F).meat().build())));
    public static final Item COOKED_HORSE = registerItem("cooked_horse",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(9).saturationModifier(1.0F).meat().build())));

    public static final Item MAGGOTY_BREAD = registerItem("maggoty_bread",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(5).saturationModifier(1.2f).build())));
    public static final Item TOUGH_BERRIES = registerItem("tough_berries",
            new AliasedBlockItem(ModNatureBlocks.TOUGH_BERRY_BUSH, new Item.Settings()));
    public static final Item STRAWBERRY = registerItem("strawberry",
            new AliasedBlockItem(ModNatureBlocks.STRAWBERRY_BUSH,
                    new FabricItemSettings().food(
                            new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).build())));

    public static final Item TOMATO = registerItem("tomato",
            new Item(new FabricItemSettings().food(
                            new FoodComponent.Builder().hunger(4).saturationModifier(0.4f).build())));
    public static final Item BELL_PEPPER = registerItem("bell_pepper",
            new Item(new FabricItemSettings().food(
                            new FoodComponent.Builder().hunger(4).saturationModifier(0.5f).build())));
    public static final Item CUCUMBER = registerItem("cucumber",
            new Item(new FabricItemSettings().food(
                            new FoodComponent.Builder().hunger(4).saturationModifier(0.5f).build())));
    public static final Item GARLIC = registerItem("garlic",
            new AliasedBlockItem(ModNatureBlocks.GARLIC_CROP,
                    new FabricItemSettings().food(
                            new FoodComponent.Builder().hunger(2).saturationModifier(0.2f).build())));
    public static final Item LEEK = registerItem("leek",
            new AliasedBlockItem(ModNatureBlocks.LEEK_CROP,
                    new FabricItemSettings().food(
                            new FoodComponent.Builder().hunger(2).saturationModifier(0.2f).build())));
    public static final Item LETTUCE = registerItem("lettuce",
            new Item(new FabricItemSettings().food(
                            new FoodComponent.Builder().hunger(3).saturationModifier(0.3f).build())));
    public static final Item ONION = registerItem("onion",
            new AliasedBlockItem(ModNatureBlocks.ONION_CROP,
                    new FabricItemSettings().food(
                            new FoodComponent.Builder().hunger(2).saturationModifier(0.2f).build())));

    public static final Item BERRY_PIE = registerItem("berry_pie",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(4).saturationModifier(0.4f).build())));
    public static final Item BOILED_EGG = registerItem("boiled_egg",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(4).saturationModifier(0.4f).build())));
    public static final Item CRAB_SOUP = registerItem("crab_soup",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(4).saturationModifier(0.4f).build())));
    public static final Item FISH_STEW = registerItem("fish_stew",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(4).saturationModifier(0.4f).build())));
    public static final Item MEAT_BOWL = registerItem("meat_bowl",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(4).saturationModifier(0.4f).build())));
    public static final Item MEAT_EGG_MEAL = registerItem("meat_egg_meal",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(4).saturationModifier(0.4f).build())));
    public static final Item MEAT_SKEWER = registerItem("meat_skewer",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(4).saturationModifier(0.4f).build())));
    public static final Item COOKED_MEAT_SKEWER = registerItem("cooked_meat_skewer",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(4).saturationModifier(0.4f).build())));
    public static final Item POULTRY_MEAL = registerItem("poultry_meal",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(4).saturationModifier(0.4f).build())));
    public static final Item RAT_SKEWER = registerItem("rat_skewer",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(4).saturationModifier(0.4f).build())));
    public static final Item COOKED_RAT_SKEWER = registerItem("cooked_rat_skewer",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(4).saturationModifier(0.4f).build())));
    public static final Item VEGETABLE_SKEWER = registerItem("vegetable_skewer",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(4).saturationModifier(0.4f).build())));
    public static final Item COOKED_VEGETABLE_SKEWER = registerItem("cooked_vegetable_skewer",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(4).saturationModifier(0.4f).build())));
    public static final Item VEGETABLE_SOUP = registerItem("vegetable_soup",
            new Item(new FabricItemSettings().food(
                    new FoodComponent.Builder().hunger(4).saturationModifier(0.4f).build())));

    private static Item registerItem(String name, Item item) {
        ModItemGroups.FOOD_CONTENTS.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.debug("Registering Mod Food Items for " + MiddleEarth.MOD_ID);
    }
}
