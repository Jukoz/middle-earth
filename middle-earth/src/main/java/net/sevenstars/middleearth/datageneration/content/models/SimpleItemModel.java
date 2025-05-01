package net.sevenstars.middleearth.datageneration.content.models;

import net.minecraft.client.data.Models;
import net.minecraft.item.Item;
import net.sevenstars.middleearth.block.ModBlocks;
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

            add(ModFoodItems.BIRCH_WATER);
            add(ModFoodItems.MAPLE_SYRUP);

            //Crops
            add(ModFoodItems.TOMATO);
            add(ModFoodItems.BELL_PEPPER);
            add(ModFoodItems.CUCUMBER);
            add(ModFoodItems.GARLIC);
            add(ModFoodItems.LEEK);
            add(ModFoodItems.LETTUCE);
            add(ModFoodItems.ONION);
            add(ModResourceItems.FLAX);

            //Pipeweed stuff
            add(ModResourceItems.PIPEWEED);
            add(ModResourceItems.DRIED_PIPEWEED);
            add(ModResourceItems.PIPEWEED_SEEDS);

            add(ModResourceItems.FLAX_SEEDS);
            add(ModResourceItems.TOMATO_SEEDS);
            add(ModResourceItems.BELL_PEPPER_SEEDS);
            add(ModResourceItems.CUCUMBER_SEEDS);
            add(ModResourceItems.LETTUCE_SEEDS);

            add(ModResourceItems.STRAW);
            add(ModResourceItems.REEDS.asItem());

            add(ModResourceItems.PIPEWEED_BANNER_PATTERN);
            add(ModResourceItems.GONDOR_BANNER_PATTERN);
            add(ModResourceItems.ROHAN_BANNER_PATTERN);
            add(ModResourceItems.LOTHLORIEN_BANNER_PATTERN);
            add(ModResourceItems.MORDOR_BANNER_PATTERN);
            add(ModResourceItems.MISTY_MOUNTAINS_ORCS_BANNER_PATTERN);
            add(ModResourceItems.ISENGARD_BANNER_PATTERN);

            add(ModResourceItems.ANVIL_BANNER_PATTERN);
            add(ModResourceItems.BELL_BANNER_PATTERN);
            add(ModResourceItems.BOW_BANNER_PATTERN);

            add(ModResourceItems.DWARF_CROWN_BANNER_PATTERN);

            add(ModResourceItems.DRAGON_BANNER_PATTERN);
            add(ModResourceItems.SNAIL_BANNER_PATTERN);

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

            add(ModFoodItems.LEMBAS);
            add(ModFoodItems.RAW_SWAN);
            add(ModFoodItems.COOKED_SWAN);
            add(ModFoodItems.MAGGOTY_BREAD);
            add(ModFoodItems.TOUGH_BERRIES);
            add(ModFoodItems.STRAWBERRY);

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

            add(ModResourceItems.SWAN_FEATHER);

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

            add(ModResourceItems.PEBBLE);
            add(ModResourceItems.PINECONE);
            add(ModResourceItems.LIT_PINECONE);

            add(ModDecorativeItems.BLUE_HOBBIT_DOOR.asItem());
            add(ModDecorativeItems.GREEN_HOBBIT_DOOR.asItem());
            add(ModDecorativeItems.LIGHT_BLUE_HOBBIT_DOOR.asItem());
            add(ModDecorativeItems.RED_HOBBIT_DOOR.asItem());
            add(ModDecorativeItems.YELLOW_HOBBIT_DOOR.asItem());
            add(ModDecorativeItems.LARCH_HOBBIT_DOOR.asItem());
            add(ModDecorativeItems.SPRUCE_HOBBIT_DOOR.asItem());
            add(ModDecorativeItems.TALL_BLACK_PINE_DOOR.asItem());
            add(ModDecorativeItems.OAK_STABLE_DOOR.asItem());
            add(ModDecorativeItems.REINFORCED_SPRUCE_DOOR.asItem());
            add(ModDecorativeItems.REINFORCED_BLACK_PINE_DOOR.asItem());
            add(ModDecorativeItems.SIMPLE_LARCH_GATE.asItem());
            add(ModDecorativeItems.RICKETY_SIMPLE_LARCH_DOOR.asItem());
            add(ModDecorativeItems.SPRUCE_STABLE_DOOR.asItem());
            add(ModDecorativeItems.LARGE_STURDY_DOOR.asItem());
            add(ModDecorativeItems.GREAT_GONDORIAN_GATE.asItem());
            add(ModDecorativeItems.GREAT_DWARVEN_GATE.asItem());
            add(ModDecorativeItems.VARNISHED_DWARVEN_DOOR.asItem());
            add(ModDecorativeItems.RUINED_DWARVEN_DOOR.asItem());
            add(ModDecorativeItems.HIDDEN_DWARVEN_DOOR.asItem());
            add(ModDecorativeItems.GREAT_ELVEN_GATE.asItem());
            add(ModDecorativeItems.GREAT_ORCISH_GATE.asItem());

            add(ModBlocks.QUARTZ_CLUSTER.asItem());
            add(ModBlocks.SMALL_QUARTZ_BUD.asItem());
            add(ModBlocks.MEDIUM_QUARTZ_BUD.asItem());
            add(ModBlocks.LARGE_QUARTZ_BUD.asItem());
            add(ModBlocks.RED_AGATE_CLUSTER.asItem());
            add(ModBlocks.SMALL_RED_AGATE_BUD.asItem());
            add(ModBlocks.MEDIUM_RED_AGATE_BUD.asItem());
            add(ModBlocks.LARGE_RED_AGATE_BUD.asItem());
            add(ModBlocks.CITRINE_CLUSTER.asItem());
            add(ModBlocks.SMALL_CITRINE_BUD.asItem());
            add(ModBlocks.MEDIUM_CITRINE_BUD.asItem());
            add(ModBlocks.LARGE_CITRINE_BUD.asItem());
            add(ModBlocks.GLOWSTONE_CLUSTER.asItem());
            add(ModBlocks.SMALL_GLOWSTONE_BUD.asItem());
            add(ModBlocks.MEDIUM_GLOWSTONE_BUD.asItem());
            add(ModBlocks.LARGE_GLOWSTONE_BUD.asItem());
        }
    };
}
