package net.sevenstars.middleearth.item.utils;

import net.minecraft.item.equipment.trim.ArmorTrimPattern;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.ModToolItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Optional;

public class ModSmithingTrimPatterns {
    public static final RegistryKey<ArmorTrimPattern> SMITHING_PART = of("smithing_part");

    public static void bootstrap(Registerable<ArmorTrimPattern> registry) {
        register(registry, SMITHING_PART);
    }

    public static void register(Registerable<ArmorTrimPattern> registry, RegistryKey<ArmorTrimPattern> key) {
        ArmorTrimPattern armorTrimPattern = new ArmorTrimPattern(key.getValue(), Text.translatable(Util.createTranslationKey("trim_pattern", key.getValue())), false);
        registry.register(key, armorTrimPattern);
    }

    private static RegistryKey<ArmorTrimPattern> of(String id) {
        return RegistryKey.of(RegistryKeys.TRIM_PATTERN, Identifier.of(MiddleEarth.MOD_ID, id));
    }
}
