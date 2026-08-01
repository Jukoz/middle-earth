package net.sevenstars.middleearth.item.items;

import net.minecraft.component.DataComponentTypes;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.resources.datas.common.RaceType;
import net.sevenstars.middleearth.resources.datas.races.RaceUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HotChickenFoodItem extends Item {
    public HotChickenFoodItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if(world.isClient) return super.finishUsing(stack, world, user);

        if(user instanceof PlayerEntity playerEntity) {
            if (!stack.isEmpty() && stack.get(DataComponentTypesME.TEMPERATURE_DATA) != null) {
                int temperature = stack.get(DataComponentTypesME.TEMPERATURE_DATA).temperature();
                float percentage = 1;
                if(stack.get(DataComponentTypes.FOOD) != null) {
                    percentage = 15 + (float)stack.get(DataComponentTypes.FOOD).nutrition() / 13;
                }
                playerEntity.setFireTicks((int) (temperature * percentage));
            }
        }
        return super.finishUsing(stack, world, user);
    }
}
