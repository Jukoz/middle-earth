package net.sevenstars.middleearth.statusEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.sevenstars.middleearth.utils.IEntityDataSaver;

import java.util.Map;

public class DelversFearStatusEffect extends StatusEffect {
    public DelversFearStatusEffect(StatusEffectCategory statusEffectCategory, int i) {
        super(statusEffectCategory, i);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        if(entity instanceof PlayerEntity){
            Map<RegistryEntry<StatusEffect>, StatusEffectInstance> map = entity.getActiveStatusEffects();
            int ticksLeft = map.get(ModStatusEffects.DELVERS_FEAR).getDuration();
            if(ticksLeft != -1 && ticksLeft < HallucinationData.STOPPING_TICK)
                DelversFearData.addEffect((IEntityDataSaver) entity, -2);
            else{
                DelversFearData.addEffect((IEntityDataSaver) entity, 2);
            }
        }

        return true;
    }

    public void stop(LivingEntity entity){
        DelversFearData.stopEffect((IEntityDataSaver) entity);
    }


}
