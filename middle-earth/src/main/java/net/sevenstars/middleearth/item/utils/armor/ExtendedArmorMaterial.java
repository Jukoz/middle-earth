package net.sevenstars.middleearth.item.utils.armor;

import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.registry.entry.RegistryEntry;


public record ExtendedArmorMaterial(RegistryEntry<ArmorMaterial> material, int durabilityModifier, ModArmorMaterials.Tiers tier) {
}
