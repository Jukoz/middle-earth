package net.sevenstars.middleearth.recipe.lapidary;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;

/**
 * Glasses and the Dwarven Monocle fitted with focusing lenses ({@link EyewearLensesRecipe}). Worn, they help at the
 * Lapidary Table: every facet's zone is a little easier (2% with Refined lenses, 4% with Exquisite), and every
 * study has one rune that moving the candle alone can light.
 */
public final class LapidaryEyewear {
    /** How much easier faceting is, by lens tier (0 none, 1 Refined, 2 Exquisite). */
    private static final double[] EASE = {0, 0.02, 0.04};

    private LapidaryEyewear() {
    }

    /** Glasses or a Dwarven Monocle. */
    public static boolean isEyewear(ItemStack stack) {
        return stack.isOf(EquipmentItemsME.GLASSES) || stack.isOf(EquipmentItemsME.DWARVEN_MONOCLE);
    }

    /** The lens tier of a lens item: 1 Refined, 2 Exquisite, 0 anything else. */
    public static int lensTier(Item lenses) {
        return lenses == ResourceItemsME.REFINED_FOCUSING_LENSES ? 1
            : lenses == ResourceItemsME.EXQUISITE_FOCUSING_LENSES ? 2 : 0;
    }

    /** The lenses fitted in a piece of eyewear: 0 none, 1 Refined, 2 Exquisite. */
    public static int fitted(ItemStack stack) {
        return isEyewear(stack) ? stack.getOrDefault(DataComponentTypesME.EYEWEAR_LENSES, 0) : 0;
    }

    /** The lenses in the eyewear {@code player} wears. */
    public static int wornTier(PlayerEntity player) {
        return fitted(player.getEquippedStack(EquipmentSlot.HEAD));
    }

    /** How much easier faceting is for {@code player}, as a share (0.02 is 2%). */
    public static double ease(PlayerEntity player) {
        return EASE[wornTier(player)];
    }
}
