package net.sevenstars.middleearth.common.registration;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

// What registration collects for datagen and the client. The datagen classes that own the matching lists
// name registry constants, so registration must not load them; it writes here and they pick these lists up.
public class RegistrationLists {
    public static final List<Block> DROPS = new ArrayList<>();
    public static final List<Item> SIMPLE_ITEM_MODELS = new ArrayList<>();
    public static final List<Block> NOT_TINTED_CROSS = new ArrayList<>();
    public static final List<Item> WEAPONS = new ArrayList<>();
    public static final List<Item> AXES = new ArrayList<>();
    public static final List<Item> DAGGERS = new ArrayList<>();
    public static final List<Item> SWORDS = new ArrayList<>();
    public static final List<Item> SHARP_WEAPONS = new ArrayList<>();

    // Puts a datagen class's hand-written entries in front of what registration collected.
    // An entry registration already added is skipped, or it would be generated twice.
    public static <T> void prepend(List<T> collected, List<T> handWritten) {
        List<T> missing = new ArrayList<>(handWritten);
        missing.removeAll(collected);
        collected.addAll(0, missing);
    }
}
