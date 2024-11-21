package net.jukoz.me.item.utils;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModResourceItems;
import net.jukoz.me.item.ModToolItems;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrimPattern;
import net.minecraft.item.trim.ArmorTrimPattern;
import net.minecraft.item.trim.ArmorTrimPatterns;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;
import java.util.Optional;

public class ModSmithingTrimPatterns {
    public static final RegistryKey<ArmorTrimPattern> SMITHING_PART = of("smithing_part");

    public static void bootstrap(Registerable<ArmorTrimPattern> registry) {
        register(registry, ModToolItems.SMITHING_HAMMER, SMITHING_PART);
    }


    public static Optional<RegistryEntry.Reference<ArmorTrimPattern>> get(RegistryWrapper.WrapperLookup registriesLookup, ItemStack stack) {
        return registriesLookup.getWrapperOrThrow(RegistryKeys.TRIM_PATTERN).streamEntries().filter(pattern -> stack.itemMatches(((ArmorTrimPattern)pattern.value()).templateItem())).findFirst();
    }

    public static void register(Registerable<ArmorTrimPattern> registry, Item template, RegistryKey<ArmorTrimPattern> key) {
        ArmorTrimPattern armorTrimPattern = new ArmorTrimPattern(key.getValue(), Registries.ITEM.getEntry(template), Text.translatable(Util.createTranslationKey("trim_pattern", key.getValue())), false);
        registry.register(key, armorTrimPattern);
    }

    private static RegistryKey<ArmorTrimPattern> of(String id) {
        return RegistryKey.of(RegistryKeys.TRIM_PATTERN, Identifier.of(MiddleEarth.MOD_ID, id));
    }
}
