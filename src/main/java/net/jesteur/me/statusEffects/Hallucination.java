package net.jesteur.me.statusEffects;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.mixin.InGameHUDInvoker;
import net.jesteur.me.utils.HallucinationData;
import net.jesteur.me.utils.IEntityDataSaver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Map;

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
            Map<StatusEffect, StatusEffectInstance> map =  entity.getActiveStatusEffects();
            int ticksLeft = map.get(this).getDuration();
            if(ticksLeft < 60)
                HallucinationData.addHallucination((IEntityDataSaver) entity, -2);
            else{
                HallucinationData.addHallucination((IEntityDataSaver) player, 1);
            }
        }
    }
    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        HallucinationData.resetHallucination((IEntityDataSaver) entity);
    }
}
