package net.sevenstars.middleearth.item.utils;

import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.sevenstars.middleearth.item.ToolItemsME;

public class SmithingTrimPatternsME {
    public static final ResourceKey<TrimPattern> SMITHING_PART = of("smithing_part");

    public static void bootstrap(BootstrapContext<TrimPattern> registry) {
        register(registry, ToolItemsME.SMITHING_HAMMER, SMITHING_PART);
    }

    public static void register(BootstrapContext<TrimPattern> registry, Item template, ResourceKey<TrimPattern> key) {
        TrimPattern armorTrimPattern = new TrimPattern(key.location(), BuiltInRegistries.ITEM.wrapAsHolder(template),
                Component.translatable(Util.makeDescriptionId("trim_pattern", key.location())), false);
        registry.register(key, armorTrimPattern);
    }

    private static ResourceKey<TrimPattern> of(String id) {
        return ResourceKey.create(Registries.TRIM_PATTERN, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, id));
    }
}
