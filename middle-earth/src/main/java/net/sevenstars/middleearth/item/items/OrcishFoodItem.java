package net.sevenstars.middleearth.item.items;

import net.sevenstars.middleearth.resources.datas.RaceType;
import net.sevenstars.middleearth.resources.datas.races.RaceUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class OrcishFoodItem extends Item {
    public OrcishFoodItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
        if(world.isClient) return false;

        if(user instanceof PlayerEntity playerEntity) {
            RaceType raceType = RaceUtil.getRaceType(playerEntity);
            if(raceType != RaceType.ORC && raceType != RaceType.URUK) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 20 * 30, 1));
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 10, 0));

            }
        }
        return true;
    }
}
