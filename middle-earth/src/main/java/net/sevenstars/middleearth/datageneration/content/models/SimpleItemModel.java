package net.sevenstars.middleearth.datageneration.content.models;

import net.minecraft.item.Item;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.item.DecorativeItemsME;
import net.sevenstars.middleearth.item.FoodItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;

import java.util.ArrayList;
import java.util.List;

public class SimpleItemModel {
    public static List<Item> items = new ArrayList<>() {
        {
            add(ResourceItemsME.MIDDLE_EARTH_MAP);

            add(ResourceItemsME.EMPTY_PHIAL);
            add(ResourceItemsME.WATER_PHIAL);
            add(ResourceItemsME.STARLIGHT_PHIAL);

            add(DecorativeItemsME.TAPPER);
            add(DecorativeItemsME.TROLL_STATUE);

            add(FoodItemsME.BIRCH_WATER);
            add(FoodItemsME.MAPLE_SYRUP);

            //Crops
            add(FoodItemsME.TOMATO);
            add(FoodItemsME.BELL_PEPPER);
            add(FoodItemsME.CUCUMBER);
            add(FoodItemsME.GARLIC);
            add(FoodItemsME.LEEK);
            add(FoodItemsME.LETTUCE);
            add(FoodItemsME.ONION);
            add(ResourceItemsME.FLAX);

            //Pipeweed stuff
            add(ResourceItemsME.PIPEWEED);
            add(ResourceItemsME.DRIED_PIPEWEED);
            add(ResourceItemsME.PIPEWEED_SEEDS);

            add(ResourceItemsME.FLAX_SEEDS);
            add(ResourceItemsME.TOMATO_SEEDS);
            add(ResourceItemsME.BELL_PEPPER_SEEDS);
            add(ResourceItemsME.CUCUMBER_SEEDS);
            add(ResourceItemsME.LETTUCE_SEEDS);

            add(ResourceItemsME.STRAW);
            add(ResourceItemsME.REEDS.asItem());

            add(ResourceItemsME.PIPEWEED_BANNER_PATTERN);
            add(ResourceItemsME.GONDOR_BANNER_PATTERN);
            add(ResourceItemsME.ROHAN_BANNER_PATTERN);
            add(ResourceItemsME.LOTHLORIEN_BANNER_PATTERN);
            add(ResourceItemsME.MORDOR_BANNER_PATTERN);
            add(ResourceItemsME.MISTY_MOUNTAINS_ORCS_BANNER_PATTERN);
            add(ResourceItemsME.ISENGARD_BANNER_PATTERN);

            add(ResourceItemsME.ANVIL_BANNER_PATTERN);
            add(ResourceItemsME.BELL_BANNER_PATTERN);
            add(ResourceItemsME.BOW_BANNER_PATTERN);

            add(ResourceItemsME.DWARF_CROWN_BANNER_PATTERN);

            add(ResourceItemsME.SPIDER_BANNER_PATTERN);

            add(ResourceItemsME.DRAGON_BANNER_PATTERN);
            add(ResourceItemsME.SNAIL_BANNER_PATTERN);

            //Food Items
            add(FoodItemsME.LAYERED_CAKE);
            add(FoodItemsME.BERRY_PIE);
            add(FoodItemsME.BOILED_EGG);
            add(FoodItemsME.FISH_STEW);
            add(FoodItemsME.MEAT_BOWL);
            add(FoodItemsME.MEAT_EGG_MEAL);
            add(FoodItemsME.MEAT_SKEWER);
            add(FoodItemsME.COOKED_MEAT_SKEWER);
            add(FoodItemsME.POULTRY_MEAL);
            add(FoodItemsME.VEGETABLE_SKEWER);
            add(FoodItemsME.COOKED_VEGETABLE_SKEWER);
            add(FoodItemsME.VEGETABLE_SOUP);
            add(FoodItemsME.SACK_OF_HORSEFEED);
            add(FoodItemsME.RAW_HORSE);
            add(FoodItemsME.COOKED_HORSE);

            add(FoodItemsME.LEMBAS);
            add(FoodItemsME.CRAM);
            add(FoodItemsME.RAW_SWAN);
            add(FoodItemsME.COOKED_SWAN);
            add(FoodItemsME.MAGGOTY_BREAD);
            add(FoodItemsME.TOUGH_BERRIES);
            add(FoodItemsME.STRAWBERRIES);

            add(ResourceItemsME.GLOWWORM_BOTTLE);

            add(ResourceItemsME.COPPER_COIN);
            add(ResourceItemsME.SILVER_COIN);
            add(ResourceItemsME.GOLD_COIN);

            add(ResourceItemsME.FUR);
            add(ResourceItemsME.FABRIC);

            add(ResourceItemsME.DWARVEN_KEY);

            add(ResourceItemsME.RED_AGATE_SHARD);
            add(ResourceItemsME.CITRINE_SHARD);
            add(ResourceItemsME.QUARTZ_SHARD);

            add(ResourceItemsME.DIRTY_BONE);
            add(ResourceItemsME.FANG);
            add(ResourceItemsME.SPIDER_STINGER);

            add(ResourceItemsME.SWAN_FEATHER);

            add(ResourceItemsME.ASH);

            add(ResourceItemsME.RAW_TIN);
            add(ResourceItemsME.TIN_INGOT);
            add(ResourceItemsME.TIN_NUGGET);

            add(ResourceItemsME.RAW_LEAD);
            add(ResourceItemsME.LEAD_INGOT);
            add(ResourceItemsME.LEAD_NUGGET);

            add(ResourceItemsME.BRONZE_INGOT);
            add(ResourceItemsME.BRONZE_NUGGET);

            add(ResourceItemsME.RAW_SILVER);
            add(ResourceItemsME.SILVER_INGOT);
            add(ResourceItemsME.SILVER_NUGGET);

            add(ResourceItemsME.BURZUM_STEEL_INGOT);
            add(ResourceItemsME.BURZUM_STEEL_NUGGET);

            add(ResourceItemsME.CRUDE_INGOT);
            add(ResourceItemsME.CRUDE_NUGGET);

            add(ResourceItemsME.STEEL_INGOT);
            add(ResourceItemsME.STEEL_NUGGET);

            add(ResourceItemsME.EDHEL_STEEL_INGOT);
            add(ResourceItemsME.EDHEL_STEEL_NUGGET);

            add(ResourceItemsME.KHAZAD_STEEL_INGOT);
            add(ResourceItemsME.KHAZAD_STEEL_NUGGET);

            add(ResourceItemsME.RAW_MITHRIL);
            add(ResourceItemsME.MITHRIL_INGOT);
            add(ResourceItemsME.MITHRIL_NUGGET);

            add(ResourceItemsME.PEBBLE);
            add(ResourceItemsME.PINECONE);
            add(ResourceItemsME.LIT_PINECONE);

            add(DecorativeItemsME.BLUE_HOBBIT_DOOR.asItem());
            add(DecorativeItemsME.GREEN_HOBBIT_DOOR.asItem());
            add(DecorativeItemsME.LIGHT_BLUE_HOBBIT_DOOR.asItem());
            add(DecorativeItemsME.RED_HOBBIT_DOOR.asItem());
            add(DecorativeItemsME.YELLOW_HOBBIT_DOOR.asItem());
            add(DecorativeItemsME.LARCH_HOBBIT_DOOR.asItem());
            add(DecorativeItemsME.SPRUCE_HOBBIT_DOOR.asItem());
            add(DecorativeItemsME.TALL_BLACK_PINE_DOOR.asItem());
            add(DecorativeItemsME.OAK_STABLE_DOOR.asItem());
            add(DecorativeItemsME.REINFORCED_SPRUCE_DOOR.asItem());
            add(DecorativeItemsME.REINFORCED_BLACK_PINE_DOOR.asItem());
            add(DecorativeItemsME.SIMPLE_LARCH_GATE.asItem());
            add(DecorativeItemsME.RICKETY_SIMPLE_LARCH_DOOR.asItem());
            add(DecorativeItemsME.SPRUCE_STABLE_DOOR.asItem());
            add(DecorativeItemsME.LARGE_STURDY_DOOR.asItem());
            add(DecorativeItemsME.LARGE_BEECH_FENCE_GATE.asItem());
            add(DecorativeItemsME.GREAT_GONDORIAN_GATE.asItem());
            add(DecorativeItemsME.GREAT_DWARVEN_GATE.asItem());
            add(DecorativeItemsME.VARNISHED_DWARVEN_DOOR.asItem());
            add(DecorativeItemsME.RUINED_DWARVEN_DOOR.asItem());
            add(DecorativeItemsME.HIDDEN_DWARVEN_DOOR.asItem());
            add(DecorativeItemsME.GREAT_ELVEN_GATE.asItem());
            add(DecorativeItemsME.GREAT_ORCISH_GATE.asItem());

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
