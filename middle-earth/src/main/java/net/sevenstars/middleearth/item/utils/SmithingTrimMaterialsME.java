package net.sevenstars.middleearth.item.utils;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.StoneBlockSets;
import net.sevenstars.middleearth.item.ResourceItemsME;

import java.util.Map;

public class SmithingTrimMaterialsME {
    public static final ResourceKey<TrimMaterial> BRONZE = of("bronze");
    public static final ResourceKey<TrimMaterial> BURZUM_STEEL = of("burzum_steel");
    public static final ResourceKey<TrimMaterial> CRUDE = of("crude");
    public static final ResourceKey<TrimMaterial> EDHEL_STEEL = of("edhel_steel");
    public static final ResourceKey<TrimMaterial> JADE = of("jade");
    public static final ResourceKey<TrimMaterial> KHAZAD_STEEL = of("khazad_steel");
    public static final ResourceKey<TrimMaterial> LEAD = of("lead");
    public static final ResourceKey<TrimMaterial> MITHRIL = of("mithril");
    public static final ResourceKey<TrimMaterial> SILVER = of("silver");
    public static final ResourceKey<TrimMaterial> STEEL = of("steel");
    public static final ResourceKey<TrimMaterial> TIN = of("tin");

    public static void bootstrap(BootstrapContext<TrimMaterial> registry) {
        register(registry, BRONZE, ResourceItemsME.BRONZE_INGOT, Style.EMPTY.withColor(13151627), 0.005F);
        register(registry, BURZUM_STEEL, ResourceItemsME.BURZUM_STEEL_INGOT, Style.EMPTY.withColor(5985355), 0.008F);
        register(registry, CRUDE, ResourceItemsME.CRUDE_INGOT, Style.EMPTY.withColor(7560021), 0.007F);
        register(registry, EDHEL_STEEL, ResourceItemsME.EDHEL_STEEL_INGOT, Style.EMPTY.withColor(15921385), 0.009F);
        register(registry, JADE, StoneBlockSets.JADEITE_SET.baseBlocks.base().asItem(), Style.EMPTY.withColor(5869927), 0.001F);
        register(registry, KHAZAD_STEEL, ResourceItemsME.KHAZAD_STEEL_INGOT, Style.EMPTY.withColor(6778743), 0.011F);
        register(registry, LEAD, ResourceItemsME.LEAD_INGOT, Style.EMPTY.withColor(6384761), 0.003F);
        register(registry, MITHRIL, ResourceItemsME.MITHRIL_INGOT, Style.EMPTY.withColor(14278631), 0.012F);
        register(registry, SILVER, ResourceItemsME.SILVER_INGOT, Style.EMPTY.withColor(15397618), 0.004F);
        register(registry, STEEL, ResourceItemsME.STEEL_INGOT, Style.EMPTY.withColor(0xECECEC), 0.006F);
        register(registry, TIN, ResourceItemsME.TIN_INGOT, Style.EMPTY.withColor(13026492), 0.002F);
    }

    private static void register(BootstrapContext<TrimMaterial> registry, ResourceKey<TrimMaterial> key, Item ingredient,
                                 Style style, float itemModelIndex) {
        register(registry, key, ingredient, style, itemModelIndex, Map.of());
    }

    private static void register(BootstrapContext<TrimMaterial> registry, ResourceKey<TrimMaterial> key, Item ingredient,
                                 Style style, float itemModelIndex,
                                 Map<Holder<ArmorMaterial>, String> overrideArmorMaterials) {
        Component text = Component.translatable(Util.makeDescriptionId("trim_material", key.location())).withStyle(style);
        registry.register(key, TrimMaterial.create(key.location().getPath(), ingredient, itemModelIndex, text, overrideArmorMaterials));
    }

    private static ResourceKey<TrimMaterial> of(String id) {
        return ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, id));
    }
}
