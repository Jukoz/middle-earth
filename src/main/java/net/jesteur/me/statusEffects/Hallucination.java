package net.jesteur.me.statusEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class Hallucination extends StatusEffect {
    public Hallucination(){
        super(
                StatusEffectCategory.HARMFUL,
                0x006666
        );
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(entity instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) entity;

        }
    }
}
