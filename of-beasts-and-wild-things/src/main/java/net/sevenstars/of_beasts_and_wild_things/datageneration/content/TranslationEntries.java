package net.sevenstars.of_beasts_and_wild_things.datageneration.content;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.sevenstars.api.enums.LangCategory;
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
            put(of(LangCategory.ITEM_GROUP, "wild_things"), "Of Beasts and Wild Things");

            put(of(LangCategory.SOUNDS, "deer_death"), "Deer dies");
            put(of(LangCategory.SOUNDS, "deer_grunt"), "Deer grunts");
            put(of(LangCategory.SOUNDS, "deer_idle"), "Deer bleats");

            put(of(LangCategory.SOUNDS, "swan_idle"), "Swan honks");
            put(of(LangCategory.SOUNDS, "swan_hurt"), "Swan hurts");
            put(of(LangCategory.SOUNDS, "swan_step"), "Swan steps");
            put(of(LangCategory.SOUNDS, "swan_death"), "Swan dies");
        }
    };

    public static String of(LangCategory langCategory, String value) {
        return OfBeastsAndWildThings.translationKey(langCategory, value);
    }
}
