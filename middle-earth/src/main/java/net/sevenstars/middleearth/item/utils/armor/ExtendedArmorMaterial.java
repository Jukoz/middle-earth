package net.sevenstars.middleearth.item.utils.armor;

import net.minecraft.item.equipment.ArmorMaterial;


public record ExtendedArmorMaterial(ArmorMaterial material, int durabilityModifier, ModArmorMaterials.Tiers tier) {
}
