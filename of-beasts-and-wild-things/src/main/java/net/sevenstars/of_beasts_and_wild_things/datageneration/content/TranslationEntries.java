package net.sevenstars.of_beasts_and_wild_things.datageneration.content;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranslationEntries {

    public static List<Block> blockEntries = new ArrayList<>() {
    };

    public static List<Item> itemEntries = new ArrayList<>() {
    };

    public static List<EntityType<?>> entityEntries = new ArrayList<>() {
    };

    public static Map<String, String> manualEntries = new HashMap<>() {
        {
            put("itemGroup." + OfBeastsAndWildThings.MOD_ID + ".spawn_egg_items", "Wild Things Spawn Eggs");
            put("itemGroup." + OfBeastsAndWildThings.MOD_ID + ".blocks", "Wild Things Blocks");
            put("itemGroup." + OfBeastsAndWildThings.MOD_ID + ".items", "Wild Things Items");
        }
    };
}
