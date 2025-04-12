package net.sevenstars.middleearth.item.utils;

import net.minecraft.item.equipment.trim.ArmorTrimAssets;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.sevenstars.middleearth.MiddleEarth;

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
        register(registry, BRONZE, Style.EMPTY.withColor(13151627), new ArmorTrimAssets(new ArmorTrimAssets.AssetId("bronze"), Map.of()));
        register(registry, BURZUM_STEEL, Style.EMPTY.withColor(5985355), new ArmorTrimAssets(new ArmorTrimAssets.AssetId("burzum_steel"), Map.of()));
        register(registry, CRUDE, Style.EMPTY.withColor(7560021), new ArmorTrimAssets(new ArmorTrimAssets.AssetId("crude"), Map.of()));
        register(registry, EDHEL_STEEL, Style.EMPTY.withColor(15921385), new ArmorTrimAssets(new ArmorTrimAssets.AssetId("edhel_steel"), Map.of()));
        register(registry, JADE, Style.EMPTY.withColor(5869927), new ArmorTrimAssets(new ArmorTrimAssets.AssetId("jade"), Map.of()));
        register(registry, KHAZAD_STEEL, Style.EMPTY.withColor(6778743), new ArmorTrimAssets(new ArmorTrimAssets.AssetId("khazad_steel"), Map.of()));
        register(registry, LEAD, Style.EMPTY.withColor(6384761), new ArmorTrimAssets(new ArmorTrimAssets.AssetId("lead"), Map.of()));
        register(registry, MITHRIL, Style.EMPTY.withColor(14278631), new ArmorTrimAssets(new ArmorTrimAssets.AssetId("mithril"), Map.of()));
        register(registry, SILVER, Style.EMPTY.withColor(15397618), new ArmorTrimAssets(new ArmorTrimAssets.AssetId("silver"), Map.of()));
        register(registry, STEEL, Style.EMPTY.withColor(0xECECEC), new ArmorTrimAssets(new ArmorTrimAssets.AssetId("steel"), Map.of()));
        register(registry, TIN, Style.EMPTY.withColor(13026492), new ArmorTrimAssets(new ArmorTrimAssets.AssetId("tin"), Map.of()));
    }

    private static void register(Registerable<ArmorTrimMaterial> registry, RegistryKey<ArmorTrimMaterial> key, Style style, ArmorTrimAssets assets) {
        Text text = Text.translatable(Util.createTranslationKey("trim_material", key.getValue())).fillStyle(style);
        registry.register(key, new ArmorTrimMaterial(assets, text));
    }

    private static RegistryKey<ArmorTrimMaterial> of(String id) {
        return RegistryKey.of(RegistryKeys.TRIM_MATERIAL, Identifier.of(MiddleEarth.MOD_ID, id));
    }
}
