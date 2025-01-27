package net.sevenstars.middleearth.item.utils.armor;

import net.minecraft.item.ArmorMaterial;
import net.minecraft.registry.entry.RegistryEntry;


public record ExtendedArmorMaterial(RegistryEntry<ArmorMaterial> material, int durabilityModifier, ModArmorMaterials.Tiers tier) {
}
