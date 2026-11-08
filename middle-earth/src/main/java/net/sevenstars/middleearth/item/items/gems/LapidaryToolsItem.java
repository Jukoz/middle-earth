package net.sevenstars.middleearth.item.items.gems;

import net.sevenstars.middleearth.recipe.lapidary.LapidaryTools;

import java.util.function.Consumer;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/**
 * A set of lapidary tools, installed in the Lapidary Table's tools slot (Faceting screen). It raises the best
 * quality the table can cut to; it does not wear out.
 */
public class LapidaryToolsItem extends Item {
    private final LapidaryTools tools;

    public LapidaryToolsItem(Settings settings, LapidaryTools tools) {
        super(settings);
        this.tools = tools;
    }

    public LapidaryTools tools() {
        return tools;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent,
                              Consumer<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, displayComponent, tooltip, type);
        tooltip.accept(Text.translatable("tooltip.middle-earth.lapidary_tools.limit",
            Text.translatable(tools.limit().translationKey()).formatted(tools.limit().formatting()))
            .formatted(Formatting.GRAY));
        tooltip.accept(Text.translatable("tooltip.middle-earth.lapidary_tools.install").formatted(Formatting.DARK_GRAY));
    }
}
