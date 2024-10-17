package net.jukoz.me.item.items;

import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.TemperatureDataComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.item.tooltip.TooltipType;

import java.util.List;

public class SmithingMaterialItem extends Item {

    public SmithingMaterialItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        TemperatureDataComponent temperatureDataComponent = stack.get(ModDataComponentTypes.TEMPERATURE_DATA);
    }
}
