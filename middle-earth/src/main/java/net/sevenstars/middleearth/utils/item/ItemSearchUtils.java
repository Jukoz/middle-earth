package net.sevenstars.middleearth.utils.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class ItemSearchUtils {
    /**
     * Finds the first occurrence of a specific item in the player's main inventory.
     *
     * @param player     The player whose inventory to search.
     * @param targetItem The item to search for.
     * @return The first matching {@link ItemStack}, or {@link ItemStack#EMPTY} if none found.
     */
    public static ItemStack findFirstInInventory(PlayerEntity player, Item targetItem) {
        return findFirstMatching(player, stack -> stack.getItem() == targetItem);
    }

    /**
     * Finds the first item in the player's main inventory that matches the given filter.
     *
     * @param player The player whose inventory to search.
     * @param filter A predicate used to match {@link ItemStack} entries.
     * @return The first matching {@link ItemStack}, or {@link ItemStack#EMPTY} if none found.
     */
    public static ItemStack findFirstMatching(PlayerEntity player, Predicate<ItemStack> filter) {
        return player.getInventory().getMainStacks().stream()
                .filter(filter)
                .findFirst()
                .orElse(ItemStack.EMPTY);
    }
}
