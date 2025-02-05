package net.sevenstars.middleearth.datageneration.content.models;

import net.minecraft.item.Item;
import net.sevenstars.middleearth.item.ModDecorativeItems;
import net.sevenstars.middleearth.item.ModFoodItems;
import net.sevenstars.middleearth.item.ModResourceItems;

import java.util.ArrayList;
import java.util.List;

public class SimpleItemModel {
    public static List<Item> items = new ArrayList<>() {
        {
            add(ModResourceItems.MIDDLE_EARTH_MAP);

            add(ModResourceItems.EMPTY_PHIAL);
            add(ModResourceItems.WATER_PHIAL);
            add(ModResourceItems.STARLIGHT_PHIAL);

            add(ModDecorativeItems.TROLL_STATUE);


            //Crops
            add(ModFoodItems.TOMATO);
            add(ModFoodItems.BELL_PEPPER);
            add(ModFoodItems.CUCUMBER);
            add(ModFoodItems.LETTUCE);
            add(ModResourceItems.FLAX);

            //Pipeweed stuff
            add(ModResourceItems.PIPEWEED);
            add(ModResourceItems.DRIED_PIPEWEED);

            add(ModResourceItems.STRAW);


            //Food Items
            add(ModFoodItems.BERRY_PIE);
            add(ModFoodItems.BOILED_EGG);
            add(ModFoodItems.FISH_STEW);
            add(ModFoodItems.MEAT_BOWL);
            add(ModFoodItems.MEAT_EGG_MEAL);
            add(ModFoodItems.MEAT_SKEWER);
            add(ModFoodItems.COOKED_MEAT_SKEWER);
            add(ModFoodItems.POULTRY_MEAL);
            add(ModFoodItems.VEGETABLE_SKEWER);
            add(ModFoodItems.COOKED_VEGETABLE_SKEWER);
            add(ModFoodItems.VEGETABLE_SOUP);
            add(ModFoodItems.SACK_OF_HORSEFEED);
            add(ModFoodItems.RAW_VENISON);
            add(ModFoodItems.COOKED_VENISON);
            add(ModFoodItems.RAW_HORSE);
            add(ModFoodItems.COOKED_HORSE);

            add(ModResourceItems.GLOWWORM_BOTTLE);

            add(ModResourceItems.COPPER_COIN);
            add(ModResourceItems.SILVER_COIN);
            add(ModResourceItems.GOLD_COIN);

            add(ModResourceItems.FUR);
            add(ModResourceItems.FABRIC);

            add(ModResourceItems.DWARVEN_KEY);

            add(ModResourceItems.RED_AGATE_SHARD);
            add(ModResourceItems.CITRINE_SHARD);
            add(ModResourceItems.QUARTZ_SHARD);

            add(ModResourceItems.DIRTY_BONE);
            add(ModResourceItems.FANG);

            add(ModResourceItems.ASH);

            add(ModResourceItems.RAW_TIN);
            add(ModResourceItems.TIN_INGOT);
            add(ModResourceItems.TIN_NUGGET);

            add(ModResourceItems.RAW_LEAD);
            add(ModResourceItems.LEAD_INGOT);
            add(ModResourceItems.LEAD_NUGGET);

            add(ModResourceItems.BRONZE_INGOT);
            add(ModResourceItems.BRONZE_NUGGET);

            add(ModResourceItems.RAW_SILVER);
            add(ModResourceItems.SILVER_INGOT);
            add(ModResourceItems.SILVER_NUGGET);

            add(ModResourceItems.BURZUM_STEEL_INGOT);
            add(ModResourceItems.BURZUM_STEEL_NUGGET);

            add(ModResourceItems.CRUDE_INGOT);
            add(ModResourceItems.CRUDE_NUGGET);

            add(ModResourceItems.STEEL_INGOT);
            add(ModResourceItems.STEEL_NUGGET);

            add(ModResourceItems.EDHEL_STEEL_INGOT);
            add(ModResourceItems.EDHEL_STEEL_NUGGET);

            add(ModResourceItems.KHAZAD_STEEL_INGOT);
            add(ModResourceItems.KHAZAD_STEEL_NUGGET);

            add(ModResourceItems.RAW_MITHRIL);
            add(ModResourceItems.MITHRIL_INGOT);
            add(ModResourceItems.MITHRIL_NUGGET);

            add(ModResourceItems.PINECONE);
            add(ModResourceItems.LIT_PINECONE);
        }
    };
}
