package net.sevenstars.middleearth.item.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.resources.datas.common.RaceType;
import net.sevenstars.middleearth.resources.datas.races.RaceUtil;

public class OrcishFoodItem extends Item {
    public OrcishFoodItem(Properties settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if(world.isClientSide) return super.finishUsingItem(stack, world, user);

        if(user instanceof Player playerEntity) {
            RaceType raceType = RaceUtil.getRaceType(playerEntity);
            if(raceType != RaceType.GOBLIN && raceType != RaceType.SNAGA && raceType != RaceType.ORC && raceType != RaceType.URUK) {
                user.addEffect(new MobEffectInstance(MobEffects.HUNGER, 20 * 30, 1));
                user.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 10, 0));

            }
        }
        return super.finishUsingItem(stack, world, user);
    }
}
