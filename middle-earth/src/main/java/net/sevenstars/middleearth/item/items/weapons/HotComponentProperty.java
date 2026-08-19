package net.sevenstars.middleearth.item.items.weapons;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.TemperatureDataComponent;
import org.jetbrains.annotations.Nullable;

public class HotComponentProperty implements ItemPropertyFunction {
    @Override
    public float call(ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int seed) {
        TemperatureDataComponent temperatureDataComponent = stack.get(DataComponentTypesME.TEMPERATURE_DATA);
        return temperatureDataComponent != null && temperatureDataComponent.temperature() > 0 ? 1.0F : 0.0F;
    }
}
