package net.jukoz.me.item.utils;

import net.jukoz.me.item.ModResourceItems;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class ModSmithingTrimMaterials {
    public static final RegistryKey<ArmorTrimMaterial> BRONZE = of("bronze");
    public static final RegistryKey<ArmorTrimMaterial> STEEL = of("steel");

    public static void bootstrap(Registerable<ArmorTrimMaterial> registry) {
        register(registry, BRONZE, ModResourceItems.BRONZE_INGOT, Style.EMPTY.withColor(13151627), 0.1f);
        register(registry, STEEL, ModResourceItems.STEEL_INGOT, Style.EMPTY.withColor(0xECECEC), 0.2f);
    }

    private static void register(Registerable<ArmorTrimMaterial> registry, RegistryKey<ArmorTrimMaterial> key, Item ingredient, Style style, float itemModelIndex) {
        register(registry, key, ingredient, style, itemModelIndex, Map.of());
    }

    private static void register(Registerable<ArmorTrimMaterial> registry, RegistryKey<ArmorTrimMaterial> key, Item ingredient, Style style, float itemModelIndex, Map<RegistryEntry<ArmorMaterial>, String> overrideArmorMaterials) {
        ArmorTrimMaterial armorTrimMaterial = ArmorTrimMaterial.of(key.getValue().getPath(), ingredient, itemModelIndex, Text.translatable(Util.createTranslationKey("trim_material", key.getValue())).fillStyle(style), overrideArmorMaterials);
        registry.register(key, armorTrimMaterial);
    }

    private static RegistryKey<ArmorTrimMaterial> of(String id) {
        return RegistryKey.of(RegistryKeys.TRIM_MATERIAL, new Identifier(id));
    }
}
