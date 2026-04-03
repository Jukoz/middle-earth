package net.sevenstars.middleearth.entity.beasts.broadhoof;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.function.ValueLists;
import net.sevenstars.middleearth.item.ResourceItemsME;

import java.util.Map;
import java.util.function.IntFunction;

public enum BroadhoofGoatBeads {
    NONE(0),
    LEATHER(1),
    COAL(2),
    COPPER(3),
    GOLD(4),
    ALMANDINE(5);

    private static final Map<Item, BroadhoofGoatBeads> BEARD_MATERIALS = Map.of(
            Items.LEATHER, LEATHER,
            Items.COAL, COAL,
            Items.COPPER_INGOT, COPPER,
            Items.GOLD_NUGGET, GOLD,
            ResourceItemsME.RED_AGATE_SHARD, ALMANDINE
            );

    public static boolean isValidMaterial(ItemStack stack) {
        return BEARD_MATERIALS.containsKey(stack.getItem());
    }

    public static BroadhoofGoatBeads getBeads(ItemStack stack) {
        return BEARD_MATERIALS.get(stack.getItem());
    }

    private static final IntFunction<BroadhoofGoatBeads> INDEX_MAPPER = ValueLists.createIndexToValueFunction(
            BroadhoofGoatBeads::getIndex, values(), ValueLists.OutOfBoundsHandling.WRAP
    );
    private final int index;

    private BroadhoofGoatBeads(final int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public static BroadhoofGoatBeads byIndex(int index) {
        return (BroadhoofGoatBeads)INDEX_MAPPER.apply(index);
    }
}
