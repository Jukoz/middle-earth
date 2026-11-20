package net.sevenstars.middleearth.common.registration;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

// Registration writes here instead of into the datagen lists, because loading those
// classes mid-registration leaves null blocks in them
public class RegistrationLists {
    public static final List<Block> DROPS = new ArrayList<>();
    public static final List<Item> SIMPLE_ITEM_MODELS = new ArrayList<>();
    public static final List<Block> NOT_TINTED_CROSS = new ArrayList<>();
    public static final List<Item> WEAPONS = new ArrayList<>();
    public static final List<Item> AXES = new ArrayList<>();
    public static final List<Item> DAGGERS = new ArrayList<>();
    public static final List<Item> SWORDS = new ArrayList<>();
    public static final List<Item> SHARP_WEAPONS = new ArrayList<>();

    // Skips entries registration already added so nothing is generated twice
    public static <T> void prepend(List<T> collected, List<T> handWritten) {
        List<T> missing = new ArrayList<>(handWritten);
        missing.removeAll(collected);
        collected.addAll(0, missing);
    }
}
