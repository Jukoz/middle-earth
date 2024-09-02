package net.jukoz.me.item;

import net.jukoz.me.item.utils.ModBannerPatterns;
import net.jukoz.me.item.utils.ModItemGroups;
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
    public static final List<BannerPatternsComponent.Layer> DURIN_BANNER_LAYERS = new ArrayList<>();

    private static final Text DURIN_BANNER_TRANSLATION_KEY = Text.translatable("block.me.durin_banner").formatted(Formatting.GOLD);
    private static final Text ISENGARD_BANNER_TRANSLATION_KEY = Text.translatable("block.me.isengard_banner").formatted(Formatting.GOLD);
    private static final Text GONDOR_BANNER_TRANSLATION_KEY = Text.translatable("block.me.gondor_banner").formatted(Formatting.GOLD);
    private static final Text GONDOR_WHITE_BANNER_TRANSLATION_KEY = Text.translatable("block.me.gondor_white_banner").formatted(Formatting.GOLD);
    private static final Text LOTHLORIEN_BANNER_TRANSLATION_KEY = Text.translatable("block.me.lothlorien_banner").formatted(Formatting.GOLD);
    private static final Text MISTY_MOUNTAINS_ORCS_EYE_BANNER_TRANSLATION_KEY = Text.translatable("block.me.misty_mountains_orcs_eye_banner").formatted(Formatting.GOLD);
    private static final Text MISTY_MOUNTAINS_ORCS_PEAKS_BANNER_TRANSLATION_KEY = Text.translatable("block.me.misty_mountains_orcs_peaks_banner").formatted(Formatting.GOLD);
    private static final Text MORDOR_BANNER_TRANSLATION_KEY = Text.translatable("block.me.mordor_banner").formatted(Formatting.GOLD);
    private static final Text MORDOR_GREAT_EYE_BANNER_TRANSLATION_KEY = Text.translatable("block.me.mordor_great_eye_banner").formatted(Formatting.GOLD);
    private static final Text ROHAN_BANNER_TRANSLATION_KEY = Text.translatable("block.me.rohan_banner").formatted(Formatting.GOLD);

    public static void addBannersToItemGroup(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(getDurinBanner(bannerPatternLookup));
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(getIsengardBanner(bannerPatternLookup));
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(getGondorBanner(bannerPatternLookup));
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(getGondorWhiteBanner(bannerPatternLookup));
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(getLothlorienBanner(bannerPatternLookup));
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(getMistyOrcsEyeBanner(bannerPatternLookup));
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(getMistyOrcsPeaksBanner(bannerPatternLookup));
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(getMordorEyeBanner(bannerPatternLookup));
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(getMordorGreatEyeBanner(bannerPatternLookup));
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(getRohanBanner(bannerPatternLookup));
    }

    public static List<ItemStack> getList(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        if (BANNERS.isEmpty()) {
            BANNERS.add(getDurinBanner(bannerPatternLookup));
            BANNERS.add(getIsengardBanner(bannerPatternLookup));
            BANNERS.add(getGondorBanner(bannerPatternLookup));
            BANNERS.add(getGondorWhiteBanner(bannerPatternLookup));
            BANNERS.add(getLothlorienBanner(bannerPatternLookup));
            BANNERS.add(getMistyOrcsEyeBanner(bannerPatternLookup));
            BANNERS.add(getMistyOrcsPeaksBanner(bannerPatternLookup));
            BANNERS.add(getMordorEyeBanner(bannerPatternLookup));
            BANNERS.add(getMordorGreatEyeBanner(bannerPatternLookup));
            BANNERS.add(getRohanBanner(bannerPatternLookup));
        }
        return BANNERS;
    }

    public static ItemStack getDurinBanner(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        DURIN_BANNER_LAYERS.add(new BannerPatternsComponent.Layer(bannerPatternLookup.getOrThrow(BannerPatterns.GRADIENT_UP), DyeColor.GRAY));
        DURIN_BANNER_LAYERS.add(new BannerPatternsComponent.Layer(bannerPatternLookup.getOrThrow(ModBannerPatterns.LONGBEARD_BANNER_PATTERN), DyeColor.WHITE));

        ItemStack itemStack = new ItemStack(Items.BLUE_BANNER);

        BannerPatternsComponent bannerPatternsComponent = (new BannerPatternsComponent.Builder())
                .add(DURIN_BANNER_LAYERS.get(0))
                .add(DURIN_BANNER_LAYERS.get(1))
                .build();
        return formatBanner(itemStack, bannerPatternsComponent, DURIN_BANNER_TRANSLATION_KEY);
    }

    public static ItemStack getIsengardBanner(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        ItemStack itemStack = new ItemStack(Items.BLACK_BANNER);
        BannerPatternsComponent bannerPatternsComponent = (new BannerPatternsComponent.Builder())
                .add(bannerPatternLookup, ModBannerPatterns.ISENGARD_BANNER_PATTERN, DyeColor.WHITE)
                .build();
        return formatBanner(itemStack, bannerPatternsComponent, ISENGARD_BANNER_TRANSLATION_KEY);
    }

    public static ItemStack getGondorBanner(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        ItemStack itemStack = new ItemStack(Items.BLACK_BANNER);
        BannerPatternsComponent bannerPatternsComponent = (new BannerPatternsComponent.Builder())
                .add(bannerPatternLookup, ModBannerPatterns.GONDOR_BANNER_PATTERN, DyeColor.WHITE)
                .build();

        return formatBanner(itemStack, bannerPatternsComponent, GONDOR_BANNER_TRANSLATION_KEY);
    }

    public static ItemStack getGondorWhiteBanner(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        ItemStack itemStack = new ItemStack(Items.WHITE_BANNER);
        BannerPatternsComponent bannerPatternsComponent = (new BannerPatternsComponent.Builder())
                .add(bannerPatternLookup, ModBannerPatterns.GONDOR_BANNER_PATTERN, DyeColor.BLACK)
                .build();
        return formatBanner(itemStack, bannerPatternsComponent, GONDOR_WHITE_BANNER_TRANSLATION_KEY);
    }

    public static ItemStack getLothlorienBanner(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        ItemStack itemStack = new ItemStack(Items.WHITE_BANNER);
        BannerPatternsComponent bannerPatternsComponent = (new BannerPatternsComponent.Builder())
                .add(bannerPatternLookup, BannerPatterns.GRADIENT_UP, DyeColor.YELLOW)
                .add(bannerPatternLookup, ModBannerPatterns.LOTHLORIEN_BANNER_PATTERN, DyeColor.YELLOW)
                .build();
        return formatBanner(itemStack, bannerPatternsComponent, LOTHLORIEN_BANNER_TRANSLATION_KEY);
    }

    public static ItemStack getMistyOrcsEyeBanner(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        ItemStack itemStack = new ItemStack(Items.BROWN_BANNER);
        BannerPatternsComponent bannerPatternsComponent = (new BannerPatternsComponent.Builder())
                .add(bannerPatternLookup, BannerPatterns.GRADIENT_UP, DyeColor.BLACK)
                .add(bannerPatternLookup, ModBannerPatterns.MISTY_MOUNTAINS_ORCS_EYE_BANNER_PATTERN, DyeColor.RED)
                .build();
        return formatBanner(itemStack, bannerPatternsComponent, MISTY_MOUNTAINS_ORCS_EYE_BANNER_TRANSLATION_KEY);
    }

    public static ItemStack getMistyOrcsPeaksBanner(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        ItemStack itemStack = new ItemStack(Items.BROWN_BANNER);
        BannerPatternsComponent bannerPatternsComponent = (new BannerPatternsComponent.Builder())
                .add(bannerPatternLookup, BannerPatterns.GRADIENT_UP, DyeColor.BLACK)
                .add(bannerPatternLookup, ModBannerPatterns.MISTY_MOUNTAINS_ORCS_PEAKS_BANNER_PATTERN, DyeColor.RED)
                .build();
        return formatBanner(itemStack, bannerPatternsComponent, MISTY_MOUNTAINS_ORCS_PEAKS_BANNER_TRANSLATION_KEY);
    }

    public static ItemStack getMordorEyeBanner(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        ItemStack itemStack = new ItemStack(Items.BLACK_BANNER);
        BannerPatternsComponent bannerPatternsComponent = (new BannerPatternsComponent.Builder())
                .add(bannerPatternLookup, BannerPatterns.GRADIENT_UP, DyeColor.GRAY)
                .add(bannerPatternLookup, ModBannerPatterns.MORDOR_EYE_BANNER_PATTERN, DyeColor.RED)
                .build();
        return formatBanner(itemStack, bannerPatternsComponent, MORDOR_BANNER_TRANSLATION_KEY);
    }

    public static ItemStack getMordorGreatEyeBanner(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        ItemStack itemStack = new ItemStack(Items.BLACK_BANNER);
        BannerPatternsComponent bannerPatternsComponent = (new BannerPatternsComponent.Builder())
                .add(bannerPatternLookup, BannerPatterns.GRADIENT_UP, DyeColor.GRAY)
                .add(bannerPatternLookup, ModBannerPatterns.MORDOR_GREAT_EYE_BANNER_PATTERN, DyeColor.RED)
                .build();
        return formatBanner(itemStack, bannerPatternsComponent, MORDOR_GREAT_EYE_BANNER_TRANSLATION_KEY);
    }

    public static ItemStack getRohanBanner(RegistryEntryLookup<BannerPattern> bannerPatternLookup) {
        ItemStack itemStack = new ItemStack(Items.GREEN_BANNER);
        BannerPatternsComponent bannerPatternsComponent = (new BannerPatternsComponent.Builder())
                .add(bannerPatternLookup, ModBannerPatterns.ROHAN_BANNER_PATTERN, DyeColor.YELLOW)
                .build();
        return formatBanner(itemStack, bannerPatternsComponent, ROHAN_BANNER_TRANSLATION_KEY);
    }

    public static ItemStack formatBanner(ItemStack itemStack, BannerPatternsComponent bannerPatternsComponent, Text translationKey) {
        itemStack.set(DataComponentTypes.BANNER_PATTERNS, bannerPatternsComponent);
        itemStack.set(DataComponentTypes.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
        itemStack.set(DataComponentTypes.ITEM_NAME, translationKey);
        return itemStack;
    }
}
