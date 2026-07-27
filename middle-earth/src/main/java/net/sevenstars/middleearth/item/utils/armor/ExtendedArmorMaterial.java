package net.sevenstars.middleearth.item.utils.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

import java.util.Map;

public record ExtendedArmorMaterial(
        Holder<ArmorMaterial> material,
        int durabilityModifier,
        ArmorMaterialsME.Tiers tier,
        Map<ArmorItem.Type, Integer> defense
) {
}
