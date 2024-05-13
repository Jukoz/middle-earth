package net.jukoz.me.item.utils;

import net.jukoz.me.item.items.CustomBootsItem;
import net.jukoz.me.item.items.CustomChestplateItem;
import net.jukoz.me.item.items.CustomHelmetItem;
import net.jukoz.me.item.items.CustomLeggingsItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;


public record ExtendedArmorMaterial(RegistryEntry<ArmorMaterial> material, int durabilityModifier, MutableText faction, MutableText subFaction, int tier) {
}
