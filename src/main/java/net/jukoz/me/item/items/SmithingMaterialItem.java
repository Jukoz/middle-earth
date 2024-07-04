package net.jukoz.me.item.items;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.TemperatureDataComponent;
import net.minecraft.client.item.TooltipType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class SmithingMaterialItem extends Item {

    public SmithingMaterialItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        TemperatureDataComponent temperatureDataComponent = stack.get(ModDataComponentTypes.TEMPERATURE_DATA);
        ProfileComponent profileComponent = stack.get(DataComponentTypes.PROFILE);

        if(temperatureDataComponent != null){
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".temperature").append(ScreenTexts.SPACE).append(String.valueOf(temperatureDataComponent.temperature())).formatted(Formatting.GRAY));
        }

        if (profileComponent != null && profileComponent.name().isPresent()) {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".artisan").append(profileComponent.name().get()).formatted(Formatting.GRAY));
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        TemperatureDataComponent temperatureDataComponent = stack.get(ModDataComponentTypes.TEMPERATURE_DATA);

        int temp;
        if(temperatureDataComponent != null){
            temp = temperatureDataComponent.temperature() - 1;
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
