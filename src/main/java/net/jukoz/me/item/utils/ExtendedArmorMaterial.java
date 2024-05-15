package net.jukoz.me.item.utils;

import net.minecraft.item.ArmorMaterial;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;


public record ExtendedArmorMaterial(RegistryEntry<ArmorMaterial> material, int durabilityModifier, ArmorMaterial.Layer layer,
                                    MutableText faction, MutableText subFaction, int tier) {
}
