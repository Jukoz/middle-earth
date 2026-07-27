package net.sevenstars.middleearth.statusEffects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.sevenstars.middleearth.utils.IEntityDataSaver;

public class EnshroudedStatusEffect extends MobEffect {
    public EnshroudedStatusEffect(MobEffectCategory statusEffectCategory, int i) {
        super(statusEffectCategory, i);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if(entity instanceof Player && entity instanceof IEntityDataSaver dataSaver){
            MobEffectInstance effect = entity.getEffect(ModStatusEffects.ENSHROUDED);
            if (effect == null) {
                return true;
            }
            int ticksLeft = effect.getDuration();
            if(ticksLeft != -1 && ticksLeft < EnshroudedData.STOPPING_TICK)
                EnshroudedData.addEffect(dataSaver, -2);
            else{
                EnshroudedData.addEffect(dataSaver, 2);
            }
        }

        return true;
    }

    public void stop(LivingEntity entity){
        if (entity instanceof IEntityDataSaver dataSaver) {
            EnshroudedData.stopEffect(dataSaver);
        }
    }


}
