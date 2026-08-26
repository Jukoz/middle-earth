package net.sevenstars.middleearth.datageneration.content.models;

import net.minecraft.item.Item;
import net.sevenstars.middleearth.block.registration.BlockRegistryME;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
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
            add(ResourceItemsME.PLAYER_BOOK);

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

            add(ResourceItemsME.PIPEWEED_BANNER_PATTERN);
            add(ResourceItemsME.GONDOR_BANNER_PATTERN);
            add(ResourceItemsME.ROHAN_BANNER_PATTERN);
            add(ResourceItemsME.LOTHLORIEN_BANNER_PATTERN);
            add(ResourceItemsME.MORDOR_BANNER_PATTERN);
            add(ResourceItemsME.MISTY_MOUNTAINS_ORCS_BANNER_PATTERN);
            add(ResourceItemsME.ISENGARD_BANNER_PATTERN);
            add(ResourceItemsME.SCREECHING_SKULL_BANNER_PATTERN);
            add(ResourceItemsME.GOBLIN_SKULL_BANNER_PATTERN);

            add(ResourceItemsME.ANVIL_BANNER_PATTERN);
            add(ResourceItemsME.BELL_BANNER_PATTERN);
            add(ResourceItemsME.BOW_BANNER_PATTERN);

            add(ResourceItemsME.DWARF_CROWN_BANNER_PATTERN);

            add(ResourceItemsME.SPIDER_BANNER_PATTERN);

            add(ResourceItemsME.GREAT_HORN_BANNER_PATTERN);
            add(ResourceItemsME.OAK_LEAF_BANNER_PATTERN);

            add(ResourceItemsME.ANTLERS_BANNER_PATTERN);
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
            add(FoodItemsME.MAGGOTY_BREAD);
            add(FoodItemsME.TOUGH_BERRIES);
            add(FoodItemsME.STRAWBERRIES);

            add(ResourceItemsME.GLOWWORM_BOTTLE);

            add(ResourceItemsME.COPPER_COIN);
            add(ResourceItemsME.SILVER_COIN);
            add(ResourceItemsME.GOLD_COIN);

            add(ResourceItemsME.FUR);
            add(ResourceItemsME.FABRIC);

            add(ResourceItemsME.BRIGAND_KEY);
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
            add(ResourceItemsME.RAW_LEAD);
            add(ResourceItemsME.RAW_SILVER);
            add(ResourceItemsME.RAW_MITHRIL);

            add(ResourceItemsME.ADAMANT);
            add(ResourceItemsME.RUBY);
            add(ResourceItemsME.SAPPHIRE);

            add(ResourceItemsME.PEBBLE);
            add(ResourceItemsME.PINECONE);
            add(ResourceItemsME.LIT_PINECONE);

            add(DecorativeBlockRegistryME.CANDLESTICK.asItem());
            add(DecorativeBlockRegistryME.CERAMIC_LAMP.asItem());
            add(DecorativeBlockRegistryME.SMALL_BRONZE_CHANDELIER.asItem());
            add(DecorativeBlockRegistryME.BRONZE_CHANDELIER.asItem());
            add(DecorativeBlockRegistryME.SMALL_CHANDELIER.asItem());
            add(DecorativeBlockRegistryME.CHANDELIER.asItem());

            add(DecorativeBlockRegistryME.SMALL_BLACK_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.SMALL_BLUE_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.SMALL_BROWN_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.SMALL_BURNT_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.SMALL_DARK_BLUE_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.SMALL_DARK_BROWN_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.SMALL_DARK_GREEN_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.SMALL_DARK_RED_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.SMALL_FANCY_BLUE_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.SMALL_FANCY_GREEN_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.SMALL_FANCY_RED_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.SMALL_GRAY_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.SMALL_GREEN_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.SMALL_PURPLE_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.SMALL_RED_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.SMALL_ROTTEN_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.SMALL_WHITE_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.SMALL_YELLOW_CURTAIN.asItem());

            add(DecorativeBlockRegistryME.BLACK_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.BLUE_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.BROWN_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.BURNT_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.DARK_BLUE_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.DARK_BROWN_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.DARK_GREEN_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.DARK_RED_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.FANCY_BLUE_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.FANCY_GREEN_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.FANCY_RED_CURTAIN   .asItem());
            add(DecorativeBlockRegistryME.GRAY_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.GREEN_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.PURPLE_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.RED_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.ROTTEN_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.WHITE_CURTAIN.asItem());
            add(DecorativeBlockRegistryME.YELLOW_CURTAIN.asItem());

            add(BlockRegistryME.QUARTZ_CLUSTER.asItem());
            add(BlockRegistryME.SMALL_QUARTZ_BUD.asItem());
            add(BlockRegistryME.MEDIUM_QUARTZ_BUD.asItem());
            add(BlockRegistryME.LARGE_QUARTZ_BUD.asItem());
            add(BlockRegistryME.RED_AGATE_CLUSTER.asItem());
            add(BlockRegistryME.SMALL_RED_AGATE_BUD.asItem());
            add(BlockRegistryME.MEDIUM_RED_AGATE_BUD.asItem());
            add(BlockRegistryME.LARGE_RED_AGATE_BUD.asItem());
            add(BlockRegistryME.CITRINE_CLUSTER.asItem());
            add(BlockRegistryME.SMALL_CITRINE_BUD.asItem());
            add(BlockRegistryME.MEDIUM_CITRINE_BUD.asItem());
            add(BlockRegistryME.LARGE_CITRINE_BUD.asItem());
            add(BlockRegistryME.GLOWSTONE_CLUSTER.asItem());
            add(BlockRegistryME.SMALL_GLOWSTONE_BUD.asItem());
            add(BlockRegistryME.MEDIUM_GLOWSTONE_BUD.asItem());
            add(BlockRegistryME.LARGE_GLOWSTONE_BUD.asItem());
        }
    };
}
