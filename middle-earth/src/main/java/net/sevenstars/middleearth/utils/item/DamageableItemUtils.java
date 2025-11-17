package net.sevenstars.middleearth.utils.item;

import net.minecraft.item.ItemStack;

public class DamageableItemUtils {


    /**
     * Checks if an ItemStack is damageable and fully used (ex. its damage equals or exceeds max damage).
     *
     * @param stack The ItemStack to check.
     * @return true if the item is damageable and out of durability; false otherwise.
     */
    public static boolean isFullyDamaged(ItemStack stack) {
        return stack.isDamageable() && stack.getDamage() >= stack.getMaxDamage();
    }

    /**
     * Increases the damage value of a damageable {@link ItemStack} by the specified amount.
     * Has no effect if the item is not damageable.
     *
     * @param stack  The ItemStack to apply damage to.
     * @param damage The amount of damage to add to the current value.
     */
    public static void incrementDamage(ItemStack stack, int damage) {
        if (stack.isDamageable()) {
            stack.setDamage(stack.getDamage() + damage);
        }
    }

    /**
     * Resets the damage value of the given {@link ItemStack} to zero, effectively restoring it to full durability.
     *
     * @param item the item stack whose damage should be reset
     */
    public static void resetDamage(ItemStack item) {
        item.setDamage(0);
    }
}
