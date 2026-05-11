package net.sevenstars.middleearth.item.items.weapons;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.render.item.property.bool.BooleanProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.TemperatureDataComponent;
import org.jetbrains.annotations.Nullable;

public class HotComponentProperty implements BooleanProperty {
    public static final MapCodec<HotComponentProperty> CODEC = MapCodec.unit(new HotComponentProperty());

    public MapCodec<HotComponentProperty> getCodec() {
        return CODEC;
    }

    @Override
    public boolean test(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        TemperatureDataComponent temperatureDataComponent = stack.get(DataComponentTypesME.TEMPERATURE_DATA);
        return temperatureDataComponent != null && temperatureDataComponent.temperature() > 0;
    }
}
