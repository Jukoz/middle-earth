package net.sevenstars.middleearth.item.items;

import net.sevenstars.middleearth.item.utils.MEEquipmentTooltip;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.VerticallyAttachableBlockItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Direction;

import java.util.List;

public class ArkenstoneItem extends VerticallyAttachableBlockItem implements MEEquipmentTooltip {


    public ArkenstoneItem(Block standingBlock, Block wallBlock, Direction verticalAttachmentDirection, Settings settings) {
        super(standingBlock, wallBlock, verticalAttachmentDirection, settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey()).formatted(Formatting.LIGHT_PURPLE).formatted(Formatting.ITALIC);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        appendBaseArtefactTooltip(tooltip, stack);
    }
}
