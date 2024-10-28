package net.jukoz.me.item.utils;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.StoneBlockSets;
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
    public static final RegistryKey<ArmorTrimMaterial> BURZUM_STEEL = of("burzum_steel");
    public static final RegistryKey<ArmorTrimMaterial> CRUDE = of("crude");
    public static final RegistryKey<ArmorTrimMaterial> EDHEL_STEEL = of("edhel_steel");
    public static final RegistryKey<ArmorTrimMaterial> JADE = of("jade");
    public static final RegistryKey<ArmorTrimMaterial> KHAZAD_STEEL = of("khazad_steel");
    public static final RegistryKey<ArmorTrimMaterial> LEAD = of("lead");
    public static final RegistryKey<ArmorTrimMaterial> MITHRIL = of("mithril");
    public static final RegistryKey<ArmorTrimMaterial> SILVER = of("silver");
    public static final RegistryKey<ArmorTrimMaterial> STEEL = of("steel");
    public static final RegistryKey<ArmorTrimMaterial> TIN = of("tin");

    public static void bootstrap(Registerable<ArmorTrimMaterial> registry) {
        register(registry, BRONZE, ModResourceItems.BRONZE_INGOT, Style.EMPTY.withColor(13151627), 0.005f);
        register(registry, BURZUM_STEEL, ModResourceItems.BURZUM_STEEL_INGOT, Style.EMPTY.withColor(5985355), 0.008f);
        register(registry, CRUDE, ModResourceItems.CRUDE_INGOT, Style.EMPTY.withColor(7560021), 0.007f);
        register(registry, EDHEL_STEEL, ModResourceItems.EDHEL_STEEL_INGOT, Style.EMPTY.withColor(15921385), 0.009f);
        register(registry, JADE, StoneBlockSets.JADEITE.base().asItem(), Style.EMPTY.withColor(5869927), 0.001f);
        register(registry, KHAZAD_STEEL, ModResourceItems.KHAZAD_STEEL_INGOT, Style.EMPTY.withColor(6778743), 0.011f);
        register(registry, LEAD, ModResourceItems.LEAD_INGOT, Style.EMPTY.withColor(6384761), 0.003f);
        register(registry, MITHRIL, ModResourceItems.MITHRIL_INGOT, Style.EMPTY.withColor(14278631), 0.012f);
        register(registry, SILVER, ModResourceItems.SILVER_INGOT, Style.EMPTY.withColor(15397618), 0.004f);
        register(registry, STEEL, ModResourceItems.STEEL_INGOT, Style.EMPTY.withColor(0xECECEC), 0.006f);
        register(registry, TIN, ModResourceItems.TIN_INGOT, Style.EMPTY.withColor(13026492), 0.002f);
    }

    private static void register(Registerable<ArmorTrimMaterial> registry, RegistryKey<ArmorTrimMaterial> key, Item ingredient, Style style, float itemModelIndex) {
        register(registry, key, ingredient, style, itemModelIndex, Map.of());
    }

    private static void register(Registerable<ArmorTrimMaterial> registry, RegistryKey<ArmorTrimMaterial> key, Item ingredient, Style style, float itemModelIndex, Map<RegistryEntry<ArmorMaterial>, String> overrideArmorMaterials) {
        ArmorTrimMaterial armorTrimMaterial = ArmorTrimMaterial.of(key.getValue().getPath(), ingredient, itemModelIndex, Text.translatable(Util.createTranslationKey("trim_material", key.getValue())).fillStyle(style), overrideArmorMaterials);
        registry.register(key, armorTrimMaterial);
    }

    private static RegistryKey<ArmorTrimMaterial> of(String id) {
        return RegistryKey.of(RegistryKeys.TRIM_MATERIAL, Identifier.of(MiddleEarth.MOD_ID, id));
    }
}
