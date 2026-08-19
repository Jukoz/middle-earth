package net.sevenstars.middleearth.utils.item;

import java.util.function.Predicate;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemSearchUtils {
    /**
     * Finds the first occurrence of a specific item in the player's main inventory.
     *
     * @param player     The player whose inventory to search.
     * @param targetItem The item to search for.
     * @return The first matching {@link ItemStack}, or {@link ItemStack#EMPTY} if none found.
     */
    public static ItemStack findFirstInInventory(Player player, Item targetItem) {
        return findFirstMatching(player, stack -> stack.getItem() == targetItem);
    }

    /**
     * Finds the first item in the player's main inventory that matches the given filter.
     *
     * @param player The player whose inventory to search.
     * @param filter A predicate used to match {@link ItemStack} entries.
     * @return The first matching {@link ItemStack}, or {@link ItemStack#EMPTY} if none found.
     */
    public static ItemStack findFirstMatching(Player player, Predicate<ItemStack> filter) {
        for (ItemStack stack : player.getInventory().items) {
            if (filter.test(stack)) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}
