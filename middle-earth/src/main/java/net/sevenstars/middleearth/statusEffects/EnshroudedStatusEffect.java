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

public class EnshroudedStatusEffect extends StatusEffect {
    public EnshroudedStatusEffect(StatusEffectCategory statusEffectCategory, int i) {
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
            int ticksLeft = map.get(ModStatusEffects.ENSHROUDED).getDuration();
            if(ticksLeft != -1 && ticksLeft < EnshroudedData.STOPPING_TICK)
                EnshroudedData.addEffect((IEntityDataSaver) entity, -2);
            else{
                EnshroudedData.addEffect((IEntityDataSaver) entity, 2);
            }
        }

        return true;
    }

    public void stop(LivingEntity entity){
        EnshroudedData.stopEffect((IEntityDataSaver) entity);
    }


}
