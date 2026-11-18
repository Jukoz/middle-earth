package net.sevenstars.middleearth.recipe.lapidary;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;

/** Lens-fitted eyewear: easier facets and one free rune while studying. */
public final class LapidaryEyewear {
    private static final double[] EASE = {0, 0.02, 0.04};

    private LapidaryEyewear() {
    }

    public static boolean isEyewear(ItemStack stack) {
        return stack.isOf(EquipmentItemsME.GLASSES) || stack.isOf(EquipmentItemsME.DWARVEN_MONOCLE);
    }

    public static int lensTier(Item lenses) {
        return lenses == ResourceItemsME.REFINED_FOCUSING_LENSES ? 1
            : lenses == ResourceItemsME.EXQUISITE_FOCUSING_LENSES ? 2 : 0;
    }

    public static int fitted(ItemStack stack) {
        return isEyewear(stack) ? stack.getOrDefault(DataComponentTypesME.EYEWEAR_LENSES, 0) : 0;
    }

    public static int wornTier(PlayerEntity player) {
        return fitted(player.getEquippedStack(EquipmentSlot.HEAD));
    }

    public static double ease(PlayerEntity player) {
        return EASE[wornTier(player)];
    }
}
