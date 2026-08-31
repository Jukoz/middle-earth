package net.sevenstars.ofhamletandheroes.dtos.banner;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;

import java.util.List;

public class Banner {
    public static final Codec<Banner> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            DyeColor.CODEC.fieldOf("base").forGetter(Banner::getBaseDye),
            Codec.list(PatternWithColor.CODEC, 1, 9).fieldOf("patterns_with_colors").forGetter(Banner::getBannerPatternsWithColors)
    ).apply(instance, Banner::new));

    private DyeColor baseBannerColor;
    private List<PatternWithColor> patternsWithColors;
    public static final DyeColor DEFAULT_DYE = DyeColor.PINK;

    public DyeColor getBaseDye() {
        return baseBannerColor;
    }

    public List<PatternWithColor> getBannerPatternsWithColors() {
        return patternsWithColors;
    }

    public Banner(DyeColor baseBannerColor, List<PatternWithColor> patternsWithColors) {
        this.baseBannerColor = baseBannerColor;
        this.patternsWithColors = patternsWithColors;
    }

    public ItemStack getBannerItem(RegistryWrapper.WrapperLookup wrapperLookup, Text text) {
        BannerPatternsComponent.Builder builder = new BannerPatternsComponent.Builder();
        RegistryWrapper.Impl<BannerPattern> registry = wrapperLookup.getOrThrow(RegistryKeys.BANNER_PATTERN);
        for (PatternWithColor patternWithColor : patternsWithColors) {
            RegistryEntry<BannerPattern> pattern = registry.getOrThrow(patternWithColor.pattern);
            builder.add(new BannerPatternsComponent.Layer(
                    pattern,
                    patternWithColor.color
            ));
        }

        return formatBanner(new ItemStack(Items.WHITE_BANNER), builder.build(), text);
    }

    public static ItemStack formatBanner(ItemStack itemStack, BannerPatternsComponent bannerPatternsComponent, Text translationKey) {
        itemStack.set(DataComponentTypes.BANNER_PATTERNS, bannerPatternsComponent);
        // itemStack.set(DataComponentTypes.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE); // TODO : Not existing
        itemStack.set(DataComponentTypes.ITEM_NAME, translationKey);
        return itemStack;
    }
}
