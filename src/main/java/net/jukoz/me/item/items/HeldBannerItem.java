package net.jukoz.me.item.items;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class HeldBannerItem extends Item {
    public HeldBannerItem(Settings settings) {
        super(settings);
    }

    public static void appendBannerTooltip(ItemStack stack, List<Text> tooltip) {
        BannerPatternsComponent bannerPatternsComponent = (BannerPatternsComponent)stack.get(DataComponentTypes.BANNER_PATTERNS);
        if (bannerPatternsComponent != null) {
            for(int i = 0; i < Math.min(bannerPatternsComponent.layers().size(), 6); ++i) {
                BannerPatternsComponent.Layer layer = (BannerPatternsComponent.Layer)bannerPatternsComponent.layers().get(i);
                tooltip.add(layer.getTooltipText().formatted(Formatting.GRAY));
            }

        }
    }

    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        appendBannerTooltip(stack, tooltip);
    }
}
