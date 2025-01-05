package net.jukoz.me.statusEffects;

import net.jukoz.me.utils.HallucinationData;
import net.jukoz.me.utils.IEntityDataSaver;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.Map;

public class HallucinationStatusEffect extends StatusEffect {
    public HallucinationStatusEffect(StatusEffectCategory statusEffectCategory, int i) {
        super(statusEffectCategory, i);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(entity instanceof PlayerEntity){
            Map<RegistryEntry<StatusEffect>, StatusEffectInstance> map = entity.getActiveStatusEffects();
            int ticksLeft = map.get(ModStatusEffects.HALLUCINATION).getDuration();
            if(ticksLeft != -1 && ticksLeft < HallucinationData.STOPPING_TICK)
                HallucinationData.addHallucination((IEntityDataSaver) entity, -2);
            else{
                HallucinationData.addHallucination((IEntityDataSaver) entity, 2);
            }
        }

        return true;
    }

    public void stop(LivingEntity entity){
        HallucinationData.stopHallucination((IEntityDataSaver) entity);
    }


}
