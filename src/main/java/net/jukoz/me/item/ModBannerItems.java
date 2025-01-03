package net.jukoz.me.item;

import net.jukoz.me.item.utils.ModBannerPatterns;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.resources.MiddleEarthFactions;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Unit;

import java.util.ArrayList;
import java.util.List;

public class ModBannerItems {
    public static final List<ItemStack> BANNERS = new ArrayList<>();
    public static void addBannersToItemGroup(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        // Good
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(getDaleBanner(bannerPatternLookup));
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(getEreborBanner(bannerPatternLookup));
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(getGondorBanner(bannerPatternLookup));
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(getLothlorienBanner(bannerPatternLookup));
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(getRohanBanner(bannerPatternLookup));
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(getShireBanner(bannerPatternLookup));
        // Evil
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(getIsengardBanner(bannerPatternLookup));
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(getMistyMountainsGoblinsBanner(bannerPatternLookup));
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(getMordorBanner(bannerPatternLookup));
    }

    public static List<ItemStack> getList(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        if (BANNERS.isEmpty()) {
            // Good
            BANNERS.add(getDaleBanner(bannerPatternLookup));
            BANNERS.add(getEreborBanner(bannerPatternLookup));
            BANNERS.add(getGondorBanner(bannerPatternLookup));
            BANNERS.add(getLothlorienBanner(bannerPatternLookup));
            BANNERS.add(getRohanBanner(bannerPatternLookup));
            BANNERS.add(getShireBanner(bannerPatternLookup));
            // Evil
            BANNERS.add(getIsengardBanner(bannerPatternLookup));
            BANNERS.add(getMistyMountainsGoblinsBanner(bannerPatternLookup));
            BANNERS.add(getMordorBanner(bannerPatternLookup));
        }
        return BANNERS;
    }

    public static ItemStack getEreborBanner(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        Text translationKey = Text.translatable("block.me.erebor_banner").formatted(Formatting.GOLD);
        return formatBanner(new ItemStack(Items.WHITE_BANNER), MiddleEarthFactions.LONGBEARDS_EREBOR.getBannerPatternComponents(bannerPatternLookup), translationKey);
    }

    public static ItemStack getIsengardBanner(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        Text translationKey = Text.translatable("block.me.isengard_banner").formatted(Formatting.GOLD);
        return formatBanner(new ItemStack(Items.WHITE_BANNER), MiddleEarthFactions.ISENGARD.getBannerPatternComponents(bannerPatternLookup), translationKey);
    }

    public static ItemStack getGondorBanner(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        Text translationKey = Text.translatable("block.me.gondor_banner").formatted(Formatting.GOLD);
        return formatBanner(new ItemStack(Items.WHITE_BANNER), MiddleEarthFactions.GONDOR.getBannerPatternComponents(bannerPatternLookup), translationKey);
    }

    public static ItemStack getLothlorienBanner(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        Text translationKey = Text.translatable("block.me.lothlorien_banner").formatted(Formatting.GOLD);
        return formatBanner(new ItemStack(Items.WHITE_BANNER), MiddleEarthFactions.LOTHLORIEN.getBannerPatternComponents(bannerPatternLookup), translationKey);
    }

    public static ItemStack getMistyMountainsGoblinsBanner(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        Text translationKey = Text.translatable("block.me.misty_mountains_goblins_banner").formatted(Formatting.GOLD);
        return formatBanner(new ItemStack(Items.WHITE_BANNER), MiddleEarthFactions.MISTY_MOUNTAINS_GOBLINS.getBannerPatternComponents(bannerPatternLookup), translationKey);
    }

    public static ItemStack getMordorBanner(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        Text translationKey = Text.translatable("block.me.mordor_banner").formatted(Formatting.GOLD);
        return formatBanner(new ItemStack(Items.WHITE_BANNER), MiddleEarthFactions.MORDOR.getBannerPatternComponents(bannerPatternLookup), translationKey);
    }

    public static ItemStack getRohanBanner(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        Text translationKey = Text.translatable("block.me.rohan_banner").formatted(Formatting.GOLD);
        return formatBanner(new ItemStack(Items.WHITE_BANNER), MiddleEarthFactions.ROHAN.getBannerPatternComponents(bannerPatternLookup), translationKey);
    }

    public static ItemStack getShireBanner(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        Text translationKey = Text.translatable("block.me.shire_banner").formatted(Formatting.GOLD);
        return formatBanner(new ItemStack(Items.WHITE_BANNER), MiddleEarthFactions.SHIRE.getBannerPatternComponents(bannerPatternLookup), translationKey);
    }

    public static ItemStack getDaleBanner(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        Text translationKey = Text.translatable("block.me.dale_banner").formatted(Formatting.GOLD);
        return formatBanner(new ItemStack(Items.WHITE_BANNER), MiddleEarthFactions.DALE.getBannerPatternComponents(bannerPatternLookup), translationKey);
    }

    public static ItemStack formatBanner(ItemStack itemStack, BannerPatternsComponent bannerPatternsComponent, Text translationKey) {
        itemStack.set(DataComponentTypes.BANNER_PATTERNS, bannerPatternsComponent);
        itemStack.set(DataComponentTypes.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
        itemStack.set(DataComponentTypes.ITEM_NAME, translationKey);
        return itemStack;
    }

    /* Custom way :
            ItemStack itemStack = new ItemStack(Items.WHITE_BANNER);
        BannerPatternsComponent bannerPatternsComponent = (new BannerPatternsComponent.Builder())
                .add(bannerPatternLookup, BannerPatterns.GRADIENT_UP, DyeColor.YELLOW)
                .add(bannerPatternLookup, ModBannerPatterns.GOLDENWOOD, DyeColor.YELLOW)
                .build();
        return formatBanner(itemStack, bannerPatternsComponent, LOTHLORIEN_BANNER_TRANSLATION_KEY);
     */
}
