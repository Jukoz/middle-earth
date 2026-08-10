package net.sevenstars.middleearth.item.items;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.LargeDoorBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;
import java.util.function.Consumer;

public class DoorBlockItem extends BlockItem {
    private final LargeDoorBlock block;

    public DoorBlockItem(LargeDoorBlock block, Settings settings) {
        super(block, settings);
        this.block = block;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        textConsumer.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".door_size").append(block.getDoorWidth() + "x" + block.getDoorHeight()));
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
    }
}
