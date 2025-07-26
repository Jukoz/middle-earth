package net.sevenstars.middleearth.item.items;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.VerticallyAttachableBlockItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Direction;

import java.util.function.Consumer;

public class ArkenstoneItem extends VerticallyAttachableBlockItem {


    public ArkenstoneItem(Block standingBlock, Block wallBlock, Direction verticalAttachmentDirection, Settings settings) {
        super(standingBlock, wallBlock, verticalAttachmentDirection, settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey()).formatted(Formatting.LIGHT_PURPLE).formatted(Formatting.ITALIC);
    }
}
