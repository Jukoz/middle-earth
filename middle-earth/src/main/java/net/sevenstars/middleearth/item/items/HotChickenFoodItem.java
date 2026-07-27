package net.sevenstars.middleearth.item.items;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.resources.datas.common.RaceType;
import net.sevenstars.middleearth.resources.datas.races.RaceUtil;

public class HotChickenFoodItem extends Item {
    public HotChickenFoodItem(Properties settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if(world.isClientSide) return super.finishUsingItem(stack, world, user);

        if(user instanceof Player playerEntity) {
            if (!stack.isEmpty() && stack.get(DataComponentTypesME.TEMPERATURE_DATA) != null) {
                int temperature = stack.get(DataComponentTypesME.TEMPERATURE_DATA).temperature();
                float percentage = 1;
                if(stack.get(DataComponents.FOOD) != null) {
                    percentage = 15 + (float)stack.get(DataComponents.FOOD).nutrition() / 13;
                }
                playerEntity.setRemainingFireTicks((int) (temperature * percentage));
            }
        }
        return super.finishUsingItem(stack, world, user);
    }
}
