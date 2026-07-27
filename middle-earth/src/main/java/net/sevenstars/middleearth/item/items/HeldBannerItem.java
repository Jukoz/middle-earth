package net.sevenstars.middleearth.item.items;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

public class HeldBannerItem extends Item {
    public HeldBannerItem(Properties settings) {
        super(settings.stacksTo(1));
    }

    public static void appendBannerTooltip(ItemStack stack, List<Component> tooltip) {
        BannerPatternLayers bannerPatternsComponent = (BannerPatternLayers)stack.get(DataComponents.BANNER_PATTERNS);
        if (bannerPatternsComponent != null) {
            for(int i = 0; i < Math.min(bannerPatternsComponent.layers().size(), 6); ++i) {
                BannerPatternLayers.Layer layer = (BannerPatternLayers.Layer)bannerPatternsComponent.layers().get(i);
                tooltip.add(layer.description().withStyle(ChatFormatting.GRAY));
            }

        }
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        appendBannerTooltip(stack, tooltip);
    }
}
